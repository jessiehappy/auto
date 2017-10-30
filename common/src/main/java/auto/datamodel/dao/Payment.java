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
@Table(name = "payment")
public class Payment implements java.io.Serializable, ICacheable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3504959512367570625L;
	public static final Payment EMPTY = new Payment(0L);
	
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
	 * 账单用户名
	 */
	private String username;
	
	/**
	 * 付款人
	 */
	private String fromUsername;
	
	/**
	 * 收款人
	 */
	private String toUsername;
	
	/**
	 * 账单总额
	 */
	private Integer amount;
	
	/**
	 * 支付金额 单位分
	 */
	private Integer paymentCost;
	
	/**
	 * 余额支付金额 单位分
	 */
	private Integer balanceCost;
	
	/**
	 * 账单类型 0：充值
	 */
	private Integer paymentType;
	
	/**
	 * 支付单描述
	 */
	private String description;
	
	/**
	 * 支付成功信息
	 */
	private String paidlnfo;
	/**
	 * 支付单状态 0：未支付 1：支付中 2：支付成功 3：取消支付
	 */
	private Integer status;
	public Payment(Long id) {
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getPaymentCost() {
		return paymentCost;
	}
	public void setPaymentCost(Integer paymentCost) {
		this.paymentCost = paymentCost;
	}
	public Integer getBalanceCost() {
		return balanceCost;
	}
	public void setBalanceCost(Integer balanceCost) {
		this.balanceCost = balanceCost;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPaidlnfo() {
		return paidlnfo;
	}
	public void setPaidlnfo(String paidlnfo) {
		this.paidlnfo = paidlnfo;
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
		SerializeUtils.writeLong(out, createTime);
		SerializeUtils.writeLong(out, modifiedTime);
		SerializeUtils.writeString(out, username);
		SerializeUtils.writeString(out, fromUsername);
		SerializeUtils.writeString(out, toUsername);
		SerializeUtils.writeInt(out, amount);
		SerializeUtils.writeInt(out, paymentCost);
		SerializeUtils.writeInt(out, balanceCost);
		SerializeUtils.writeInt(out, paymentType);
		SerializeUtils.writeString(out, description);
		SerializeUtils.writeString(out, paidlnfo);
		SerializeUtils.writeInt(out, status);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		createTime = SerializeUtils.readLong(in);
		modifiedTime = SerializeUtils.readLong(in);
		username = SerializeUtils.readString(in);
		fromUsername = SerializeUtils.readString(in);
		toUsername = SerializeUtils.readString(in);
		amount = SerializeUtils.readInt(in);
		paymentCost = SerializeUtils.readInt(in);
		balanceCost = SerializeUtils.readInt(in);
		paymentType = SerializeUtils.readInt(in);
		description = SerializeUtils.readString(in);
		paidlnfo = SerializeUtils.readString(in);
		status = SerializeUtils.readInt(in);
	}
	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
