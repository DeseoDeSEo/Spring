package kr.spring.entity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class CustomUser extends User {
	
	private Member member;
	
	public CustomUser(Member member) {
		super(member.getUserName(), member.getMemPw(), AuthorityUtils.createAuthorityList("ROLE_"+member.getRole().toString()));
		this.member = member;
	}
}
