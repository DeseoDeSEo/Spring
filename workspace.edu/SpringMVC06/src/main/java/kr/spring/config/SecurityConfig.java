package kr.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.spring.security.MemberUserDetailsService;

@Configuration // WEbConfig.java에서 SecurityConfig를 읽어오기 위한 어노테이션
@EnableWebSecurity // web에서 Sercurity를 쓰겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	//!!!!! 마지막으로 여기와서 아래 두개를 실행함.
	//09/26화요일
	@Bean // 우리가 만들어놓은 MemberUserDetailsService메모리를 올려 사용하겠다.
	public UserDetailsService memberUserDetailsService() {
		
		return new MemberUserDetailsService();
	}
	
	//AuthenticationManagerBuilder 얘가 맞는지 확인함.
	//memberUserDetailsService :security와 mapper사이를 연결해주는 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//내가 만든 MemberUserDetailsService와 암호화 및 복호화를 해주는 패스워드 인코더를 
		//Spring security에 등록하는 메서드.
		auth.userDetailsService(memberUserDetailsService()).passwordEncoder(passwordEncoder());	
	}
	//Spring Security 환경설정하는 클래스
	//websecurity~ : 요청에 대한 보안 설정을 해주는 클래스
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 요청에 대한 보안 설정하는 곳
		// 교재 p.605
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);
		
		//09/26화요일
		// 클라이언트가 요청을 했을 때 권한 설정
		//회원 인증부분
		// 모든 요청이 dispatcher servlet하기 전에 보안 httpsecurity를 한번 거쳐서 들어옴. 
		http
			.authorizeRequests()  /* 요청에 따른 권한을 처리하겠다. */
				.antMatchers("/") // 메인페이지는 "/" 경로로 왔을 때 권한 처리로 할 것이다. 
					.permitAll()  // 누구나 접근가능하게끔 전체 권한.  누가 와도 다 볼 수 있도록.
						.and()   // 권한을 또 추가하겠다.
					.formLogin()  // 로그인에 보안기능 추가하겠다.
						.loginPage("/loginForm.do") //Spring Security에서 제공하는 로그인폼이 아닌 우리가 만든 로그인폼을 사용하겠다.
						.loginProcessingUrl("/Login.do")  //해당 url로 요청이 들어왔을 때, Spring security에 자체 로그인 기능을 수행하겠다.  login.do를 하면 memberuserdetailsservlet으로 감.
					    .permitAll()  //누구나 로그인은 사용해야하기 때문에 권한은 모두 준다.
					    .and()  //권한 추가
			        .logout()   //로그아웃 기능
			        	.invalidateHttpSession(true) // 세션을 만료하겠다. Spring security가 알아서.
			        	.logoutSuccessUrl("/")  //로그아웃시키면 어디로 갈지 -> "/"하면 메인페이지로 이동함.
			        	.and()
			        .exceptionHandling().accessDeniedPage("/access-denied"); //로그인 안 하면 특정 페이지에 접근 불가하도록
						// 로그인도 안 하고 특정페이지에 접근하려고 할 때 특정 url로 요청하겠다.
		
		
	}
	//객체로 등록하겠다 사용할 때 사용함.
	@Bean
	//패스워드 인코딩 기능을 메모리에 올리는 작업
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
