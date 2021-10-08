create table `v_admin_user`
(
    `id`          int(11) not null auto_increment,
    `username`    varchar(64)  not null default '' comment '用户名',
    `realname`    varchar(32)  not null default '' comment '真实姓名',
    `avatar`      varchar(125) not null default '' comment '头像',
    `status`      tinyint(4) not null default 0 comment '状态',
    `password`    varchar(32)  not null default '' comment '密码',
    `login_time`  timestamp    not null default '0000-00-00 00:00:00' comment '登录时间',
    `unlock_time` timestamp null comment '解锁',
    `create_time` timestamp    not null default current_timestamp comment '创建时间',
    `update_time` timestamp    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`)
) comment '管理员用户表';

insert into `v_admin_user` (`id`, `username`, `realname`, `avatar`, `status`, `password`, `login_time`, `create_time`,
                            `update_time`)
values (1, 'admin', '江大大',
        'https://lets-sys-back.oss-cn-guangzhou.aliyuncs.com/test/file/ececab4c-8a0c-495e-9c7a-b844cb2d6c18.png', 0,
        'e10adc3949ba59abbe56e057f20f883e', '2021-06-05 21:30:20', '2021-04-20 15:46:11', '2021-04-27 16:11:43'),
       (2, 'user', '盛大大',
        'https://lets-sys-back.oss-cn-guangzhou.aliyuncs.com/test/file/0d47a2cb-15a8-4985-b976-cb861a730b1f.gif', 0,
        'e10adc3949ba59abbe56e057f20f883e', '2021-05-30 11:07:42', '2021-04-21 17:03:48', '2021-04-27 16:10:04');

create table `v_permission`
(
    `id`          int(11) not null auto_increment,
    `pid`         int(11) not null default 0 comment '父id',
    `icon`        varchar(32)  not null default '' comment '图标',
    `name`        varchar(125) not null default '' comment '页面名称',
    `title`       varchar(125) not null default '' comment '菜单名称',
    `path`        varchar(125) not null default '' comment 'url路径',
    `component`   varchar(125) not null default '' comment '组件',
    `perms`       varchar(125) not null default '' comment '权限标识',
    `type`        tinyint(4) not null default 0 comment '类型',
    `sort`        int(4) not null default 0 comment '排序',
    `create_time` timestamp    not null default current_timestamp comment '创建时间',
    `update_time` timestamp    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`)
) comment '权限表';

