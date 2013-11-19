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

	// SQLiteDatabase�̒�`
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;
	public TextView txtInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		
		//�e�[�u�����C�A�E�g�̎w��
		TableLayout tableLayout = (TableLayout)findViewById(R.id.list);
        // �e�[�u�����C�A�E�g�̃N���A
        tableLayout.removeAllViews();

		// �N��E���ʂ̒�`
        String friendsID;
		String friendsName;
		String friendsMemo;
		String meetPlace;
		int favoriteFlg;
		int searchSex;
		int searchAge;

		// �f�[�^�̎擾
		FriendsListEntity friListEntity = new FriendsListEntity();
		
		Intent intent = getIntent();
		//DTO�f�[�^�̎󂯎��
		friListEntity = (FriendsListEntity)intent.getSerializableExtra("setData");
				
		
		searchAge = friListEntity.getSearchAge();
		searchSex = friListEntity.getSearchSex();
		
		// DB�쐬
		helper = new CreateProductHelper(SearchResultActivity.this);
		
		Cursor c = null;
		
		try {
			db = helper.getWritableDatabase();
			String searchResultSQL = "select * from friendsList where age="
					+ "'" + searchAge + "' and sex = '" + searchSex + "'";
			//System.out.println(searchResultSQL);
			c = db.rawQuery(searchResultSQL, null);

			//�w�b�_�̕\��
			TableRow headrow = new TableRow(SearchResultActivity.this);
            
			//�w�b�_�̑}��
			TextView headCheck= new TextView(SearchResultActivity.this);
			headCheck.setText("ID");
			headCheck.setBackgroundColor(Color.rgb(51,153,102));
			headCheck.setTextSize(12.0f);
			headCheck.setWidth(40);
							
			TextView headName = new TextView(SearchResultActivity.this);
			headName.setText("���O");
			headName.setBackgroundColor(Color.rgb(51,153,102));
			headName.setTextSize(12.0f);
			headName.setWidth(220);
			
			TextView headPlace = new TextView(SearchResultActivity.this);
			headPlace.setText("�ꏊ");
			headPlace.setBackgroundColor(Color.rgb(51,153,102));
			headPlace.setTextSize(12.0f);
			headPlace.setWidth(220); 
            
			TextView headMemo = new TextView(SearchResultActivity.this);
			headMemo.setText("����");
			headMemo.setTextSize(12.0f);
			headMemo.setBackgroundColor(Color.rgb(51,153,102));
			headMemo.setWidth(220);                      
				
			//�e����s�i�w�b�_�j�ɒǉ�
			headrow.addView(headCheck);
            headrow.addView(headName);
            headrow.addView(headPlace);
            headrow.addView(headMemo);

            tableLayout.addView(headrow);
            int rowColor = 0;
            CheckBox[] checkBox = null; 
            int i = 0;
            
			// �������ʂ̑S���擾
			while (c.moveToNext()) {
				
				Log.d("result", "" + c.getInt(0) + " " + 
						 c.getString(1) + " " +  c.getString(2));
				//DB����f�[�^���擾����
				friendsID = c.getString(c.getColumnIndex("_id"));;
				friendsName = c.getString(c.getColumnIndex("friendsName"));
				friendsMemo = c.getString(c.getColumnIndex("friendsMemo"));
				meetPlace = c.getString(c.getColumnIndex("meetPlace"));
				favoriteFlg = c.getInt(c.getColumnIndex("favoriteFlg"));
				//System.out.println(friendsID);
				//System.out.println(favoriteFlg);
				
				//�e�[�u������
                TableRow row = new TableRow(SearchResultActivity.this);
                         
                //ID�̎擾
                TextView frinedsID = new TextView(SearchResultActivity.this);
                frinedsID.setGravity(Gravity.LEFT);
                frinedsID.setTextSize(12.0f);
                frinedsID.setText(friendsID);
                
                //���O�̎擾
                TextView frinedsName = new TextView(SearchResultActivity.this);
                frinedsName.setGravity(Gravity.LEFT);
                frinedsName.setTextSize(12.0f);
                frinedsName.setText(friendsName);
                
                //�ꏊ�̎擾
                TextView place = new TextView(SearchResultActivity.this);
                place.setGravity(Gravity.LEFT);
                place.setTextSize(12.0f);
                place.setText(meetPlace);
                                
                //�����̎擾
                TextView frinedsMemo = new TextView(SearchResultActivity.this);
                frinedsMemo.setGravity(Gravity.LEFT);
                frinedsMemo.setTextSize(12.0f);
                frinedsMemo.setText(friendsMemo);
                
                //���C�ɓ���̔���
                if(favoriteFlg == 1){
                	friendsName = "��" + friendsName;
                }
               
                //�e�[�u���s�ɒǉ�
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
			String insertErrorMessage = "�f�[�^�����G���[";
			Toast.makeText(SearchResultActivity.this, insertErrorMessage,
					Toast.LENGTH_SHORT).show();
			System.err.println(e.getMessage());			
		} finally {
			if (c != null) {
				c.close();
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
				String insertErrorMessage = "ID���s���ł��B";
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
