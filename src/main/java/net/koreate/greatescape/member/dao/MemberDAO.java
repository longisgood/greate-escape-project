package net.koreate.greatescape.member.dao;


import java.util.List;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.member.vo.SalesVO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.DetailBoardVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;

import net.koreate.greatescape.utils.Criteria;


public interface MemberDAO {
	
	//로그인
	@Select("SELECT * FROM tbl_member WHERE member_id = #{member_id} AND member_pw = #{member_pw}")
	MemberVO loginCheck(MemberVO vo);

	//회원가입
	@Insert("INSERT INTO tbl_member(member_id,member_pw,member_name,member_gender,member_birth,member_phone,member_address,"
			+ "member_address_detail,member_email) VALUES(#{member_id},#{member_pw},#{member_name},#{member_gender},#{member_birth},"
			+ "#{member_phone},#{member_address},#{member_address_detail},#{member_email})")
	void join(MemberVO vo);

	
	// ID중복체크
	@Select("SELECT count(*) FROM tbl_member WHERE member_id = #{member_id}")
	int idCheck(MemberVO vo);

	// 이용자 정보가져오기
	@Select("SELECT * FROM tbl_member WHERE member_num = #{member_num}")
	MemberVO findUserInfo(int member_num);

	// 상품 찾기
	@Select("SELECT * FROM tbl_product WHERE product_num =  #{product_num}")
	ProductVO findProductList(int product_num);
	
	// 예약 인원 검색 
	@Select("SELECT * FROM tbl_reservation WHERE member_id = #{vo.member_id} and product_num = #{product_num}")
	ReservationVO findPeople(@Param("vo")MemberVO vo,@Param("product_num")int product_num);

	//비밀번호 일치여부확인
	@Select("SELECT * FROM tbl_member WHERE member_num = #{member_num} AND member_pw = #{member_pw}")
	MemberVO pwCheck(MemberVO vo);

	// 회원정보수정
	@Update("UPDATE tbl_member SET member_pw = #{member_pw} , member_phone = #{member_phone} , member_birth = #{member_birth} ,"
			+ " member_address = #{member_address}, "
			+ "member_address_detail = #{member_address_detail} , member_email = #{member_email} , member_updatedate = "
			+ "now() , member_gender = #{member_gender} WHERE member_num = #{member_num}")
	int modify(MemberVO vo);

	//예약 테이블 수정
	@Update("UPDATE tbl_reservation SET rev_birth = #{member_birth} , rev_phone = #{member_phone} , rev_email = #{member_email} WHERE member_id = #{member_id}")
	void reservChange(MemberVO changeMember);

	//회원탈퇴 여부 N변경
	@Update("UPDATE tbl_member SET member_leave = 'N' WHERE member_num = #{member_num}")
	void changeLeave(MemberVO loginMember);

	//예약 일정정보 가져오기
	@Select("SELECT * FROM tbl_detail WHERE product_num = #{product_num}")
	DetailBoardVO findDetailInfo(int product_num);

	//회원정보에서 예약 캔슬
	@Update("UPDATE tbl_member SET rev_num = 0 WHERE member_num = #{member_num}")
	void cancelProduct(MemberVO loginMember);
	
	// 예약테이블에서 예약삭제
	@Delete("DELETE FROM tbl_reservation WHERE member_id = #{member_id} and product_num = #{product_num}")
	void deletereserv(ReservationVO vo);
	
	// 상품번호에 해당하는 상품 잔여좌석 +1
	@Update("UPDATE tbl_product SET product_seat = product_seat + 1 WHERE product_num = #{product_num}")
	void seatPlus(ReservationVO vo);

	// 이메일로 아이디 찾기
	@Select("SELECT * FROM tbl_member WHERE member_email = #{member_email}")
	MemberVO idFinder(MemberVO vo);

	// 이름/이메일/전화번호로 예약찾기
	@Select("SELECT * FROM tbl_reservation WHERE rev_name = #{rev_name} AND rev_phone = #{rev_phone} AND rev_email = #{rev_email}")
	ReservationVO nmreservation(ReservationVO vo);

	// 정렬된 전체회원 목록 찾기
	@Select("SELECT * FROM tbl_member ORDER BY member_num DESC limit #{startRow} , #{perPageNum}" )
	List<MemberVO> memberList(Criteria cri);

	// 페이징 처리용 전체 회원수 찾기
	@Select("SELECT count(*) FROM tbl_member")
	int listCount();

