use master;

EXEC sp_configure 'show advanced options', 1;
	GO
	--更新配置；
	RECONFIGURE;
	GO
	--启用xp_cmdshell存储过程；
	EXEC sp_configure 'xp_cmdshell', 1;
	GO
	--更新配置；
	RECONFIGURE;
	GO

	--调用系统存储过程，新建E:\SEC目录；
	EXEC xp_cmdshell 'mkdir E:\SQL\千里之行购物系统', NO_OUTPUT
	GO
/*创建文件夹代码结束*/
/*若当前系统中存在HR表，则删除之；*/
IF EXISTS (SELECT * FROM sysdatabases WHERE name = 'shoppingDB')
BEGIN
	DROP DATABASE shoppingDB;
END
GO
/*创建数据库HR；*/
CREATE DATABASE shoppingDB
ON 
(
	NAME = 'shoppingDB_data',
	FILENAME = 'E:\SQL\千里之行购物系统\shoppingDB_data.mdf',
	SIZE = 5 MB,
	FILEGROWTH = 20%
)
LOG ON 
(
	NAME = 'shoppingDB_log', 
	FILENAME = 'E:\SQL\千里之行购物系统\shoppingDB_log.ldf',
	SIZE = 1MB, 
	MAXSIZE = 20MB,
	FILEGROWTH = 1
);
GO
 
use shoppingDB
go

--用户信息表
create table userInfo
(
	id			int			not null	identity(1,1),--用户编号
	username	varchar(50)	not null,--用户名称
	userpwd		varchar(50)	not null,--用户密码
)
go

insert into userInfo values('admin','123456');


alter table userInfo
	--主键约束 用户编号为主键约束
	add constraint pk_userInfo_id primary key(id),
	--唯一约束 用户名称为唯一约束
	constraint uq_userInfo_username unique(username),
	--检查约束 用户密码长度大于等于6位
	constraint ch_userInfo_userpwd check (len(userpwd) >= 6)
go

--公告信息表
create table bulletininfo
(
	id			int				not null	identity(1,1),--公告编号
	title		varchar(100)	not null,--公告标题
	content		text			not null,--公告内容
	userid		int				not null,--发布者
	createtime	datetime		not null,--发布时间
)
go

alter table bulletininfo
	--主键约束 公告编号为主键
	add constraint pk_bulletininfo_id	primary key(id),
	--外键约束 外键userid(发布者)引用主键id(用户编号)
	constraint fk_bulletininfo_title foreign key(userid) references userInfo(id),
	--默认约束 发布时间默认为当前时间
	constraint df_bulletininfo_createtime default (getdate()) for createtime
go	

--客户信息表
create table customerinfo
(
	id				int				not null	identity(1,1),--客户编号
	email			varchar(100)	not null,--电子邮箱（客户账户名）
	pwd				varchar(20)		not null,--密码
	registertime	datetime		not null,--注册时间
)
go

alter table customerinfo
	--主键约束 客户编号为主键
	add constraint pk_customerinfo_id	primary key(id),
	--检查约束 电子邮箱格式必须合法（包含@，@前后必须有符号，@后必须包含点，点前后必须有符号）
	constraint ch_customerinfo_email check(email like '%_@%_.%_'),
	--唯一约束 电子邮箱为唯一
	constraint uq_customerinfo_email unique(email),
	--检查约束 密码长度大于等于6位
	constraint ch_customerinfo_pwd check(len(pwd) >= 6),
	--默认约束 注册时间默认为当前时间
	constraint df_customerinfo_registertime default(getdate()) for registertime
go


--客户详细信息表
create table CustomerDetailInfo
(
	customerid		int			not null,--客户编号
	name			varchar(50)	not null,--收货人姓名
	telphone		varchar(20) null,--固定电话
	mobiletelephone	varchar(20) null,--移动电话
	address			varchar(100)	not null,--收货地址
)
go


alter table CustomerDetailInfo
	--外键约束 外键表CustomerDetailInfo列名为customerid(客户编号)引用主键表customerinfo列名为id(客户编号)
	add constraint fk_CustomerDetailInfo_customerid foreign key (customerid) references customerinfo(id),
	--检查约束 固定电话格式为xxxx-xxxxxxxx
	constraint ch_CustomerDetailInfo_telphone check(telphone like '____-________'),
	--检查约束 移动电话必须为11位数字
	constraint ch_CustomerDetailInfo_mobiletelephone check(len(mobiletelephone) = 11)
go

--商品类别
create table goodstype
(
	typeid		int		not null	identity(1,1),--类别编号
	typename	varchar(20) not null,--类别名称
)
go

alter table goodstype
	--主键约束
	add constraint pk_goodstype_typeid primary key(typeid),
	--唯一约束
	constraint uq_goodstype_typename unique(typename)
go

