package com.markany.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.markany.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	SqlSession sqlSession;

	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert",postVo);
	}

	public List<PostVo> findPostList(Long categoryNo) {
		return sqlSession.selectList("post.findpostlist", categoryNo);
	}

	public PostVo findPost(Long no) {
		return sqlSession.selectOne("post.find", no);
	}

	public Long findFirstPostNo(Long no) {
		return sqlSession.selectOne("post.findfirstpost", no);
	}
	
	
}
