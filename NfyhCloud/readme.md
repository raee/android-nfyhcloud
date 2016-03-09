#南方院后云服务安卓客户端项目说明
---

##包名说明
- `cn.rui.framework.ui` 通用的Toast,对话框，浏览器的UI实现。
- `cn.rui.framework.utils` 一些工具类，比如：获取手机信息、百度地图定位、拨打电话、日期处理、文本校验、上传工具、调用Soap的工具。
- `cn.rui.framework.widget` 自定义的CkeckBox，TabView
- `com.rae.alarm` 闹钟逻辑处理
- `com.rae.alarm.view` 闹钟界面实现辅助UI
- `com.yixin.nfyh.cloud` 应用程序、登录、主界面、设备连接、移动急救、QQ登录、上传图片、欢迎页、引导页、闹钟响铃页的界面交互视图。
- `com.yixin.nfyh.cloud.activity` 处理上诉以为的所有界面都在这里。
- `com.yixin.nfyh.cloud.adapter` ListView 用到的adapter存放在这里。
- `com.yixin.nfyh.cloud.bll` 帐号登录逻辑，平台切换控制，配置管理、急救悬浮框实现、照片分类处理、体征处理（体征上传、本地保存）

- `com.yixin.nfyh.cloud.bll.sign` 体征逻辑处理：个性化告警逻辑、体征数据库操作。
- `com.yixin.nfyh.cloud.data` 本地数据库操作，使用`DataFactory`来获取数据库的接口。
 
- `com.yixin.nfyh.cloud.device` 调用设备接口，处理设备蓝牙的回调，这里可以获取到设备传递过来的数据。
-  `com.yixin.nfyh.cloud.dialog` 自定义对话框
-   `com.yixin.nfyh.cloud.i` 本地定义的一些常用接口：通用授权接口、通用登录接口、通用照片分类接口、**消息推送接口**、 **体征上传接口**
-   `com.yixin.nfyh.cloud.model` 本地数据库的实体，跟 `com.yixin.nfyh.cloud.data`对应。**以及WebService 的实体。**

-`com.yixin.nfyh.cloud.model.view` 界面用到的少量交互实体，一般用于adapter中进行交互。

-`com.yixin.nfyh.cloud.receiver` 跌倒仪、消息推送广播接收处理。

-`com.yixin.nfyh.cloud.server` 跌倒监听服务，未处理的异常接收。
 
- `com.yixin.nfyh.cloud.service` **核心服务层** 蓝牙设备交互、消息推送。后台蓝牙数据传输核心服务层。
- `com.yixin.nfyh.cloud.sos` 移动急救流程逻辑实现。
- `com.yixin.nfyh.cloud.ui`、`com.yixin.nfyh.cloud.widget` 程序自定控件
- `com.yixin.nfyh.cloud.utils` 程序工具类
- `com.yixin.nfyh.cloud.w` **网站接口交互的核心层**这里实现调用网站的接口API
- `kankan.wheel.widget`、`kankan.wheel.widget.adapters` 体征界面里面用户滑动选择数值的第三方开源控件。


##平台切换说明
在`string_urls` 修改`platfrom`，取值：院后总平台、柯尔迈平台

	<?xml version="1.0" encoding="utf-8"?>
	<resources>

    <!-- 平台选择 ，取值：院后总平台、柯尔迈平台 -->
    <string name="platfrom">院后总平台</string>

    <!-- 院后总平台 -->
    <string name="company">广州南方宜信信息科技有限公司</string>
    <string name="nfyh_domain">http://nfyh.smu.edu.cn</string>
    <string name="url_health_assess">/RMBase/SysHealthAM/HealthReport.aspx</string>
    <string name="url_myddc">/RMBase/SysSurvey/SmsSurveyRecord.aspx</string>
    <string name="url_yjfk">/RMBase/SysFeedback/Feedback.aspx</string>
    <string name="url_bqpg">/RMBase/SysAssess/MyAssess_List.aspx?uid=@uid</string>
    <string name="soap_url">/WebService/Api.asmx</string>
    <string name="soap_namespace">http://nfyxHospital.org/</string>
    <string name="url_method_photo_list">/WebService/Data.ashx?action=getImage</string>
    <string name="url_method_photo_upload">/WebService/Data.ashx?action=uploadImage</string>

    <!-- 柯尔迈平台 -->
    <string name="company_kermatelmed">重庆科尔迈科技有限公司</string>
    <string name="kermatelmed_domain">http://nfyh.kermatelmed.com</string>

	</resources>
