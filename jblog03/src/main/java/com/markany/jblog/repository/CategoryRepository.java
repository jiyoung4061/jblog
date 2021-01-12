package com.markany.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.markany.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	SqlSession sqlSession;
	
	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}

	public List<CategoryVo> findCategoryList(String id) {
		return sqlSession.selectList("category.find", id);
	}
}
