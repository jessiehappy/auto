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
@Table(name= "dealer_account")
public class DealerAccount implements java.io.Serializable, ICacheable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7328355695251175868L;
	public static final DealerAccount EMPTY = new DealerAccount(0L);
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	/**
	 * 代理商用户名
	 */
	private String username;
	
	/**
	 * 账户真实姓名
	 */
	private String realName;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 支付宝账户
	 */
	private String alipayNumber;
	
	/**
	 * 余额 单位分
	 */
	private Integer balance;
	public DealerAccount(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAlipayNumber() {
		return alipayNumber;
	}
	public void setAlipayNumber(String alipayNumber) {
		this.alipayNumber = alipayNumber;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeString(out, username);
		SerializeUtils.writeString(out, realName);
		SerializeUtils.writeString(out, idCard);
		SerializeUtils.writeString(out, alipayNumber);
		SerializeUtils.writeInt(out, balance);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		username = SerializeUtils.readString(in);
		realName = SerializeUtils.readString(in);
		idCard = SerializeUtils.readString(in);
		alipayNumber = SerializeUtils.readString(in);
		balance = SerializeUtils.readInt(in);
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
