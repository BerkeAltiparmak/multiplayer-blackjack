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

public class BankPanel extends JPanel
{
  private Image backgroundImage;
  
  private Player player;
  private boolean deal;
  private int bank;
  private int bet;
  private MainGamePanel gamePanel;
  
  private Stack<Integer> chipStack;
  
  public BankPanel(Player ply, MainGamePanel pnl)
  {
    try
    {
      //Puts the background image
      backgroundImage = ImageIO.read(new File("wallpapers/bank.jpg"));
    }
    catch(IOException e)
    {
    }
    
    player = ply;
    deal = false;
    bank = player.getMoney();
    bet = 0;
    gamePanel = pnl;
    
    chipStack = new Stack<Integer>();
    
    setLayout(new BorderLayout());
    add(createTitleLabel(), BorderLayout.NORTH);    
    add(createBetPanel(), BorderLayout.CENTER);
  }
  
  //Displays the name of the player who is currently betting
  private JLabel createTitleLabel()
  {
    JLabel titleLabel = new JLabel();
    titleLabel.setText(player.getUserName() + " is betting.");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setVerticalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
    titleLabel.setForeground(Color.WHITE);
    return titleLabel;
  }
  
  private JPanel createBetPanel()
  {
    JPanel betPanel = new JPanel();
    betPanel.setOpaque(false);
    betPanel.setLayout(new GridLayout(1, 3));
    
    JPanel chipGridPanel = new JPanel();
    chipGridPanel.setOpaque(false);
    chipGridPanel.setLayout(new GridLayout(3, 3));    
     
    HashMap<JLabel, Integer> chipMap = new HashMap<JLabel, Integer>();
    int[] chipValues = new int[]{1, 5, 10, 20, 50, 100, 1000, 10000, 100000};
    
    JLabel lastAddedChipLabel = new JLabel(); //Displays the last added chip
    
    
    //Shows the amount of money left in the player's bank
    JLabel bankLabel = new JLabel("Bank: " + bank);
    bankLabel.setHorizontalAlignment(SwingConstants.CENTER);
    bankLabel.setVerticalAlignment(SwingConstants.CENTER);
    bankLabel.setFont(new Font("Serif", Font.BOLD, 30));
    bankLabel.setForeground(Color.WHITE);
    
    //Shows the amount of money currently betted
    JLabel betLabel = new JLabel("Bet: " + bet);
    betLabel.setHorizontalAlignment(SwingConstants.CENTER);
    betLabel.setVerticalAlignment(SwingConstants.CENTER);
    betLabel.setFont(new Font("Serif", Font.BOLD, 30));
    betLabel.setForeground(Color.WHITE);
    
    class ChipRemoveListener implements MouseListener
    {
      public void mouseClicked(MouseEvent e) 
      {
        try
        {
          if(!chipStack.empty()) //If there is a chip on the stack, remove it
          {
            int lastChipValue = chipStack.pop();
            bet = bet - lastChipValue;
            bank = bank + lastChipValue;
            
            bankLabel.setText("Bank: " + bank);
            betLabel.setText("Bet: " + bet);
            
            if(!chipStack.empty())
            {
              Image chipImage = ImageIO.read(new File("chips/" + chipStack.peek() + ".png"));
              Image scaledChipImage = chipImage.getScaledInstance(lastAddedChipLabel.getWidth(), lastAddedChipLabel.getHeight(), Image.SCALE_SMOOTH);
              ImageIcon scaledChipIcon = new ImageIcon(scaledChipImage);
              lastAddedChipLabel.setIcon(scaledChipIcon);
            }
            else
            {            
              lastAddedChipLabel.setIcon(null);
            }   
          }       
        }
        catch(IOException exc)
        {
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
    
    lastAddedChipLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lastAddedChipLabel.setVerticalAlignment(SwingConstants.NORTH);
    lastAddedChipLabel.setSize(300, 300);
    lastAddedChipLabel.setLocation(60, 20);
    lastAddedChipLabel.addMouseListener(new ChipRemoveListener());
    
    class ChipAddListener implements MouseListener
    {
      public void mouseClicked(MouseEvent e) 
      {
        try
        {
          //Adds the selected to the top of the stack
          JLabel clickedChip = (JLabel) e.getSource();
          int chipValue = chipMap.get(clickedChip);          
          if(chipValue <= bank)
          {
            bank = bank - chipValue;
            bet = bet + chipValue; //Increases the bet amount accordingly
            chipStack.push(chipValue);
            Image chipImage = ImageIO.read(new File("chips/" + chipValue + ".png"));
            Image scaledChipImage = chipImage.getScaledInstance(lastAddedChipLabel.getWidth(), lastAddedChipLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledChipIcon = new ImageIcon(scaledChipImage);
            lastAddedChipLabel.setIcon(scaledChipIcon);
          }
          bankLabel.setText("Bank: " + bank);
          betLabel.setText("Bet: " + bet);        
        }
        catch(IOException exc)
        {
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
    
    for(Integer value : chipValues)
    {
      try
      {
        JLabel chipLabel = new JLabel();
        chipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chipLabel.setVerticalAlignment(SwingConstants.NORTH);
        chipLabel.setSize(90, 90);
        Image chipImage = ImageIO.read(new File("chips/" + value + ".png"));
        Image scaledChipImage = chipImage.getScaledInstance(chipLabel.getWidth(), chipLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledChipIcon = new ImageIcon(scaledChipImage);
        chipLabel.setIcon(scaledChipIcon);
        chipLabel.addMouseListener(new ChipAddListener());
        chipGridPanel.add(chipLabel);
        chipMap.put(chipLabel, value);
      }
      catch(IOException e) 
      {
      }
    }
    
    //Closes the betting panel
    class DealListener implements MouseListener
    {
      public void mouseClicked(MouseEvent e) 
      {
        if(bet < GameRules.MIN_BET) //Do not allow values less than the minimum bet amount
        {
          JOptionPane.showMessageDialog(gamePanel, "You need to bet at least $" + GameRules.MIN_BET + ".", "Bad Move", JOptionPane.ERROR_MESSAGE);      
        }
        else
        {
          deal = true; //deal is over
          player.setBet(bet);
          ArrayList<Integer> chipArrayList = new ArrayList<Integer>();
          while(!chipStack.empty()) //add every chip value in the stack to the arraylist
          {
            chipArrayList.add(chipStack.pop());
          }
          player.addChipArrayList(chipArrayList); //add the chip value arraylist to the player
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
    
    JPanel moneyPanel = new JPanel();
    moneyPanel.setOpaque(false);
    moneyPanel.setLayout(new GridLayout(3, 1));
    moneyPanel.add(bankLabel);
    moneyPanel.add(betLabel);    
    JLabel dealLabel = new JLabel();
    dealLabel.setHorizontalAlignment(SwingConstants.CENTER);
    dealLabel.setVerticalAlignment(SwingConstants.NORTH);
    dealLabel.setSize(200, 50);
    try
    {
      Image dealImage = ImageIO.read(new File("buttons/deal.png"));
      Image scaledDealImage = dealImage.getScaledInstance(dealLabel.getWidth(), dealLabel.getHeight(), Image.SCALE_SMOOTH);
      ImageIcon scaledDealIcon = new ImageIcon(scaledDealImage);
      dealLabel.setIcon(scaledDealIcon);
    }
    catch(IOException exc)
    {
    }
    dealLabel.addMouseListener(new DealListener());
    moneyPanel.add(dealLabel);
    
    JPanel lastAddedChipPanel = new JPanel();
    lastAddedChipPanel.setOpaque(false);
    lastAddedChipPanel.setLayout(null);
    lastAddedChipPanel.add(lastAddedChipLabel);
    
    betPanel.add(chipGridPanel);
    betPanel.add(lastAddedChipPanel);
    betPanel.add(moneyPanel);
    return betPanel;
  }
  
  public boolean dealDone() //method to check if user clicked on deal
  {
    return deal;
  }
  
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
  }
}