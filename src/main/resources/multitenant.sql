/* 字典主表  */
/*
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('7ff72e4a-def0-459a-8773-fa011ce7f40d', 'industryType', null, '行业规范文件类型', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('81a4ec1c-6d38-4b95-8014-2e152f3c4382', 'messageType', null, '消息类型', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('8c12ecac-e8c4-4cd1-bed2-bbd937b6569f', 'planStatus', null, '材料审批状态', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('9f29db0e-1cba-4267-8c9c-6a92397a3e34', 'sex', null, '人员性别', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('b0e29c18-5130-4eab-87a5-c492ca55b958', 'area', null, '项目管理所属区域', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('b45b1966-2a4a-405b-b3b5-71a2ad333098', 'projectStatus', null, '项目状态', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('c03e8bc0-a062-43c7-9f3a-14dd153a4a5f', 'ownOrLease', null, '设备自有或租赁', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('d02b5049-6a00-432d-9447-dd51feac6300', 'fileType', null, '文件类型', null);
INSERT INTO bdf3_dictionary (id_, code_, default_value_, name_, parent_id_) VALUES ('e39bf278-8580-4090-ad73-b7bda14530c0', 'messageStatus', null, '消息状态', null);
*/
/* 字典子表  */
/*
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('07542f3b-a569-49fb-a9e8-5303db5d7973', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '2', 2, null, '人员突发事件');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('0bd01fd8-4092-4812-a12a-9301b49955f0', 'b45b1966-2a4a-405b-b3b5-71a2ad333098', true, '0', 0, null, '进行中');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('0e612ce5-e1ef-4d82-a4d2-02fa649389ae', '8c12ecac-e8c4-4cd1-bed2-bbd937b6569f', true, '0', 0, null, '未提交');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('13abb9a5-d9f7-4457-9004-e12bb3297a6b', 'b45b1966-2a4a-405b-b3b5-71a2ad333098', true, '1', 1, null, '未完成');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('19c3f71f-e5f0-4a99-86bf-432ba320bf12', 'c03e8bc0-a062-43c7-9f3a-14dd153a4a5f', true, '租赁', 2, null, '租赁');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('23e9270e-cec4-46f4-b745-01e51bef4ec7', 'd02b5049-6a00-432d-9447-dd51feac6300', true, '5', 5, null, '施工报告');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('28437a4c-d4b4-4681-a0c2-adacc42834eb', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '东北', 4, null, '东北');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('2adc6dc5-e108-4c56-8a58-1f2e6f538c3f', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '西北', 1, null, '西北');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('2b919d27-fb6d-45d4-8810-59e368dfc3c2', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '8', 8, null, '成本风险');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('2cee2540-fb3c-4a70-92f1-6bb70743f064', '7ff72e4a-def0-459a-8773-fa011ce7f40d', true, '1', 1, null, '行业规范');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('3beedf7f-62ab-4319-b240-9ee3818e53db', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '西南', 2, null, '西南');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('40d5c2ab-fc26-4d3a-a5bc-9de35458902e', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '华北', 6, null, '华北');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('4402b040-ea2e-4c95-9ac2-51718411a645', 'b45b1966-2a4a-405b-b3b5-71a2ad333098', true, '2', 2, null, '已完成');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('4bc82ddb-0133-44c6-bf10-5ddc2ce81239', '9f29db0e-1cba-4267-8c9c-6a92397a3e34', true, '女', 1, null, '女');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('551bee38-527e-4012-9f06-68569f9b925a', 'd02b5049-6a00-432d-9447-dd51feac6300', true, '2', 2, null, '商务资料');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('599722e1-3a8a-4360-99fc-a6560a84174e', 'd02b5049-6a00-432d-9447-dd51feac6300', true, '1', 1, null, '资料管理');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('61e4d360-fb59-4522-9abd-16ede4120492', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '9', 9, null, '材料审批');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('6c65a0b5-0c93-4871-ab67-19a88ed19db1', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '7', 7, null, '技术风险');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('6dc34259-6a14-4580-ad3a-2047548a2416', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '国外', 9, null, '国外');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('83691489-bb55-418f-a580-aaac9e9a97fa', '9f29db0e-1cba-4267-8c9c-6a92397a3e34', true, '男', 0, null, '男');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('852fb203-9b0a-4c14-9e6e-87e825659ea0', 'e39bf278-8580-4090-ad73-b7bda14530c0', true, '0', 0, null, '未查看');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('88698ed2-a7c4-4a2e-8afe-9e4d75ddcd0c', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '1', 1, null, '普通消息');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('8ad34395-d70e-4ac1-af4d-dfbaab7ffce8', '8c12ecac-e8c4-4cd1-bed2-bbd937b6569f', true, '1', 1, null, '等待审批');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('9b60beba-5339-4c77-b5aa-339e714a6de7', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '华中', 5, null, '华中');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('c6c4435b-8601-4a81-a4e5-6618f32c2156', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '6', 6, null, '其他事件');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('ce42784f-b8b3-43bb-bcdb-0deee260fa0f', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '4', 4, null, '施工质量事件');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('d0a543ec-1b58-4001-8e5a-3fdc8ef19847', 'd02b5049-6a00-432d-9447-dd51feac6300', true, '0', 6, null, '其他');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('d147064b-47ac-4b99-9cb8-71bc57a2731a', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '华东', 7, null, '华东');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('dd17a1dd-d0c0-4519-9951-b4bb9aedd555', '7ff72e4a-def0-459a-8773-fa011ce7f40d', true, '2', 2, null, '软件');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('dfe8edbf-8551-4c68-9e1f-c3e35c2cc454', '8c12ecac-e8c4-4cd1-bed2-bbd937b6569f', true, '3', 3, null, '已审批（拒绝）');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('e04d1047-dd17-4be0-aaaf-19d4b200bb78', 'd02b5049-6a00-432d-9447-dd51feac6300', true, '3', 3, null, '技术资料');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('e0d926d7-4503-4bec-97c1-ec76d6d17cab', '8c12ecac-e8c4-4cd1-bed2-bbd937b6569f', true, '2', 2, null, '已审批（同意）');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('e16d8aae-3cf7-4065-91ae-c0e1b33b2656', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '东南', 3, null, '东南');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('f0c3562f-f4e7-4f76-99f6-6a1f51cc8702', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '3', 3, null, '自然灾害事件');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('f1d4e628-f75e-425a-b5b1-ac329da58d76', 'b0e29c18-5130-4eab-87a5-c492ca55b958', true, '华南', 8, null, '华南');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('f5888d7f-0972-4b07-8693-5fde1a18de59', '81a4ec1c-6d38-4b95-8014-2e152f3c4382', true, '5', 5, null, '现场安全事件');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('fa176f3f-3eb2-483e-aae9-eee143972068', 'd02b5049-6a00-432d-9447-dd51feac6300', true, '4', 4, null, '会议记录');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('fcf55dff-2d27-4331-a71a-8ca65cfb5c85', 'e39bf278-8580-4090-ad73-b7bda14530c0', true, '1', 1, null, '已查看');
INSERT INTO bdf3_dictionary_item (id_, dictionary_id_, enabled_, key_, order_, parent_id_, value_) VALUES ('ff1c688c-ba14-4468-9947-debdea65a0ea', 'c03e8bc0-a062-43c7-9f3a-14dd153a4a5f', true, '自有', 1, null, '自有');
*/

