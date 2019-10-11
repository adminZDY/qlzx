use master;

EXEC sp_configure 'show advanced options', 1;
	GO
	--�������ã�
	RECONFIGURE;
	GO
	--����xp_cmdshell�洢���̣�
	EXEC sp_configure 'xp_cmdshell', 1;
	GO
	--�������ã�
	RECONFIGURE;
	GO

	--����ϵͳ�洢���̣��½�E:\SECĿ¼��
	EXEC xp_cmdshell 'mkdir E:\SQL\ǧ��֮�й���ϵͳ', NO_OUTPUT
	GO
/*�����ļ��д������*/
/*����ǰϵͳ�д���HR����ɾ��֮��*/
IF EXISTS (SELECT * FROM sysdatabases WHERE name = 'shoppingDB')
BEGIN
	DROP DATABASE shoppingDB;
END
GO
/*�������ݿ�HR��*/
CREATE DATABASE shoppingDB
ON 
(
	NAME = 'shoppingDB_data',
	FILENAME = 'E:\SQL\ǧ��֮�й���ϵͳ\shoppingDB_data.mdf',
	SIZE = 5 MB,
	FILEGROWTH = 20%
)
LOG ON 
(
	NAME = 'shoppingDB_log', 
	FILENAME = 'E:\SQL\ǧ��֮�й���ϵͳ\shoppingDB_log.ldf',
	SIZE = 1MB, 
	MAXSIZE = 20MB,
	FILEGROWTH = 1
);
GO
 
use shoppingDB
go

--�û���Ϣ��
create table userInfo
(
	id			int			not null	identity(1,1),--�û����
	username	varchar(50)	not null,--�û�����
	userpwd		varchar(50)	not null,--�û�����
)
go

insert into userInfo values('admin','123456');


alter table userInfo
	--����Լ�� �û����Ϊ����Լ��
	add constraint pk_userInfo_id primary key(id),
	--ΨһԼ�� �û�����ΪΨһԼ��
	constraint uq_userInfo_username unique(username),
	--���Լ�� �û����볤�ȴ��ڵ���6λ
	constraint ch_userInfo_userpwd check (len(userpwd) >= 6)
go

--������Ϣ��
create table bulletininfo
(
	id			int				not null	identity(1,1),--������
	title		varchar(100)	not null,--�������
	content		text			not null,--��������
	userid		int				not null,--������
	createtime	datetime		not null,--����ʱ��
)
go

alter table bulletininfo
	--����Լ�� ������Ϊ����
	add constraint pk_bulletininfo_id	primary key(id),
	--���Լ�� ���userid(������)��������id(�û����)
	constraint fk_bulletininfo_title foreign key(userid) references userInfo(id),
	--Ĭ��Լ�� ����ʱ��Ĭ��Ϊ��ǰʱ��
	constraint df_bulletininfo_createtime default (getdate()) for createtime
go	

--�ͻ���Ϣ��
create table customerinfo
(
	id				int				not null	identity(1,1),--�ͻ����
	email			varchar(100)	not null,--�������䣨�ͻ��˻�����
	pwd				varchar(20)		not null,--����
	registertime	datetime		not null,--ע��ʱ��
)
go

alter table customerinfo
	--����Լ�� �ͻ����Ϊ����
	add constraint pk_customerinfo_id	primary key(id),
	--���Լ�� ���������ʽ����Ϸ�������@��@ǰ������з��ţ�@���������㣬��ǰ������з��ţ�
	constraint ch_customerinfo_email check(email like '%_@%_.%_'),
	--ΨһԼ�� ��������ΪΨһ
	constraint uq_customerinfo_email unique(email),
	--���Լ�� ���볤�ȴ��ڵ���6λ
	constraint ch_customerinfo_pwd check(len(pwd) >= 6),
	--Ĭ��Լ�� ע��ʱ��Ĭ��Ϊ��ǰʱ��
	constraint df_customerinfo_registertime default(getdate()) for registertime
go


