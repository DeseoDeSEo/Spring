package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.entity.Board;

@Mapper // MyBatis interFace를 찾기 위해 달아주는 부분.
public interface BoardMapper {
	
	
	 public List<Board> getLists();

	public void boardInsert(Board board);


	public Board boardContent(int num);
	
	public void boardDelete(int num);
	public void boardUpdate(Board vo);


	public void boardCount(int num);
}
