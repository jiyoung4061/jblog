package com.markany.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markany.jblog.repository.CategoryRepository;
import com.markany.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryVo> getCategoryList(String id) {
		return categoryRepository.findCategoryList(id);
	}

	public boolean addCategory(CategoryVo vo) {
		int count = categoryRepository.insert(vo);
		return count == 1;
	}
}
