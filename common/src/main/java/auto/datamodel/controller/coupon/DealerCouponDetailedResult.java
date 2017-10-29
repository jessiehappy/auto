package auto.datamodel.controller.coupon;

import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.dao.DealerCoupon;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于小b首页展示经销商代金券
 * @author wangWentao
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
	 * 车系名称
	 */
	private String seriesName;
	/**
	 * 车系指导价
	 */
	private String guidePrice;
	/**
	 * 佣金
	 */
	private Integer commission;
	
	/**
	 * 代金券份数  如果代金券没有生成 则显示“未生成” 否则 显示份数
	 */
	private String couponNum;
	
	public DealerCouponDetailedResult(DealerCoupon dealerCoupon) {
		// TODO Auto-generated constructor stub
		this.id = dealerCoupon.getId();
		this.seriesImg = ;
		this.seriesName = ;
		this.guidePrice = dealerCoupon.get;
		this.commission = dealerCoupon.getCommission();
		this.couponNum = fillCouponNum(dealerCoupon);
	}

	private String fillCouponNum(DealerCoupon dealerCoupon) {
		// TODO Auto-generated method stub
		if (dealerCoupon.getCouponNum() == null) {
			return JsonStatus.couponEmpty;
		}
		return String.valueOf(dealerCoupon.getCouponNum());
	}
}
