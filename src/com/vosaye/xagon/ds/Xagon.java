/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vosaye.xagon.ds;

import com.vosaye.xagon.ds.exception.*;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Roger
 */
public class Xagon {
	public int current = 0, previous = -1;
    int minBoxes = 3;
    //public int end;
    Random random;
    int rows = 11, columns, diagonals;
    Random r;
    public Node matrix[][];
    Vector<int[]> upperD = new Vector<int[]>();
    Vector<int[]> lowerD = new Vector<int[]>();
    Vector<Integer> groups;
    public int nodesCount = (minBoxes*2)+((minBoxes+1)*2)+(minBoxes+2);
    public Vector<Node> nodes = new Vector<Node>();
    public Xagon(int minNoOfBoxes, int nOfRows) throws XagonException{
        this.minBoxes = minNoOfBoxes;
        this.rows = nOfRows;
        if(rows==3&&minBoxes==1) nodesCount = 4;
        else if(rows==1) nodesCount = minBoxes;
        this.r = new Random();
        int temp = 0;
        random = new Random();
        groups = new Vector<Integer>();
       
        //Calculate nodesCount
        for(int i=0; i<(rows/2); i++){
            temp = temp + minBoxes + i;
        }
        temp = (temp*2)+(((rows))/2)+minBoxes;
        nodesCount = temp;
        
        
        //Initiate nodes
        
        /*
        for(int i=0; i<nodesCount; i++){
            Node node = new Node(""+i);
            
            nodes.add(node);
        }
        */
        
        //Calculate nOfColumns
        temp = ((rows)/2)+minBoxes;
        columns = (2*temp) + 1;
        
        //Calculating number of diagonals
        diagonals = (6+(2*(minBoxes-2)))+(rows-3);
        
        
        //InitiateMatrix
        matrix = new Node[columns][(rows)];
        for(int i=0; i<columns; i++)
        	for(int j=0; j<rows; j++){
                matrix[i][j] = new Node("n");
                matrix[i][j].value = 1;
            }
        
        //Assign data and Horizontal Distances
        //temp = 0;
        int counter = -1;
        for(int i = 0; i<rows; i++){
            temp = i;
            if(temp>rows/2) temp = rows - (temp+1);
            counter += 1;
            //System.out.println(counter);
            groups.add(counter);
            counter += ((temp-1)+minBoxes);
            //System.out.println(counter);
            groups.add(counter);
            //Random rand = new Random();

            //no of cols = minboxes + temp
            for(int j=getFirstIndex(minBoxes+temp); j<=getFirstIndex(minBoxes+temp)+((minBoxes+temp-1)*2); j+=2){
            	if(matrix[j][i]==null) matrix[j][i] = new Node("n");
            	
                matrix[j][i].value = random.nextInt(3);
                matrix[j][i].jCol = j;
                matrix[j][i].iRow = i;
                
                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                //int randomNum = rand.nextInt((3 - 0) + 1) + 0;

                matrix[j][i].colorCode = random.nextInt(2);
                nodes.add(matrix[j][i]);
                nodes.elementAt(0).color = Node.SELECTED;
                
            }
            
            //if(matrix[getDistanceIndex(minBoxes+temp)][i]==null) matrix[getDistanceIndex(minBoxes+temp)][i] = new Node("n");
            //matrix[getDistanceIndex(minBoxes+temp)][i].value = 3;
        }
        //nodes.elementAt(nodes.size()-1).value = 3;
        //nodes.elementAt(0).value = Node.TRIANGLE;
        //Displaying
        temp = 0;
        for(int i=0; i<(rows); i++){
            
            for(int j=0;j<columns;j++){
                //if(matrix[j][i].value==2||matrix[j][i].value==3)
                    if(matrix[j][i]!=null)
                        System.out.print(" "+matrix[j][i].value+" ");
                    else
                        System.out.print(" x "+" ");
                //else
                    //System.out.print(" x ");
            }
            System.out.println();
        }
        
        //Assign Diagonal Distances
        for(int i=0; i<=rows/2; i++){
            if(i==0){
            for(int j=getFirstIndex(minBoxes+i); j<=getFirstIndex(minBoxes+i)+((minBoxes+i-1)*2); j+=2){
                int a[] = {i,j,r.nextInt(10)+1};
                upperD.add(a);
            }
            }
            else{
                int a[] = {i,(getFirstIndex(minBoxes+i)+((minBoxes+i-1)*2)),r.nextInt(10)+1};
                upperD.add(a);
            }
        }
        for(int i=rows-1; i>=rows/2; i--){
            temp = i;
            if(temp > rows/2) temp = rows - (temp+1);
            if(i==rows-1){
                for(int j=getFirstIndex(minBoxes+temp); j<=getFirstIndex(minBoxes+temp)+((minBoxes+temp-1)*2); j+=2){
                int a[] = {i,j,r.nextInt(10)+1};
                lowerD.add(a);
            }
            }else{
                int a[] = {i,(getFirstIndex(minBoxes+temp)+((minBoxes+temp-1)*2)),r.nextInt(10)+1};
                lowerD.add(a);
            }
        }
        //System.out.println("Upper Dialogs");
        for(int i=0;i<upperD.size();i++){
            int a[] = upperD.elementAt(i);
            //System.out.println(a[0]+" "+a[1]+" "+a[2]);
        }
        //System.out.println("Lower diagonal");
        for(int i=0;i<lowerD.size();i++){
            int a[] = lowerD.elementAt(i);
            //System.out.println(a[0]+" "+a[1]+" "+a[2]);
        }
        
        //end = nodes.size()-1;
        //nodes.elementAt(end).color = Node.UNSELECTED;
        
    }
    
    
    
