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
	
	//SQLiteDatabase�̒�`
	CreateProductHelper helper = null;
    SQLiteDatabase db = null;		
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_friends);
		
		//���W�I�{�^���Ƀ`�F�b�N������
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);		
		radioGroup.check(R.id.man);		
		
		// �����ꂽ�{�^���ɉ����āA�^�O��t�^����
		Button searchButton = (Button) findViewById(R.id.search_button);
		searchButton.setTag("search");
		Button menuButton = (Button) findViewById(R.id.menu_button);
		menuButton.setTag("menu");		
		
		
		// �N���b�N���X�i�[�̎��s
		SearchMenuClickListener searchBCListerner = new SearchMenuClickListener();
		searchButton.setOnClickListener(searchBCListerner);
		menuButton.setOnClickListener(searchBCListerner);	
		
		
	    // DB�쐬
        helper = new CreateProductHelper(SearchFriendsActivity.this);		
	}
	
	
	class SearchMenuClickListener implements OnClickListener {
		public void onClick(View v) {
			//DB�����̒ǉ�
			db = helper.getWritableDatabase();
			
			//�^�O�̎擾�i�ǂ̃{�^���������ꂽ�̂����ʁj
			String tag = (String)v.getTag();
			System.out.println(tag);
			
			
			//�o�^�{�^���������ꂽ�ۂ̏���
			if(tag.equals("search")){
				
				//�J�[�\���̐錾
				Cursor c = null; 
				
				try{
						
					//���W�I�{�^���̂̒l���擾����
					RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);
					RadioButton checkedButton =  (RadioButton) findViewById(radioGroup
					        .getCheckedRadioButtonId());
					String sexText = checkedButton.getText().toString();
					System.out.println(sexText);
		
					//DB�����p��sex�̒l������
					int searchSex;
					
					if(sexText.equals("�j")){
						searchSex = 0;
					}else{
						searchSex = 1;
					}
		
					//�N����擾����
					Spinner spinner =(Spinner)findViewById(R.id.age_list);
					System.out.println(spinner);
					String ageSelect = (String)spinner.getSelectedItem();
					int searchAge = 0;
					//DB�ɓo�^����age�̒l������
					if(ageSelect.equals("10��")){
						searchAge = 0;
					}else if(ageSelect.equals("20��")){
						searchAge = 1;
					}else if(ageSelect.equals("30��")){
						searchAge = 2;
					}else if(ageSelect.equals("40��")){
						searchAge = 3;
					}else if(ageSelect.equals("50��")){
						searchAge = 4;
					}
					
					
					
					
					
					//SQL��DB������
					String searchCountSQL = "select count(*) from friendsList where age=" +
							"'" + searchAge + "' and sex = '" + searchSex  + "'";
					System.out.println(searchCountSQL);
					c = db.rawQuery(searchCountSQL, null);
				    c.moveToLast();
				    long count = c.getLong(0);
				    
				    System.out.println(count);
				    
				    //�擾�����̃`�F�b�N
				    if(count == 0){
				    	//��ʑJ�ڂ��s�킸�Ƀ��b�Z�[�W��\������
				    	String insertErrorMessage = "�F�B���o�^����Ă��܂���B������ύX���Ă��������B";
						Toast.makeText(SearchFriendsActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
						.show();
				    }else{
				    	FriendsListEntity friListEntity = new FriendsListEntity();
				    	//System.out.println("�Z�b�g�O");
				    	//System.out.println(searchSex);
				    	//System.out.println(searchAge);
				    	friListEntity.setSearchSex(searchSex);
				    	friListEntity.setSearchAge(searchAge);
				    	
				        int newsex = friListEntity.getSearchSex();
				        int newage = friListEntity.getSearchAge();				
				        //System.out.println("�Q�b�g��");
				        //System.out.println(newsex);
				        //System.out.println(newage);
				    
						//����ʂɑJ��
						Intent intentSearchResult = new Intent(SearchFriendsActivity.this, SearchResultActivity.class);
						intentSearchResult.putExtra("setData", friListEntity);
						
						// �������ʉ�ʂɑJ�ڂ���
						startActivity(intentSearchResult);		
				    }
				}catch(Exception e){
					String insertErrorMessage = "�f�[�^�����G���[";
					Toast.makeText(SearchFriendsActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
					.show();
					System.err.println(e.getMessage());
				}finally{
					if(c != null){
						c.close();
					}
				}
			}
			
			//���j���[�{�^���ɖ߂鏈��
			if(tag.equals("menu")){
				
			    // �C���e���g�̃C���X�^���X����
				Intent intentMenu = new Intent(SearchFriendsActivity.this, MainActivity.class);
				// ���j���[��ʂɑJ�ڂ���
				startActivity(intentMenu);				
			}
		}	
	}
}