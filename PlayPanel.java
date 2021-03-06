//Can Ivit, Berke Altiparmak, Yigit Kilicoglu, Naci Keskinler
//27 March 2020
/*
 * Copyright (c) 2020, Want A Punch? Studio and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Want A Punch? Studio or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

public class PlayPanel extends JPanel
{
  private Image backgroundImage;  
  private Player player;
  private Deck deck;
  private MainGamePanel gamePanel;
  private boolean done;
  
  public PlayPanel(Player ply, Deck d,  MainGamePanel pnl)
  {
    try
    {
      //Read the background image
      backgroundImage = ImageIO.read(new File("wallpapers/bank.jpg"));
    }
    catch(IOException e)
    {
    }
    
    player = ply;
    deck = d;
    gamePanel = pnl;
    done = false;
    
    setLayout(new BorderLayout());
    add(createTitleLabel(), BorderLayout.NORTH); //username at the top
    add(createControlPanel(), BorderLayout.CENTER); //control buttons at the center
  }
  
  //Displays which player is betting at that moment
  private JLabel createTitleLabel()
  {
    JLabel titleLabel = new JLabel();
    titleLabel.setText(player.getUserName() + " is playing.");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setVerticalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
    titleLabel.setForeground(Color.WHITE);
    return titleLabel;
  }
  
  //Creates the hit, stand and double panels and 
  //adds their respective listeners.
  private JPanel createControlPanel()
  {
    JPanel controlPanel = new JPanel();
    controlPanel.setOpaque(false);
    controlPanel.setLayout(new GridLayout(1, 3));
    
    class HitListener implements MouseListener
    {
      public void mouseClicked(MouseEvent e) 
      {
        //Hits a new card and adds it to the player's hand
        Card newCard = deck.draw();
        newCard.turnUp();
        player.addCardToHand(newCard);
        gamePanel.repaint();
        if(player.getHandValue() >= 21)
        {
          done = true; //If the had value is greater than 21, the player busts
        }
        
      }
      public void mousePressed(MouseEvent e)
      {
        
      }
      public void mouseReleased(MouseEvent e)
      { 
        
      }
      public void mouseEntered(MouseEvent e)
      {
        
      }    
      public void mouseExited(MouseEvent e)
      {
      }   
    }
    
    class DoubleListener implements MouseListener
    {
      public void mouseClicked(MouseEvent e) 
      {
        if(player.doubleBet()) //if player has enough money to double, double his bet and add one more card to his hand
        {
          gamePanel.repaint(); //repaint game panel to show newly added chips
          Card newCard = deck.draw(); //draw one more card from the deck
          newCard.turnUp(); 
          player.addCardToHand(newCard); //add it to player's hand
          gamePanel.repaint();
          done = true;
        }
        else
        {
          JOptionPane.showMessageDialog(gamePanel, "You can only double if you have enough money.", "Bad Move", JOptionPane.ERROR_MESSAGE);      
        }
      }
      public void mousePressed(MouseEvent e)
      {
        
      }
      public void mouseReleased(MouseEvent e)
      { 
        
      }
      public void mouseEntered(MouseEvent e)
      {
        
      }    
      public void mouseExited(MouseEvent e)
      {
      }   
    }
    
    class StandListener implements MouseListener
    {
      public void mouseClicked(MouseEvent e) 
      {
        done = true; //The player is no longer able to make any moves
      }
      public void mousePressed(MouseEvent e)
      {
        
      }
      public void mouseReleased(MouseEvent e)
      { 
        
      }
      public void mouseEntered(MouseEvent e)
      {
        
      }    
      public void mouseExited(MouseEvent e)
      {
      }   
    }
    
    JPanel standPanel = new JPanel();
    standPanel.setOpaque(false);
    standPanel.setLayout(null);
    JLabel standLabel = new JLabel();
    standLabel.setSize(240, 60);
    try
    {
      //Adds the stand button image to the label
      Image standImage = ImageIO.read(new File("buttons/stand.png"));
      Image scaledStandImage = standImage.getScaledInstance(standLabel.getWidth(), standLabel.getHeight(), Image.SCALE_SMOOTH); //resixe image accordingly
      ImageIcon scaledStandIcon = new ImageIcon(scaledStandImage);
      standLabel.setIcon(scaledStandIcon);
    }
    catch(IOException exc)
    {
    }
    standLabel.addMouseListener(new StandListener());
    standPanel.add(standLabel);
    standLabel.setLocation(50, 30);
    controlPanel.add(standPanel);
    
    JPanel doublePanel = new JPanel();
    doublePanel.setOpaque(false);
    doublePanel.setLayout(null);
    JLabel doubleLabel = new JLabel();
    doubleLabel.setSize(240, 60);
    try
    {
      //Adds the double button image to the label
      Image doubleImage = ImageIO.read(new File("buttons/double.png"));
      Image scaledDoubleImage = doubleImage.getScaledInstance(doubleLabel.getWidth(), doubleLabel.getHeight(), Image.SCALE_SMOOTH);
      ImageIcon scaledDoubleIcon = new ImageIcon(scaledDoubleImage);
      doubleLabel.setIcon(scaledDoubleIcon);
    }
    catch(IOException exc)
    {
    }
    doubleLabel.addMouseListener(new DoubleListener());
    doublePanel.add(doubleLabel);
    doubleLabel.setLocation(80, 30);
    controlPanel.add(doublePanel);
    
    JPanel hitPanel = new JPanel();
    hitPanel.setOpaque(false);
    hitPanel.setLayout(null);
    JLabel hitLabel = new JLabel();
    hitLabel.setSize(240, 60);
    try
    {
      //Adds the hit button image to the label
      Image hitImage = ImageIO.read(new File("buttons/hit.png"));
      Image scaledHitImage = hitImage.getScaledInstance(hitLabel.getWidth(), hitLabel.getHeight(), Image.SCALE_SMOOTH);
      ImageIcon scaledHitIcon = new ImageIcon(scaledHitImage);
      hitLabel.setIcon(scaledHitIcon);
    }
    catch(IOException exc)
    {
    }
    hitLabel.addMouseListener(new HitListener());
    hitPanel.add(hitLabel);
    hitLabel.setLocation(100, 30);
    controlPanel.add(hitPanel);    
    
    return controlPanel;
  }
  
  public boolean playDone() //method for checking if player's play is over
  {
    return done;
  }
  
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
  }
}