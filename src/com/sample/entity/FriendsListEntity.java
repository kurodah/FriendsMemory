package com.sample.entity;

import java.io.Serializable;

public class FriendsListEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String friendsID;
	
	private String friendsName;
	
	private String friendsMemo;
	
	private String meetPlace;
	
	private int favoriteFlg;

	public int getFavoriteFlg() {
		return favoriteFlg;
	}

	public void setFavoriteFlg(int favoriteFlg) {
		this.favoriteFlg = favoriteFlg;
	}

	public String getFriendsID() {
		return friendsID;
	}

	public void setFriendsID(String friendsID) {
		this.friendsID = friendsID;
	}

	public String getFriendsName() {
		return friendsName;
	}

	public void setFriendsName(String friendsName) {
		this.friendsName = friendsName;
	}

	public String getFriendsMemo() {
		return friendsMemo;
	}

	public void setFriendsMemo(String friendsMemo) {
		this.friendsMemo = friendsMemo;
	}

	public String getMeetPlace() {
		return meetPlace;
	}

	public void setMeetPlace(String meetPlace) {
		this.meetPlace = meetPlace;
	}

	// ê´ï ÇÃåüçıíl
	private int searchSex;

	// îNóÓÇÃåüçıíl
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
