package com.example.mydemoapp.util.retrofit;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("color")
	private String color;

	@SerializedName("year")
	private int year;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("pantone_value")
	private String pantoneValue;

	public String getColor(){
		return color;
	}

	public int getYear(){
		return year;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getPantoneValue(){
		return pantoneValue;
	}
}