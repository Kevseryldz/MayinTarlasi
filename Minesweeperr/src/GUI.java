import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
	
	public boolean resetter=false;
	
	Date startDate = new Date();
	Date endDate;

	int spacing=5;
	
	int neighs=0;
	
	String vicMes="HENÜZ DEÐÝL";
	
	public int mx=-100;
	public int my=-100;	
	
	public int smileyX=605;
	public int smileyY=5;
	
	public int smileyCenterX=smileyX+35;
	public int smileyCenterY=smileyY+35;
	
	public int timeX=1130;
	public int timeY=5;
	
	public int vicMesX=800;
	public int vicMesY=-50;
	
	
	public int sec=0;
	
	public boolean happiness=true;
	
	public boolean victory=false;
	
	public boolean defeat=false;
	
	Random rand =new Random();
	
	int[][] mines =new int [16][9];
	int[][] neighbours = new int [16][9];
	boolean [][] revealed =new boolean [16][9];
	boolean [][] flagged=new boolean [16][9];
	
	
	
	public GUI() {
		this.setTitle("MAYIN TARLASI");
		this.setSize(1286,829);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if (rand.nextInt(100)<20) {
					mines [i][j]=1;
				}else {
					mines[i][j]=0;
				}
				revealed[i][j]=false;
		
			}
				
		}
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				neighs=0;
				for(int m=0;m<16;m++) {
					for(int n=0;n<9;n++) {
						if(!(m == i && n == j)) {
							if(isN(i,j,m,n) == true)
							neighs++;
						}
					}	
				}
				neighbours[i][j]=neighs;
			}	
		}
		
		Board board = new Board();
		this.setContentPane(board);
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click=new Click();
		this.addMouseListener(click);
		
	}
	
	public class Board extends JPanel{
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 1280, 800);
			for(int i=0;i<16;i++) {
				for(int j=0; j<9; j++) { 
					g.setColor(Color.gray);
					if (revealed[i][j] == true) {
						g.setColor(Color.white);
						if(mines[i][j]==1) {
						 g.setColor(Color.red);	
						}
					}
					if (mx >= spacing+i*80 && mx <i*80+80-spacing && my >= spacing+j*80+106 && my < j*80+186-spacing ) {
						g.setColor(Color.LIGHT_GRAY );
					}
					g.fillRect(spacing+i*80,spacing+j*80+80, 80-2*spacing,80-2*spacing);
					if (revealed[i][j] == true) {
						g.setColor(Color.black);
						if(mines[i][j]== 0 && neighbours[i][j] != 0){
							if(neighbours[i][j]==1) {
								g.setColor(Color.blue);
							}else if(neighbours[i][j]==2) {
								g.setColor(Color.green);
								
							}else if(neighbours[i][j]==3) {
								g.setColor(Color.red);
								
							}else if(neighbours[i][j]==4) {
								g.setColor(new Color(0,0,128));
								
							}else if(neighbours[i][j]==5) {
								g.setColor(new Color(178,34,34));
								
							}else if(neighbours[i][j]==6) {
								g.setColor(new Color(72,209,204));
								
							}else if(neighbours[i][j]==8) {
								g.setColor(Color.DARK_GRAY);
								
							}
							
						 g.setFont(new Font("Tahoma",Font.BOLD,40));
						 g.drawString(Integer.toString(neighbours[i][j]), i*80+27, j*80+80+50);
						}else if(mines[i][j]==1){
							g.fillRect(i*80+10+20,j*80+80+20, 20, 40);
							g.fillRect(i*80+20,j*80+80+10+20, 40, 20);
							g.fillRect(i*80+5+20,j*80+80+5+20, 30, 30);
							g.fillRect(i*80+38,j*80+80+15,4,50);
							g.fillRect(i*80+15,j*80+80+38,50,4);
						}
				    } 
				}	
			}
			//smiley painting
			
			g.setColor(Color.yellow);
			g.fillOval(smileyX,smileyY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(smileyX+15,smileyY+20, 10, 10);
			g.fillOval(smileyX+45,smileyY+20, 10, 10);
			if(happiness==true) {
				g.fillRect(smileyX+20, smileyY+50, 30, 5);
				g.fillRect(smileyX+17, smileyY+45, 5, 5);
				g.fillRect(smileyX+48, smileyY+45, 5, 5);
			}else {
				g.fillRect(smileyX+20, smileyY+45, 30, 5);
				g.fillRect(smileyX+17, smileyY+50, 5, 5);
				g.fillRect(smileyX+48, smileyY+50, 5, 5);
             }
			//time counter painting
			
			g.setColor(Color.black);
			g.fillRect(timeX, timeY, 140, 70);
			if(defeat==false && victory==false) {
				sec=(int)((new Date().getTime()-startDate.getTime())/1000);
			}
			if(sec>999) {
			   sec=999;
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("Tahoma",Font.PLAIN,80));
			if (sec<10) {
			 g.drawString("00"+Integer.toString(sec), timeX, timeY+65);
			 }
			else if (sec<100) {
				g.drawString("0"+Integer.toString(sec), timeX, timeY+65);
				
			}else {
				g.drawString(Integer.toString(sec), timeX, timeY+65);
			}
			
			//VÝCTORY MESSAGE
			 
			if(victory==true) {
				g.setColor(Color.green);
				vicMes= "KAZANDIN!";
				
			}else if(defeat==true) {
				g.setColor(Color.red);
				vicMes="KAYBETTÝN!!!!";
			}	
			if (victory == true || defeat == true) {
				vicMesY= -50 + (int) (new Date().getTime() - endDate.getTime())/10;
				g.drawString(vicMes, vicMesX, vicMesY);
			}
	  }
	}
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			mx=e.getX();
       		my=e.getY();
       		
			/*
			System.out.println("Fare Hareket Etti!");
		
       		System.out.println("X: " +  mx  + ",Y: " +  my);
			*/
		}
		
	}
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			
			mx=e.getX();
       		my=e.getY();
       		
       		
			if (inBoxX() != -1 && inBoxY() != -1) {
				revealed[inBoxX()][inBoxY()]=true;
			
			}
			
			if (inBoxX() != -1 && inBoxY() != -1) {
			System.out.println("Fare [" + inBoxX() + "," + inBoxY() + "] üzerinde!,Komþu mayýnýn numaralarý:"+neighbours[inBoxX()][inBoxY()]);
			}else {
				System.out.println("The pointer is not inside of any box!");
         }
			 
			if(inSmiley()==true) {
				resetAll();
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void checkVictoryStatus() {
		
		if(defeat==false) {
			for(int i=0;i<16;i++) {
				for(int j=0;j<9;j++) {
					if (revealed[i][j]==true && mines [i][j]==1) {
				
					defeat=true;
					happiness=false; 
					endDate=new Date();
			
					
					 }
			       }
					
				}
		
			}
		if(totalBoxesRevealed() >= 144- totalMines() && victory==false) {
			victory=true;
			endDate=new Date();
		}
		
	}
	public int totalMines() {
		int total=0;
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if (mines [i][j]==1) {
					total++;
					
				}
		
			}
				
		}
		
		return total;
	}
	
	public int totalBoxesRevealed() {
		int total=0;
		for(int i=0;i<16;i++) {
		 for(int j=0;j<9;j++) {
			if (revealed [i][j]==true) {
				total++;
				
			}
				
		 }
	
		}
		return total;
	}
	public void resetAll() {
		resetter=true;
		startDate=new Date();
		vicMesY= -50;
		vicMes="HENÜZ DEÐÝL"; 
		
		
		happiness=true;
		victory=false;
		defeat=false;
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if (rand.nextInt(100)<20) {
					mines [i][j]=1;
				}else {
					mines[i][j]=0;
				}
				revealed[i][j]=false;
				flagged[i][j]=false;
		
			}
				
		}
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				neighs=0;
				for(int m=0;m<16;m++) {
					for(int n=0;n<9;n++) {
						if(!(m == i && n == j)) {
							if(isN(i,j,m,n) == true)
							neighs++;
						}
					}	
				}
				neighbours[i][j]=neighs;
			}	
		}
		resetter=false;
	}
	public boolean inSmiley() {
		int dif = (int)Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
		if (dif<35){
		 return true;
		 }
		return false;
	}
	public int inBoxX() {
		for(int i=0;i<16;i++) {
			for(int j=0; j<9; j++) { 
				if (mx >= spacing+i*80 && mx <spacing+i*80+80-spacing && my >= spacing+j*80+106 && my < spacing+j*80+186-spacing ) {
					return i;
				}	
			}	
		}
		return -1 ;
		
	}
	
	public int inBoxY() {
		for(int i=0;i<16;i++) {
			for(int j=0; j<9; j++) { 
				if (mx >= spacing+i*80 && mx <spacing+i*80+80-spacing && my >= spacing+j*80+106 && my < spacing+j*80+186-spacing ) {
					return j;
			    }
			}
	     }
		return - 1;
	}
	public boolean isN(int mX,int mY,int cX,int cY) {
		if (mX - cX <2 && mX - cX > -2 && mY - cY <2 && mY - cY > -2 && mines[cX][cY]==1 ) {
			return true;
		}
		return false;
	}
}
	
	
	



