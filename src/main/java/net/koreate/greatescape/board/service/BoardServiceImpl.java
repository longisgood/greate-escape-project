package net.koreate.greatescape.board.service;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.board.dao.BoardDAO;
import net.koreate.greatescape.board.vo.CommentVO;
import net.koreate.greatescape.board.vo.FAQBoardVO;
import net.koreate.greatescape.board.vo.NoticeBoardVO;
import net.koreate.greatescape.board.vo.QNABoardVO;
import net.koreate.greatescape.product.dao.ProductDAO;
import net.koreate.greatescape.utils.FileUtils;
import net.koreate.greatescape.utils.PageMaker;
import net.koreate.greatescape.utils.SearchCriteria;
import net.koreate.greatescape.utils.SearchPageMaker;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardDAO bdao;
	private final String uploadFolder;
	private final ServletContext context;
	private String realPath;
	

	@PostConstruct
	public void initPath() {
		realPath = context.getRealPath(File.separator + uploadFolder);
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/****************************** FAQ ************************************/
	@Override
	public PageMaker getPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<FAQBoardVO> faqList(SearchCriteria cri) throws Exception {
		List<FAQBoardVO> list = null;
		list = bdao.getFAQList(cri);
		return list;
	}

	@Override
	public void writeFAQ(FAQBoardVO fvo) throws Exception {
		bdao.writeFAQ(fvo);
	}

	@Override
	public void deleteFAQ(int faq_num) throws Exception {
		bdao.deleteFAQ(faq_num);
	}

	@Override
	public List<FAQBoardVO> getTripList(SearchCriteria cri) throws Exception {
		return bdao.getTripList(cri);
	}

	@Override
	public PageMaker getTripPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getTripCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<FAQBoardVO> getAirlineList(SearchCriteria cri) throws Exception {
		return bdao.getAirlineList(cri);
	}

	@Override
	public PageMaker getAirlinePageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getAirlineCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<FAQBoardVO> getPassList(SearchCriteria cri) throws Exception {
		return bdao.getPassList(cri);
	}

	@Override
	public PageMaker getPassPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getPassCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<FAQBoardVO> getReservList(SearchCriteria cri) throws Exception {
		return bdao.getReservList(cri);
	}

	@Override
	public PageMaker getReservPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getReservCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<FAQBoardVO> getOtherList(SearchCriteria cri) throws Exception {
		return bdao.getOtherList(cri);
	}

	@Override
	public PageMaker getOtherPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getOtherCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	/****************************** QNA ************************************/

	// qna게시글
	@Override
	public List<QNABoardVO> getQnaList(SearchCriteria cri) throws Exception {
		List<QNABoardVO> list = null;
		list = bdao.getQNAList(cri);
		return list;
	}

	// 상단에 띄워줄 공지사항
	@Override
	public List<NoticeBoardVO> qnaNoticeList() {
		return bdao.getQnaNoticeList();
	}

	// qna 페이지메이커
	@Override
	public PageMaker getQnaPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getQnaCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		cri.setPerPageNum(7);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	// 게시글 작성
	@Override
	public void writeQNA(QNABoardVO qvo) throws Exception {
		bdao.writeQNA(qvo);
	}

	// 상세보기
	@Override
	public QNABoardVO readQNA(int qna_num) throws Exception {
		QNABoardVO vo = bdao.readQNA(qna_num);
		return vo;
	}

	// 댓글작성
	@Override
	public void addComment(CommentVO vo) throws Exception {
		bdao.addComment(vo);
	}

	// 댓글리스트
	public List<CommentVO> getCommentList(int qna_num) throws Exception {
		return bdao.getCommentList(qna_num);
	}

	// 댓글 달리면 업데이트
	@Override
	public void updateQNAanswer(CommentVO vo) throws Exception {
		bdao.updateQNAanswer(vo);
	}

	// 내가쓴글
	@Override
	public List<QNABoardVO> myQnaList(SearchCriteria cri, int member_num) {
		return bdao.myQNAList(cri, member_num);
	}

	@Override
	public List<QNABoardVO> getCheckingList(SearchCriteria cri) throws Exception {
		return bdao.getCheckingList(cri);
	}

	@Override
	public List<QNABoardVO> getCheckedList(SearchCriteria cri) throws Exception {
		return bdao.getChekedList(cri);
	}

	/***************************** Notice **********************************/
	@Override
	public List<NoticeBoardVO> noticeList(SearchCriteria cri) throws Exception {
		List<NoticeBoardVO> list = bdao.getNoticeList(cri);
		return list;
	}

	@Override
	public void writeNotice(NoticeBoardVO nvo) throws Exception {
		bdao.noticeWrite(nvo);
	}

	@Override
	public NoticeBoardVO readNotice(int notice_num) throws Exception {
		NoticeBoardVO vo = bdao.readNotice(notice_num);
		return vo;
	}

	@Override
	public PageMaker getNoticePageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getNoticeCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<NoticeBoardVO> informList(SearchCriteria cri) throws Exception {
		return bdao.getInformList(cri);
	}

	@Override
	public PageMaker getInformPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getInformCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public List<NoticeBoardVO> mofaList(SearchCriteria cri) throws Exception {
		return bdao.getMofaList(cri);
	}

	@Override
	public PageMaker mofaPageMaker(SearchCriteria cri) throws Exception {
		int totalCount = bdao.getMofaCount(cri);
		PageMaker pm = new SearchPageMaker();
		pm.setDisplayPageNum(5);
		pm.setCri(cri);
		pm.setTotalCount(totalCount);
		return pm;
	}

	@Override
	public void deleteNotice(int notice_num) {
		bdao.deleteNotice(notice_num);
	}
  
	public boolean fileUpload(MultipartFile[] files) throws Exception {
		int result = 0;
		for (MultipartFile file : files) {
			String uploadFileName = FileUtils.uploadFile(realPath, file);
			result += bdao.addFile(uploadFileName);
		}
		return result > 0 ? true : false;
	}

	@Override
	public List<String> getFileNameList(int notice_num) throws Exception {
		return bdao.getFileNameList(notice_num);
	}

	

	

	
	

}
