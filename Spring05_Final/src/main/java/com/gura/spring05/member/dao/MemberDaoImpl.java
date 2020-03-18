package com.gura.spring05.member.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.member.dto.MemberDto;

@Repository	//component scan 을 통해서 bean 으로 만들기 위함.스프링에 맡기겠다. 스프링이 객체를 생성하고 관리해라!    그런데 dao는 어떤 이유때문에 repository로 어노테이션을 붙인다.
public class MemberDaoImpl implements MemberDao{

	//핵심 의존 객체를 spring 으로 부터 주입 받기(Dependency Injection (DI))
	@Autowired
	private SqlSession session;		//SqlSession이란 타입 객체가 있으면 session이란 필드를 사용할 때 값을 넣어줘라!!
	//SqlSession 은 servlet-context.xml 에서 이미 미리 다 준비가 되어 있는 상태이다! 그래서 받고 받고 받고.. 단계를 거친것이다. 그렇기 때문에 메서드를 통해서 맵퍼를 사용할 수 있게된다. 맵퍼는 configuration 어쩌구에서 이미 준비가 되어 있는것.
	
	@Override
	public List<MemberDto> getList() {
		List<MemberDto> list=session.selectList("member.getList");	// MemberMapper를 가리키는 것이다.	 mapper의 namespace=>(member), <select id=>(메서드 getList) resultType=>(List 제너릭에 들어가는 MemberDto) >
							//session은 원래 null이 맞지만  객체의 참조값을 스프링의 컨테이너에서 넣어주라 해서 참조값이 있다.		=>Dependency Injection 인터페이스 타입으로
		
		return list;		
	}		
	@Override
	public void delete(int num) {
		session.delete("member.delete",num);	//sql의 id 는 delete, parameter는 int type 의 num
	}
	
	@Override
	public void insert(MemberDto dto) {
		session.insert("member.insert", dto);
	}
	
	@Override
	public MemberDto getData(int num) {
		MemberDto dto=session.selectOne("member.getData", num);
		//이 셀렉트는 row가 하나 짜리이다.(목록이 아닌) 따라서 selectOne 메서드를 이용하고 resultType => MemberDto 를 통해 Dto에 담아준다.
		return dto;
	}
	
	@Override
	public void update(MemberDto dto) {
		session.update("member.update", dto);
	}
	
	@Override
	public List<Map<String, Object>> getList2() {
		List<Map<String, Object>> list=session.selectList("member.getList2");	// List<Map>의 형태로 데이터가 담긴다.
		return list;
	}
	
	@Override
	public List<Map<String, Object>> memberGetList() {
		return session.selectList("member.memberGetList");	// 알아낸 사실. 만약 mapper에서 가져오는 이름이 겹치지 않는다면 앞에 namespace를 생략해도  찾아올 수 있다. => namespace는 mapper를 찾아주는 역할.
	}
}

/*
 *	select를 했을 때 row가 여러개면 selectList를 한다 => List를 리턴 => List<> 타입으로 받아야 한다.
 *	select된 row 하나 하나 담을 타입을 결정하는 것이 resultType => Dto, int, String, Map<> 등등.. 
 */
