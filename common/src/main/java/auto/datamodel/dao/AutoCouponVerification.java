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

import auto.datamodel.CouponVerificationStatus;
import auto.datamodel.cache.ICacheable;
import auto.util.SerializeUtils;
import lombok.NoArgsConstructor;

/**
 * @author wangWentao
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "auto_coupon_cancel_after_verification")
public class AutoCouponVerification implements java.io.Serializable, ICacheable{/**
	 * 
	 */
	private static final long serialVersionUID = 114228974410744567L;
	private static final AutoCouponVerification EMPTY = new AutoCouponVerification(0L);
	
	
	private Long id;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifiedTime;
	
	/**
	 * 核销金额 单位分
	 */
	private Integer amount;
	
	/**
	 * 经销商用户名
	 */
	private String username;
	
	/**
	 * 汽车券ID
	 */
	private Long autoCouponId;
	
	/**
	 * 代理人
	 */
	private String proxyUsername;
	/**
	 * 优惠券金额 单位分
	 */
	private Integer coupon;
	/**
	 * 领券电话
	 */
	private String telephone;
	/**
	 * 领券人 可以为空
	 */
	private String cUsername;
	/**
	 * 车系ID
	 */
	private Long seriesId;
	
	/**
	 * 核销状态 0：未核销 1：核销成功 2:核销失败
	 * @see CouponVerificationStatus
	 */
	private Integer status;
	
	public AutoCouponVerification(Long id) {
		super();
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getAutoCouponId() {
		return autoCouponId;
	}

	public void setAutoCouponId(Long autoCouponId) {
		this.autoCouponId = autoCouponId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeDate(out, createTime);
		SerializeUtils.writeDate(out, modifiedTime);
		SerializeUtils.writeInt(out, amount);
		SerializeUtils.writeString(out, username);
		SerializeUtils.writeLong(out, autoCouponId);
		SerializeUtils.writeString(out, proxyUsername);
		SerializeUtils.writeInt(out, coupon);
		SerializeUtils.writeString(out, telephone);
		SerializeUtils.writeString(out, cUsername);
		SerializeUtils.writeLong(out, seriesId);
		SerializeUtils.writeInt(out, status);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		createTime = SerializeUtils.readDate(in);
		modifiedTime = SerializeUtils.readDate(in);
		amount = SerializeUtils.readInt(in);
		username = SerializeUtils.readString(in);
		autoCouponId = SerializeUtils.readLong(in);
		proxyUsername = SerializeUtils.readString(in);
		coupon = SerializeUtils.readInt(in);
		telephone = SerializeUtils.readString(in);
		cUsername = SerializeUtils.readString(in);
		seriesId = SerializeUtils.readLong(in);
		status = SerializeUtils.readInt(in);
	}

	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
