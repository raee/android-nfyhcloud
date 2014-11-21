
-- 初始化 设备
delete from devices;
insert into devices(devid,sdk,name, isused,logo,devicename,devicepin,comment)values('Mindray-H900',0,'迈瑞H900',0,'logo_mindray','mindray-ubicare','4321','同迈瑞UbeConn，但蓝牙名字（mindray-ubicare）。点击设备上的开始按钮进行血压测量。');
insert into devices(devid,sdk,name, isused,logo,devicename,devicepin,comment)values('UbeConn-Multi',0,'迈瑞UbeConn',1,'logo_mindray','UbeConn-Multi','4321','同迈瑞mindray-ubicare，但蓝牙名字（UbeConn-Multin）。点击设备上的开始按钮进行血压测量。');
insert into devices(devid,sdk,name, isused,logo,devicename,devicepin,comment)values('HEM-7081-IT',1,'欧姆龙设备',0,'logo_omron','HEM-7081-IT','','欧姆龙血压仪，按住[上传]进行蓝牙配对。<br>多次连接不上请取消配对，并关闭蓝牙，并开始重新连接，重新连接时需要重新配对。');
insert into devices(devid,sdk,name, isused,logo,devicename,devicepin,comment)values('TZ100',2,'马纳特血糖仪',0,'logo_manette','TZ100','1234','请开始测量血糖，测量完毕点击连接并按一下血糖仪上的M键。');


