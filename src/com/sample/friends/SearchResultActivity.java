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

	// SQLiteDatabase�̒�`
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;
	public TextView txtInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);

		// �e�[�u�����C�A�E�g�̎w��
		TableLayout tableLayout = (TableLayout) findViewById(R.id.list);
		// �e�[�u�����C�A�E�g�̃N���A
		tableLayout.removeAllViews();

		// �N��E���ʂ̒�`
		int searchSex;
		int searchAge;

		// �f�[�^�̎擾
		FriendsListEntity friListEntity = new FriendsListEntity();

		Intent intent = getIntent();
		// DTO�f�[�^�̎󂯎��
		friListEntity = (FriendsListEntity) intent
				.getSerializableExtra("setData");

		searchAge = friListEntity.getSearchAge();
		searchSex = friListEntity.getSearchSex();

		// �w�b�_�̕\��
		TableRow headrow = new TableRow(SearchResultActivity.this);

		// �w�b�_�̑}��
		TextView headCheck = new TextView(SearchResultActivity.this);
		headCheck.setText("ID");
		headCheck.setBackgroundColor(Color.rgb(51, 153, 102));
		headCheck.setTextSize(12.0f);
		headCheck.setWidth(40);

		TextView headName = new TextView(SearchResultActivity.this);
		headName.setText("���O");
		headName.setBackgroundColor(Color.rgb(51, 153, 102));
		headName.setTextSize(12.0f);
		headName.setWidth(220);

		TextView headPlace = new TextView(SearchResultActivity.this);
		headPlace.setText("�ꏊ");
		headPlace.setBackgroundColor(Color.rgb(51, 153, 102));
		headPlace.setTextSize(12.0f);
		headPlace.setWidth(220);

		TextView headMemo = new TextView(SearchResultActivity.this);
		headMemo.setText("����");
		headMemo.setTextSize(12.0f);
		headMemo.setBackgroundColor(Color.rgb(51, 153, 102));
		headMemo.setWidth(220);

		// �e����s�i�w�b�_�j�ɒǉ�
		headrow.addView(headCheck);
		headrow.addView(headName);
		headrow.addView(headPlace);
		headrow.addView(headMemo);
		
		
        /**
         * �ȉ��ADB�Ɋi�[����Ă���f�[�^���o�͂���B
         */
        
        Context con = SearchResultActivity.this;
        SearchFriendsDao searchFriendsDao = new SearchFriendsDao(); 
        List<FriendsListEntity> friendsList =  searchFriendsDao.searchDB(con, searchAge, searchSex);
        
        //�s���ȁX�ɂ��邽�߂̕ϐ�
        int rowColor = 0;

		if (friendsList != null) {

			// �g��For����DB����擾�����f�[�^�����o��
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
				
				
                //���C�ɓ���̔���
                if(favoriteFlg == 1){
                	lineFriendsNameText = "��" + lineFriendsNameText;
                }
				
				
                //ID�̎擾
                TextView frinedsID = new TextView(SearchResultActivity.this);
                frinedsID.setGravity(Gravity.LEFT);
                frinedsID.setTextSize(12.0f);
                frinedsID.setText(lineFriendsIDText);
                
                //���O�̎擾
                TextView frinedsName = new TextView(SearchResultActivity.this);
                frinedsName.setGravity(Gravity.LEFT);
                frinedsName.setTextSize(12.0f);
                frinedsName.setText(lineFriendsNameText);
                                               
                //�����̎擾
                TextView frinedsMemo = new TextView(SearchResultActivity.this);
                frinedsMemo.setGravity(Gravity.LEFT);
                frinedsMemo.setTextSize(12.0f);
                frinedsMemo.setText(lineFriendsMemoText);
                
                //�ꏊ�̎擾
                TextView place = new TextView(SearchResultActivity.this);
                place.setGravity(Gravity.LEFT);
                place.setTextSize(12.0f);
                place.setText(linePlaceText);
                
                
                lineRow.addView(frinedsID);
                lineRow.addView(frinedsName);
                lineRow.addView(frinedsMemo);  
                lineRow.addView(place);
				
                if(rowColor%2 == 1){
                	lineRow.setBackgroundColor(Color.rgb(204,255,204));
                }
                
                tableLayout.addView(lineRow);
                
                
			}
		}

		// �{�^���̓���
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
				String insertErrorMessage = "ID���s���ł��B";
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
