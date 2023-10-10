package kr.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data  
@AllArgsConstructor
@NoArgsConstructor
@ToString
// 오른쪽에 outline에 보임
// @ data 위를 추가하면 getter/setter 다 추가됨.
// @ allargeconstructor 위에 추가하면 생성자 다 추가됨.
// @ noargeconstructor 위에 추가하면 기본생성자 다 추가됨.
public class Board {
	//이게 dto역할을 함. 이름에는 dto 안 들어감.
	
	private int num; // 번호
	private String title; //제목
	private String author; //작가
	private String company; // 출판사
	private String isbn;  //isbn
	private int count; //보유도서수
	
}
