package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import auto.datamodel.Status;
import lombok.NoArgsConstructor;

/**
 * 代理人账户 
 * @author wanglongtao
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "proxy_account")
public class ProxyAccount {

	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 余额
	 */
	private Integer balance;
	/**
	 * 累计收益
	 */
	private Integer couponTotal;
	/**
	 * 账户状态
	 * @see Status
	 */
	private Integer status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
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
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getCouponTotal() {
		return couponTotal;
	}
	public void setCouponTotal(Integer couponTotal) {
		this.couponTotal = couponTotal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