--商品信息表
create table goodsinfo
(
	goodsid		int				not null	identity(1,1),--商品编号
	typeid		int				not null,--商品类别
	goodsname	varchar(50)		not null,--商品名称
	price		decimal(8,2)	not null,--商品价格
	discount	float			not null,--折扣
	isnew		int				not null,--是否新品
	isrecommend	int				not null,--是否推荐
	status		int				not null,--商品状态
	photo		varchar(50)		null,--商品图片
	remark		varchar(200)	null,--备注
	detailed   text null
)
go
update goodsinfo set detailed = null where goodsid = 89 
select a.goodsid,a.goodsname,a.price ,a.isnew,a.isrecommend,a.status,a.discount ,b.typename , a.remark , a.photo , a.detailed from goodsinfo a left join  goodstype b on a.typeid = b.typeid where a.goodsid = 89
select * from goodsinfo
alter table goodsinfo
	--主键约束 商品编号为主键
	add constraint pk_goodsinfo_goodsid primary key(goodsid),
	--外键约束 外键表goodsinfo列名为typeid(商品类别)引用主键表goodstype列名为typeid(商品类别)
	constraint fk_goodsinfo_typeid foreign key(typeid) references goodstype(typeid),
	--默认约束 默认商品价格为10.0
	constraint df_goodsinfo_discount default(10.0) for discount,
	--检查约束 是否新品（0新品，1不是新品）
	constraint ch_goodsinfo_isnew check(isnew in(0,1)),
	--检查约束 是否推荐（0推荐，1不推荐）
	constraint ch_goodsinfo_isrecommend check(isrecommend in (0,1)),
	--检查约束 商品状态（0上架，1下架）
	constraint ch_goodsinfo_status check(status in (0,1))
go


--订单信息表
create table orderinfo
(
	orderid		int		not null	identity(1,1),--订单编号
	customerid	int		not null,--客户编号
	status		int		not null,--订单状态
	ordertime	datetime not null,--下订单时间
)
go

alter table orderinfo
	--主键约束 订单编号为主键
	add constraint pk_orderinfo_orderid	primary key(orderid),
	--外键约束 外键表orderinfo列名为外键表customerid(客户编号)引用主键表customerinfo列名为id(客户编号)
	constraint fk_orderinfo_customerid foreign key(customerid) references customerinfo(id),
	--[0表示未付款][1用户付款成功][2商家已发货][3用户确认收货][4用户评价——》交易成功]
	
	--检查约束 订单状态（0未确定，1确定,）
	constraint ch_orderinfo_status check(status in (0,1,2,3,4)),
	--默认约束 下订单时间为默认当前系统时间
	constraint df_orderinfo_ordertime default(getdate()) for ordertime
go

delete from orderinfo where orderid = 61
update orderinfo set status = 2  where orderid = 60
select * from orderinfo

--订单商品信息表
create table ordergoodsinfo
(
	orderid		int		not null,--订单编号
	goodsid		int		not null,--商品编号
	quantity	int		not null,--商品数量
)
go

alter table ordergoodsinfo
	--联合主键约束 订单编号，商品编号为主键
	add constraint pk_ordergoodsinfo_orderid_goodsid primary key(orderid,goodsid),
	--检查约束 商品数量大于0
	constraint ch_ordergoodsinfo_quantity check(quantity > 0),
	--默认约束 商品数量默认为1
	constraint df_ordergoodsinfo_quantity default(1) for quantity,
	
	constraint fk_ordergoodsinfo_orderid foreign key(orderid) references orderinfo(orderid),
	
	constraint fk_ordergoodsinfo_goodsid foreign key(goodsid) references goodsinfo(goodsid)
go

select * from ordergoodsinfo

--商品留言表
create table Review
(
	--留言编号
	id int primary key identity(1,1) not null,
	--用户编号
	customerId int not null,
	--商品编号
	goodid int not null,
	--订单编号
	orderId int not null,
	--发言时间
	reviewTime datetime  default(getDate()) not null,
	--评价（1差评,2一般,3好评）
 	reviewStatus int  default(3) not null check(reviewStatus in(1,2,3)),
	--留言内容（表情，文字，图片）  
	reviewContent text default(null) null
)
go
select * from Review a left join customerinfo b on a.customerId = b.id where a.goodid= 94

alter table Review
	--留言商品
	add constraint fk_Review_goodsinfo foreign key(goodid) references goodsinfo(goodsid)
go	
alter table Review
	--留言用户
	add constraint fk_Review_customerinfo foreign key(customerId) references customerinfo(id)
alter table Review
	--留言订单
	add constraint fk_Review_orderinfo foreign key(orderId) references orderinfo(orderid)



--插入用户信息

insert into userInfo values ('zhangsan' ,123456)

insert into userInfo values ('lisi' ,123456)

select* from userInfo

--插入公告信息
insert into bulletininfo 
	values ('测试数据-公告标题1' ,'测试数据-公告内容1',1,GETDATE())

insert into bulletininfo 
	values ('测试数据-公告标题2' ,'测试数据-公告内容2',1,GETDATE())

insert into bulletininfo 
	values ('测试数据-公告标题3' ,'测试数据-公告内容3',1,GETDATE())

insert into bulletininfo 
	values ('测试数据-公告标题4' ,'测试数据-公告内容4',1,GETDATE())

insert into bulletininfo 
	values ('测试数据-公告标题5' ,'测试数据-公告内容5',1,GETDATE())

insert into bulletininfo 
	values ('测试数据-公告标题6' ,'测试数据-公告内容6',1,'2009-07-22 10:25:08.800')

