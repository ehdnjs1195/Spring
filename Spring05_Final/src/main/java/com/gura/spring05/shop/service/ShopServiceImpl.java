package com.gura.spring05.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.shop.dao.OrderDao;
import com.gura.spring05.shop.dao.ShopDao;

@Repository
public class ShopServiceImpl implements ShopService{
	//의존 객체
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private OrderDao orderDao;
	
	
	
	@Override
	public void getList(ModelAndView mView) {
		
	}
	@Override
	public void buy(HttpServletRequest request, ModelAndView mView) {
		
	}
	
}
