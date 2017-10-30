package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	/**
	 * 创建时间
	 */
	private Long createTime;
	
	/**
	 * 修改时间
	 */
	private Long modifiedTime;
	
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
	 * 核销状态 0：未核销 1：核销成功
	 */
	private Integer status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public int getAmount() {
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
	public int getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public AutoCouponVerification(Long id) {
		this.id = id;
	}
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeLong(out, createTime);
		SerializeUtils.writeLong(out, modifiedTime);
		SerializeUtils.writeInt(out, amount);
		SerializeUtils.writeString(out, username);
		SerializeUtils.writeLong(out, autoCouponId);
		SerializeUtils.writeInt(out, status);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		createTime = SerializeUtils.readLong(in);
		modifiedTime = SerializeUtils.readLong(in);
		amount = SerializeUtils.readInt(in);
		username = SerializeUtils.readString(in);
		autoCouponId = SerializeUtils.readLong(in);
		status = SerializeUtils.readInt(in);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
