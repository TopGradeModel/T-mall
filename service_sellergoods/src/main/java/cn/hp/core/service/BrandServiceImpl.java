package cn.hp.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.hp.core.dao.good.BrandDao;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.good.Brand;
import cn.hp.core.pojo.good.BrandQuery;
import cn.hp.core.pojo.good.BrandQuery.Criteria;

/**
 * 品牌模块
 * @author 35814
 *
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	@Autowired
	private BrandDao brandDao;
	//查询所有的品牌
	public List<Brand> findAll() {
		List<Brand> list = brandDao.selectByExample(null);
		return list;
	}
	//查询所有的品牌分页
	public PageResult findPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);	
		Page<Brand> pageList=   (Page<Brand>) brandDao.selectByExample(null);
		return new PageResult(pageList.getTotal(), pageList.getResult());
	}
	//添加商标
	public void add(Brand brand) {
		brandDao.insert(brand);
	}
	//单查
	public Brand findOne(Long id) {
		Brand onewBrand = brandDao.selectByPrimaryKey(id);
		return onewBrand;
	}
	//修改
	public void update(Brand brand) {
		brandDao.updateByPrimaryKeySelective(brand);
	}
	//删除
	public void delete(Long[] ids) {
			if(ids!=null) {
				for (Long id : ids) {
					brandDao.deleteByPrimaryKey(id);
				}
			}
	}
	//搜索
	public PageResult search(Brand brand, Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		//创建brandQuery对象
		BrandQuery example=new BrandQuery();
		Criteria criteria = example.createCriteria();
		if(brand!=null) {
			if(brand.getName()!=null&&!"".equals(brand.getName())) {
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null&&!"".equals(brand.getFirstChar())) {
				criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
			}
		}
		Page<Brand> barnd=(Page<Brand>) brandDao.selectByExample(example);
		return new PageResult(barnd.getTotal(), barnd.getResult());
	}
	//回显品牌信息到模板管理新增界面
	public List<Map> selectOptionList() {
		//自定义查询
		List<Map> optionList = brandDao.selectOptionList();
		return optionList;
	}

}
