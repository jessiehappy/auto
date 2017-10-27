package auto.datamodel.dao;
/**
 * 小b-代理商表
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

import lombok.NoArgsConstructor;
import auto.datamodel.cache.ICacheable;
import auto.util.SerializeUtils;

@NoArgsConstructor
@Entity
@Table(name = "proxy_user")
public class PUser implements java.io.Serializable, ICacheable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2582059062715919058L;

	public static final PUser EMPTY=new PUser(0L);

	private Long id;
	private Date createTime;//创建时间
	private Date modifyTime;//修改时间
	private String username;//用户名
	private String telephone;//电话号码
    private String wechatId;//微信ID
    private String wechatName;//微信名称
    private String wechatFavicon;//微信头像
    private String openId;
    private String token;
    private String imgFile;//认证图像存放文件夹名称（默认和username同名）
    private int status;//状态  0：未认证    1：审核中  2：未通过  3：已通过
    private int dataStatus;//数据状态 （0、正常   1、已删除）
	
    private PUser (Long id){
		this.id=id;
	}
    
    @Override
	public void writeFields(DataOutput out) throws IOException {
    	out.writeLong(id);
    	SerializeUtils.writeDate(out, createTime);
    	SerializeUtils.writeDate(out, modifyTime);
    	SerializeUtils.writeString(out, username);
    	SerializeUtils.writeString(out, telephone);
    	SerializeUtils.writeString(out, wechatId);
    	SerializeUtils.writeString(out, wechatName);
    	SerializeUtils.writeString(out, wechatFavicon);
    	SerializeUtils.writeString(out, openId);
    	SerializeUtils.writeString(out, token);
    	
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id=in.readLong();
		createTime=SerializeUtils.readDate(in);
		modifyTime=SerializeUtils.readDate(in);
		username=SerializeUtils.readString(in);
		telephone=SerializeUtils.readString(in);
		wechatId=SerializeUtils.readString(in);
		wechatName=SerializeUtils.readString(in);
		wechatFavicon=SerializeUtils.readString(in);
		openId=SerializeUtils.readString(in);
		token=SerializeUtils.readString(in);
				
		
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

	public int getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
		
}
