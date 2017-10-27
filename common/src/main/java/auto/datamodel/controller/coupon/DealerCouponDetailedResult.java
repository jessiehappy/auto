package auto.datamodel.controller.coupon;

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
}
