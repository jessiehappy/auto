##### 1.小b首页获取经销商代金券请求

**接口地址**：/proxy/home

**返回参数**：json

**请求方式**：http post

**请求实例**：https://api.dl.com/proxy/home

**接口备注**：获取经销商代金券请求

###### 请求参数说明

- - -

| 名称		| 类型		| 必填		| 说明		|
|:-----------:|:-------------:|:----------:|:--------:|
| platform    | string     | 是    | 平台（ios:苹果，android:安卓）   |
| deviceId    | string     | 是    | 设备ID   |
| code    	  | String     | 是	  | 授权码   |
| telephone   | String 	   | 是    | 小b的电话号码|

######返回信息说明
- - -

| 名称		| 类型		| 说明		|
|:-----------:|:-------------:|:--------:|
| errCode    | Integer    | 数据请求状态码   |
| msg     	 | string     | 返回请求状态的信息   |
| code       | String     | 授权码   	 |
| seriesImg  | String     | 车系图片 	|
| seriesName | String	  | 车系名称  ：品牌-系列	|
| guidePrice | String	  | 车系指导价 ： 128.5-150.68  |
| commission | Number 	  | 佣金		3600  |
| couponQuota| String 	  | 代金券额度 如果未生成，则显示"未生成"，否则，显示代金券额度|

###### 返回结果
***json实例***
<pre>
<code>
	{
    "errCode": 200,
    "msg": "success",
    "data": {
        "code": "0982-2nndfjas-2313234123",
        "items": [
            {
            	"id":"10",
                "seriesImg": "https://api.dl.com/65ccdd9j.jpg",
                "seriesName": "东风雪铁龙-天逸",
                "guidePrice": "128.5-150.68",
                "commission": 3600,
                "couponQuota": 30
            },
            {
            	"id":"11",
                "seriesImg": "https://api.dl.com/dkjn22kjsc.jpg",
                "seriesName": "宝马-X5",
                "guidePrice": "128.5-150.6",
                "commission": 3600,
                "couponQuota": "未生成"
            }
		  ]
  	  }
	}
</code>
</pre>

##### 2. 小b从首页点击进到详情的请求

**接口地址**：/coupon/show/details

**返回参数**：json

**请求方式**：http post

**请求实例**：https://api.dl.com/coupon/show/details

**接口备注**：获取经销商代金券请求

###### 请求参数说明

- - -

| 名称		| 类型		| 必填		| 说明		|
|:-----------:|:-------------:|:----------:|:--------:|
| platform    | string     | 是    | 平台（ios:苹果，android:安卓）   |
| deviceId    | string     | 是    | 设备ID   |
| code    	  | String     | 是	  | 授权码   |
| id          | Number 	   | 是    | 经销商代金券主键|
| telephone   | String     | 是    | 小b的电话号码 |

######返回信息说明
- - -

| 名称		| 类型		| 说明		|
|:-----------:|:-------------:|:--------:|
| errCode    | Integer    | 数据请求状态码   |
| msg     	 | string     | 返回请求状态的信息   |
| code       | String     | 授权码   	 |
| img        | String     | 大图片 	|
| name		 | String	  | 名称 ：品牌-系列	|
| guidePrice | String	  | 指导价 ：128.5-150.6  |
| commission | Number 	  | 佣金		1500  |
| couponQuota| Number 	  | 代金券额度 如果未生成，该字段值为null，否则，显示代金券额度|
| couponNum  | Number	  | 代金券份数，是由经销商设定的 |
| offerDeadline| Number   | 代金券优惠截止日期 |
| dealerCompany| String   | 经销商公司名 |
| address      | String   | 经销商公司详细地址|

###### 返回结果
***json实例***
<pre>
<code>
	{
    	"errCode":200,
        "msg":"success",
        "data",{
        	"code":"0982-2nndfjas-2313234123",
            "img":"https://api.dl.com/65ccdd9j.jpg | https://api.dl.com/65ccdd9j.jpg",
            "name":"大众桑塔纳 2016款"
            "guidePrice":"128.5-150.6",
            "commission":1500,
            "couponQuota":300 或者 null,
            "couponNum":200,
            "offerDeadline":"2222-22-22",
            "dealerCompany":"青岛别克经销商",
            "address":"青岛市后新界区321"
        }
    }
</code>
</pre>

##### 3. 小b点击生成代金券请求

