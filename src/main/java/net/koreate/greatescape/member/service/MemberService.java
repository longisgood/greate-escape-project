package net.koreate.greatescape.member.service;

import java.util.List;

import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.member.vo.SalesVO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.DetailBoardVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;
import net.koreate.greatescape.utils.Criteria;
import net.koreate.greatescape.utils.PageMaker;
import net.koreate.greatescape.utils.SearchCriteria;

import net.koreate.greatescape.utils.Criteria;
import net.koreate.greatescape.utils.PageMaker;
import net.koreate.greatescape.utils.SearchCriteria;

public interface MemberService {

	// 로그인
	MemberVO loginCheck(MemberVO vo);

	// 회원가입
	void join(MemberVO vo);

	// ID중복체크
	int reduplcationCheck(MemberVO vo);

	// 예약 인원 찾기
	ReservationVO findpeople(MemberVO vo,int product_num);

	// 비밀번호 확인
	boolean pwCheck(MemberVO vo, String member_pw);

	// 회원정보 수정
	int modify(MemberVO vo);

	// 회원정보 수정에 따른 예약정보 수정
	void changeRev(MemberVO changeMember);

	// 회원탈퇴 진행
	void changeLeave(MemberVO loginMember);

	// 예약페이지의 정보가져오기
	DetailBoardVO findtripInfo(int product_num);

	// 회원번호로 회원정보 찾기
	MemberVO memberInfo(int member_num);

	// 상품번호로 상품들찾기
	List<ProductVO> findProductList(List<ReservationVO> rev);

	// 아이디 찾기
	MemberVO findId(MemberVO vo);

	// 비회원 예약찾기
	ReservationVO findrev(ReservationVO vo);

	// 비회원 예약취소
	void deleteNP(ReservationVO noMember);

	// 회원번호순 정보 불러오기
	List<MemberVO> memberList(Criteria cri);

	// 회원 페이징처리
	PageMaker pageMaker(Criteria cri);

	// 회원의 관리자 여부
	List<MemberVO> typeMemberList(Criteria cri, String member_master);

	// 회원 타입 페이징처리 
	PageMaker typePageMaker(Criteria cri, String member_master);

	// 관리자 계정 생성
	void createAdmin(MemberVO vo);
	
	// 대륙별 상품 판매 개수 검색
	int countContinent(String continent);

	// 대륙별 상품 판매 총액을 위한 정보검색
	List<SalesVO> totalSales(String asia);
	
	// 상품 전체 찾기
	List<ProductVO> productList(Criteria cri);

	// 상품 페이징처리
	PageMaker pPageMaker(Criteria cri);

	// 상품 대륙별 리스트
	List<ProductVO> typeProductList(Criteria cri, String product_continent);

	// 상품 대륙별 리스트 페이징 처리
	PageMaker typeProPageMaker(Criteria cri, String product_continent);

	// 회원id로 예약상품 여부확인
	List<ReservationVO> checkRev(MemberVO loginMember);

	// 회원 예약이 있는지 확인
	List<MemberVO> revCheck(String member_id);

	// 상세보기용 상품찾기
	ProductVO findProduct(int product_num);

	// 해당하는 예약 인원 찾기
	ReservationVO findNum(MemberVO loginMember, int product_num);

	// 예약 삭제
	void deleteReserv(ReservationVO rev);

	// 잔여좌석 추가
	void seatPlus(ReservationVO rev);

	// 회원에 해당하는 예약전부 찾기
	List<ReservationVO> findRevList(MemberVO loginMember);

	// 회원 아이디에 해당하는 모든 예약 삭제
	void deleteRevId(MemberVO loginMember);

	// 모든예약 리스트
	List<ReservationVO> allRev();

	// 예약번호로 해당 예약찾기
	ReservationVO revFind(ReservationVO r);

	// 아이디로 회원정보 찾기
	MemberVO findInfo(ReservationVO reserv);

	// 상품번호들에 해당하는 상품 삭제
	void deleteProduct(int[] product_nums);

	// 선택한 상품들 잔여좌석 마감시키기
	List<ProductVO> deadlineSet(int[] product_nums);

	MemberVO pwCheck(MemberVO vo);

}
