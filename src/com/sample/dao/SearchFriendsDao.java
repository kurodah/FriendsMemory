package com.sample.dao;

import java.util.ArrayList;
import java.util.List;

import com.sample.entity.FriendsListEntity;
import com.sample.friends.CreateProductHelper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class SearchFriendsDao extends Activity{
    

    public List<FriendsListEntity> searchDB(Context con,int searchAge,int searchSex){
        CreateProductHelper helper = null;
        SQLiteDatabase db = null;
        
    	helper = new CreateProductHelper(con);    
    	db = helper.getWritableDatabase();
    	Cursor c = null;
    	
        String friendsID;
		String friendsName;
		String friendsMemo;
		String meetPlace;
		int favoriteFlg;


    	// Testデータ
        List<FriendsListEntity> friendsList = null;
   
        try {
        		
        	//DB使用の準備
        	db = helper.getWritableDatabase();
        		
        	//SQL文の準備と実行
			String searchResultSQL = "select * from friendsList where age="
					+ "'" + searchAge + "' and sex = '" + searchSex + "'";
        	c = db.rawQuery(searchResultSQL, null);
        		
        	while (c.moveToNext()) {				
        	    
                if (friendsList == null) {
                	friendsList = new ArrayList<FriendsListEntity>();
                }
        	    
                
                FriendsListEntity friendsListEntity = null;
                friendsListEntity = new FriendsListEntity();
                
                
        		friendsID = c.getString(c.getColumnIndex("_id"));
        		friendsName = c.getString(c.getColumnIndex("lat"));
        		friendsMemo = c.getString(c.getColumnIndex("longit"));
        		meetPlace = c.getString(c.getColumnIndex("longit"));
        		favoriteFlg = c.getInt(c.getColumnIndex("favoriteFlg"));
        		
        		friendsListEntity.setFriendsID(friendsID);
        		friendsListEntity.setFriendsName(friendsName);
        		friendsListEntity.setFriendsMemo(friendsMemo);
        		friendsListEntity.setMeetPlace(meetPlace);
        		friendsListEntity.setFavoriteFlg(favoriteFlg);
        		friendsList.add(friendsListEntity);

        	}
        
        } catch (Exception e) {
        
        } finally {
        	if (c != null) {
        		c.close();
        	}
        }
        return friendsList;
    }
}
