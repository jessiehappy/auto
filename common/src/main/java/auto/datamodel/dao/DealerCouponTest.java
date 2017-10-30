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

/**
 * 经销商优惠券
 * @author wangWentao
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "dealer_coupon_test")
public class DealerCouponTest implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3508359657643502026L;
	public static final DealerCouponTest EMPTY = new DealerCouponTest(0L);
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	/**
	 * 经销商用户名
	 */
	private String dealerUsername;
	
	/**
	 * 经销商车系ID
	 */
	private Long dealerSeriesId;
	
	/**
	 * 车系ID
	 */
	private Long seriesId;
	
	/**
	 * 优惠券数量
	 */
	private Integer couponNum;
	
	/**
	 * 佣金 单位分
	 */
	private Integer commission;
	
	/**
	 * 优惠券生效时间
	 */
	private Long startTime;
	
	/**
	 * 优惠券失败时间
	 */
	private Long finishedTime;
	
	/**
	 * 是否锁定 0：锁定 1：未锁定
	 */
	private Integer lock;
	
	/**
	 * 锁定时间
	 */
	private Long lockTime;
	
	/**
	 * 优惠券状态 ：0-有效 1-无效
	 */
	private Integer status;
	public DealerCouponTest(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDealerUsername() {
		return dealerUsername;
	}
	public void setDealerUsername(String dealerUsername) {
		this.dealerUsername = dealerUsername;
	}
	public Long getDealerSeriesId() {
		return dealerSeriesId;
	}
	public void setDealerSeriesId(Long dealerSeriesId) {
		this.dealerSeriesId = dealerSeriesId;
	}
	public Long getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Long seriesId) {
		this.seriesId = seriesId;
	}
	public int getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(Integer couponNum) {
		this.couponNum = couponNum;
	}
	public int getCommission() {
		return commission;
	}
	public void setCommission(Integer commission) {
		this.commission = commission;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(Long finishedTime) {
		this.finishedTime = finishedTime;
	}
	public int getLock() {
		return lock;
	}
	public void setLock(Integer lock) {
		this.lock = lock;
	}
	public Long getLockTime() {
		return lockTime;
	}
	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
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
		SerializeUtils.writeString(out, dealerUsername);
		SerializeUtils.writeLong(out, dealerSeriesId);
		SerializeUtils.writeLong(out, seriesId);
		SerializeUtils.writeInt(out, couponNum);
		SerializeUtils.writeInt(out, commission);
		SerializeUtils.writeLong(out, startTime);
		SerializeUtils.writeLong(out, finishedTime);
		SerializeUtils.writeInt(out, lock);
		SerializeUtils.writeLong(out, lockTime);
		SerializeUtils.writeInt(out, status);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		dealerUsername = SerializeUtils.readString(in);
		dealerSeriesId = SerializeUtils.readLong(in);
		seriesId = SerializeUtils.readLong(in);
		couponNum = SerializeUtils.readInt(in);
		commission = SerializeUtils.readInt(in);
		startTime = SerializeUtils.readLong(in);
		finishedTime = SerializeUtils.readLong(in);
		lock = SerializeUtils.readInt(in);
		lockTime = SerializeUtils.readLong(in);
		status = SerializeUtils.readInt(in);
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}