	// 관리자 여부에 따른 멤버찾기
	@Select("SELECT * FROM tbl_member WHERE member_master = #{member_master} ORDER BY member_num DESC limit #{cri.startRow} , #{cri.perPageNum}")
	List<MemberVO> booleanMaster(@Param("cri")Criteria cri,@Param("member_master") String member_master);

	// 관리자 여부에 따른 회원수 찾기
	@Select("SELECT count(*) FROM tbl_member WHERE member_master = #{member_master}")
	int typelistCount(String member_master);
	
	// 관리자 계정 생성
	@Insert("INSERT INTO tbl_member(member_id,member_pw,member_name,member_gender,member_birth,member_phone,member_email,member_master) "
			+ "VALUES(#{member_id},#{member_pw},#{member_name},#{member_gender},#{member_birth},#{member_phone},#{member_email},'Y')")
	void createAdmin(MemberVO vo);

	// 대륙조건으로 상품 팔린 개수 검색
	@Select("SELECT count(*) FROM tbl_product NATURAL JOIN tbl_reservation WHERE product_continent = #{continent}")
	int getCountContinent(String continent);

	// 대륙 조건으로 팔린 상품 금액계산용 정보 검색
	@Select("SELECT product_adult,product_minor,rev_adult,rev_minor FROM tbl_product NATURAL JOIN tbl_reservation WHERE product_continent = #{continent}")
	List<SalesVO> getTotalSales(String continent);

	// 상품 번호 순으로 정렬된 상품전체 리스트
	@Select("SELECT * FROM tbl_product ORDER BY product_num DESC limit #{startRow} , #{perPageNum}")
	List<ProductVO> productList(Criteria cri);

	// 페이징처리용 전체상품수
	@Select("SELECT count(*) FROM tbl_product")
	int pListCount();

	// 대륙 조건으로 상품 리스트 출력
	@Select("SELECT * FROM tbl_product WHERE product_continent = #{product_continent} ORDER BY product_num DESC limit #{cri.startRow} , #{cri.perPageNum}")
	List<ProductVO> typeProductList(@Param("cri")Criteria cri, @Param("product_continent")String product_continent);

	// 대륙 조건으로 상품 리스트 수
	@Select("SELECT count(*) FROM tbl_product WHERE product_continent = #{product_continent}")
	int typeProListCount(String product_continent);

	// 회원정보로 예약테이블 정보가져오기
	@Select("SELECT * FROM tbl_reservation WHERE member_id = #{member_id}")
	List<ReservationVO> findRevList(MemberVO loginMember);

	// 회원 id에 해당하는 예약이 있는지 확인
	@Select("select * from tbl_reservation where member_id = #{member_id}")
	List<MemberVO> revCheck(String member_id);

	// 상품 하나만 찾기용
	@Select("SELECT * FROM tbl_product WHERE product_num =  #{product_num}")
	ProductVO findProduct(int product_num);

	// 예약 하나만 찾기용
	@Select("SELECT * FROM tbl_reservation WHERE member_id = #{member_id}")
	ReservationVO findRev(MemberVO loginMember);

	//회원 아이디와 상품번호로 정확한 상품 찾기
	@Select("select * from tbl_reservation where member_id = #{loginMember.member_id} and product_num = #{product_num}")
	ReservationVO findNum(@Param("loginMember")MemberVO loginMember,@Param("product_num") int product_num);

	// 회원아이디에 해당하는 예약 삭제
	@Delete("DELETE FROM tbl_reservation WHERE member_id = #{member_id}")
	void deleteRevId(MemberVO loginMember);

	// 예약테이블 모든 정보
	@Select("select * from tbl_reservation")
	List<ReservationVO> allRev();

	// 예약번호로 예약찾기
	@Select("select * from tbl_reservation where rev_num = #{rev_num}")
	ReservationVO revFind(ReservationVO r);

	// 아이디로 회원정보 가져오기
	@Select("select * from tbl_member where member_id = #{member_id}")
	MemberVO findInfo(ReservationVO reserv);

	// 상품번호 받아서 해당 상품 삭제하기
	@Delete("DELETE FROM tbl_product WHERE product_num = #{product_num}")
	void deleteProduct(int product_num);
	
	// 상품 번호 받아서 해당 상품 seat를 0으로 만들어 예약마감 상태 만들기
	@Update("update tbl_product set product_seat = 0 where product_num = #{product_num}")
	void deadlineSet(int product_num);

	@Insert("INSERT INTO tbl_auth VALUES (#{member_id}, 'ROLE_MEMBER')")
	void addMemberAuth(MemberVO vo);

	@Insert("INSERT INTO tbl_auth VALUES (#{member_id}, 'ROLE_ADMIN')")
	void addAdminAuth(MemberVO vo);


	
}






