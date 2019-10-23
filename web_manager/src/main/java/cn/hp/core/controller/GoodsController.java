package cn.hp.core.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.entity.GoodsEntity;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.good.Goods;
import cn.hp.core.service.GoodsService;

/**
 * 	商品管理模块
 * 	@author 35814
 *
 */
@RequestMapping("/goods")
@RestController
public class GoodsController {
	@Reference
	private GoodsService goodsService;
	
	/**
	 * 	高级查询
	 * @return	返回商品信息集合
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody Goods goods,Integer page,Integer rows){
		//获取商家信息上下文对象
		SecurityContext context = SecurityContextHolder.getContext();
		//获取用户对象
		User user = (User) context.getAuthentication().getPrincipal();
		goods.setSellerId(user.getUsername());
		return goodsService.search(goods,page,rows);
	}
	/**
	 * 	单查商品信息、商品详情、库存
	 * @param id	商品id
	 * @return	商品自定义实体类（商品信息、商品详情、库存）
	 */
	@RequestMapping("/findOne")
	public GoodsEntity findOne(Long id){
		
		return goodsService.findOne(id);
	}
	/**
	 * 更新状态
	 */
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status) {
		try {
			goodsService.updateStatus(ids, status);
			return new Result(true, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "更新失败");
		}
	}
	/**
	 * 批量删除
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
}
