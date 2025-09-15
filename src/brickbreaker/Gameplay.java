package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	private boolean oyun=false;
	private int skor=0;
	private int toplam=21;
	private Timer sure;
	private int delay=8;
	private int playerX=300;
	private int topX=340, topY=340;
	private int topXYon=-2, topYYon=-3;
	private Map harita;
	
	public Gameplay() {
		harita=new Map(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		sure=new Timer(delay,this);
		sure.start();
	}
	
	public void paint(Graphics g) {
		//arkaplanı ekle
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		//tuğlaları yerleştir
		harita.draw((Graphics2D) g);
		//duvarları çiz
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(683, 0, 3, 592);
		
		//skor
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+skor, 590, 30);
		
		//kontrol çubuğu
		g.setColor(Color.yellow);
		g.fillRect(playerX, 550, 100, 8);
		
		//top
		g.setColor(Color.green);
		g.fillOval(topX, topY, 20, 20);
		
		//oyun başlangıcı
		if(!oyun && toplam==21) {
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Space to Start!", 210, 300);
		}
		
		//top aşağı düşerse
		if(topY>550) {
			oyun=false;
			topXYon=0;
			topYYon=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over! Skor: "+skor, 190, 300);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Enter to Restart!", 190, 340);
		}
		
		//tüm bloklar kırılınca
		if(toplam==0) {
			oyun=false;
			topXYon=-1;
			topYYon=-2;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over! Skor: "+skor, 190, 300);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Enter to Restart!", 190, 340);
		}
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		sure.start();
		if(oyun) {
			//topla çubuk çarpışınca
			if(new Rectangle(topX,topY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				topYYon=-topYYon;
			}
			A:
			for(int i=0; i<harita.map.length; i++) {
				for(int j=0; j<harita.map[0].length; j++) {
					if(harita.map[i][j]>0) {
						int brickX=j*harita.en+80;
						int brickY=i*harita.boy+50;
						int brickEn=harita.en;
						int brickBoy=harita.boy;
						
						Rectangle rect=new Rectangle(brickX, brickY, brickEn, brickBoy);
						Rectangle top=new Rectangle(topX, topY, 20, 20);
						Rectangle tugla=rect;
						//topla tuğla çarpışınca
						if(top.intersects(tugla)) {
							harita.setBrickValue(0, i, j);
							toplam--;
							skor+=5;
							if(topX+19<=tugla.x || topX+1>=tugla.x+brickEn) {
								topXYon=-topXYon;
							}else {
								topYYon=-topYYon;
							}
							break A;
						}
					}
				}
			}
			topX+=topXYon;
			topY+=topYYon;
			if(topX<3) {
				topXYon=-topXYon;
			}
			if(topY<3) {
				topYYon=-topYYon;
			}
			if(topX>663) {
				topXYon=-topXYon;
			}
		}
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		if(ke.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=576) {
				playerX=576;
			}else {
				saga();
			}
		}
		if(ke.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<=10) {
				playerX=10;
			}else {
				sola();
			}
		}
		if(ke.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!oyun) {
				topX=340;
				topY=340;
				topXYon=-2;
				topYYon=-3;
				skor=0;
				playerX=300;
				toplam=21;
				harita=new Map(3,7);
				oyun=true;
				repaint();
			}
		}
		if(ke.getKeyCode()==KeyEvent.VK_SPACE) {
			if(!oyun) {
				oyun=true;
				repaint();
			}
		}
	}
	
	public void saga() {
		if(oyun) {
			playerX+=20;
		}
	}
	
	public void sola() {
		if(oyun) {
			playerX-=20;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
