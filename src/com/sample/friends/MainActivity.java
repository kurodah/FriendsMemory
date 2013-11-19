package com.sample.friends;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		//�{�^���̓���
		  Button registButton=(Button)findViewById(R.id.registMenu_button);
		  Button searchButton=(Button)findViewById(R.id.searchMenu_button);
		  
		  //�o�^���j���[�փ{�^���������ꂽ�ꍇ�̏���
		  registButton.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
					// �C���e���g�̃C���X�^���X����
					Intent intentRegistMenu = new Intent(MainActivity.this, RegistFriendsActivity.class);
					// �o�^���j���[�̃A�N�e�B�r�e�B�N��
					startActivity(intentRegistMenu);
			  }
		  });

		//�������j���[�փ{�^���������ꂽ�ꍇ�̏���
		  searchButton.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
					// �C���e���g�̃C���X�^���X����
					Intent intentSearchMenu = new Intent(MainActivity.this, SearchFriendsActivity.class);
					// �o�^���j���[�̃A�N�e�B�r�e�B�N��
					startActivity(intentSearchMenu);
			  }
		  });		  
		  
	}	
}
