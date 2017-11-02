package auto.datamodel.controller.coupon;

import java.util.Date;

import auto.datamodel.dao.Brand;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import auto.util.CommonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerateCouponResult {
	
	/**
	 * 优惠券名称 品牌 + 车系
	 */
	private String name;
	/**
	 * 代金券优惠截止时间  2017-10-31
	 */
	private String offerDeadline;
	/**
	 * 佣金  设定的代金券额度不能超过佣金
	 */
	private Integer commission;
	
	public GenerateCouponResult(DealerCoupon dealerCoupon, Series series,
			Brand brand) {
		this.name = fillName(series, brand);
		this.offerDeadline = CommonUtils.getShortTimeFormat(new Date(dealerCoupon.getFinishedTime()));
		this.commission = dealerCoupon.getCommission();
	}

	private String fillName(Series series, Brand brand) {
		return brand.getBrandName() + series.getName();
	}
}
