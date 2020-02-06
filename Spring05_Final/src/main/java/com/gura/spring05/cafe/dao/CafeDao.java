package com.gura.spring05.cafe.dao;

import java.util.List;

import com.gura.spring05.cafe.dto.CafeDto;

public interface CafeDao {
	public int getCount(CafeDto dto);
	public List<CafeDto> getList(CafeDto dto);
	public CafeDto getData(int num);
	public void addViewCount(int num);
	public void insert(CafeDto dto);
	public void update(CafeDto dto);
	public void delete(int num);
}
