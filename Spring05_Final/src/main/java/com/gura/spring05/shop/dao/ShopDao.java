package com.gura.spring05.shop.dao;

import java.util.List;

import com.gura.spring05.shop.dto.ShopDto;

public interface ShopDao {
	//상품의 목록을 리턴해주는 메서드
	public List<ShopDto> getList();
	//상품 재고를 감소 시키는 메서드
	public void minusCount(int num);
	//잔고 감소 시키는 메서드
	public void minusMoney(ShopDto dto);
	//포인트를 증가 시키는 메서드
	public void plusPoint(ShopDto dto);
	//특상 상품의 가격을 리턴하는 메서드
	public int getPrice(int num);
}
