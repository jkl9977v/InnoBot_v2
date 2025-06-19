package com.innochatbot.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 📂 [파일 저장 전용 서비스]
 * - 실제 PDF 파일을 서버에 저장
 * - 저장된 경로 반환
 * - DB에는 관여하지 않음 (쓰기 서비스에서 처리)
 */
@Service
public class ManualFileService {

    /**
     * 파일을 서버에 저장하고 경로를 반환
     * @param file 업로드된 MultipartFile
     * @return 저장된 경로 (ex: uploads/manuals/uuid_파일명.pdf)
     */
    public String store(MultipartFile file) throws IOException {
        String baseDir = "uploads/manuals/";
        String originalName = file.getOriginalFilename();
        String storedName = UUID.randomUUID() + "_" + originalName;

        File dest = new File(baseDir + storedName);
        file.transferTo(dest); // 실제 파일 저장

        return baseDir + storedName; // 저장된 경로 반환
    }
}
