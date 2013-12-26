
/**
 * This class is the view part of MVC model
 * this class represent the drag and drop flavor 
 */
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.*;

//This class contains all the parts of the GUI interface
class GUI extends JFrame {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String level,EMPTY="";
	public int ROWS,COLS;//variables
	public int CELL_SIZE; // Pixels
	//internal classes
    private GraphicsPanel puzzleGraphics;
	 private GraphicsPanel2 puzzleGraphics2;
	 //model of MVC
	 private Model puzzleModel;
	 public final JFrame f;
	 public GUI(int row, int col, int cell_size,String s) {
		 f=new JFrame("SLIDE PUZZLE GAME");
	      ROWS=row;COLS=col;CELL_SIZE=cell_size;level=s;
		 puzzleModel=new Model(ROWS,COLS,CELL_SIZE);
		 
		 JLabel mode=new JLabel(level,SwingConstants.CENTER);
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
				 f.add(new JLabel(puzzleModel.record1[0]));
		   		 f.add(new JLabel(puzzleModel.record1[3],SwingConstants.RIGHT));
		   		 f.add(new JLabel("INTERMEDIATE: "));
		   		 f.add(new JLabel(puzzleModel.record1[1]));
		   		 f.add(new JLabel(puzzleModel.record1[4],SwingConstants.RIGHT));
		   		 f.add(new JLabel("ADVANCED: "));
		   		 f.add(new JLabel(puzzleModel.record1[2]));
		   		 f.add(new JLabel(puzzleModel.record1[5],SwingConstants.RIGHT));
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
	
	     // panel for adding buttons
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
	 class GraphicsPanel extends JPanel implements DragSourceListener, DragGestureListener, DropTargetListener{
	     /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		DragSource drag;
		private int r1,r2,c1,c2;
		
	     public GraphicsPanel() {
	         this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
	         this.setBackground(Color.gray);
	         drag=DragSource.getDefaultDragSource();
	         drag.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
	         DropTarget dropTarget = new DropTarget(this, this);  //( component to monitor, listener to notify)
	         this.setDropTarget(dropTarget); // Tell the component about it.
	     }//end constructor
	     
	     //draw the saved tiles
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
	         }//checking whether the game is complete. if yes complete the pic.
	         if(puzzleModel.isGameOver()){
	           	 g.drawImage(puzzleModel.lastTile,(COLS-1)*CELL_SIZE,(ROWS-1)*CELL_SIZE,this);
	            }
	         
	     }//end paintComponent
	     
	    public void mouseDragged(MouseEvent arg0) {}
		/**
		   * This method implements the DragGestureListener interface. It will be
		   * invoked when the DragGestureRecognizer thinks that the user has initiated
		   * a drag.
		   * **/
		  public void dragGestureRecognized(DragGestureEvent d) {
			  MouseEvent input=(MouseEvent)d.getTriggerEvent();
			  int col=input.getX()/CELL_SIZE;
			  int row=input.getY()/CELL_SIZE;
			  r1=row;c1=col;
			  if(puzzleModel.isLegalRowCol(row, col)){
				  Cursor cursor=DragSource.DefaultMoveDrop;
				  //Point spot=new Point(row,col);
				  try{
				  d.startDrag(cursor,(Transferable)puzzleModel.getTransferData(DataFlavor.imageFlavor));
				  }
				  catch(Exception e){System.out.println("Exception : "+e);}
				  return;
			  }
		  }
	
		public void dragDropEnd(DragSourceDropEvent d) {
			if(!d.getDropSuccess())
				return;
			}
		public void dragExit(DragSourceEvent arg0) {}
		public void dragOver(DragSourceDragEvent arg0) {}
		public void dropActionChanged(DragSourceDragEvent arg0) {}
		public void dragEnter(DragSourceDragEvent arg0) {}
	
		public void dragEnter(DropTargetDragEvent d) {
			if (d.isDataFlavorSupported(DataFlavor.imageFlavor)) {
			      d.acceptDrag(DnDConstants.ACTION_MOVE);
			    }		
		}
		public void drop(DropTargetDropEvent d){
			if (d.isDataFlavorSupported(DataFlavor.imageFlavor)) {
			      d.acceptDrop(DnDConstants.ACTION_MOVE);
			}
			else{
				d.rejectDrop();
				return;
			}
			Point p = d.getLocation(); // Where did the drop happen?
			int col =(int) p.getX()/CELL_SIZE;
			int row =(int) p.getY()/CELL_SIZE;
			r2=row;c2=col;
		    if (puzzleModel.isLegalRowCol(row, col)) {
	            // moveTile moves tile if legal, else returns false.
		    	puzzleModel.exchangeTiles(r1, c1, r2, c2);
		    	d.dropComplete(true);
	            
	        }
		    else{
		    	Toolkit.getDefaultToolkit().beep();
	            d.dropComplete(false);
		    }
		    this.repaint();		
		    //check game complete and display the winning number of moves
		    if(puzzleModel.isGameOver()){
		    	if(level=="BEGINNER"){
		    		if(Integer.parseInt(puzzleModel.record1[3])>puzzleModel.count){
		    			String out="NEW RECORD \n you finished the BEGINNER mode in "+puzzleModel.count+" moves"+
			    		"\nnext level is INTERMEDIATE level\n\nPlease enter your name";
		    			puzzleModel.record1[0]=JOptionPane.showInputDialog(null,out);
		    			puzzleModel.record1[3]=""+puzzleModel.count;
		    		}
		    		f.setVisible(false);
		    		new GUI(6,6,55,"INTERMEDIATE");
		    	}
		    	else if(level=="INTERMEDIATE"){
		    		if(Integer.parseInt(puzzleModel.record1[4])>puzzleModel.count){
		    			String out="NEW RECORD \nyou finished the INTERMEDIATE mode in "+puzzleModel.count+
			    		" moves"+"\nnext level is ADVANCED level\n\nPlease enter your name";
		    			puzzleModel.record1[1]=JOptionPane.showInputDialog(null,out);
		    			puzzleModel.record1[4]=""+puzzleModel.count;
		    			}
		    		f.setVisible(false);
		    		new GUI(9,9,35,"ADVANCED");
		    	}
		    	else if(level=="ADVANCED"){
		    		if(Integer.parseInt(puzzleModel.record1[5])>puzzleModel.count){
		    			String out="NEW RECORD \nyou finished the ADVANCED mode in "+puzzleModel.count+
			    		" moves"+"\n\nPlease enter your name";
		    			puzzleModel.record1[2]=JOptionPane.showInputDialog(null,out);
		    			puzzleModel.record1[5]=""+puzzleModel.count;
		    			}
		    		f.setVisible(false);
		    		JOptionPane.showMessageDialog(null,"THANK YOU FOR PLAYING","THANK YOU",JOptionPane.PLAIN_MESSAGE);
		    	}
	        }
		}
		public void dragExit(DropTargetEvent arg0) {}
		public void dragOver(DropTargetDragEvent arg0) {}
		public void dropActionChanged(DropTargetDragEvent arg0) {}
	 }//end class GraphicsPanel
	 //class for full image
	 public class GraphicsPanel2 extends JPanel{
	 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			     
	     public GraphicsPanel2() {
	         this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
	         JButton button=new JButton("FULL IMAGE");
	         button.setEnabled(false);
	         add(button);
	     }
		 public void paintComponent(Graphics g){
			 g.drawImage(puzzleModel.fullImage(),0,0,this);
		 }
		 public void update(){
			 this.repaint();
		 }
	 }
}//end class SlidePuzzleGUI

