package com.huyhoang.covid19.entities;

import java.time.LocalTime;

public class TimeAvailable {
	int index;
	LocalTime time;
	
	public TimeAvailable() {
		// TODO Auto-generated constructor stub
	}

	public TimeAvailable(int index, LocalTime time) {
		this.index = index;
		this.time = time;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	
}
