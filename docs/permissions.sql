INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (1, 0, 'el-icon-setting', '系统管理', '系统管理', '/sys', 'Layout', '_sys', 0, 0, '2021-04-26 15:47:21',
        '2021-04-27 14:04:54');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (2, 1, '', '管理员用户', '管理员用户', 'admin-user', '/sys/admin-user', 'sys_admin-user', 0, 1, '2021-04-26 15:49:01',
        '2021-05-13 14:38:45');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (4, 0, 'el-icon-sunrise', '系统监控', '系统监控', '/monitor', 'Layout', '_monitor', 0, 1, '2021-04-26 15:49:55',
        '2021-05-13 14:38:51');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (5, 4, '', '登录日志', '登录日志', 'login-log', '/monitor/login-log', 'monitor_login-log', 0, 1, '2021-04-26 15:50:46',
        '2021-05-13 14:38:51');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (6, 2, '', '列表', '', '', '', 'admin-user:list', 1, 0, '2021-04-26 15:52:47', '2021-05-10 10:41:48');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (7, 2, '', '新增', '', '', '', 'admin-user:add', 1, 0, '2021-04-26 15:53:03', '2021-05-10 10:41:53');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (8, 2, '', '编辑', '', '', '', 'admin-user:update', 1, 0, '2021-04-26 15:53:43', '2021-05-10 10:41:55');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (9, 2, '', '修改密码', '', '', '', 'password:update', 1, 0, '2021-04-26 15:54:35', '2021-05-10 10:42:01');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (10, 1, '', '菜单管理', '菜单管理', 'menu', '/sys/menu', 'sys_menu', 0, 0, '2021-04-26 17:25:18', '2021-04-27 14:04:31');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (17, 1, '', '角色权限', '角色权限', 'role', '/sys/role', 'sys_role', 0, 2, '2021-04-27 00:40:13', '2021-04-27 14:05:53');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (18, 17, '', '列表', '', '', '', 'role:list', 1, 0, '2021-04-27 00:41:16', '2021-05-10 10:42:07');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (19, 17, '', '新增', '', '', '', 'role:add', 1, 0, '2021-04-27 00:42:53', '2021-05-10 10:42:09');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (20, 17, '', '编辑', '', '', '', 'role:update', 1, 0, '2021-04-27 00:43:16', '2021-05-10 10:42:13');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (21, 4, '', '在线用户', '在线用户', 'online-user', '/monitor/online-user', 'monitor_online-user', 0, 2,
        '2021-04-27 00:48:46', '2021-04-27 14:06:22');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (22, 4, '', '系统日志', '系统日志', 'sys-log', '/monitor/sys-log', 'monitor_sys-log', 0, 0, '2021-04-27 00:49:19',
        '2021-04-27 14:06:11');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (24, 10, '', '新增', '', '', '', 'menu:add', 1, 0, '2021-04-27 03:39:09', '2021-05-13 14:39:26');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (25, 10, '', '编辑', '', '', '', 'menu:update', 1, 0, '2021-04-27 03:39:23', '2021-05-13 14:39:27');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (26, 10, '', '删除', '', '', '', 'menu:delete', 1, 0, '2021-04-27 03:39:49', '2021-05-13 14:39:28');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (28, 22, '', '列表', '', '', '', 'sys-log:list', 1, 0, '2021-04-27 03:43:01', '2021-05-13 14:39:28');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (29, 22, '', '删除', '', '', '', 'sys-log:delete', 1, 0, '2021-04-27 03:43:29', '2021-05-13 14:39:28');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (30, 5, '', '列表', '', '', '', 'login-log:list', 1, 0, '2021-04-27 03:45:00', '2021-05-13 14:39:29');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (31, 5, '', '删除', '', '', '', 'login-log:delete', 1, 0, '2021-04-27 03:45:13', '2021-05-13 14:39:29');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (32, 21, '', '列表', '', '', '', 'online-user:list', 1, 0, '2021-04-27 03:45:55', '2021-05-13 14:39:30');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (33, 21, '', '踢出用户', '', '', '', 'online-user:kickout', 1, 0, '2021-04-27 03:46:09', '2021-05-13 14:39:31');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (34, 0, 'el-icon-guide', '其他模块', '其他模块', '/other', 'Layout', '_other', 0, 2, '2021-04-27 16:44:19',
        '2021-05-29 00:37:30');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (35, 34, '', '富文本', '富文本', 'tinymce', '/other/tinymce', 'other_tinymce', 0, 0, '2021-04-29 23:49:57',
        '2021-04-29 23:50:13');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (36, 34, '', '返回顶部', '返回顶部', 'back-to-top', '/other/back-to-top', 'other_back-to-top', 0, 1,
        '2021-04-30 11:13:06', '2021-04-30 11:48:26');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (37, 34, '', '数字滑动', '数字滑动', 'count-to', '/other/count-to', 'other_count-to', 0, 2, '2021-04-30 11:48:22',
        '2021-04-30 11:48:22');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (38, 34, '', '图标', '图标', 'icon', '/other/icons', 'other_icons', 0, 3, '2021-04-30 13:09:47',
        '2021-04-30 14:27:03');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (39, 34, '', '复制', '复制', 'clipboard', '/other/clipboard', 'other_clipboard', 0, 4, '2021-04-30 15:10:56',
        '2021-04-30 15:10:56');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (40, 34, 'excel', 'Excel', 'Excel', 'excel', '/other/excel/index', 'other_excel', 0, 5, '2021-04-30 15:33:30',
        '2021-04-30 15:54:02');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (41, 40, '', '导出 Excel', '导出 Excel', 'export', '/other/excel/export-excel', 'other_excel_export', 0, 0,
        '2021-04-30 15:35:10', '2021-04-30 15:35:10');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (43, 40, '', '导出 多级表头', '导出 多级表头', 'merge-header', '/other/excel/merge-header', 'other_excel_merge-header', 0, 1,
        '2021-04-30 17:31:17', '2021-04-30 17:54:37');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (44, 34, 'zip', 'Zip', 'Zip', 'zip', '/other/zip', 'other_zip', 0, 6, '2021-04-30 17:47:53',
        '2021-04-30 17:47:53');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (45, 40, '', '上传 Excel', '上传 Excel', 'upload-excel', '/other/excel/upload-excel', 'other_upload-excel', 0, 0,
        '2021-04-30 17:56:02', '2021-04-30 17:56:02');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (46, 22, '', '导出', '', '', '', 'sys-log:export', 1, 0, '2021-05-07 12:27:00', '2021-05-13 14:39:42');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (56, 22, '', '批量导出', '', '', '', 'sys-log:batch-export', 1, 0, '2021-05-28 21:05:23', '2021-05-28 21:05:23');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (57, 5, '', '批量导出', '', '', '', 'login-log:batch-export', 1, 0, '2021-05-28 21:09:06', '2021-05-28 21:09:06');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (58, 0, '', '任务中心', '', '', '', '', 2, 3, '2021-05-29 00:48:59', '2021-05-29 00:48:59');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (59, 58, '', '列表', '', '', '', 'task:list', 1, 0, '2021-05-29 00:59:06', '2021-05-29 00:59:06');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (60, 58, '', '下载', '', '', '', 'task:download', 1, 1, '2021-05-29 01:00:07', '2021-05-29 01:00:07');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (61, 58, '', '取消', '', '', '', 'task:cancel', 1, 2, '2021-05-29 01:00:27', '2021-05-29 01:00:27');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (62, 58, '', '删除', '', '', '', 'task:delete', 1, 3, '2021-05-29 01:00:45', '2021-05-29 01:00:45');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (63, 34, '', '引导页', '引导页', 'guide', '/other/guide/index', 'ohter_guide', 0, 7, '2021-06-03 15:17:32',
        '2021-06-03 15:24:53');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (64, 34, '', '权限指令', '权限指令', 'permission-directive', '/other/permission-directive', 'other_permission-directive',
        0, 8, '2021-06-03 17:09:32', '2021-06-03 17:09:32');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (65, 1, '', '字典管理', '字典管理', 'dict', '/sys/dict', 'sys_dict', 0, 3, '2021-07-22 05:10:24', '2021-07-22 05:10:58');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (66, 65, '', '列表', '', '', '', 'dict:list', 1, 0, '2021-07-22 19:10:08', '2021-07-22 19:10:08');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (67, 65, '', '新增', '', '', '', 'dict:add', 1, 1, '2021-07-22 19:10:27', '2021-07-22 19:10:27');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (68, 65, '', '编辑', '', '', '', 'dict:update', 1, 2, '2021-07-22 19:10:42', '2021-07-22 19:10:42');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (69, 65, '', '删除', '', '', '', 'dict:delete', 1, 3, '2021-07-22 19:10:56', '2021-07-22 19:10:56');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (70, 65, '', '数据列表', '', '', '', 'dict:data-list', 1, 4, '2021-07-22 19:11:16', '2021-07-22 19:11:16');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (71, 65, '', '数据新增', '', '', '', 'dict:data-add', 1, 5, '2021-07-22 19:11:32', '2021-07-22 19:11:32');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (72, 65, '', '数据编辑', '', '', '', 'dict:data-update', 1, 6, '2021-07-22 19:11:48', '2021-07-22 19:11:48');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (73, 65, '', '数据删除', '', '', '', 'dict:data-delete', 1, 7, '2021-07-22 19:12:02', '2021-07-22 19:12:02');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (74, 34, '', 'json编辑器', 'json编辑器', 'json-editor-demo', '/other/json-editor-demo', 'other_json-editor-demo', 0, 9,
        '2021-08-03 15:02:48', '2021-08-03 15:04:09');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (75, 34, '', '高德地图', '高德地图', 'gdmap', '/other/gdmap', 'other_gdmap', 0, 10, '2021-08-12 16:20:43',
        '2021-08-13 10:55:11');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (76, 34, '', '百度地图', '百度地图', 'bdmap', '/other/bdmap', 'other_bdmap', 0, 11, '2021-08-13 11:11:26',
        '2021-08-13 11:11:26');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (77, 34, '', '邮件验证码', '邮件验证码', 'mail-captcha', '/other/mail-captcha', 'other_mail-captcha', 0, 12,
        '2021-08-13 14:39:44', '2021-08-13 14:39:44');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (78, 34, '', 'DPlayer播放器', 'DPlayer播放器', 'd-player', '/other/d-player', 'other_d-player', 0, 13,
        '2021-08-27 12:15:33', '2021-08-27 12:15:33');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (79, 34, '', '拖拽 Dialog', '拖拽 Dialog', 'drag-dialog', '/other/drag-dialog', 'other_drag-dialog', 0, 14,
        '2021-08-31 17:19:17', '2021-08-31 17:19:17');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (80, 34, '', '拖拽 Select', '拖拽 Select', 'drag-select', '/other/drag-select', 'other_drag-select', 0, 15,
        '2021-08-31 17:26:17', '2021-08-31 17:26:17');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (81, 34, '', '拖拽看板', '拖拽看板', 'drag-kanban', '/other/drag-kanban', 'other_drag-kanban', 0, 16,
        '2021-08-31 17:34:00', '2021-08-31 17:34:00');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (82, 34, '', '拖拽图片', '拖拽图片', 'drag-image', '/other/drag-image', 'other_drag-image', 0, 17, '2021-09-01 10:47:23',
        '2021-09-01 10:47:23');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (83, 34, '', '单图上传', '单图上传', 'single-upload', '/other/single-upload', 'other_single-upload', 0, 18,
        '2021-09-01 15:08:27', '2021-09-01 15:08:27');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (84, 34, '', '多图上传', '多图上传', 'multiple-upload', '/other/multiple-upload', 'other_multiple-upload', 0, 19,
        '2021-09-01 15:09:14', '2021-09-01 15:09:14');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (85, 1, '', '系统配置', '系统配置', 'configure', '/sys/configure', 'sys_configure', 0, 4, '2021-09-27 10:18:53',
        '2021-09-27 10:18:53');
