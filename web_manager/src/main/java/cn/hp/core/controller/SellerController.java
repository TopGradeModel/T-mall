package cn.hp.core.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.seller.Seller;
import cn.hp.core.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {
	@Reference
	private SellerService sellerService;
	/**
	 * 查询商家信息
	 * @param seller 商家实体类
	 * @param page	从什么地方查
	 * @param rows	查多少条数据
	 * @return	分页实体类
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody Seller seller,Integer page,Integer rows) {
		
		return sellerService.search(seller, page, rows);
	}
	//在详情页面中回显商家信息
	@RequestMapping("/findOne")
	public Seller findOne(String id) {
		
		return sellerService.findOne(id);
	}
	//更新商家状态0-未审核，1-审核通过，2-审核不通过，3-关闭商家
	@RequestMapping("/updateStatus")
	public Result updateStatus(String sellerId,String status) {
		try {
			sellerService.updateStatus(sellerId,status);
			return new Result(true, "添加成功!");
			
		} catch (Exception e) {
			return new Result(false, "添加失败!");
		}
	}

}
