package com.innochatbot.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ChatMapper {
	//유사도 검색1 순차적으로(file -> chunk)
	public List<Map<String, Object>> fileNameEmbeddingSelect();

	public List<Map<String, Object>> chunkEmbeddingSelect(@Param("id") List<String> id);
	//List<String> candidateFileIds
	
	
	//유사도 검색 동시에
	public List<Map<String, Object>> fileAndChunkEmbeddingSelect();
	
	//기본형 (chunk만)
	public List<Map<String, Object>> selectChunkCandidates(@Param("limit") int limit);

}