--�ͻ���ϸ��Ϣ��
create table CustomerDetailInfo
(
	customerid		int			not null,--�ͻ����
	name			varchar(50)	not null,--�ջ�������
	telphone		varchar(20) null,--�̶��绰
	mobiletelephone	varchar(20) null,--�ƶ��绰
	address			varchar(100)	not null,--�ջ���ַ
)
go


alter table CustomerDetailInfo
	--���Լ�� �����CustomerDetailInfo����Ϊcustomerid(�ͻ����)����������customerinfo����Ϊid(�ͻ����)
	add constraint fk_CustomerDetailInfo_customerid foreign key (customerid) references customerinfo(id),
	--���Լ�� �̶��绰��ʽΪxxxx-xxxxxxxx
	constraint ch_CustomerDetailInfo_telphone check(telphone like '____-________'),
	--���Լ�� �ƶ��绰����Ϊ11λ����
	constraint ch_CustomerDetailInfo_mobiletelephone check(len(mobiletelephone) = 11)
go

--��Ʒ���
create table goodstype
(
	typeid		int		not null	identity(1,1),--�����
	typename	varchar(20) not null,--�������
)
go

alter table goodstype
	--����Լ��
	add constraint pk_goodstype_typeid primary key(typeid),
	--ΨһԼ��
	constraint uq_goodstype_typename unique(typename)
go

--��Ʒ��Ϣ��
create table goodsinfo
(
	goodsid		int				not null	identity(1,1),--��Ʒ���
	typeid		int				not null,--��Ʒ���
	goodsname	varchar(50)		not null,--��Ʒ����
	price		decimal(8,2)	not null,--��Ʒ�۸�
	discount	float			not null,--�ۿ�
	isnew		int				not null,--�Ƿ���Ʒ
	isrecommend	int				not null,--�Ƿ��Ƽ�
	status		int				not null,--��Ʒ״̬
	photo		varchar(50)		null,--��ƷͼƬ
	remark		varchar(200)	null,--��ע
	detailed   text null
)
go
update goodsinfo set detailed = null where goodsid = 89 
select a.goodsid,a.goodsname,a.price ,a.isnew,a.isrecommend,a.status,a.discount ,b.typename , a.remark , a.photo , a.detailed from goodsinfo a left join  goodstype b on a.typeid = b.typeid where a.goodsid = 89
select * from goodsinfo
alter table goodsinfo
	--����Լ�� ��Ʒ���Ϊ����
	add constraint pk_goodsinfo_goodsid primary key(goodsid),
	--���Լ�� �����goodsinfo����Ϊtypeid(��Ʒ���)����������goodstype����Ϊtypeid(��Ʒ���)
	constraint fk_goodsinfo_typeid foreign key(typeid) references goodstype(typeid),
	--Ĭ��Լ�� Ĭ����Ʒ�۸�Ϊ10.0
	constraint df_goodsinfo_discount default(10.0) for discount,
	--���Լ�� �Ƿ���Ʒ��0��Ʒ��1������Ʒ��
	constraint ch_goodsinfo_isnew check(isnew in(0,1)),
	--���Լ�� �Ƿ��Ƽ���0�Ƽ���1���Ƽ���
	constraint ch_goodsinfo_isrecommend check(isrecommend in (0,1)),
	--���Լ�� ��Ʒ״̬��0�ϼܣ�1�¼ܣ�
	constraint ch_goodsinfo_status check(status in (0,1))
go


--������Ϣ��
create table orderinfo
(
	orderid		int		not null	identity(1,1),--�������
	customerid	int		not null,--�ͻ����
	status		int		not null,--����״̬
	ordertime	datetime not null,--�¶���ʱ��
)
go

alter table orderinfo
	--����Լ�� �������Ϊ����
	add constraint pk_orderinfo_orderid	primary key(orderid),
	--���Լ�� �����orderinfo����Ϊ�����customerid(�ͻ����)����������customerinfo����Ϊid(�ͻ����)
	constraint fk_orderinfo_customerid foreign key(customerid) references customerinfo(id),
	--[0��ʾδ����][1�û�����ɹ�][2�̼��ѷ���][3�û�ȷ���ջ�][4�û����ۡ��������׳ɹ�]
	
	--���Լ�� ����״̬��0δȷ����1ȷ��,��
	constraint ch_orderinfo_status check(status in (0,1,2,3,4)),
	--Ĭ��Լ�� �¶���ʱ��ΪĬ�ϵ�ǰϵͳʱ��
	constraint df_orderinfo_ordertime default(getdate()) for ordertime
