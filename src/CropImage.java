
/**
 * This class is mainly used for browsing pics
 * jpeg and crop the selected image
 * by getting and setting as well.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

public class CropImage extends JFrame {
	private static final long serialVersionUID = 1L;
	public String location="gun.jpg";//image should be present in the package folder
	Image image;	
	ImageIcon icon;
	
	/**
	 * this Crop function returns the cropped image of the given full image
	 */
	 Image Crop(int x, int y, int w, int h) {
		 location=gtLocation();
		 icon=new ImageIcon(location);
		 image = createImage(new FilteredImageSource(icon.getImage().
		 getSource(),new CropImageFilter(x, y, w, h)));
		 return image;
	 }
	 //this function sets the location of the image
	  public void setLocation(String s){
		  location = s;
	  }
	  //this function returns the set location of image
	  public String gtLocation(){
		  return location;
	  }
  
}