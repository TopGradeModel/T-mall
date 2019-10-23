package cn.hp.core.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.template.TypeTemplate;
import cn.hp.core.service.TemplateService;

/**
 * 模板管理模块
 * @author 35814
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {
	@Reference
	private TemplateService templateService;
	//搜索按钮查询分页
	/**
	 * @param typeTemplate	模板实体
	 * @param page	从哪查
	 * @param rows	查多少
	 * @return	分页自定义实体
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TypeTemplate typeTemplate, Integer page,Integer rows) {
		PageResult pageResult=templateService.findPage(typeTemplate,page,rows);
		return pageResult;
	}
	/**
	 * 添加模板
	 * @param template 模板实体类
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TypeTemplate template) {
		try {
			templateService.add(template);
			return new Result(true, "添加成功!");
		} catch (Exception e) {
			return new Result(false, "添加失败!");
		}
		
	}
	/**
	 * 删除模板
	 * @param ids 复选框按钮传来的模板id
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			templateService.delete(ids);
			return new Result(true, "添加成功!");
		} catch (Exception e) {
			return new Result(false, "添加失败!");
		}
		
	}
	/**
	 *  	    回显模板信息到修改界面
	 * @param id 模板id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TypeTemplate findOne(Long id) {
		
		return templateService.findOne(id);
	}
	/**
	 * 	修改模板信息
	 * @param id 模板id
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TypeTemplate template) {
		try {
			templateService.update(template);
			return new Result(true, "添加成功!");
		} catch (Exception e) {
			return new Result(false, "添加失败!");
		}
		
	}
}
