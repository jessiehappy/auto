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
@Table(name = "category_brand")
public class CategoryBrand implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8229117690771431925L;
	private static final CategoryBrand EMPTY = new CategoryBrand(0L);
	
	private Long id;
	
	/**
	 * 品牌ID
	 */
	private Long brandId;

	/**
	 * 品牌当前类目下是否有效 0:有效 1：无效
	 */
	private Integer brandStatus;
	
	/**
	 * 排序
	 */
	private Integer sortNumber;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifiedTime;
	
	/**
	 * 平台二级目录ID
	 */
	private Long secondLevId;
	/**
	 * 平台三级目录ID
	 */
	private Long thirdLevId;
	
	public CategoryBrand(Long id) {
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

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public int getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(Integer brandStatus) {
		this.brandStatus = brandStatus;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
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

	public Long getSecondLevId() {
		return secondLevId;
	}

	public void setSecondLevId(Long secondLevId) {
		this.secondLevId = secondLevId;
	}
	
	public Long getThirdLevId() {
		return thirdLevId;
	}

	public void setThirdLevId(Long thirdLevId) {
		this.thirdLevId = thirdLevId;
	}
	
	@Override
	public void writeFields(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeLong(id);
		SerializeUtils.writeLong(out, brandId);
		SerializeUtils.writeInt(out, brandStatus);
		SerializeUtils.writeInt(out, sortNumber);
		SerializeUtils.writeDate(out, createTime);
		SerializeUtils.writeDate(out, modifiedTime);
		SerializeUtils.writeLong(out, secondLevId);
		SerializeUtils.writeLong(out, thirdLevId);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		brandId = SerializeUtils.readLong(in);
		brandStatus = SerializeUtils.readInt(in);
		sortNumber = SerializeUtils.readInt(in);
		createTime = SerializeUtils.readDate(in);
		modifiedTime = SerializeUtils.readDate(in);
		secondLevId= SerializeUtils.readLong(in);
		thirdLevId= SerializeUtils.readLong(in);
	}

	@Transient
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
