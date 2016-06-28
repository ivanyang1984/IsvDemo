package com.iflytek.isvdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuListActivity extends Activity {

	private Button menu_btn_1;
	private Button menu_btn_2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menulist);

		menu_btn_1 = (Button) findViewById(R.id.menu_btn_1);
		menu_btn_2 = (Button) findViewById(R.id.menu_btn_2);

		menu_btn_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MenuListActivity.this, UserActivity.class);
				startActivity(intent);

			}
		});

		menu_btn_2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MenuListActivity.this, UserActivity.class);
				startActivity(intent);

			}
		});

	}
}
