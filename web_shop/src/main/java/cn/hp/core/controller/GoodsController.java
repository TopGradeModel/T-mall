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
	 * 	添加商品
	 * @param goodsEntity 添加的表单实体类
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody GoodsEntity goodsEntity) {
		try {
			//将用户姓名添加到goos中
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String sellerId = user.getUsername();
			//设置商家id
			goodsEntity.getGoods().setSellerId(sellerId);
			//调用业务层处理数据
			goodsService.add(goodsEntity);
			return new Result(true, "保存成功!");
		} catch (Exception e) {
			e.getStackTrace();
			return new Result(false, "保存失败!");
		}
	}
	
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
	 * 	修改商品信息
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody GoodsEntity goodsEntity) {
		try {
			//检验是否为当前商家的id
			GoodsEntity goods = goodsService.findOne(goodsEntity.getGoods().getId());
			//获取当前登陆的商家id
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			//如果传递过来的商家id并不是当前登陆的id，则属于非法操作
			if(!goods.getGoods().getSellerId().equals(sellerId)||!goodsEntity.getGoods().getSellerId().equals(sellerId)) {
				return new Result(false, "非法操作!");
			}
			goodsService.update(goodsEntity);
			return new Result(true, "更新成功!");
		} catch (Exception e) {
			return new Result(false, "更新失败!");
		}
		
		
	}
	
	
	
	
	
	
	
}
