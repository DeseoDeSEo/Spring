package kr.spring.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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
	@RequestMapping("/updateForm.do")
	public String updateForm() {
		return "member/updateForm";
	}
	
	@RequestMapping("/update.do")
	public String update(Member m, RedirectAttributes rttr, HttpSession session) {
		
		//문제
		//mapper의 update메서드를 통해 해당 회원의 정보를 수정하시오.
		// 조건1 : 하나라도 입력안한 데이터가 있으면 updateForm.jsp로 다시 돌려보내면서 
		//        updateForm.jsp에서는 "모든 내용을 입력하세요"라는 모달창을 띄우세요.
		// 조건 2: 회원 수정에 실패 했을 때는 joinForm.jsp로 다시 돌려보내면서
		//       joinForm.jsp에서는 "회원 수정이 실패했습니다."라는 모달창을 띄우세요.
		// 조건 3 : 회원수정에 성공했을 떄는 index.jsp로 다시 돌려보내면서
		//         index.jsp에서는 "회원 정보 수정에 성공했습니다."라는 모달창을 띄우세요.
	
		if(m.getMemID() ==null || m.getMemID().equals("")|| m.getMemPassword()==null || m.getMemPassword().equals("")||
				m.getMemName()==null || m.getMemName().equals("")|| m.getMemAge() ==0 || m.getMemEmail() == null ||
				m.getMemEmail().equals("")) {
			rttr.addFlashAttribute("msgType", "실패메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
			return "redirect:/updateForm.do";
		}else {
				int cnt =mapper.update(m);
				
				if(cnt ==1) {

				rttr.addFlashAttribute("msgType", "성공메세지");
				rttr.addFlashAttribute("msg", "회원 정보 수정에 성공했습니다.");
				session.setAttribute("mvo", m);
				return "redirect:/";
				
			}else {

				rttr.addFlashAttribute("msgType", "실패메세지");
				rttr.addFlashAttribute("msg", "회원 정보 수정에 실패했습니다.");
				return "redirect:/joinForm.do";
			}
			}
			
			
		}

	
	
	@RequestMapping("/imageform.do")
	public String imageForm() {
		
		return"member/imageForm";
	}
	@RequestMapping("/imageUpdate.do")
	public String imageUpdate(HttpServletRequest request, HttpSession session, RedirectAttributes rttr) {
		
		//파일 업로드를 할 수 있게 도와주는 객체 필요.(cos.jar안에 있음)
		//파일 업로드를 할 수 있게 도와주는 MultipartRequest 객체를 생성하기 위해서는 
		// 5개의 정보가 필요하다.
		// 요청 데이터, 저장경로, 최대 크기, 인코딩, 파일명 중복제거 
		MultipartRequest multi = null;
		//저장경로
		String savePath = request.getRealPath("resources/upload");
		
		//이미지 최대크기
		int fileMaxSize = 10*1024*1024;
		//1.기본 해당 프로필 이미지 삭제
		// - 내가 현재 로그인 한 사람의 프로필 값을 가져와야함.
		String memID = ((Member)session.getAttribute("mvo")).getMemID();
		//getMember메서드는 memID와 일치하는 회원의 정보(Member)를가져온다.
		String oldImg =mapper.getMember(memID).getMemProfile();
		
		//기존의 프로필 사진 삭제
		//해당 경로안에 파일을 가져오겠다.
		File oldFile = new File(savePath+"/"+oldImg);
		if(oldFile.exists()) {
			oldFile.delete();
		}

		
		//객체 생성
		try {
			multi =new MultipartRequest(request,savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//내가 업로드한 파일 가져오기
		File file = multi.getFile("memProfile");
		//멤버 프로파일로 보냄. 객체 생성 후
		if(file != null) {
			//업로드가 된 상태
			//System.out.println(file.getName());
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
			ext=ext.toUpperCase();
			
			if(!(ext.equals("PNG") ||ext.equals("GIF")||ext.equals("JPG"))) {
				if(file.exists()) {
					file.delete();
					rttr.addFlashAttribute("msgType", "실패메세지");
					rttr.addFlashAttribute("msg", "이미지 파일만 가능합니다.");
					return "redirect:/imageForm.do";
				}
			}
			
			
		}
		
	
		// 업로드한 파일의 이름을 가져오는 코드
		String newProfile = multi.getFilesystemName("memProfile");
		
		Member mvo= new Member();
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		
		int cnt = mapper.profileUpdate(mvo);
		
		//사진 업데이트 후 수정된 회원정보를 다시 가져와서 세션에 담기
		Member m  =mapper.getMember(memID);
		session.setAttribute("mvo", m);
		
		rttr.addFlashAttribute("msgType", "성공메세지");
		rttr.addFlashAttribute("msg", "이미지 변경이 성공했습니다.");
		return "redirect:/";
	}
		
}