-- 初始化 字典表
delete from dicts;
insert into dicts(name,code_name,dic_value,comment)values('KEY_AUTO_TIPS','KEY_AUTO_TIPS','false',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_AUTO_CONNECTED','KEY_AUTO_CONNECTED','false',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_ENABLE_FALL','KEY_ENABLE_FALL','false',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_ENABLE_DESKTOP','KEY_ENABLE_DESKTOP','false',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_ENABLE_TIXING','KEY_ENABLE_TIXING','true',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_ENABLE_PULLMSG','KEY_ENABLE_PULLMSG','true',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_AUTO_UPLOAD','KEY_AUTO_UPLOAD','true',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_PHONE_NUMBER','KEY_PHONE_NUMBER','15692015603',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_DESKTOP_EVENT_LIST','KEY_DESKTOP_EVENT_LIST','旧病复发了',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_DESKTOP_EVENT_LIST','KEY_DESKTOP_EVENT_LIST','摔倒了',NULL);
insert into dicts(name,code_name,dic_value,comment)values('KEY_DESKTOP_EVENT_LIST','KEY_DESKTOP_EVENT_LIST','其他',NULL);

-- 体征类型
delete from sign_types;
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('1000','血压','mmHg',NULL,'ic_xueya',1,1,0,80);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('1001','收缩压','mmHg','1000','ic_xueya',1,1,1,80);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('1002','舒张压','mmHg','1000','ic_xueya',1,2,1,96);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('1003','脉搏','次/分','1000','ic_xueya',1,3,1,80);

insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('2000','血糖','mmol',NULL,'ic_xuetang',1,2,0,4.5);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('2001','血糖','mmol','2000','ic_xuetang',2,1,1,4.5);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('2002','测量类型','','2000','ic_default_item',-1,2,0,'微量血糖测定');

insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('3000','血氧','ml',NULL,'ic_xueyang',1,3,0,90);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('3001','血氧','ml','3000','ic_xueyang',1,1,1,90);

insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('4000','体温','℃',NULL,'ic_tiwen',1,4,0,37.5);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('4001','体温','℃','4000','ic_tiwen',2,1,1,37.5);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('4002','测量位置','','4000','ic_tiwen',-1,1,0,"腋下");

insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('5000','呼吸峰速','PEF',NULL,'ic_tizhong',1,5,0,60);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('5001','呼吸峰速',' L/Min','5000','ic_tizhong',1,1,1,120);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('5002','测量时间','','5000','ic_default_item',-1,1,0,'早');

insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('6000','心跳','次/分',NULL,'ic_xintiao',1,6,0,80);
insert into sign_types(typeid,name,type_unit,p_typeid,type_icon,data_type,order_id,is_sign,default_value)values('6001','心跳','次/分','6000','ic_xintiao',1,1,1,90);


-- 体征范围
delete from sign_range;
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(1,'1001',30,240,0,'收缩压取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(2,'1002',60,180,0,'舒张压取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(3,'1003',50,120,0,'脉搏取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(4,'2001',1.0,12.0,0,'血糖取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(5,'3001',0,100,0,'血氧取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(6,'4001',0,50,0,'体温取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(7,'5001',60,850,0,'呼吸峰速取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(8,'6001',0,120,0,'心跳取值范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,range_arr,name) values(9,'2002',0,0,0,'["胰岛素治疗","微量血糖测定"]','血糖类型');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,range_arr,name) values(10,'4002',0,0,0,'["腋下","口腔"]','体温位置');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,range_arr,name) values(11,'5002',0,0,0,'["早","中","晚"]','测量时间');

-- 收缩压范围
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(12,'1001',0,120,1,'理想状态的收缩压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(13,'1001',121,129,1,'正常状态的收缩压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(14,'1001',130,139,2,'高正常高血压状态的收缩压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(15,'1001',140,159,2,'一级高血压状态的收缩压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(16,'1001',160,179,3,'二级高血压状态的收缩压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(17,'1001',179,300,4,'三级高血压状态的收缩压');

-- 舒张压范围
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(18,'1002',0,80,1,'理想状态的舒张压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(19,'1002',80,84,1,'正常状态的舒张压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(20,'1002',85,89,2,'高正常血压状态的舒张压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(21,'1002',90,99,3,'一级高血压状态的舒张压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(22,'1002',100,109,4,'二级高血压状态的舒张压');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(23,'1002',110,300,4,'三级高血压状态的舒张压');

-- 血糖范围
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(24,'2001',9.5,9.5,1,'入院血糖');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(25,'2001',6.1,6.1,1,'出院血糖');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(26,'2001',8.0,8.0,1,'临床血糖');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(27,'2001',6.1,6.1,4,'糖尿病空腹状态正常血糖');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(28,'2001',10.0,10.0,1,'餐后2小时正常血糖');

insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(29,'1003',50,120,0,'正常脉搏范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(30,'3001',50,100,0,'正常血氧范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(31,'6001',0,120,0,'心跳正常范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(32,'4001',0,50,0,'体温正常范围');
insert into sign_range(rangeid,_SignTypes_typeId,left_range,right_range,range_type,name) values(33,'5001',40,120,0,'呼吸峰速正常范围');

-- 体征提示
delete from sign_tips;
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(11,1,-1,'您的收缩压理想',5);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(12,1,-1,'您的收缩压正常，请继续保持！',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(13,1,-1,'您的收缩压有点高了，是不是测量的时候激动了，请保持平静再测一次吧，如果还是依旧，请注意日常的生活饮食',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(14,1,-1,'您的收缩压真的挺高，如果延续下去，很有可能导致高血压，请务必注意日常的生活饮食，多运动！',7);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(15,1,-1,'您的收缩压已经达到二级高血压的状态，建议您找医生咨询！',8);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(16,1,-1,'警告！！警告！！！您的收缩压严重异常！！强烈建议您入院检查！！',9);

insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(17,1,-1,'您的舒张压很理想，请继续保持！',5);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(18,1,-1,'您的舒张压非常好！，请继续保持！',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(19,1,-1,'您的舒张压正常，请继续保持！',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(19,1,-1,'您的舒张压有点高了，是不是测量的时候激动了，请保持平静再测一次吧，如果还是依旧，请注意日常的生活饮食',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(21,1,-1,'您的舒张压真的挺高，如果延续下去，很有可能导致高血压，请务必注意日常的生活饮食，多运动！',7);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(22,1,-1,'您的舒张压已经达到二级高血压的状态，建议您找医生咨询！',8);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(23,1,-1,'警告！！警告！！！您的舒张压严重异常！！强烈建议您入院检查！！',9);

insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(24,0,0,'警告！！您的血糖比入院血糖还高，强烈建议您入院进行检查！',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(24,1,1,'您比入院血糖低了哦，请保持！',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(25,0,0,'您比出院时的血糖高了，请注意饮食健康，平淡饮食。',6);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(26,1,1,'您比出院时的血糖还低，真棒！请保持！',7);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(27,0,0,'您比临床血糖高了，请注意，如果发展下去则血压入院检查了~~',8);

insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(29,1,-1,'脉搏正常！',5);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(30,1,-1,'血氧正常！',5);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(31,1,-1,'心跳正常！',5);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(32,1,-1,'体温正常！',5);
insert into sign_tips(_signrange_rangeid,tips_symbol,tips_value,tips_comment,tips_level)values(33,1,-1,'呼吸峰速正常！',5);