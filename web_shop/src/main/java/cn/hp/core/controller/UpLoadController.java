package cn.hp.core.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hp.core.pojo.entity.Result;
import cn.hp.core.util.FastDFSClient;

/**
 * 	上传图片模块
 * @author 35814
 *
 */
@RestController
@RequestMapping("/upload")
public class UpLoadController {
	@Value("${FILE_SERVER_URL}")
	private String FILE_SERVER_URL;
	/**
	 * @param file 页面的图片文件spring
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadFile")
	public Result uploadFile(MultipartFile file) throws Exception {
		try {
			//加载fdfs配置文件
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
			//调用方法执行文件上传
			String path = fastDFSClient.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
			return new Result(true,FILE_SERVER_URL+path);
		} catch (Exception e) {
			return new Result(true,"上传失败!");
		}
		
	}
}
