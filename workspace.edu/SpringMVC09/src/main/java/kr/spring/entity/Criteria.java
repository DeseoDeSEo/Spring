package kr.spring.entity;

import lombok.Data;

@Data
public class Criteria { //기준이라는 뜻임.
	
	//10.13 검색 기능에 필요한 변수
	private String type; //이름, 제목, 내용
	private String keyword; //검색 내용
	
	private int page; //현재 페이지 번호를 저장할 변수
	private int perPageNum; //한 페이지에 보여줄 게시글의 개수
	
	//Criteria 기본 셋팅 생성자를 통해서 하기
	public Criteria() {
		this.page  = 1;
		this.perPageNum =5;
	}
	//현재 페이지의 게시글의 시작번호를 구하는 메소드
	// :현재 1페이지를 보고 싶으면 1부터 10까지의 게시물을 보여줌.
	//  다음 페이지를 클릭하면 11 ~ 20까지의 게시물을 보여줌.
	// 예외 : mysql에서는 시작값을 0으로 인식 -> 1페이지에서는 0~9임,
	public int getPageStart() {
		return(page-1) * perPageNum;
	}
}
