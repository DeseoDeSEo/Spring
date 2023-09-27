package kr.spring.entity;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

//user는 userdetails상속받음.
// memberuser는 user를 상속받음.
// super안에 3가지 정보를 넣고 나머지 정보는 멤버에 담음. 그다음 security config로 
@Data
public class MemberUser extends User{
	//Spring Security에 Member객체를 담을 수 있게 해주는 클래스.
	
	private Member member;
	
	public MemberUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
                                                     //  배열 형태를 받아오겠다. collection= 배열
		// MemberUser 객체 생성시 아이디, 비밀번호, 권한을 입력받는다.
		//실제로 우리는 이 생서자를 사용하지 않는다.
		//추상메서드라서 어쩔 수 없이 구현함.
		super(username, password, authorities);
		//super는 부모에 있는 생성자.(=user안에 있음)
	}
	//실제로 우리가 사용할 생성자
	public MemberUser(Member mvo) {
		//user(부모)클래스의 생성자를 사용해서 구현한다.
		// 생성자(아이디, 비밀번호, 권한을 넣어준다.)
		super(mvo.getMemID(), mvo.getMemPassword(), 
		// USer클래스의 생성자의 사용하는 권한 정보는 Collection<SimpleGrantedAuthority>형태로 넣어줘야한다.
				mvo.getAuthList().stream() //바이트로 변경
				.map(auth-> new SimpleGrantedAuthority(auth.getAuth()))
				//List<Auth> -> collection안에 들어갈 수 있게 변경함.
				.collect(Collectors.toList())
				//최종 collection리스트로 변경.
				);
		this.member = mvo; //09/27 나머지 계정 정보를 넣기위해 만든거임.
		
		
	}
	
}
