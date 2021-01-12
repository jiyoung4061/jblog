package com.markany.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markany.jblog.repository.BlogRepository;
import com.markany.jblog.repository.UserRepository;
import com.markany.jblog.repository.CategoryRepository;
import com.markany.jblog.vo.BlogVo;
import com.markany.jblog.vo.CategoryVo;
import com.markany.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional
	public boolean join(UserVo userVo) {
		BlogVo blogVo = new BlogVo();
		CategoryVo categoryVo = new CategoryVo();
		int count = 0;

		userRepository.insert(userVo);
		userVo = userRepository.findById(userVo.getId());
		blogVo.setId(userVo.getId());
		blogVo.setLogo("default-img.jpg");
		blogVo.setTitle("기본페이지");

		blogRepository.insert(blogVo);

		categoryVo.setName("미분류");
		categoryVo.setDescription("카테고리를 설정해주세요");
		categoryVo.setId(blogVo.getId());
		count = categoryRepository.insert(categoryVo);

		return count == 1;
	}

	public UserVo getUser(UserVo vo) {
		// TODO Auto-generated method stub
		return null;
	}
}
