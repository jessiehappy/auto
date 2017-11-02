package auto.datamodel.controller.coupon;

import java.util.Date;

import auto.datamodel.dao.Brand;
import auto.datamodel.dao.DealerAuth;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import auto.util.CommonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OneDealerCouponResult {

	/**
	 * 授权码
	 */
	private String code;
	/**
	 * 车系图片
	 */
	private String img;
	/**
	 * 商品名称 品牌 +系列
	 */
	private String name;
	/**
	 * 指导价
	 */
	private String guidePrice;
	/**
	 * 佣金
	 */
	private Integer commission;
	/**
	 * 代金券额度 不为null时   显示代金券额度 
	 */
	private Integer couponQuota;
	/**
	 * 代金券数量
	 */
	private Integer couponNum;
	/**
	 * 代金券优惠截止时间  2017-10-31
	 */
	private String offerDeadline;
	/**
	 * 经销商名称
	 */
	private String dealerCompany;
	/**
	 * 经销商公司的详细地址
	 */
	private String address;
	
	public OneDealerCouponResult(DealerCoupon dealerCoupon, Series series,
			Brand brand, Integer couponQuota, DealerAuth dealerAuth) {
		this.img = series.getBigImg();
		this.name = fillName(series, brand);
		this.guidePrice = fillGuidePrice(series);
		this.commission = dealerCoupon.getCommission();
		this.couponQuota = couponQuota;
		this.couponNum = dealerCoupon.getCouponNum();
		this.offerDeadline = CommonUtils.getShortTimeFormat(new Date(dealerCoupon.getFinishedTime()));
		this.dealerCompany = dealerAuth.getCompany();
		this.address = dealerAuth.getAddress();
		
	}

	private String fillGuidePrice(Series series) {
		return series.getMinPrice() + "-" + series.getMaxPrice();
	}

	private String fillName(Series series, Brand brand) {
		return brand.getBrandName() + series.getName();
	}
}
