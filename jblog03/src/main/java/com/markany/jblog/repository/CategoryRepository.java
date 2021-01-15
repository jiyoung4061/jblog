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

//	public List<Long> findPostCountOfCategory(String id) {
//		return sqlSession.selectList("categroy.findpostcount", id);
//	}

	public int delete(Long no) {
		return sqlSession.delete("category.delete", no);
	}

	public Long findFirstCategory(String id) {
		return sqlSession.selectOne("category.findfirstno", id);
	}
}
