package com.gura.spring05.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.cafe.dto.CafeDto;

@Repository
public class CafeDaoImpl implements CafeDao{
	@Autowired
	private SqlSession session;
	@Override
	public int getCount() {
		return session.selectOne("cafe.getCount");
	}
	@Override
	public List<CafeDto> getList(CafeDto dto) {
		List<CafeDto> list=session.selectList("cafe.getList", dto);
		return list;
	}
	@Override
	public CafeDto getData(int num) {
		return session.selectOne("cafe.getData", num);
	}
}
