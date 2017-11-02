package auto.datamodel.controller.coupon;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponHomeBrandDetailedResult {
	
	/**
	 * 代金券主页的每个品牌主键
	 */
	private Long id;
	/**
	 * 品牌图片的地址
	 */
	private String imgUrl;
	/**
	 * 品牌名称
	 */
	private String brandName;
}
