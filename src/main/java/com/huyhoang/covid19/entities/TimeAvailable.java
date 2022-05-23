package com.huyhoang.covid19.entities;

import java.time.LocalTime;

public class TimeAvailable {
	int index;
	LocalTime begin_at;
	LocalTime end_at;
	
	public TimeAvailable() {
		// TODO Auto-generated constructor stub
	}

	public TimeAvailable(int index, LocalTime begin_at, LocalTime end_at) {
		this.index = index;
		this.begin_at = begin_at;
		this.end_at = end_at;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LocalTime getBegin_at() {
		return begin_at;
	}

	public void setBegin_at(LocalTime begin_at) {
		this.begin_at = begin_at;
	}

	public LocalTime getEnd_at() {
		return end_at;
	}

	public void setEnd_at(LocalTime end_at) {
		this.end_at = end_at;
	}
	
	

	
	
}
