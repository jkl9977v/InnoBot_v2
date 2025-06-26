package com.innochatbot.api.service;

import com.innochatbot.api.dto.ChatResponse;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

//OpenAI API 호출
@Service
public class ChatService {

    private final JdbcTemplate jdbc;
    private final EmbeddingService embeddingService;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${chat.useDummy:true}")
    private boolean useDummy;

    private OpenAiService client;

    public ChatService(JdbcTemplate jdbc, EmbeddingService embeddingService) {
        this.jdbc = jdbc;
        this.embeddingService = embeddingService;
    }

    // @PostConstruct 로 초기화해도 좋습니다.
    private OpenAiService getClient() {
        if (client == null) {
            client = new OpenAiService(apiKey);
        }
        return client;
    }

    public ChatResponse handle(String question) {
        if (useDummy) {
            // 테스트용 더미 응답
            List<Long> dummyIds = List.of(1L, 2L, 3L);
            String dummyAnswer = "이것은 테스트용 더미 응답입니다.";
            return new ChatResponse(dummyAnswer, dummyIds);
        }

        // 1) 질문 임베딩
        float[] qVec = embeddingService.embed(question);

        // 2) 유사도 Top-K 검색 (K=5)
        String sql = """
          SELECT id, content
          FROM chunk
          ORDER BY (embedding <=> ?)
          LIMIT 5
        """;
        List<Map<String, Object>> rows = jdbc.queryForList(sql, toBytes(qVec));

        // 3) 프롬프트 생성
        StringBuilder prompt = new StringBuilder();
        for (Map<String, Object> row : rows) {
            prompt.append(row.get("content")).append("\n---\n");
        }
        prompt.append("Question: ").append(question);

        // 4) GPT 호출
        CompletionRequest req = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt(prompt.toString())
                .maxTokens(500)
                .build();

        String answer = getClient()
                .createCompletion(req)
                .getChoices().get(0).getText().trim();

        /*
        //3.5 터보 모델 사용시
        ChatMessage system = new ChatMessage("system", "다음 문서를 참고해서 사용자의 질문에 답하세요.");
ChatMessage user = new ChatMessage("user", prompt.toString());

ChatCompletionRequest req = ChatCompletionRequest.builder()
        .model("gpt-3.5-turbo")
        .messages(List.of(system, user))
        .maxTokens(500)
        .build();

String answer = getClient()
        .createChatCompletion(req)
        .getChoices().get(0).getMessage().getContent()
        .trim();
         */
        // 5) source chunk IDs 수집
        List<Long> ids = rows.stream()
                .map(r -> ((Number) r.get("id")).longValue())
                .toList();

        return new ChatResponse(answer, ids);
    }

    private byte[] toBytes(float[] vec) {
        ByteBuffer buf = ByteBuffer.allocate(vec.length * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        for (float v : vec) {
            buf.putFloat(v);
        }
        return buf.array();
    }
}
