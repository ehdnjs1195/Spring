package com.gura.spring05.shop.service;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.exception.NoDeliveryException;
import com.gura.spring05.shop.dao.OrderDao;
import com.gura.spring05.shop.dao.ShopDao;
import com.gura.spring05.shop.dto.OrderDto;
import com.gura.spring05.shop.dto.ShopDto;

@Repository
public class ShopServiceImpl implements ShopService{
	//의존 객체
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private OrderDao orderDao;
	
	
	
	@Override
	public void getList(ModelAndView mView) {
		List<ShopDto> list=shopDao.getList();
		mView.addObject("list",list);
	}
	
	/*
	 * 	- Spring 프레임 워크에서 트랜젝션 설정 방법
	 * 	1. pom.xml 에 spring-tx dependency 추가
	 * 	2. servlet-context.xml 에 transaction 설정 추가
	 * 	3. 트랜젝션을 적용할 서비스의 메서드에 @Transactional 어노테이션을 추가 한다. 
	 */
	//상품 구입 처리를 하는 비즈니스 로직
	@Transactional	//하나라도 실패 하지않고 다 성공하면 커밋. 중간 하나라도 실패하면(DataAccessException이 발생하면) 롤백시킴(취소). 	
	@Override		//=>DB에서 발생할 수도 있지만 필요하다면 프로그래머가 임의로 로직에 의해 발생 시킬수도 있다. DataAccessException을 상속받아서 커스텀 Exception을 만들 수 있다. Throw방식으로.
	public void buy(HttpServletRequest request, ModelAndView mView) {
		//로그인된 아이디
		String id=(String)request.getSession().getAttribute("id");
		//구입할 상품 번호
		int num=Integer.parseInt(request.getParameter("num"));
		//1. 상훔의 가격정보 얻어오기
		int price=shopDao.getPrice(num);
		//2. 상품의 가격만큼 계좌 잔액 줄이기
		ShopDto dto=new ShopDto();
		dto.setId(id);
		dto.setPrice(price);
		dto.setNum(num);
		shopDao.minusMoney(dto);
		//3. 가격의 10% 를 포인트로 적립
		shopDao.plusPoint(dto);
		//4. 재고의 갯수를 1 감소 시키기
		shopDao.minusCount(num);	//갯수가 부족하면 exception이 발생하는데 자동으로 plusPoint 와 minusMoney가 취소된다. 만약 트랜잭션이 붙지 않으면 순서대로 메서드를 실행시키기 때문에 재고는 없는 상태에서 돈은 줄고 포인트는 적립되는 상황이 발생하게 된다.
		//5. 배송 요청 정보를 추가한다.
		OrderDto dto2=new OrderDto();
		dto2.setId(id);
		dto2.setCode(num);
		dto2.setAddr("강남역 스타벅스");
		orderDao.addOrder(dto2);	
	}
}
