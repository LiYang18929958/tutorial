package com.zuimeihui.tutorial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuimeihui.tutorial.dao.TvSeriesDao;
import com.zuimeihui.tutorial.pojo.TvSeries;

@Service
public class TvSeriesService {

	@Autowired TvSeriesDao tvSeriesDao;
	
	public List<TvSeries> getAllTvSeries(){
		return tvSeriesDao.getAll();
	}
}
