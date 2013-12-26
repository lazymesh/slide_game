
/**
 * This is the MAIN	class of slide game project.
 * simple GUI for inputs of rows and columns.
 * three buttons for different flavors of slide game.
**/
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
class Main {
	public static int row,col,cell_size;
	public static String s,choice1="",choice2="";
	
 public static void main(String[] args) {
    final JFrame window = new JFrame("Please Choose");//main frame
     window.setLayout(new GridLayout(6,1));
     
     //all the labels used in main frame.
     JLabel L1=new JLabel("THIS GAME IS DESIGNED BY",SwingConstants.CENTER);
     JLabel L2=new JLabel("Ramesh, Binod & Shanta",SwingConstants.CENTER);
     JLabel L3=new JLabel("1  Please choose the levels you want",SwingConstants.LEFT);
     JLabel L4=new JLabel("2  Please choose the modes of your choice",SwingConstants.LEFT);
     //JLabel L5=new JLabel(choice1);
     //JLabel L6=new JLabel(choice2);
     
     //all the buttons used in the main frame.
     final JButton beginner=new JButton("BEGINNER");
     final JButton inter=new JButton("INTERMEDIATE");
     final JButton advanced=new JButton("ADVANCED");
     final JButton drag_drop=new JButton("drag & drop");drag_drop.setEnabled(false);
     final JButton move=new JButton("mouse move");move.setEnabled(false);
     final JButton press=new JButton("mouse press");press.setEnabled(false);
    
     //panel for adding buttons.
     JPanel addButtons1=new JPanel();
     JPanel addButtons2=new JPanel();
     
     //six buttons with action listener to choose levels and modes.
     //setting properties and values when BEGINNER button is pressed
     beginner.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		 beginner.setEnabled(false);inter.setEnabled(false);advanced.setEnabled(false);
    		 drag_drop.setEnabled(true);move.setEnabled(true);press.setEnabled(true);
    		 row=3;col=3;cell_size=100;s=choice1="BEGINNER";
    	 }
     });
     //for INTERMEDIATE button
     inter.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		 beginner.setEnabled(false);inter.setEnabled(false);advanced.setEnabled(false);
    		 drag_drop.setEnabled(true);move.setEnabled(true);press.setEnabled(true);
    		 row=6;col=6;cell_size=55;s=choice1="INTERMEDIATE";
    	 }
     });
     //for ADVANCED button
     advanced.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		 beginner.setEnabled(false);inter.setEnabled(false);advanced.setEnabled(false);
    		 drag_drop.setEnabled(true);move.setEnabled(true);press.setEnabled(true);
    		 row=9;col=9;cell_size=35;s=choice1="ADVANCED";
    	 }
     });
     //calling GUI class when this button is pressed
     drag_drop.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		 try{
    			 new GUI(row, col, cell_size, s);
    			 beginner.setEnabled(true);inter.setEnabled(true);advanced.setEnabled(true);
        		 drag_drop.setEnabled(false);move.setEnabled(false);press.setEnabled(false);
        		 choice2="DRAG AND DROP";
    		 }
    		 catch(Exception ex){
    			 JOptionPane.showMessageDialog(null,"please consult your administrator");
    			 }
    	 }
     });
     //calling GUI2 class when this button is pressed
     move.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		 try{
    			 new GUI2(row, col, cell_size, s);
    			 beginner.setEnabled(true);inter.setEnabled(true);advanced.setEnabled(true);
        		 drag_drop.setEnabled(false);move.setEnabled(false);press.setEnabled(false);
        		 choice2="MOUSE MOVE";
    		 }
    		 catch(Exception ex){
    			 JOptionPane.showMessageDialog(null,"please consult your administrator");
    			 }
    	 }
     });
     //calling GUI3 class when this button is pressed
     press.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		 try{
    			 new GUI3(row, col, cell_size, s);
    			 beginner.setEnabled(true);inter.setEnabled(true);advanced.setEnabled(true);
        		 drag_drop.setEnabled(false);move.setEnabled(false);press.setEnabled(false);
        		 choice2="MOUSE PRESS";
    		 }
    		 catch(Exception ex){
    			 JOptionPane.showMessageDialog(null,"please consult your administrator");
    			 }
    	 }
     });
     window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     window.add(L1);window.add(L2);window.add(L3);//adding labels.
     addButtons1.add(beginner);addButtons1.add(inter);addButtons1.add(advanced);//grouping buttons for levels
     window.add(addButtons1);//adding level buttons
    // window.add(L5);//choice1
     window.add(L4);//adding label
     addButtons2.add(press);addButtons2.add(move);addButtons2.add(drag_drop);//grouping buttons
     window.add(addButtons2);//adding mode buttons
     //window.add(L6);//choice2
     window.setLocation(0,0);//location of frame on the screen.
     window.pack();  // finalize layout
     window.setVisible(true);  // make window visible
     window.setResizable(false);//resize unavailable
 }//end main
}//end class Main
