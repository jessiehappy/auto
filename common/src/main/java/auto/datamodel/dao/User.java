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
@Table(name = "c_user")
public class User implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6516533481976574879L;
	public static final User EMPTY=new User(0L);

	private Long id;
	private Date createTime;//创建时间
	private String telephone;//电话号码
    private String wechatId;//微信ID
    private String wechatName;//微信名称
    private String wechatFavicon;//微信头像
	
    private User (Long id){
		this.id=id;
	}
    
    @Override
	public void writeFields(DataOutput out) throws IOException {
    	out.writeLong(id);
    	SerializeUtils.writeDate(out, createTime);
    	SerializeUtils.writeString(out, telephone);
    	SerializeUtils.writeString(out, wechatId);
    	SerializeUtils.writeString(out, wechatName);
    	SerializeUtils.writeString(out, wechatFavicon);
    	
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id=in.readLong();
		createTime=SerializeUtils.readDate(in);
		telephone=SerializeUtils.readString(in);
		wechatId=SerializeUtils.readString(in);
		wechatName=SerializeUtils.readString(in);
		wechatFavicon=SerializeUtils.readString(in);
				
		
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
	

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getWechatFavicon() {
		return wechatFavicon;
	}

	public void setWechatFavicon(String wechatFavicon) {
		this.wechatFavicon = wechatFavicon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}
