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
	
	//SQLiteDatabase�̒�`
	CreateProductHelper helper = null;
    SQLiteDatabase db = null;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_friends);
		
		// �{�^���̓���
		Button registButton = (Button) findViewById(R.id.register);
		
		//���W�I�{�^���Ƀ`�F�b�N������
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);		
		radioGroup.check(R.id.man);

				
		//�����ꂽ�{�^���𔻕ʂ��邽�߁Atag��t�^����B
		registButton.setTag("insert");
		
		// �N���b�N���X�i�[�̎��s
		registButtonClickListener regBCListerner = new registButtonClickListener();
		registButton.setOnClickListener(regBCListerner);

	    // DB�쐬
        helper = new CreateProductHelper(RegistFriendsActivity.this);		
	}

	// �o�^�{�^���̃N���b�N���X�i�[�̒�`
	class registButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			
			//�^�O�̎擾�i�ǂ̃{�^���������ꂽ�̂����ʁj
			String tag = (String)v.getTag();
			
            // �Y��DB�I�u�W�F�N�g�擾
            db = helper.getWritableDatabase();
			
			//�o�^�{�^���������ꂽ�ۂ̏���
			if(tag.equals("insert")){
	
				//�F�B���̒ǉ�����
				try{
					//�e�L�X�g�{�b�N�X���疼�O�A�������擾����
					EditText friendsNameText = (EditText) findViewById(R.id.friends_name);
					EditText placeText = (EditText) findViewById(R.id.place);
					EditText friendsMemoText = (EditText) findViewById(R.id.friends_memo);
		
					String friendsName = friendsNameText.getText().toString();
					String meetPlace = placeText.getText().toString();
					String friendsMemo = friendsMemoText.getText().toString();
					
					System.out.println(friendsName);
					
					if(friendsName != ""){
						
						//�e�L�X�g�{�b�N�X�̎擾���e�m�F
						//System.out.println(friendsName);
						//System.out.println(friendsMemo);					
						
						
						//�`�F�b�N�{�b�N�X�̏�Ԃ��擾����
						CheckBox checkbox = (CheckBox)findViewById(R.id.favoriteCheck);
						Boolean favoriteCheck = checkbox.isChecked();										
						
						//�`�F�b�N�{�b�N�X�̎擾���e�m�F
						System.out.println(favoriteCheck);
						
						//DB�o�^�p��favoriteFlg�̒l������
						int favoriteFlg;
						
						if(favoriteCheck == true){
							favoriteFlg = 1;
						}else{
							favoriteFlg = 0;
						}
						
						
						//�N����擾����
						Spinner spinner =(Spinner)findViewById(R.id.age_list);
						String ageSelect = (String)spinner.getSelectedItem();
						int age = 0;
						//DB�ɓo�^����age�̒l������
						if(ageSelect.equals("10��")){
							age = 0;
						}else if(ageSelect.equals("20��")){
							age = 1;
						}else if(ageSelect.equals("30��")){
							age = 2;
						}else if(ageSelect.equals("40��")){
							age = 3;
						}else if(ageSelect.equals("50��")){
							age = 4;
						}
						
						
						
						//���W�I�{�^���̒l���擾����
						RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex_group);
						RadioButton checkedButton =  (RadioButton) findViewById(radioGroup
						        .getCheckedRadioButtonId());
						String sexText = checkedButton.getText().toString();
						System.out.println(sexText);
	
						//DB�o�^�p��sex�̒l������
						int sex;
						
						if(sexText.equals("�j")){
							sex = 0;
						}else{
							sex = 1;
						}
						
						RegistFriendsDao regFriendsDao = new RegistFriendsDao();
						Context con = RegistFriendsActivity.this;
						// @sugiim Inputはバラバラに渡すのではなく、entityにまとめてから渡すべき（仕様変更に弱くなる）
						regFriendsDao.registDB(con,friendsName,meetPlace,friendsMemo,favoriteFlg,age,sex);
						
											
						//Toast�ŕ\���p�ɉ��H
						friendsName = friendsName + "�����ǉ����܂����B";
											
						// �o�^�������O��Toast�ŕ\������B
						Toast.makeText(RegistFriendsActivity.this, friendsName, Toast.LENGTH_SHORT)
							.show();
										
						// �F�B�o�^������ʂɑJ�ڂ���B
						Intent registComplete = new Intent(RegistFriendsActivity.this, RegistResultActivity.class);
						startActivity(registComplete);
						
					}else{
						String insertErrorMessage = "���O���͂��Ă�������";
						Toast.makeText(RegistFriendsActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
						.show();
					}
					
				}catch(Exception e){
					String insertErrorMessage = "�f�[�^Insert�G���[";
					Toast.makeText(RegistFriendsActivity.this, insertErrorMessage, Toast.LENGTH_SHORT)
					.show();
                }						
			}
		}
	}
}
