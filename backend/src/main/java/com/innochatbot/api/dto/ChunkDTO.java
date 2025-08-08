package com.innochatbot.api.dto;

import org.apache.ibatis.type.Alias;
import org.springframework.security.web.webauthn.api.Bytes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("chunkDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChunkDTO {
	Long chunkId;
	String fileId;
	Integer sequence;
	String content;
	Bytes embeddeing;
}
