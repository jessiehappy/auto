package auto.datamodel.controller.coupon;

import auto.datamodel.dao.Coupon;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小c用户-我的代金券 列表
 */
@Data
@NoArgsConstructor
public class CustomCouponList {
	
	//来源于 coupon表(使用的优惠券)
	private String couponName;//优惠券名称
	private Long createTime;//优惠券生效时间
	private Long endedTime;//优惠券失效时间
	
	public CustomCouponList(Coupon coupon){
		this.couponName=coupon.getCouponName();
		this.createTime=coupon.getCreateTime();
		this.endedTime=coupon.getEndedTime();	
	}
	
}
