package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import auto.datamodel.PaymentStatus;
import auto.datamodel.WithdrawalType;

/**
 * user withdrawal
 * operation 
 * @author wanglongtao
 *
 */
public class Withdrawal {

	private Long id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 提现金额
	 */
	private Integer amount;
	/**
	 * 实际提现金额 = 提现金额 - 税费
	 */
	private Integer acutalAmout;
	/**
	 * 提现手续费  暂时定为提现金额的15%
	 */
	private Integer tax;
	/**
	 * 发起提现申请时间
	 */
	private Date createdTime;
	/**
	 * 提现开始处理时间
	 */
	private Date applyTime;
	/**
	 * 提现 平台支付时间
	 */
	private Date payingTime;
	/**
	 * 提现成功或失败时间
	 */
	private Date finishTime;
	/**
	 * 提现账号， 借记卡
	 */
	private String debitCard;
	/**
	 * 提现备注（运营备注）
	 */
	private String comment;
	/**
	 * 提现类型
	 * @see WithdrawalType
	 */
	private Integer type;
	/**
	 * 提现状态
	 * @see PaymentStatus
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getAcutalAmout() {
		return acutalAmout;
	}
	public void setAcutalAmout(Integer acutalAmout) {
		this.acutalAmout = acutalAmout;
	}
	public Integer getTax() {
		return tax;
	}
	public void setTax(Integer tax) {
		this.tax = tax;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getPayingTime() {
		return payingTime;
	}
	public void setPayingTime(Date payingTime) {
		this.payingTime = payingTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getDebitCard() {
		return debitCard;
	}
	public void setDebitCard(String debitCard) {
		this.debitCard = debitCard;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
