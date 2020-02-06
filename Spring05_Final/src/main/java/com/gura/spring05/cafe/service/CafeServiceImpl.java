package com.gura.spring05.cafe.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.cafe.dao.CafeDao;
import com.gura.spring05.cafe.dto.CafeDto;
import com.gura.spring05.file.dto.FileDto;

@Repository
public class CafeServiceImpl implements CafeService{
	@Autowired
	private CafeDao dao;
	
	@Override
	public void list(HttpServletRequest request) {
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		
		//검색 키워드가 존재한다면 키워드를 담을 FileDto 객체 생성 
		CafeDto dto=new CafeDto();
		if(keyword != null) {//검색 키워드가 전달된 경우
			if(condition.equals("titlewriter")) {//제목+작성자명 검색
				dto.setTitle(keyword);
				dto.setWriter(keyword);
			}else if(condition.equals("title")) {//제목 검색
				dto.setTitle(keyword);
			}else if(condition.equals("writer")) {//작성자 검색
				dto.setWriter(keyword);
			}
			/*
			 *  검색 키워드에는 한글이 포함될 가능성이 있기 때문에
			 *  링크에 그대로 출력가능하도록 하기 위해 미리 인코딩을 해서
			 *  request 에 담아준다.
			 */
			String encodedKeyword=null;
			try {
				encodedKeyword=URLEncoder.encode(keyword, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//키워드와 검색조건을 request 에 담는다. 
			request.setAttribute("keyword", keyword);
			request.setAttribute("encodedKeyword", encodedKeyword);
			request.setAttribute("condition", condition);
		}	
		
		//한 페이지에 나타낼 row 의 갯수
		final int PAGE_ROW_COUNT=5;
		//하단 디스플레이 페이지 갯수
		final int PAGE_DISPLAY_COUNT=5;
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//전체 row 의 갯수를 읽어온다.
		int totalRow=dao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}	
		
		//CafeDto 객체에 위에서 계산된 startRowNum 과 endRowNum을 담는다.
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		//1. DB에서 파일 목록을 얻어온다.
		List<CafeDto> list=dao.getList(dto);	//전달된 dto에 맞는 정보만 select해 오겠다!
		//2. view page 에 필요한 값을 request에 담아둔다.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("list", list);
		request.setAttribute("totalRow", totalRow);
	}
	
	@Override
	public void showDetail(int num, int pageNum, ModelAndView mView) {
		
		//2. DB 에서 해당 글 정보를 얻어온다.
		CafeDto dto=dao.getData(num);
		//3. 해당글의 조회수를 1 증가시킨다.
		dao.addViewCount(num);
		//4. 글 정보를 등답한다.
		mView.addObject("pageNum", pageNum);
		mView.addObject("dto", dto);
		mView.addObject("num", num);	
	}
	@Override
	public void addContent(CafeDto dto, HttpServletRequest request) {
		String id=(String)request.getSession().getAttribute("id");
		dto.setWriter(id);
		dao.insert(dto);
	}
	
	@Override
	public void showContent(int num, int pageNum, HttpServletRequest request) {
		CafeDto dto=dao.getData(num);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("dto", dto);
		request.setAttribute("num", num);
	}
	
	@Override
	public void updateContent(int pageNum, CafeDto dto, HttpServletRequest request) {
		String id=(String)request.getSession().getAttribute("id");
		dto.setWriter(id);
		dao.update(dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("dto", dto);
	}
	
	@Override
	public void deleteContent(int num) {
		dao.delete(num);
	}
}
