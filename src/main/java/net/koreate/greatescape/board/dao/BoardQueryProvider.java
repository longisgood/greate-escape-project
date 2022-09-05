package net.koreate.greatescape.board.dao;

import org.apache.ibatis.jdbc.SQL;

import net.koreate.greatescape.utils.SearchCriteria;

public class BoardQueryProvider {
	
	public String getSearchTripList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='해외여행'");
		getSearchWhere(cri, sql);
		sql.ORDER_BY("faq_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}
	
	public String getSearchAirlineList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='항공'");
		getSearchWhere(cri, sql);
		sql.ORDER_BY("faq_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}

	public String getSearchPassList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='여권/비자/환전'");
		getSearchWhere(cri, sql);
		sql.ORDER_BY("faq_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}

	public String getSearchReservList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='예약/결제'");
		getSearchWhere(cri, sql);
		sql.ORDER_BY("faq_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}

	public String getSearchOtherList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='홈페이지/기타'");
		getSearchWhere(cri, sql);
		sql.ORDER_BY("faq_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}

	//전체
	public String getSearchFAQList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_faq");
		getSearchWhere(cri, sql);
		sql.ORDER_BY("faq_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}

	// 검색조건별 개수
	public String searchSelectCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_faq");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	public String searchTripCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='해외여행'");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}

	public String searchAirlineCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='항공'");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	public String searchPassCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='여권/비자/환전'");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	public String searchReservCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='예약/결제'");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	public String searchOtherCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_faq");
		sql.WHERE("faq_category='홈페이지/기타'");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	// 검색조건
	public void getSearchWhere(SearchCriteria cri, SQL sql) {
		String titleQuery = "faq_title LIKE CONCAT('%',#{keyword},'%')";
		String contentQuery = "faq_content LIKE CONCAT('%',#{keyword},'%')";
		
		if (cri.getKeyword() != null && !cri.getKeyword().trim().equals("")) {
			sql.OR().WHERE(titleQuery);
			sql.OR().WHERE(contentQuery);
		}
	}

	//공지리스트
	public String getNoticeList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_notice");
		sql.ORDER_BY("notice_num DESC");
		sql.LIMIT(cri.getPerPageNum()); // limit: 개수
		sql.OFFSET(cri.getStartRow()); // offset: 검색시작인덱스
		String query = sql.toString();
		return query;
	}
	
	//공지사항 게시글 개수
	public String getNoticeCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_notice");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	//qna게시글 개수
	public String getQnaCount(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM("tbl_qna");
		getSearchWhere(cri, sql);
		String query = sql.toString();
		return query;
	}
	
	//qna 리스트
	//공지리스트
	public String getQnaList(SearchCriteria cri) {
		SQL sql = new SQL();
		sql.SELECT("*");
		sql.FROM("tbl_qna");
		sql.ORDER_BY("qna_num DESC");
		sql.LIMIT(cri.getPerPageNum());
		sql.OFFSET(cri.getStartRow()); 
		String query = sql.toString();
		return query;
	}
	
}