go

delete from orderinfo where orderid = 61
update orderinfo set status = 2  where orderid = 60
select * from orderinfo

--������Ʒ��Ϣ��
create table ordergoodsinfo
(
	orderid		int		not null,--�������
	goodsid		int		not null,--��Ʒ���
	quantity	int		not null,--��Ʒ����
)
go

alter table ordergoodsinfo
	--��������Լ�� ������ţ���Ʒ���Ϊ����
	add constraint pk_ordergoodsinfo_orderid_goodsid primary key(orderid,goodsid),
	--���Լ�� ��Ʒ��������0
	constraint ch_ordergoodsinfo_quantity check(quantity > 0),
	--Ĭ��Լ�� ��Ʒ����Ĭ��Ϊ1
	constraint df_ordergoodsinfo_quantity default(1) for quantity,
	
	constraint fk_ordergoodsinfo_orderid foreign key(orderid) references orderinfo(orderid),
	
	constraint fk_ordergoodsinfo_goodsid foreign key(goodsid) references goodsinfo(goodsid)
go

select * from ordergoodsinfo

--��Ʒ���Ա�
create table Review
(
	--���Ա��
	id int primary key identity(1,1) not null,
	--�û����
	customerId int not null,
	--��Ʒ���
	goodid int not null,
	--�������
	orderId int not null,
	--����ʱ��
	reviewTime datetime  default(getDate()) not null,
	--���ۣ�1����,2һ��,3������
 	reviewStatus int  default(3) not null check(reviewStatus in(1,2,3)),
	--�������ݣ����飬���֣�ͼƬ��  
	reviewContent text default(null) null
)
go
select * from Review a left join customerinfo b on a.customerId = b.id where a.goodid= 94

alter table Review
	--������Ʒ
	add constraint fk_Review_goodsinfo foreign key(goodid) references goodsinfo(goodsid)
go	
alter table Review
	--�����û�
	add constraint fk_Review_customerinfo foreign key(customerId) references customerinfo(id)
alter table Review
	--���Զ���
	add constraint fk_Review_orderinfo foreign key(orderId) references orderinfo(orderid)



--�����û���Ϣ

insert into userInfo values ('zhangsan' ,123456)

insert into userInfo values ('lisi' ,123456)

select* from userInfo

--���빫����Ϣ
insert into bulletininfo 
	values ('��������-�������1' ,'��������-��������1',1,GETDATE())

insert into bulletininfo 
	values ('��������-�������2' ,'��������-��������2',1,GETDATE())

insert into bulletininfo 
	values ('��������-�������3' ,'��������-��������3',1,GETDATE())

insert into bulletininfo 
	values ('��������-�������4' ,'��������-��������4',1,GETDATE())

insert into bulletininfo 
	values ('��������-�������5' ,'��������-��������5',1,GETDATE())

insert into bulletininfo 
	values ('��������-�������6' ,'��������-��������6',1,'2009-07-22 10:25:08.800')

select * from bulletininfo

--����ͻ���Ϣ
insert into customerinfo  
	values ('a@sina.com' ,'123456','2009-06-06 12:30:45.000')

insert into customerinfo  
	values ('b@sina.com' ,'123456','2009-06-06 12:30:45.000')

insert into customerinfo  
	values ('c@sina.com' ,'123456','2009-06-06 12:30:45.000')

insert into customerinfo  
	values ('d@sina.com' ,'123456','2009-06-06 12:30:45.000')
	
select * from customerinfo 

--����ͻ���ϸ��Ϣ
insert into CustomerDetailInfo  
	values (1,'����' ,'0001-32456754','13534563234','����������������·1��')

insert into CustomerDetailInfo  
	values (2,'��˹' ,'0002-32456754','13534563234','������ɳƺ����ɳ��·14��')