**接口地址**：/coupon/click/generate/coupon

**返回参数**：json

**请求方式**：http post

**请求实例**：https://api.dl.com/coupon/click/generate/coupon

**接口备注**：小b点击生成代金券请求

###### 请求参数说明

- - -

| 名称		| 类型		| 必填		| 说明		|
|:-----------:|:-------------:|:----------:|:--------:|
| platform    | string     | 是    | 平台（ios:苹果，android:安卓）   |
| deviceId    | string     | 是    | 设备ID   |
| code    	  | String     | 是	  | 授权码   |
| id          | Number 	   | 是    | 经销商代金券主键|

######返回信息说明
- - -

| 名称		| 类型		| 说明		|
|:-----------:|:-------------:|:--------:|
| errCode    | Integer    | 数据请求状态码   |
| msg     	 | string     | 返回请求状态的信息   |
| name       | String     | 名称 品牌 系列  |
| offerDeadline | String  | 优惠券截止时间 2017-10-31|
| commission | Number     | 佣金 为了设置额度时不能超过 佣金数|

###### 返回结果
***json实例***
<pre>
<code>
	{
    	"errCode":200,
        "msg":"success",
        "data",{
        	"name":"雷克萨斯四驱高配手动豪华版",
            "offerDeadline":"2017-10-31",
            "commission":1500
        }
    }
</code>
</pre>


##### 4. 小b点击确认，生成代金券请求
**接口地址**：/coupon/generate/coupon

**返回参数**：json

**请求方式**：http post

**请求实例**：https://api.dl.com/coupon/generate/coupon

**接口备注**：小b生成代金券请求
###### 请求参数说明

- - -

| 名称		| 类型		| 必填		| 说明		|
|:-----------:|:-------------:|:----------:|:--------:|
| platform    | string     | 是    | 平台（ios:苹果，android:安卓）   |
| deviceId    | string     | 是    | 设备ID   |
| id          | Number 	   | 是    | 经销商代金券主键|
| telephone   | String 	   | 是    | 小b的电话号码  |
| couponQuota | Number     | 是    | 小b设置的代金券额度|

######返回信息说明
- - -

| 名称		| 类型		| 说明		|
|:-----------:|:-------------:|:--------:|
| errCode    | Integer    | 数据请求状态码   |
| msg     	 | string     | 返回请求状态的信息   |
| data       | Number     | 返回是否成功的状态码null或者200 或者 2022(代金券生成失败)|

###### 返回结果
***json实例***
<pre>
<code>
	{
    	"errCode":200,
        "msg":"success",
        "data",null 或者 200 或者 2022
    }
</code>
</pre>

##### 5. 小b的代金券首页请求
**接口地址**：/coupon/home

**返回参数**：json

**请求方式**：http post

**请求实例**：https://api.dl.com/coupon/home

**接口备注**：小b的代金券首页请求

###### 请求参数说明

- - -

| 名称		| 类型		| 必填		| 说明		|
|:-----------:|:-------------:|:----------:|:--------:|
| platform    | string     | 是    | 平台（ios:苹果，android:安卓）   |
| deviceId    | string     | 是    | 设备ID   |
| telephone   | String 	   | 是    | 小b的电话号码，进行身份认证|

######返回信息说明
- - -

| 名称		| 类型		| 说明		|
|:-----------:|:-------------:|:--------:|
| errCode    | Integer    | 数据请求状态码   |
| msg     	 | string     | 返回请求状态的信息   |
| category   | String     | 类目名     |
| id         | Number     | 每个品牌的ID|
| imgUrl     | String     | 品牌图片的地址 |
| brandName  | String     | 品牌名称      |

###### 返回结果
***json实例***
<pre>
<code>
{
    "errCode": 200,
    "msg": "success",
    "data": [
        {
            "category": "进口车",
            "items": [
                {
                    "id": 23,
                    "imgUrl": "",
                    "brandName": "宝马"
                },
                {
                    "id": 24,
                    "imgUrl": "",
                    "brandName": "宾利"
                }
            ]
        },
        {
            "category": "国产车",
            "items": [
                {
                    "id": 23,
                    "imgUrl": "",
                    "brandName": "比亚迪"
                },
                {
                    "id": 24,
                    "imgUrl": "",
                    "brandName": "上海大众"
                }
            ]
        }
    ]
}
</code>
</pre>