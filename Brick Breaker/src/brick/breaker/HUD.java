/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brick.breaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Aman Yanni
 */
public class HUD {
        private int score;
        
    private  Color colors[];
        
        public HUD(Color col[]){
            init();
            colors=col;
        }
        
        public void init(){
            score =0;
        }
    
        public void draw(Graphics2D g){
            g.setColor((colors[0]==Color.BLACK||colors[0]==Color.WHITE)?colors[1]:colors[3]);
            g.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,18));
            g.drawString("Score "+score, 20,20);
                       
        }
        
        public int getScore(){
            return score;
        }
        
        public void addScore(int x){
            score+=x;
        }
}