/*角色表*/
/*
INSERT INTO bdf3_role (id_, description_, name_) VALUES ('6373b3de-834a-4612-9837-21f058d02e59', null, '安全员');
INSERT INTO bdf3_role (id_, description_, name_) VALUES ('8e350314-5c8b-42fa-815e-54bb58dafd1c', null, '项目经理');
INSERT INTO bdf3_role (id_, description_, name_) VALUES ('bd6cd8b7-3c57-4552-aa60-492c0f04aff3', null, '资料员');
INSERT INTO bdf3_role (id_, description_, name_) VALUES ('db68d880-9cb8-4025-862a-d04f95b090bb', null, '系统管理员');
*/

/*组件按钮表*/
/*
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (13, 'addBtn', '新建消息', '新建消息', 'com.kuchi.geotechnical.view.message.MessageMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (14, 'addBtn', '添加行业规范', '新增', 'com.kuchi.geotechnical.view.industry.IndustryMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (15, 'downloadBtn', '行业规范下载', '下载', 'com.kuchi.geotechnical.view.industry.IndustryMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (16, 'deleteBtn', '删除行业规范', '删除', 'com.kuchi.geotechnical.view.industry.IndustryMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (17, 'showBtn', '查看', '查看', 'com.kuchi.geotechnical.view.industry.IndustryMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (21, 'addBtn', '新增', '新增', 'com.kuchi.geotechnical.view.sys.meterial.MeterialMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (22, 'modifyBtn', '修改', '修改', 'com.kuchi.geotechnical.view.sys.meterial.MeterialMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (23, 'deleteBtn', '删除', '删除', 'com.kuchi.geotechnical.view.sys.meterial.MeterialMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (24, 'addBtn', '新增', '新增', 'com.kuchi.geotechnical.view.sys.subcontract.SubContractMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (25, 'modifyBtn', '修改', '修改', 'com.kuchi.geotechnical.view.sys.subcontract.SubContractMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (26, 'deleteBtn', '删除', '删除', 'com.kuchi.geotechnical.view.sys.subcontract.SubContractMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (27, 'addBtn', '新增', '新增', 'com.kuchi.geotechnical.view.sys.dept.DeptMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (28, 'modifyBtn', '修改', '修改', 'com.kuchi.geotechnical.view.sys.dept.DeptMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (29, 'deleteBtn', '删除', '删除', 'com.kuchi.geotechnical.view.sys.dept.DeptMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (30, 'addBtn', '一键备份数据库', '一键备份数据库', 'com.kuchi.geotechnical.view.sys.backup.BackupMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (31, 'deleteBtn', '删除备份数据', '删除', 'com.kuchi.geotechnical.view.sys.backup.BackupMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (32, 'downloadBtn', '下载备份数据文件', '下载', 'com.kuchi.geotechnical.view.sys.backup.BackupMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (33, 'addBtn', '新增', '新增', 'com.kuchi.geotechnical.view.sys.user.UserMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (34, 'modifyBtn', '修改或查看详情', '详情', 'com.kuchi.geotechnical.view.sys.user.UserMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (35, 'deleteBtn', '删除', '删除', 'com.kuchi.geotechnical.view.sys.user.UserMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (36, 'resetBtn', '重置用户密码', '重置密码', 'com.kuchi.geotechnical.view.sys.user.UserMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (37, 'managerBtn', '赋予或取消管理员身份', '赋予或取消管理员身份', 'com.kuchi.geotechnical.view.sys.user.UserMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (38, 'addBtn', '新建项目', '新增项目', 'com.kuchi.geotechnical.view.project.ProjectMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (39, 'showBtn', '概况', '概况', 'com.kuchi.geotechnical.view.project.ProjectMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (40, 'deleteBtn', '删除', '删除', 'com.kuchi.geotechnical.view.project.ProjectMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (41, 'managerBtn', '管理', '管理', 'com.kuchi.geotechnical.view.project.ProjectMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (42, 'saveBtn', '保存', '保存', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (43, 'deleteBtn', '删除附件按钮', '删除', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (44, 'addBtn', '新增', '新增', 'com.kuchi.geotechnical.view.project.manager.subcontract.ProjectSubcontract.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (45, 'modifyBtn', '修改', '修改', 'com.kuchi.geotechnical.view.project.manager.subcontract.ProjectSubcontract.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (46, 'deleteBtn', '删除', '删除', 'com.kuchi.geotechnical.view.project.manager.subcontract.ProjectSubcontract.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (47, 'exportBtn', '导出', '导出', 'com.kuchi.geotechnical.view.project.manager.subcontract.ProjectSubcontract.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (48, 'exportBtn', '导出', '导出', 'com.kuchi.geotechnical.view.project.manager.log.ProjectLog.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (49, 'addFileBtn','添加附件', '添加附件', 'com.kuchi.geotechnical.view.project.manager.log.ProjectLog.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (50, 'deleteFileBtn', '删除附件', '删除', 'com.kuchi.geotechnical.view.project.manager.log.ProjectLog.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (51, 'saveBtn', '保存', '保存', 'com.kuchi.geotechnical.view.project.manager.log.ProjectLog.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (52, 'addPlanBtn', '添加材料计划', '添加材料计划', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (53, 'showPlanBtn', '查看或修改按钮', '查看', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (54, 'deletePlanBtn', '删除按钮', '删除', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (55, 'approvePlanBtn', '提交或撤回审批申请按钮', '提交或撤回按钮', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (56, 'addPlanFileBtn', '添加附件', '添加附件', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (57, 'deletePlanFileBtn', '删除附件按钮', '删除附件', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (59, 'deletePlanFileBtn', '删除计划附件按钮', '删除计划附件按钮', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (60, 'addPlanClBtn', '添加计划材料按钮', '添加计划材料按钮', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (61, 'modifyPlanClBtn', '修改计划材料按钮', '修改计划材料按钮', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (62, 'deletePlanClBtn', '删除计划材料按钮', '删除计划材料按钮', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (63, 'savePlanBtn', '保存材料计划按钮', '保存', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (65, 'savePlanBtn', '保存材料计划按钮', '保存材料计划', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (66, 'addUseClBtn', '添加材料清单按钮', '添加材料清单', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (67, 'showUseClBtn', '查看材料使用清单', '查看', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (68, 'addUseClFileBtn', '添加材料清单附件按钮', '添加附件', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (69, 'deleteUseClFileBtn', '删除材料清单附件按钮', '删除附件', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (70, 'addUseClSubBtn', '添加清单材料按钮', '添加材料', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (71, 'modifyUseClSubBtn', '修改清单材料按钮', '修改材料', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (72, 'deleteUseClSubBtn', '删除清单材料按钮', '删除材料', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (73, 'saveUseBtn', '保存清单按钮', '保存清单', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (74, 'addBtn', '新增资料按钮', '新增', 'com.kuchi.geotechnical.view.project.manager.datum.ProjectDatum.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (75, 'deleteBtn', '删除资料按钮', '删除', 'com.kuchi.geotechnical.view.project.manager.datum.ProjectDatum.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (76, 'downloadBtn', '下载按钮', '下载', 'com.kuchi.geotechnical.view.project.manager.datum.ProjectDatum.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (77, 'addBtn', '新增人员按钮', '新增', 'com.kuchi.geotechnical.view.project.manager.person.ProjectPerson.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (78, 'modifyBtn', '修改人员信息按钮', '修改', 'com.kuchi.geotechnical.view.project.manager.person.ProjectPerson.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (79, 'deleteBtn', '删除人员信息按钮', '删除', 'com.kuchi.geotechnical.view.project.manager.person.ProjectPerson.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (80, 'addBtn', '新增设备按钮', '新增', 'com.kuchi.geotechnical.view.project.manager.facility.ProjectFacility.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (81, 'modifyBtn', '修改设备信息按钮', '修改', 'com.kuchi.geotechnical.view.project.manager.facility.ProjectFacility.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (82, 'deleteBtn', '删除按钮', '删除', 'com.kuchi.geotechnical.view.project.manager.facility.ProjectFacility.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (84, 'saveTopBtn', '保存交底书按钮', '保存交底书', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (85, 'uploadTopBtn', '上传交底书附件按钮', '上传附件', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (86, 'deleteTopFileBtn', '删除交底书附加', '删除附件', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (87, 'showTopFileBtn', '查看交底书附件按钮', '查看附件', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (89, 'addTopBtn', '编辑交底书按钮', '编辑', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (90, 'addSubBtn', '新增违规记录按钮', '新增违规记录', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (91, 'modifySunBtn', '修改违规记录按钮', '修改', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (92, 'deleteSubBtn', '删除违规记录按钮', '删除', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (93, 'deleteSubFileBtn', '删除记录附件按钮', '删除记录附件', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (94, 'showSubFileBtn', '查看记录附件按钮', '查看记录附件', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (95, 'addBtn', '新增按钮', '新增', 'com.kuchi.geotechnical.view.sys.equipment.EquipmentMaintain.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (96, 'modifyBtn', '修改按钮', '修改', 'com.kuchi.geotechnical.view.sys.equipment.EquipmentMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (97, 'deleteBtn', '删除按钮', '删除', 'com.kuchi.geotechnical.view.sys.equipment.EquipmentMaintain.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (98, 'addFileBtn', '添加附件按钮', '添加附件', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (99, 'showBtn', '查看资料按钮', '查看', 'com.kuchi.geotechnical.view.project.manager.datum.ProjectDatum.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (100, 'exportPlanBtn', '导出材料计划按钮', '导出', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (101, 'exportUseBtn', '导出清单按钮', '导出清单', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (102, 'addWorkAmountBtn', '新增工程量按钮', '新增工程量', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d', 'Button');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (103, 'modifyWorkAmountBtn', '修改工程量按钮', '修改', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, url_path_, type_) VALUES (104, 'deleteWorkAmountBtn', '删除工程量按钮', '删除工程量', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d', 'Label');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (105, 'downloadTopFileBtn', '交底书附件下载按钮', '交底书附件下载', 'Label', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (106, 'downloadDownFileBtn', '记录附件下载按钮', '记录附件下载', 'Label', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (107, 'deleteUseClBtn', '删除材料清单按钮', '删除材料清单', 'Label', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (108, 'addExecuteBtn', '新增材料执行分析', '添加材料执行分析', 'Button', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (109, 'showExecuteClBtn', '查看材料执行分析按钮', '查看材料执行分析', 'Label', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (110, 'deleteExecuteClBtn', '删除材料执行分析按钮', '删除材料执行分析', 'Label', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d');
INSERT INTO custom_component (id_, component_id_, description_, name_, type_, url_path_) VALUES (111, 'itemBtn', '详情按钮', '详情', 'Label', 'com.kuchi.geotechnical.view.project.manager.subcontract.ProjectSubcontract.d');
*/

