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
public class Paddle {
    
    private double x;
    private int scrWid,scrHei;
    public int height,width,Y;
    private Color colors[];
    public Paddle(int Wid,int Hei,Graphics2D g,Color col[]){
        colors=col;
        scrWid=Wid;
        scrHei=Hei;
        Y=scrHei-50;
        width=150;
        height=15;
        x=(scrWid/2-width/2);
        //draw(g);
    }
    
    public void update(){
        
    }
    
    public void draw(Graphics2D g){
        g.setColor(colors[0]);
        g.fillRoundRect((int)x+1, Y+1, width-1, height-1,15,15);
        g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect((int)x, Y,width,height,15,15);
    }
    
    public void mouseMoved(int xPos){
        if(xPos>scrWid-width/2)x=scrWid-width;
        else if(xPos<0+width/2)x=0;
        else x=xPos-width/2;
    }
    public Rectangle getRect(){
        return new Rectangle((int)x,Y,width,height);
    }
    public int getPadCen(){
        return (int)x+width/2;
    }
    public int getWidth(){
        return width;
    }
    
}
