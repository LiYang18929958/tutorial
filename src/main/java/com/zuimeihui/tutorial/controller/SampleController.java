package com.zuimeihui.tutorial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zuimeihui.tutorial.service.SampleService;

@RestController
@RequestMapping("/")
public class SampleController {
	private final Log log = LogFactory.getLog(SampleController.class);
	
	@Autowired SampleService sampleService;
	
	@GetMapping("")
	@PreAuthorize("hasRole('author')")
	public List<Map<String, Object>> getAll(){
		if(log.isTraceEnabled()) {
			log.trace("getAll()");
		}
		List<Map<String, Object>> list = new ArrayList<>();
		HashMap<String, Object> m1 = new HashMap<>();
		m1.put("id", 101);
		m1.put("name", "jack");
		list.add(m1);
		
		HashMap<String, Object> m2 = new HashMap<>();
		m2.put("id", 102);
		m2.put("name", "Dolores");
		list.add(m2);
		
		return list;
	}
}
