package auto.datamodel.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.NoArgsConstructor;
import auto.datamodel.cache.ICacheable;
import auto.util.SerializeUtils;

/**
 * 提供商认证表
 */

@NoArgsConstructor
@Entity
@Table(name = "dealer_auth")//implements java.io.Serializable, ICacheable
public class DealerAuth {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562754448409199820L;

	public static final DealerAuth EMPTY=new DealerAuth(0L);

	private Long id;
	private String username;//用户名
	private String telephone;//电话
	private String address;//公司地址
	private String  company;//公司名称
	private String businessLicence;//营业执照
	private BigDecimal longitude;//经度
	private BigDecimal latitude;//纬度
	private int status;//认证状态   0-未认证  1-审核中  2-未通过  3-已通过
	
    private DealerAuth (Long id){
		this.id=id;
	}
    
    /*@Override
	public void writeFields(DataOutput out) throws IOException {
    	out.writeLong(id);
    	SerializeUtils.writeString(out, username);
    	SerializeUtils.writeString(out, telephone);
    	SerializeUtils.writeString(out, address);
    	SerializeUtils.writeString(out, company);
    	SerializeUtils.writeString(out, businessLicence);
    	SerializeUtils.writeString(out, longitude.stripTrailingZeros().toPlainString());
    	SerializeUtils.writeString(out, latitude.stripTrailingZeros().toPlainString());
    	SerializeUtils.writeInt(out, status);	
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id=in.readLong();
		username=SerializeUtils.readString(in);
		telephone=SerializeUtils.readString(in);
		address=SerializeUtils.readString(in);
		company=SerializeUtils.readString(in);
		businessLicence=SerializeUtils.readString(in);
		longitude=new BigDecimal(SerializeUtils.readString(in));
		latitude=new BigDecimal(SerializeUtils.readString(in));
		status=SerializeUtils.readInt(in);	
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		 return EMPTY.getId().equals(id);
	}
	*/
	
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