select * from bulletininfo

--插入客户信息
insert into customerinfo  
	values ('a@sina.com' ,'123456','2009-06-06 12:30:45.000')

insert into customerinfo  
	values ('b@sina.com' ,'123456','2009-06-06 12:30:45.000')

insert into customerinfo  
	values ('c@sina.com' ,'123456','2009-06-06 12:30:45.000')

insert into customerinfo  
	values ('d@sina.com' ,'123456','2009-06-06 12:30:45.000')
	
select * from customerinfo 

--插入客户详细信息
insert into CustomerDetailInfo  
	values (1,'张三' ,'0001-32456754','13534563234','重庆市万州区国本路1号')

insert into CustomerDetailInfo  
	values (2,'李斯' ,'0002-32456754','13534563234','重庆市沙坪坝区沙杨路14号')

insert into CustomerDetailInfo  
	values (3,'王五' ,'0003-32456754','13534563234','重庆市沙坪坝区汉渝路22号')

select * from CustomerDetailInfo 
--插入商品类别
insert into goodstype   
	values ('野营帐篷')

insert into goodstype   
	values ('睡袋垫子')

insert into goodstype   
	values ('户外桌子')

insert into goodstype   
	values ('运动手表')

insert into goodstype   
	values ('野营用品')

insert into goodstype   
	values ('登山攀岩器材')

insert into goodstype   
	values ('户外服装')
	
select * from goodstype  

