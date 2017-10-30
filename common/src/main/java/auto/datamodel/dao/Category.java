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
 * @author wangWentao
 *
 */
@NoArgsConstructor
@Entity
@Table (name = "category")
public class Category implements java.io.Serializable, ICacheable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8702919323454026827L;

	private static final Category EMPTY = new Category(0L);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	/**
	 * 类别名称
	 */
	private String cName;
	
	/**
	 * 类目级别 1：一级类目；2：二级类目；3：三级类目
	 */
	private Integer lev;
	
	/**
	 * 父ID
	 */
	private Long parentId;
	
	/**
	 * 排序 如：进口车为1，国产车位2
	 */
	private Integer sortNumber;
	
	/**
	 * 创建时间
	 */
	private Long creatTime;
	
	/**
	 * 修改时间
	 */
	private Long modifiedTime;
	
	/**
	 * 是否启用 0：未启用 1：已起用
	 */
	private Integer status;

	public Category(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getcName() {
		return cName;
	}


	public void setcName(String cName) {
		this.cName = cName;
	}


	public int getLev() {
		return lev;
	}


	public void setLev(Integer lev) {
		this.lev = lev;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public int getSortNumber() {
		return sortNumber;
	}


	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}


	public Long getCreatTime() {
		return creatTime;
	}


	public void setCreatTime(Long creatTime) {
		this.creatTime = creatTime;
	}


	public Long getModifiedTime() {
		return modifiedTime;
	}


	public void setModifiedTime(Long modifiedTime) {
		this.modifiedTime = modifiedTime;
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
		SerializeUtils.writeString(out, cName);
		SerializeUtils.writeInt(out, lev);
		SerializeUtils.writeLong(out, parentId);
		SerializeUtils.writeInt(out, sortNumber);
		SerializeUtils.writeLong(out, creatTime);
		SerializeUtils.writeLong(out, modifiedTime);
		SerializeUtils.writeInt(out, status);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		id = in.readLong();
		cName = SerializeUtils.readString(in);
		lev = SerializeUtils.readInt(in);
		parentId = SerializeUtils.readLong(in);
		sortNumber = SerializeUtils.readInt(in);
		creatTime = SerializeUtils.readLong(in);
		modifiedTime = SerializeUtils.readLong(in);
		status = SerializeUtils.readInt(in);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return EMPTY.getId().equals(id);
	}
}
