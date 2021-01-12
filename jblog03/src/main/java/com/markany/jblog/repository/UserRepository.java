package com.markany.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.markany.jblog.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		return sqlSession.insert("user.insert", userVo);
	}
	
	public UserVo findById(String id) {
		UserVo vo = sqlSession.selectOne("user.findById", id);
		return vo;
	}
}
