package cn.hp.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.hp.core.pojo.entity.PageResult;
import cn.hp.core.pojo.entity.Result;
import cn.hp.core.pojo.good.Brand;
import cn.hp.core.service.BrandService;
@RestController
@RequestMapping("/brand")
public class BrandController {
	@Reference
	private BrandService brandService;
	@RequestMapping("/findAll")
	public List<Brand> findAll(){
		List<Brand> list = brandService.findAll();
		return list;
	}
	
	//分页查询商标
	@RequestMapping("/findByPage")
	public PageResult findPage(Integer page,Integer rows){
		System.out.println("1111");
		PageResult pageResult = brandService.findPage(page,rows);
		return pageResult;
	}
	//添加商标
	@RequestMapping("/save")
	public Result add(@RequestBody Brand brand) {
		try {
			brandService.add(brand);
			return new Result(true,"添加成功");
		} catch (Exception e) {
			return new Result(false,"添加失败");
		}
	}
	//单查
	@RequestMapping("/findById")
	public Brand findOne(Long id) {
		 
		return brandService.findOne(id);
	}
	//修改
	@RequestMapping("/update")
	public Result update(@RequestBody Brand brand) {
		try {
			brandService.update(brand);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			return new Result(false, "修改失败");
		}
	}
	//删除
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {
		try {
			brandService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			return new Result(false, "删除失败");
		}
	}
	//搜索
	@RequestMapping("/search")
	public PageResult search(@RequestBody Brand brand,Integer page,Integer rows) {
		PageResult pageResult=brandService.search(brand,page,rows);
		return pageResult;
	}
	//回显品牌信息到模板管理新增界面
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		return brandService.selectOptionList();
	}
}
