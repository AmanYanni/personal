/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brick.breaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Aman Yanni
 */
public class Ball {
    
    private double x,y,dx,dy;
    private int ballSize=20,scrWid,scrHei;
    private Color colors[];
    
    public Ball(int Wid,int Hei,Graphics2D g,Color col[]){
        colors=col;
        scrWid=Wid;
        scrHei=Hei;
        x=scrWid/2-ballSize/2;
        y=Hei-52-ballSize;
        dx=2.4;
        dy=-4.3;
        draw(g);
        
    }
    
    public void update(){
        setPosition();
    }
    
    public void setPosition(){
        x+=dx;
        y+=dy;
        if(x<0)
            dx=-dx;
            
        if(y<0)
            dy=-dy;
        if(x>scrWid-ballSize)
            dx=-dx;
        if(y>scrHei-ballSize)
            dy=-dy;
    }
    
    public void draw(Graphics2D g){
        
        g.setColor(colors[0]);
        g.fillOval((int)x+1, (int)y+1, ballSize-1,ballSize-1);
        g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
        g.setStroke(new BasicStroke(4));
        g.drawOval((int)x, (int)y, ballSize,ballSize);
    }
    public void followPaddle(int xPos,Graphics2D g){
        x=xPos-ballSize/2;
        draw(g);
    }
    
    public Rectangle getRect(){
        return new Rectangle((int)x,(int)y,ballSize,ballSize);
    }
    
    public void setDy(double newDy){
        dy=newDy;
    }
    
    public double getDY(){
        return dy;
    }
    
    public void setDx(double newDx){
        dx=newDx;
    }
    
    public double getDX(){
        return dx;
    }
    public double getX(){
        return x+ballSize/2;
    }
    public double getY(){
        return y+ballSize/2;        
    }
    
    
    public boolean isLose(){
        boolean lose=false;
        
        if(y>scrHei-ballSize){
            lose=true;
        }
        
        return lose;
    }
    public int getWidth(){
        return ballSize;
    }
}
