package cn.hp.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.hp.core.dao.specification.SpecificationDao;
import cn.hp.core.dao.specification.SpecificationOptionDao;
import cn.hp.core.dao.template.TypeTemplateDao;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.specification.SpecificationOption;
import cn.hp.core.pojo.specification.SpecificationOptionQuery;
import cn.hp.core.pojo.template.TypeTemplate;
import cn.hp.core.pojo.template.TypeTemplateQuery;
import cn.hp.core.pojo.template.TypeTemplateQuery.Criteria;

/**
 * 模板管理模块
 * @author 35814
 *
 */
@Service
public class TemplateServiceImpl implements TemplateService {
	@Autowired
	private TypeTemplateDao typeTemplateDao;
	//引入规格选项表
	@Autowired
	private SpecificationOptionDao specificationOptionDao;
	//搜索分页查询
	public PageResult findPage(TypeTemplate typeTemplate, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		//创建连接对象
		TypeTemplateQuery templateQuery = new TypeTemplateQuery();
		Criteria criteria = templateQuery.createCriteria();
		if(typeTemplate!=null) {
			if(typeTemplate.getName()!=null&&!"".equals(typeTemplate.getName())) {
				criteria.andNameEqualTo("%"+typeTemplate.getName()+"%");
			}
		}
		Page<TypeTemplate> list = (Page<TypeTemplate>) typeTemplateDao.selectByExample(templateQuery);
		
		return new PageResult(list.getTotal(), list.getResult()) ;
	}
	//添加模板
	public void add(TypeTemplate template) {
		typeTemplateDao.insertSelective(template);
	}
	//删除模板
	@Override
	public void delete(Long[] ids) {
		if(ids!=null) {
			for (Long id : ids) {
				typeTemplateDao.deleteByPrimaryKey(id);
			}
		}
		
	}
	//回显模板信息到修改界面
	@Override
	public TypeTemplate findOne(Long id) {
		return typeTemplateDao.selectByPrimaryKey(id);
	}
	//修改模板信息
	@Override
	public void update(TypeTemplate template) {
		typeTemplateDao.updateByPrimaryKeySelective(template);
	}
	//查询规格集合和规格选项集合
	public List<Map> findBySpecList(Long id) {
		//1.查询模板对象
		TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
		//2.获得规格数据，查询出来规格json字符串
		String specIds = typeTemplate.getSpecIds();
		//3.解析json字符串为java对象中的list集合
		List<Map> list = JSON.parseArray(specIds, Map.class);
		if(list!=null) {
			for (Map map : list) {
				//根据规格id查询规格选项集合
				long specId = Long.parseLong(String.valueOf(map.get("id")));
				//查询数据
				SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
				cn.hp.core.pojo.specification.SpecificationOptionQuery.Criteria criteria = optionQuery.createCriteria();
				criteria.andSpecIdEqualTo(specId);
				List<SpecificationOption> optionList = specificationOptionDao.selectByExample(optionQuery);
				map.put("options", optionList);
			}
		}
		return list;
	}

}
