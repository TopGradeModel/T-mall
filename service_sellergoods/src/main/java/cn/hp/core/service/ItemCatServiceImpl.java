package cn.hp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.hp.core.dao.item.ItemCatDao;
import cn.hp.core.pojo.item.ItemCat;
import cn.hp.core.pojo.item.ItemCatQuery;
import cn.hp.core.pojo.item.ItemCatQuery.Criteria;

/**
 * 商品分类模块
 * @author 35814
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatDao itemCatDao;
	/**
	 * 根据上级ID查询列表
	 */
	@Override
	public List<ItemCat> findByParentId(Long parentId) {
		ItemCatQuery itemCatQuery = new ItemCatQuery();
		Criteria criteria = itemCatQuery.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		return  itemCatDao.selectByExample(itemCatQuery);		

	}
	/* 
	 * 根据parentId查询模板id
	 */
	public ItemCat findOne(Long id) {
		return itemCatDao.selectByPrimaryKey(id);
	}
	/* 
	 * 查询所有分类信息
	 */
	@Override
	public List<ItemCat> findAll() {
		return itemCatDao.selectByExample(null);
	}

	

}
