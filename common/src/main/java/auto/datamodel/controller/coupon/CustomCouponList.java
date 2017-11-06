package auto.datamodel.controller.coupon;

import java.util.Date;

import auto.datamodel.dao.Coupon;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小c用户-我的代金券 列表
 */
@Data
@NoArgsConstructor
public class CustomCouponList {
	private Long couponId;
	private String brandName;//品牌名称
	private String seriesName;//车系名称
	private String titleName;//标题名称
	private Date endedTime;//优惠券截止时间
	private Integer status;//优惠券使用状态（0-未使用  1-已使用  2-已过期）
	
	public CustomCouponList(Coupon coupon, Series series, DealerCoupon dealerCoupon){
		this.couponId=coupon.getId();
		this.brandName=series.getBrandName();
		this.seriesName=series.getName();
		this.titleName=dealerCoupon.getTitleName();
		this.endedTime=coupon.getEndedTime();
		this.status=coupon.getStatus();
	}
	
	public CustomCouponList(CustomCoupon customCoupon){
		this.couponId=customCoupon.getCouponId();
		this.brandName=customCoupon.getBrandName();
		this.seriesName=customCoupon.getSeriesName();
		this.titleName=customCoupon.getTitleName();
		this.endedTime=customCoupon.getEndedTime();
		this.status=customCoupon.getStatus();
	}
	
}
