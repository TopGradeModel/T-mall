package cn.hp.core.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.hp.core.dao.good.BrandDao;
import cn.hp.core.dao.good.GoodsDao;
import cn.hp.core.dao.good.GoodsDescDao;
import cn.hp.core.dao.item.ItemCatDao;
import cn.hp.core.dao.item.ItemDao;
import cn.hp.core.dao.seller.SellerDao;
import cn.hp.core.pojo.entity.GoodsEntity;
import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.good.Brand;
import cn.hp.core.pojo.good.Goods;
import cn.hp.core.pojo.good.GoodsDesc;
import cn.hp.core.pojo.good.GoodsDescQuery;
import cn.hp.core.pojo.good.GoodsQuery;
import cn.hp.core.pojo.good.GoodsQuery.Criteria;
import cn.hp.core.pojo.item.Item;
import cn.hp.core.pojo.item.ItemCat;
import cn.hp.core.pojo.item.ItemQuery;
import cn.hp.core.pojo.seller.Seller;

/**
 * 	商品模块
 * 
 * @author 35814
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {
	/**
	 * 商品表
	 */
	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 商品详情表
	 */
	@Autowired
	private GoodsDescDao goodsDescDao;
	/**
	 * 	库存表
	 */
	@Autowired
	private ItemDao itemDao;
	/**
	 * 商品分类信息表
	 */
	@Autowired
	private ItemCatDao itemCatDao;
	/**
	 * 	品牌表
	 */
	@Autowired
	private BrandDao brandDao;
	/**
	 *	 商家信息需表
	 */
	@Autowired
	private SellerDao SellerDao;

	// 添加商品
	public void add(GoodsEntity goodsEntity) {
		// 1.保存商品
		// 设置商品状态为0-未审核，1-审核通过，2-关闭
		goodsEntity.getGoods().setAuditStatus("0");
		goodsDao.insertSelective(goodsEntity.getGoods());
		// 2.保存商品详情
		// 保存id
		Long id = goodsEntity.getGoods().getId();
		goodsEntity.getGoodsDesc().setGoodsId(id);
		goodsDescDao.insertSelective(goodsEntity.getGoodsDesc());
		// 3.保存库存
		saveItemCat(goodsEntity);
	}
	
	//抽取规格的添加功能
	private void saveItemCat(GoodsEntity goodsEntity) {
		if ("1".equals(goodsEntity.getGoods().getIsEnableSpec())) {
			// 勾选了启用规格
			List<Item> itemList = goodsEntity.getItemList();
			if (itemList != null) {
				for (Item item : itemList) {
					// 商品标题，商品名称+规格，方便消费者更精准的搜索
					String title = goodsEntity.getGoods().getGoodsName();
					String spec = item.getSpec();
					Map specMap = JSON.parseObject(spec, Map.class);
					Collection<String> values = specMap.values();
					for (String value : values) {
						title += " " + value;
					}
					item.setTitle(title);
					//设置商品规格
					setItemValue(goodsEntity, item);
					itemDao.insertSelective(item);
				}
			}
			
		}else {
			//没有勾选复选框,没有库存数据.但是我们需要初始化一条. 
			Item item=new Item();
			//设置价格,BigDecimal计算精度更准确
			item.setPrice(new BigDecimal("999999999"));
			//设置库存数量 
			item.setNum(0);
			//设置规格
			item.setSpec("{}");
			//设置标题 
			item.setTitle(goodsEntity.getGoods().getGoodsName());
			//设置商品规格
			setItemValue(goodsEntity, item);
			itemDao.insertSelective(item);
		}
	}
	
	
	//从goodsEntity中拿到数据保存到Item实体中
	public Item setItemValue(GoodsEntity goodsEntity,Item item) {
		// 创建时间
		item.setCreateTime(new Date());
		// 更新时间
		item.setUpdateTime(new Date());
		// 商品品牌
		Brand brand = brandDao.selectByPrimaryKey(goodsEntity.getGoods().getBrandId());
		item.setBrand(brand.getName());
		// 商品id
		item.setGoodsId(goodsEntity.getGoods().getId());
		// 卖家名称
		Seller seller = SellerDao.selectByPrimaryKey(goodsEntity.getGoods().getSellerId());
		item.setSeller(seller.getName());
		// 库存状态
		item.setStatus("0");
		//分类ID:库存使用商品的三级分类作为库存分类 
		item.setCategoryid(goodsEntity.getGoods().getCategory3Id());
		// 分类名称
		ItemCat itemCat = itemCatDao.selectByPrimaryKey(goodsEntity.getGoods().getCategory3Id());
		item.setCategory(itemCat.getName());
		// 实例图片
		String itemImages = goodsEntity.getGoodsDesc().getItemImages();
		List<Map> maps = JSON.parseArray(itemImages, Map.class);
		if (maps != null && maps.size() > 0) {
			String url = String.valueOf(maps.get(0).get("url"));
			item.setImage(url);
		}
		return item;
	}
	/* 
	 * 高级查询
	 */
	@Override
	public PageResult search(Goods goods, Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//设置查询信息
		GoodsQuery goodsQuery = new GoodsQuery();
		Criteria criteria = goodsQuery.createCriteria();
		//判断goods内容是否为空
		if(goods!=null) {
			if(goods.getGoodsName()!=null&&!"".equals(goods.getGoodsName())) {
				//条件查询
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null&&"".equals(goods.getAuditStatus())) {
				//根据状态查询
				criteria.andAuditStatusEqualTo(goods.getAuditStatus());
			}
			if(goods.getSellerId()!=null&&!"".equals(goods.getSellerId())&&!"admin".equals(goods.getSellerId())&&!"wc".equals(goods.getSellerId())) {
				//根据商家名称
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
		}
		criteria.andIsDeleteIsNull();//指定条件为逻辑删除记录
		//查新商品表goods
		Page<Goods> goodsList=(Page<Goods>) goodsDao.selectByExample(goodsQuery);
		return new PageResult(goodsList.getTotal(), goodsList.getResult());
	}
	/* 
	 * 	单查商品信息、商品详情、库存
	 */
	@Override
	public GoodsEntity findOne(Long id) {
		//创建自定义实体类
		GoodsEntity goodsEntity = new GoodsEntity();
		//根据id查询商品信息表
		Goods goods = goodsDao.selectByPrimaryKey(id);
		goodsEntity.setGoods(goods);
		//根据id查询商品详情表
		GoodsDesc goodsDesc = goodsDescDao.selectByPrimaryKey(id);
		goodsEntity.setGoodsDesc(goodsDesc);
		//根据id查询库存表
		ItemQuery itemQuery = new ItemQuery();
		cn.hp.core.pojo.item.ItemQuery.Criteria criteria2 = itemQuery.createCriteria();
		criteria2.andGoodsIdEqualTo(id);
		List<Item> itemList = itemDao.selectByExample(itemQuery);
		goodsEntity.setItemList(itemList);
		return goodsEntity;
	}
	
	//更新商品
	public void update(GoodsEntity goodsEntity) {
		//更新基本数据
		goodsDao.updateByPrimaryKeySelective(goodsEntity.getGoods());
		//更新扩展表数据
		goodsDescDao.updateByPrimaryKeySelective(goodsEntity.getGoodsDesc());
		//删除原先的SKU数据列表
		//new出条件
		ItemQuery itemQuery=new ItemQuery();
		cn.hp.core.pojo.item.ItemQuery.Criteria criteria = itemQuery.createCriteria();
		criteria.andGoodsIdEqualTo(goodsEntity.getGoods().getId());
		itemDao.deleteByExample(itemQuery);
		//重新添加规格
		saveItemCat(goodsEntity);
	}

	//批量修改状态
	public void updateStatus(Long[] ids, String startus) {
		for (Long id : ids) {
			Goods goods = goodsDao.selectByPrimaryKey(id);
			goods.setAuditStatus(startus);
			goodsDao.updateByPrimaryKey(goods);
		}
		
	}

	//批量删除
	public void delete(Long[] ids) {
		if(ids!=null) {
			for(Long id:ids){
				//更新状态，防止真的删除
				Goods goods = goodsDao.selectByPrimaryKey(id);
				goods.setIsDelete("1");
				goodsDao.updateByPrimaryKey(goods);
			}	
		}
	}
}