INSERT INTO `vel`.`v_permission`(`id`, `pid`, `icon`, `name`, `title`, `path`, `component`, `perms`, `type`, `sort`,
                                 `create_time`, `update_time`)
VALUES (86, 85, '', '保存密码策略', '', '', '', 'configure:save-password-strategy', 1, 0, '2021-09-27 16:54:22',
        '2021-09-28 11:39:07');

INSERT INTO `v_role_permission` (`role_id`, `permission_id`)
VALUES (2, 4),
       (2, 22),
       (2, 28),
       (2, 29),
       (2, 46),
       (2, 5),
       (2, 30),
       (2, 21),
       (2, 32),
       (1, 1),
       (1, 10),
       (1, 24),
       (1, 25),
       (1, 26),
       (1, 2),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 17),
       (1, 18),
       (1, 19),
       (1, 20),
       (1, 4),
       (1, 22),
       (1, 28),
       (1, 29),
       (1, 46),
       (1, 56),
       (1, 5),
       (1, 30),
       (1, 31),
       (1, 57),
       (1, 21),
       (1, 32),
       (1, 33),
       (1, 34),
       (1, 35),
       (1, 36),
       (1, 37),
       (1, 38),
       (1, 39),
       (1, 40),
       (1, 41),
       (1, 45),
       (1, 43),
       (1, 44),
       (1, 63),
       (1, 64),
       (1, 58),
       (1, 59),
       (1, 60),
       (1, 61),
       (1, 62),
       (1, 63),
       (1, 64),
       (1, 65),
       (1, 66),
       (1, 67),
       (1, 68),
       (1, 69),
       (1, 70),
       (1, 71),
       (1, 72),
       (1, 73),
       (1, 74),
       (1, 75),
       (1, 76),
       (1, 77),
       (1, 78),
       (1, 79),
       (1, 80),
       (1, 81),
       (1, 82),
       (1, 83),
       (1, 84),
       (1, 85),
       (1, 86);

