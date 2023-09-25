package kr.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.spring.entity.Auth;
import kr.spring.entity.Board;
import kr.spring.entity.Member;

@Mapper // MyBatis가 interFace를 찾기 위해 달아주는 부분.
public interface MemberMapper {
	//sql session factory가 이걸 이용해서 사용함.
	public Member registerCheck(String memID);

	public int join(Member m);

	public int login(String memID, String memPassword);


	public Member login(Member m);

	public int update(Member m);

	public int profileUpdate(Member mvo);

	public Member getMember(String memID);

	public void authInsert(Auth saveVO);
	

}
