package kr.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@ToString
@Entity //Board VO가 Database Table로 만들 떄 설정
@Data
public class Member {  
    
	@Id
	@Column(nullable = false)
	private String MemId;
	
	@Column(nullable = false) //길이 지정 : 길이지정 따로 안할 때 길이 255임.
	private String MemPw;
	
	@Column(nullable = false)
	private String NickName;
	
	private String MemPhone;
	
	private String MemSex;
	
	
}