select * from goodsinfo
--插入商品信息
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1182 双人双层帐篷','498',1,1,0,'zhangpeng1.jpg','双人双层帐 (')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'ND-1133 三人双门帐篷','256','9.5',0,1,0,'zhangpeng2.jpg','三人双层帐 (')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'ND-1132 三人帐篷。。。','296','6.7',1,0,0,'zhangpeng3.jpg','三人双层帐 (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1136 四人双层帐篷','438',0,1,0,'zhangpeng4.jpg','四人双层帐 (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1121 双人双层帐篷','186',1,1,0,'zhangpeng5.jpg','双人双层帐 (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1009 六角纱网帐篷','680',1,0,0,'zhangpeng6.jpg','六角纱网帐 (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'MOBI GARDEN牧高笛','642',1,0,0,'zhangpeng7.jpg','双层自动2秒帐篷')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'多人双层铝杆帐篷','846',1,1,0,'zhangpeng8.jpg','双层自动2秒帐篷')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'冷山3人双层铝杆帐篷','957',1,1,0,'zhangpeng9.jpg','双层自动2秒帐篷')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'山籁3人双层铝杆帐篷','654','6.7',0,0,0,'zhangpeng10.jpg','双层自动2秒帐篷')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'高科技棒','654','6.7',0,0,0,'zhangpeng1.jpg','双层自动2秒帐篷')

insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'ND-1372 蛋巢双层...','198','8.5',1,1,0,'shuidai1.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'ND-1255L 信封睡...','146','9.5',0,1,0,'shuidai2.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'ND-1202 羽绒妈咪...','296','6.7',1,0,0,'shuidai2.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(2,'爱非碧尔斯500 羽','438',0,1,0,'shuidai5.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(2,'CHAMONIX334SOFT睡袋','186',1,1,0,'shuidai5.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(2,'CHAMONIX265超小睡袋','267',1,0,0,'shuidai6.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'highrockS106107...','467','6.5',1,0,0,'shuidai7.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'highrockS4543 萤火虫','456','8.8',1,1,0,'shuidai8.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(3,'Featherf4005 木..','198','8.5',1,1,0,'shuidai9.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(3,'Featherf4007 蓝色','146','9.5',0,0,0,'shuihu1.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'MAINNAV950DM 高..','298','8.5',1,1,0,'shuihu2.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(4,'MAINNAV950D GPS户','286',0,0,0,'shuihu3.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'SUUNTO D6 潜水系列..','358','8.5',1,1,0,'shuihu4.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'SUNNTOt 3C Black..','434','9.5',0,1,0,'shuihu5.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'SUNNTOt T3C BLACK..','499','8.5',1,1,0,'shuihu6.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'驱动之星A77心率表','564','9.5',0,1,0,'toudeng1.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(4,'风尚之星A77高精度表','532',1,0,0,'biao4.jpg','时尚系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(4,'激尚之星A77高精度表','342',0,1,0,'biao5.jpg','时尚系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-8629 背后防雨罩XL','198','8.5',1,1,0,'bao1.jpg','60L-80L')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-8627 背后防雨罩M','186',0,1,0,'bao2.jpg','25L-40L')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-8626 背后防雨罩S','8.5',1,1,0,'bao3.jpg','25L以下')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-2129 小号挎包','121','8.5',0,1,0,'kuabao1.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-2125 腰包','43',1,0,0,'jijiubao1.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-3211 腰包','34','9.5',0,1,0,'jijiubao1.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-3210 急救包','59','9.5',0,1,0,'jijiubao2.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-2315 护脖','55','9.5',0,0,0,'maojing.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'Featherf201 遮阳伞','532',1,1,0,'zheyangsan.jpg','遮阳伞')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3565 3+5LED头灯','89',0,1,0,'toudeng1.jpg','头灯')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3566 2+1X头灯','68',0,1,0,'toudeng2.jpg','头灯')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-3565 3+5LED头灯','92','6.5',0,1,0,'toudeng3.jpg','头灯')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3613 1X手电(U40)','55',1,0,0,'shoudian1.jpg','手电')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3612 3LED手电...','58',0,1,0,'shoudian2.jpg','手电')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'D-3611 1X手电(N30)','34','7.7',0,1,0,'shoudian3.jpg','手电')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1781 000...','158','8.5',1,1,0,'shoudian4.jpg','MINI 8 FIG...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1772 138...','186',0,1,0,'xie1.jpg','PETIT 8 FI...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1813 153...','68',1,1,0,'xie2.jpg','登山系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1813 213...','84','9.5',0,1,0,'xie3.jpg','登山系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1813 234...','99','3.5',1,1,0,'xie4.jpg','登山系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(7,'snowwolf女款步行...','298','8.5',1,1,0,'yi1.jpg','MINI 8 FIG...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(7,'snowwolf男款步行...','286',0,1,0,'yi2.jpg','PETIT 8 FI...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(7,'snowwolf女款薰衣...','358',1,1,0,'yi3.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(7,'GARMONT ZEPHYR4...','265','9.5',0,1,0,'xie2.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(7,'GARMONT ZEPHYR5...','258','8.5',1,1,0,'xie3.jpg','野营系列')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(7,'GARMONT Syncro...','199',1,1,0,'xie4.jpg','野营系列')select * from goodsinfo


--插入订单信息表
insert into orderinfo   
	values (1,0,'2009-07-22 10:25:08.690')
	
insert into orderinfo   
	values (2,0,'2009-07-22 10:25:08.690')
	
insert into orderinfo   
	values (3,0,'2009-07-22 10:25:08.690')
	
select * from orderinfo 

--插入订单商品信息
insert into ordergoodsinfo    
	values (1,44,10)
	
insert into ordergoodsinfo   
	values (1,46,16)
	
insert into ordergoodsinfo   
	values (2,8,2)
	
insert into ordergoodsinfo   
	values (2,14,1)
	
insert into ordergoodsinfo   
	values (3,8,1)
	
insert into ordergoodsinfo   
	values (3,26,1)
	
select * from ordergoodsinfo

--插入一条公告信息
insert into bulletininfo values('国庆中秋送礼','为庆祝建国60周年，在国庆期间，千里之行全部8折优惠',2,default)


----判断商品类型是否相应的商品
--if not exists(select 1 from goodstype where typeid = 1)
--	begin
--		print '删除失败，这个商品类型还是相应的商品!'
--	end
--else
--	begin
--		--delete from goodstype where typeid = 1
--		print '删除商品类型成功'
--	end


----修改"ND-1133 三人双层账..."这个商品现在是下架状态，现在要使这个商品上架
--update goodsinfo set status = 1 where goodsid = 2


----3、查询所有客户信息
----判断是否存在该视图
--if exists(select 1 from sysobjects where name = 'view_querycustomer')
--	drop view view_querycustomer
--go
----创建视图
--create view view_querycustomer
--as
--	select a.id as '客户编号',b.name as '收货人姓名',b.telphone as '固定电话',
--	b.mobiletelephone as '移动电话',a.email as '电子邮箱',b.address as '收货地址',
--	a.registertime as '注册时间',a.pwd as '密码' 
--	from customerinfo a join CustomerDetailInfo b on a.id = b.customerid
--go

----调用视图
--select * from view_querycustomer

----5、查询所有订单的基本信息
----判断是否存在该视图
--if exists(select 1 from sysobjects where name = 'view_queryorderinfo')
--	drop view view_queryorderinfo
--go
----创建视图
--create view view_queryorderinfo
--as
--	select c.orderid as '订单编号',
--	case c.status when 0 then '未确定'
--	else '确定'
--	end as '订单状态' ,
--	ordertime as '下单时间',email as '客户账户/邮箱',name as '收货人',
--	telphone as '固定电话',mobiletelephone as '移动电话'
--	from customerinfo a join CustomerDetailInfo b on a.id = b.customerid
--	join orderinfo c on c.orderid = a.id 
--go
----调用视图
--select * from view_queryorderinfo


----6、订单相关的商品信息

----判断是否存在该视图
--if exists(select 1 from sysobjects where name = 'view_queryorderdetail')
--	drop view view_queryorderdetail
--go
----创建视图
--create view view_queryorderdetail
--as 	
--	select b.goodsid as '商品编号',b.typeid as '商品类别',b.goodsname as '商品名称',
--	b.price as '商品价格',b.discount as '折扣',c.quantity as '订购数量',b.price*b.discount/10*c.quantity  as '小计'
--	from goodstype a join goodsinfo b on a.typeid=b.typeid 
--					 join ordergoodsinfo c on c.goodsid = b.goodsid 
--go
----调用视图
--select * from  view_queryorderdetail

----订单总金额
--select SUM(price*discount/10*quantity) as '订单总金额' from (select b.price,b.discount ,c.quantity 
--from goodstype a join goodsinfo b on a.typeid=b.typeid 
--				 join ordergoodsinfo c on c.goodsid = b.goodsid)as order_sum


----求出客户账号为"a@sina.com"的客户编号
--declare @customerid int--存储客户编号
--set @customerid = 1
----插入新订单记录
--select * from orderinfo
--insert into orderinfo values(@customerid ,5,default)
----根据表1-10，插入订购的商品
--declare @orderid int--存储订单编号
--select @orderid = @@IDENTITY --得到刚插入的订单的编号
--insert into ordergoodsinfo values(@orderid ,29,435)

--insert into ordergoodsinfo values (@orderid ,30,3)

--insert into ordergoodsinfo values (@orderid ,31,56)

--insert into ordergoodsinfo values (@orderid ,32,2)

--insert into ordergoodsinfo values (@orderid ,33,45)

--insert into ordergoodsinfo values (@orderid ,34,32)

--insert into ordergoodsinfo values (@orderid ,35,67)

--insert into ordergoodsinfo values (@orderid ,36,27)

--insert into ordergoodsinfo values (@orderid ,37,1)

--insert into ordergoodsinfo values (@orderid ,38,8)

--insert into ordergoodsinfo values (@orderid ,39,65)

--insert into ordergoodsinfo values (@orderid ,40,43)

--insert into ordergoodsinfo values (@orderid ,41,123)

--insert into ordergoodsinfo values (@orderid ,42,10)

--insert into ordergoodsinfo values (@orderid ,43,11)
--go


----8、商品销售排行
----判断是否存在该视图
--if exists(select 1 from sysobjects where name = 'view_orderbyquantity')
--	drop view view_orderbyquantity
--go
----创建视图
--create view view_orderbyquantity
--as
--	select top 3 a.goodsid as '商品编号', a.goodsname as '商品名称', sum(c.quantity) as '销售数量'
--	from goodsinfo a join ordergoodsinfo c on a.goodsid = c.goodsid 
--	group by a.goodsid,a.goodsname order by sum(c.quantity) desc
--go

--select * from view_orderbyquantity

----判断如果索引存在则先删除索引。
--if exists(select 1 from sysindexes where name = 'index_orderinfo_customerid')
--	drop index orderinfo.index_orderinfo_customerid
--go
----创建非聚集索引
--create nonclustered index index_orderinfo_customerid
--on orderinfo(customerid)
--with
--	fillfactor = 40
--go
----调用索引
--select * from orderinfo with(index=index_orderinfo_customerid) 


----判断是否存在该触发器
--if exists(select 1 from sysobjects where name ='tr_delete_customerinfo')
--	drop trigger tr_delete_customerinfo--删除触发器
--go
----创建触发器
--create trigger tr_delete_customerinfo
--on customerinfo
--instead of delete 
--as
--	--定义变量存储用户编号
--	declare @customerid int
--	--变量添加用户编号
--	select @customerid = id from deleted
	
--	--判断用户是否有订单
--	if exists(select 1 from orderinfo where customerid = @customerid)
--		begin
--			print '此用户有订单，是优质客户，不能删除!'
--		end
--	else 
--		begin
--			--删除客户详细信息表
--			delete from customerdetailinfo where customerid = @customerid
--			--订单信息表
--			delete from orderinfo where customerid = @customerid
--			--客户信息表
--			delete from customerinfo where id = @customerid
--			print '用户删除成功!'			
--		end
--go		
----测试触发器
--delete from customerinfo where email = 'a@sina.com'


----创建存储过程并测试

----判断存储过程是否存在
--if exists(select 1 from sysobjects where name = 'proc_queryorderinfo')
--	drop procedure proc_queryorderinfo
--go
----创建存储过程
--create procedure proc_queryorderinfo
--	@orderinput varchar(50) = ''--存储过程参数，用于接收订单编号或客户账号
--as	
--begin
--	declare @sql varchar(100) --用于存储拼接的sql语句
	
--	set @sql = 'select * from view_queryorderinfo where 订单编号 like ''%'+@orderinput+'%''or [客户账户/邮箱] like ''%'+@orderinput+'%'''
	
--	exec (@sql)--执行字符串所对应的sql语句
--end
----调用存储过程
--exec proc_queryorderinfo 1


----判断是否存在该存储过程
--if exists(select 1 from sysobjects where name = 'proc_inssertorder')
--	drop procedure proc_inssertorder
--go
----创建存储过程
--create procedure proc_inssertorder
--	--@email varchar(50),--下订单的客户账号
--	@customerid int,--客户编号
--	@goodsid	int,--商品编号
--	@quantity	int--购买的商品数量
--as
--	--判断该商品编号是否存在
--	if exists(select 1 from goodsinfo where goodsid = @goodsid )
--	begin
--		begin transaction--开始事务
		
--		declare @sumerrors int--存储错误总和
--		set @sumerrors = 0
		
--		--求出客户账号的客户编号
--		--declare @customerid int--存储客户编号
--		--select @customerid = id from customerinfo where email = @email
--		--set @sumerrors = @@error
		
--		--插入新订单记录
--		insert into orderinfo values (@customerid,0,getdate())
--		set @sumerrors += @@error
		
--		declare @orderid int--存储订单编号
--		select @orderid = @@identity--得到刚插入的订单的编号
--		insert into ordergoodsinfo values(@orderid,@goodsid,@quantity)
--		set @sumerrors += @@error
		
--		--判断错误是否不等于0
--		if(@sumerrors <> 0)
--			begin
--				print 'false'
--				rollback transaction--回滚事务
--			end
--		else
--			begin
--				print 'true'
--				commit transaction--提交事务
--			end
--	end
--	else
--		begin
--			print '不存在该商品编号'
--		end

----调用存储过程
--exec proc_inssertorder 1,300,100


--insert into orderinfo values (1,0,getdate()) 
--select  @@identity as 'id'

----删除订单信息
--if exists(select 1 from sysobjects where name = 'proc_orderdelete')
--	drop procedure proc_orderdelete
--go
----创建存储过程
--create procedure proc_orderdelete

--	@orderid int--订单编号
--as
--	--判断该订单编号是否存在
--	if exists(select 1 from orderinfo where orderid = @orderid )
--	begin
--		begin transaction--开始事务
		
--		declare @sumerrors int--存储错误总和
--		set @sumerrors = 0
		
--		--删除订单商品记录
--		delete from ordergoodsinfo where orderid = @orderid
--		set @sumerrors += @@ROWCOUNT
		
--		delete from orderinfo where orderid = @orderid
--		set @sumerrors += @@ROWCOUNT
		
--		--判断错误是否不等于0
--		if(@sumerrors <> 2)
--			begin
--				print 'false'
--				rollback transaction--回滚事务
--			end
--		else
--			begin
--				print 'true'
--				commit transaction--提交事务
--			end
--	end
--	else
--		begin
--			print '不存在该订单编号'
--		end

----调用存储过程
--exec proc_orderdelete 44


----删除商品信息
--if exists(select 1 from sysobjects where name = 'proc_goodsdelete')
--	drop procedure proc_goodsdelete
--go
----创建存储过程
--create procedure proc_goodsdelete

--	@goodsid int--商品编号
--as
--	--判断该商品编号是否存在
--	if exists(select 1 from goodsinfo where goodsid = @goodsid )
--	begin
--		begin transaction--开始事务
		
--		declare @orderid int
--		set @orderid = 0
--		declare @sumerrors int--存储错误总和
--		set @sumerrors = 0
		
--		if exists(select 1 from ordergoodsinfo where goodsid = @goodsid )
--		begin
--			print  'false'
--			rollback transaction--回滚事务
			
--		end
--		--删除商品信息
--		delete from goodsinfo where goodsid = @goodsid 
--		set @sumerrors += @@ROWCOUNT
		
--		--判断错误是否不等于0
--		if(@sumerrors <> 1 )
--			begin
--				--print 'false'
--				rollback transaction--回滚事务
--			end
--		else
--			begin
--				--print   'true' 
--				commit transaction--提交事务
--			end
--	end
--	else
--		begin
--			--print '不存在该订单编号'
--			print  'false' 
--		end

----调用存储过程
--exec proc_goodsdelete 11

--select * from goodsinfo
--select * from orderinfo
--select * from ordergoodsinfo where goodsid = 3
--update ordergoodsinfo set orderid = 38  where orderid = 42
--select COUNT(*) from ordergoodsinfo where orderid = 36
--select * from ordergoodsinfo where goodsid = 3
--select * from goodstype

----创建系统维护账号并授权
----添加SQL登录账号
--create login admin with password = '123456'
----创建数据库用户
--create user sysadmindbuser for login admin
----授权
--grant select,insert,update,delete on shoppingDB to sysadmindbuser

--select * from customerinfo

--select * from customerdetailinfo

--select * from goodstype

--select * from goodsinfo

--select * from ordergoodsinfo 

--select * from orderinfo
--select * from shoppingDB 
--select top 7 a.*,b.userName from bulletininfo a  left join shoppingDB b on a.userId = b.id  where a.userid=1 and a.id not in(select top 0 id from BulletinInfo where userid = 1 order by createtime desc)order by a.createtime desc
--select * from bulletininfo

--select * from goodsinfo where typeid = 6
--select top 6 a.*,b.userName from bulletininfo a left join shoppingDB b on a.userId = b.id  where  a.id not in
--(select top 6 id from BulletinInfo order by createtime desc)order by a.createtime desc

--select a.* , c.username from  bulletininfo a inner join shoppingDB c on a.userid = c.id 

--select count(1) from  bulletininfo

--update bulletininfo set createtime = GETDATE();

--delete from bulletininfo where id=1

--select count(1) from bulletininfo where title like '%国庆%'

--select top 7 a.*,b.username from bulletininfo a inner join shoppingDB b on a.userId = b.id where a.id not in(select top 0 id from BulletinInfo)

--select top 7 * from bulletininfo a inner join shoppingDB b on a.userId = b.id where a.title like '%标题%'  and a.id not in(select top 7 id from BulletinInfo where title like '%标题%')
 
--select top 7 b.id as '公告ID',a.username,a.id as '用户',b.userid as '公告' from  bulletininfo b left join shoppingDB a 
--on a.id = b.userid where b.id not in(select top (7*(1-1))id from bulletininfo)

--select b.*,a.id,a.username from shoppingDB a right join bulletininfo b on a.id = b.userid

--select * from bulletininfo

--update orderinfo set status = 1 

--select top 10 sum(a.quantity) as quantity,b.goodsid,b.goodsname from ordergoodsinfo a left join
-- goodsinfo b on a.goodsid = b.goodsid inner join
--  orderinfo c on c.orderid = a.orderid where
--   b.typeid = 4 and b.status = 0 and c.status=1 group by
--    b.goodsid , b.goodsname ORDER  BY sum(a.quantity) DESC
    
   
-- select top 10 a.goodsid, a.goodsname , a.price , a.discount , a.photo  from goodsinfo a
-- where  a.goodsid not in(select top 0 goodsid from goodsinfo where typeid = 4) and  a.typeid = 4 and  a.status = 0
   
-- select COUNT(*) as 'goodsid' from goodsinfo where typeid = 4 and  status = 0
   
-- update goodsinfo set photo = 'bao1.jpg' where typeid = 4 
 
-- select * from goodsinfo where typeid = 4
 
-- update goodsinfo set discount = 9.5,price = 59.0 where goodsid = 7
-- delete from goodsinfo where goodsid in (1,2,3);
 
--select b.photo , b.goodsname , c.typename ,b.price , b.discount ,a.quantity , a.orderid from ordergoodsinfo a left join
--  goodsinfo b on a.goodsid = b.goodsid inner join
--  goodstype c on b.typeid = c.typeid inner join
--  orderinfo d on d.orderid = a.orderid inner join
--  customerinfo e on d.customerid = e.id where
--  b.status = 0 and d.status = 0 and d.customerid = 1
-- select a.goodsid,a.goodsname,a.price ,a.discount ,b.typename , a.remark , a.photo from goodsinfo a left join  goodstype b on a.typeid = b.typeid where a.goodsid = 7
 
--select a.goodsid,a.goodsname,a.price ,a.discount ,b.typename , a.remark , a.photo from goodsinfo a left join  goodstype b on a.typeid = b.typeid where a.goodsid = 7

-- update ordergoodsinfo set quantity = 3 where orderid = 1
-- update goodsinfo set photo = 'bao1.jpg' where goodsid = 1
-- update ordergoodsinfo set orderid = 1  where goodsid = 14
-- update orderinfo set status = 0  where orderid = 1
  
--select top 5 * from goodstype where typeid not in (select top 5 typeid from goodstype  order by typeid asc) order by typeid  asc

--select * from goodsinfo where isrecommend = 0 and status = 0


--select * from goodsinfo where isnew = 0 and status = 0

--select top 7 a.*,b.userName from bulletininfo a
--				 left join shoppingDB b on a.userId = b.id 
--				 where  a.id not in(select top 0 id from BulletinInfo  order by createtime desc)order by a.createtime desc
--select * from goodsinfo where discount != 10.0 and status = 0 order by discount asc



-- select top 7 a.orderid,a.status,a.ordertime,c.id,c.email,d.name,d.telphone,d.mobiletelephone from 
--  orderinfo a  left join
--  customerinfo c on c.id = a.customerid left join
--  customerDetailinfo d on d.customerid = c.id where
--  a.orderid not in(select top 0 orderid from orderinfo order by status,ordertime )order by status,ordertime
  
--   select top 7 a.orderid,a.status,a.ordertime,c.id,c.email,d.name,d.telphone,d.mobiletelephone from orderinfo a  left join customerinfo c on c.id = a.customerid left join customerDetailinfo d on d.customerid = c.id where   a.orderid not in(select top 0 orderid from orderinfo order by status,ordertime )order by status,ordertime
--   select count(1) from  orderinfo a  left join  
--				customerinfo c on c.id = a.customerid   
--				where c.email like '%b%'
		
--		select * from orderinfo

-- select * from ordergoodsinfo a  left join
--  orderinfo b on b.orderid = a.orderid left join
--  customerinfo c on c.id = b.customerid left join
--  goodsinfo e on a.goodsid = e.goodsid left join
--  goodstype f on e.typeid = f.typeid where c.id = 6 and b.orderid = 32 order by b.status,ordertime
   
   
--  select * from 
--  orderinfo b left join
--  customerinfo c on c.id = b.customerid left join
--  customerDetailinfo d on d.customerid = c.id  where c.id = 1 and b.orderid = 30 order by b.status,ordertime
  
--  select * from goodsinfo
--  select * from ordergoodsinfo
  
--  --客户信息部分
--  select * from orderinfo c left join customerinfo a on c.customerid = a.id left join customerDetailinfo b on a.id = b.customerid  where id = 6 and orderid = 32
--  --订单信息部分
--  select * from orderinfo a inner join ordergoodsinfo b on a.orderid = b.orderid where a.customerid = 6 and b.orderid = 32
  
-- --添加客户并下订单
--  insert into customerinfo values('abcd@sina.com','123456',GETDATE());
--  select * from customerinfo where email = 'bb@sina.com' 
--  declare @customerId int
--  declare @order int
--  select @customerId = @@IDENTITY
--  select * from customerDetailinfo
  
--  insert into orderinfo(customerid,status) values(13,0);
--  select @order = @@IDENTITY
--							 --订单编号--商品编号--数量
--  insert into ordergoodsinfo values(@order,30,3);
--  insert into ordergoodsinfo values(@order,31,3);
--  insert into ordergoodsinfo values(@order,32,3); 
--  go
--  select * from goodsinfo
--  select * from ordergoodsinfo
--  select * from orderinfo
--  select * from customerinfo
--  --指定客户下订单
--   declare @customerId int 
--   set @customerId = 1
--  declare @order int 
--   insert into orderinfo(customerid,status) values(@customerId,0);
--  select @order = @@IDENTITY
--  							 --订单编号--商品编号--数量
--  insert into ordergoodsinfo values(@order,1,3);
--  insert into ordergoodsinfo values(@order,2,3);
--  insert into ordergoodsinfo values(@order,3,3); 
--  select * from ordergoodsinfo where orderid = @order
--  go
  
--  declare @customerId int 
--   set @customerId = 2
--  declare @order int 
--   insert into orderinfo(customerid,status) values(@customerId,0);
--  select @order = @@IDENTITY
--  							 --订单编号--商品编号--数量
--  insert into ordergoodsinfo values(@order,1,3);
--  insert into ordergoodsinfo values(@order,2,3);
--  insert into ordergoodsinfo values(@order,3,3); 
--  select * from ordergoodsinfo where orderid = @order
--  go
--  declare @customerId int 
--   set @customerId = 3
--  declare @order int 
--   insert into orderinfo(customerid,status) values(@customerId,0);
--  select @order = @@IDENTITY
--  							 --订单编号--商品编号--数量
--  insert into ordergoodsinfo values(@order,1,3);
--  insert into ordergoodsinfo values(@order,2,3);
--  insert into ordergoodsinfo values(@order,3,3); 
--  select * from ordergoodsinfo where orderid = @order
--  go
--  declare @customerId int 
--   set @customerId = 4
--  declare @order int 
--   insert into orderinfo(customerid,status) values(@customerId,0);
--  select @order = @@IDENTITY
--  							 --订单编号--商品编号--数量
--  insert into ordergoodsinfo values(@order,1,3);
--  insert into ordergoodsinfo values(@order,2,3);
--  insert into ordergoodsinfo values(@order,3,3); 
--  select * from ordergoodsinfo where orderid = @order
--  go
--  declare @customerId int 
--   set @customerId = 5
--  declare @order int 
--   insert into orderinfo(customerid,status) values(@customerId,0);
--  select @order = @@IDENTITY
--  							 --订单编号--商品编号--数量
--  insert into ordergoodsinfo values(@order,1,3);
--  insert into ordergoodsinfo values(@order,2,3);
--  insert into ordergoodsinfo values(@order,3,3); 
--  select * from ordergoodsinfo where orderid = @order
--  go
--  update goodsinfo set typeid = ? , goodsname = ?  , price = ? ,discount = ? ,isnew = ? , isrecommend =? , status = ? , photo = ? , remark = ? where goodsid = ?
--  select * from goodsinfo 
--select * from customerinfo where email like '%d%'

--update customerinfo set pwd = ? where id = ?
--select * from goodstype

-- select top 7 a.orderid,a.status,a.ordertime,c.id,c.email,d.name,d.telphone,d.mobiletelephone from orderinfo a  left join customerinfo c on c.id = a.customerid left join customerDetailinfo d on d.customerid = c.id where   a.orderid not in(select top 7 orderid from orderinfo order by status,ordertime )order by status,ordertime


--select top 4 * from  CustomerDetailInfo a right join  CustomerInfo b on b.id=a.customerId where b.customerId not in (select top  4 customerId from  CustomerDetailInfo a right join  CustomerInfo b on b.id=a.customerId order by registerTime desc) order by registerTime desc

--select top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 
--where email like '%b%' and a.id not in (select top 0 id from customerinfo 
--where email like '%b%' order by registerTime desc) order by registerTime desc

--select  COUNT(*) as 'num' from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 
--where email like '%b%'

--select  top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 
--where name like  '%b%' and a.id not in (select top 0 id from customerinfo where name like  '%b%' 
-- order by registerTime desc) order by registerTime desc


--select  top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid where name like  '%b%' and a.id not in 
--(select top 0 id from customerinfo where email like  '%b%'  order by registerTime desc) order by registerTime desc

--select * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid order by a.registertime desc

--select * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 

--select  top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 
--where a.id not in (select top 0 id from customerinfo order by id,registerTime desc)
--order by registerTime desc

--select top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 
--where a.id not in (select top 4 id from customerinfo order by id,registerTime desc)
--order by a.id ,a.registertime desc

--select  top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid 
--where a.id not in (select top 8 id from customerinfo order by id,registerTime desc)
--order by a.id ,a.registerTime desc


--select  top 4 * from customerinfo a left join CustomerDetailInfo b on a.id = b.customerid  where email like  '%%' and a.id not in (select top 0 id from customerinfo   where email like  '%%'  order by registerTime desc) order by registerTime desc

