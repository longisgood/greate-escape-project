package net.koreate.greatescape.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import net.koreate.greatescape.product.provider.ProductProvider;
import net.koreate.greatescape.product.vo.FullProductDTO;
import net.koreate.greatescape.product.vo.ProductVO;
import net.koreate.greatescape.reservation.vo.ReservationVO;

public interface ProductDAO {
	
	@Select("SELECT * FROM tbl_product")
	List<ProductVO> getAllList();
	
	@Select("SELECT * FROM tbl_product WHERE product_continent = #{continent}")
	List<ProductVO> getListByContinent(String continent);
	
	@Select("SELECT * FROM tbl_product WHERE product_city = #{city}")
	List<ProductVO> getListByCity(String city);

	@Select("SELECT p.*, d.detail_info, d.detail_schedule, d.detail_title_image, d.detail_viewcnt FROM tbl_product AS p INNER JOIN tbl_detail AS d ON p.product_num = d.product_num WHERE p.product_num = #{id}")
	FullProductDTO getFullProductById(String id);

	@Select("SELECT * FROM tbl_product WHERE product_num = #{id}")
	ProductVO getProductById(String id);

	@SelectProvider(type = ProductProvider.class, method = "getListBySearch")
	List<ProductVO> getListBySearch(Map<String, String> map);

	@Insert("INSERT INTO tbl_product VALUES (NULL, #{product_continent}, #{product_country}, #{product_city}, #{product_name}, #{product_airplane}, #{product_departure}, #{product_arrive}, (Date(#{product_arrive}) - Date(#{product_departure})), #{product_adult}, #{product_minor}, #{product_seat})")
	int createProduct(FullProductDTO vo);
	
	@Insert("INSERT INTO tbl_detail VALUES (NULL, LAST_INSERT_ID(), #{detail_info}, #{detail_schedule}, #{detail_title_image}, 0)")
	int createDetail(FullProductDTO vo);
	
	@Insert("INSERT INTO tbl_reservation VALUES (NULL, #{member_id}, #{rev_name}, #{rev_birth}, #{rev_phone}, #{rev_email}, #{product_num}, #{rev_adult}, #{rev_minor})")
	int reserve(ReservationVO rvo);
	
	@Update("UPDATE tbl_product SET product_seat = product_seat - 1 WHERE product_num = #{product_num}")
	int seatMinus(ReservationVO rvo);

	@Select("SELECT LAST_INSERT_ID()")
	int getLastInsertId();

	@Update("UPDATE tbl_product AS p, tbl_detail AS d SET p.product_continent = #{product_continent}, p.product_country = #{product_country}, p.product_city = #{product_city}, p.product_name = #{product_name}, p.product_airplane = #{product_airplane}, p.product_departure = #{product_departure}, p.product_arrive = #{product_arrive}, p.product_plan = (Date(#{product_arrive}) - Date(#{product_departure})), p.product_adult = #{product_adult}, p.product_minor = #{product_minor}, p.product_seat = #{product_seat}, d.detail_info = #{detail_info}, d.detail_schedule = #{detail_schedule}, d.detail_title_image = #{detail_title_image} WHERE p.product_num = #{product_num} AND d.product_num = #{product_num}")
	int updateProduct(FullProductDTO dto);

	@Update("UPDATE tbl_detail SET detail_viewcnt = detail_viewcnt + 1 WHERE product_num = #{id}")
	int updateViewcntById(String id);
	
	@Select("SELECT * FROM tbl_reservation WHERE member_id = #{member_id} AND product_num = #{product_num}")
	ReservationVO getReservationOfMember(@Param("member_id") String member_id, @Param("product_num") String product_num);

	@Delete("DELETE FROM tbl_product WHERE product_num = #{id}")
	int deleteProduct(int id);

	@Update("UPDATE tbl_product_html SET product_num = #{id} WHERE file_name = #{fileName}")
	int createHtmlList(@Param("id") int id, @Param("fileName") String fileName);

	@Select("SELECT file_name FROM tbl_product_html WHERE product_num = #{id}")
	List<String> getFileNameList(int id);

	@Delete("DELETE FROM tbl_product_html WHERE product_num = #{id}")
	void deleteFileNameList(int id);

	@Insert("INSERT INTO tbl_product_html (file_name) VALUES (#{uploadedName})")
	int addHTMLImage(String uploadedName);
	
	@Select("SELECT file_name FROM tbl_product_html WHERE product_num IS NULL")
	List<String> getTrashList();

	@Delete("DELETE FROM tbl_product_html WHERE product_num IS NULL")
	void deleteTrashList();
}
