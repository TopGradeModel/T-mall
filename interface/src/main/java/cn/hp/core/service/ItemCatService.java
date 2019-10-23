package cn.hp.core.service;

import java.util.List;

import cn.hp.core.pojo.item.ItemCat;

/**
 * 商品分类模块
 * @author 35814
 *
 */
public interface ItemCatService {
	/**
	 * 根据上级ID返回列表
	 * @return
	 */
	public List<ItemCat> findByParentId(Long parentId);

	/**
	 * 根据parentId查询模板id
	 */
	public ItemCat findOne(Long id);

	/**	
	 * 	查询所有信息
	 * @return 所有分类信息
	 */
	public List<ItemCat> findAll();
	
}
