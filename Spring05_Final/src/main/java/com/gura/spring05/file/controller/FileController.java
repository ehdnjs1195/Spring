package com.gura.spring05.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring05.file.dto.FileDto;
import com.gura.spring05.file.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService service;
	
	//파일 목록 보기 요청 처리
	@RequestMapping("/file/list")
	public ModelAndView list(ModelAndView mView, HttpServletRequest request) {
		//파일 목록과 페이징 처리에 필요한 값들을 request 에 담아주는 서비스 메소드 호출하기
		service.list(request);
		mView.setViewName("file/list");
		return mView;
	}
	//파일 업로드 폼 요청 처리
	@RequestMapping("/file/upload_form")
	public ModelAndView authUploadForm(HttpServletRequest request) {
		
		return new ModelAndView("file/upload_form");
	}
	
	//파일 업로드 요청 처리
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	public ModelAndView authUpload(HttpServletRequest request, @ModelAttribute FileDto dto) {	//폼이 제출 되면서 제목과 파일정보가 자동으로 dto에 저장된다.
		service.saveFile(request, dto);
		return new ModelAndView("redirect:/file/list.do");
	}
	//파일 다운로드 처리
	@RequestMapping("/file/download")
	public ModelAndView download(ModelAndView mView, @RequestParam int num) {	//파일 정보를 int num을 이용해서 select 하고 
		service.getFileData(mView, num);
		service.addDownCount(num);
		mView.setViewName("fileDownView");	//다운로드를 view에서 할 수 있도록 해준다. 
		/*
		 *  /view/fileDownView.jsp 라는 의미가 되는데 jsp를 찾아가기 전에 
		 *  찾아기 전에 fileDownView bean 을 먼저 찾는다. 찾아서 있으면 fileDownView 로 포워드 이동한다.
		 *  => download.jsp로 응답을 하지 않겠다. 파일의 목적에 맞지 않기 때문에!!!
		 */
		return mView;
	}
	
	@RequestMapping("/file/delete")
	public ModelAndView authDelete(HttpServletRequest request) {
		service.removeFile(request);
		return new ModelAndView("redirect:/file/list.do");
	}
}
