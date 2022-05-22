package com.huyhoang.covid19.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListTimeAvailable {
	private ArrayList<TimeAvailable> listime = new ArrayList<>();
	
	public ListTimeAvailable() {
		// TODO Auto-generated constructor stub
	}

	public ListTimeAvailable(ArrayList<TimeAvailable> listime) {
		this.listime = listime;
	}

	public ArrayList<TimeAvailable> getListime() {
		return listime;
	}

	public void setListime(ArrayList<TimeAvailable> listime) {
		this.listime = listime;
	}
	
	
}
