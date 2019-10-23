package cn.hp.core.service;

import java.util.List;

import cn.hp.core.pojo.entity.GoodsEntity;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.good.Goods;

/**
 * 	商品模块
 * @author 35814
 *
 */
public interface GoodsService {
	//添加商品
	public void add(GoodsEntity goodsEntity);
	//高级查询
	public PageResult search(Goods goods, Integer page, Integer rows);
	//单查商品信息、商品详情、库存
	public GoodsEntity findOne(Long id);
	//修改商品
	public void update(GoodsEntity goodsEntity);
	//批量修改状态
	public void updateStatus(Long [] ids,String startus);
	//批量删除商品
	public void delete(Long [] ids);
}
