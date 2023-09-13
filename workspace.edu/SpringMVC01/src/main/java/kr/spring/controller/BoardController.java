package kr.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.entity.Board;
import kr.spring.mapper.BoardMapper;

@Controller // 현재 클래스를 핸들러맵핑이 찾기 위해 컨트롤러로 등록하는 부분.
public class BoardController {

	@Autowired // 자동으로 연결하는 거.
	private BoardMapper mapper;// mybatis한테 JCBC를 실행하라고 요청하는 객체
	 
	@RequestMapping("/")// 요청url로 들어왔을 때 아래 기능을 수행하겠다.
	 public String home(){
		System.out.println("홈 기능 수행"); 
		return "redirect:/boardList.do"; // 다시 접속할 주소를 리다이렉트 방식으로 리턴하는거임. 
	}
	 
	@RequestMapping("/boardList.do") // 요청url로 들어왔을 때 아래 기능을 수행하겠다. 보통 메서드도 요청 url앞 부분이랑 맞춰줌.
	public String boardList(Model model) {
		System.out.println("게시판목록보기 기능수행");

		// web-inf > view > boardList생성.
		// 실행해서 열리는 페이지의 url에 /boardList.do 붙이면 console창에 게시판목록보기 기능수행이 출력됨.
		// 게시글 정보 가져오기
		// 한 개의 게시물로 하나로 묶음. board (dto)형으로 바꿈
		// 전체 게시글 조회기능
		List<Board> list = mapper.getLists();
		
		//객체 바인딩- 동적바인딩
		model.addAttribute("list", list);
		return "boardList";

	}
	@RequestMapping("/boardForm.do")
	public String boardForm() {
		System.out.println("글쓰기 페이지 이동");
		return "boardForm";
	}
	
	@RequestMapping("/boardInsert.do")
//	Board board라고 하면 Board객체 안에 알아서 옴. 1. input의 name과 vo name의 값이 같아야한다. 2. default 기본 생성자가 있어야한다. getter/setter도 있어야한다.
	public String boardInsert(Board board) {
		System.out.println("게시글 등록 기능 수행");
		
		mapper.boardInsert(board);
		return "redirect:/boardList.do";
	}
	
	@RequestMapping("/boardContent.do/{idx}")
//	url이 더 간편해짐.( 최신 버전)
	public String boardContent(@PathVariable("idx") int idx, Model model) {
		System.out.println("게시글 상세보기 기능 수행");
//		게시글 연결하고db관련된거는 mapper에게 부탁함.
//		한개의 게시글이 리턴타입임. 그래서board객체로 받아옴,
		
		// 게시글 조회수 증가
		mapper.boardCount(idx); //count =count+1
		Board vo = mapper.boardContent(idx);
        model.addAttribute("vo",vo);	
		return "boardContent";
//		모델이라는 공간에 담아서 boardContent로 보냄.
	}
	@RequestMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) {
		System.out.println("게시글 삭제기능수행");
		mapper.boardDelete(idx);
		return "redirect:/boardList.do";
//		여기서 idx를 받으면 Boardmapper.java로 이동함.  그 다음 BoardMapper.xml로 이동함.
	}
	
	@RequestMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		System.out.println("게시글 수정화면 이동 기능 수행");
// 		수정 화면으로 이동 => 하나의 게시글 정보(=idx)를 가지고(=> RequestParam으로 idx를 받아서 int idx에 저장. boardUpdateForm으로 이동함.	
		Board vo = mapper.boardContent(idx);
		model.addAttribute("vo", vo);
		return "boardUpdateForm";
		// boradUpdateForm.jsp로 이동함.
	}
	// 동기방식: 한 가지의 일을 하는 동안 다른 일은 못함.
	// 비동기방식: 동시에 다른일도 할 수 있음.
	@RequestMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) {
		mapper.boardUpdate(vo);
		return "redirect:/boardList.do";
	}
}
