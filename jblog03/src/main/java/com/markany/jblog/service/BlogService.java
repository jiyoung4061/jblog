package com.markany.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markany.jblog.repository.BlogRepository;
import com.markany.jblog.vo.BlogVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	public BlogVo getBlog(String id) {
		return blogRepository.findById(id);
	}

	public boolean setMain(BlogVo vo) {
		int count = blogRepository.update(vo);
		return count == 1;
	}

}
