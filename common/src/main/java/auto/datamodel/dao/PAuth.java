package auto.datamodel.dao;
/**
 * 代理人认证表
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
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
@Table(name = "proxy_auth")
public class PAuth implements java.io.Serializable, ICacheable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8109342625642288042L;

	public static final PAuth EMPTY=new PAuth(0L);

	private Long id;
	private Date createTime;//创建时间
	private Date modifiedTime;//修改时间
	private String username;//代理人用户名
	private String realName;//真实姓名
	private String idNo;//身份证号
	private String frontImg;//身份证正面照
	private String backImg;//身份证背面照
	private String qualification;//资格证
	private BigDecimal longitude;//经度
	private BigDecimal latitude;//纬度
	private int status;//认证状态   0-未认证  1-审核中  2-未通过  3-已通过
	
	
    private PAuth (Long id){
		this.id=id;
	}
    
    @Override
	public void writeFields(DataOutput out) throws IOException {
    	out.writeLong(id);
    	SerializeUtils.writeDate(out, createTime);
    	SerializeUtils.writeDate(out, modifiedTime);
    	SerializeUtils.writeString(out, username);
    	SerializeUtils.writeString(out, realName);
    	SerializeUtils.writeString(out, idNo);
    	SerializeUtils.writeString(out, frontImg);
    	SerializeUtils.writeString(out, backImg);
    	SerializeUtils.writeString(out, qualification);
    	SerializeUtils.writeString(out, longitude.stripTrailingZeros().toPlainString());
    	SerializeUtils.writeString(out, latitude.stripTrailingZeros().toPlainString());
    	SerializeUtils.writeInt(out, status);
    	
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id=in.readLong();
		createTime=SerializeUtils.readDate(in);
		modifiedTime=SerializeUtils.readDate(in);
		username=SerializeUtils.readString(in);
		realName=SerializeUtils.readString(in);
		idNo=SerializeUtils.readString(in);
		frontImg=SerializeUtils.readString(in);
		backImg=SerializeUtils.readString(in);
		qualification=SerializeUtils.readString(in);
		longitude=new BigDecimal(SerializeUtils.readString(in));
		latitude=new BigDecimal(SerializeUtils.readString(in));
		status=SerializeUtils.readInt(in);
				
		
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		
		 return EMPTY.getId().equals(id);
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFrontImg() {
		return frontImg;
	}

	public void setFrontImg(String frontImg) {
		this.frontImg = frontImg;
	}

	public String getBackImg() {
		return backImg;
	}

	public void setBackImg(String backImg) {
		this.backImg = backImg;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	
}
