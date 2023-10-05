package kr.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.entity.Board;
import kr.spring.entity.Member;
import kr.spring.mapper.BoardMapper;

@Service  //여기가 실질적으로 일하는 service임. 
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper mapper;
	
	//10/05 인터페이스의 메서드 구현 하는 거임.
	@Override
	public List<Board> getList() {
		// 게시글 전체목록 가져오기 기능 = mapper가 하는 일임.controller > service > mapper.
		List<Board> list = mapper.getList();
		return list;
	}

	@Override
	public Member login(Member vo) {
		// 
		Member mvo = mapper.login(vo);
		return mvo;
	}

	@Override
	public void register(Board vo) {
		mapper.insertSelectKey(vo);
	}

	@Override
	public Board get(int idx) {
		// TODO Auto-generated method stub
		Board vo = mapper.read(idx);
		return vo;
	}
	
}
