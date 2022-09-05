package net.koreate.greatescape.member.service;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.koreate.greatescape.member.dao.MemberDAO;
import net.koreate.greatescape.member.vo.MemberVO;
import net.koreate.greatescape.member.vo.SalesVO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.DetailBoardVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;
import net.koreate.greatescape.utils.Criteria;
import net.koreate.greatescape.utils.PageMaker;

import net.koreate.greatescape.utils.Criteria;
import net.koreate.greatescape.utils.PageMaker;
import net.koreate.greatescape.utils.SearchCriteria;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberDAO mdao;
	private final PasswordEncoder bCryptPasswordEncoder;

	@Override
	public MemberVO loginCheck(MemberVO vo) {
		return mdao.loginCheck(vo);
	}

	@Override
	@Transactional
	public void join(MemberVO vo) {
		vo.setMember_pw(bCryptPasswordEncoder.encode(vo.getMember_pw()));
		mdao.join(vo);
		mdao.addMemberAuth(vo);
	}

	@Override
	public int reduplcationCheck(MemberVO vo) {
		int result = mdao.idCheck(vo);
		return result;
	}

	@Override
	public ReservationVO findpeople(MemberVO vo,int product_num) {
		return mdao.findPeople(vo,product_num);
	}

	@Override
	public MemberVO pwCheck(MemberVO vo) {
		return mdao.pwCheck(vo);
	}

	@Override
	public int modify(MemberVO vo) {
		vo.setMember_pw(bCryptPasswordEncoder.encode(vo.getMember_pw()));
		return mdao.modify(vo);
	}

	@Override
	public void changeRev(MemberVO changeMember) {
		mdao.reservChange(changeMember);

	}

	@Override
	public void changeLeave(MemberVO loginMember) {
		mdao.changeLeave(loginMember);

	}

	@Override
	public DetailBoardVO findtripInfo(int product_num) {
		return mdao.findDetailInfo(product_num);
	}

	@Override
	public MemberVO memberInfo(int member_num) {
		return mdao.findUserInfo(member_num);
	}

	@Override
	public List<ProductVO> findProductList(List<ReservationVO> rev) {
		List<ProductVO> list = new ArrayList<>();
		
		for(ReservationVO r : rev) {
			list.add(mdao.findProductList(r.getProduct_num()));
		}
		
		return list;
	}


	@Override
	@Transactional
	public MemberVO findId(MemberVO vo) {
		MemberVO findedMember = mdao.idFinder(vo);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			char c = (char) ( (int) (Math.random() * 58) + 65 );
			sb.append(c);
		}
		String newPw = sb.toString();
		String encodedPw = bCryptPasswordEncoder.encode(newPw);
		findedMember.setMember_pw(encodedPw);
		mdao.modify(findedMember);
		findedMember.setMember_pw(newPw);
		return findedMember;
	}

	@Override
	public ReservationVO findrev(ReservationVO vo) {
		return mdao.nmreservation(vo);
	}

	@Override
	@Transactional
	public void deleteNP(ReservationVO noMember) {
		// 예약테이블 내역 삭제
		mdao.deletereserv(noMember);
		// 잔여좌석+1
		mdao.seatPlus(noMember);
		
	}

	@Override
	public List<MemberVO> memberList(Criteria cri) {
		return mdao.memberList(cri);
	}

	@Override
	public PageMaker pageMaker(Criteria cri) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(mdao.listCount());
		return pageMaker;
	}

	@Override
	public List<MemberVO> typeMemberList(Criteria cri, String member_master) {
		System.out.println(member_master);
		return mdao.booleanMaster(cri,member_master);
	}

	@Override
	public PageMaker typePageMaker(Criteria cri,String member_master) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(mdao.typelistCount(member_master));
		return pageMaker;
	}

	@Override
	@Transactional
	public void createAdmin(MemberVO vo) {
		vo.setMember_pw(bCryptPasswordEncoder.encode(vo.getMember_pw()));
		mdao.createAdmin(vo);
		mdao.addAdminAuth(vo);
	}

	@Override
	public int countContinent(String continent) {
		return mdao.getCountContinent(continent);
	}

	@Override
	public List<SalesVO> totalSales(String continent) {
		return mdao.getTotalSales(continent);
	}

	@Override
	public List<ProductVO> productList(Criteria cri) {
		return mdao.productList(cri);
	}

	@Override
	public PageMaker pPageMaker(Criteria cri) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(mdao.pListCount());
		return pageMaker;
	}

	@Override
	public List<ProductVO> typeProductList(Criteria cri, String product_continent) {
		return mdao.typeProductList(cri,product_continent);
	}

	@Override
	public PageMaker typeProPageMaker(Criteria cri, String product_continent) {
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(mdao.typeProListCount(product_continent));
		return pageMaker;
	}

	@Override
	public List<ReservationVO> checkRev(MemberVO loginMember) {
		return mdao.findRevList(loginMember);
	}

	@Override
	public List<MemberVO> revCheck(String member_id) {
		return mdao.revCheck(member_id);
	}

	@Override
	public ProductVO findProduct(int product_num) {
		return mdao.findProduct(product_num);
	}

	@Override
	public ReservationVO findNum(MemberVO loginMember, int product_num) {
		return mdao.findNum(loginMember,product_num);
	}

	@Override
	public void deleteReserv(ReservationVO rev) {
		mdao.deletereserv(rev);
		
	}

	@Override
	public void seatPlus(ReservationVO rev) {
		mdao.seatPlus(rev);
		
	}

	@Override
	public List<ReservationVO> findRevList(MemberVO loginMember) {
		return mdao.findRevList(loginMember);
	}

	@Override
	public void deleteRevId(MemberVO loginMember) {
		mdao.deleteRevId(loginMember);
		
	}

	@Override
	public List<ReservationVO> allRev() {
		return mdao.allRev();
	}

	@Override
	public ReservationVO revFind(ReservationVO r) {
		return mdao.revFind(r);
	}

	@Override
	public MemberVO findInfo(ReservationVO reserv) {
		return mdao.findInfo(reserv);
	}

	@Override
	public void deleteProduct(int[] product_nums) {
		for(int i=0;i<product_nums.length;i++) {
			int product_num = product_nums[i];
			mdao.deleteProduct(product_num);
		}
		
	}

	@Override
	public List<ProductVO> deadlineSet(int[] product_nums) {
		List<ProductVO> list = new ArrayList<>();
		for(int i=0;i<product_nums.length;i++) {
			int product_num = product_nums[i];
			mdao.deadlineSet(product_num);
			ProductVO vo = mdao.findProduct(product_num);
			list.add(vo);
		}
		return list;
	}

	@Override
	public boolean pwCheck(MemberVO vo, String member_pw) {
		return bCryptPasswordEncoder.matches(member_pw, vo.getMember_pw());
	}


}
