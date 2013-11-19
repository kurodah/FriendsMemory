package com.sample.friends;

import com.sample.entity.FriendsListEntity;
import com.sample.friends.RegistFriendsActivity.registButtonClickListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultActivity extends Activity {

	// SQLiteDatabaseの定義
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;
	public TextView txtInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		
		//テーブルレイアウトの指定
		TableLayout tableLayout = (TableLayout)findViewById(R.id.list);
        // テーブルレイアウトのクリア
        tableLayout.removeAllViews();

		// 年齢・性別の定義
        String friendsID;
		String friendsName;
		String friendsMemo;
		String meetPlace;
		int favoriteFlg;
		int searchSex;
		int searchAge;

		// データの取得
		FriendsListEntity friListEntity = new FriendsListEntity();
		
		Intent intent = getIntent();
		//DTOデータの受け取り
		friListEntity = (FriendsListEntity)intent.getSerializableExtra("setData");
				
		
		searchAge = friListEntity.getSearchAge();
		searchSex = friListEntity.getSearchSex();
		
		// DB作成
		helper = new CreateProductHelper(SearchResultActivity.this);
		
		Cursor c = null;
		
		try {
			db = helper.getWritableDatabase();
			String searchResultSQL = "select * from friendsList where age="
					+ "'" + searchAge + "' and sex = '" + searchSex + "'";
			//System.out.println(searchResultSQL);
			c = db.rawQuery(searchResultSQL, null);

			//ヘッダの表示
			TableRow headrow = new TableRow(SearchResultActivity.this);
            
			//ヘッダの挿入
			TextView headCheck= new TextView(SearchResultActivity.this);
			headCheck.setText("ID");
			headCheck.setBackgroundColor(Color.rgb(51,153,102));
			headCheck.setTextSize(12.0f);
			headCheck.setWidth(40);
							
			TextView headName = new TextView(SearchResultActivity.this);
			headName.setText("名前");
			headName.setBackgroundColor(Color.rgb(51,153,102));
			headName.setTextSize(12.0f);
			headName.setWidth(220);
			
			TextView headPlace = new TextView(SearchResultActivity.this);
			headPlace.setText("場所");
			headPlace.setBackgroundColor(Color.rgb(51,153,102));
			headPlace.setTextSize(12.0f);
			headPlace.setWidth(220); 
            
			TextView headMemo = new TextView(SearchResultActivity.this);
			headMemo.setText("メモ");
			headMemo.setTextSize(12.0f);
			headMemo.setBackgroundColor(Color.rgb(51,153,102));
			headMemo.setWidth(220);                      
				
			//各列を行（ヘッダ）に追加
			headrow.addView(headCheck);
            headrow.addView(headName);
            headrow.addView(headPlace);
            headrow.addView(headMemo);

            tableLayout.addView(headrow);
            int rowColor = 0;
            CheckBox[] checkBox = null; 
            int i = 0;
            
			// 検索結果の全件取得
			while (c.moveToNext()) {
				
				Log.d("result", "" + c.getInt(0) + " " + 
						 c.getString(1) + " " +  c.getString(2));
				//DBからデータを取得する
				friendsID = c.getString(c.getColumnIndex("_id"));;
				friendsName = c.getString(c.getColumnIndex("friendsName"));
				friendsMemo = c.getString(c.getColumnIndex("friendsMemo"));
				meetPlace = c.getString(c.getColumnIndex("meetPlace"));
				favoriteFlg = c.getInt(c.getColumnIndex("favoriteFlg"));
				//System.out.println(friendsID);
				//System.out.println(favoriteFlg);
				
				//テーブル処理
                TableRow row = new TableRow(SearchResultActivity.this);
                         
                //IDの取得
                TextView frinedsID = new TextView(SearchResultActivity.this);
                frinedsID.setGravity(Gravity.LEFT);
                frinedsID.setTextSize(12.0f);
                frinedsID.setText(friendsID);
                
                //名前の取得
                TextView frinedsName = new TextView(SearchResultActivity.this);
                frinedsName.setGravity(Gravity.LEFT);
                frinedsName.setTextSize(12.0f);
                frinedsName.setText(friendsName);
                
                //場所の取得
                TextView place = new TextView(SearchResultActivity.this);
                place.setGravity(Gravity.LEFT);
                place.setTextSize(12.0f);
                place.setText(meetPlace);
                                
                //メモの取得
                TextView frinedsMemo = new TextView(SearchResultActivity.this);
                frinedsMemo.setGravity(Gravity.LEFT);
                frinedsMemo.setTextSize(12.0f);
                frinedsMemo.setText(friendsMemo);
                
                //お気に入りの判別
                if(favoriteFlg == 1){
                	friendsName = "◎" + friendsName;
                }
               
                //テーブル行に追加
                row.addView(frinedsID);
                row.addView(frinedsName);
                row.addView(place);
                row.addView(frinedsMemo);
                
                if(rowColor%2 == 1){
                	row.setBackgroundColor(Color.rgb(204,255,204));
                }
                
                tableLayout.addView(row);
                
                rowColor = rowColor + 1;
                i = i + 1;
				
			}
		} catch (Exception e) {
			String insertErrorMessage = "データ検索エラー";
			Toast.makeText(SearchResultActivity.this, insertErrorMessage,
					Toast.LENGTH_SHORT).show();
			System.err.println(e.getMessage());			
		} finally {
			if (c != null) {
				c.close();
			}
		}

		// ボタンの特定
		Button menuButton = (Button) findViewById(R.id.menu_button);
		Button deleteButton = (Button) findViewById(R.id.delete_button);
		menuButtonClickListener menuBCListerner = new menuButtonClickListener();
		menuButton.setOnClickListener(menuBCListerner);
		
		deleteButtonClickListener delBCListerner = new deleteButtonClickListener();
		deleteButton.setOnClickListener(delBCListerner);
		
	}
	
	class menuButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			Intent intentMenu = new Intent(SearchResultActivity.this, MainActivity.class);
			startActivity(intentMenu);
		}
	}
	
	class deleteButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			EditText textID= (EditText) findViewById(R.id.friends_id);
			String friendsID = textID.getText().toString();
			String deleteFriendsSQL = "delete from friendsList where _ID=" 
					+friendsID;
			try{
				db.execSQL(deleteFriendsSQL);
			}catch(Exception e){
				String insertErrorMessage = "IDが不正です。";
				Toast.makeText(SearchResultActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
				.show();
			}
			FriendsListEntity friListEntity = new FriendsListEntity();
	    	friListEntity.setSearchSex(friListEntity.getSearchSex());
	    	friListEntity.setSearchAge(friListEntity.getSearchAge());
			Intent intentUpdate = new Intent(SearchResultActivity.this, SearchResultActivity.class);
			intentUpdate.putExtra("setData", friListEntity);
			startActivity(intentUpdate);
		}
	}	
}
