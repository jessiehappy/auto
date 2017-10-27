# 汽车城-用户-接口文档

##  c端用户

### 1. 获取短信验证码

**接口地址**：/user/custom/sendPhoneNum

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/custom/sendPhoneNum

**接口备注**：手机验证码发送请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| openId | string | 是    | 微信用户唯一标识   |
| telephone  | String | 是 | 电话号码 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---

*** json示例 ***

```json
{
  "errCode": 200,
  "msg": "success"
}
```

### 2. 验证短信

**接口地址**：/user/custom/verifyPhoneNum

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/custom/verifyPhoneNum

**接口备注**：实现短信验证码验证请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| openId | string | 是    | 微信用户唯一标识   |
| telephone | string | 是    | 手机号码 |
| phoneNum | string | 是    | 发送给手机的短信验证码 |
##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---

*** json示例 ***

```json
成功：
{
	"errCode":"200",
	"msg":"sucess"
}
失败：
{
  "errCode": 2016,
  "msg": "手机验证码错误"
}
```
3.

##  小b-代理商

### 1. 获取短信验证码

**接口地址**：/user/proxy/sendPhoneNum

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/sendPhoneNum

**接口备注**：手机验证码发送请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| openId | string | 是    | 微信用户唯一标识   |
| telephone  | String | 是 | 电话号码 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---

*** json示例 ***

```json
{
  "errCode": 200,
  "msg": "success"
}
```

### 2. 验证短信

**接口地址**：/user/proxy/verifyPhoneNum

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/verifyPhoneNum

**接口备注**：实现短信验证码验证请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| openId | string | 是    | 微信用户唯一标识   |
| telephone | string | 是    | 手机号码 |
| phoneNum | string | 是    | 发送给手机的短信验证码 |
##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---

*** json示例 ***

```json
成功：
{
	"errCode":"200",
	"msg":"sucess"
}
失败：
{
  "errCode": 2016,
  "msg": "手机验证码错误"
}
```
### 3. 判断用户是否通过认证

**接口地址**：/user/proxy/identifyPUser

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/identifyPUser

**接口备注**：判断用户是否通过认证   status 0-未认证   1-审核中  2-未通过  3-已通过

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 手机号码 |
##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| status	 | int | 返回认证状态  0-未认证   1-审核中  2-未通过  3-已通过|

##### 返回结果

---

*** json示例 ***

```json
{
	"errCode":"200",
	"msg":"sucess"
    “data":{
         "status":3
    }
}
```

### 4. 切换账号

**接口地址**：/user/proxy/switchPUser

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/switchPUser

**接口备注**：现用户清除openId,然后新建用户

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 切换的新手机号码 |
| openId     | string | 是    | openId |
##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---

*** json示例 ***

```json
{
	"errCode":"200",
	"msg":"sucess"
}
```
### 5. 上传身份证正面照

**接口地址**：/user/proxy/uploadFrontImg

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/uploadFrontImg

**接口备注**：上传身份证正面照，返回图片地址

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 切换的新手机号码 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| frontImg     | string | 返回图片地址  |

##### 返回结果

---

*** json示例 ***

```json
{
	"errCode":"200",
	"msg":"sucess"
    "data":{
       "frontImg":"XXXX"
    }
}
```
### 6. 上传资格证

**接口地址**：/user/proxy/uploadQualification

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/uploadQualification

**接口备注**：上传资格证，返回图片地址

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 切换的新手机号码 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| qualification| string | 返回图片地址  |

##### 返回结果

---

*** json示例 ***

```json
{
	"errCode":"200",
	"msg":"sucess"
    "data":{
       "qualification":"XXXX"
    }
}
```
### 7. 提交 认证信息

**接口地址**：/user/proxy/submitPUser

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/submitPUser

