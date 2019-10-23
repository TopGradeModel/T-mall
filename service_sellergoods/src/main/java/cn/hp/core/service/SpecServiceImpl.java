package cn.hp.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.hp.core.dao.specification.SpecificationDao;
import cn.hp.core.dao.specification.SpecificationOptionDao;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.SpecEntity;
import cn.hp.core.pojo.specification.Specification;
import cn.hp.core.pojo.specification.SpecificationOption;
import cn.hp.core.pojo.specification.SpecificationOptionQuery;
import cn.hp.core.pojo.specification.SpecificationQuery;
import cn.hp.core.pojo.specification.SpecificationQuery.Criteria;

/**
 * 	规格service
 * @author 35814
 *
 */
@Service
@Transactional
public class SpecServiceImpl implements SpecService {
	@Autowired
	private SpecificationDao specificationDao;
	@Autowired
	private SpecificationOptionDao specificationOptionDao;
	//分页查询规格--无效
	public PageResult findPage(Integer page,Integer rows) {
		//设置分页条件
		PageHelper.startPage(page,rows);
		//将查询到的集合放入Page对象中
		Page<Specification> specification=(Page<Specification>) specificationDao.selectByExample(null);
		//返回pageResult对象
		return new PageResult(specification.getTotal(), specification.getResult());
	}
	//搜索功能
	public PageResult search(Specification specification, Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//创建specQuery对象
		SpecificationQuery specificationQuery = new SpecificationQuery();
		//获取设置条件对象
		Criteria criteria = specificationQuery.createCriteria();
		if(specification!=null) {
			if(specification.getSpecName()!=null&&!"".equals(specification.getSpecName())) {
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
		}
		//执行查询条件
		Page<Specification> specifications=(Page<Specification>) specificationDao.selectByExample(specificationQuery);
		return new PageResult(specifications.getTotal(), specifications.getResult());
	}
	//添加规格
	public void add(SpecEntity specEntity) {
		//添加规格对象
		specificationDao.insertSelective(specEntity.getSpecification());
		//添加规格选项
		if(specEntity.getSpecificationOptionList()!=null) {
			for (SpecificationOption option : specEntity.getSpecificationOptionList()) {
				System.out.println(option);
				Long id = specEntity.getSpecification().getId();
				option.setSpecId(id);
				specificationOptionDao.insert(option);
			}
		}
	}
	//单查规格和规格参数
	public SpecEntity findOne(Long id) {
		//单查规格
		Specification specification = specificationDao.selectByPrimaryKey(id);
		//创建查询对象
		SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
		cn.hp.core.pojo.specification.SpecificationOptionQuery.Criteria criteria = optionQuery.createCriteria();
		criteria.andSpecIdEqualTo(id);
		//查询规格参数集合
		List<SpecificationOption> specificationOption = specificationOptionDao.selectByExample(optionQuery);
		System.out.println(specificationOption.toString());
		//将数据放入pojo
		SpecEntity specEntity = new SpecEntity();
		specEntity.setSpecification(specification);
		specEntity.setSpecificationOptionList(specificationOption);
		return specEntity;
	}
	//修改规格和参数
	public void update(SpecEntity specEntity) {
		//修改规格
		specificationDao.updateByPrimaryKeySelective(specEntity.getSpecification());
		//创建规格参数example
		SpecificationOptionQuery optionQuery = new SpecificationOptionQuery();
		cn.hp.core.pojo.specification.SpecificationOptionQuery.Criteria criteria = optionQuery.createCriteria();
		criteria.andSpecIdEqualTo(specEntity.getSpecification().getId());
		//修改规格参数
		//先删除旧的关系
		specificationOptionDao.deleteByExample(optionQuery);
		//再添加新的关系
		if(specEntity!=null) {
			if(specEntity.getSpecificationOptionList()!=null) {
				//获取页面规格参数的集合
				List<SpecificationOption> specificationOptionLsit = specEntity.getSpecificationOptionList();
				for (SpecificationOption specificationOption : specificationOptionLsit) {
					//将外键添加进去
					 specificationOption.setSpecId(specEntity.getSpecification().getId());
					//遍历添加
					specificationOptionDao.insertSelective(specificationOption);
				}
				
			}
		}
		
	}
	//删除
	public void delete(Long[] ids) {
		//根据主键删除
		for (Long id : ids) {
			specificationDao.deleteByPrimaryKey(id);
			//创建条件对象
			SpecificationOptionQuery example = new SpecificationOptionQuery();
			//获取条件对象
			cn.hp.core.pojo.specification.SpecificationOptionQuery.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionDao.deleteByExample(example);
		}
	}
	//回显规格参数到商品管理模板管理添加
	public List<Map> selectOptionList() {
		List<Map> list = specificationDao.selectOptionList();
		return list;
	}
}
