package cn.hp.core.pojo.entity;

import java.io.Serializable;
import java.util.List;

import cn.hp.core.pojo.good.Goods;
import cn.hp.core.pojo.good.GoodsDesc;
import cn.hp.core.pojo.item.Item;

/**
 * 	添加商品自定义实体
 * @author 35814
 *
 */
public class GoodsEntity implements Serializable{
	//商品表
	private Goods goods;
	//商品详情表
	private GoodsDesc goodsDesc;
	//库存表
	private List<Item> itemList;
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public GoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(GoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
}
