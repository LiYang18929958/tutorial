package com.zuimeihui.tutorial.pojo;

import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TvSeries {
	
	@Null
	private int id;
	@NotNull
	private String name;
	@DecimalMin("1")
	private int seasonCount;
//	@Valid
//	@NotNull
//	@Size(min = 2)
//	private List<TvCharacterDto> tvCharacters;
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
	@Past
	private Date originRelease;
	
	public TvSeries() {
		
	}
	
	public TvSeries(int id, String name, int seasonCount, Date originReleas) {
		this.id            = id;
		this.name          = name;
		this.seasonCount   = seasonCount;
		this.originRelease = originReleas;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeasonCount() {
		return seasonCount;
	}
	public void setSeasonCount(int seasonCount) {
		this.seasonCount = seasonCount;
	}
	public Date getOriginRelease() {
		return originRelease;
	}
	public void setOriginRelease(Date originRelease) {
		this.originRelease = originRelease;
	}
	
	@Override
	public String toString() {
		return "TvSeriesDto [id=" + id + ", name=" + name + ", seasonCount=" + seasonCount + ", originRelease="
				+ originRelease + "]";
	}
}
