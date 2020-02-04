package com.gura.spring05.users.dao;

import com.gura.spring05.users.dto.UsersDto;

public interface UsersDao {
	public boolean isExist(String inputId);
	public void insert(UsersDto dto);
	public String getPwdHash(String inputId);	//입력한 아이디를 이용해서 암호화된 비밀번호를 가져오도록. null일 가능성도 있다.
}
