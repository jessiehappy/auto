package auto.datamodel.controller.coupon;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealerCouponResult {
	
	/**
	 * 授权码
	 */
	private String code;
	/**
	 * 代金券详细信息
	 */
	private List<DealerCouponDetailedResult> items = new ArrayList<DealerCouponDetailedResult>();
}
