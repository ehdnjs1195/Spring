package com.gura.spring05.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gura.spring05.member.dao.MemberDao;
import com.gura.spring05.member.dto.MemberDto;

@Controller
public class AndroidController {
	@Autowired
	private MemberDao memberDao;
	
	/*
	 *  ["김구라", "해골", "원숭이"] 형식의 문자열 => JSONArray
	 *  
	 *  pom.xml 에 dependency로 jackson-databind가 있고, @ResponseBody 가 있어야 json을 쓸 수 있다.(jackson-databind가 있어야만 @ResponseBody가 동작한다.)
	 *  그러면  안드로이드에서도 json데이터를 받아쓸 수 있다.
	 */
	@ResponseBody
	@RequestMapping("/android/getnames")
	public List<String> getNames(){
		List<String> names=new ArrayList<>();
		names.add("김구라");
		names.add("해골");
		names.add("원숭이");
		
		return names;
	}
	/*
	 * 	{"num":1, "name":"김구라","isMan":true} 형식의 json 문자열이 응답된다. => JSONObject
	 */
	@ResponseBody
	@RequestMapping("/android/getdetail")
	public Map<String, Object> getDetail(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", 1);
		map.put("name", "김구라");
		map.put("isMan", true);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/android/getmember")
	public List<Map<String, Object>> getMember(){
		
		return memberDao.getList2();
	}
	
	@ResponseBody
	@RequestMapping("/android/member/list.do")
	public List<Map<String, Object>> memberGetList(){
		
		return memberDao.memberGetList();
	}
	
	/*
	 *  Dto 를 리턴하고 @ResponseBody 어노테이션을 붙여도 
	 *  Map 을 리턴한것과 동일하게 응답된다.
	 *  {"num":1, "name":"김구라", "addr":"노량진"}
	 *  필드명이 키값이 된다.(여기서는 소문자, 필드명을 소문자로 썼기 때문에)
	 */
	@ResponseBody
	@RequestMapping("/android/member/detail")
	public MemberDto memberDetail(@RequestParam int num) {
		
		return memberDao.getData(num);	//Dto 를 가져와서 Map으로 리턴됨
	}
	//안드로이드에서 member를 DB에서 삭제하는 메서드
	@ResponseBody
	@RequestMapping("/android/member/delete")
	public String memberDelete(@RequestParam int num) {
		memberDao.delete(num);
		return "{\"isSuccess\":true}";	//JSON 형식으로 응답.
	}
	
	
	/*
	 * 	[ POST, GET 방식으로 요청을 했을 때 Dto에 담기는 방식 ]
	 * 
	 * 	name=김구라&addr=노량진 					와 같은 문자열을 
	 * 
	 * 	MemberDto dto = new MemberDto();	스프링 프레임 워크가 알아서 Dto를 생성하고 세터메서드가 있는지 보고(필드와 이름이 같으면 있을터!) 있으면 담아준다.
	 *  dto.setName(name);
	 *  dto.setAddr(addr);
	 *  
	 *  심지어 @ModelAttribute는 생략도 가능하다. Dto만 선언해주어도 담긴다.
	 */
	@ResponseBody
	@RequestMapping("/android/member/insert")
	public String memberInsert(@ModelAttribute MemberDto dto) {
		memberDao.insert(dto);
		return "{\"isSuccess\":true}";	//JSON 형식으로 응답.
	}
}
