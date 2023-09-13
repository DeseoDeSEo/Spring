package kr.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.entity.Board;
import kr.spring.mapper.BoardMapper;

@Controller // 현재 클래스를 핸들러맵핑이 찾기 위해 컨트롤러로 등록하는 부분.
public class BoardController {

	@Autowired // 자동으로 연결하는 거.
	private BoardMapper mapper;// mybatis한테 JCBC를 실행하라고 요청하는 객체
	 
	@RequestMapping("/")// 요청url로 들어왔을 때 아래 기능을 수행하겠다.
	 public String home(){
		System.out.println("홈 기능 수행"); 
		return "main"; // 다시 접속할 주소를 리다이렉트 방식으로 리턴하는거임. 
	}
	

}
