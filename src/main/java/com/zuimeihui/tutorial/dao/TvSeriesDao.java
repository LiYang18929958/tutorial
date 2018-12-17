package com.zuimeihui.tutorial.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.zuimeihui.tutorial.pojo.TvSeries;

public interface TvSeriesDao {

	@Select("select * from tv_series")
	public List<TvSeries> getAll();
}