create table `v_role`
(
    `id`          int(11) not null auto_increment,
    `role_name`   varchar(125) not null default '' comment '角色名称',
    `remarks`     varchar(255) not null default '' comment '角色描述',
    `create_time` timestamp    not null default current_timestamp comment '创建时间',
    `update_time` timestamp    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`)
) comment '角色表';

insert into `v_role` (`id`, `role_name`, `remarks`, `create_time`, `update_time`)
values (1, '超级管理员', '拥有全部权限', '2021-04-20 15:59:11', '2021-04-20 15:59:11'),
       (2, '管理员', '拥有绝大部分权限', '2021-04-20 15:59:40', '2021-04-20 15:59:40');

create table `v_role_permission`
(
    `role_id`       int(11) not null default 0,
    `permission_id` int(11) not null default 0
) comment '角色权限表';

create table `v_admin_user_role`
(
    `admin_user_id` int(11) not null default 0,
    `role_id`       int(11) not null default 0
) comment '用户角色表';

insert into `v_admin_user_role` (`admin_user_id`, `role_id`)
values (2, 2),
       (1, 1);

create table `v_login_log`
(
    `id`         int(11) not null auto_increment,
    `username`   varchar(64) not null default '' comment '用户名',
    `location`   varchar(50) not null default '' comment '登录地点',
    `ip`         varchar(50) not null default '' comment 'ip地址',
    `system`     varchar(50) not null default '' comment '操作系统',
    `browser`    varchar(50) not null default '' comment '浏览器',
    `login_time` timestamp   not null default current_timestamp comment '登录时间',
    primary key (`id`)
) comment '登录日志表';

create table `v_log`
(
    `id`          int(11) not null auto_increment,
    `username`    varchar(64)    not null default '' comment '操作用户',
    `operation`   text comment '操作内容',
    `time`        decimal(11, 0) not null default 0.0 comment '耗时(毫秒)',
    `method`      text comment '操作方法',
    `params`      text comment '方法参数',
    `ip`          varchar(64) null default '' comment '操作者ip',
    `location`    varchar(50) null default '' comment '操作地点',
    `type`        tinyint(4) not null default 0 comment '日志类型',
    `create_time` timestamp      not null default current_timestamp comment '创建时间',
    primary key (`id`)
) comment '操作日志表';

create table `v_batch_export_record`
(
    `id`                int(11) not null auto_increment,
    `user_id`           int(11) not null default 0 comment '操作用户id',
    `username`          varchar(64)    not null default '' comment '操作用户',
    `file_name`         varchar(64)    not null default '' comment '文件名',
    `file_size`         varchar(32)    not null default '' comment '文件大小',
    `storage_file_name` varchar(125)   not null default '' comment '存储的文件名',
    `url`               varchar(125)   not null default '' comment '下载地址',
    `time`              decimal(11, 0) not null default 0.0 comment '耗时(毫秒)',
    `status`            tinyint(4) not null default '0' comment '状态',
    `exception`         text comment '异常原因',
    `create_time`       timestamp      not null default current_timestamp comment '创建时间',
    primary key (`id`)
) comment '批量导出记录表';

create table `v_dict_type`
(
    `id`          int(0) not null auto_increment,
    `name`        varchar(125) not null default '' comment '字典名称',
    `type`        varchar(125) not null default '' comment '字典类型',
    `create_time` timestamp(0) not null default current_timestamp(0) comment '创建时间',
    `update_time` timestamp(0) not null default current_timestamp(0) on update current_timestamp (0) comment '更新时间',
    primary key (`id`)
)comment '字典类型';

create table `v_dict_data`
(
    `id`          int(0) not null auto_increment,
    `sort`        int(0) not null default 0 comment '字典排序',
    `label`       varchar(125) not null default '' comment '字典标签',
    `value`       varchar(125) not null default '' comment '字典键值',
    `type`        varchar(125) not null default '' comment '字典类型',
    `status`      tinyint(0) not null default 0 comment '状态：0正常 1停用',
    `extend`      varchar(625) not null default '{}' comment '扩展',
    `create_time` timestamp(0) not null default current_timestamp(0) comment '创建时间',
    `update_time` timestamp(0) not null default current_timestamp(0) on update current_timestamp (0) comment '更新时间',
    primary key (`id`)
)comment '字典数据';

INSERT INTO `v_dict_type`(`id`, `name`, `type`, `create_time`, `update_time`)
VALUES (1, '系统配置', 'SYSTEM_CONFIGURE', '2021-09-27 10:45:27', '2021-09-27 10:45:27');
INSERT INTO `v_dict_data`(`id`, `sort`, `label`, `value`, `type`, `status`, `extend`, `create_time`,
                          `update_time`)
VALUES (1, 0, '密码策略', 'password-strategy', 'SYSTEM_CONFIGURE', 0,
        '{\"failLoginCount\":2,\"failLoginTime\":30,\"failLoginTimeType\":2,\"passwordComplexity\":[0,1,3],\"passwordMixLength\":6,\"unlockTime\":30,\"unlockTimeType\":1}',
        '2021-09-27 10:46:31', '2021-09-27 16:31:12');

create table `t_user`
(
    `id`          int(11) not null auto_increment,
    `username`    varchar(64) not null default '' comment '用户名',
    `password`    varchar(32) not null comment '密码',
    `status`      tinyint(4) not null default 0 comment '状态',
    `create_time` timestamp   not null default current_timestamp comment '创建时间',
    primary key (`id`)
) comment '用户表';

create table `t_tinymce`
(
    `id`      int(11) not null auto_increment,
    `content` text comment '内容',
    primary key (`id`)
) comment '富文本表';

insert into `t_tinymce`(`id`, `content`)
values (1,
        '<h1 style=\"text-align: center;\">welcome to the tinymce demo!</h1><p style=\"text-align: center; font-size: 15px;\"><img title=\"tinymce logo\" src=\"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageview2/1/w/80/h/80\" alt=\"tinymce logo\" width=\"110\" height=\"97\" /><ul>\n        <li>our <a href=\"//www.tinymce.com/docs/\">documentation</a> is a great resource for learning how to configure tinymce.</li><li>have a specific question? visit the <a href=\"https://community.tinymce.com/forum/\">community forum</a>.</li><li>we also offer enterprise grade support as part of <a href=\"https://tinymce.com/pricing\">tinymce premium subscriptions</a>.</li>\n      </ul>');
