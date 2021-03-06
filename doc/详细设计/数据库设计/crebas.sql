/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/5 17:30:34                            */
/*==============================================================*/


drop table if exists Datum;

drop table if exists business;

drop table if exists carousel;

drop table if exists companyprocess;

drop table if exists introduction;

drop table if exists joinus;

drop table if exists manager;

drop table if exists master;

drop table if exists member;

drop table if exists news;

drop table if exists newstype;

drop table if exists product;

drop table if exists productparam;

drop table if exists productpic;

drop table if exists producttype;

drop table if exists recruit;

drop table if exists recruitapply;

drop table if exists role;

drop table if exists teamprocess;

/*==============================================================*/
/* Table: Datum                                                 */
/*==============================================================*/
create table Datum
(
   datumid              int not null auto_increment,
   name                 char(32),
   title                char(32) not null,
   brief                char(255) not null,
   url                  char(255) not null,
   keywords             char(32) not null,
   datetime             datetime not null,
   primary key (datumid)
);

alter table Datum comment '公司资料表';

/*==============================================================*/
/* Table: business                                              */
/*==============================================================*/
create table business
(
   businessid           int not null auto_increment,
   managername          char(32),
   des                  char(255) not null,
   icon                 char(64) not null,
   datetime             datetime not null,
   businessname         char(32) not null,
   primary key (businessid)
);

alter table business comment '业务范围表';

/*==============================================================*/
/* Table: carousel                                              */
/*==============================================================*/
create table carousel
(
   carouselid           int not null auto_increment,
   name                 char(32),
   title                char(32) not null,
   content              char(255) not null,
   datetime             datetime not null,
   image                mediumblob not null,
   primary key (carouselid)
);

alter table carousel comment '用于记录网站的首页轮播消息，照片最大不超过16M';

/*==============================================================*/
/* Table: companyprocess                                        */
/*==============================================================*/
create table companyprocess
(
   processid            int not null auto_increment,
   name                 char(32),
   title                char(32) not null,
   brief                char(255) not null,
   date                 date not null,
   image                mediumblob not null,
   primary key (processid)
);

alter table companyprocess comment '记录公司的发展历程及里程碑事件';

/*==============================================================*/
/* Table: introduction                                          */
/*==============================================================*/
create table introduction
(
   introductionid       int not null auto_increment,
   name                 char(32),
   content              text not null,
   datetime             datetime not null,
   primary key (introductionid)
);

alter table introduction comment '公司简介表';

/*==============================================================*/
/* Table: joinus                                                */
/*==============================================================*/
create table joinus
(
   joinusid             int not null auto_increment,
   name                 char(32) not null,
   company              char(64),
   email                char(64),
   phone                char(32) not null,
   comment              char(255),
   primary key (joinusid)
);

/*==============================================================*/
/* Table: manager                                               */
/*==============================================================*/
create table manager
(
   name                 char(32) not null,
   roleid               char(24) not null,
   regdatetime          datetime not null,
   portrait             mediumblob not null,
   sex                  bool not null,
   password             char(64) not null,
   permitted            boolean not null,
   primary key (name)
);

alter table manager comment '管理员表';

/*==============================================================*/
/* Table: master                                                */
/*==============================================================*/
create table master
(
   mastername           char(32) not null,
   roleid               char(24),
   password             char(64) not null,
   primary key (mastername)
);

alter table master comment '超级管理员，全局唯一';

/*==============================================================*/
/* Table: member                                                */
/*==============================================================*/
create table member
(
   memberid             int not null auto_increment,
   name                 char(32),
   membername           char(32) not null,
   post                 char(64) not null,
   image                mediumblob not null,
   primary key (memberid)
);

alter table member comment '团队成员表';

/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
create table news
(
   newsid               int not null auto_increment,
   name                 char(32),
   typeid               int,
   newsdate             date not null,
   publishdatetime      datetime not null,
   title                char(64) not null,
   content              text not null,
   isheadline           bool not null,
   image                mediumblob not null,
   keywords             char(255) not null,
   primary key (newsid)
);

alter table news comment '新闻表';

/*==============================================================*/
/* Table: newstype                                              */
/*==============================================================*/
create table newstype
(
   typeid               int not null auto_increment,
   name                 char(32) not null,
   des                  char(255) not null,
   primary key (typeid)
);

alter table newstype comment '新闻类型表';

INSERT INTO newstype values
(0,'行业新闻','主要是跟公司主营业务相关的行业新闻'),
(0,'公司新闻','主要是公司内部新闻');

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   productid            int not null auto_increment,
   typeid               int,
   name                 char(32),
   productname          char(255) not null,
   productmodel         char(255) not null,
   des                  text not null,
   primary key (productid)
);

