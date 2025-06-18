package com.innochatbot.api.service;

import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class EmbeddingService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${embedding.useDummy:true}")
    private boolean useDummy;

    private OpenAiService client;

    @PostConstruct
    private void init() {
        // OpenAiService 클라이언트 초기화
        this.client = new OpenAiService(apiKey);
    }

    /**
     * 질문 또는 청크 텍스트를 임베딩합니다.
     * 개발/테스트 단계에서는 더미 벡터를 반환하도록 설정할 수 있습니다.
     */
    public float[] embed(String text) {
        System.out.println("EmbeddingService.useDummy="+useDummy);
        if (useDummy) {
            return embedDummy(text);
        } else {
            return embedProduction(text);
        }
    }

    //실제 OpenAI API를 사용해 임베딩 벡터를 반환합니다.
     
    private float[] embedProduction(String text) {
        // 임베딩 요청 구성
        EmbeddingRequest request = EmbeddingRequest.builder()
                .model("text-embedding-3-small")
                .input(List.of(text))
                .build();

        // API 호출 및 결과 변환
        List<Double> data = client.createEmbeddings(request)
                                  .getData().get(0).getEmbedding();
        float[] vec = new float[data.size()];
        for (int i = 0; i < data.size(); i++) {
            vec[i] = data.get(i).floatValue();
        }
        return vec;
    }
    

    //개발/테스트용 더미 벡터를 반환합니다.
    
    private float[] embedDummy(String text) {
        // 모델 차원(예: 1536)에 맞춰 0으로 초기화된 벡터 반환
        return new float[1536];
    }
}
