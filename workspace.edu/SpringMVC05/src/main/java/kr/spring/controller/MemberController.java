package kr.spring.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.spring.entity.Auth;
import kr.spring.entity.Member;
import kr.spring.mapper.MemberMapper;

@Controller

public class MemberController {
//	mapper만드는 법 기억해야됨.
	@Autowired
	private MemberMapper mapper;
	@Autowired // 내가 만들어 놓은 비밀번호 암호화 객체를 주입받아 사용하겠다. !!!!!!!!!!!!!! 이게 securityConfig.java에서 옴.
	private PasswordEncoder pwEncoder;
	

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
				m.getMemEmail().equals("")||m.getAuthList().size()==0) {
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
			//비밀번호 암호화 !!!!!!!!!!!!!!요기서 
			String encyPw = pwEncoder.encode(m.getMemPassword());
			m.setMemPassword(encyPw);

			
			int cnt = mapper.join(m);
			//회원가입 후 index.jsp로 이동시키시오.
			// 추가 : 권한 테이블에 회원의 권한을 저장하기 (09/25 월)
			//m은 회원가입할 때 정보들임. 
			List<Auth> list = m.getAuthList();
			//권한을 여러개 선택할 수 있으니가 list로 받아옴. ?!
			for(Auth auth : list) {
				//Auth형태로 받아올거임.
				if(auth.getAuth() != null) {
					//가져온 권한 값이 있을 때만 권한테이블에 값 넣기
					Auth saveVO = new Auth();
					saveVO.setMemID(m.getMemID()); // 회원 아이디 넣기
					saveVO.setAuth(auth.getAuth()); //권한 넣기
					//권한 저장
					mapper.authInsert(saveVO);
					
				}
			}
			

			
			if(cnt==1) {
				System.out.println("회원가입 성공");
				rttr.addFlashAttribute("msgType", "성공메세지");
				rttr.addFlashAttribute("msg", "회원가입에 성공했습니다.");
				//회원가입 성공 시 로그인 처리까지 시키기
				
				//09/25 회원가입 성공 시 회원정보 + 권한정보까지 가져오기
				Member mvo = mapper.getMember(m.getMemID());
				
				session.setAttribute("mvo", mvo);
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
		  // 추가 비밀번호 일치여부 체크
		if(mvo != null && pwEncoder.matches(m.getMemPassword(), mvo.getMemPassword())  ) {

				//답을 가져와서 matches가 일치한지 알려줌. true, false로 알려줌.
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
				m.getMemEmail().equals("") || m.getAuthList().size()==0) {
			rttr.addFlashAttribute("msgType", "실패메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
			return "redirect:/updateForm.do";
		}else {
					Member mvo = (Member)session.getAttribute("mvo");
					m.setMemProfile(mvo.getMemProfile());
					//비밀번호 암호화
					String encyPw = pwEncoder.encode(mvo.getMemProfile());
					m.setMemPassword(encyPw);
					
					//권한 삭제
					mapper.authDelete(m.getMemID());
					//권한 입력
					List<Auth> list = m.getAuthList();
					//권한을 여러개 선택할 수 있으니가 list로 받아옴. ?!
					for(Auth auth : list) {
						//Auth형태로 받아올거임.
						if(auth.getAuth() != null) {
							//가져온 권한 값이 있을 때만 권한테이블에 값 넣기
							Auth saveVO = new Auth();
							saveVO.setMemID(m.getMemID()); // 회원 아이디 넣기
							saveVO.setAuth(auth.getAuth()); //권한 넣기
							//권한 저장
							mapper.authInsert(saveVO);
						}
					}
				int cnt =mapper.update(m);
				
				if(cnt ==1) {

				rttr.addFlashAttribute("msgType", "성공메세지");
				rttr.addFlashAttribute("msg", "회원 정보 수정에 성공했습니다.");
				Member info = mapper.getMember(m.getMemID());
				//09월 26일! m에는 부족한 정보가 있어서 info로 새롭게 객체 생성
				session.setAttribute("mvo", info);
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
