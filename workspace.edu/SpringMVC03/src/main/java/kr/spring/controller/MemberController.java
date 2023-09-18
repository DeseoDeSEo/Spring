package kr.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.entity.Member;
import kr.spring.mapper.MemberMapper;

@Controller

public class MemberController {
//	mapper만드는 법 기억해야됨.
	@Autowired
	private MemberMapper mapper;
	

	@RequestMapping("/joinForm.do")
	public String joinForm() {
		return "member/joinForm";
	}
	@RequestMapping("/registerCheck.do")
	public @ResponseBody int registerCheck(@RequestParam("memID") String memID) {
		//registercheck로 memid넘어옴.
		Member m = mapper.registerCheck(memID);
		// 여기서 membermapper.java로 이동함.
		// m == null -> 아이디 중복 사용 가능함.
		// m != null -> 아이디 사용 불가능
		if(m != null || memID.equals("")) {
			return 0;
		}else {
			return 1;
		}
	}
	@RequestMapping("/join.do")
	public String join(Member m, RedirectAttributes rttr, HttpSession session) {
		System.out.println("회원가입 기능요청");
		
		//유효성 검사
		if(m.getMemID() ==null || m.getMemID().equals("")|| m.getMemPassword()==null || m.getMemPassword().equals("")||
				m.getMemName()==null || m.getMemName().equals("")|| m.getMemAge() ==0 || m.getMemEmail() == null ||
				m.getMemEmail().equals("")) {
			//회원 가입을 할 수 없다 하나라도 누락되어 있기 때문에
			
			//실패시 joinForm.do로 msgType과 msg내용을 보내야함.
			rttr.addFlashAttribute("msgType", "실패메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
			return "redirect:/joinForm.do";
			//redirect방식에서는 model에 데이터 저장 불가.
			//forward방식에서만 model에 데이터저장 가능.
			//RedirectAttributes - 리다이렉트 방식으로 이동할 때 보낼 데이터를 저장하는 객체 
		}else {
			//회원 가입 가능

			//null값 대신 넣으려구.
			m.setMemProfile("");
			int cnt = mapper.join(m);
			//회원가입 후 index.jsp로 이동시키시오.
			if(cnt==1) {
				System.out.println("회원가입 성공");
				rttr.addFlashAttribute("msgType", "성공메세지");
				rttr.addFlashAttribute("msg", "회원가입에 성공했습니다.");
				//회원가입 성공 시 로그인 처리까지 시키기
				session.setAttribute("mvo", m);
				//mvo라는 이름으로 m을 넣겠다.
				//성공시 main으로 이동함,
				return "redirect:/";
			}else {
				System.out.println("회원가입 실패.");
				rttr.addFlashAttribute("msgType", "실패메세지");
				rttr.addFlashAttribute("msg", "회원가입에 실패했습니다.");
				return "redirect:/joinForm.do" ;
			}
			
		}
			
		}
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		//session을 사용하고 싶으면 매개변수에 적으면 된다.
		session.invalidate();
		//로그아웃 기능
		return "redirect:/";
	}
	
	@RequestMapping("/loginForm.do")
	public String loginForm() {
		
		return "member/loginForm";
	}
	
	@RequestMapping("/Login.do")
	public String login(RedirectAttributes rttr,  Member m, HttpSession session) {
		//문제
		// mapper에 login이라는 메소드 이름으로 로그인 기능을 수행하시오.
		// 로그인 성공 시 -> index.jsp 이동 후, 로그인에 성공했습니다 modal창 띄우기
		// 로그인 실패 시 -> login.jsp 이동 후, 로그인에 실패했습니다 modal 창 띄우기
		Member mvo =mapper.login(m);
		//mvo는 실패하면 null이 들어감.
		//mvo가 실패하지 않는다면 
		if(mvo != null ) {
			
			rttr.addFlashAttribute("msgType", "성공메세지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다.");
			session.setAttribute("mvo", mvo);
			return "redirect:/";
		}else {
			rttr.addFlashAttribute("msgType", "실패메세지");
			rttr.addFlashAttribute("msg", "로그인에 실패했습니다.");
			return "redirect:/loginForm.do";
		}
		
	}
	
	
		
		
}
