package com.gura.spring05.file.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.exception.CanNotDeleteException;
import com.gura.spring05.file.dao.FileDao;
import com.gura.spring05.file.dto.FileDto;

@Repository
public class FileServiceImpl implements FileService{
	@Autowired
	private FileDao dao;
	
	@Override
	public void list(HttpServletRequest request) {
		/* 
		 * 	request 에 검색 keyword 가 전달될 수도 있고 안 될 수도 있다.
		 * 	- 전달 안 되는 경우: navbar 에서 file 목록 보기를 누른경우.
		 * 	- 전달 되는 경우: 하단에 검색어를 입력하고 검색 버튼을 누른 경우
		 * 	- 전달 되는 경우2: 이미 검색을 한 상태에서 하단 페이지 번호를 누른 경우
		 */
		//검색과 관련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		
		//검색 키워드가 존재한다면 키워드를 담을 FileDto 객체 생성 
		FileDto dto=new FileDto();
		if(keyword != null) {//검색 키워드가 전달된 경우
			if(condition.equals("titlename")) {//제목+파일명 검색
				dto.setTitle(keyword);
				dto.setOrgFileName(keyword);
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
		
		//FileDto 객체에 위에서 계산된 startRowNum 과 endRowNum을 담는다.
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		//1. DB에서 파일 목록을 얻어온다.
		List<FileDto> list=dao.getList(dto);	//전달된 dto에 맞는 정보만 select해 오겠다!
		//2. view page 에 필요한 값을 request에 담아둔다.
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("list", list);
		//전체 글의 갯수도 담기
		request.setAttribute("totalRow", totalRow);
	}
	
	@Override
	public void saveFile(HttpServletRequest request, FileDto dto) {
		//파일을 저장할 폴더의 절대 경로를 얻어온다.
		String realPath=request.getServletContext().getRealPath("/upload");
		//콘솔창에 테스트 출력
		System.out.println(realPath);
		
		//MultipartFile 객체의 참조값 얻어오기
		//FileDto 에 담긴 MultipartFile 객체의 참조값을 얻어온다.
		MultipartFile mFile=dto.getMyFile();
		//원본 파일명
		String orgFileName=mFile.getOriginalFilename();
		//파일 사이즈
		long fileSize=mFile.getSize();
		//저장할 파일의 상세 경로
		String filePath=realPath+File.separator;
		//디렉토리를 만들 파일 객체 생성
		File file=new File(filePath);
		if(!file.exists()){//디렉토리가 존재하지 않는다면
			file.mkdir();//디렉토리를 만든다.
		}
		//파일 시스템에 저장할 파일명을 만든다. (겹치치 않게)
		String saveFileName=
				System.currentTimeMillis()+orgFileName;
		try{
			//upload 폴더에 파일을 저장한다. (저장해 둔것을 옮겨오는 작업. 옮겨와야 저장이 된다.)
			mFile.transferTo(new File(filePath+saveFileName));	// 어디에 저장할지에 대한 파일 객체를 넣어준다. 결국 경로는 /upload 이다. 그리고 실제 파일은 upload 폴더에 있다. 
		}catch(Exception e){
			e.printStackTrace();
		}
		//FileDto 객체에 추가 정보를 담는다.
		String id=(String)request.getSession().getAttribute("id");
		dto.setWriter(id); //작성자
		dto.setOrgFileName(orgFileName);
		dto.setSaveFileName(saveFileName);
		dto.setFileSize(fileSize);
		//FileDao 객체를 이용해서 DB 에 저장하기
		dao.insert(dto);			
	}
	
	@Override
	public void getFileData(ModelAndView mView, int num) {
		//다운로드 시켜줄 파일의 정보를 어다
		FileDto dto=dao.getData(num);
		//ModelAndView 객체에 담아주기
		mView.addObject("dto", dto);
	}
	@Override
	public void addDownCount(int num) {
		//다운로드 횟수 증가 시키기
		dao.addDownCount(num);
	}
	@Override
	public void removeFile(HttpServletRequest request) {
		//1. 삭제할 파일의 번호를 읽어온다.
		int num=Integer.parseInt(request.getParameter("num"));
		//2. 삭제할 파일의 정보를 읽어와서 삭제할 파일의 저장된 파일명을 얻어낸다.
		FileDto dto=dao.getData(num);
		//파일 작성자와 로그인된 아이디가 다르면 예외를 발생시킨다.(예외가 발생하면 아래로 수행이 작업이 진행되지 않는다.)
		String id=(String)request.getSession().getAttribute("id");
		if(!id.equals(dto.getWriter())) {
			//예외를 발생 시켜서 메서드가 정상 수행되지 않도록 막는다.
			throw new CanNotDeleteException();
		}
		
		String saveFileName=dto.getSaveFileName();
		//3. DB에서 파일정보 삭제
		dao.delete(num);
		//4. 파일 시스템에서 파일 삭제
		String path=request.getServletContext().getRealPath("/upload")+File.separator+saveFileName;		//c:\xxx\xxx\ upload(getRealPath)  \(separator)  32124142.jpg(saveFileName)
		File f=new File(path);
		boolean isDelete=f.delete();
		System.out.println("파일삭제확인 : "+isDelete);
	}
}
