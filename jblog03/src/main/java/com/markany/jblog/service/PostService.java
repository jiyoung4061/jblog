package com.markany.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markany.jblog.repository.PostRepository;
import com.markany.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository; 
	
	public boolean addPost(PostVo postVo) {
		int count = postRepository.insert(postVo);
		return count == 1;
	}

	public List<PostVo> getPostList(Long categoryNo) {
		return postRepository.findPostList(categoryNo);
	}

	public Long getFirstPost(Long categoryNo) {
		return postRepository.findFirstPostNo(categoryNo);
	}

	public PostVo getPost(Long postNo) {
		return postRepository.findPost(postNo);
	}

	
}
