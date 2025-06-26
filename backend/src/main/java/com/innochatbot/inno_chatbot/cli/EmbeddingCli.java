package com.innochatbot.inno_chatbot.cli;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;

/**
 * Spring Boot 애플리케이션 실행 직후 자동 실행되는 일괄처리 컴포넌트 1. docs 폴더 내 PDF 파일을 순회하며 2. 텍스트
 * 추출 → 청크 분할 → 임베딩 벡터 생성 3. DB의 chunk 테이블에 저장
 */
@Component
public class EmbeddingCli implements CommandLineRunner {
    //PDF파일을 읽고, 400자 단위로 나눈 다음, OpenAI 임베딩 벡터를 생성하고, chunk테이블에 저장하는 일괄처리(batch)파일

    @Value("${openai.api.key}")         // application.properties 또는 .env에서 API 키 주입
    private String apiKey;

    @Autowired                          // Spring에서 JdbcTemplate 자동 주입
    private JdbcTemplate jdbc;

    //@Autowired
    //FileDTO fileDTO;
    // chunk
    /*
    String chunkId
    String fileId
    int sequence
    string content
    varbinary(3027) embedding 벡터데이터
    String summary 
     */
    @Override
    public void run(String... args) throws Exception {
        // 시작 시 기존 chunk 테이블 데이터 삭제 (테스트 용도일 수 있음)
        //jdbc.update("DELETE FROM chunk");
        System.out.println("▶ EmbeddingCli 시작");

        // 1. docs 폴더 내 PDF 파일 순회
        Path docsDir = Paths.get("D:/Inno_ChatBot/docs");   // PDF 위치 경로
        Files.walk(docsDir)
                .filter(p -> p.toString().endsWith(".pdf")) // .pdf 확장자만 선택
                .forEach(pdfPath -> processPdf(pdfPath));      // 각 PDF 처리

        System.out.println("▶ EmbeddingCli 완료");
    }

    //파일의 해시 값 계산 함수
    private String getFileHash(Path filePath) throws Exception {
        byte[] fileBytes = Files.readAllBytes(filePath);
        var md = java.security.MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(fileBytes);
        return Base64.getEncoder().encodeToString(digest);
    }

    // 기존 해시 가져오는 함수
    private String getFileHashFromDb(String fileId) {
        try {
            return jdbc.queryForObject("SELECT hash FROM file WHERE file_id = ?", String.class, fileId);
        } catch (Exception e) {
            return null;
        }
    }

    // 개별 PDF 파일 처리 함수
    private void processPdf(Path pdfPath) {
        System.out.println("처리대상 PDF: " + pdfPath.toAbsolutePath());

        //현재 파일 hash 계산, file_id와 그 파일의 기존 해시 조회한다.
        try {
            //현재 파일 hash 계산
            String currentHash = getFileHash(pdfPath);

            //file_id, 기존 해시 조회
            String fileId = getFileId(pdfPath.getFileName().toString());
            String oldHash = getFileHashFromDb(fileId);

            if (fileId != null && currentHash.equals(oldHash)) {
                System.out.println("파일 변경 없음 -> 생략: " + pdfPath.getFileName());
                return;
            }

            //텍스트 추출
            String text = new PDFTextStripper().getText(PDDocument.load(pdfPath.toFile()));
            List<String> chunks = split(text, 400);

            if (fileId == null) {
                System.err.println("fileId 조회 실패: file 테이블 확인");
                return;
            }

            jdbc.update("DELETE FROM chunk WHERE fileId = ?", fileId);

            //chunk 저장
            for (int i = 0; i < chunks.size(); i++) {
                float[] vec = callEmbedding(chunks.get(i));
                saveChunk(fileId, i + 1, chunks.get(i), vec);
            }

            //file 테이블의 hash 갱신
            jdbc.update("UPDATE file SET hash = ? WHERE file_id = ?", currentHash, fileId);

            System.out.printf("  • 처리 완료: %s (%d 청크)%n", pdfPath.getFileName(), chunks.size());

        } catch (Exception e) {
            System.err.printf("  ! 오류: %s → %s%n", pdfPath.getFileName(), e.getMessage());
        }
        /*
        try (PDDocument doc = PDDocument.load(pdfPath.toFile())) {
            // ① PDF 텍스트 추출
            String text = new PDFTextStripper().getText(doc);

            // ② 텍스트를 400자 단위로 분할
            List<String> chunks = split(text, 400);

            // ③ file_path 테이블을 기준으로 file_id 조회
            String fileId = getFileId(pdfPath.getFileName().toString());
            System.out.println("fileId 조회: " + fileId);

            if (fileId == null) {
                System.err.println("fileId조회 실패: file_path 테이블 확인 필요");
                return;
            }

            // ④ 각 청크에 대해 임베딩 벡터 생성 + DB 저장
            for (int i = 0; i < chunks.size(); i++) {
                float[] vec = callEmbedding(chunks.get(i));
                saveChunk(fileId, i + 1, chunks.get(i), vec);
            }

            System.out.printf("  • 처리 완료: %s (%d 청크)%n", pdfPath.getFileName(), chunks.size());

        } catch (Exception e) {
            System.err.printf("  ! 오류: %s → %s%n", pdfPath.getFileName(), e.getMessage());
        }
         */
    }

