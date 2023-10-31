package kr.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.spring.entity.Member;
import kr.spring.repository.MemberRepository;

@SpringBootTest
class SimkoongApplicationTests {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncodmr;
	
	
	
	@Test
	void contextLoads() {
		//회원가입 테스트
		Member m= new Member();
		m.setUserName("admin");
		m.setMemPw("1234");
		m.setNickName("dormir");
		m.setMemSex("M"); //계정 활성화 상태
		m.setMemPhone("010-1234-5678");
		memberRepository.save(m);
	}

}
