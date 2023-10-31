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
	private String userName;
	
	@Column(nullable = false) //길이 지정 : 길이지정 따로 안할 때 길이 255임.
	private String memPw;
	
	@Column(nullable = false)
	private String nickName;
	
	
	private String memPhone;
	
	private String memSex;
	
	private String memProfile;
	
	private String memIntroduce;
	
	private long memAge;
	
	private String role;
	
	private String home;
	
	private String interest;
	
	private String mbti;
	
	private String alcohol;
	
	private String food;
	
	private long smoking;
	
	private String sport;
	
	private String job;
	
	private long pfAge;
	
	private String pfHome;
	
	private String pfInterest;
	
	private String pfMbti;
	
	private long pfAlcohol;
	
	private String pfFood;
	
	private long pfSmoking;
	
	private String pfSport;
	
	private String pfJob;
	
	
}
