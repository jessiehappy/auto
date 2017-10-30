package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.NoArgsConstructor;
import auto.datamodel.cache.ICacheable;
import auto.util.SerializeUtils;

/**
 * @author wangWentao
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "auto_coupon")
public class AutoCoupon implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1428447787648515171L;
	public static final AutoCoupon EMPTY = new AutoCoupon(0L);

	private Long id;
	
	/**
	 * 优惠券ID
	 */
	private Long couponId;
	
	/**
	 * 汽车优惠券二维码图片地址
	 */
	private String couponUrl;
	
	/**
	 * c端用户电话号码
	 */
	private String cTelephone;
	
	/**
	 * 经销商优惠券ID
	 */
	private Long dealerCouponId;
	
	/**
	 * 代理人优惠券ID
	 */
	private Long proxyCouponId;
	
	public AutoCoupon(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getcTelephone() {
		return cTelephone;
	}

	public void setcTelephone(String cTelephone) {
		this.cTelephone = cTelephone;
	}

	public Long getDealerCouponId() {
		return dealerCouponId;
	}

	public void setDealerCouponId(Long dealerCouponId) {
		this.dealerCouponId = dealerCouponId;
	}

	public Long getProxyCouponId() {
		return proxyCouponId;
	}

	public void setProxyCouponId(Long proxyCouponId) {
		this.proxyCouponId = proxyCouponId;
	}
	
	public String getCouponUrl() {
		return couponUrl;
	}

	public void setCouponUrl(String couponUrl) {
		this.couponUrl = couponUrl;
	}
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeLong(out, couponId);
		SerializeUtils.writeString(out, couponUrl);
		SerializeUtils.writeString(out, cTelephone);
		SerializeUtils.writeLong(out, dealerCouponId);
		SerializeUtils.writeLong(out, proxyCouponId);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		couponId = SerializeUtils.readLong(in);
		couponUrl = SerializeUtils.readString(in);
		cTelephone = SerializeUtils.readString(in);
		dealerCouponId = SerializeUtils.readLong(in);
		proxyCouponId = SerializeUtils.readLong(in);
	}

	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
