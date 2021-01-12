package com.markany.jblog.repository;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.markany.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	SqlSession sqlSession;
	
	public int insert(BlogVo blogVo) {
		return sqlSession.insert("blog.insert", blogVo);
	}

	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}

	public int update(BlogVo vo) {
		return sqlSession.update("blog.update", vo);
	}

}
