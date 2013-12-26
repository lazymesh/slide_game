
/**
 * class to get and set the images.
 */
import java.awt.Image;

////////////////////////////////////////////////////////// class Tile
// Represents the individual "tiles" that slide in puzzle.
class Tile {
    private int _row;     // row of final position
    private int _col;     // col of final position
    private Image _face;  // string to display 
    //end instance variables
    
    
    public Tile(int row, int col, Image face) {
        _row = row;
        _col = col;
        _face = face;
    }//end constructor
    
    
    public void setFace(Image newFace) {
        _face = newFace;
    }//end getFace
    
    
    public Image getFace() {
        return _face;
    }//end getFace
   
   
    public boolean isInFinalPosition(int r, int c) {
        return r==_row && c==_col;
    }//end isInFinalPosition
}//end class Tile
