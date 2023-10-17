package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Board;
import kr.spring.entity.Criteria;
import kr.spring.entity.PageMaker;
import kr.spring.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("/reply")
	public String reply(@RequestParam("idx") int idx, Model model, @ModelAttribute("cri") Criteria cri) {
		Board vo = service.get(idx);
		model.addAttribute("vo", vo);
		return "board/reply";
	}
	@PostMapping("/reply")
	public String reply(Board vo,Criteria cri, RedirectAttributes rttr) {
		service.reply(vo);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		return "redirect:/board/list";
	}
	
	
	@GetMapping("/remove")
	public String remove(@RequestParam("idx") int idx, Criteria cri, RedirectAttributes rttr) {
		service.remove(idx);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		return"redirect:/board/list";
	}

	
	@GetMapping("/modify")
	//location.href는 get방식임.
	public String modify(@RequestParam("idx") int idx, Model model,  @ModelAttribute("cri") Criteria cri) {
		//model에 idx를 담아서 board/modify로 이동함.
		Board vo = service.get(idx);
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@PostMapping("/modify")
	public String modify(Board vo, Criteria cri, RedirectAttributes rttr) {
		service.modify(vo);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
//		type과 keyword도 넘겨야 검색 결과와 키워드 유지됨.
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		return "redirect:/board/list";
	}
	
	@GetMapping("/get")
	// 게시글 상세보기
	// 하나만 받앙올 떄는 requestparam사용. idx를 받아와서 idx안에 넣겠다.            10/12. model.addAttribute(cri)와 같은역할
	public String get(@RequestParam("idx") int idx, Model model, @ModelAttribute("cri") Criteria cri) {
		// 받아오는 자료는 board자료형태이고 이름은 vo이다.
		Board vo = service.get(idx);
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
	@RequestMapping("/list")
	public String boardList(Model model, Criteria cri) {		
		// board파일의 boardList.
		// 이제는 페이지 정보를 알고 있는 Criteria객체를 Service에게 전달해야됨.
		List<Board> list = service.getList(cri); //크리테리아를 기준으로 리스트를 불러올거임.
		//페이징 처리에 필요한 pageMaker객체도 생성해야한다.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);         // pagemaker가 페이지기법을 하기위한 cri객체 전달
		pageMaker.setTotalCount(service.totalCount(cri)); //페이징 기법을 하려면 전체 게시글 개수를 알려줘야함.
		
		System.out.println(pageMaker.toString());
		
		model.addAttribute("list",list);
		model.addAttribute("pageMaker",pageMaker); // 페이징 정보를 알고 있는 객체를 전달
		return "board/list";
	}
	
	
}
