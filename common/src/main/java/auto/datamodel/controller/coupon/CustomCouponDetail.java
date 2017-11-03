package auto.datamodel.controller.coupon;

import java.math.BigDecimal;
import auto.datamodel.dao.Coupon;
import auto.datamodel.dao.DealerAuth;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小c用户-领取后-代金券详细展示信息
 *
 */
@Data
@NoArgsConstructor
public class CustomCouponDetail {
	
	//来源于 coupon表(使用的优惠券)
	private String couponName;//优惠券名称
	private Long createTime;//优惠券生效时间
	private Long endedTime;//优惠券失效时间
	private String couponUrl;//生成的优惠券二维码地址
	
	//来源于dealer_auth表（经销商认证表）
	private String company;//经销商公司名称
	private String address;//经销商公司地址
	private String telephone;//经销商公司电话
	private BigDecimal longitude;//经销商位置-经度
	private BigDecimal latitude;//经销商位置-纬度 
	
	public CustomCouponDetail(Coupon coupon,DealerAuth dAuth){
		this.couponName=coupon.getCouponName();
		this.createTime=coupon.getCreateTime();
		this.endedTime=coupon.getEndedTime();
		this.couponUrl=coupon.getCouponUrl();
		this.company=dAuth.getCompany();
		this.address=dAuth.getAddress();
		this.telephone=dAuth.getTelephone();
		this.longitude=dAuth.getLongitude();
		this.latitude=dAuth.getLatitude();
	}
}
