package cn.hp.core.service;

import java.util.List;
import java.util.Map;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.template.TypeTemplate;

/**
 * 模板管理模块
 * @author 35814
 *
 */
public interface TemplateService {
	//搜索查询
	PageResult findPage(TypeTemplate typeTemplate, Integer page, Integer rows);
	//添加模板
	void add(TypeTemplate template);
	//删除模板
	void delete(Long[] ids);
	//回显模板信息到修改界面
	TypeTemplate findOne(Long id);
	//修改模板
	void update(TypeTemplate template);
	//查询规格集合和规格选项集合
	List<Map> findBySpecList(Long id);
}