    public Xagon(int minNoOfBoxes, int nOfRows,boolean inTut) throws XagonException{
        this.minBoxes = minNoOfBoxes;
        this.rows = nOfRows;
        this.r = new Random();
        int temp = 0;
        random = new Random();
        groups = new Vector<Integer>();
       
        //Calculate nodesCount
        for(int i=0; i<(rows/2); i++){
            temp = temp + minBoxes + i;
        }
        temp = (temp*2)+(((rows))/2)+minBoxes;
        nodesCount = temp;
        
        

        if(rows==3&&minBoxes==1) nodesCount = 4;
        else if(rows==1) nodesCount = minBoxes;
        
        System.out.println(nodesCount+" Vosayen");
        
        //Initiate nodes
        
        /*
        for(int i=0; i<nodesCount; i++){
            Node node = new Node(""+i);
            
            nodes.add(node);
        }
        */
        
        //Calculate nOfColumns
        temp = ((rows)/2)+minBoxes;
        columns = (2*temp) + 1;
        
        //Calculating number of diagonals
        diagonals = (6+(2*(minBoxes-2)))+(rows-3);
        
        
        //InitiateMatrix
        matrix = new Node[columns][(rows)];
        for(int i=0; i<columns; i++)
        	for(int j=0; j<rows; j++){
                matrix[i][j] = new Node("n");
                matrix[i][j].value = 1;
            }
        
        //Assign data and Horizontal Distances
        //temp = 0;
        int counter = -1;
        for(int i = 0; i<rows; i++){
            temp = i;
            if(temp>rows/2) temp = rows - (temp+1);
            counter += 1;
            //System.out.println(counter);
            groups.add(counter);
            counter += ((temp-1)+minBoxes);
            //System.out.println(counter);
            groups.add(counter);
            //Random rand = new Random();
            int x = 0;
            //no of cols = minboxes + temp
            for(int j=getFirstIndex(minBoxes+temp); j<=getFirstIndex(minBoxes+temp)+((minBoxes+temp-1)*2); j+=2){
            	if(matrix[j][i]==null) matrix[j][i] = new Node("n");
            	if(inTut)
                matrix[j][i].value = x;
            	else matrix[j][i].value = random.nextInt(3);
                matrix[j][i].jCol = j;
                matrix[j][i].iRow = i;
                
                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                //int randomNum = rand.nextInt((3 - 0) + 1) + 0;
                if(inTut){
	                if(x<=1)
	                	matrix[j][i].colorCode = 0;
	                else
	                    matrix[j][i].colorCode = 1;
	            }
                else  matrix[j][i].colorCode = random.nextInt(2);
                nodes.add(matrix[j][i]);
                nodes.elementAt(0).color = Node.SELECTED;
                x++;
                
            }
            
            //if(matrix[getDistanceIndex(minBoxes+temp)][i]==null) matrix[getDistanceIndex(minBoxes+temp)][i] = new Node("n");
            //matrix[getDistanceIndex(minBoxes+temp)][i].value = 3;
        }
        //nodes.elementAt(nodes.size()-1).value = 3;
        //nodes.elementAt(0).value = Node.TRIANGLE;
        //Displaying
        temp = 0;
        for(int i=0; i<(rows); i++){
            
            for(int j=0;j<columns;j++){
                //if(matrix[j][i].value==2||matrix[j][i].value==3)
                    if(matrix[j][i]!=null)
                        System.out.print(" "+matrix[j][i].value+" ");
                    else
                        System.out.print(" x "+" ");
                //else
                    //System.out.print(" x ");
            }
            System.out.println();
        }
        
        //Assign Diagonal Distances
        for(int i=0; i<=rows/2; i++){
            if(i==0){
            for(int j=getFirstIndex(minBoxes+i); j<=getFirstIndex(minBoxes+i)+((minBoxes+i-1)*2); j+=2){
                int a[] = {i,j,r.nextInt(10)+1};
                upperD.add(a);
            }
            }
            else{
                int a[] = {i,(getFirstIndex(minBoxes+i)+((minBoxes+i-1)*2)),r.nextInt(10)+1};
                upperD.add(a);
            }
        }
        for(int i=rows-1; i>=rows/2; i--){
            temp = i;
            if(temp > rows/2) temp = rows - (temp+1);
            if(i==rows-1){
                for(int j=getFirstIndex(minBoxes+temp); j<=getFirstIndex(minBoxes+temp)+((minBoxes+temp-1)*2); j+=2){
                int a[] = {i,j,r.nextInt(10)+1};
                lowerD.add(a);
            }
            }else{
                int a[] = {i,(getFirstIndex(minBoxes+temp)+((minBoxes+temp-1)*2)),r.nextInt(10)+1};
                lowerD.add(a);
            }
        }
        //System.out.println("Upper Dialogs");
        for(int i=0;i<upperD.size();i++){
            int a[] = upperD.elementAt(i);
            //System.out.println(a[0]+" "+a[1]+" "+a[2]);
        }
        //System.out.println("Lower diagonal");
        for(int i=0;i<lowerD.size();i++){
            int a[] = lowerD.elementAt(i);
            //System.out.println(a[0]+" "+a[1]+" "+a[2]);
        }
        
        //end = nodes.size()-1;
        //nodes.elementAt(end).color = Node.UNSELECTED;
        
    }
    
    
    
    
    final public int getFirstIndex(int nOfNodes){
        return ((columns)-(nOfNodes+(nOfNodes-1)))/2;
    }
    
