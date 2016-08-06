package com.vosaye.xagon;

import com.vosaye.xagon.customviews.ImagePlayGround;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayGround extends Activity {
	ImagePlayGround ipg;
	int rows, minboxes;
	Xagons xagons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		xagons = (Xagons) this.getApplication();
		try{
		setContentView(R.layout.activity_play_ground_land);
		ipg = (ImagePlayGround) this.findViewById(R.id.board);
		
		TextView instr1, instr2;
		instr1 = (TextView) this.findViewById(R.id.textView7);
		instr2 = (TextView) this.findViewById(R.id.textView8);
		

		
		if(!xagons.paused){
		
		Intent i = this.getIntent();
		int rows = i.getIntExtra("rows", 1);
		int minboxes = i.getIntExtra("minboxes", 2);
		boolean tut = i.getBooleanExtra("tut", false);
		//ipg.setIsTut(tut);
		
		if(!tut){
			
			instr1.setText("");
			instr1.setHeight(0);
			instr2.setText("");
			instr2.setHeight(0);
			
		}
		ipg.setMetrics(rows,minboxes, tut);
		ipg.setActivity(this);
		ipg.invalidate();
		
		}
		else{
			
		}
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void onStart(){
		super.onStart();
		
		
		
		

		
		
	}
	
	public void onResume(){
		super.onResume();
		
        /*
 if(xagons.paused){ 

        	setContentView(R.layout.activity_play_ground_land);

        	xagons.paused = false;
			ipg = (ImagePlayGround) this.findViewById(R.id.board);
			TextView instr1, instr2;
			instr1 = (TextView) this.findViewById(R.id.textView7);
			instr2 = (TextView) this.findViewById(R.id.textView8);
			ipg.resumeGame(xagons);
			if(!ipg.tut){
				
				instr1.setVisibility(View.GONE);
				instr2.setVisibility(View.GONE);
			}
			ipg.setActivity(this);
			ipg.invalidate();

		}
*/
	}
	
	
	
	
	public void onPause(){
		super.onPause();
		//ipg.saveGame();
		
		
	}
	
	public void howtoplay(View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("How to Play");
		//builder.setView(view);
	}
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_play_ground_land);

            if(xagons.paused){ 

    			
            	xagons.paused = false;
				ipg = (ImagePlayGround) this.findViewById(R.id.board);
				TextView instr1, instr2;
				instr1 = (TextView) this.findViewById(R.id.textView7);
				instr2 = (TextView) this.findViewById(R.id.textView8);
				ipg.resumeGame(xagons);
				if((!ipg.tut)||(ipg.tut&&ipg.stepIndex>4)){
    				
    				instr1.setHeight(0);
    				instr2.setHeight(0);
    				instr1.setText("");
    				instr2.setText("");
    				
    			}else{
    				if(ipg.tut){
						if(ipg.stepIndex==1){
							instr1.setText("Excellent! Now tap it again!!");
							instr2.setText("tapping it will make it red again.\nClicking a brick switches its color.");
							
						}else if(ipg.stepIndex==2){
							instr1.setText("Perfect! tap it once more!!");
							instr2.setText("Every brick's number can't exceed the level itself. In this case it can't exceed 3!");
						}else if(ipg.stepIndex==3){

							instr1.setText("You are getting a hang of this!\nGo on, tap it again!");
							instr2.setText("Bricks that exceed the level will start back at 1");
						}else if(ipg.stepIndex==4){
							instr1.setText("You are good at this! \n Now go ahead tap any bricks!!");
							instr2.setText("Bring the total of the numbers on bricks to the target on top right. The current total is displayed below it.");
						}else if(ipg.stepIndex>4){
							instr1.setText("");
							instr2.setText("");
							instr1.setHeight(0);
							instr2.setHeight(0);
						}
    				}
    			}
				ipg.setActivity(this);
				ipg.invalidate();

    		}
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.only_landscape_message);         
            
    		xagons.paused = true;
    		System.out.println("vosayen : changed to potrait");
    		System.out.println("vosayen : "+xagons.currentS+" - "+xagons.totalS+" - "+ipg.xagon.nodesCount);
        }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_ground, menu);
		return true;
	}
	
	

}
