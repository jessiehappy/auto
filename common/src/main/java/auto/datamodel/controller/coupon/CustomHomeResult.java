package auto.datamodel.controller.coupon;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小c用户首页显示----代金券相关信息
 *
 */
@Data
@NoArgsConstructor
public class CustomHomeResult {
	//来源于series表
	private String bigImg;//车系大图
	private Integer minPrice;//商品最低价格
	private Integer maxPrice;//商品最高价格
	//来源于dealer_coupon表
	private Integer commission;//优惠券金额
	private Integer verifiedNum;//领取优惠券人数
	
	private String titleName;//商品名称
	private String couponName;//优惠券名称
	private Integer couponStatus;//优惠券状态
	   

	
	/*private Long id;
	*//**
	 * 车系图片
	 *//*
	private String seriesImg;
	*//**
	 * 车系名称   品牌-系列
	 *//*
	private String seriesName;
	*//**
	 * 车系指导价 12.09-123.30
	 *//*
	private String guidePrice;
	*//**
	 * 佣金
	 *//*
	private Integer commission;
	
	*//**
	 * 代金券额度  如果代金券没有生成 则显示“未生成” 否则 显示代金券额度
	 *//*
	private String couponQuota;
	
	public CustomCouponDetailedResult(DealerCoupon dealerCoupon, Series series, Brand brand, Integer couponQuota) {
		// TODO Auto-generated constructor stub

		this.id = dealerCoupon.getId();
		this.seriesImg = series.getSmallImg();
		this.seriesName = fillSeriesName(series,brand);
		this.guidePrice = fillGuidePrice(series);
		this.commission = dealerCoupon.getCommission();
		this.couponQuota = fillCouponQuota(couponQuota);
	}

	private String fillGuidePrice(Series series) {
		return series.getMinPrice() + "-" + series.getMaxPrice();
	}

	private String fillSeriesName(Series series, Brand brand) {
		return brand.getBrandName() + "-" + series.getName();
	}

	private String fillCouponQuota(Integer couponQuota) {
		if (couponQuota != null) {
			return couponQuota.toString();
		}
		return JsonStatus.couponQuota;
	}*/
}
