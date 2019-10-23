package cn.hp.core.service;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.seller.Seller;

/**
 *  商家模块
 * @author 35814
 *
 */
public interface SellerService {
	//添加商家入驻信息
	void add(Seller seller);
	//分页查询商家信息是否审核
	PageResult search(Seller seller,Integer page,Integer rows);
	//在详情页面中回显商家信息
	Seller findOne(String id);
	//更新商家状态0-未审核，1-审核通过，2-审核不通过，3-关闭商家
	void updateStatus(String sellerId, String status);
}
