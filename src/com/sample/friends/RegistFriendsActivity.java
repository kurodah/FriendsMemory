package com.sample.friends;

import com.sample.dao.RegistFriendsDao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistFriendsActivity extends Activity {

	// SQLiteDatabaseの定義
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_friends);

		// ボタンの特定
		Button registButton = (Button) findViewById(R.id.register);

		// ラジオボタンにチェックをつける
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);
		radioGroup.check(R.id.man);

		// 押されたボタンを判別するため、tagを付与する。
		registButton.setTag("insert");

		// クリックリスナーの実行
		registButtonClickListener regBCListerner = new registButtonClickListener();
		registButton.setOnClickListener(regBCListerner);

		// DB作成
		helper = new CreateProductHelper(RegistFriendsActivity.this);
	}

	// 登録ボタンのクリックリスナーの定義
	class registButtonClickListener implements OnClickListener {
		public void onClick(View v) {

			// タグの取得（どのボタンが押されたのか判別）
			String tag = (String) v.getTag();

			// 該当DBオブジェクト取得
			db = helper.getWritableDatabase();

			// 登録ボタンが押された際の処理
			if (tag.equals("insert")) {

				// 友達情報の追加処理
				try {
					// テキストボックスから名前、メモを取得する
					EditText friendsNameText = (EditText) findViewById(R.id.friends_name);
					EditText placeText = (EditText) findViewById(R.id.place);
					EditText friendsMemoText = (EditText) findViewById(R.id.friends_memo);

					String friendsName = friendsNameText.getText().toString();
					String meetPlace = placeText.getText().toString();
					String friendsMemo = friendsMemoText.getText().toString();

					System.out.println(friendsName);

					if (friendsName != "") {

						// テキストボックスの取得内容確認
						// System.out.println(friendsName);
						// System.out.println(friendsMemo);

						// チェックボックスの状態を取得する
						CheckBox checkbox = (CheckBox) findViewById(R.id.favoriteCheck);
						Boolean favoriteCheck = checkbox.isChecked();

						// チェックボックスの取得内容確認
						System.out.println(favoriteCheck);

						// DB登録用にfavoriteFlgの値を決定
						int favoriteFlg;

						if (favoriteCheck == true) {
							favoriteFlg = 1;
						} else {
							favoriteFlg = 0;
						}

						// 年齢を取得する
						Spinner spinner = (Spinner) findViewById(R.id.age_list);
						String ageSelect = (String) spinner.getSelectedItem();
						int age = 0;
						// DBに登録するageの値を決定
						if (ageSelect.equals("10代")) {
							age = 0;
						} else if (ageSelect.equals("20代")) {
							age = 1;
						} else if (ageSelect.equals("30代")) {
							age = 2;
						} else if (ageSelect.equals("40代")) {
							age = 3;
						} else if (ageSelect.equals("50代")) {
							age = 4;
						}

						// ラジオボタンの値を取得する
						RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);
						RadioButton checkedButton = (RadioButton) findViewById(radioGroup
								.getCheckedRadioButtonId());
						String sexText = checkedButton.getText().toString();
						System.out.println(sexText);

						// DB登録用にsexの値を決定
						int sex;

						if (sexText.equals("男")) {
							sex = 0;
						} else {
							sex = 1;
						}

						RegistFriendsDao regFriendsDao = new RegistFriendsDao();
						Context con = RegistFriendsActivity.this;
						// @sugiim
						// Inputはバラバラに渡すのではなく、entityにまとめてから渡すべき（仕様変更に弱くなる）
						regFriendsDao.registDB(con, friendsName, meetPlace,
								friendsMemo, favoriteFlg, age, sex);

						// Toastで表示用に加工
						friendsName = friendsName + "さんを追加しました。";

						// 登録した名前をToastで表示する。
						Toast.makeText(RegistFriendsActivity.this, friendsName,
								Toast.LENGTH_SHORT).show();

						// 友達登録完了画面に遷移する。
						Intent registComplete = new Intent(
								RegistFriendsActivity.this,
								RegistResultActivity.class);
						startActivity(registComplete);

					} else {
						String insertErrorMessage = "名前を入力してください";
						Toast.makeText(RegistFriendsActivity.this,
								insertErrorMessage, Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					String insertErrorMessage = "データInsertエラー";
					Toast.makeText(RegistFriendsActivity.this,
							insertErrorMessage, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}