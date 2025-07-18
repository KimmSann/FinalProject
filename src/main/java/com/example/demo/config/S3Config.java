package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

// aws s3 설정 클래스

@Configuration
public class S3Config {
	
	// 프로퍼티에 있는 s3 정보 가져오기
	// 유지보수 시 프로퍼티에서 수정하는게 좋음
	@Value("${s3.access-key}")
	String accessKey;
	
	@Value("${s3.secret-key}")
	String secretKey;
	
	@Value("${s3.region}")
	String region;
	
	// 빈 생성
	@Bean
	public AmazonS3 amazonS3 () {
		
		System.out.println("S3!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		System.out.println(accessKey);
		System.out.println(secretKey);
		System.out.println(region);
		
		// 인증정보 생성
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		
		AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);
		
		AmazonS3 s3 = AmazonS3ClientBuilder
				.standard()
				.withCredentials(provider)
				.withRegion(region)
				.build();
		
		return s3;
	}
	
	
	

}
