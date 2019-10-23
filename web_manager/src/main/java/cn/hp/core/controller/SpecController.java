package cn.hp.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.entity.SpecEntity;
import cn.hp.core.pojo.specification.Specification;
import cn.hp.core.service.SpecService;

/**
 * 规格controller
 * 
 * @author 35814
 *
 */
@RestController
@RequestMapping("/specification")
public class SpecController {
	// 分页查询-----无效
	@Reference
	private SpecService specService;

	@RequestMapping("/findPage")
	public PageResult findPage(Integer page, Integer rows) {
		PageResult pageResult = specService.findPage(page, rows);
		return pageResult;
	}

	// 搜索功能
	@RequestMapping("/search")
	public PageResult search(@RequestBody Specification specification, Integer page, Integer rows) {
		PageResult pageResult = specService.search(specification, page, rows);
		return pageResult;
	}

	// 添加功能
	@RequestMapping("/add")
	public Result add(@RequestBody SpecEntity specEntity) {
		try {
			specService.add(specEntity);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			return new Result(false, "添加失败");
		}
	}

	// 单查规格和规格参数
	@RequestMapping("/findOne")
	public SpecEntity findOne(Long id) {
		SpecEntity specEntity = specService.findOne(id);
		return specEntity;
	}

	// 更新规格和规格参数
	@RequestMapping("/update")
	public Result update(@RequestBody SpecEntity specEntity) {
		try {
			specService.update(specEntity);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			return new Result(false, "修改失败");
		}
	}
	//删除
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			specService.delete(ids);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			return new Result(false, "修改失败");
		}
	}
	//回显规格参数到商品管理模板管理
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		return specService.selectOptionList();
	}
	
	

}