    final public int getDistanceIndex(int nOfNodes){
        return getFirstIndex(nOfNodes)+1;
    }
    final public int getGroup(int i){
        for(int j=0;j<groups.size();j+=2){
            if(i>= groups.elementAt(j)&&i<= groups.elementAt(j+1))
                return j;
        }
        return 0;
    }
    final public boolean transit(int i, int j) throws XagonException{
    	
        Node a,b;
        if(i==-1||j==-1) throw new IllegalArgumentException("Wrong indexes");
        a = this.nodes.elementAt(i);
        b = this.nodes.elementAt(j);
        System.out.println(a.iRow+"  "+a.jCol+"  "+i+"  -  "+b.iRow+"  "+b.jCol+"  "+j);
        if(((Math.abs(j-i)==1)&&(getGroup(i) == getGroup(j)))||((Math.abs(a.iRow - b.iRow)==1)&&((Math.abs(a.jCol - b.jCol)==1)))){
        	
        	
            return true;
        }else{
            return false;
        }
    }
    
    
    
    final public boolean findWhichDiagonal(int x1, int y1, int x2, int y2){
        if(x2<x1&&y2<y1){
            return false;
        }
        else if(x2>x1&&y2<y1){
            return true;
        }
        else if(x2<x1&&y2>y1){
            return true;
        }
        else if(x2>x1&&y2>y1){
            return false;
        }
        return false;
    }
    
    final public int getDiagonalDistance(int x1, int y1, int x2, int y2){
        int a[];
        if(this.findWhichDiagonal(x1, y1, x2, y2)){
            System.out.println("Upper");
            for(int i=0;i<upperD.size();i++){
                a = upperD.elementAt(i);
                if((Math.abs(y1-a[0])==Math.abs(x1-a[1]))&&(Math.abs(y2-a[0])==Math.abs(x2-a[1]))){
                    System.out.println(a[0]+"  "+a[1]);
                    return a[2];
                }
            }
        }
        else{
            System.out.println("Lower");
            for(int i=0;i<lowerD.size();i++){
                a = lowerD.elementAt(i);
                if((Math.abs(y1-a[0])==Math.abs(x1-a[1]))&&(Math.abs(y2-a[0])==Math.abs(x2-a[1]))){
                    System.out.println(a[0]+"  "+a[1]);
                    return a[2];
                }
            }
        }
        return -1;
    }
    
    
    
    public static void main(String args[]){
        try {
            new Xagon(2, 5);
        } catch (XagonException ex) {
            ex.printStackTrace();
        }
    }
}
