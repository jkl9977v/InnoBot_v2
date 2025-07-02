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

import com.innochatbot.api.service.EmbeddingService;

/**
 * Spring Boot 애플리케이션 실행 직후 자동 실행되는 일괄처리 컴포넌트 1. docs 폴더 내 PDF 파일을 순회하며 2. 텍스트
 * 추출 → 청크 분할 → 임베딩 벡터 생성 3. DB의 chunk 테이블에 저장
 */
@Component
public class EmbeddingCli implements CommandLineRunner {        // 텍스트 임베딩 기능을 수행한다.
    //PDF파일을 읽고, 400자 단위로 나눈 다음, OpenAI 임베딩 벡터를 생성하고, chunk테이블에 저장하는 일괄처리(batch)파일

    @Value("${openai.api.key}")         // application.properties 또는 .env에서 API 키 주입
    private String apiKey;

    @Autowired                          // Spring에서 JdbcTemplate 자동 주입
    private JdbcTemplate jdbc;

    @Autowired
    private EmbeddingService embeddingService;

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
        Path docsDir = Paths.get("D:/InnoBot_v3/docs");   // 문서파일 위치 경로
        Files.walk(docsDir)
                .filter(p -> p.toString().endsWith(".pdf")) // .pdf 확장자만 선택
                .forEach(filePath -> processPdf(filePath));      // 각 PDF 처리를 위한 파일경로 전달

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
    private void processPdf(Path filePath) {
        System.out.println("처리대상 PDF: " + filePath.toAbsolutePath());
        System.out.println("파일명: " + filePath.getFileName().toString());
        System.out.println("filePath: " + filePath);

        //현재 파일 hash 계산, file_id와 그 파일의 기존 해시 조회한다.
        try {

            //현재 파일 hash 계산
            String currentHash = getFileHash(filePath);
            //file_path 테이블을 기준으로 file_id 조회, 파일의 기존 해시 조회(수정 여부 확인하기 위해)
            String fileId = getFileId(filePath.getFileName().toString(), filePath);
            String oldHash = getFileHashFromDb(fileId);
            if (fileId != null && currentHash.equals(oldHash)) {
                System.out.println("파일 변경 없음 -> 생략: " + filePath.getFileName());
                return;
            } else {
                String text;
                List<String> chunks;

                // ① PDF 텍스트 추출
                try (PDDocument doc = PDDocument.load(filePath.toFile())) {
                    text = new PDFTextStripper().getText(doc);
                    // ② 텍스트를 400자 단위로 분할
                    chunks = split(text, 400);
                }

                if (fileId == null) {
                    System.err.println("fileId 조회 실패: file 테이블 확인" + fileId);
                    return;
                }

                jdbc.update("DELETE FROM chunk WHERE file_id = ?", fileId);

                // ④ 각 청크에 대해 임베딩 벡터 생성 + DB 저장
                for (int i = 0; i < chunks.size(); i++) {
                    //float[] vec = callEmbedding(chunks.get(i));
                    float[] vec = embeddingService.embed(chunks.get(i));
                    //System.out.println("embedding length: " + vec.length);
                    saveChunk(fileId, i + 1, chunks.get(i), vec);
                    //System.out.println("Embedding float length: " + vec.length);
                    //System.out.println("Embedding byte size: " + toBytes(vec).length);
                }

                //file 테이블의 hash 갱신
                jdbc.update("UPDATE file SET hash = ? WHERE file_id = ?", currentHash, fileId);
                System.out.printf("  • 처리 완료: %s (%d 청크)%n", filePath.getFileName(), chunks.size());

            }
        } catch (Exception e) {
            System.err.printf("  ! 오류: %s → %s%n", filePath.getFileName(), e.getMessage());
        }
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

    /*  //오류 발생할 수 있는 코드
    // fileName 기준으로 file 테이블에서 file_id 조회
    private Long getFileId(String fileName) {
        String sql = "SELECT fileId FROM file WHERE file_name LIKE ?";
        return jdbc.queryForObject(sql, Long.class, "%" + fileName);
    }
//
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
    //기존 코드에서 발생할 수 있는 오류 보완 가능한 코드, UUID를 활용, 양수를 보장하여 chunkId에 양수만 들어가게 함
    // fileName 기준으로 file 테이블에서 file_id 조회
    private String getFileId(String fileName, Path filePath) {
        String fullPath = filePath.toAbsolutePath().toString().replace("\\", "/"); //Windows 경로 정리
        String path = fullPath.replace("/" + fileName, "");
        String sql = "SELECT file_id FROM file f "
                + "join file_path fp "
                + "on f.path_id=fp.path_id "
                + " WHERE file_name LIKE ? and fp.path = ?";
        List<String> results = jdbc.queryForList(sql, String.class, "%" + fileName, path);
        return results.isEmpty() ? null : results.get(0);
    }

    //chunkId 생성 및 값 부여
    private Long generateChunkId() {
        UUID uuid = UUID.randomUUID();
        Long chunkId = Math.abs(uuid.getMostSignificantBits()); //양수 보장
        return chunkId;
    }

    // chunk 테이블에 임베딩된 청크 삽입
    private void saveChunk(String fileId, int sequence, String content, float[] embedding) {
        Long chunkId = generateChunkId(); // 또는 UUID를 long으로 변환하거나 sequence 활용
        String sql = "INSERT INTO chunk(chunk_id, file_id, sequence, content, embedding) VALUES(?,?,?,?,?)";
        //System.out.println(sql);
        /*summary*/
        jdbc.update(con -> {
            var ps = con.prepareStatement(sql);
            ps.setLong(1, chunkId);
            ps.setString(2, fileId);
            ps.setInt(3, sequence);
            ps.setString(4, content);
            ps.setBytes(5, toBytes(embedding));  // float[] → byte[]
            //ps.setString(6, null);
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
