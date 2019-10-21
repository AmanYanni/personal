/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brick.breaker;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;


/**
 *
 * @author Aman Yanni
 */
public class GamePanel extends JPanel{
    
    private int WIDTH,HEIGHT;
    public boolean running,started=false,paused=false;
    private BufferedImage image;
    private Graphics2D g;
    private MouseMotionHandle myMotionHandle;
    private MouseHandle myHandle;
    private TimeHandle myTimeHandle;
    private int dimension[];
    private  Color colors[];
    
    
    private int xM;
    
    Timer myTime;
    Ball myBall;
    Paddle myPaddle;
    Map myMap;
    HUD myHUD;
    
    
    public GamePanel(int x, int y, int dim[],Color col[]){
        WIDTH=x;
        HEIGHT=y;
        dimension=dim;
        colors=col;
        init();
    }
    
    @Override
    public void setSize(int x,int y){
        WIDTH=x;
        HEIGHT=y;
    }
    
    public void init(){
        xM=WIDTH/2;
        myMap= new Map(dimension[0],dimension[1],WIDTH,HEIGHT,colors);
        myHUD=new HUD(colors);
        running=true;
        image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        g=(Graphics2D)image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myBall=new Ball(WIDTH,HEIGHT,g,colors);
        myPaddle=new Paddle(WIDTH,HEIGHT,g,colors);
        myMotionHandle=new MouseMotionHandle();
        myHandle=new MouseHandle();
        myTimeHandle = new TimeHandle();
        myTime=new Timer(13,myTimeHandle);
        addMouseMotionListener(myMotionHandle);
        addMouseListener(myHandle);
    }
    
    
    public void checkCollision(){
        Rectangle ballRect=myBall.getRect();
        Rectangle paddleRect = myPaddle.getRect();
        
        if(ballRect.intersects(paddleRect)){
            myBall.setDy(-myBall.getDY());
            if(myBall.getX()>xM-myPaddle.getWidth()/4&&myBall.getX()<xM+myPaddle.getWidth()/4){
                if(myBall.getDX()>0)myBall.setDx(2.4);
                else if(myBall.getDX()<0)myBall.setDx(-2.4);
            }
            else if(myBall.getX()<xM-myPaddle.getWidth()/4&&myBall.getX()>xM-myPaddle.getWidth()*3/8){
                myBall.setDx(myBall.getDX()-0.6);
            }
            else if(myBall.getX()<xM-myPaddle.getWidth()*3/8){
                if(myBall.getDX()>0)myBall.setDx(-2.4);
                else myBall.setDx(myBall.getDX()-1.2);
                
            }
            else if(myBall.getX()>xM+myPaddle.getWidth()/4&&myBall.getX()<xM+myPaddle.getWidth()*3/8){
                myBall.setDx(myBall.getDX()+0.6);
               
            }
            else if(myBall.getX()>xM+myPaddle.getWidth()*3/8){
                if(myBall.getDX()<0)myBall.setDx(2.4);
                else myBall.setDx(myBall.getDX()+1.2);
            }
        }
        
        int myArr[][]=myMap.getMap();
        
        A: for(int r=0;r<myMap.getMap().length;r++){
            for(int c=0;c<myMap.getMap()[r].length;c++){
                if(myMap.getMap()[r][c]>0){
                    int bX=c*myMap.getWidth()+myMap.H_PAD,
                    bY=r*myMap.getHeight()+myMap.V_PAD,
                    bW=myMap.getWidth(),
                    bH=myMap.getHeight();
                    Rectangle brickRect=new Rectangle(bX,bY,bW,bH);
                    if(ballRect.intersects(brickRect)){
                        myMap.hitBrick(r, c);
                        if(myBall.getX()+myBall.getWidth()<=bX||myBall.getX()>=bX+bW) myBall.setDx(-myBall.getDX());
                        else myBall.setDy(-myBall.getDY());
                        myHUD.addScore(10);
                        break A;
                    }
                    
                }
            }
        }
    }
    
    public void update(){
        
        if(started){
            myBall.update();
        }
        
        checkCollision();
    }
    
    public void draw(){
        g.setColor(colors[0]);
        g.fillRect(0,0,WIDTH,HEIGHT);
        myBall.draw(g);
        myPaddle.draw(g);
        myMap.draw(g);
        myHUD.draw(g);
        if(!started){
            drawIns();
        }
        if(myMap.isWin()){
            drawWin();
            running=false;
            myTime.stop();
        }
        if(myBall.isLose()){
            drawLose();
            running=false;
            myTime.stop();
        }
        if(paused){
            drawPause();
            myTime.stop();
        }
        
    }
    
    public void drawPause(){
        g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
        g.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,36));
        g.drawString("PAUSED", WIDTH/2,HEIGHT/2-15);
        drawIns();
    }
    
    public void drawIns(){
        g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
        g.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,22));
        g.drawString("Click Mouse to start and pause game, Move mouse to control paddle", 300,HEIGHT/2+15);
    }
    
    public void drawWin(){
        g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
        g.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,36));
        g.drawString("Winner!!!", WIDTH/2,HEIGHT/2-15);
        
    }
    
    public void drawLose(){
        g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
        g.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,36));
        g.drawString("LOSER", WIDTH/2,HEIGHT/2-15);
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D) g;
        g2.drawImage(image,0,0,WIDTH,HEIGHT,null);
        g2.dispose();
    }
    
    private class TimeHandle implements ActionListener{
        public void actionPerformed(ActionEvent e){
                update();   
                draw(); 
                repaint();
        }
    }
    
    
    private class MouseHandle implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent evt){
            if(started){
                if(paused){
                    paused=false;
                    myTime.start();
                }
                else {
                    paused=true;
                    
                }
            }
            started=true;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            }

        @Override
        public void mouseReleased(MouseEvent e) {
             }

        @Override
        public void mouseEntered(MouseEvent e) {
            }

        @Override
        public void mouseExited(MouseEvent e) {
           }
    }
    private class MouseMotionHandle implements MouseMotionListener{
        @Override 
        public void mouseMoved(MouseEvent evt){
            myPaddle.mouseMoved(evt.getX());
            xM=myPaddle.getPadCen();
            if(!started)myBall.followPaddle(myPaddle.getPadCen(),g);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            }
    }
}
