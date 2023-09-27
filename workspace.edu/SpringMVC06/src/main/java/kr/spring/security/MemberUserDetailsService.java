package kr.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.spring.entity.Member;
import kr.spring.entity.MemberUser;
import kr.spring.mapper.MemberMapper;

public class MemberUserDetailsService implements UserDetailsService{
	// Spring Security에서 Mapper을 사용하기 위한 연결 클래스 ->  Service
	
	@Autowired
	private MemberMapper mapper;
	
	// securityconfig와 db를 연결하는 mapper와 연결시키는 곳임. 여기서 로그인 처리를 함. 로그인 성공하면 username을 반환함. username은 id.
	//하면 mvo에 담아서 세션에 담아야함.
	//세션에 저장하려고 memberuser 클래스로 감.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loaduserbyUsername :user의 name을 통해서 user정보를 가져옴. 여기서 user의 name은 id임.
		//                      => id(user_name)를 기준으로 로그인 정보를 가져오는 메서드
		// 내부적으로 보이지는 않지만 spring security가 해당 아이디의 계정을 가져오고
		//암호화된 비밀번호 비교까지 해서 로그인을 체크하는 메서드
		
		//login.do를 하면 여기로 옴.
		//로그인 처리하기
		// 이미 springsecurity가 알아서 로그인 기능을 다 끝마친 상태
		// -이제 개발자는 진짜로 중간에 비밀번호를 알 수 있는 방법이 없음.
		Member mvo = mapper.getMember(username);
		//Spring security 내부 보안 규정상 우리가 직접 만든 클래스 객체(VO)
		// 그래서 바로 담을 수 없음.
		// 내가 원하는 vo를 담을 수 있게 변환해주는 user Class가 필요함.
		
		//09/27 세션에 저장하면 보안에 취약하다고 생각함,
		// 그래서 서버 자체의 보안 장소인 spring context holder에 저장함.
		if(mvo != null) {
			// 해당 사용자가 有
			return new MemberUser(mvo);
			//Spring Security Context안에 회원의 정보를 저장.
		}else {
			//해당 사용자가 無
			throw new UsernameNotFoundException("user with username" + username + "does not exist.");
		}
		
		
	}
	

}
