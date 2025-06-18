package com.innochatbot.inno_chatbot.cli;

import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.embedding.EmbeddingRequest;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//애플리케이션이 시작될 때 한번 실행되는 커맨드라인툴
/*
1. 전체 역할 
- Spring Boot 애플리케이션이 부팅된 직후(CommahndLineRunner 구현)
- docs 폴더 안의 모든 PDF를 탐색
- 각 PDF에서 텍스트를 뽑아 400자 단위로 나누고
- OpenAI 임베딩을 호출해 벡터를 얻은 뒤
- chunk 테이블에 저장
이 과정을 자동화된 일괄작업(batch job)으로 한 번에 처리하기 위해 만든 파일
*/

@Component
public class EmbeddingCli implements CommandLineRunner {

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void run(String... args) throws Exception {
        //이전 데이터 삭제(이 부분 추후 지우기)
        jdbc.update("DELETE FROM chunk");
        System.out.println("▶ EmbeddingCli 시작");
        // 1) docs 폴더 내 PDF 순회
        Path docsDir = Paths.get("D:/Inno_ChatBot/docs");
        Files.walk(docsDir)
             .filter(p -> p.toString().endsWith(".pdf"))
             .forEach(pdfPath -> processPdf(pdfPath));
        System.out.println("▶ EmbeddingCli 완료");
    }

    private void processPdf(Path pdfPath) {
        System.out.println("처리대상 PDF:"+pdfPath.toAbsolutePath());
        try (PDDocument doc = PDDocument.load(pdfPath.toFile())) {
            String text = new PDFTextStripper().getText(doc);
            List<String> chunks = split(text, 400);
            Long manualId = getManualId(pdfPath.getFileName().toString());
            System.out.println("manualId 조회: "+manualId);
            if(manualId == null){
                System.err.println("manualId조회 실패:file_path테이블 확인 필요");
                return;
            }
            for (int i = 0; i < chunks.size(); i++) {
                float[] vec = callEmbedding(chunks.get(i));
                saveChunk(manualId, i + 1, chunks.get(i), vec);
            }
            System.out.printf("  • 처리 완료: %s (%d 청크)%n", pdfPath.getFileName(), chunks.size());
        } catch (Exception e) {
            System.err.printf("  ! 오류: %s → %s%n", pdfPath.getFileName(), e.getMessage());
        }
    }

    // 2-3-3. 텍스트 400자 분할
    private List<String> split(String text, int size) {
        List<String> result = new ArrayList<>();
        int pos = 0;
        while (pos < text.length()) {
            int end = Math.min(pos + size, text.length());
            result.add(text.substring(pos, end));
            pos = end;
        }
        return result;
    }

    // 2-3-4. OpenAI 임베딩 호출
    private float[] callEmbedding(String chunk) {
        OpenAiService service = new OpenAiService(apiKey);
        var request = EmbeddingRequest.builder()
                          .model("text-embedding-3-small")
                          .input(List.of(chunk))
                          .build();
        var response = service.createEmbeddings(request).getData().get(0);
        // List<Double> → float[]
        List<Double> data = response.getEmbedding();
        float[] vec = new float[data.size()];
        for (int i = 0; i < data.size(); i++) {
            vec[i] = data.get(i).floatValue();
        }
        return vec;
        //return new float[1536];
    }

    // 2-3-5. manual_id 조회 (파일명 기준)
    private Long getManualId(String filename) {
        String sql = "SELECT id FROM manual WHERE file_path LIKE ?";
        return jdbc.queryForObject(sql, Long.class, "%" + filename);
    }

    // 2-3-5. chunk 테이블 INSERT
    private void saveChunk(Long manualId, int pageNo, String content, float[] embedding) {
        String sql = "INSERT INTO chunk(manual_id, page_no, content, embedding) VALUES(?,?,?,?)";
        jdbc.update(con -> {
            var ps = con.prepareStatement(sql);
            ps.setLong(1, manualId);
            ps.setInt(2, pageNo);
            ps.setString(3, content);
            ps.setBytes(4, toBytes(embedding));
            return ps;
        });
    }

    // float[] → byte[] 변환 (4바이트씩 LE)
    private byte[] toBytes(float[] vec) {
        ByteBuffer buf = ByteBuffer.allocate(vec.length * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        for (float v : vec) buf.putFloat(v);
        return buf.array();
    }
}