/* 菜单表 */
/*
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('01770aac-af0a-4e9f-8da2-ff556b43aa47', null, 'fa fa-ban', '资料管理', true, 4, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.datum.ProjectDatum.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('07efe0f8-1d7c-483c-bc3c-54e87f9f285b', null, 'fa fa-user', '人员管理', true, 5, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.user.UserMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('1491d097-0795-4a0f-8608-7b452f8ac2be', null, 'fa fa-bars', '菜单管理', true, 0, '28d3203a-b1c7-42cd-9c90-2015a6c20464', 'bdf3.security.ui.view.UrlMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('e9db79a1-571a-4804-9111-ae9b17680b95', null, 'image', '项目分布图', true, 1, null, 'com.kuchi.geotechnical.view.project.map.CompanyMap.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('2f858c99-1253-498b-801d-aa5c4f021542', null, 'fa fa-user-secret', '角色管理', true, 6, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.role.RolePermissionMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('31079a66-fabd-4186-9aa3-eab84ac20ee6', null, 'fa fa-asterisk', '安全管理', true, 7, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.safety.ProjectSafety.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('328d6a2a-3acf-4501-b621-5687f1b057e1', null, 'fa fa-laptop', '设备品类管理', true, 0, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.equipment.EquipmentMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('350369ad-6867-4349-be25-c7696f838fc1', null, 'fa fa-braille', '项目信息', true, 0, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.info.ProjectDetails.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('4523af08-01f0-44f5-be89-3b2241da0e9e', null, 'image', '项目列表', true, 3, null, 'com.kuchi.geotechnical.view.project.ProjectMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('46f0ee9b-04a8-41f2-9008-81efa3fa2fe1', null, 'fa fa-graduation-cap', '角色分配', true, 7, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.role.RoleAssignmentMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('4759e1c7-286c-4f74-a808-a2fc5665c5e8', null, 'fa fa-tree', '材料品类管理', true, 1, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.meterial.MeterialMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('6fd00879-07f0-4e30-8109-c405fead4d30', null, 'fa fa-clock-o', '日志查询', true, 9, '82d375d1-c24e-402c-996d-a8be7225e29d', 'bdf3.log.view.LogInfoMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('76b7e353-b743-40cc-b7aa-85666a8e66f3', null, 'image', '行业规范', true, 5, null, 'com.kuchi.geotechnical.view.industry.IndustryMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('82d375d1-c24e-402c-996d-a8be7225e29d', null, 'image', '系统维护', true, 6, null, 'com.kuchi.geotechnical.view.sys.equipment.EquipmentMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('317952ad-a481-45e4-aa5b-d880a8fd51f8', null, 'image', '消息管理', true, 4, null, 'com.kuchi.geotechnical.view.message.MessageMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('8b9f683f-b8e6-4eee-8cad-4fd8475860d4', null, 'fa fa-cubes', '分包商管理', true, 2, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.subcontract.SubContractMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('8c78af18-91b5-48f8-b733-2bc90de545de', null, 'fa fa-database', '数据管理', true, 4, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.backup.BackupMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('8d4d885e-154a-4747-983a-8a9960a1372b', null, 'fa fa-audio-description', '施工日志', true, 2, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.log.ProjectLog.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('92e3ef45-dd50-4c6c-aedc-68d5ec305ccd', null, 'fa fa-users', '分公司管理', true, 3, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.dept.DeptMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('94c1b824-c585-4d76-a797-ae8b688115a6', null, 'fa fa-bath', '设备管理', true, 6, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.facility.ProjectFacility.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('a76c0fe0-8148-44e9-814f-8325a162e76c', null, 'fa fa-balance-scale', '材料管理', true, 3, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.materials.Materials.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('a9e2927d-f99d-46c8-953f-aef65019a1d3', null, 'fa fa-at', '分包管理', true, 1, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.subcontract.ProjectSubcontract.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('c617fba4-244c-4baf-afc0-fb2bcc5ddccb', null, 'fa fa-assistive-listening-systems', '人员管理', true, 5, '4523af08-01f0-44f5-be89-3b2241da0e9e', 'com.kuchi.geotechnical.view.project.manager.person.ProjectPerson.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('c7cb63b4-eb12-4d72-b943-aac6acbd4f6c', null, 'fa fa-book', '字典管理', true, 8, '82d375d1-c24e-402c-996d-a8be7225e29d', 'com.kuchi.geotechnical.view.sys.dict.DictionaryMaintain.d');
INSERT INTO bdf3_url (id_, description_, icon_, name_, navigable_, order_, parent_id_, path_) VALUES ('ef4f7636-5018-47aa-9825-c045a88e8e57', null, 'image', '项目进度', true, 2, null, 'com.kuchi.geotechnical.view.project.progress.ProjectProgress.d');
*/

