package com.example.demo.postimg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.post.entity.Post;
import com.example.demo.postimg.dto.PostimgDto;
import com.example.demo.postimg.entity.Postimg;
import com.example.demo.postimg.repository.PostimgRepository;
// import com.example.demo.util.S3FileUtil;
import com.example.demo.util.S3FileUtil;

@Service
public class PostimgServiceImpl implements PostimgService {
	
	// AWS S3 기능 제거를 위한 주석 처리
	 @Autowired
	 S3FileUtil fileUtil;

	@Autowired
	PostimgRepository repository;

	@Override
	public void savePostImage(int postId, MultipartFile[] file) {
		
		Post postEntity = Post.builder()
				.postid(postId)
				.build();

		List<Postimg> postimgList = new ArrayList<>();
		
		// 파일의 수만큼 반복해서 저장
		for (MultipartFile mulFile : file) {
			
			String url = fileUtil.fileUpload(mulFile);

			Postimg postimg = Postimg.builder()
					.storedFileName(url)
					.postid(postEntity)
					.build();

			System.out.println(postimg + " 저장완료");
			postimgList.add(postimg);
		}

		repository.saveAll(postimgList);
	}

	@Override
	public List<PostimgDto> getPostImages(int postId) {
		Post post = Post.builder().postid(postId).build();
		List<Postimg> list = repository.findByPostid(post);

		return list.stream().map(entity -> PostimgDto.builder()
				.fileid(entity.getFileid())
				.storedFileName(entity.getStoredFileName())
				.postid(entity.getPostid().getPostid())
				.build()
		).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void modify(int postId, MultipartFile[] files) {
		Post entity = Post.builder()
				.postid(postId)
				.build();
		
		// jpa로 일단 다시 지우고 다시 생성
		repository.deleteByPostid(entity);
		savePostImage(postId, files);
	}
}
