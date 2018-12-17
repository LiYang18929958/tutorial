package com.zuimeihui.tutorial.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zuimeihui.tutorial.pojo.TvSeries;
import com.zuimeihui.tutorial.service.TvSeriesService;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
	
//	定义日志
	private static final Log log = LogFactory.getLog(TvSeriesController.class);
	
	@Autowired TvSeriesService tvSeriesService;
	
//	GET,获取列表
	@GetMapping("")
	public List<TvSeries> getAll(){
		if(log.isTraceEnabled()) {
			log.trace("getAll(); 被调用了");
		}
		List<TvSeries> list = tvSeriesService.getAllTvSeries();
		if(log.isTraceEnabled()) {
			log.trace("查询获得" + list.size() + "条记录。");
		}
		return list;
	}
	
//	GET,获取一行数据
	@GetMapping("/{id}")
	public TvSeries getOne(@PathVariable int id) {
		if(log.isTraceEnabled()) {
			log.trace("getOne()" + id);
		}
		if(id == 101) {
			return createWestWorld();
		}else if(id == 102) {
			return createPoi();
		}else {
			throw new ResourceNotFoundException();
		}
	}
	
//	POST,插入数据
	@PostMapping("")
	public TvSeries insertOne(@Valid @RequestBody TvSeries tvSeriesDto) {
		if(log.isTraceEnabled()) {
			log.trace("这里应该写新增tvSeriesDto熬数据库的代码，传递进来的参数是：" + tvSeriesDto);
		}
		tvSeriesDto.setId(9999);
		return tvSeriesDto;
	}
	
//	PUT,更新数据
	@PutMapping("/{id}")
	public TvSeries updateOne(@PathVariable int id, @RequestBody TvSeries tvSeriesDto) {
		if(log.isTraceEnabled()) {
			log.trace("updateOne() " + id);
		}
		if(id == 101 || id == 102) {
			return createPoi();
		}else {
			throw new ResourceNotFoundException();
		}
	}
	
//	DELETE,删除数据
	@DeleteMapping("/{id}")
	public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request, @RequestParam(value="delete_reason", required = false) String deleteReason) throws Exception{
		if(log.isTraceEnabled()) {
			log.trace("deleteOne() " + id);
		}
		Map<String, String> result = new HashMap<>();
		if(id == 101) {
			result.put("message", "#101被" + request.getRemoteAddr() + "删除（原因：" + deleteReason + ")");
		}else if(id == 102) {
			throw new RuntimeException("#102不能删除");
		}else {
			throw new ResourceNotFoundException();
		}
		return result;
	}

//	POST,上传图片，consumes指定接收格式
	@PostMapping(value = "/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile imgFile) throws Exception {
		if(log.isTraceEnabled()) {
			log.trace("接受到文件 " + id + "收到文件：" + imgFile.getOriginalFilename());
		}
		FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
		IOUtils.copy(imgFile.getInputStream(), fos);
		fos.close();
	}
	
	//GET,打开图片，produces指定输出格式
	@GetMapping(value="/{id}/icon", produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] getIcon(@PathVariable int id) throws Exception {
		if(log.isTraceEnabled()) {
			log.trace("getIcon() " + id);
		}
		String iconFile = "/Users/LiYang/Desktop/图片/6408a386jw1e8qgp5bmzyj2050050aa8.jpg";
		InputStream is = new FileInputStream(iconFile);
		return org.apache.commons.io.IOUtils.toByteArray(is);
	}
	
//	一行数据
	private TvSeries createPoi() {
		Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.SEPTEMBER, 22, 0, 0, 0);
		return new TvSeries(102, "Person of Interest", 5, c.getTime());
	}
	
//	一行数据
	private TvSeries createWestWorld() {
		Calendar c = Calendar.getInstance();
		c.set(2016, Calendar.OCTOBER, 2, 0, 0 , 0);
		return new TvSeries(101, "West World", 1, c.getTime());
	}
}
