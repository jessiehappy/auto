package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import auto.datamodel.Status;

/**
 * 用户卡包
 * @author wanglongtao
 *
 */
public class UserCard {

	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 借记卡
	 */
	private String debitCard;
	/**
	 * 状态 0:有效 1:无效
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
	public String getDebitCard() {
		return debitCard;
	}
	public void setDebitCard(String debitCard) {
		this.debitCard = debitCard;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
