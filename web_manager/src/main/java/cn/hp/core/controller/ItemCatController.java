package cn.hp.core.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.item.ItemCat;
import cn.hp.core.service.ItemCatService;

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
	 * 查询所有分类信息
	 */
	@RequestMapping("/findAll")
	public List<ItemCat> findAll() {
		return itemCatService.findAll();
	}

}
