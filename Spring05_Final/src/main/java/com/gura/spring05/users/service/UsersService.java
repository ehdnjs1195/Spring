package com.gura.spring05.users.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.users.dto.UsersDto;

public interface UsersService {

	public Map<String, Object> isExistId(String inputId);		// Map에 데이터를 담아서 잘 응답하면 Json 으로 자동으로 바뀐다.  
	public void addUser(UsersDto dto);
	public void validUser(UsersDto dto, HttpSession session, ModelAndView mView);
	public void showInfo(String id, ModelAndView mView);
}
