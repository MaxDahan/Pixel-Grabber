import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PG {

	private PixelGrabber pix;
	private JFrame frame = new JFrame("Pixel_Grabber");
	private JLabel pic;
	private String[][] pixs;
	private int[][] boundary; //0 is nothing 1 is boundary
	
	//START
	public static void main(String[] args) {
		new PG();
	}
	public PG() {
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setLocation(0, 0);
		frame.setSize(155, 155);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
		File file = new File("data\\check_mark.png");
		String path = file.getAbsolutePath();
		file = new File(path);
	    Image image = null;
	    try {image = ImageIO.read(file);} catch (IOException e1) {e1.printStackTrace();}
	    pic = new JLabel(new ImageIcon(image));
	    frame.getContentPane().add(pic);
	    frame.pack();
	    
	    boundary = new int[frame.getWidth()][frame.getHeight()];
	    
		pixs = new String[frame.getWidth()][frame.getHeight()];
	    
		handlepixels(image, 0, 0, frame.getWidth(), frame.getHeight());
	}
	
	 //DETERMINING BOUNDARY OF PICTURE
	 public void boundary() {
		 for(int i = 0; i < pixs.length; i++) {
			 for(int j = 0; j < pixs[i].length; j++) {
				 if(j > 0) {
					 if(pixs[i][j].equals("COLOR") && pixs[i][j - 1].equals("ALPHA")) {
						 boundary[i][j] = 1;
					 } else {
						 boundary[i][j] = 0;
					 }
				 }
				 if(j < pixs[i].length - 1) {
					 if(pixs[i][j].equals("COLOR") && pixs[i][j + 1].equals("ALPHA")) {
						 boundary[i][j] = 1;
					 }
				 }
				 if(i > 0) {
					 if(pixs[i][j].equals("COLOR") && pixs[i - 1][j].equals("ALPHA")) {
						 boundary[i][j] = 1;
					 }
				 }
				 if(i < pixs[j].length - 1) {
					 if(pixs[i][j].equals("COLOR") && pixs[i + 1][j].equals("ALPHA")) {
						 boundary[i][j] = 1;
					 }
				 }
			 }
		 }
		 printBound();
	 }
	 
	 //CONSOLE VISUALIZATION OF PIXELS
 	 public void printBound() {
		 for(int i = 0; i < boundary.length - 1; i++) {
			 for(int j = 0; j < boundary[i].length - 1; j++) {
				 System.out.print(boundary[i][j] + " ");
			 }
			 System.out.println();
		 }
	 }
	 
	 //PIXEL HANDLING
	 public void handlepixels(Image img, int x, int y, int w, int h) {
	      int[] pixels = new int[w * h];
	      PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
	      try {
			pg.grabPixels();
	      } catch (InterruptedException e1) {
			e1.printStackTrace();
	      }
	      for (int j = 0; j < h; j++) {
	          for (int i = 0; i < w; i++) {
	              handlesinglepixel(x+j, y+i, pixels[j * w + i]);
	          }
	      }
	      boundary();
	 }
	 public void handlesinglepixel(int x, int y, int pixel) {
	     if(pixel == 0 || pixel == -1) {pixs[x][y] = "ALPHA";}
	     else {pixs[x][y] = "COLOR";}      
	}
}