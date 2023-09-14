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
	
	private int idx; // 번호
	private String title; //제목
	private String content; //내용
	private String writer; //작성자
	private String indate;  //작성일
	private int count; //조회수
	
}