**接口备注**：上传认证信息，返回需要展示的认证信息

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 手机号码 |
| realName | string | 是    | 真实姓名|
| idNo | string | 是       | 身份证号 |
| frontImg | string | 是    | 身份证正面图片地址 |
| qualification | string | 是    | 资格证图片地址 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| realName| string | 返回姓名 |
| idNo	  | string | 返回身份证号 |
| frontImg| string | 返回身份证正面照地址 |
| qualification| string | 返回资格证照地址  |
| status	| int | 认证状态  0:未认证 1:审核中  2:未通过 3:已通过 |

##### 返回结果

---

*** json示例 ***

```json
{
	"errCode":"200",
	"msg":"sucess"
    "data":{
       "realName":"XXXX",
       "idNo":"XXXX",
       "frontImg":"XXXX",
       "qualification":"XXXX",
       "status":0
    }
}
```

### 7. 审核 认证信息

**接口地址**：/user/proxy/auditPUser

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/proxy/auditPUser

**接口备注**：审核代理人认证信息

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 手机号码 |
| status    | string | 是    | 审核结果- 1：审核中  2：未通过  3：已通过 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---

*** json示例 ***

```json
{
	"errCode":"200",
	"msg":"sucess"
}
```
##  大b-提供商

### 1. 用户登录

**接口地址**：/user/dealer/login

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/login

**接口备注**：用户登录 方式：手机号码+密码

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| telephone | string | 是    | 手机号码  |
| password | string | 是    | 密码  |

##### 返回信息说明

- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| telephone	 | string |  电话号码 |
| nickName	 | string |  昵称   |
| favicon 	| string | 头像地址  |
| gender	 | int  |  性别  1:未知 1:男  2:女  3：第三性   |
| status 	| int | 认证状态  0:未认证 1:审核中  2:未通过 3:已通过  |

##### 返回结果

---

*** json示例 ***

```json
成功：
{
  "errCode": 200,
  "msg": "success",
  "data": {
    "telephone": "XXXX"，
    "nickName": "XXXX"，
    "favicon": "XXXX"，
    "gender": 2，
    "status": 0
  }
}
失败：
{
  "errCode": 2017,
  "msg": "密码不正确"
}
或
{
  "errCode": 2017,
  "msg": "用户不存在"
}
```
### 2. 手机短信验证码请求

**接口地址**：/user/dealer/sendPhoneNum

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/sendPhoneNum

**接口备注**：手机验证码发送请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| deviceId | string | 是    | 设备ID   |
| telephone  | String | 是 | 电话号码 |

##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
##### 返回结果
---
*** json示例 ***
```json
{
	"errCode":"200",
	"msg":"sucess"
}
```
### 3. 手机短信验证码验证

**接口地址**：/user/dealer/verifyPhoneNum

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/verifyPhoneNum

**接口备注**：实现短信验证码验证请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| deviceId | string | 是    | 设备ID   |
| telephone  | string | 是 | 手机号 |
| phoneNum | string | 是 |用户输入的短信验证码 |
##### 返回信息说明
- - -

| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果

---
*** json示例 ***
```json
成功：
{
	"errCode":"200",
	"msg":"sucess",
}
失败：
{
  "errCode": 2016,
  "msg": "手机验证码错误"
}
```
### 4. 图形验证码请求

**接口地址**：/user/dealer/sendGraphCode

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/sendGraphCode

**接口备注**：图形验证码获取请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| deviceId | string | 是    | 设备ID   |

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| picUrl	   | string | 返回图形验证码地址|

##### 返回结果
---
*** json示例 ***
```json
成功：
{
	"errCode":"200",
	"msg":"sucess",
    "data":{
		"picUrl":"XXXX"
	}
}
```

### 5. 图形验证码验证

**接口地址**：/user/dealer/verifyPicCode

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/verifyPicCode

**接口备注**：实现图形验证码验证请求

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| deviceId | string | 是    | 设备ID   |
| picCode | string | 是 |用户输入的图形验证码|

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果
---

*** json示例 ***
```json
{
	"errCode":"200",
	"msg":"sucess",
}
```

### 6. 用户注册

**接口地址**：/user/dealer/register

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/register

