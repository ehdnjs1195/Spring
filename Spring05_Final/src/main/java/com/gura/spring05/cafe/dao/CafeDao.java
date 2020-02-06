package com.gura.spring05.cafe.dao;

import java.util.List;

import com.gura.spring05.cafe.dto.CafeDto;

public interface CafeDao {
	public int getCount();
	public List<CafeDto> getList(CafeDto dto);
	public CafeDto getData(int num);
}
