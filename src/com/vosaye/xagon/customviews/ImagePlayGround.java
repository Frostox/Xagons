package com.vosaye.xagon.customviews;

import com.vosaye.xagon.R;
import com.vosaye.xagon.Xagons;
import com.vosaye.xagon.ds.Node;
import com.vosaye.xagon.ds.Xagon;
import com.vosaye.xagon.ds.exception.XagonException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path.FillType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.view.View.OnClickListener;

import java.util.*;

public class ImagePlayGround extends ImageView implements OnTouchListener, OnClickListener{
	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	ImageView board;
	float width, height;
	float boxTop, boxLeft, boxRight, boxBottom,boxwidth,boxheight;
	Random r = new Random();
	public int stepIndex = 0;
	TextView level, targetSum, currentSum;
	int targetS=0, currentS=0;
	TextView header, footer;
	Toast toast;
	//int shapes[] = {0,1,2}; 
	public int numbers = 2;
	//int colors[] = {Color.parseColor("#1E824C"),Color.parseColor("#c0392b"),Color.parseColor("#2C3E50"),Color.parseColor("#D35400")};
	public boolean tut = false;
	
	int colors[] = {Color.parseColor("#c0392b"),Color.parseColor("#2c3e50")/*,Color.parseColor("#2c3e50")*/};
	Rect bounds = new Rect();
	Activity context;
	public Xagon xagon;
	Xagons xagonApp;
	int row = 11, minBoxes = 3;
	float maxBoxes = minBoxes+((row)/2);
	Vector<float[]> blocks = new Vector<float[]>();
	public ImagePlayGround(Context context) {
		super(context);
		
		board = (ImageView) this.findViewById(R.id.board);
		board.setOnTouchListener(this);
		try {
			//xagon = new Xagon(minBoxes, row);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.onFinishInflate();
		this.isInEditMode();

		saveGame();
	}
	
	public ImagePlayGround(Context context, AttributeSet attrs){
		super(context,attrs);
		board = (ImageView) this.findViewById(R.id.board);
		board.setOnTouchListener(this);
		
		try {
			//xagon = new Xagon(minBoxes, row);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saveGame();
	}
	
	public void resumeGame(Xagons xagonApp){
		numbers = xagonApp.numbers;
		row = xagonApp.row;
		minBoxes = xagonApp.minBoxes;
		currentS = xagonApp.currentS;
		targetS = xagonApp.totalS;
		tut = xagonApp.isTut;
		xagon = xagonApp.xagon;
		stepIndex = xagonApp.stepIndex;
		this.invalidate();
	}
	
	public ImagePlayGround(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		board = (ImageView) this.findViewById(R.id.board);
		board.setOnTouchListener(this);
		board.setOnClickListener(this);
		try {
			//xagon = new Xagon(minBoxes, row);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		saveGame();
	}
	public void setIsTut(boolean tut){
		this.tut = tut;
		try {
			xagon = new Xagon(minBoxes, row);
			
			this.invalidate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setMetrics(int row, int minBoxes, boolean isTut){
		this.row = row;
		this.tut = isTut;
		this.minBoxes = minBoxes;
		try {
			xagon = new Xagon(minBoxes, row, isTut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}
		if(isTut)
		targetS = 6;
		invalidate();
		
		//saveGame();
	}
	
	public void saveGame(){
		if(xagon==null) return;
		try{
		xagonApp.row = row;
		xagonApp.minBoxes = minBoxes;
		xagonApp.currentS = currentS;
		xagonApp.totalS = this.targetS;
		xagonApp.isTut = this.tut;
		xagonApp.xagon = xagon;
		xagonApp.numbers = numbers;
		xagonApp.stepIndex = stepIndex;
		}catch(Exception e){e.printStackTrace();}
	}
	
	View v;
	@SuppressWarnings("static-access")
	public void setActivity(Activity act){
		context = act;
		xagonApp = (Xagons) act.getApplication();
		level = (TextView) context.findViewById(R.id.textView1);
		targetSum = (TextView) context.findViewById(R.id.textView3);
		currentSum = (TextView) context.findViewById(R.id.textView6);
		
		level.setText(""+(numbers+1)+"");
		
		targetSum.setText(targetS+"");
		currentSum.setText(currentS+"");
		

		header = (TextView) act.findViewById(R.id.textView7);
		footer = (TextView) act.findViewById(R.id.textView8);
		
		toast = Toast.makeText(act, "", Toast.LENGTH_LONG);
		v = act.getLayoutInflater().inflate(R.layout.toast_layout, null);
		toast.setView(v);
		toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 15);
		saveGame();
		
	}
	boolean changer = true;
	@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        if(xagon==null) return;
        int count = 0;
        p.setColor(Color.GREEN);
		blocks.clear();
	
        width = this.getWidth();
        height = this.getHeight();
        p.setStrokeWidth(1);
        if(width<height){
        	boxLeft = 0;
        	boxRight = width;
        	boxTop = (height - width)/2;
        	boxBottom = boxTop+width;
        	height = width;
        	
        }
        else{
        	
        	
        	if(!tut&&(row!=1)){
        	boxTop = 10;
        	boxBottom = height-25;
        	height = height - 25;
        	}else{
        		boxTop = height/3.5f;
            	boxBottom = height-(height/3.5f);
            	
        	}
        	
        	
        	boxLeft = (width - height)/2;
        	
        	boxRight = boxLeft+height;
	        width = height;
	        
        }
        //float boxheight = (height/row)-10;
        //for(int i=1; i<=row; i++){
        //	canvas.drawRect(10, (i-1)*boxheight+10, 100, i*boxheight, p);
        //	p.setColor(Color.WHITE);
        //	canvas.drawRect(15, ((i-1)*boxheight+10)+5, 100-5, (i*boxheight)-5, p);
        //	p.setColor(Color.BLUE);
        //}
        //canvas.drawRect(boxLeft, boxTop, boxRight, boxBottom, p);
        p.setColor(Color.WHITE);
        //canvas.drawRect(boxLeft+5, boxTop+5, boxRight-5, boxBottom-5, p);
        int temp;
        maxBoxes = minBoxes+(row/2);
		//Toast.makeText(this.getContext(),""+maxBoxes,Toast.LENGTH_LONG).show();
        boxwidth = (boxRight - boxLeft)/maxBoxes;
        boxheight = (boxBottom - boxTop)/row;
    	float tempx =0;
		for(int i=row-1;i>=1;i--){
			tempx = i*(boxheight/4);
		}
		boxheight += tempx;
		currentS = 0;
        for(int i=0; i<row; i++){
        	temp = i;
        	if(i>(row)/2){
        		temp =  (row - (temp+1));
        	}
        	int cols =  (temp+minBoxes);
        	System.out.println(cols);
        	
        	for(int j=0; j<cols; j++){
        		if(currentSum!=null)
        		currentS += (xagon.nodes.elementAt(count+j).value+1);
        		
        		p.setStrokeWidth(3);	
        		p.setColor(colors[xagon.nodes.elementAt(count+j).colorCode]); 
        		//p.setColor(Color.rgb(255,228,196));
        		//canvas.drawRect((boxwidth*(j))+boxLeft+((width-(boxwidth*cols))/2), boxheight*(i), (boxwidth*(j)+boxLeft)+((width-(boxwidth*cols))/2)+boxwidth, (boxheight*(i))+boxheight, p);
        	    float left = (boxwidth*(j))+boxLeft+((width-(boxwidth*cols))/2),right = left+boxwidth,top = (boxheight*i)-(i*(boxheight/4))+boxTop,bottom = top+ boxheight;
		     	//p.setColor(Color.WHITE);
				//canvas.drawRect((boxwidth*(j))+boxLeft+((width-(boxwidth*cols))/2)+10, boxheight*(i)+10, (boxwidth*(j)+boxLeft)+((width-(boxwidth*cols))/2)+boxwidth-10, (boxheight*(i))+boxheight-10, p);
                //p.setColor(Color.GREEN);
				
        	    
        	    p.setColor(colors[xagon.nodes.elementAt(count+j).colorCode]); 
				p.setStyle(Paint.Style.FILL);
			    
				Path hex = new Path();
				hex.reset();
				hex.moveTo(left+(boxwidth/2), top);
				hex.lineTo(left, top+ (boxheight/4));
				hex.lineTo(left, top+ (3*boxheight/4));
				hex.lineTo(left+ (boxwidth/2), bottom);
				hex.lineTo(right, top+(3*boxheight/4));
				hex.lineTo(right, top+(boxheight/4));
				hex.lineTo(left+(boxwidth/2), top);
				canvas.drawPath(hex,p);
				
				
				
				 
				p.setStyle(Style.STROKE);
				p.setColor(Color.parseColor("#aaaaaa"));
		        p.setStrokeWidth(5);
				hex.reset();
				hex.moveTo(left+(boxwidth/2), top);
				hex.lineTo(left, top+ (boxheight/4));
				hex.lineTo(left, top+ (3*boxheight/4));
				hex.lineTo(left+ (boxwidth/2), bottom);
				hex.lineTo(right, top+(3*boxheight/4));
				hex.lineTo(right, top+(boxheight/4));
				hex.lineTo(left+(boxwidth/2), top);
				canvas.drawPath(hex,p);
			    p.setTextSize(boxwidth-boxwidth/1.5f);
			    //if(xagon.nodes.elementAt(count+j).colorCode!=0)
			    //p.setColor(Color.parseColor("#bdc3c7"));
			    // else 
			    p.setColor(Color.parseColor("#ffffff"));
			    
			    String temps = ""+(count+j);
			    p.getTextBounds(""+(count+j), 0, temps.length(), bounds);
			    p.setStyle(Paint.Style.FILL_AND_STROKE);
				
		        p.setStrokeWidth(4);
		        p.setColor(Color.WHITE);
		        p.setStyle(Paint.Style.FILL);
				//else p.setColor(colors[xagon.nodes.elementAt(count+j).value]); 
		        
				canvas.drawText((xagon.nodes.elementAt(count+j).value+1)+"",left+(boxwidth-p.measureText(""+(xagon.nodes.elementAt(count+j).value+1)))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
				
				//canvas.drawText(""+xagon.nodes.elementAt(count+j).value,left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
				/*if(xagon.nodes.elementAt(count+j).value==Node.SQUARE){
					//canvas.drawRect(left+(boxwidth/4), top+(boxheight/4), left+3*(boxwidth/4), top+3*(boxheight/4), p); 
					
					if(xagon.nodes.elementAt(count+j).value<2)
						p.setColor(colorsLight[xagon.nodes.elementAt(count+j).value]); 
					
					
					p.setStyle(Paint.Style.FILL);
					//else p.setColor(colors[xagon.nodes.elementAt(count+j).value]); 
					canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
					
					
					p.setStyle(Paint.Style.STROKE);
					p.setColor(Color.WHITE);
					canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
					
					
				}
				else if(xagon.nodes.elementAt(count+j).value==Node.TRIANGLE){
					/*
 Path path = new Path();
					path.moveTo(left+(boxwidth/2), top+(boxheight/4));
					path.lineTo(left+(boxwidth/4), top+3*(boxheight/4));
					path.moveTo(left+(boxwidth/4), top+3*(boxheight/4));
					path.lineTo(left+3*(boxwidth/4), top+3*(boxheight/4));
					path.moveTo(left+3*(boxwidth/4), top+3*(boxheight/4));
					path.lineTo(left+(boxwidth/2), top+(boxheight/4));
					path.close();
					canvas.drawPath(path, p);

					
					
					p.setStyle(Paint.Style.FILL);
					if(xagon.nodes.elementAt(count+j).value<2)
					p.setColor(colorsLight[xagon.nodes.elementAt(count+j).value]); 
					//else p.setColor(colors[xagon.nodes.elementAt(count+j).value]); 
	        		
					canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
					
					
					
					p.setStyle(Paint.Style.STROKE);
					p.setColor(Color.WHITE);
					canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
					
					
					
				}
				else if(xagon.nodes.elementAt(count+j).value==Node.CIRCLE){
					//canvas.drawCircle(left+(boxwidth/2), top+(boxheight/2), (top+(boxheight/2))-(top+(boxheight/4)), p);
					
					p.setStyle(Paint.Style.FILL);
					if(xagon.nodes.elementAt(count+j).value<2){
					p.setColor(colorsLight[xagon.nodes.elementAt(count+j).value]); }
					//else p.setColor(colors[xagon.nodes.elementAt(count+j).value]); 
	        		
					canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
					
					
					
					p.setStyle(Paint.Style.STROKE);
					p.setColor(Color.WHITE);
					canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
					
					
					
				}
				else if(xagon.nodes.elementAt(count+j).value==3){
					if(xagon.nodes.elementAt(count+j).value<2)
						p.setColor(colorsLight[xagon.nodes.elementAt(count+j).value]); 
						//else p.setColor(colors[xagon.nodes.elementAt(count+j).value]); 
		        		
						canvas.drawText(""+(xagon.nodes.elementAt(count+j).value+1),left+(boxwidth-p.measureText(""+xagon.nodes.elementAt(count+j).value))/2,top+bounds.height()+(boxheight-bounds.height())/2,p);
						
				}*/
				float a[] = {left,top+(boxheight/4), right, top+(3*boxheight/4)};
				blocks.add(a);
        	}   
        	count += cols;
        }
        
        if(currentS == targetS){
        	
        	if(tut){
        		if(this.numbers==2){
	        		header.setText("You did it! I knew you could make it!!");
					footer.setText("You're at level 4 now! Lets play some more!!");
					stepIndex++;
        		}
        		
				else {

	        		header.setText("");
					footer.setText("");
					header.setHeight(0);
					footer.setHeight(0);
					stepIndex++;
				}
        	}
        	numbers++;
        	if(level!=null)
        	level.setText(""+(numbers+1)+"");
        	//Random r = new Random();
        	do
    		if((!tut))
    		targetS = r.nextInt((((xagon.nodesCount)*(numbers+1)))-(xagon.nodesCount))+(xagon.nodesCount);
    		else targetS = r.nextInt((((minBoxes)*(numbers+1))-minBoxes))+(minBoxes);
        	while(targetS==currentS);
        	if(targetSum!=null){
    			targetSum.setText(targetS+"");
    		}
        	invalidate();
        	try{
        		if((tut&&numbers>3)||(!tut)){
        			((TextView)v).setText("Excellent job\nYou are at level "+(numbers+1)+" now.");
        			
        			toast.show();
        		
        		}
        	}catch(Exception e){}
        }
        
        if(targetS==0){
        	//Random r = new Random();
        	int x=0;
        	
        	do
        	{
	    		if((!tut)){
	    			targetS = this.getNextTarget(); 
	    			
	    			//targetS = r.nextInt((((xagon.nodesCount)*(numbers+1)))-(xagon.nodesCount))+(xagon.nodesCount);
	    			System.out.println("Vosayener : "+xagon.nodesCount+"  "+numbers);
	    		}
	    		else {
	    			targetS = 6;
	    			
	    		}
    		
	    		x++;
	    		if(x>3) {targetS = r.nextInt((((xagon.nodesCount)*(numbers+1)))-(xagon.nodesCount))+(xagon.nodesCount); }
    		
        	}
        	while(targetS==currentS||targetS<=xagon.nodesCount||targetS>=xagon.nodesCount*(numbers+1));
        	if(targetSum!=null){
    			targetSum.setText(targetS+""); 
    		}
        }
        if(currentSum!=null)
        currentSum.setText(currentS+"");
        
        saveGame();
    }
	int current = -1, next = -1;
	@Override
	public boolean onTouch(View vx, MotionEvent event) {
		if(xagon==null) return false;
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(x>=boxLeft&&x<=boxRight&&y<=boxBottom&&y>=boxTop)
			for(int i=0; i<blocks.size();i++){
				float a[] = blocks.elementAt(i);
				
				if(x>=a[0]&&x<=a[2]&&y<=a[3]&&y>=a[1]){
					
					//Touch down here
					current = i;
					this.invalidate();
					return true;
				}
				//if(!found){ return false; }
			}
		    return false;
		case MotionEvent.ACTION_MOVE:
			//Toast.makeText(this.getContext(), "Moving", Toast.LENGTH_LONG).show();
			System.out.println(current+"  here it is");
			if(x>=boxLeft&&x<=boxRight&&y<=boxBottom&&y>=boxTop)
				for(int i=0; i<blocks.size();i++){
					float a[] = blocks.elementAt(i);
					if(x>=a[0]&&x<=a[2]&&y<=a[3]&&y>=a[1]){
						
						//move
						
						//invalidate();
						return true;
					}
				}
			this.invalidate();
		    return true;
		case MotionEvent.ACTION_UP:
			for(int i=0; i<blocks.size();i++){
				float a[] = blocks.elementAt(i);
				
				if(x>=a[0]&&x<=a[2]&&y<=a[3]&&y>=a[1]){
					if(tut&&i!=0&&stepIndex<4) {
						((TextView)v).setText("Tap the brick on left");
	        			
	        			toast.show();
						break;
					}
					else if(tut&&stepIndex<4){
						if(stepIndex==0){
							header.setText("Excellent! Now tap it again!!");
							footer.setText("tapping it will make it red again.\nClicking a brick switches its color.");
							
						}else if(stepIndex==1){
							header.setText("Perfect! tap it once more!!");
							footer.setText("Every brick's number can't exceed the level itself. In this case it can't exceed 3!");
						}else if(stepIndex==2){

							header.setText("You are getting a hang of this!\nGo on, tap it again!");
							footer.setText("Bricks that exceed the level will start back at 1");
						}else if(stepIndex==3){
							header.setText("You are good at this! \n Now go ahead tap any bricks!!");
							footer.setText("Bring the total of the numbers on bricks to the target on top right. The current total is displayed below it.");
						}
						this.stepIndex++;
					}
					
					//Touch down here
					if(i==current){
						for(int j=0; j<blocks.size(); j++){
							if(j==i) continue;
							Node node = xagon.nodes.elementAt(j);
							if(xagon.nodes.elementAt(i).colorCode==node.colorCode){
							//node.colorCode = getNextColor(node.colorCode);
							node.value = getNextShape(node.value);
							}
							//node.value = getNextShape(node.value);
							
							
						}
						xagon.nodes.elementAt(current).colorCode = getNextColor(xagon.nodes.elementAt(current).colorCode);
						//xagon.nodes.elementAt(current).value = getNextShape(xagon.nodes.elementAt(current).value);

						this.invalidate();
						return true;
					}
				}
				//if(!found){ return false; }
			}
			return true;
		    
		default:
		    return false;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public int getNextShape(int index){
		if(index==numbers) return 0;
		else return index+1;
		
	}
	public int getNextColor(int index){
		
		if(index==colors.length-1) return 0;
		else return index+1;
	}
	
	
	public int getNextTarget(){
		int newTarget = 0;
		int total = 0;
		HashMap<Integer,Integer> grp1 = new HashMap<Integer,Integer>(), grp2 = new HashMap<Integer,Integer>();
		//Initialize keys
		for(int i=0;i<=numbers;i++){
			grp1.put(i, 0);
			grp2.put(i, 0);
		}
		//Initialize counts
		for(int i=0; i<blocks.size(); i++){
			Node node = xagon.nodes.elementAt(i);
			if(node.colorCode==0){
				//grp1
				grp1.put(node.value, grp1.get(node.value)+1);
			}
			else{
				//grp2
				grp2.put(node.value, grp1.get(node.value)+1);
				
			}
			total += (node.value+1);
			
		}
		System.out.println("total "+total);
		int clicks = 0;
		Vector<Integer> selectedones = new Vector<Integer>();
		do{
			int selected;
			while(selectedones.contains(selected = r.nextInt(blocks.size()-1)));
			
			selectedones.add(selected);
			Node node = xagon.nodes.elementAt(selected);
			if(node.colorCode==0){
				//grp1
				System.out.println("total color 0 selected");
				grp1.put(node.value, grp1.get(node.value)-1);
				grp2.put(node.value, grp2.get(node.value)+1);
				int swap1, swap2;
				for(int j=0; j<=numbers;j++){
					swap1 = grp1.get(j);
					if(j==0){grp1.put(j, grp1.get(numbers));}
					else{
						
						swap2 = grp1.get(j);
						grp1.put(j, swap1);
						swap1 = swap2;
						
					}
				}
				
			}
			else{
				//grp2

				System.out.println("total color 1 selected"); 
				grp2.put(node.value, grp2.get(node.value)-1);
				grp1.put(node.value, grp1.get(node.value)+1);
				
				int swap1, swap2;
				for(int j=0; j<=numbers;j++){
					swap1 = grp2.get(j);
					if(j==0){grp2.put(j, grp2.get(numbers));}
					else{
						
						swap2 = grp2.get(j);
						grp2.put(j, swap1);
						swap1 = swap2;
						
					}
				}
			}
			
			newTarget = 0;
			for(int j=0; j<=numbers;j++){
				newTarget += (j+1)*(grp1.get(j)+grp2.get(j));
			}
			
			System.out.println("total new target "+newTarget);
			System.out.println("total clicks "+clicks);
			
			clicks++;
			
			//if(clicks>10) break; 
		}while(clicks<1);
		
		
		return newTarget;
	}
	
	public int getPreviousColor(int index){
		if(index==0) index = colors.length-1;
		else index = index - 1;
		
		return index;
	}

}
