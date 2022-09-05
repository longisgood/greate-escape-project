package net.koreate.greatescape.member.controller;

import java.util.Arrays;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.member.service.MemberService;
import net.koreate.greatescape.member.vo.EmbassyVO;
import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.member.vo.SalesVO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.DetailBoardVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;
import net.koreate.greatescape.utils.Criteria;
import net.koreate.greatescape.utils.PageMaker;


@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	private final MemberService ms;
	
	@Autowired
	JavaMailSender mailSender;
	
	// 로그인 페이지 이동
	@GetMapping("/login")
	public String login(HttpServletRequest request,HttpSession session) {
		if(request.getHeader("Referer") != null) {
			session.setAttribute("url",request.getHeader("Referer"));
		}
		return "member/login";
	}
	
	// 회원가입 페이지 이동
	@GetMapping("/join")
	public String join() {
		return "member/join";
	}
	
	/*
	// 로그인 시도
	@PostMapping("/loginPost")
	public String loginPost(MemberVO vo,HttpSession session,HttpServletRequest request,
			HttpServletResponse response,Model model) throws Exception{
		MemberVO login = ms.loginCheck(vo);
		
		if(request.getParameter("remember_id") != null) {
			String remember = request.getParameter("remember_id");
			if(remember.equals("checked")) {
				Cookie cookie = new Cookie("id",vo.getMember_id());
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);
			}
		}else {
			Cookie cookie = new Cookie("id", vo.getMember_id());
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		if(login != null && login.getMember_leave().equals("Y")) {
			session.setAttribute("userInfo",login);
			if(session.getAttribute("url") != null) {
				String url = (String) session.getAttribute("url");
				System.out.println(url);
				
				String prevPage = url.replace("http://localhost:8080/greatescape/", "");
				System.out.println(prevPage);
				return "redirect:/"+prevPage;
			}
		}
		String message = "아이디와 비밀번호가 일치하지 않습니다.";
		model.addAttribute("message",message);
		return "member/login";
	}
	*/
	
	// 로그아웃 시도
	@PostMapping("/logOut")
	public String logOut(HttpSession session){
		if (session.getAttribute("userInfo") != null) {
			session.removeAttribute("userInfo");
			if(session.getAttribute("url") != null) {
				session.removeAttribute("url");
			}
		}
		return "redirect:/";
	}
	@GetMapping("/logOut")
	public String logOut(){
		return "member/logOut";
	}
	
	// 아이디 찾기 페이지이동
	@GetMapping("id_find")
	public String id_find(Model model) {
		String id = "아이디찾기";
		model.addAttribute("id",id);
		return "member/find";
	}
	
	// 비밀번호 찾기 페이지이동
	@GetMapping("pw_find")
	public String pw_find() {
		return "member/find";
	}
	

	// 계정정보 찾기 시도
	@PostMapping("findInfo")
	@ResponseBody
	public MemberVO findId(MemberVO vo) {
		MemberVO findMember = ms.findId(vo);
		
		return findMember;

	}
	

	//인증메일 전송
	@GetMapping("/checkEmail")
	@ResponseBody
	public String sendMail(@RequestParam("member_email") String email)throws Exception {
		System.out.println(email);
		String code = "";
		for(int i=0; i<5;i++) {
			code +=(int)(Math.random()*10);
		}
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");
		helper.setFrom("yukitozx7@gmail.com");
		helper.setTo(email);
		helper.setSubject("인증 메일입니다.");
		helper.setText("인증 코드 : <h3>["+code+"]</h3>",true);
		mailSender.send(message);
		System.out.println("발신 완료");
		return code;
	}

	
	// 회원가입 시도
	@PostMapping("/joinPost")
	public String joinPost(MemberVO vo, Model model) throws Exception {
		
		ms.join(vo);
		String message = "대 탈출에 오신 것을 환영합니다.";
		model.addAttribute("message",message);
		
		return "member/login";
	}
	
	// 회원가입 아이디 중복체크
	@PostMapping("/redu_check")
	@ResponseBody
	public int reduplicationCheck(MemberVO vo,Model model)throws Exception{
		int result = ms.reduplcationCheck(vo);
		return result;
	}
	
	
	// (회원)마이페이지 출력-예약내역
	@GetMapping("/myPage")
	@Transactional
	public String myPage(MemberVO loginMember,HttpServletRequest request,HttpSession session,Model model) throws Exception{
		loginMember = (MemberVO) session.getAttribute("userInfo");
		List<MemberVO> isCheck = ms.revCheck(loginMember.getMember_id());
		if(isCheck != null) {
			
			List<ReservationVO> rev = ms.checkRev(loginMember);
			List<ProductVO> pList = ms.findProductList(rev);
			
			
			model.addAttribute("product", pList);
			
			return "member/info";
		}
		
		return "member/info";
	}
	
	
	// (회원)예약내역 상세보기
	@GetMapping("/reservDetail")
	@Transactional
	public String reservDetail(ProductVO vo,HttpSession session,Model model,EmbassyVO url)throws Exception{
		MemberVO loginMember = (MemberVO) session.getAttribute("userInfo");
		ReservationVO reserv = ms.findNum(loginMember,vo.getProduct_num());
		DetailBoardVO tripInfo = ms.findtripInfo(vo.getProduct_num());
		
		ProductVO product = ms.findProduct(vo.getProduct_num());
		
		String embassy = url.findEmbassy(product.getProduct_country());
		System.out.println(embassy);
		
		model.addAttribute("embassy",embassy);
		model.addAttribute("product",product);
		model.addAttribute("reservation",reserv);
		model.addAttribute("tripInfo",tripInfo);
		
		return "member/product";
	}
	
	// (회원)예약내역 취소
	@GetMapping("/deleteProduct")
	@Transactional
	public String deleteProduct(HttpSession session,Model model,ProductVO vo) throws Exception{
		MemberVO loginMember = (MemberVO) session.getAttribute("userInfo");
		ReservationVO rev = ms.findpeople(loginMember,vo.getProduct_num());
		ms.deleteReserv(rev);
		ms.seatPlus(rev);
		
		String message = "해당 예약이 취소되었습니다.";
		model.addAttribute("message",message);
		
		return "redirect:/member/myPage";
	}
	
	
	// (회원) 정보수정 페이지이동
	@GetMapping("/edit_check")
	public String changeInfo() {
		return "member/edit_check";
	}
	
	// (회원) 정보수정 비밀번호 확인
	@PostMapping("/insertPass")
	public String insertPass(MemberVO vo,Model model,HttpServletRequest request,HttpSession session)throws Exception{
		String member_pw = request.getParameter("member_pw");
		vo = (MemberVO) session.getAttribute("userInfo");
		boolean result = ms.pwCheck(vo, member_pw);
		if (result) {
			return "member/edit";
		}
		/*
		vo.setMember_pw(member_pw);
		MemberVO loginMember = ms.pwCheck(vo);
		if(loginMember != null) {
			return "member/edit";
		}
		*/
		
		String message = "비밀번호를 틀리셨습니다. 다시입력해주세요.";
		model.addAttribute("message",message);
		return "member/edit_check";
	}

	
	// (회원) 정보수정 적용
	@PostMapping("/modify")
	public String modify(MemberVO vo,HttpSession session,RedirectAttributes rttr) throws Exception{
		int result = ms.modify(vo);
		
		if(result != 0) {
			MemberVO changeMember = ms.memberInfo(vo.getMember_num());
			ms.changeRev(changeMember);
			
			session.removeAttribute("userInfo");
			session.setAttribute("userInfo",changeMember);
			
			String message = "성공적으로 정보가 수정되었습니다";
			rttr.addFlashAttribute("flashMessage",message);
			
			return "redirect:/member/myPage";
		}
		
		String message = "정보 수정에 실패하셨습니다. 다시 시도해주세요.";
		rttr.addFlashAttribute("flashMessage",message);
		
		return "redirect:/member/edit_check";
	}
	
	// (회원) 탈퇴 페이지 이동
	@GetMapping("/withdraw")
	public String withdraw(Model model) {
		model.addAttribute("withdraw","탈퇴");
		return "member/edit_check";
	}
	
	// (회원) 탈퇴시 필요한 비밀번호 확인 후 탈퇴 진행
	@PostMapping("/delete")
	@Transactional
	public String delete(RedirectAttributes rttr,String member_pw, HttpSession session, HttpServletRequest request) {
		MemberVO loginMember = (MemberVO) session.getAttribute("userInfo");
		List<ReservationVO> list = ms.findRevList(loginMember);
		System.out.println(list);
		if (list != null && !list.isEmpty()) { 
			rttr.addFlashAttribute("flashMessage", "현재 예약 상품이 존재하여 탈퇴할 수 없습니다. 예약 내역을 먼저 확인해주세요.");
			return "redirect:/member/myPage";
		}
		boolean bool = ms.pwCheck(loginMember, member_pw);
		 // 비밀번호 확인
		 if(bool) {
			 // 회원 가입여부 'N'변경
			 ms.changeLeave(loginMember);
			 return "redirect:/member/logOut";
		 }
		String message = "비밀번호를 틀리셨습니다. 다시입력해주세요.";
		rttr.addFlashAttribute("flashMessage", message);
		return "redirect:/member/edit_check";
	}
	
	
	// 비회원 예약확인 페이지 이동
	@GetMapping("/rev_check")
	public String rev_check() {
		return "nomember/index";
	}

	// 비회원 예약확인시도
	@PostMapping("/norev")
	@Transactional
	public String norev(ReservationVO vo,Model model,HttpSession session,EmbassyVO url) throws Exception{
		ReservationVO noMember = ms.findrev(vo);
		
		if(noMember != null) {
			session.setAttribute("noMember", noMember);
			
			ProductVO noproduct = ms.findProduct(noMember.getProduct_num());
			model.addAttribute("noproduct",noproduct);
			
			String embassy = url.findEmbassy(noproduct.getProduct_country());
			model.addAttribute("embassy",embassy);
			
			DetailBoardVO tripInfo = ms.findtripInfo(noproduct.getProduct_num());
			model.addAttribute("tripInfo",tripInfo);
			
			return "nomember/show";
		}
		
		String message = "해당하는 예약이 없습니다.";
		
		model.addAttribute("message",message);
		
		return "nomember/index";
	}
	
	// 비회원 예약취소
	@GetMapping("/deleteNoProduct")
	public String deleteNoProduct(RedirectAttributes rttr, HttpSession session) throws Exception{
		ReservationVO noMember = (ReservationVO) session.getAttribute("noMember");
		ms.deleteNP(noMember);
		
		String message = "예약이 취소되었습니다.";
		rttr.addFlashAttribute("flashMessage", message);
		session.removeAttribute("noMember");
		
		return "redirect:/";
	}
	

	// 관리자 페이지 이동
	@GetMapping("/adminPage")
	@Transactional
	public String adminPage(@ModelAttribute("cri") Criteria cri,
			Model model,HttpServletRequest request) throws Exception{
		String type = request.getParameter("type");
		System.out.println(type);
		if(type != null) {
			if(type.equals("회원")) {
				String member_master = "N";
				List<ReservationVO> rev = ms.allRev();
				List<MemberVO> list = ms.typeMemberList(cri,member_master);
				PageMaker pm = ms.typePageMaker(cri,member_master);
				
				model.addAttribute("rev",rev);
				model.addAttribute("list",list);
				model.addAttribute("pm",pm);
				return "admin/index";
			}else {
				String member_master = "Y";
				List<MemberVO> list = ms.typeMemberList(cri,member_master);
				PageMaker pm = ms.typePageMaker(cri,member_master);
				
				model.addAttribute("list",list);
				model.addAttribute("pm",pm);
				return "admin/index";
			}
		}
		List<MemberVO> list = ms.memberList(cri);
		
		List<ReservationVO> rev = ms.allRev();
		
		PageMaker pm  = ms.pageMaker(cri);
		
		model.addAttribute("rev",rev);
		model.addAttribute("list",list);
		model.addAttribute("pm",pm);
		return "admin/index";
	}
	
	// 회원정보 상세보기 페이지 이동
	@GetMapping("detailInfo")
	public String detailInfo(MemberVO vo,Model model) throws Exception{
		MemberVO clickMember = ms.memberInfo(vo.getMember_num());
		
		model.addAttribute("clickMember",clickMember);
		
		return "admin/indexShow";
	}
	
	// (관리자)회원 예약내역 보기
	@GetMapping("memberReserv")
	@Transactional
	public String memberReserv(Model model,EmbassyVO url,ReservationVO r) throws Exception{
		ReservationVO reserv = ms.revFind(r);
		MemberVO member = ms.findInfo(reserv);
		ProductVO product = ms.findProduct(reserv.getProduct_num());
		DetailBoardVO tripInfo = ms.findtripInfo(product.getProduct_num());
		
		String embassy = url.findEmbassy(product.getProduct_country());
		System.out.println(embassy);
		
		model.addAttribute("embassy",embassy);
		
		model.addAttribute("member",member);
		model.addAttribute("product",product);
		model.addAttribute("reservation",reserv);
		model.addAttribute("tripInfo",tripInfo.getDetail_info());
		
		
		return "admin/indexProduct";
	}
	
	// 관리자 계정추가 페이지 이동
	@GetMapping("createAdmin")
	public String createAdmin() {
		return "admin/indexMakeAdmin";
	}
	
	// 새 관리자 계정 등록
	@PostMapping("newAdmin")
	public String newAdmin(MemberVO vo) throws Exception{
		ms.createAdmin(vo);
		return "redirect:/member/adminPage";
	}
	

	// 매출페이지 이동
	@GetMapping("sales")
	public String sales(Model model) throws Exception{
		// 대륙별 상품 판매 개수
		String asia = "아시아";
		String america = "아메리카";
		String oseania = "오세아니아";
		String europe = "유럽";

		
		int countAsia = ms.countContinent(asia);
		int countAmerica = ms.countContinent(america);
		int countOseania = ms.countContinent(oseania);
		int countEurope = ms.countContinent(europe);
		
		model.addAttribute("countAsia",countAsia);
		model.addAttribute("countAmerica",countAmerica);
		model.addAttribute("countOseania",countOseania);
		model.addAttribute("countEurope",countEurope);
		
		// 대륙별 상품 판매 총액
		List<SalesVO> asiaSales = ms.totalSales(asia);
		List<SalesVO> americaSales = ms.totalSales(america);
		List<SalesVO> oseaniaSales = ms.totalSales(oseania);
		List<SalesVO> europeSales = ms.totalSales(europe);
		
		int totalAsia = 0;
		int totalAmerica = 0;
		int totalOseania = 0;
		int totalEurope = 0;
		
		for(SalesVO a : asiaSales) {
			totalAsia += a.getProduct_adult()*a.getRev_adult()+a.getProduct_minor()*a.getRev_minor();
		}
		
		for(SalesVO am : americaSales) {
			totalAmerica += am.getProduct_adult()*am.getRev_adult()+am.getProduct_minor()*am.getRev_minor();
		}
		
		for(SalesVO o : oseaniaSales) {
			totalOseania += o.getProduct_adult()*o.getRev_adult()+o.getProduct_minor()*o.getRev_minor();
		}
		
		for(SalesVO e : europeSales) {
			totalEurope += e.getProduct_adult()*e.getRev_adult()+e.getProduct_minor()*e.getRev_minor();
		}
		
		int totalSales = totalAsia+totalAmerica+totalEurope+totalOseania;
		
		model.addAttribute("asiaSales", totalAsia);
		model.addAttribute("americaSales", totalAmerica);
		model.addAttribute("oseaniaSales", totalOseania);
		model.addAttribute("europeSales", totalEurope);
		model.addAttribute("totalSales",totalSales);
		 

		return "admin/money";
	}
	
	
	// 상품관리 페이지이동
	@GetMapping("control")
	public String control(@ModelAttribute("cri") Criteria cri,
			Model model,HttpServletRequest request) throws Exception{
		String continent = request.getParameter("continent");
		System.out.println(continent);
		if(continent != null) {
			if(continent.equals("아시아")) {
				String product_continent = "아시아";
				List<ProductVO> list = ms.typeProductList(cri,product_continent);
				PageMaker pm = ms.typeProPageMaker(cri,product_continent);
				
				model.addAttribute("list",list);
				model.addAttribute("pm",pm);
				return "admin/product";
			}else if(continent.equals("유럽")){
				String product_continent = "유럽";
				List<ProductVO> list = ms.typeProductList(cri,product_continent);
				PageMaker pm = ms.typeProPageMaker(cri,product_continent);
				
				model.addAttribute("list",list);
				model.addAttribute("pm",pm);
				return "admin/product";
			}else if(continent.equals("아메리카")){
				String product_continent = "아메리카";
				List<ProductVO> list = ms.typeProductList(cri,product_continent);
				PageMaker pm = ms.typeProPageMaker(cri,product_continent);
				
				model.addAttribute("list",list);
				model.addAttribute("pm",pm);
				return "admin/product";
			}else{
				String product_continent = "오세아니아";
				List<ProductVO> list = ms.typeProductList(cri,product_continent);
				PageMaker pm = ms.typeProPageMaker(cri,product_continent);
				
				model.addAttribute("list",list);
				model.addAttribute("pm",pm);
				return "admin/product";
			}
			
		}
		List<ProductVO> list = ms.productList(cri);
		
		PageMaker pm  = ms.pPageMaker(cri);
		
		model.addAttribute("list",list);
		model.addAttribute("pm",pm);
		
		return "admin/product";
	}
	
	
	
	//(관리자) 체크된 상품 삭제하기
	@PostMapping("deleteP")
	public String deleteP(HttpServletRequest request)throws Exception{
		String[] nums = request.getParameterValues("product_num");
		 int[] product_nums = Arrays.stream(nums)
                 .mapToInt(Integer::parseInt)
                 .toArray();
		 
		 ms.deleteProduct(product_nums);

		 
		return "redirect:/member/control";
	}
	
	// (관리자) 상품 강제 마감
	@GetMapping("deadline")
	@ResponseBody
	public List<ProductVO> deadline(@RequestParam String list) throws Exception{
		String[] listArr = list.split("/");
		System.out.println(Arrays.toString(listArr));
		int[] product_nums = Arrays.stream(listArr)
                .mapToInt(Integer::parseInt)
                .toArray();
		System.out.println(Arrays.toString(product_nums));
		List<ProductVO> pList = ms.deadlineSet(product_nums);
		
		
		return pList;
	}
	
}	




















