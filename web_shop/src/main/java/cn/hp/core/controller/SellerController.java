package cn.hp.core.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.seller.Seller;
import cn.hp.core.service.SellerService;
/**
 * 	商家申请入驻模块
 * @author 35814
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
	@Reference
	private SellerService sellerService;
	//添加商家入驻信息
	@RequestMapping("/add")
	public Result add(@RequestBody Seller seller) {
		try {
			sellerService.add(seller);
			return new Result(true, "添加成功!");
		} catch (Exception e) {
			return new Result(false, "添加失败!");
		}
		
	}
}
