package com.gura.spring05.shop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.shop.dto.ShopDto;

@Repository
public class ShopDaoImpl implements ShopDao{
	//의존객체
	@Autowired
	private SqlSession session;
	
	@Override
	public List<ShopDto> getList() {
		
		return null;
	}

	@Override
	public void minusCount(int num) {
		
		
	}

	@Override
	public void minusMoney(ShopDto dto) {
		
		
	}

	@Override
	public void plusPoint(ShopDto dto) {
		
		
	}

	@Override
	public int getPrice(int num) {
		
		return 0;
	}

}
