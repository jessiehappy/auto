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
@Table(name = "series")
public class Series implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6255822491681412912L;
	public static final Series EMPTY = new Series(0L);
	
	private Long id;

	/**
	 * 系类名称
	 */
	private String name;
	
	/**
	 * 创建时间
	 */
	private Date creatTime;
	
	/**
	 * 最后修改时间
	 */
	private Date modifiedTime;
	
	/**
	 * 商品分类ID
	 */
	private Long cId;
	
	/**
	 * 车系小图
	 */
	private String smallImg;
	
	/**
	 * 车系大图
	 */
	private String bigImg;
	
	/**
	 * 商品品牌ID
	 */
	private Long brandId;
	
	/**
	 * 商品品牌名称
	 */
	private String brandName;
	
	/**
	 * 最低价格
	 */
	private Integer minPrice;
	
	/**
	 * 最高价格
	 */
	private Integer maxPrice;
	
	/**
	 * 审核状态 0：审核通过 1：审核中 2：审核未通过
	 */
	private Integer auditStatus;
	
	/**
	 * 是否上架 0：上架 1：下架
	 */
	private Integer saleStatus;
	public Series(Long id) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
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
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	public Integer getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeString(out, name);
		SerializeUtils.writeDate(out, creatTime);
		SerializeUtils.writeDate(out, modifiedTime);
		SerializeUtils.writeLong(out, cId);
		SerializeUtils.writeString(out, smallImg);
		SerializeUtils.writeString(out, bigImg);
		SerializeUtils.writeLong(out, brandId);
		SerializeUtils.writeString(out, brandName);
		SerializeUtils.writeInt(out, minPrice);
		SerializeUtils.writeInt(out, maxPrice);
		SerializeUtils.writeInt(out, auditStatus);
		SerializeUtils.writeInt(out, saleStatus);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		name = SerializeUtils.readString(in);
		creatTime = SerializeUtils.readDate(in);
		modifiedTime = SerializeUtils.readDate(in);
		cId = SerializeUtils.readLong(in);
		smallImg = SerializeUtils.readString(in);
		bigImg = SerializeUtils.readString(in);
		brandId = SerializeUtils.readLong(in);
		brandName = SerializeUtils.readString(in);
		minPrice = SerializeUtils.readInt(in);
		maxPrice = SerializeUtils.readInt(in);
		auditStatus = SerializeUtils.readInt(in);
		saleStatus = SerializeUtils.readInt(in);
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