alter table product comment '产品表';

/*==============================================================*/
/* Table: productparam                                          */
/*==============================================================*/
create table productparam
(
   paramid              int not null auto_increment,
   productid            int,
   paramname            char(32) not null,
   paramvalue           char(255) not null,
   primary key (paramid)
);

alter table productparam comment '产品参数表';

/*==============================================================*/
/* Table: productpic                                            */
/*==============================================================*/
create table productpic
(
   picid                int not null auto_increment,
   productid            int,
   pic                  mediumblob not null,
   mainpic              bool not null,
   primary key (picid)
);

alter table productpic comment '产品图片';

/*==============================================================*/
/* Table: producttype                                           */
/*==============================================================*/
create table producttype
(
   typeid               int not null auto_increment,
   name                 char(32) not null,
   des                  char(255) not null,
   primary key (typeid)
);

alter table producttype comment '产品类型表';

INSERT INTO producttype values
(0,'灭火剂','包括泡沫灭火剂、水系灭火剂等'),
(0,'民用灭火器','包括手提式水基型灭火器、简易式水基型灭火器等'),
(0,'军用灭火器','包括军用推车式水基型灭火器、军用手提式灭火器等'),
(0,'警用灭火器','包括警用水基型灭火器等');

/*==============================================================*/
/* Table: recruit                                               */
/*==============================================================*/
create table recruit
(
   recruitid            int not null auto_increment,
   name                 char(32),
   recruitname          char(64) not null,
   age                  char(32) not null,
   workplace            char(32) not null,
   payment              char(32) not null,
   recruitnumber        char(32) not null,
   publishdate          date not null,
   deadline             date not null,
   duty                 text not null,
   requirement          text not null,
   primary key (recruitid)
);

alter table recruit comment '招聘信息表';

/*==============================================================*/
/* Table: recruitapply                                          */
/*==============================================================*/
create table recruitapply
(
   applyid              int not null auto_increment,
   recruitid            int not null,
   applyname            char(32) not null,
   applysex             bool,
   applynation          char(32),
   applynative          char(32),
   applyeducation       char(32) not null,
   applyemail           char(64),
   applyphone           char(32) not null,
   applyresume          text,
   applydatetime        datetime not null,
   primary key (applyid)
);

alter table recruitapply comment '招聘申请';

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   roleid               char(24) not null,
   rolename             char(24) not null,
   des                  char(255) not null,
   primary key (roleid)
);

alter table role comment '网站维护人员的角色表，包括普通管理员若干和一个全局唯一的超级管理员';

INSERT INTO role values
('master','超级管理员','具有最高权限且唯一，具有对管理员的管理权限'),
('admin','管理员','具有后台维护的权限以及普通用户的管理权限，也具有普通用户的功能权限');

/*==============================================================*/
/* Table: teamprocess                                           */
/*==============================================================*/
create table teamprocess
(
   processid            int not null auto_increment,
   name                 char(32),
   location             char(32) not null,
   pos                  blob,
   brief                char(255) not null,
   date                 date not null,
   image                mediumblob,
   primary key (processid)
);

alter table teamprocess comment '团队进程表';

alter table Datum add constraint FK_Reference_11 foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table business add constraint FK_business_manager foreign key (managername)
      references manager (name) on delete restrict on update restrict;

alter table carousel add constraint FK_carousel_manager foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table companyprocess add constraint FK_Reference_9 foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table introduction add constraint FK_introduction_manager foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table manager add constraint FK_manager_role foreign key (roleid)
      references role (roleid) on delete restrict on update restrict;

alter table master add constraint FK_master_role foreign key (roleid)
      references role (roleid) on delete restrict on update restrict;

alter table member add constraint FK_Reference_10 foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table news add constraint FK_news_manager foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table news add constraint FK_news_newstype foreign key (typeid)
      references newstype (typeid) on delete restrict on update restrict;

alter table product add constraint FK_Reference_12 foreign key (typeid)
      references producttype (typeid) on delete restrict on update restrict;

alter table product add constraint FK_Reference_13 foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table productparam add constraint FK_Reference_14 foreign key (productid)
      references product (productid) on delete restrict on update restrict;

alter table productpic add constraint FK_Reference_15 foreign key (productid)
      references product (productid) on delete restrict on update restrict;

alter table recruit add constraint FK_Reference_16 foreign key (name)
      references manager (name) on delete restrict on update restrict;

alter table recruitapply add constraint FK_Reference_17 foreign key (recruitid)
      references recruit (recruitid) on delete restrict on update restrict;

alter table teamprocess add constraint FK_teamprocess_manager foreign key (name)
      references manager (name) on delete restrict on update restrict;

