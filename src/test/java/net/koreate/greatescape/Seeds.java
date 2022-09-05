package net.koreate.greatescape;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.greatescape.common.dao.TempDAO;
import net.koreate.greatescape.product.vo.ProductDetailVO;
import net.koreate.greatescape.product.vo.ProductVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context/root-context.xml" })
public class Seeds {

	@Inject
	TempDAO dao;

	private String[] continent = { "아시아", "유럽", "아메리카", "오세아니아" };

	private String[][] country = { { "일본", "베트남", "필리핀" }, { "이탈리아", "프랑스", "영국" }, { "미국", "캐나다", "칠레" },
			{ "오스트레일리아", "뉴질랜드" } };

	private String[][][] city = { { { "오사카", "도쿄" }, { "다낭", "호치민" }, { "보라카이", "마닐라" } },
			{ { "로마", "베네치아" }, { "파리", "리옹" }, { "런던", "에든버러" } },
			{ { "하와이", "플로리다" }, { "밴쿠버", "퀘백" }, { "산티아고", "비냐 델 마르" } }, { { "시드니", "골드코스트" }, { "남섬", "오클랜드" } }, };

	private String[] name1 = { "재미있는", "환상적인", "낭만적인", "신나는", "최악의", "가기 싫은", "부모님과 가기 좋은", "연인과 가기 좋은", "그저 그런" };

	private String[] name2 = { "관광여행", "문화여행", "독서여행", "온천여행", "도시탐방", "유적탐방", "힐링여행", "체험여행" };

	private String[] airplane = { "대한항공", "아시아나항공", "티웨이항공", "진에어", "에어서울", "에어부산", "제주항공", "이스타항공", "플라이강원",
			"에어로케이항공" };

	@Test
	public void seeds() throws Exception {
		dao.deleteSeed();
		for (int i = 0; i <= 150; i++) {
			ProductVO vo = new ProductVO();
			randomSet(vo);
			dao.productSeed(vo);
			ProductDetailVO productDetailVO = new ProductDetailVO();
			productDetailVO.setDetail_info("인천 국제공항 미팅 관련 안내\r\n" + 
					"​미팅장소 : 인천공항 제1터미널 3층 14번 출입구 여행박사 테이블\r\n" + 
					"미팅시간 : 오후 8시 예정 (공항 비상연락처: 010-8962-1773 / 출발 당일만 통화가능)\r\n" + 
					"\r\n" + 
					"[비엣젯항공] VJ875편 22:45 인천 국제공항 출발 (비행 소요시간 - 약 4시간 40분)\r\n" + 
					"[비엣젯항공] VJ875편 01:25 다낭 국제공항 도착\r\n" + 
					"\r\n" + 
					"가이드 미팅\r\n" + 
					"\r\n" + 
					"다낭 \r\n" + 
					"\r\n" + 
					"다낭은 베트남 중부 최대의 상업 도시입니다. 베트남에서는 호치민, 하노이, 하이퐁 다음으로 큰 4번째 도시이기도 합니다.\r\n" + 
					"다낭이 최근 한국인 여행자들에게 인기를 얻게 된 것은 관광객을 수용할만한 충분한 인프라와 문화유적, 천혜의 자연환경을\r\n" + 
					"갖추었기 때문입니다. 베트남 중부의 교통 중심지이기 때문에 인근 후에와 호이안, 바나산맥까지 한꺼번에 돌아볼 수 있습니\r\n" + 
					"다. 최근 몇 년 사이 가장 빠르게 관광지로 성장한 다낭은 휴양 도시로서의 발돋움을 위해 많은 호텔도 들어서고 있습니다.");
			productDetailVO.setDetail_schedule("호텔 조식 후 가이드 미팅\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"마블 마운틴 (오행산)\r\n" + 
					"다낭과 호이안 중간(다낭에서 9km)에 위치해 있습니다. 5개의 산(투이썬 Thuy Son-물, 목썬 Moc son-나무, 호아썬 Hoa\r\n" + 
					"Son-불, 낌썬 Kim Son-철, 터썬 Tho Son-흙,지구)을 총칭하여 부르는 이름으로, 동양 철학의 세상을 구성하는 다섯 요소에\r\n" + 
					"해당됩니다. 대리석이 많이 나기 때문에 영어로 블루 마운틴이라 불립니다. 해발 108m인 투이썬이 가장 높은 산으로, 엘리\r\n" + 
					"베이터를 이용해 올라가 주변의 경관을 한눈에 내려다 볼 수 있습니다.");
			productDetailVO.setDetail_title_image("/2022/08/31/s_6a974ab8f9ad4158900f80b5b9ab2c83_mike-erskine-S VbdMTsdiA-unsplash (1).jpg");
			dao.detailSeed(productDetailVO);
		}
	}

	private void randomSet(ProductVO vo) {
		int continentNum = randomInt(continent);
		int countryNum = randomInt(country[continentNum]);
		vo.setProduct_continent(continent[continentNum]);
		vo.setProduct_country(country[continentNum][countryNum]);
		vo.setProduct_city(randomValue(city[continentNum][countryNum]));
		vo.setProduct_name(randomValue(name1) + " " + randomValue(name2));
		vo.setProduct_airplane(randomValue(airplane));
		vo.setProduct_departure(new Date());
		vo.setProduct_arrive(new Date());
		vo.setProduct_plan("1");
		vo.setProduct_adult((int) (Math.random() * 10000000) + 100000);
		vo.setProduct_minor((int) (Math.random() * 10000000) + 50000);
		vo.setProduct_seat((int) (Math.random() * 150));
	}

	private String randomValue(String[] array) {
		return array[randomInt(array)];
	}

	private int randomInt(String[] array) {
		return (int) (Math.random() * 1000) % array.length;
	}

}