**接口备注**：用户注册

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| deviceId | string | 是    | 设备Id |
| telephone| string | 是    | 手机号码  |
| phoneNum| string | 是    | 用户输入手机验证码 |
| picCode | string | 是    | 用户输入图形验证码  |
| password | string | 是    | 密码 |
##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| telephone	 | string | 电话号码 |
| nickName	 | string |  昵称   |
| favicon 	| string |  头像地址  |
| gender	 | int  |  性别  1:未知 1:男  2:女  3：第三性   |
| status 	| int | 认证状态  0:未认证 1:审核中  2:未通过 3:已通过  |

##### 返回结果
---

*** json示例 ***

```json
成功：
{
  "errCode": 200,
  "msg": "success",
  "data": {
    "telephone": "XXXX"，
    "nickName": "XXXX"，
    "favicon": "XXXX"，
    "gender": 2，
    "status": 0
  }
}
失败：
{
  "errCode": 2017,
  "msg": "密码不正确"
}
{
  "errCode": 2017,
  "msg": "用户不存在"
}
```

### 7. 重置密码

**接口地址**：/user/dealer/reset

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/reset

**接口备注**：用户重置密码

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| username | string | 是    | 账号|
| oldPS		| string | 是    | 旧密码  |
| newPS		| string | 是    | 新密码 |

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果
---

*** json示例 ***
```json
{
  "errCode": 200,
  "msg": "success"
}
```
### 8. 更换手机号码

**接口地址**：/user/dealer/editPhone

**返回参数**：json

**请求方式**：http  post

**请求示例**：https://api.dl.com/user/dealer/editPhone

**接口备注**：用户更换手机号码

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| username | string | 是    | 账号|
| telephone	| string | 是    |新手机号  |
| deviceId	| string | 是    | deviceId |
| phoneNum  | string | 是    | 用户新手机号输入的短信验证码  |

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果
---

*** json示例 ***
```json
{
  "errCode": 200,
  "msg": "success"
}
```
### 9. 编辑性别

**接口地址**：/user/dealer/editGender

**返回参数**：json

**请求方式**：http

**请求示例**：https://api.dl.com/user/dealer/editGender

**接口备注**：用户设置性别

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| username | string | 是    | 账号|
| gender	| string | 是    |0-未知  1-男  2-女 3-第三性  |

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果
---

*** json示例 ***
```json
{
  "errCode": 200,
  "msg": "success"
}
```
### 10. 设置昵称

**接口地址**：/user/dealer/editNickname

**返回参数**：json

**请求方式**：http

**请求示例**：https://api.dl.com/user/dealer/editNickname

**接口备注**：用户设置新的昵称

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| username | string | 是    | 账号|
| nickName	| string | 是    |新的昵称  |

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果
---

*** json示例 ***
```json
{
  "errCode": 200,
  "msg": "success"
}
```
### 11. 设置新的头像

**接口地址**：/user/dealer/editFavicon

**返回参数**：json

**请求方式**：http

**请求示例**：https://api.dl.com/user/dealer/editFavicon

**接口备注**：用户更换新的头像

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| username | string | 是    | 账号|
| favicon	| string | 是    |新头像地址 |

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|

##### 返回结果
---

*** json示例 ***
```json
{
  "errCode": 200,
  "msg": "success"
}
```
### 12. 上传头像

**接口地址**：/user/dealer/euploadFavicon

**返回参数**：json

**请求方式**：http

**请求示例**：https://api.dl.com/user/dealer/uploadFavicon

**接口备注**：用户上传新的头像

##### 请求参数说明

- - -

| 名称        | 类型          | 必填       | 说明       |
|:-----------:|:-------------:|:----------:|:--------:|
| file | File| 是    | 上传的头像文件|

##### 返回信息说明
- - -
| 名称        | 类型        | 说明       |
|:-------------:|:----------:|:--------:|
| errCode      | string | 数据请求状态   |
| msg 		   | string | 返回请求状态的消息|
| favicon 	   | string | 返回上传头像地址|
##### 返回结果
---

*** json示例 ***
```json
{
  "errCode": 200,
  "msg": "success",
  "data":{
  	"favicon":"XXXX"
  }
}
```





