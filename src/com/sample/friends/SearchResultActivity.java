package com.sample.friends;

import java.util.List;

import com.sample.dao.SearchFriendsDao;
import com.sample.entity.FriendsListEntity;
import com.sample.friends.RegistFriendsActivity.registButtonClickListener;

import android.app.Activity;
import android.content.Context;
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

		// テーブルレイアウトの指定
		TableLayout tableLayout = (TableLayout) findViewById(R.id.list);
		// テーブルレイアウトのクリア
		tableLayout.removeAllViews();

		// 年齢・性別の定義
		int searchSex;
		int searchAge;

		// データの取得
		FriendsListEntity friListEntity = new FriendsListEntity();

		Intent intent = getIntent();
		// DTOデータの受け取り
		friListEntity = (FriendsListEntity) intent
				.getSerializableExtra("setData");

		searchAge = friListEntity.getSearchAge();
		searchSex = friListEntity.getSearchSex();

		// ヘッダの表示
		TableRow headrow = new TableRow(SearchResultActivity.this);

		// ヘッダの挿入
		TextView headCheck = new TextView(SearchResultActivity.this);
		headCheck.setText("ID");
		headCheck.setBackgroundColor(Color.rgb(51, 153, 102));
		headCheck.setTextSize(12.0f);
		headCheck.setWidth(40);

		TextView headName = new TextView(SearchResultActivity.this);
		headName.setText("名前");
		headName.setBackgroundColor(Color.rgb(51, 153, 102));
		headName.setTextSize(12.0f);
		headName.setWidth(220);

		TextView headPlace = new TextView(SearchResultActivity.this);
		headPlace.setText("場所");
		headPlace.setBackgroundColor(Color.rgb(51, 153, 102));
		headPlace.setTextSize(12.0f);
		headPlace.setWidth(220);

		TextView headMemo = new TextView(SearchResultActivity.this);
		headMemo.setText("メモ");
		headMemo.setTextSize(12.0f);
		headMemo.setBackgroundColor(Color.rgb(51, 153, 102));
		headMemo.setWidth(220);

		// 各列を行（ヘッダ）に追加
		headrow.addView(headCheck);
		headrow.addView(headName);
		headrow.addView(headPlace);
		headrow.addView(headMemo);

		/**
		 * 以下、DBに格納されているデータを出力する。
		 */

		Context con = SearchResultActivity.this;
		SearchFriendsDao searchFriendsDao = new SearchFriendsDao();
		List<FriendsListEntity> friendsList;
		try {
			friendsList = searchFriendsDao.searchDB(con, searchAge, searchSex);

			// 行を縞々にするための変数
			int rowColor = 0;

			if (friendsList != null) {

				// 拡張For文でDBから取得したデータを取り出す
				for (FriendsListEntity friendsListEntity : friendsList) {

					String lineFriendsIDText;
					String lineFriendsNameText;
					String lineFriendsMemoText;
					String linePlaceText;
					int favoriteFlg;

					TableRow lineRow = new TableRow(SearchResultActivity.this);

					lineFriendsIDText = friendsListEntity.getFriendsID();
					lineFriendsNameText = friendsListEntity.getFriendsName();
					lineFriendsMemoText = friendsListEntity.getFriendsName();
					linePlaceText = friendsListEntity.getMeetPlace();
					favoriteFlg = friendsListEntity.getFavoriteFlg();

					// お気に入りの判別
					if (favoriteFlg == 1) {
						lineFriendsNameText = "◎" + lineFriendsNameText;
					}

					// IDの取得
					TextView frinedsID = new TextView(SearchResultActivity.this);
					frinedsID.setGravity(Gravity.LEFT);
					frinedsID.setTextSize(12.0f);
					frinedsID.setText(lineFriendsIDText);

					// 名前の取得
					TextView frinedsName = new TextView(
							SearchResultActivity.this);
					frinedsName.setGravity(Gravity.LEFT);
					frinedsName.setTextSize(12.0f);
					frinedsName.setText(lineFriendsNameText);

					// メモの取得
					TextView frinedsMemo = new TextView(
							SearchResultActivity.this);
					frinedsMemo.setGravity(Gravity.LEFT);
					frinedsMemo.setTextSize(12.0f);
					frinedsMemo.setText(lineFriendsMemoText);

					// 場所の取得
					TextView place = new TextView(SearchResultActivity.this);
					place.setGravity(Gravity.LEFT);
					place.setTextSize(12.0f);
					place.setText(linePlaceText);

					lineRow.addView(frinedsID);
					lineRow.addView(frinedsName);
					lineRow.addView(frinedsMemo);
					lineRow.addView(place);

					if (rowColor % 2 == 1) {
						lineRow.setBackgroundColor(Color.rgb(204, 255, 204));
					}

					tableLayout.addView(lineRow);

				}
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
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
			Intent intentMenu = new Intent(SearchResultActivity.this,
					MainActivity.class);
			startActivity(intentMenu);
		}
	}

	class deleteButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			EditText textID = (EditText) findViewById(R.id.friends_id);
			String friendsID = textID.getText().toString();
			String deleteFriendsSQL = "delete from friendsList where _ID="
					+ friendsID;
			try {
				db.execSQL(deleteFriendsSQL);
			} catch (Exception e) {
				String insertErrorMessage = "IDが不正です。";
				Toast.makeText(SearchResultActivity.this, insertErrorMessage,
						Toast.LENGTH_SHORT).show();
			}
			FriendsListEntity friListEntity = new FriendsListEntity();
			friListEntity.setSearchSex(friListEntity.getSearchSex());
			friListEntity.setSearchAge(friListEntity.getSearchAge());
			Intent intentUpdate = new Intent(SearchResultActivity.this,
					SearchResultActivity.class);
			intentUpdate.putExtra("setData", friListEntity);
			startActivity(intentUpdate);
		}
	}
}