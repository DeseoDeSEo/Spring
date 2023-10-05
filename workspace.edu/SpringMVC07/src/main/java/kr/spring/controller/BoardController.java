package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("/get")
	// 게시글 상세보기
	// 하나만 받앙올 떄는 requestparam사용. idx를 받아와서 idx안에 넣겠다.
	public String get(@RequestParam("idx") int idx, Model model) {
		// 받아오는 자료는 board자료형태이고 이름은 vo이다.
		Board vo =service.get(idx);
		model.addAttribute("vo", vo);
		return "board/get";
	}
	
	
	//단순히 페이지 이동만 하니까 getmapping임.
	@GetMapping("register")
	public String register() {
		return "board/register";
				
	}
	
	
	@PostMapping("/register")
	public String register(Board vo, RedirectAttributes rttr) {
		// System.out.println(vo.toString());
		service.register(vo);
		// System.out.println(vo.toString()); idx,boardGroup이 채워져서 print된다.
		                       //result라는 이름으로 
		rttr.addFlashAttribute("result", vo.getIdx());
		return "redirect:/board/list";
	}
	
	
	//(10/05)BoardService는 클래스가 아니라 인터페이스임. BoardServiceImpl가 BoardService 업캐스팅 된다.
	@GetMapping("/list")
	public String boardList(Model model) {		
		// board파일의 boardList.
		List<Board> list = service.getList();
		model.addAttribute("list",list);
		
		return "board/list";
	}
	
	
}
