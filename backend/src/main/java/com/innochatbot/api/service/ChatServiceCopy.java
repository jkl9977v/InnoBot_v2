package com.innochatbot.api.service;

import org.springframework.stereotype.Service;


@Service
public class ChatServiceCopy{
    /*
    @Autowired JdbcTemplate jdbc;
    @Autowired EmbeddingService embSvc;
    @Value("${openai.api.key}") String apiKey;

    public ChatResponse handle(String question){
        //1) 질문 임베딩
        float[] qVec = embSvc.embed(question);

        //2) 유사도 Top-K검색(예: K=5)
        String sql="""
            SELECT id, content
            FROM chunk
            ORDER BY (embedding <=> ?) // MariaDB 3.11+ 벡터 유사도 연산자 
            LIMIT 5
        """;
        List<Map<String,Object>> rows = jdbc.queryForList(sql, toBytes(qVec));

        //3) 프롬프트 만들기
        StringBuilder prompt = new StringBuilder();
        rows.forEach(r-> prompt.append(r.get("content")).append("\n---\n"));
        prompt.append("Question: ").append(question);

        //4) GPT호출
        OpenAiService svc = new OpenAiService(apiKey);
        var completion =svc.createCompletion(
            CompletionRequest.builder()
            .model("gpt-3.5-turbo")
            .prompt(prompt.toString())
            .maxTokens(500)
            .build()
            );
        
        String answer = completion.getChoices().get(0).getText().trim();
        List<Long> ids = rows.stream()
            .map(r -> ((Number)r.get("id")).longValue()).toList();
        
        return new ChatResponse(answer, ids);

    }
    private byte[] toBytes(float[] vec){
        // float[]->byte[] util
        // 1float =4bytes이므로 전체 크기는 vec.length*4
        ByteBuffer buf = ByteBuffer.allocate(vec.length*4);
        //openAI Embedding 저장용은 lieetle endian 순서
        buf.order(ByteOrder.LITTLE_ENDIAN);

        //각 float 값을 순서대로 byte buffer에 채워넣기
        for(float v:vec){
            buf.putFloat(v);
        }

        //내부 버퍼를 byte[]로 반환
        return buf.array();
        
    }
    */
}