    // 문자열을 지정 길이(size)로 분할하는 유틸 함수
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

    // OpenAI 임베딩 API 호출 (단일 청크 기준)
    private float[] callEmbedding(String chunk) {
        OpenAiService service = new OpenAiService(apiKey);

        var request = EmbeddingRequest.builder()
                .model("text-embedding-3-small") // 사용 모델
                .input(List.of(chunk)) // 입력 텍스트 리스트
                .build();

        var response = service.createEmbeddings(request)
                .getData().get(0);

        // List<Double> → float[]로 변환
        List<Double> data = response.getEmbedding();
        float[] vec = new float[data.size()];
        for (int i = 0; i < data.size(); i++) {
            vec[i] = data.get(i).floatValue();
        }

        return vec;
    }

    /*
    // filename 기준으로 file 테이블에서 file_id 조회
    private Long getFileId(String filename) {
        String sql = "SELECT fileId FROM file WHERE file_name LIKE ?";
        return jdbc.queryForObject(sql, Long.class, "%" + filename);
    }

    // chunk 테이블에 임베딩된 청크 삽입
    private void saveChunk(Long fileId, int pageNo, String content, float[] embedding) {
        String sql = "INSERT INTO chunk(manual_id, page_no, content, embedding) VALUES(?,?,?,?)";
        jdbc.update(con -> {
            var ps = con.prepareStatement(sql);
            ps.setLong(1, fileId);
            ps.setInt(2, pageNo);
            ps.setString(3, content);
            ps.setBytes(4, toBytes(embedding));  // float[] → byte[]
            return ps;
        }
        );
    }
     */
    //기존 코드에서 발생할 수 있는 오류 보완 가능한 코드
    private String getFileId(String filename) {
        String sql = "SELECT fileId FROM file WHERE file_name LIKE ?";
        List<String> results = jdbc.queryForList(sql, String.class, "%" + filename);
        return results.isEmpty() ? null : results.get(0);
    }

    private Long generateChunkId() {
        UUID uuid = UUID.randomUUID();
        Long chunkId = Math.abs(uuid.getMostSignificantBits()); //양수 보장
        return chunkId;
    }

    // chunk 테이블에 임베딩된 청크 삽입
    private void saveChunk(String fileId, int sequence, String content, float[] embedding) {
        Long chunkId = generateChunkId(); // 또는 UUID를 long으로 변환하거나 sequence 활용
        String sql = "INSERT INTO chunk(chunkId, fileId, sequence, content, toBytes(embedding), summary) VALUES(?,?,?,?,?,?)";
        jdbc.update(con -> {
            var ps = con.prepareStatement(sql);
            ps.setLong(1, chunkId);
            ps.setString(2, fileId);
            ps.setInt(3, sequence);
            ps.setString(4, content);
            ps.setBytes(5, toBytes(embedding));  // float[] → byte[]
            ps.setString(6, null);
            return ps;
        });
    }

    // float[] → byte[] 변환 (PostgreSQL pgvector용, LE 방식)
    private byte[] toBytes(float[] vec) {
        ByteBuffer buf = ByteBuffer.allocate(vec.length * 4);
        buf.order(ByteOrder.LITTLE_ENDIAN); // 리틀 엔디언으로 설정
        for (float v : vec) {
            buf.putFloat(v);
        }
        return buf.array();
    }
}
