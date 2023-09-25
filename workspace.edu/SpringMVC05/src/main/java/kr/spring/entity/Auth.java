package kr.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Auth {
   //권한정보를 저장할 클래스
	private int no; // 일련번호
	private String memID; 
	private String auth; //회원권한(role_user, role_manager, role_admin)
	
}
