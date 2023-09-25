package kr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.spring.entity.Board;
import kr.spring.mapper.BoardMapper;

@RequestMapping("/board")
@RestController
// RestController는 비동기방식의 일만 처리하는 Controller
// Rest 전송방식을 처리할 수 있다.
// - 요청 URL + 전송방식(상태)을 묶어서 처리 가능.
// 사용 이유 : url의 통일성 및 단순화.
public class BoardRestcontroller {
	
	//앞으로 게시글 관련 요청할 떄는 무조건 board로 시작점.
	// /board/ 하고싶은 기능 요청 url
	// ex) /board/boardList.do
	
	@Autowired // 자동으로 연결하는 거.
	private BoardMapper mapper;// mybatis한테 JCBC를 실행하라고 요청하는 객체
	
	 @GetMapping("/all")
	 // 이렇게 하면 url의 마지막에 /board/all이렇게 된다.
	 //이제 Get방식으로만 요청해야지 볼 수 있음.
	 public List<Board> boardList() {
		 System.out.println("게시글 전체보기 기능수행");
		 //@responsebody를 넣어야지 비동기방식으로 됨.
		 // 확장성을 위해서 arraylist대신 List를 사용함.
		 List<Board> list = mapper.getLists();
		 return list;
	 }
	 
	 @PostMapping("/new")
	 //여기는 get방식으로 하면 오류 나서 post방식으로 해야됨.
	 public void boardInsert(Board board) {
		 System.out.println("게시글 작성 기능수행");
		 mapper.boardInsert(board);
	 }
	 
	 @GetMapping("/{idx}")
	 public Board boardContent(@PathVariable("idx") int idx) {
		 Board vo = mapper.boardContent(idx);  // 하나의 게시글 정보를 리턴함.
		 return vo;
		 
	 }
	 
	 @DeleteMapping("/{idx}")
	 public void boardDelete(@PathVariable("idx") int idx) {
		 System.out.println("게시글 삭제 기능수행");
		 mapper.boardDelete(idx);
	 }
	 @PutMapping("/update")
	 public void boardUpdate(@RequestBody Board vo) { //put방식으로 비동기 요청할때만 requestbody사용. requestbody는 vo를 사용하기 위해서
		 System.out.println("게시글 수정 기능수행");
		 mapper.boardUpdate(vo);
	 }
	 @PutMapping("/count")
	 public void boardCount(@RequestBody Board vo) {
		 mapper.boardCount(vo.getIdx());
	 }
		/*
		 * 선생님 방식
		 * @PutMapping("/count/{idx}")
		 *  public void boardCount(@PathVariable ("idx") int idx) {
		 * mapper.boardCount(idx); }
		 */
	 
	
	 
	 

}
