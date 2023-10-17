package kr.spring.service;

import java.util.List;

import kr.spring.entity.Board;

public interface BoardService {
	
	public List<Board> getList(); // 게시글 전체 조회
	
	public void resister(Board vo); //게시글 등록
	
	
	
}
