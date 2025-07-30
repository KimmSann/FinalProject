package com.example.demo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

@Component
public class S3FileUtil {
	
	@Autowired
	AmazonS3 amazonS3;
	
	// S3 버킷명
	String bucketName = "hyunjaebucket";
	
	// 파일 업로드
	// 매개변수 : 파일 스트림
	// 반환값 : 파일의 경로
	public String fileUpload(MultipartFile file) {

		String originFilename = file.getOriginalFilename();
			
		// 파일의 확장자 확장자 구분하기
		int index = originFilename.lastIndexOf(".");
		// .에 따라 나눔
		String extention = originFilename.substring(index);
			
		// 새로운 파일명 만들기
		// 중복되지 않는 이름
		// 파일 업로드 시 기존 파일과 동일한 이름의 파일이 들어올 수 있음
		String uid = UUID.randomUUID().toString().substring(0, 10);
			
		// 랜덤으로 만든 문자열과 원본 파일 이름을 합쳐서 이상한 이름 생성
		String s3FileName = uid + originFilename;
			
		// S3 저장소에 파일 업로드
		InputStream is;
		
		try {
			// 실제 파일 데이터
			is = file.getInputStream();
			// stream -> byte
			byte[] bs = IOUtils.toByteArray(is);
			
			ObjectMetadata metadata = new ObjectMetadata();
			// 파일의 형식을 적기
			metadata.setContentType("image/" + extention);
			
			// 파일으 크기
			metadata.setContentLength(bs.length);
			
			ByteArrayInputStream stream = new ByteArrayInputStream(bs);
			
			// 인자 : 버킷명, 파일명, 파일스트림, 부가정보
			PutObjectRequest request = new PutObjectRequest(bucketName, s3FileName, stream, metadata);
			
			// 파일 업로드 api 호출
			amazonS3.putObject(request);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 파일을 업로드하고 난 후 파일 url 꺼내기
		String url = amazonS3.getUrl(bucketName, s3FileName).toString();
		return url;
	}
	
}
