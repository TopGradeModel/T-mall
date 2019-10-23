package cn.hp.core.service;

import java.util.List;
import java.util.Map;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.good.Brand;

public interface BrandService {
	//查询品牌
	public List<Brand> findAll();
	//查询品牌分页
	public PageResult findPage(Integer page, Integer rows);
	//添加
	public void add(Brand brand);
	//修改
	public void update(Brand brand);
	//单查
	public Brand findOne(Long id);
	//删除
	public void delete(Long[] ids);
	//搜索
	public PageResult search(Brand brand, Integer page, Integer rows);
	//回显品牌信息到模板管理新增界面
	public List<Map> selectOptionList();
	
}
