package com.sample.friends;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateProductHelper  extends SQLiteOpenHelper{
    
    // コンストラクタ定義
    public CreateProductHelper(Context con){
        // SQLiteOpenHelperのコンストラクタ呼び出し
        super(con,"friendsDB",null,1);
    }
        
    //onCreateメソッド
    @Override
    public void onCreate(SQLiteDatabase db) {    
		String createTableSql 
		= "create table friendsList(_id integer primary key autoincrement," +
		  "friendsName text not null," +
		  "meetPlace text," +
		  "friendsMemo text," +
		  "age int," +
		  "favoriteFlg int," +
		  "sex int)";				
		
		// SQL実行
		db.execSQL(createTableSql);    	
    }
    
    // onUpgradeメソッド
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
    }
}
