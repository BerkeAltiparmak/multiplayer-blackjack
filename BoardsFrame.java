import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class BoardsFrame extends JFrame implements MouseListener, MouseMotionListener
{
  private static final int FRAME_WIDTH = 1200;
  private static final int FRAME_HEIGHT = 800;
  
  int row = -1;
  int column = -1;
  int boardNumber = 1;
  
  int oldRow = -1;
  int oldColumn = -1;
  
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel panel3 = new JPanel();
  JPanel panel4 = new JPanel();
  JPanel panel5 = new JPanel();
  JPanel panel6 = new JPanel();
  
  String bright1 = "dark";
  String bright2 = "dark";
  String bright3 = "dark";
  String bright4 = "dark";
  String bright5 = "dark";
  String bright6 = "dark";
  
  public BoardsFrame ()
  {
    createFrame();
    addMouseListener(this); //adds a mouse listener 
    addMouseMotionListener(this); 
  }
  
  public void createFrame()
  {
    
    setTitle("Choose a boardNumber");
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
    
    setLayout(new GridLayout(2,3));  
    
    createPanels();
    
    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        createPanels();
      }
    });
  }
  
  public void createPanels() 
  {
        getContentPane().removeAll();
        repaint();
        
    System.out.println("Brightness of the first panel should be " + bright1);
    JLabel label1 = new JLabel();
    label1.setIcon(new ImageIcon(new ImageIcon("boards/"+bright1+"1.png").getImage().getScaledInstance(getWidth()/3, getHeight()/2, Image.SCALE_DEFAULT)));
    panel1.add(label1);
    add(panel1);
    
    JLabel label2 = new JLabel();
    label2.setIcon(new ImageIcon(new ImageIcon("boards/"+bright2+"2.png").getImage().getScaledInstance(getWidth()/3, getHeight()/2, Image.SCALE_DEFAULT)));
    panel2.add(label2);
    add(panel2);
    
    JLabel label3 = new JLabel();
    label3.setIcon(new ImageIcon(new ImageIcon("boards/"+bright3+"3.png").getImage().getScaledInstance(getWidth()/3, getHeight()/2, Image.SCALE_DEFAULT)));
    panel3.add(label3);
    add(panel3);
    
    JLabel label4 = new JLabel();
    label4.setIcon(new ImageIcon(new ImageIcon("boards/"+bright4+"4.png").getImage().getScaledInstance(getWidth()/3, getHeight()/2, Image.SCALE_DEFAULT)));
    panel4.add(label4);
    add(panel4);
    
    JLabel label5 = new JLabel();
    label5.setIcon(new ImageIcon(new ImageIcon("boards/"+bright5+"5.png").getImage().getScaledInstance(getWidth()/3, getHeight()/2, Image.SCALE_DEFAULT)));
    panel5.add(label5);
    add(panel5);
    
    JLabel label6 = new JLabel();
    label6.setIcon(new ImageIcon(new ImageIcon("boards/"+bright6+"6.png").getImage().getScaledInstance(getWidth()/3, getHeight()/2, Image.SCALE_DEFAULT)));
    panel6.add(label6);
    add(panel6);
  }
  
  public void mouseClicked(MouseEvent e) {}  
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e)
  {
    
    //this gets where the user clicked in the x direction, and assigns it a number from 0 to 2.
    if (e.getX() < getWidth()/3)
    {
      column = 0;
    }
    else if (e.getX() < getWidth()*2/3)
    {
      column = 1;
    }
    else if (e.getX() < getWidth())
    {
      column = 2;
    }
    
    //this gets where the user clicked in the y direction, and assigns it a number from 0 to 1.
    if (e.getY() < getHeight()/2)
    {
      row = 0;
    }
    else if (e.getY() < getHeight())
    {
      row = 1;
    }
    
    boardNumber = row*3 + column + 1; //finds which boardNumber the user clicked on
    
    System.out.println(boardNumber);
    
    setVisible(false);
    MainGameFrame mgf = new MainGameFrame();
    mgf.setVisible(true);
    
    
    
  }
  
  public void mouseDragged(MouseEvent e) {} 
  
  public void mouseMoved(MouseEvent e) 
  { 
    //this gets where the user hovered over in the x direction, and assigns it a number from 0 to 2.
    if (e.getX() < getWidth()/3)
    {
      column = 0;
    }
    else if (e.getX() < getWidth()*2/3)
    {
      column = 1;
    }
    else if (e.getX() < getWidth())
    {
      column = 2;
    }
    
    //this gets where the user hovered over in the y direction, and assigns it a number from 0 to 1.
    if (e.getY() < getHeight()/2)
    {
      row = 0;
    }
    else if (e.getY() < getHeight())
    {
      row = 1;
    }
    
    if(row != oldRow || column != oldColumn)
    {
      System.out.println(row + column);
      if(boardNumber == 1) {bright1 = "dark";}
      if(boardNumber == 2) {bright2 = "dark";}
      if(boardNumber == 3) {bright3 = "dark";}
      if(boardNumber == 4) {bright4 = "dark";}
      if(boardNumber == 5) {bright5 = "dark";}
      if(boardNumber == 6) {bright6 = "dark";}
      
      oldRow = row;
      oldColumn = column;
      
      boardNumber = row*3 + column + 1; //finds which boardNumber the user hovered over
      
      
      if(boardNumber == 1) {bright1 = "light";}
      if(boardNumber == 2) {bright2 = "light";}
      if(boardNumber == 3) {bright3 = "light";}
      if(boardNumber == 4) {bright4 = "light";}
      if(boardNumber == 5) {bright5 = "light";}
      if(boardNumber == 6) {bright6 = "light";}
      
      createPanels();
    }
    
  } 
  
  
}