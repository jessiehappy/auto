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

@NoArgsConstructor
@Entity
@Table(name = "proxy_coupon")
public class ProxyCoupon implements java.io.Serializable, ICacheable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 39522776626328935L;
	public static final ProxyCoupon EMPTY = new ProxyCoupon(0L);
	
	private Long id;

	/**
	 * 代理人
	 */
	private String proxyUsername;
	
	/**
	 * 代理人优惠价格设置
	 */
	private Integer coupon;
	
	/**
	 * 经销商优惠券ID
	 */
	private Long dealerCouponId;
	
	/**
	 * 经销商用户名
	 */
	private String dealerUsername;
	
	/**
	 * 总佣金经销商设置的佣金单位分
	 */
	private Integer proxyCommission;
	
	/**
	 * 车系ID
	 */
	private Long seriesId;
	public ProxyCoupon(Long id) {
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
	public String getProxyUsername() {
		return proxyUsername;
	}
	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}
	public Integer getCoupon() {
		return coupon;
	}
	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}
	public long getDealerCouponId() {
		return dealerCouponId;
	}
	public void setDealerCouponId(Long dealerCouponId) {
		this.dealerCouponId = dealerCouponId;
	}
	public String getDealerUsername() {
		return dealerUsername;
	}
	public void setDealerUsername(String dealerUsername) {
		this.dealerUsername = dealerUsername;
	}
	public Integer getProxyCommission() {
		return proxyCommission;
	}
	public void setProxyCommission(Integer proxyCommission) {
		this.proxyCommission = proxyCommission;
	}
	public Long getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Long seriesId) {
		this.seriesId = seriesId;
	}
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeString(out, proxyUsername);
		SerializeUtils.writeInt(out, coupon);
		SerializeUtils.writeLong(out, dealerCouponId);
		SerializeUtils.writeString(out, dealerUsername);
		SerializeUtils.writeInt(out, proxyCommission);
		SerializeUtils.writeLong(out, seriesId);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		proxyUsername = SerializeUtils.readString(in);
		coupon = SerializeUtils.readInt(in);
		dealerCouponId = SerializeUtils.readLong(in);
		dealerUsername = SerializeUtils.readString(in);
		proxyCommission = SerializeUtils.readInt(in);
		seriesId = SerializeUtils.readLong(in);
	}
	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
