package auto.datamodel.controller.coupon;

import java.math.BigDecimal;

import auto.datamodel.dao.Coupon;
import auto.datamodel.dao.DealerAuth;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小c用户-代金券
 *
 */
@Data
@NoArgsConstructor
public class CustomCoupon {
	
	private Long couponId;
	private String couponName;//优惠券名称
	private Long createTime;//优惠券生效时间
	private String couponUrl;//生成的优惠券二维码地址
	private Integer coupon;//优惠券金额
	private Long endedTime;//优惠券截止时间
	private Integer status;//优惠券使用状态（0-未使用  1-已使用  2-已过期）
	
	private String brandName;//品牌名称
	private String seriesName;//车系名称
	
	private String titleName;//标题名称
	private Integer verifiedNum;//优惠券使用人数
	
	private String company;//经销商公司名称
	private String address;//经销商公司地址
	private String telephone;//经销商公司电话
	private BigDecimal longitude;//经销商位置-经度
	private BigDecimal latitude;//经销商位置-纬度 
	
	public CustomCoupon(Coupon coupon, DealerAuth dAuth, Series series, DealerCoupon dealerCoupon){
		this.couponId=coupon.getId();
		this.couponName=coupon.getCouponName();
		this.createTime=coupon.getCreateTime();
		this.couponUrl=coupon.getCouponUrl();
		this.coupon=coupon.getCoupon();
		this.endedTime=coupon.getEndedTime();
		this.status=coupon.getStatus();
		
		this.brandName=series.getBrandName();
		this.seriesName=series.getName();
		
		this.titleName=dealerCoupon.getTitleName();
		this.verifiedNum=dealerCoupon.getVerifiedNum();
		
		
		this.company=dAuth.getCompany();
		this.address=dAuth.getAddress();
		this.telephone=dAuth.getTelephone();
		this.longitude=dAuth.getLongitude();
		this.latitude=dAuth.getLatitude();
		
	}
}
