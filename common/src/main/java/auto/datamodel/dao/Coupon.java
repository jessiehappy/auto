package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import auto.datamodel.cache.ICacheable;
import auto.util.SerializeUtils;

@NoArgsConstructor
@Entity
@Table(name = "coupon")
public class Coupon implements java.io.Serializable, ICacheable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3933685576158459316L;

	public static final Coupon EMPTY = new Coupon(0L);
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	/**
	 * 优惠券编码
	 */
	private String couponCode;
	
	/**
	 * c端用户
	 */
	private String username;
	
	/**
	 * 优惠金额
	 */
	private Integer coupon;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	/**
	 * 结束时间
	 */
	private Long endedTime;
	
	/**
	 * 优惠券类型 0：汽车优惠券 1：other
	 */
	private Integer type;
	
	/**
	 * 优惠券使用状态 0：未使用 1：已使用 2：已过期
	 */
	private Integer status;
	public Coupon(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCoupon() {
		return coupon;
	}
	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getEndedTime() {
		return endedTime;
	}
	public void setEndedTime(Long endedTime) {
		this.endedTime = endedTime;
	}
	public int getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeString(out, couponCode);
		SerializeUtils.writeString(out, username);
		SerializeUtils.writeInt(out, coupon);
		SerializeUtils.writeLong(out, createTime);
		SerializeUtils.writeLong(out, endedTime);
		SerializeUtils.writeInt(out, type);
		SerializeUtils.writeInt(out, status);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		couponCode = SerializeUtils.readString(in);
		username = SerializeUtils.readString(in);
		coupon = SerializeUtils.readInt(in);
		createTime = SerializeUtils.readLong(in);
		endedTime = SerializeUtils.readLong(in);
		type = SerializeUtils.readInt(in);
		status = SerializeUtils.readInt(in);
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
