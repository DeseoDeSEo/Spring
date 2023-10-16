package kr.spring.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageMaker {
	//실제 페이징처리 클래스임. criteria는 설정 클래스임.
	private Criteria cri; //게시판의 정보(현재 페이지의 정보, 한 페이지당 몇개의 게시물..)를 가지고 있는 객체 criteria가 있어야 페이징 처리가 가능하다.
	private int totalCount; // 총 게시글의 수(를 알아야 몇 페이지가 필요한지 알 수 있다.)
	private int startPage; // 시작페이지 번호(현재 있는 페이지 번호?!)
	private int endPage; //끝페이지 번호
	private boolean prev; //이전 버튼
	private boolean next; // 다음 버튼
	private int displayPageNum = 5; // 하단에 몇개의 페이지를 보여줄 것 인지( 페이지 목록에 들어가는 페이지 개수?!)
	
	//총 게시글의 수를 구하는 메서드
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		makePagein();
	}
	
	public void makePagein() {
		//1. 화면에 보여질 마지막 페이지 번호
		//   현재 하단에 보여줄 페이지 개수는 ? 10개
		//   Q : 현재 13페이지를 보고 있다면 우측 끝에 있는 마지막 페이지는 ? 20
		//   13/10 -> 소수 발생 시 올림 -> 2 *10 = 20 
		//   현재 페이지/ 하단에 보여줄 페이지 개수  -> 소수 발생 시 올림 -> 결과 *10 
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum)* displayPageNum);
		
		// 2. 화면에 보여질 시작 페이지 번호
		// 현재 15페이지라면 11~20. 20(마지막페이지)-10(보여줄 페이지 개수) + 1
		startPage = endPage - displayPageNum +1;
		
		if(startPage <=0) {
			startPage =1; // 혹시라도 startPage가  0보다 작거나 같다면 1부터 시작할 수 있게		
		}
		//3.최종 페이지가 맞는지 유효성 검사
		// ex) 실제로 글이 101개라면 10개 페이지 +1개 페이지 더 만들어야한다.
		//마지막 페이지 계산
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		//4.화면에 보여질 마지막 페이지 유효성 체크
		if(tempEndPage < endPage) {
			endPage = tempEndPage; // 마지막 페이지가 진짜로 구한 페이지 숫자보다 높으면 치환.
		}
		//5.이전, 다음 페이지 버튼 존재여부
		prev = (startPage ==1)?false : true;
		next = (endPage < tempEndPage) ? true: false;
		
		
	}

}
