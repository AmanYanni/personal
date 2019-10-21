/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brick.breaker;

import java.awt.*;
import static java.awt.BorderLayout.NORTH;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Aman Yanni
 */
public class BBMain{
    
    public int Wid=1280,Hei=720, xM,yM;
    
    
    
   BBMain(int dim[],Color col[]){
        
        JFrame myFrame = new JFrame("BBMain");
        JPanel mainPanel = new JPanel(){
                protected void paintComponent(Graphics g){
                Dimension arcs = new Dimension(15,15);
                int width=getWidth();
                int height = getHeight();
                Graphics2D grph=(Graphics2D) g;
                grph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                grph.setColor(getBackground());
                grph.fillRoundRect(0,0,width-1,height-1,arcs.width,arcs.height);
                grph.setColor((col[0]==Color.BLACK||col[0]==Color.WHITE)?col[1]:col[3]);
                grph.drawRoundRect(0,0,width-1,height-1,arcs.width,arcs.height);
            }
        };
       
        JPanel titleBar=new JPanel();
        JPanel Screen=new JPanel();
        JLabel close = new JLabel("X");
        JLabel title=new JLabel("BrickBreaker");
        
        
        
        myFrame.setSize(Wid,Hei);
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(51,51,51));
        myFrame.setUndecorated(true);
        myFrame.setBackground(new Color(0,0,0,0));
        titleBar.setLayout(null);
        Screen.setLayout(new BorderLayout());
        titleBar.setBackground(new Color(51,51,51));
        myFrame.setLayout(new BorderLayout());
        int x=((Toolkit.getDefaultToolkit().getScreenSize().width/2)-(myFrame.getWidth()/2));
        int y=((Toolkit.getDefaultToolkit().getScreenSize().height/2)-(myFrame.getHeight()/2));
        myFrame.setLocation(x, y);
        myFrame.setResizable(false);
        close.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,18));
        close.setForeground(Color.WHITE);
        title.setFont(new Font("Tw Cen MT Condensed Extra Bold",Font.BOLD,24));
        title.setForeground(new Color(0,102,102));
        
        
        
        myFrame.add(mainPanel);
        mainPanel.add(titleBar);
        titleBar.setBounds(10, 10, Wid-20, 55);
        mainPanel.add(Screen);
        Screen.setBounds(10,55,Wid-20,Hei-65);
        GamePanel myPanel=new GamePanel(Screen.getWidth(),Screen.getHeight(),dim,col);
        Screen.add(myPanel);
        titleBar.add(close);
        titleBar.add(title);
        close.setBounds(Wid-45,12,15,15);
        title.setBounds(15,10,500,20);
        
        close.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                myFrame.dispose();
                MainWindow.main(null);
            }
            public void mouseEntered(MouseEvent me){
                close.setForeground(new Color(0,102,102));
            }
            public void mouseExited(MouseEvent me){
                close.setForeground(Color.WHITE);
            }
            

        });
        titleBar.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me){
                xM=me.getX();
                yM=me.getY();
            }
        });
        titleBar.addMouseMotionListener(new MouseMotionAdapter(){
            
            public void mouseDragged(MouseEvent me){
                int x=me.getXOnScreen();
                int y=me.getYOnScreen();
                myFrame.setLocation(x-xM,y-yM);
            }

        });
        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
       
        myPanel.myTime.start();
        
        myPanel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                if(!myPanel.running){
                    myFrame.dispose();
                    MainWindow.main(null);
                }
            }
        });
    }
    
}
