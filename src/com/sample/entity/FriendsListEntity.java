package com.sample.entity;

import java.io.Serializable;

public class FriendsListEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	//性別の検索値
	private int searchSex;
	
	//年齢の検索値
	private int searchAge;
	
	public int getSearchSex() {
		return searchSex;
	}
	public void setSearchSex(int searchSex) {
		this.searchSex = searchSex;
	}
	public int getSearchAge() {
		return searchAge;
	}
	public void setSearchAge(int searchAge) {
		this.searchAge = searchAge;
	}
}
