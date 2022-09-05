package net.koreate.greatescape.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import net.koreate.greatescape.board.vo.CommentVO;
import net.koreate.greatescape.board.vo.FAQBoardVO;
import net.koreate.greatescape.board.vo.NoticeBoardVO;
import net.koreate.greatescape.board.vo.QNABoardVO;
import net.koreate.greatescape.utils.SearchCriteria;

public interface BoardDAO {

	/****************************** FAQ ************************************/

	// 게시글개수
	@SelectProvider(type = BoardQueryProvider.class, method = "searchSelectCount")
	int getCount(SearchCriteria cri) throws Exception;

	@Select("SELECT count(*) FROM tbl_faq WHERE faq_category=#{category} limit #{cri.startRow}, #{cri.perPageNum}")
	int getCategoryCount(@Param("cri") SearchCriteria cri, @Param("category") String category) throws Exception;

	// 글 목록-동적쿼리
	@SelectProvider(type = BoardQueryProvider.class, method = "getSearchFAQList")
	List<FAQBoardVO> getFAQList(SearchCriteria cri) throws Exception;

	@SelectProvider(type = BoardQueryProvider.class, method = "getSearchOtherList")
	List<FAQBoardVO> getOtherList(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "getSearchReservList")
	List<FAQBoardVO> getReservList(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "getSearchTripList")
	List<FAQBoardVO> getTripList(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "getSearchAirlineList")
	List<FAQBoardVO> getAirlineList(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "getSearchPassList")
	List<FAQBoardVO> getPassList(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "searchOtherCount")
	int getOtherCount(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "searchReservCount")
	int getReservCount(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "searchPassCount")
	int getPassCount(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "searchAirlineCount")
	int getAirlineCount(SearchCriteria cri);

	@SelectProvider(type = BoardQueryProvider.class, method = "searchTripCount")
	int getTripCount(SearchCriteria cri);

	// 글 쓰기
	@Insert("INSERT INTO tbl_faq VALUES(null, #{faq_category}, #{faq_title}, #{faq_content})")
	void writeFAQ(FAQBoardVO fvo) throws Exception;

	// 글 수정
	@Update("UPDATE tbl_faq SET faq_category=#{faq_category}, faq_title= #{faq_title}, faq_content= #{faq_content} "
			+ "WHERE faq_num= #{faq_num}")
	void modifyFAQ(FAQBoardVO fvo);

	// 글 삭제
	@Delete("DELETE FROM tbl_faq WHERE faq_num=#{faq_num}")
	void deleteFAQ(int faq_num) throws Exception;

	/****************************** QNA ************************************/
	// 맨위에 띄워줄 공지목록
	@Select("SELECT * FROM tbl_notice ORDER BY notice_num DESC limit 0,3")
	List<NoticeBoardVO> getQnaNoticeList();

	// 글 목록
	@Select("SELECT * FROM tbl_qna ORDER BY qna_num DESC")
	List<QNABoardVO> getQNAList(SearchCriteria cri) throws Exception;

	// 게시글 개수
	@SelectProvider(type = BoardQueryProvider.class, method = "getQnaCount")
	int getQnaCount(SearchCriteria cri) throws Exception;

	// 글 쓰기
	@Insert("INSERT INTO tbl_qna(member_num, qna_title, qna_question, qna_writer) VALUES (#{member_num}, #{qna_title}, #{qna_question}, #{qna_writer})")
	void writeQNA(QNABoardVO qvo);

	// 상세보기
	@Select("SELECT * FROM tbl_qna WHERE qna_num = #{qna_num}")
	QNABoardVO readQNA(int qna_num);

	// 댓글
	@Insert("INSERT INTO tbl_comment VALUES (null, #{qna_num},#{qna_answer}, #{comment_writer})")
	void addComment(CommentVO vo);

	// 댓글 리스트
	@Select("SELECT * FROM tbl_comment WHERE qna_num=#{qna_num}")
	List<CommentVO> getCommentList(int qna_num);

	@Update("UPDATE tbl_qna SET qna_answer = #{qna_answer} WHERE qna_num=#{qna_num}")
	void updateQNAanswer(CommentVO vo);

	// 내가쓴 qna리스트
	@Select("SELECT qna.* FROM tbl_qna AS qna NATURAL JOIN tbl_member AS mem WHERE mem.member_num=#{member_num} ORDER BY qna_num DESC limit #{cri.startRow}, #{cri.perPageNum}")
	List<QNABoardVO> myQNAList(@Param("cri") SearchCriteria cri, @Param("member_num") int member_num);

	// 확인중
	@Select("SELECT * FROM tbl_qna WHERE qna_answer IS NULL ORDER BY qna_num DESC limit #{cri.startRow}, #{cri.perPageNum}")
	List<QNABoardVO> getCheckingList(@Param("cri") SearchCriteria cri);

	// 답변끝
	@Select("SELECT * FROM tbl_qna WHERE qna_answer IS NOT NULL ORDER BY qna_num DESC  limit #{cri.startRow}, #{cri.perPageNum}")
	List<QNABoardVO> getChekedList(@Param("cri") SearchCriteria cri);

	/***************************** Notice **********************************/
	@SelectProvider(type = BoardQueryProvider.class, method = "getNoticeCount")
	int getNoticeCount(SearchCriteria cri) throws Exception;

	// 글 목록
	@SelectProvider(type = BoardQueryProvider.class, method = "getNoticeList")
	List<NoticeBoardVO> getNoticeList(SearchCriteria cri);

	// 새글 쓰기
	@Insert("INSERT INTO tbl_notice(notice_category, notice_title, notice_content) VALUES (#{notice_category}, #{notice_title}, #{notice_content})")
	void noticeWrite(NoticeBoardVO nvo);

	// 상세보기
	@Select("SELECT * FROM tbl_notice WHERE notice_num = #{notice_num}")
	NoticeBoardVO readNotice(int notice_num);

	@Select("SELECT * FROM tbl_notice WHERE notice_category = '공지사항'")
	List<NoticeBoardVO> getInformList(SearchCriteria cri);

	@Select("SELECT count(*) FROM tbl_notice WHERE notice_category = '공지사항'")
	int getInformCount(SearchCriteria cri);

	@Select("SELECT * FROM tbl_notice WHERE notice_category = '외교부소식'")
	List<NoticeBoardVO> getMofaList(SearchCriteria cri);

	@Select("SELECT count(*) FROM tbl_notice WHERE notice_category = '외교부소식'")
	int getMofaCount(SearchCriteria cri);

	@Delete("DELETE FROM tbl_notice WHERE notice_num=#{notice_num}")
	void deleteNotice(int notice_num);
  
	@Insert("INSERT INTO tbl_notice_file VALUES (LAST_INSERT_ID(), #{uploadFileName})")
	int addFile(String uploadFileName);

	@Select("SELECT notice_fileName FROM tbl_notice_file WHERE notice_num = #{notice_num}")
	List<String> getFileNameList(int notice_num);

}
