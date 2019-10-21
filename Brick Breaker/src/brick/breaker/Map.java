/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brick.breaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Aman Yanni
 */
public class Map {
    
    private int[][] myMap;
    private int brickHeight,brickWidth,scrWid,scrHei;
    private  Color col[];
    
    public final int H_PAD=100,V_PAD=75;
    
    public Map(int r,int c,int Wid,int Hei,Color col[]){
        this.col=col;
        init(r,c);
        scrWid=Wid;
        scrHei=Hei;
        brickWidth=(scrWid-2*H_PAD)/c;
        brickHeight=(scrHei/2-V_PAD*2)/r;
    }
    
    public void init(int r,int c){
        myMap= new int[r][c];
        for(int i=0;i<myMap.length;i++){
            for(int j=0;j<myMap[i].length;j++){
                int rand=(int)(Math.random()*3+1);
                myMap[i][j]=rand;
            }
        }
    }
    
    public void draw(Graphics2D g){
        
        for(int r=0;r<myMap.length;r++){
            for(int c=0;c<myMap[r].length;c++){
                if(myMap[r][c]>0){
                    g.setColor(col[4-myMap[r][c]]);
                    g.fillRect(c*brickWidth+H_PAD,r*brickHeight+V_PAD,brickWidth,brickHeight);
                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.BLACK);
                    g.drawRect(c*brickWidth+H_PAD,r*brickHeight+V_PAD,brickWidth,brickHeight);
                }
            }
        }
    }
    public boolean isWin(){
        boolean win=false;
        int brickCnt=0;
        
        for(int i=0;i<myMap.length;i++){
            for(int j=0;j<myMap[i].length;j++){
                brickCnt+=myMap[i][j];
            }
        }
        if(brickCnt==0)win=true;
        return win;
    }
    public int[][] getMap(){
        return myMap;
    }
    public void setBrick(int r, int c,int value){
        myMap[r][c]=value;
    }
    
    public int getValue(int r , int c){
        return 0;
    }
    
    public int getWidth(){
        return brickWidth;
    }
    
    public int getHeight(){
        return brickHeight;
    }
    
    public void hitBrick(int r,int c){
        myMap[r][c]-=1;
        if(myMap[r][c]<0)myMap[r][c]=0;
    }
    
}
