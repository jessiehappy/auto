package auto.datamodel.dao;
/**
 * 大b-经销商表
 */
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

import org.apache.commons.lang.StringUtils;

import lombok.NoArgsConstructor;
import auto.datamodel.cache.ICacheable;
import auto.util.SerializeUtils;

@NoArgsConstructor
@Entity
@Table(name = "dealer_user")
public class DealerUser implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1219375535782727216L;
	
	public static final String DEFAULT_FAVICON="http://7u2hvg.com2.z0.glb.qiniucdn.com/107room_20170808104602591_QaWAD5/favicon/2017080816001108461.jpg";
	
	public static final String DEFAULT_PASSWORD = "5585812774057885129";
    
    public static final String ENCRYPTED_DEFAULT_PASSWORD = "7dc1d3f9f31a1290";
	
    public static final DealerUser EMPTY=new DealerUser(0L);

	private Long id;
	private Date createTime;//创建时间
	private Date modifyTime;//修改时间
	private String username;//用户名
	private String telephone;//电话号码
	private String password;//密码
    private String wechatId;//微信ID
    private String wechatName;//微信名称
    private String wechatFavicon;//微信头像
    private String openId;
    private String token;
    private String nickName;//昵称
    private String favicon;//头像
    private int gender;//性别  0：无   1：男   2：女  3:第三性
    private int status;//状态  0：未认证    1：审核中  2：未通过  3：已通过
    private int dataStatus;//数据状态 （0、正常   1、已删除）
    
	
    private DealerUser (Long id){
		this.id=id;
	}
 
    
    @Override
	public void writeFields(DataOutput out) throws IOException {
    	out.writeLong(id);
    	SerializeUtils.writeDate(out, createTime);
    	SerializeUtils.writeDate(out, modifyTime);
    	SerializeUtils.writeString(out, username);
    	SerializeUtils.writeString(out, telephone);
    	SerializeUtils.writeString(out, password);
    	SerializeUtils.writeString(out, wechatId);
    	SerializeUtils.writeString(out, wechatName);
    	SerializeUtils.writeString(out, wechatFavicon);
    	SerializeUtils.writeString(out, openId);
    	SerializeUtils.writeString(out, token);
    	SerializeUtils.writeString(out, nickName);
    	SerializeUtils.writeString(out, favicon);
    	SerializeUtils.writeInt(out, gender);
    	SerializeUtils.writeInt(out, status);
    	
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id=in.readLong();
		createTime=SerializeUtils.readDate(in);
		modifyTime=SerializeUtils.readDate(in);
		username=SerializeUtils.readString(in);
		telephone=SerializeUtils.readString(in);
		password=SerializeUtils.readString(in);
		wechatId=SerializeUtils.readString(in);
		wechatName=SerializeUtils.readString(in);
		wechatFavicon=SerializeUtils.readString(in);
		openId=SerializeUtils.readString(in);
		token=SerializeUtils.readString(in);
		nickName=SerializeUtils.readString(in);
		favicon=SerializeUtils.readString(in);
		gender=SerializeUtils.readInt(in);
		status=SerializeUtils.readInt(in);
				
		
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		
		 return EMPTY.getId().equals(id);
	}
	
	@Transient
    public boolean hasPassword() {
        if (StringUtils.isEmpty(password) || ENCRYPTED_DEFAULT_PASSWORD.equals(password)) {
            return false;
        }
        return true;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFavicon() {
		return favicon;
	}

	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	
	
}
