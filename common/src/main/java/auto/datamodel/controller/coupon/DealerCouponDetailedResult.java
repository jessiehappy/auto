package auto.datamodel.controller.coupon;

import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.dao.Brand;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于小b首页展示经销商代金券
 *
 */
@Data
@NoArgsConstructor
public class DealerCouponDetailedResult {
	
	private Long id;
	/**
	 * 车系图片
	 */
	private String seriesImg;
	/**
	 * 车系名称   品牌-系列
	 */
	private String seriesName;
	/**
	 * 车系指导价 12.09-123.30
	 */
	private String guidePrice;
	/**
	 * 佣金
	 */
	private Integer commission;
	
	/**
	 * 代金券额度  如果代金券没有生成 则显示“未生成” 否则 显示代金券额度
	 */
	private String couponQuota;
	
	public DealerCouponDetailedResult(DealerCoupon dealerCoupon, Series series, Brand brand, Integer couponQuota) {
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
	}
}
