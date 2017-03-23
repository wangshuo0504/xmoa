
/*==============================================================*/
/* Table: T_SYS_MODULE      资源表                              */
/*==============================================================*/
create  table T_SYS_MODULE  (
   FID                  VARCHAR2(32)     not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   URL                  VARCHAR2(100),
   ICON                 VARCHAR2(100),
   PARENT_ID            VARCHAR2(32),
   REMARK               VARCHAR2(200),
   SORT_NO              VARCHAR2(50),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_SYS_MODULE primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_DEPT    单位表                                  */
/*==============================================================*/
create table T_SYS_DEPT  (
   FID                  VARCHAR2(32)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   SHORT_NAME           VARCHAR2(50),
   PARENT_ID            VARCHAR2(32),
   DEPT_MAIL            VARCHAR2(100),
   LEAD_ID              VARCHAR2(32),
   ADMIN_ID             VARCHAR2(32),
   REMARK               VARCHAR2(200),
   SORT_NO              VARCHAR2(50),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_SYS_DEPT primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_GOURP     群组表                                */
/*==============================================================*/
create table T_SYS_GROUP  (
   FID                  VARCHAR2(32)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   REMARK               VARCHAR2(200),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_SYS_GROUP primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_ACTIONRIGHT    功能权限表                       */
/*==============================================================*/
create table T_SYS_ACTIONRIGHT  (
   FID                  VARCHAR2(32)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   MODULE_ID            VARCHAR2(32),
   DESCRIPTION          VARCHAR2(200),
   REMARK               VARCHAR2(200),
   constraint PK_T_SYS_ACTIONRIGHT primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_RTDEPTROLE   单位角色关系表                     */
/*==============================================================*/
create table T_SYS_RTDEPTROLE  (
   DEPT_ID              VARCHAR2(32)                    not null,
   ROLE_ID              VARCHAR2(32)                    not null,
   constraint PK_T_SYS_RTDEPTROLE primary key (DEPT_ID, ROLE_ID)
);


/*==============================================================*/
/* Table: T_SYS_RTGROUPUSER   群组用户关系表                    */
/*==============================================================*/
create table T_SYS_RTGROUPUSER  (
   GROUP_ID             VARCHAR2(32)                    not null,
   USER_ID              VARCHAR2(32)                    not null,
   SORT_NO              VARCHAR2(32),
   constraint PK_T_SYS_RTGROUPUSER primary key (GROUP_ID, USER_ID)
);

/*==============================================================*/
/* Table: T_SYS_RTGROUPROLE  群组角色关系表                     */
/*==============================================================*/
create table T_SYS_RTGROUPROLE  (
   GROUP_ID             VARCHAR2(32)                    not null,
   ROLE_ID              VARCHAR2(32)                    not null,
   constraint PK_T_SYS_RTGROUPROLE primary key (GROUP_ID, ROLE_ID)
);


/*==============================================================*/
/* Table: T_SYS_USER     用户表                                 */
/*==============================================================*/
create table T_SYS_USER  (
   FID                  VARCHAR2(32)                    not null,
   USER_NAME            VARCHAR2(100),
   PASSWORD             VARCHAR2(100),
   NAME                 VARCHAR2(100),
   MAIL                 VARCHAR2(100),
   PHONE                VARCHAR2(100),
   DEPT_ID              VARCHAR2(32),
   STATUS               VARCHAR2(100),
   DUTY                 VARCHAR2(100),
   FAX                  VARCHAR2(100),
   OFFICE_TEL           VARCHAR2(100),
   OFFICE_ADDR          VARCHAR2(200),
   HOME_TEL             VARCHAR2(100),
   HOME_ADDR            VARCHAR2(200),
   REMARK               VARCHAR2(200),
   SORT_NO              VARCHAR2(50),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_SYS_USER primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_RTROLEACTION     角色权限表                     */
/*==============================================================*/
create table T_SYS_RTROLEACTION  (
   ROLE_ID              VARCHAR2(32)                    not null,
   ACTION_ID            VARCHAR2(32)                    not null,
   constraint PK_T_SYS_RTROLEACTION primary key (ROLE_ID, ACTION_ID)
);

/*==============================================================*/
/* Table: T_SYS_RTUSERROLE   用户角色关系表                     */
/*==============================================================*/
create table T_SYS_RTUSERROLE  (
   USER_ID              VARCHAR2(32)                    not null,
   ROLE_ID              VARCHAR2(32)                    not null,
   ROLE_TYPE            VARCHAR2(50)                    not null,
   constraint PK_T_SYS_RTUSERROLE primary key (USER_ID, ROLE_ID, ROLE_TYPE)
);

/*==============================================================*/
/* Table: T_SYS_ROLESYSTEM    系统角色表                        */
/*==============================================================*/
create table T_SYS_ROLESYSTEM  (
   FID                  VARCHAR2(32)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   REMARK               VARCHAR2(200),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_SYS_ROLESYSTEM primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_ROLEBUSINESS    业务角色表                      */
/*==============================================================*/
create table T_SYS_ROLEBUSINESS  (
   FID                  VARCHAR2(32)                    not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   REMARK               VARCHAR2(200),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_SYS_ROLEBUSINESS primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_DICTTYPE   基础代码类型                         */
/*==============================================================*/
create table T_SYS_DICTTYPE  (
   FID                  VARCHAR2(32)                    not null,
   TYPE                 VARCHAR2(100),
   NAME                 VARCHAR2(100),
   REMARK               VARCHAR2(200),
   constraint PK_T_SYS_DICTTYPE primary key (FID)
);

/*==============================================================*/
/* Table: T_SYS_DICTDATA   基础代码数据表                       */
/*==============================================================*/
create table T_SYS_DICTDATA  (
   FID                  VARCHAR2(32)                    not null,
   TYPE                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   NAME                 VARCHAR2(100),
   ENAME                VARCHAR2(100),
   PARENT_ID                 VARCHAR2(32),
   DEFAULT_VALUE        VARCHAR2(100),
   VALUE                VARCHAR2(100),
   CREATE_USER          VARCHAR2(32),
   CREATE_TIME          TIMESTAMP,
   UPDATE_USER          VARCHAR2(32),
   UPDATE_TIME          TIMESTAMP
);


/*==============================================================*/
/* Table: T_SYS_LOG   系统操作日志表                            */
/*==============================================================*/
create table T_SYS_LOG  (
   FID                  VARCHAR2(32)                    not null,
   USER_ID              VARCHAR2(32),
   TIME                 TIMESTAMP,
   TYPE                 VARCHAR2(100),
   DETAIL               VARCHAR2(200),
   constraint PK_T_SYS_LOG primary key (FID)
);
