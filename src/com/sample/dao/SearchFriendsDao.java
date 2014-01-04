package com.sample.dao;

import java.util.ArrayList;
import java.util.List;

import com.sample.entity.FriendsListEntity;
import com.sample.friends.CreateProductHelper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SearchFriendsDao extends Activity {

	public List<FriendsListEntity> searchDB(Context con, int searchAge,
			int searchSex) throws Exception {
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

			// DB使用の準備
			db = helper.getWritableDatabase();

			// SQL文の準備と実行
			String searchResultSQL = "select * from friendsList where age="
					+ "'" + searchAge + "' and sex = '" + searchSex + "'";
			c = db.rawQuery(searchResultSQL, null);

			// @sugiim LOOPに入る前に必要な変数を宣言しておく。
			// FriendsListEntity friendsListEntity = null;
			FriendsListEntity friendsListEntity = null;

			while (c.moveToNext()) {

				if (friendsList == null) {
					friendsList = new ArrayList<FriendsListEntity>();
				}

				// @sugiim LOOPが始まる前に変数は宣言（領域を確保）しておき、
				// ここでは初期化のみを行う
				// friendsListEntity = new FriendsListEntity();
				// FriendsListEntity friendsListEntity = null;
				friendsListEntity = new FriendsListEntity();

				// @sugiim Stringを取り出してsetするのは１行で書ける。以下のような書き方でよい。
				// friendsListEntity.setFriendsID(c.getString(c.getColumnIndex("_id")));
				// Stringの領域がムダ。

				friendsListEntity.setFriendsID(c.getString(c
						.getColumnIndex("_id")));
				friendsListEntity.setFriendsID(c.getString(c
						.getColumnIndex("friendsName")));
				friendsListEntity.setFriendsID(c.getString(c
						.getColumnIndex("meetPlace")));
				friendsListEntity.setFriendsID(c.getString(c
						.getColumnIndex("friendsMemo")));
				friendsListEntity.setFriendsID(c.getString(c
						.getColumnIndex("favoriteFlg")));

				// friendsID = c.getString(c.getColumnIndex("_id"));
				// friendsName = c.getString(c.getColumnIndex("lat"));
				// friendsMemo = c.getString(c.getColumnIndex("longit"));
				// meetPlace = c.getString(c.getColumnIndex("longit"));
				// favoriteFlg = c.getInt(c.getColumnIndex("favoriteFlg"));

				// friendsListEntity.setFriendsID(friendsID);
				// friendsListEntity.setFriendsName(friendsName);
				// friendsListEntity.setFriendsMemo(friendsMemo);
				// friendsListEntity.setMeetPlace(meetPlace);
				// friendsListEntity.setFavoriteFlg(favoriteFlg);
				// friendsList.add(friendsListEntity);

			}

		} catch (Exception e) {
			// @sugiim 何もしないのはありえない...。エラーに気がつかないため。
			// log出力、Exceptionのthrowwすべき
			// System.out.println(e);
			// throw e;

			System.out.println(e);
			throw e;
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return friendsList;
	}
}
