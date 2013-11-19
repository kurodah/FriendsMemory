package com.sample.friends;

import com.sample.entity.FriendsListEntity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchFriendsActivity extends Activity {
	
	//SQLiteDatabaseの定義
	CreateProductHelper helper = null;
    SQLiteDatabase db = null;		
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_friends);
		
		//ラジオボタンにチェックをつける
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);		
		radioGroup.check(R.id.man);		
		
		// 押されたボタンに応じて、タグを付与する
		Button searchButton = (Button) findViewById(R.id.search_button);
		searchButton.setTag("search");
		Button menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setTag("menu");		
		
		
		// クリックリスナーの実行
		SearchMenuClickListener searchBCListerner = new SearchMenuClickListener();
		searchButton.setOnClickListener(searchBCListerner);
		menuButton.setOnClickListener(searchBCListerner);	
		
		
	    // DB作成
        helper = new CreateProductHelper(SearchFriendsActivity.this);		
	}
	
	
	class SearchMenuClickListener implements OnClickListener {
		public void onClick(View v) {
			//DB処理の追加
			db = helper.getWritableDatabase();
			
			//タグの取得（どのボタンが押されたのか判別）
			String tag = (String)v.getTag();
			System.out.println(tag);
			
			
			//登録ボタンが押された際の処理
			if(tag.equals("search")){
				
				//カーソルの宣言
				Cursor c = null; 
				
				try{
						
					//ラジオボタンのの値を取得する
					RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);
					RadioButton checkedButton =  (RadioButton) findViewById(radioGroup
					        .getCheckedRadioButtonId());
					String sexText = checkedButton.getText().toString();
					System.out.println(sexText);
		
					//DB検索用にsexの値を決定
					int searchSex;
					
					if(sexText.equals("男")){
						searchSex = 0;
					}else{
						searchSex = 1;
					}
		
					//年齢を取得する
					Spinner spinner =(Spinner)findViewById(R.id.age_list);
					System.out.println(spinner);
					String ageSelect = (String)spinner.getSelectedItem();
					int searchAge = 0;
					//DBに登録するageの値を決定
					if(ageSelect.equals("10代")){
						searchAge = 0;
					}else if(ageSelect.equals("20代")){
						searchAge = 1;
					}else if(ageSelect.equals("30代")){
						searchAge = 2;
					}else if(ageSelect.equals("40代")){
						searchAge = 3;
					}else if(ageSelect.equals("50代")){
						searchAge = 4;
					}
					
					
					
					
					
					//SQLでDBを検索
					String searchCountSQL = "select count(*) from friendsList where age=" +
							"'" + searchAge + "' and sex = '" + searchSex  + "'";
					System.out.println(searchCountSQL);
					c = db.rawQuery(searchCountSQL, null);
				    c.moveToLast();
				    long count = c.getLong(0);
				    
				    System.out.println(count);
				    
				    //取得件数のチェック
				    if(count == 0){
				    	//画面遷移を行わずにメッセージを表示する
				    	String insertErrorMessage = "友達が登録されていません。条件を変更してください。";
						Toast.makeText(SearchFriendsActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
						.show();
				    }else{
				    	FriendsListEntity friListEntity = new FriendsListEntity();
				    	//System.out.println("セット前");
				    	//System.out.println(searchSex);
				    	//System.out.println(searchAge);
				    	friListEntity.setSearchSex(searchSex);
				    	friListEntity.setSearchAge(searchAge);
				    	
				        int newsex = friListEntity.getSearchSex();
				        int newage = friListEntity.getSearchAge();				
				        //System.out.println("ゲット後");
				        //System.out.println(newsex);
				        //System.out.println(newage);
				    
						//次画面に遷移
						Intent intentSearchResult = new Intent(SearchFriendsActivity.this, SearchResultActivity.class);
						intentSearchResult.putExtra("setData", friListEntity);
						
						// 検索結果画面に遷移する
						startActivity(intentSearchResult);		
				    }
				}catch(Exception e){
					String insertErrorMessage = "データ検索エラー";
					Toast.makeText(SearchFriendsActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
					.show();
					System.err.println(e.getMessage());
				}finally{
					if(c != null){
						c.close();
					}
				}
			}
			
			//メニューボタンに戻る処理
			if(tag.equals("menu")){
				
			    // インテントのインスタンス生成
				Intent intentMenu = new Intent(SearchFriendsActivity.this, MainActivity.class);
				// メニュー画面に遷移する
				startActivity(intentMenu);				
			}
		}	
	}
}