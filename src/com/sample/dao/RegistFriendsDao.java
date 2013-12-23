package com.sample.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.sample.friends.CreateProductHelper;

public class RegistFriendsDao {

	public void registDB(Context con, String friendsName, String meetPlace,
			String friendsMemo, int favoriteFlg, int age, int sex) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		CreateProductHelper helper = null;
		SQLiteDatabase db = null;
		helper = new CreateProductHelper(con);
		db = helper.getWritableDatabase();

		// �l�̎擾
		try {
			String insertSQL = "insert into friendsList(friendsName,meetPlace,friendsMemo,favoriteFlg,age,sex)" +
					"values('" + friendsName + "','"+ meetPlace + "','" + friendsMemo 
								+ "','" +  favoriteFlg  + "','"  +  age  + "','"+ sex +  "')";

			// SQL�̎��s
			db.execSQL(insertSQL);


		} catch (Exception e) {
			
			// @sugiim メッセージはリソースファイルなどに定義すべき
			String failMessage = "���s�p�^�[��";
			
			
			// @sugiim DaoでToastを扱うのは不適切すぎ。
			// Daoの役割としてはエラーがあったこと、エラーの内容を返却するだけとする。表示の制御はActivityで行うべき。
			Toast.makeText(con, failMessage,
					Toast.LENGTH_SHORT).show();
		} finally {
			db.close();
		}
	}

}