insert into CustomerDetailInfo  
	values (3,'����' ,'0003-32456754','13534563234','������ɳƺ��������·22��')

select * from CustomerDetailInfo 
--������Ʒ���
insert into goodstype   
	values ('ҰӪ����')

insert into goodstype   
	values ('˯������')

insert into goodstype   
	values ('��������')

insert into goodstype   
	values ('�˶��ֱ�')

insert into goodstype   
	values ('ҰӪ��Ʒ')

insert into goodstype   
	values ('��ɽ��������')

insert into goodstype   
	values ('�����װ')
	
select * from goodstype  

select * from goodsinfo
--������Ʒ��Ϣ
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1182 ˫��˫������','498',1,1,0,'zhangpeng1.jpg','˫��˫���� (')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'ND-1133 ����˫������','256','9.5',0,1,0,'zhangpeng2.jpg','����˫���� (')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'ND-1132 �������񡣡���','296','6.7',1,0,0,'zhangpeng3.jpg','����˫���� (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1136 ����˫������','438',0,1,0,'zhangpeng4.jpg','����˫���� (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1121 ˫��˫������','186',1,1,0,'zhangpeng5.jpg','˫��˫���� (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'ND-1009 ����ɴ������','680',1,0,0,'zhangpeng6.jpg','����ɴ���� (')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'MOBI GARDEN���ߵ�','642',1,0,0,'zhangpeng7.jpg','˫���Զ�2������')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'����˫����������','846',1,1,0,'zhangpeng8.jpg','˫���Զ�2������')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(1,'��ɽ3��˫����������','957',1,1,0,'zhangpeng9.jpg','˫���Զ�2������')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'ɽ��3��˫����������','654','6.7',0,0,0,'zhangpeng10.jpg','˫���Զ�2������')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(1,'�߿Ƽ���','654','6.7',0,0,0,'zhangpeng1.jpg','˫���Զ�2������')

insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'ND-1372 ����˫��...','198','8.5',1,1,0,'shuidai1.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'ND-1255L �ŷ�˯...','146','9.5',0,1,0,'shuidai2.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'ND-1202 ��������...','296','6.7',1,0,0,'shuidai2.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(2,'���Ǳ̶�˹500 ��','438',0,1,0,'shuidai5.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(2,'CHAMONIX334SOFT˯��','186',1,1,0,'shuidai5.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(2,'CHAMONIX265��С˯��','267',1,0,0,'shuidai6.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'highrockS106107...','467','6.5',1,0,0,'shuidai7.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(2,'highrockS4543 ө���','456','8.8',1,1,0,'shuidai8.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(3,'Featherf4005 ľ..','198','8.5',1,1,0,'shuidai9.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(3,'Featherf4007 ��ɫ','146','9.5',0,0,0,'shuihu1.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'MAINNAV950DM ��..','298','8.5',1,1,0,'shuihu2.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(4,'MAINNAV950D GPS��','286',0,0,0,'shuihu3.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'SUUNTO D6 Ǳˮϵ��..','358','8.5',1,1,0,'shuihu4.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'SUNNTOt 3C Black..','434','9.5',0,1,0,'shuihu5.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'SUNNTOt T3C BLACK..','499','8.5',1,1,0,'shuihu6.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(4,'����֮��A77���ʱ�','564','9.5',0,1,0,'toudeng1.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(4,'����֮��A77�߾��ȱ�','532',1,0,0,'biao4.jpg','ʱ��ϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(4,'����֮��A77�߾��ȱ�','342',0,1,0,'biao5.jpg','ʱ��ϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-8629 ���������XL','198','8.5',1,1,0,'bao1.jpg','60L-80L')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-8627 ���������M','186',0,1,0,'bao2.jpg','25L-40L')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-8626 ���������S','8.5',1,1,0,'bao3.jpg','25L����')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-2129 С�ſ��','121','8.5',0,1,0,'kuabao1.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-2125 ����','43',1,0,0,'jijiubao1.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-3211 ����','34','9.5',0,1,0,'jijiubao1.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-3210 ���Ȱ�','59','9.5',0,1,0,'jijiubao2.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-2315 ����','55','9.5',0,0,0,'maojing.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'Featherf201 ����ɡ','532',1,1,0,'zheyangsan.jpg','����ɡ')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3565 3+5LEDͷ��','89',0,1,0,'toudeng1.jpg','ͷ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3566 2+1Xͷ��','68',0,1,0,'toudeng2.jpg','ͷ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'ND-3565 3+5LEDͷ��','92','6.5',0,1,0,'toudeng3.jpg','ͷ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3613 1X�ֵ�(U40)','55',1,0,0,'shoudian1.jpg','�ֵ�')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(5,'ND-3612 3LED�ֵ�...','58',0,1,0,'shoudian2.jpg','�ֵ�')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(5,'D-3611 1X�ֵ�(N30)','34','7.7',0,1,0,'shoudian3.jpg','�ֵ�')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1781 000...','158','8.5',1,1,0,'shoudian4.jpg','MINI 8 FIG...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1772 138...','186',0,1,0,'xie1.jpg','PETIT 8 FI...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1813 153...','68',1,1,0,'xie2.jpg','��ɽϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1813 213...','84','9.5',0,1,0,'xie3.jpg','��ɽϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(6,'Edelrid1813 234...','99','3.5',1,1,0,'xie4.jpg','��ɽϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(7,'snowwolfŮ���...','298','8.5',1,1,0,'yi1.jpg','MINI 8 FIG...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(7,'snowwolf�п��...','286',0,1,0,'yi2.jpg','PETIT 8 FI...')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(7,'snowwolfŮ��޹��...','358',1,1,0,'yi3.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(7,'GARMONT ZEPHYR4...','265','9.5',0,1,0,'xie2.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,discount,isNew,isRecommend,status,photo,remark)
values(7,'GARMONT ZEPHYR5...','258','8.5',1,1,0,'xie3.jpg','ҰӪϵ��')
insert into GoodsInfo (typeId,goodsName,price,isNew,isRecommend,status,photo,remark)
values(7,'GARMONT Syncro...','199',1,1,0,'xie4.jpg','ҰӪϵ��')select * from goodsinfo


--���붩����Ϣ��
insert into orderinfo   
	values (1,0,'2009-07-22 10:25:08.690')
	
insert into orderinfo   
	values (2,0,'2009-07-22 10:25:08.690')
	
insert into orderinfo   
	values (3,0,'2009-07-22 10:25:08.690')
	
select * from orderinfo 

--���붩����Ʒ��Ϣ
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

--����һ��������Ϣ
insert into bulletininfo values('������������','Ϊ��ף����60���꣬�ڹ����ڼ䣬ǧ��֮��ȫ��8���Ż�',2,default)


----�ж���Ʒ�����Ƿ���Ӧ����Ʒ
--if not exists(select 1 from goodstype where typeid = 1)
--	begin
--		print 'ɾ��ʧ�ܣ������Ʒ���ͻ�����Ӧ����Ʒ!'
--	end
--else
--	begin
--		--delete from goodstype where typeid = 1
--		print 'ɾ����Ʒ���ͳɹ�'
--	end


----�޸�"ND-1133 ����˫����..."�����Ʒ�������¼�״̬������Ҫʹ�����Ʒ�ϼ�
--update goodsinfo set status = 1 where goodsid = 2


----3����ѯ���пͻ���Ϣ
----�ж��Ƿ���ڸ���ͼ
--if exists(select 1 from sysobjects where name = 'view_querycustomer')
--	drop view view_querycustomer
--go
----������ͼ
--create view view_querycustomer
--as
--	select a.id as '�ͻ����',b.name as '�ջ�������',b.telphone as '�̶��绰',
--	b.mobiletelephone as '�ƶ��绰',a.email as '��������',b.address as '�ջ���ַ',
--	a.registertime as 'ע��ʱ��',a.pwd as '����' 
--	from customerinfo a join CustomerDetailInfo b on a.id = b.customerid
--go

----������ͼ
--select * from view_querycustomer

----5����ѯ���ж����Ļ�����Ϣ
----�ж��Ƿ���ڸ���ͼ
--if exists(select 1 from sysobjects where name = 'view_queryorderinfo')
--	drop view view_queryorderinfo
--go
----������ͼ
--create view view_queryorderinfo
--as
--	select c.orderid as '�������',
--	case c.status when 0 then 'δȷ��'
--	else 'ȷ��'
--	end as '����״̬' ,
--	ordertime as '�µ�ʱ��',email as '�ͻ��˻�/����',name as '�ջ���',
--	telphone as '�̶��绰',mobiletelephone as '�ƶ��绰'
--	from customerinfo a join CustomerDetailInfo b on a.id = b.customerid
--	join orderinfo c on c.orderid = a.id 
--go
----������ͼ
--select * from view_queryorderinfo


----6��������ص���Ʒ��Ϣ

----�ж��Ƿ���ڸ���ͼ
--if exists(select 1 from sysobjects where name = 'view_queryorderdetail')
--	drop view view_queryorderdetail
--go
----������ͼ
--create view view_queryorderdetail
--as 	
--	select b.goodsid as '��Ʒ���',b.typeid as '��Ʒ���',b.goodsname as '��Ʒ����',
--	b.price as '��Ʒ�۸�',b.discount as '�ۿ�',c.quantity as '��������',b.price*b.discount/10*c.quantity  as 'С��'
--	from goodstype a join goodsinfo b on a.typeid=b.typeid 
--					 join ordergoodsinfo c on c.goodsid = b.goodsid 
--go
----������ͼ
--select * from  view_queryorderdetail

----�����ܽ��
--select SUM(price*discount/10*quantity) as '�����ܽ��' from (select b.price,b.discount ,c.quantity 
--from goodstype a join goodsinfo b on a.typeid=b.typeid 
--				 join ordergoodsinfo c on c.goodsid = b.goodsid)as order_sum


----����ͻ��˺�Ϊ"a@sina.com"�Ŀͻ����
--declare @customerid int--�洢�ͻ����
--set @customerid = 1
----�����¶�����¼
--select * from orderinfo
--insert into orderinfo values(@customerid ,5,default)
----���ݱ�1-10�����붩������Ʒ
--declare @orderid int--�洢�������
--select @orderid = @@IDENTITY --�õ��ղ���Ķ����ı��
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


----8����Ʒ��������
----�ж��Ƿ���ڸ���ͼ
--if exists(select 1 from sysobjects where name = 'view_orderbyquantity')
--	drop view view_orderbyquantity
--go
----������ͼ
--create view view_orderbyquantity
--as
--	select top 3 a.goodsid as '��Ʒ���', a.goodsname as '��Ʒ����', sum(c.quantity) as '��������'
--	from goodsinfo a join ordergoodsinfo c on a.goodsid = c.goodsid 
--	group by a.goodsid,a.goodsname order by sum(c.quantity) desc
--go

--select * from view_orderbyquantity

----�ж����������������ɾ��������
--if exists(select 1 from sysindexes where name = 'index_orderinfo_customerid')
--	drop index orderinfo.index_orderinfo_customerid
--go
----�����Ǿۼ�����
--create nonclustered index index_orderinfo_customerid
--on orderinfo(customerid)
--with
--	fillfactor = 40
--go
----��������
--select * from orderinfo with(index=index_orderinfo_customerid) 


----�ж��Ƿ���ڸô�����
--if exists(select 1 from sysobjects where name ='tr_delete_customerinfo')
--	drop trigger tr_delete_customerinfo--ɾ��������
--go
----����������
--create trigger tr_delete_customerinfo
--on customerinfo
--instead of delete 
--as
--	--��������洢�û����
--	declare @customerid int
--	--��������û����
--	select @customerid = id from deleted
	
--	--�ж��û��Ƿ��ж���
--	if exists(select 1 from orderinfo where customerid = @customerid)
--		begin
--			print '���û��ж����������ʿͻ�������ɾ��!'
--		end
--	else 
--		begin
--			--ɾ���ͻ���ϸ��Ϣ��
--			delete from customerdetailinfo where customerid = @customerid
--			--������Ϣ��
--			delete from orderinfo where customerid = @customerid
--			--�ͻ���Ϣ��
--			delete from customerinfo where id = @customerid
--			print '�û�ɾ���ɹ�!'			
--		end
--go		
----���Դ�����
--delete from customerinfo where email = 'a@sina.com'


----�����洢���̲�����

----�жϴ洢�����Ƿ����
--if exists(select 1 from sysobjects where name = 'proc_queryorderinfo')
--	drop procedure proc_queryorderinfo
--go
----�����洢����
--create procedure proc_queryorderinfo
--	@orderinput varchar(50) = ''--�洢���̲��������ڽ��ն�����Ż�ͻ��˺�
--as	
--begin
--	declare @sql varchar(100) --���ڴ洢ƴ�ӵ�sql���
	
--	set @sql = 'select * from view_queryorderinfo where ������� like ''%'+@orderinput+'%''or [�ͻ��˻�/����] like ''%'+@orderinput+'%'''
	
--	exec (@sql)--ִ���ַ�������Ӧ��sql���
--end
----���ô洢����
--exec proc_queryorderinfo 1


----�ж��Ƿ���ڸô洢����
--if exists(select 1 from sysobjects where name = 'proc_inssertorder')
--	drop procedure proc_inssertorder
--go
----�����洢����
--create procedure proc_inssertorder
--	--@email varchar(50),--�¶����Ŀͻ��˺�
--	@customerid int,--�ͻ����
--	@goodsid	int,--��Ʒ���
--	@quantity	int--�������Ʒ����
--as
--	--�жϸ���Ʒ����Ƿ����
--	if exists(select 1 from goodsinfo where goodsid = @goodsid )
--	begin
--		begin transaction--��ʼ����
		
--		declare @sumerrors int--�洢�����ܺ�
--		set @sumerrors = 0
		
--		--����ͻ��˺ŵĿͻ����
--		--declare @customerid int--�洢�ͻ����
--		--select @customerid = id from customerinfo where email = @email
--		--set @sumerrors = @@error
		
--		--�����¶�����¼
--		insert into orderinfo values (@customerid,0,getdate())
--		set @sumerrors += @@error
		
--		declare @orderid int--�洢�������
--		select @orderid = @@identity--�õ��ղ���Ķ����ı��
--		insert into ordergoodsinfo values(@orderid,@goodsid,@quantity)
--		set @sumerrors += @@error
		
--		--�жϴ����Ƿ񲻵���0
--		if(@sumerrors <> 0)
--			begin
--				print 'false'
--				rollback transaction--�ع�����
--			end
--		else
--			begin
--				print 'true'
--				commit transaction--�ύ����
--			end
--	end
--	else
--		begin
--			print '�����ڸ���Ʒ���'
--		end

----���ô洢����
--exec proc_inssertorder 1,300,100


--insert into orderinfo values (1,0,getdate()) 
--select  @@identity as 'id'

----ɾ��������Ϣ
--if exists(select 1 from sysobjects where name = 'proc_orderdelete')
--	drop procedure proc_orderdelete
--go
----�����洢����
--create procedure proc_orderdelete

--	@orderid int--�������
--as
--	--�жϸö�������Ƿ����
--	if exists(select 1 from orderinfo where orderid = @orderid )
--	begin
--		begin transaction--��ʼ����
		
--		declare @sumerrors int--�洢�����ܺ�
--		set @sumerrors = 0
		
--		--ɾ��������Ʒ��¼
--		delete from ordergoodsinfo where orderid = @orderid
--		set @sumerrors += @@ROWCOUNT
		
--		delete from orderinfo where orderid = @orderid
--		set @sumerrors += @@ROWCOUNT
		
--		--�жϴ����Ƿ񲻵���0
--		if(@sumerrors <> 2)
--			begin
--				print 'false'
--				rollback transaction--�ع�����
--			end
--		else
--			begin
--				print 'true'
--				commit transaction--�ύ����
--			end
--	end
--	else
--		begin
--			print '�����ڸö������'
--		end

----���ô洢����
--exec proc_orderdelete 44


----ɾ����Ʒ��Ϣ
--if exists(select 1 from sysobjects where name = 'proc_goodsdelete')
--	drop procedure proc_goodsdelete
--go
----�����洢����
--create procedure proc_goodsdelete

--	@goodsid int--��Ʒ���
--as
--	--�жϸ���Ʒ����Ƿ����
--	if exists(select 1 from goodsinfo where goodsid = @goodsid )
--	begin
--		begin transaction--��ʼ����
		
--		declare @orderid int
--		set @orderid = 0
--		declare @sumerrors int--�洢�����ܺ�
--		set @sumerrors = 0
		
--		if exists(select 1 from ordergoodsinfo where goodsid = @goodsid )
--		begin
--			print  'false'
--			rollback transaction--�ع�����
			
--		end
--		--ɾ����Ʒ��Ϣ
--		delete from goodsinfo where goodsid = @goodsid 
--		set @sumerrors += @@ROWCOUNT
		
--		--�жϴ����Ƿ񲻵���0
--		if(@sumerrors <> 1 )
--			begin
--				--print 'false'
--				rollback transaction--�ع�����
--			end
--		else
--			begin
--				--print   'true' 
--				commit transaction--�ύ����
--			end
--	end
--	else
--		begin
--			--print '�����ڸö������'
--			print  'false' 
--		end

----���ô洢����
--exec proc_goodsdelete 11

--select * from goodsinfo
--select * from orderinfo
--select * from ordergoodsinfo where goodsid = 3
--update ordergoodsinfo set orderid = 38  where orderid = 42
--select COUNT(*) from ordergoodsinfo where orderid = 36
--select * from ordergoodsinfo where goodsid = 3
--select * from goodstype

----����ϵͳά���˺Ų���Ȩ
----���SQL��¼�˺�
--create login admin with password = '123456'
----�������ݿ��û�
--create user sysadmindbuser for login admin
----��Ȩ
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

--select count(1) from bulletininfo where title like '%����%'

--select top 7 a.*,b.username from bulletininfo a inner join shoppingDB b on a.userId = b.id where a.id not in(select top 0 id from BulletinInfo)

--select top 7 * from bulletininfo a inner join shoppingDB b on a.userId = b.id where a.title like '%����%'  and a.id not in(select top 7 id from BulletinInfo where title like '%����%')
 
--select top 7 b.id as '����ID',a.username,a.id as '�û�',b.userid as '����' from  bulletininfo b left join shoppingDB a 
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
  
--  --�ͻ���Ϣ����
--  select * from orderinfo c left join customerinfo a on c.customerid = a.id left join customerDetailinfo b on a.id = b.customerid  where id = 6 and orderid = 32
--  --������Ϣ����
--  select * from orderinfo a inner join ordergoodsinfo b on a.orderid = b.orderid where a.customerid = 6 and b.orderid = 32
  
-- --��ӿͻ����¶���
--  insert into customerinfo values('abcd@sina.com','123456',GETDATE());
--  select * from customerinfo where email = 'bb@sina.com' 
--  declare @customerId int
--  declare @order int
--  select @customerId = @@IDENTITY
--  select * from customerDetailinfo
  
--  insert into orderinfo(customerid,status) values(13,0);
--  select @order = @@IDENTITY
--							 --�������--��Ʒ���--����
--  insert into ordergoodsinfo values(@order,30,3);
--  insert into ordergoodsinfo values(@order,31,3);
--  insert into ordergoodsinfo values(@order,32,3); 
--  go
--  select * from goodsinfo
--  select * from ordergoodsinfo
--  select * from orderinfo
--  select * from customerinfo
--  --ָ���ͻ��¶���
--   declare @customerId int 
--   set @customerId = 1
--  declare @order int 
--   insert into orderinfo(customerid,status) values(@customerId,0);
--  select @order = @@IDENTITY
--  							 --�������--��Ʒ���--����
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
--  							 --�������--��Ʒ���--����
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
--  							 --�������--��Ʒ���--����
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
--  							 --�������--��Ʒ���--����
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
--  							 --�������--��Ʒ���--����
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

