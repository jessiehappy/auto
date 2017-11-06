package auto.datamodel.controller.coupon;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CouponHomeBrandResult {

	/**
	 * 类目名
	 */
	private String category;
	/**
	 * 类目下的详细品牌
	 */
	private List<CouponHomeBrandDetailedResult> items = 
			new ArrayList<CouponHomeBrandDetailedResult>();
	
	/**
	 * 类目商品主键 从下一层传，用于下一步的操作
	 * 以"1_2_3_4_"的格式展示
	 *//*
	private String categoryBrandId;*/
}
