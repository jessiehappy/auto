package auto.datamodel.controller.coupon;

import auto.datamodel.dao.Brand;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateCouponResult {
	
	/**
	 * 优惠券名称 品牌 + 车系 + 汽车商品名称
	 */
	private String name;
	/**
	 * 代金券优惠截止时间  2017-10-31
	 */
	private Long offerDeadline;
	/**
	 * 佣金  设定的代金券额度不能超过佣金
	 */
	private Integer commission;
	
	public GenerateCouponResult(DealerCoupon dealerCoupon, Series series,
			Brand brand) {
		this.name = fillName(series, brand, dealerCoupon);
		this.offerDeadline = dealerCoupon.getFinishedTime().getTime();
		this.commission = dealerCoupon.getCommission();
	}

	private String fillName(Series series, Brand brand, DealerCoupon dealerCoupon) {
		return brand.getBrandName() + series.getName() + dealerCoupon.getTitleName();
	}
}
