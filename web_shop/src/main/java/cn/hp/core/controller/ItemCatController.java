package cn.hp.core.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.item.ItemCat;
import cn.hp.core.service.ItemCatService;

/**
 * 商品添加模块
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
	@Reference
	private ItemCatService itemCatService;

	/**
	 * 根据上级ID查询列表
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/findByParentId")
	public List<ItemCat> findByParentId(Long parentId) {
		return itemCatService.findByParentId(parentId);
	}
	
	/**
	 * 根据parentId查询模板id
	 */
	@RequestMapping("/findOne")
	public ItemCat findOne(Long id) {
		return itemCatService.findOne(id);
	}
	/**
	 * 查询所有分类信息
	 */
	@RequestMapping("/findAll")
	public List<ItemCat> findAll() {
		return itemCatService.findAll();
	}

}
