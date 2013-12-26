

/**
 * This is the model class connecting control with view.
 */
import java.awt.Image;
import java.awt.datatransfer.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class Model implements Transferable {
	CropImage crop=new CropImage();
	public int count;
	 private int ROWS;
	 private int COLS;
     private int CELL_SIZE; // Pixels
     public static String[] record1={"Anonymous","Anonymous","Anonymous","999","999","999"};
     public static String[] record2={"Anonymous","Anonymous","Anonymous","999","999","999"};
     public static String[] record3={"Anonymous","Anonymous","Anonymous","999","999","999"};
    // public static String s;
	 private Tile[][] _contents;  // All tiles.
	 private Tile  _emptyTile; // The empty space.
	 Image lastTile; //last tile.
	 private boolean over=true;
	 
	 public Model(int rows,int cols, int cell_size) {
		 ROWS=rows;COLS=cols;CELL_SIZE=cell_size;
	     _contents = new Tile[ROWS][COLS];
	     reset();               // Initialize and shuffle tiles.
	 }//end constructor
	 
	 // Return the image to display at given row, col.
	 Image getFace(int row, int col) {
	     return _contents[row][col].getFace();
	 }//end getFace
	 
	 //choose a file(image(jpg))
	 public void call(){
		 try{
			 JFileChooser choose = new JFileChooser();
			 choose.showOpenDialog(null);
			 crop.setLocation(choose.getSelectedFile().toString());
		 }
		 catch(Exception e){
			 JOptionPane.showMessageDialog(null,"WRONG IMAGE FORMAT");
		 }
	 }
	 //get full image.
	 public Image fullImage(){
		 return crop.Crop(0, 0, COLS*CELL_SIZE, ROWS*CELL_SIZE);
	 }
	 // Initialize and shuffle the tiles.
	 public void reset() {
	     for (int r=0; r<ROWS; r++) {
	         for (int c=0; c<COLS; c++) {
	             _contents[r][c] = new Tile(r, c, crop.Crop(c*CELL_SIZE,r*CELL_SIZE,CELL_SIZE,CELL_SIZE));
	         }
	     }
	     //Set last tile face to null to mark empty space
	     _emptyTile = _contents[ROWS-1][COLS-1];
	     lastTile =crop.Crop((COLS-1)*CELL_SIZE,(ROWS-1)*CELL_SIZE, CELL_SIZE, CELL_SIZE);
	     _emptyTile.setFace(null);
	     
	     //Shuffle - Exchange each tile with random tile.
	     for (int r=0; r<ROWS; r++) {
	         for (int c=0; c<COLS; c++) {
	             exchangeTiles(r, c, (int)(Math.random()*ROWS)
	                               , (int)(Math.random()*COLS));
	         }
	     }
	     count=0;
	 }//end reset
	 
	 
	 // Move a tile to empty position beside it, if possible.
	 // Return true if it was moved, false if not legal.
	 public boolean moveTile(int r, int c) {
	     //It's a legal move if the empty cell is next to it.
	     return checkEmpty(r, c, -1, 0) || checkEmpty(r, c, 1, 0)
	         || checkEmpty(r, c, 0, -1) || checkEmpty(r, c, 0, 1);
	 }//end moveTile
	 
	 
	 // Check to see if there is an empty position beside tile.
	 // Return true and exchange if possible, else return false.
	 private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {
	     int rNeighbor = r + rdelta;
	     int cNeighbor = c + cdelta;
	     // Check to see if this neighbor is on board and is empty.
	     if (isLegalRowCol(rNeighbor, cNeighbor) 
	               && _contents[rNeighbor][cNeighbor] == _emptyTile) {
	         exchangeTiles(r, c, rNeighbor, cNeighbor);
	         return true;
	     }
	     return false;
	 }//end checkEmpty
	 
	 
	 // Check for legal row, col
	 public boolean isLegalRowCol(int r, int c) {
	     return r>=0 && r<ROWS && c>=0 && c<COLS;
	 }//end isLegalRowCol
	 
	 
	 // Exchange two tiles.
	 public void exchangeTiles(int r1, int c1, int r2, int c2) {
	     Tile temp = _contents[r1][c1];
	     _contents[r1][c1] = _contents[r2][c2];
	     _contents[r2][c2] = temp;
	     count++;
	 }//end exchangeTiles
	     
	 //function to check if the game is over.
	public boolean isGameOver() {
	     for (int r=0; r<ROWS; r++) {
	         for (int c=0; c<COLS; c++) {
	             Tile trc = _contents[r][c];
	             if(!trc.isInFinalPosition(r, c)){
	            	  over=false;
	            	  r=ROWS;
	            	  break;
	             }
	             else
	            	 over=true;
	         }
	     }
	     if(over==false){
	    	 return over;}
	     else {
	    	 return over;}
	     
    // return false;
	 }//end isGameOver

	// This is the custom DataFlavor for Scribble objects
	  public static DataFlavor modelDataFlavor = new DataFlavor(
	      Model.class, "model");

	  // This is a list of the flavors we know how to work with
	  public static DataFlavor[] supportedFlavors = { modelDataFlavor,
	      DataFlavor.imageFlavor };
	  
	  /** Return the data formats or "flavors" we know how to transfer */
	  public DataFlavor[] getTransferDataFlavors() {
	    return (DataFlavor[]) supportedFlavors.clone();
	  }

	/** Check whether we support a given flavor */
	  public boolean isDataFlavorSupported(DataFlavor flavor) {
	    return (flavor.equals(DataFlavor.imageFlavor));
	  }

	  /**
	   * Return the scribble data in the requested format, or throw an exception
	   * if we don't support the requested format
	   */
	  public Object getTransferData(DataFlavor flavor)
	      throws UnsupportedFlavorException {
	    if (flavor.equals(DataFlavor.imageFlavor)) {
	      return this;
	    } else
	      throw new UnsupportedFlavorException(flavor);
	  }

}//end class SlidePuzzleModel
