package net.koreate.greatescape.board.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.board.service.BoardService;
import net.koreate.greatescape.board.vo.CommentVO;
import net.koreate.greatescape.board.vo.FAQBoardVO;
import net.koreate.greatescape.board.vo.NoticeBoardVO;
import net.koreate.greatescape.board.vo.QNABoardVO;
import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.utils.PageMaker;
import net.koreate.greatescape.utils.SearchCriteria;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

	private final BoardService bs;
	/****************** faq 자주묻는질문******************/
	//글작성(관리자)
	@GetMapping("faqWrite")
	public String faqWrite() throws Exception{
		return "board/faqWrite";
	}
	
	@PostMapping("faqWrite")
	public String writeFAQ( @RequestParam("faq_category")String faq_category, 
			@RequestParam("faq_title")String faq_title, @RequestParam("faq_content")String faq_content) throws Exception {
		FAQBoardVO vo= new FAQBoardVO(faq_category,faq_title,faq_content);
		bs.writeFAQ(vo);
		return "redirect:faq";
	}

	//faq 리스트(아무나 가능)
	@GetMapping("faq")
	public ModelAndView faqList(ModelAndView mav, SearchCriteria cri) throws Exception {
		mav.setViewName("board/faq");
		List<FAQBoardVO> list = bs.faqList(cri);
		mav.addObject("list",list);
		PageMaker pm= bs.getPageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	
	@GetMapping("faq/trip")
	public ModelAndView tripList(ModelAndView mav, SearchCriteria cri) throws Exception {
		mav.setViewName("board/faq");
		List<FAQBoardVO> list = bs.getTripList(cri);
		mav.addObject("list",list);
		PageMaker pm= bs.getTripPageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	
	@GetMapping("faq/reserv")
	public ModelAndView reservList(ModelAndView mav, SearchCriteria cri) throws Exception {
		mav.setViewName("board/faq");
		List<FAQBoardVO> list = bs.getReservList(cri);
		mav.addObject("list",list);
		PageMaker pm= bs.getReservPageMaker(cri);
		System.out.println("pm: "+pm);
		mav.addObject("pm",pm);
		return mav;
	}
	
	@GetMapping("faq/airline")
	public ModelAndView airlineList(ModelAndView mav, SearchCriteria cri) throws Exception {
		mav.setViewName("board/faq");
		List<FAQBoardVO> list = bs.getAirlineList(cri);
		mav.addObject("list",list);
		PageMaker pm= bs.getAirlinePageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	
	@GetMapping("faq/pass")
	public ModelAndView passList(ModelAndView mav, SearchCriteria cri) throws Exception {
		mav.setViewName("board/faq");
		List<FAQBoardVO> list = bs.getPassList(cri);
		mav.addObject("list",list);
		PageMaker pm= bs.getPassPageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	
	@GetMapping("faq/other")
	public ModelAndView otherList(ModelAndView mav, SearchCriteria cri) throws Exception {
		mav.setViewName("board/faq");
		List<FAQBoardVO> list = bs.getOtherList(cri);
		mav.addObject("list",list);
		PageMaker pm= bs.getOtherPageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	
	//faq삭제 (관리자)
	@PostMapping("faqDelete")
	public String faqDelete(@RequestParam("faq_num")int faq_num, HttpServletRequest request) throws Exception {
		bs.deleteFAQ(faq_num);
		return "redirect:"+request.getHeader("Referer");
	}
	
	/******************** notice 공지사항  ***************/
	
	//공지리스트(아무나)
	@GetMapping("notice")
	public ModelAndView noticeList(ModelAndView mav, SearchCriteria cri) throws Exception {		
		List<NoticeBoardVO> noticeList = bs.noticeList(cri);
		PageMaker pm= bs.getNoticePageMaker(cri);
		mav.addObject("noticeList",noticeList);
		mav.addObject("pm",pm);
		mav.setViewName("board/notice");
		return mav;
	}
	//카테고리가 공지사항인 notice
	@GetMapping("notice/inform")
	public ModelAndView informList(ModelAndView mav, SearchCriteria cri) throws Exception {
		List<NoticeBoardVO> informList = bs.informList(cri);
		PageMaker pm= bs.getInformPageMaker(cri);
		mav.addObject("noticeList",informList);
		mav.addObject("pm",pm);
		mav.setViewName("board/notice");
		return mav;
	}
	
	//카테고리가 외교부인 notice
	@GetMapping("notice/mofa")
	public ModelAndView mofaList(ModelAndView mav, SearchCriteria cri) throws Exception {
		List<NoticeBoardVO> mofaList = bs.mofaList(cri);
		PageMaker pm= bs.mofaPageMaker(cri);
		mav.addObject("noticeList",mofaList);
		mav.addObject("pm",pm);
		mav.setViewName("board/notice");
		return mav;
	}
	
	//공지작성(관리자)
	@GetMapping("noticeWrite")
	public String noticeWrite() throws Exception{
		return "board/noticeWrite";
	}
	
	@PostMapping("noticeWrite")
	@Transactional
	public String writeNotice(MultipartFile[] files, @RequestParam("notice_category")String notice_category, 
			@RequestParam("notice_title")String notice_title, @RequestParam("notice_content")String notice_content) throws Exception {
		NoticeBoardVO vo= new NoticeBoardVO(notice_category,notice_title,notice_content);
		bs.writeNotice(vo);
		bs.fileUpload(files);
		System.out.println("controller 에서 vo : "+vo);
		return "redirect:notice";
	}
	
	//공지 세부내용(아무나)
	@GetMapping("noticeDetail")
	public String readNotice(MultipartFile[] files, int notice_num, Model model) throws Exception {
		NoticeBoardVO vo = bs.readNotice(notice_num);
		
		List<String> fileNameList = bs.getFileNameList(notice_num);
		model.addAttribute("notice", vo);
		model.addAttribute("files", fileNameList);
		return "board/noticeDetail";
	}
	
	//공지삭제 (관리자)
	@PostMapping("noticeDelete")
	public String noticeDelete(@RequestParam("notice_num")int notice_num, HttpServletRequest request) throws Exception {
		int noticeNum= Integer.parseInt(request.getParameter("notice_num"));
		bs.deleteNotice(noticeNum);
		return "redirect:notice";
	}

	/********************** qna ************************/
	//문답 리스트(아무나)
	@GetMapping("/qna")
	public ModelAndView qna(ModelAndView mav, SearchCriteria cri) throws Exception {
		List<NoticeBoardVO> qnaNoticeList = bs.qnaNoticeList();
		mav.addObject("qnaNoticeList",qnaNoticeList);
		List<QNABoardVO> qnaList = bs.getQnaList(cri);
		PageMaker pm= bs.getQnaPageMaker(cri);
		mav.addObject("qnaList",qnaList);
		mav.addObject("pm",pm);
		mav.setViewName("board/qna");
		return mav;
	}
	
	//상세보기(회원, 관리자)
	@GetMapping("qnaDetail")
	public String readQNA(int qna_num, Model model) throws Exception {
		QNABoardVO vo = bs.readQNA(qna_num);
		List<CommentVO> comments= bs.getCommentList(qna_num);
		model.addAttribute("qna", vo);
		model.addAttribute("commentList", comments);
		return "board/qnaDetail";
	}
	
	//글쓰기(회원, 관리자)
	@GetMapping("qnaWrite")
	public String qnaWrite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session= request.getSession();
		MemberVO loginMember= (MemberVO)session.getAttribute("userInfo");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(loginMember == null) {
			out.print("<script>");
			out.print("alert('로그인이 필요한 서비스입니다.');");
			out.print("location.href='"+request.getContextPath()+"/member/login'");
			out.print("</script>");
		} 
		return "board/qnaWrite";
	}
	
	@PostMapping("qnaWrite")
	public String writeQNA(@RequestParam("member_num")int member_num, @RequestParam("qna_title")String qna_title, 
			@RequestParam("qna_question")String qna_question, @RequestParam("qna_writer")String qna_writer) throws Exception {
		QNABoardVO vo= new QNABoardVO(qna_title, qna_question, qna_writer);
		vo.setMember_num(member_num);
		bs.writeQNA(vo);
		return "redirect:qna";
	}
	
	//댓글 쓰기(관리자)
	@PostMapping("comment")
	public String addComment(@RequestParam("qna_num")int qna_num, @RequestParam("qna_answer")String qna_answer,
			@RequestParam("comment_writer")String comment_writer) throws Exception{
		CommentVO vo= new CommentVO();
		vo.setQna_num(qna_num);
		vo.setQna_answer(qna_answer);
		vo.setComment_writer(comment_writer);
		bs.addComment(vo);
		bs.updateQNAanswer(vo);
		String redirectURL="redirect:qnaDetail?qna_num="+qna_num;
		return redirectURL;
	}
	//댓글리스트(회원, 관리자)
	@GetMapping("getCommentList")
	@ResponseBody
	public List<CommentVO> getCommentList(@RequestParam("qna_num")int qna_num)throws Exception{
		CommentVO vo= new CommentVO();
		vo.setQna_num(qna_num);
		return bs.getCommentList(qna_num);
	}
	
	//내가 쓴 qna(회원, 관리자)
	@GetMapping("myQNA")
	public ModelAndView myQNA(ModelAndView mav, SearchCriteria cri, HttpServletRequest req) throws Exception{
		HttpSession session= req.getSession();
		MemberVO loginMember= (MemberVO)session.getAttribute("userInfo");
		if (loginMember!=null) {
			List<QNABoardVO> qnaList = bs.myQnaList(cri, loginMember.getMember_num());
			mav.addObject("loginMember", loginMember);
			mav.addObject("qnaList", qnaList);
		} 
		List<NoticeBoardVO> qnaNoticeList = bs.qnaNoticeList();
		mav.addObject("qnaNoticeList",qnaNoticeList);
		mav.setViewName("board/qna");
		PageMaker pm= bs.getQnaPageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	//확인중인 qna(아무나)
	@GetMapping("qna/checking")
	public ModelAndView checkingQNAList(ModelAndView mav, SearchCriteria cri) throws Exception{
		List<NoticeBoardVO> qnaNoticeList = bs.qnaNoticeList();
		mav.addObject("qnaNoticeList",qnaNoticeList);
		List<QNABoardVO> checkingList= bs.getCheckingList(cri);
		mav.addObject("qnaList", checkingList);
		mav.setViewName("board/qna");
		PageMaker pm= bs.getQnaPageMaker(cri);
		mav.addObject("pm",pm);
		return mav;
	}
	
	//답변완료된 qna(아무나)
	@GetMapping("qna/checked")
	public ModelAndView checkedQNAList(ModelAndView mav, SearchCriteria cri) throws Exception{
		List<NoticeBoardVO> qnaNoticeList = bs.qnaNoticeList();
		mav.addObject("qnaNoticeList",qnaNoticeList);
		List<QNABoardVO> checkedList= bs.getCheckedList(cri);
		mav.addObject("qnaList", checkedList);
		PageMaker pm= bs.getQnaPageMaker(cri);
		mav.addObject("pm",pm);
		mav.setViewName("board/qna");
		return mav;
	}
}

