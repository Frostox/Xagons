package com.vosaye.xagon;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class GameChooser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_chooser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_chooser, menu);
		return true;
	}
	
	public void cancel(View v){
		this.finish();
		
	}
	public void easy(View v){
		Intent i = new Intent(this,PlayGround.class);
		i.putExtra("rows", 1);
		i.putExtra("minboxes", 3);
		//i.putExtra("tut", true);
		
		this.startActivity(i);
	}
	public void hard(View v){
		Intent i = new Intent(this,PlayGround.class);
		i.putExtra("rows", 3);
		i.putExtra("minboxes", 1);
		this.startActivity(i);
		
	}
	public void bigger(View v){
		Intent i = new Intent(this,PlayGround.class);
		i.putExtra("rows", 3);
		i.putExtra("minboxes",2);
		this.startActivity(i);
		
	}

}
