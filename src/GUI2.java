
/**
 * This class is the view part of MVC model
 * this class represent the mouse moved flavor 
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//This class contains all the parts of the GUI2 interface
class GUI2 extends JPanel {
	private static final long serialVersionUID = 1L;
	public String level,EMPTY="";
	public int win=0,ROWS,COLS;//variables
	public int CELL_SIZE; // Pixels
	//internal classes
    private GraphicsPanel puzzleGraphics;
	 private GraphicsPanel2 puzzleGraphics2;
	 //model of MVC
	 private Model puzzleModel;
	 public final JFrame f;
	 public GUI2(int row, int col, int cell_size,String s) {
		 f=new JFrame("SLIDE PUZZLE GAME");
	      ROWS=row;COLS=col;CELL_SIZE=cell_size;level=s;
		 puzzleModel = new Model(ROWS,COLS,CELL_SIZE);

	     //panel for adding internal classes
	     puzzleGraphics = new GraphicsPanel();
	     puzzleGraphics2 = new GraphicsPanel2();
	     
	     JButton score=new JButton("view score");
		 score.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 JFrame f=new JFrame("Slide Puzzle");
				 f.setLayout(new GridLayout(4,3));
				 f.add(new JLabel("LEVEL"));
				 f.add(new JLabel("NAME"));
				 f.add(new JLabel("SCORE",SwingConstants.RIGHT));
		   		 f.add(new JLabel("BEGINNER: "));
				 f.add(new JLabel(puzzleModel.record2[0]));
		   		 f.add(new JLabel(puzzleModel.record2[3],SwingConstants.RIGHT));
		   		 f.add(new JLabel("INTERMEDIATE: "));
		   		 f.add(new JLabel(puzzleModel.record2[1]));
		   		 f.add(new JLabel(puzzleModel.record2[4],SwingConstants.RIGHT));
		   		 f.add(new JLabel("ADVANCED: "));
		   		 f.add(new JLabel(puzzleModel.record2[2]));
		   		 f.add(new JLabel(puzzleModel.record2[5],SwingConstants.RIGHT));
		   		 f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		   		 f.setLocation(240,0);
		   		 f.pack();
		   		 f.setVisible(true);
			 }
		 });
	     //Create new game button.  Add a listener to it.
	     JButton newGameButton = new JButton("New Game");
	     newGameButton.addActionListener(new ActionListener(){
	    	 public void actionPerformed(ActionEvent e){
	    		 puzzleModel.reset();
	             puzzleGraphics.repaint();
	    	 }
	    });
	     //Create select image button.  Add a listener to it.
	     final JButton selectImage = new JButton("Select Image");
	     selectImage.addActionListener(new ActionListener(){
	    	 public void actionPerformed(ActionEvent e){
	    		 if(selectImage.getActionCommand()=="Select Image"){
	    			 puzzleModel.call();
	    			 puzzleModel.reset();
	    			 puzzleGraphics.repaint();
	    		 }
	    	 }
	     });
		 JLabel mode=new JLabel(level,SwingConstants.CENTER);
	     //--- Create control panel
	     JPanel controlPanel = new JPanel();
	     controlPanel.setLayout(new GridLayout(2,1));
	     controlPanel.add(mode);controlPanel.add(score);
	     controlPanel.add(newGameButton);
	     controlPanel.add(selectImage);
	     
	     //Set the layout and add the components
	     f.setLayout(new BorderLayout());
	    // this.add(mode,BorderLayout.EAST);
	     f.add(controlPanel, BorderLayout.NORTH);
	     f.add(puzzleGraphics, BorderLayout.CENTER);
	     f.add(puzzleGraphics2,BorderLayout.SOUTH);
	     f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   		 f.setLocation(350,0);
   		 f.pack();
   		 f.setVisible(true);
	 }//end constructor
	 
	 // This is defined inside the outer class so that
	 // it can use the outer class instance variables.
	 class GraphicsPanel extends JPanel implements MouseListener, MouseMotionListener{
	    private static final long serialVersionUID = 1L;
		
		public GraphicsPanel() {
	         this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
	         this.setBackground(Color.gray);
	         addMouseListener(this);
	         addMouseMotionListener(this);  // Listen own mouse events.
	     }//end constructor
	     
	     public void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         for (int r=0; r<ROWS; r++) {
	             for (int c=0; c<COLS; c++) {
	                 int x = c * CELL_SIZE;
	                 int y = r * CELL_SIZE;
	                 Image text = puzzleModel.getFace(r, c);
	                 if (text != null) {
	                     g.drawImage(text, x, y,this);
	                 }
	             }
	         }//check if the game is over
	         if(puzzleModel.isGameOver()){
	           	 g.drawImage(puzzleModel.lastTile,(COLS-1)*CELL_SIZE,(ROWS-1)*CELL_SIZE,this);
	            }
	     }//end paintComponent
	     
	     public void mouseMoved(MouseEvent e) {
	        //--- map x,y coordinates into a row and col.
	         int col = e.getX()/CELL_SIZE;
	         int row = e.getY()/CELL_SIZE;
	         if (!puzzleModel.moveTile(row, col)) {
	             // moveTile moves tile if legal, else returns false.
	             //Toolkit.getDefaultToolkit().beep();
	         }
	         this.repaint();  // Show any updates to model.
			    //check game complete and display the winning number of moves
	         if(puzzleModel.isGameOver()){
	        	 if(level=="BEGINNER"){
			    		if(Integer.parseInt(puzzleModel.record2[3])>puzzleModel.count){
			    			String out="NEW RECORD \n you finished the BEGINNER mode in "+puzzleModel.count+" moves"+
				    		"\nnext level is INTERMEDIATE level\n\nPlease enter your name";
			    			puzzleModel.record2[0]=JOptionPane.showInputDialog(null,out);
			    			puzzleModel.record2[3]=""+puzzleModel.count;
			    		}
			    		f.setVisible(false);
			    		new GUI2(6,6,55,"INTERMEDIATE");
			    	}
			    	else if(level=="INTERMEDIATE"){
			    		if(Integer.parseInt(puzzleModel.record2[4])>puzzleModel.count){
			    			String out="NEW RECORD \nyou finished the INTERMEDIATE mode in "+puzzleModel.count+
				    		" moves"+"\nnext level is ADVANCED level\n\nPlease enter your name";
			    			puzzleModel.record2[1]=JOptionPane.showInputDialog(null,out);
			    			puzzleModel.record2[4]=""+puzzleModel.count;
			    			}
			    		f.setVisible(false);
			    		new GUI2(9,9,35,"ADVANCED");
			    	}
			    	else if(level=="ADVANCED"){
			    		if(Integer.parseInt(puzzleModel.record2[5])>puzzleModel.count){
			    			String out="NEW RECORD \nyou finished the ADVANCED mode in "+puzzleModel.count+
				    		" moves"+"\n\nPlease enter your name";
			    			puzzleModel.record2[2]=JOptionPane.showInputDialog(null,out);
			    			puzzleModel.record2[5]=""+puzzleModel.count;
			    			}
			    		f.setVisible(false);
			    		JOptionPane.showMessageDialog(null,"THANK YOU FOR PLAYING","THANK YOU",JOptionPane.PLAIN_MESSAGE);
			    	}
		        }
	     }//end mouse moved
	     public void mousePressed (MouseEvent e){}
	     public void mouseClicked (MouseEvent e) {}
	     public void mouseReleased(MouseEvent e) {}
	     public void mouseEntered (MouseEvent e) {}
	     public void mouseExited  (MouseEvent e) {}
	     public void mouseDragged(MouseEvent arg0) {}
	}//end class GraphicsPanel
	
	 public class GraphicsPanel2 extends JPanel{
		private static final long serialVersionUID = 1L;
		public GraphicsPanel2() {
	         this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
	         JButton full=new JButton("FULL IMAGE");
	         full.setEnabled(false);
	         add(full);
	     }
		 public void paintComponent(Graphics g){
			 g.drawImage(puzzleModel.fullImage(),0,0,this);
		 }
		 public void update(){
			 this.repaint();
		 }
	 }
}//end class SlidePuzzleGUI2
