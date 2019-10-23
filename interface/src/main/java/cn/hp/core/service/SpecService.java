package cn.hp.core.service;

import java.util.List;
import java.util.Map;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.SpecEntity;
import cn.hp.core.pojo.specification.Specification;

/**
 * 规格接口
 * @author 35814
 *
 */
public interface SpecService {
	//分页查询规格参数--无效
	public PageResult findPage(Integer page,Integer rows);
	//搜索
	public PageResult search(Specification specification, Integer page, Integer rows);
	//添加
	public void add(SpecEntity specEntity);
	//单查规格和规格参数
	public SpecEntity findOne(Long id);
	//修改
	public void update(SpecEntity specEntity);
	//删除
	public void delete(Long[] ids);
	//回显规格参数到商品管理模板管理添加
	public List<Map> selectOptionList();
}
