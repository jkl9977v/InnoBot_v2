package com.innochatbot.api.service;


import org.springframework.stereotype.Service;


//2-4-1
@Service
public class EmbeddingService1 {
    /*
    @Value("${openai.api.key}") 
    private String apiKey;


    //private final OpenAiService svc = new OpenAiService(apiKey);

    public float[] embed(String text){
        OpenAiService svc = new OpenAiService(apiKey);

        //임베딩 요청 구성(text-embedding-3-small모델 사용)
        EmbeddingRequest request = EmbeddingRequest.builder()
                    .model("text-embedding-3-small")
                    .input(List.of())
                    .build();

        //API호출 후 List<Double> 결과를 받아 float[]로 변환
        List<Double> data = svc.createEmbeddings(request)
                  .getData().get(0).getEmbedding();
        float[] vec = new float[data.size()];
        for(int i=0; i < data.size(); i++){
             vec[i] = data.get(i).floatValue();
        }
        return vec;
                  //.stream().mapToFloat(f -> f.floatValue()).toArray();
    }

    // public float[] embed(String text) {
    //     return new float[1536];
    //     //;throw new UnsupportedOperationException("Not supported yet.");
    // }
    */
}