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
@Table(name = "dealer_series")
public class DealerSeries implements java.io.Serializable, ICacheable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4257160738907230391L;
	public static final DealerSeries EMPTY = new DealerSeries(0L);
	
	private Long id;

	/**
	 * 经销商用户名
	 */
	private String dealerUsername;
	
	/**
	 * 销售车系ID
	 */
	private Long seriesId;
	
	/**
	 * 最低指导价
	 */
	private Integer guidePriceMin;
	
	/**
	 * 最高指导价
	 */
	private Integer guidePriceMax;
	
	/**
	 * 经销商填写的商品价格字符串
	 */
	private String priceStr;
	
	/**
	 * 车系小图 默认车系表小图
	 */
	private String smallImg;
	
	/**
	 * 车系大图 默认车系表大图
	 */
	private String bigImg;
	
	/**
	 * 系列销售上架状态 0：上架 1：下架
	 */
	private Integer status;
	public DealerSeries(Long id) {
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
	public String getDealerUsername() {
		return dealerUsername;
	}
	public void setDealerUsername(String dealerUsername) {
		this.dealerUsername = dealerUsername;
	}
	public Long getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Long seriesId) {
		this.seriesId = seriesId;
	}
	public int getGuidePriceMin() {
		return guidePriceMin;
	}
	public void setGuidePriceMin(Integer guidePriceMin) {
		this.guidePriceMin = guidePriceMin;
	}
	public int getGuidePriceMax() {
		return guidePriceMax;
	}
	public void setGuidePriceMax(Integer guidePriceMax) {
		this.guidePriceMax = guidePriceMax;
	}
	public String getSmallImg() {
		return smallImg;
	}
	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}
	public String getBigImg() {
		return bigImg;
	}
	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeString(out, dealerUsername);
		SerializeUtils.writeLong(out, seriesId);
		SerializeUtils.writeInt(out, guidePriceMin);
		SerializeUtils.writeInt(out, guidePriceMax);
		SerializeUtils.writeString(out, priceStr);
		SerializeUtils.writeString(out, smallImg);
		SerializeUtils.writeString(out, bigImg);
		SerializeUtils.writeInt(out, status);
		
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		dealerUsername = SerializeUtils.readString(in);
		seriesId = SerializeUtils.readLong(in);
		guidePriceMin = SerializeUtils.readInt(in);
		guidePriceMax = SerializeUtils.readInt(in);
		priceStr = SerializeUtils.readString(in);
		smallImg = SerializeUtils.readString(in);
		bigImg = SerializeUtils.readString(in);
		status = SerializeUtils.readInt(in);
	}
	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
