package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

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
@Table(name = "coupon")
public class Coupon implements java.io.Serializable, ICacheable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3933685576158459316L;

	public static final Coupon EMPTY = new Coupon(0L);
	
	private Long id;

	/**
	 * 优惠券编码
	 */
	private String couponCode;
	
	/**
	 * 优惠券名称 品牌 + 车系
	 */
	private String couponName;
	
	/**
	 * 优惠券二维码地址
	 */
	private String couponUrl;
	
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
	private Date createTime;
	
	/**
	 * 结束时间
	 */
	private Date endedTime;
	
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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndedTime() {
		return endedTime;
	}
	public void setEndedTime(Date endedTime) {
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
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
		SerializeUtils.writeString(out, couponCode);
		SerializeUtils.writeString(out, couponName);
		SerializeUtils.writeString(out, couponUrl);
		SerializeUtils.writeString(out, username);
		SerializeUtils.writeInt(out, coupon);
		SerializeUtils.writeDate(out, createTime);
		SerializeUtils.writeDate(out, endedTime);
		SerializeUtils.writeInt(out, type);
		SerializeUtils.writeInt(out, status);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		couponCode = SerializeUtils.readString(in);
		couponName = SerializeUtils.readString(in);
		couponUrl = SerializeUtils.readString(in);
		username = SerializeUtils.readString(in);
		coupon = SerializeUtils.readInt(in);
		createTime = SerializeUtils.readDate(in);
		endedTime = SerializeUtils.readDate(in);
		type = SerializeUtils.readInt(in);
		status = SerializeUtils.readInt(in);
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
