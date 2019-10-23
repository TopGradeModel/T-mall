package cn.hp.core.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.hp.core.dao.seller.SellerDao;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.seller.Seller;
import cn.hp.core.pojo.seller.SellerQuery;
import cn.hp.core.pojo.seller.SellerQuery.Criteria;

/**
 * 商家模块
 * 
 * @author 35814
 *
 */
@Service
public class SellerServiceImpl implements SellerService {
	@Autowired
	private SellerDao sellerDao;
	// 添加商家入驻信息
	public void add(Seller seller) {
		seller.setStatus("0");
		seller.setCreateTime(new Date());
		sellerDao.insertSelective(seller);
	}
	//分页查询商家信息是否审核
	public PageResult search(Seller seller, Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		SellerQuery sellerQuery = new SellerQuery();
		Criteria criteria = sellerQuery.createCriteria();
		if(seller!=null) {
			if(seller.getStatus()!=null && !"".equals(seller.getStatus())) {
				criteria.andStatusEqualTo(seller.getStatus());
			}
			if(seller.getName()!=null && !"".equals(seller.getName())) {
				criteria.andNameLike("%"+seller.getName()+"%");
			}
			if(seller.getNickName()!=null && !"".equals(seller.getNickName())) {
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}
		}
		//查询商家信息
		Page<Seller> list=(Page<Seller>) sellerDao.selectByExample(sellerQuery);
		return new PageResult(list.getTotal(), list.getResult());
	}
	//在详情页面中回显商家信息
	public Seller findOne(String id) {
		return sellerDao.selectByPrimaryKey(id);
	}
	//更新商家状态0-未审核，1-审核通过，2-审核不通过，3-关闭商家
	public void updateStatus(String sellerId, String status) {
		Seller seller =new Seller();
		seller.setSellerId(sellerId);
		seller.setStatus(status);
		sellerDao.updateByPrimaryKeySelective(seller);
	}

}
