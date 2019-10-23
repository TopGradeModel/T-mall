package cn.hp.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.template.TypeTemplate;
import cn.hp.core.service.TemplateService;

/**
 * 商品添加模块
 * @author 35814
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {
	@Reference
	private TemplateService templateService;
	/**
	 *  	    回显模板信息到添加商品界面
	 * @param id 模板id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TypeTemplate findOne(Long id) {
		return templateService.findOne(id);
	}
	//查询规格集合和规格选项集合
	@RequestMapping("/findBySpecList")
	public List<Map> findBySpecList(Long id){
		return templateService.findBySpecList(id);
	}
}
