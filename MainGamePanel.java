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
import javax.swing.JOptionPane;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.io.File;
import java.io.IOException;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class MainGamePanel extends JPanel 
{
  private Image backgroundImage;
  private MainGamePanel gamePanel;
  
  private ArrayList<Player> players;
  private Player dealer;
  private Deck deck;
  
  public MainGamePanel()
  {
    setLayout(null);
    try
    {
      backgroundImage = ImageIO.read(new File("wallpapers/blackjack7.png"));
    }
    catch(IOException e)
    {
    }
    
    gamePanel = this;
    
    players = new ArrayList<Player>();
    dealer = new Player("Dealer");
    deck = new Deck(1);
    deck.shuffle();    
    
    startGame();
  }
  
  private void startGame()
  {
    class Game implements Runnable
    {
      public void run()
      {
        int playerCount = Integer.parseInt(JOptionPane.showInputDialog(gamePanel, "How many players (1-5):", "New Game", JOptionPane.QUESTION_MESSAGE));    
        for(int i = 0; i < playerCount; i++) //add players to the player arraylist
        {
          String userName = JOptionPane.showInputDialog(gamePanel, "Type your name:", "New Player " + (i+1), JOptionPane.QUESTION_MESSAGE);
          players.add(new Player(userName));
        }
    
        try
        {
          Thread.sleep(1000);
        }
        catch(InterruptedException exc)
        {
        }
        while(true) //continuously play until all the players looses all their money
        {
          for(Player player : players)
          {
            playerBet(player); //eack player bets
          }
          
          dealerDeal(); //dealer deals cards
          
          for(Player player : players)
          {
            playerPlay(player); //each players plays
          }
          
          boolean allPlayersBusted = true;
          for(Player player : players) //if all te players busted dealer does not need to play
          {
            if(!player.checkBust())
            {
              allPlayersBusted = false;
              break;
            }
          }
          if(!allPlayersBusted)
          {
            dealerPlay(); //dealer plays if there players that are not busted
          }
          
          for(Player player : players)
          {
            checkEndings(player);
          }
          
          for(int playerIndex = 0; playerIndex < players.size(); playerIndex++)
          { 
            Player player = players.get(playerIndex);       
            if(player.getMoney() < GameRules.MIN_BET) //if a player does not have enough money to continue playing, take him out.
            {
              players.remove(playerIndex);
              playerIndex--;
            }
          }
          
          dealer.resetHand();
          repaint();
          
          if(players.size() == 0) //if all the players lost all their money, finish the game.
          {
            JOptionPane.showMessageDialog(null, "None of the players have enough money for another hand.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            break;
          }
        }
      }
    }
    Thread gameThread = new Thread(new Game());
    gameThread.start();
  }
  
  private void playerBet(Player player)
  {    
    BankPanel bankPanel = new BankPanel(player, this);
    bankPanel.setSize(1200, 400);
    bankPanel.setLocation(0, 800);
    add(bankPanel);
    revalidate();
    
    class UpAnimation implements Runnable //Animation for moving bank panel upwards
    {
      public void run()
      {
        int y = 800; //start from bottom
        while(y != 400)
        {
          y-=5; //move it to the up step by step
          bankPanel.setLocation(0, y);
          try
          {
            Thread.sleep(5);
          }
          catch(InterruptedException exc)
          {
          }
        }
      }
    }
    
    class DownAnimation implements Runnable //Animation for moving bank panel upwards
    {
      public void run()
      {
        int y = 400;
        while(y != 800) //move panel down step by step until panel reaches to very bottom
        {
          y+=5;
          bankPanel.setLocation(0, y);
          try
          {
            Thread.sleep(5);
          }
          catch(InterruptedException exc)
          {
          }
        }
      }
    }
    
    Thread upThread = new Thread(new UpAnimation());
    Thread downThread = new Thread(new DownAnimation());
    upThread.start(); //start upwards animation
    try
    {
      upThread.join(); //wait until upwards animation is over
    }
    catch(InterruptedException exc)
    {
    }
    
    while(!bankPanel.dealDone()) //check if player clicked on deal button
    {
      try
      {
        Thread.sleep(100);
      }
      catch(InterruptedException exc)
      {
      }
    }
    repaint(); //repaint to draw chip images that the player selected during betting
    downThread.start();    
    try
    {
      downThread.join();
    }
    catch(InterruptedException exc)
    {
    }
    remove(bankPanel);
    revalidate();
  }
  
  private void dealerDeal()
  {
    Card newCard;
    
    for(Player player : players) //first dealer deals one face up card to each player
    {
      newCard = deck.draw();
      newCard.turnUp();
      player.addCardToHand(newCard);
      repaint();
      try
      {
        Thread.sleep(1000); //wait 1 seconds before dealing card
      }
      catch(InterruptedException exc)
      {
      }    
    }
    
    newCard = deck.draw();
    newCard.turnUp();
    dealer.addCardToHand(newCard); //then the dealer deals one face up card to himself
    repaint();
    try
    {
      Thread.sleep(1000);
    }
    catch(InterruptedException exc)
    {
    }
    
    for(Player player : players) //then dealer deals one more face up card to each player
    {
      newCard = deck.draw();
      newCard.turnUp();
      player.addCardToHand(newCard);
      repaint();
      try
      {
        Thread.sleep(1000);
      }
      catch(InterruptedException exc)
      {
      }    
    }
    
    dealer.addCardToHand(deck.draw()); //then the dealer deals one face down card to himself
    repaint();
    try
    {
      Thread.sleep(1000);
    }
    catch(InterruptedException exc)
    {
    }
  }
  
  private void playerPlay(Player player)
  {
    if(player.checkBlackjack()) //if player gets a blacjack promt a message and end his play turn
    {
      JOptionPane.showMessageDialog(this, player.getUserName() + " got a blackjack!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
    }
    else //if player does not get a blackjack he should play
    {
      PlayPanel playPanel = new PlayPanel(player, deck, this); //play panel will be used to get commands (hit, stand, double) from the player
      playPanel.setSize(1200, 200);
      add(playPanel);
      playPanel.setLocation(0, 600);
      revalidate();
      
      class UpAnimation implements Runnable
      {
        public void run()
        {
          int y = 800;
          while(y != 600)
          {
            y-=5;
            playPanel.setLocation(0, y);
            try
            {
              Thread.sleep(5);
            }
            catch(InterruptedException exc)
            {
            }
          }
        }
      }
      
      class DownAnimation implements Runnable
      {
        public void run()
        {
          int y = 600;
          while(y != 800)
          {
            y+=5;
            playPanel.setLocation(0, y);
            try
            {
              Thread.sleep(5);
            }
            catch(InterruptedException exc)
            {
            }
          }
        }
      }
      
      Thread upThread = new Thread(new UpAnimation());
      Thread downThread = new Thread(new DownAnimation());
      upThread.start();
      try
      {
        upThread.join();
      }
      catch(InterruptedException exc)
      {
      }
      
      while(!playPanel.playDone())
      {
        try
        {
          Thread.sleep(100);
        }
        catch(InterruptedException exc)
        {
        }
      }
      downThread.start();
      try
      {
        downThread.join();
      }
      catch(InterruptedException exc)
      {
      }
      remove(playPanel);
      revalidate();
      
      if(player.checkBust()) //if player goes over 21, show a message that tells how much he has lost
      {
        JOptionPane.showMessageDialog(this, player.getUserName() + " busted and lost $" + player.getBet() + ".", "Busted", JOptionPane.INFORMATION_MESSAGE);      
      }
    }
    try
    {
      Thread.sleep(1000);
    }
    catch(InterruptedException exc)
    {
    }
  }
  
  private void dealerPlay()
  {
    dealer.turnHandUp(); //dealer reveals his face down card
    repaint();
    try
    {
      Thread.sleep(1000);
    }
    catch(InterruptedException exc)
    {
    }
    
    if(dealer.checkBlackjack())
    {
      JOptionPane.showMessageDialog(this, "Dealer got a blackjack!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    
    while(dealer.getHandValue() < 17) //dealer hits until 17
    {
      Card newCard = deck.draw();
      newCard.turnUp();
      dealer.addCardToHand(newCard);
      repaint();
      try
      {
        Thread.sleep(1000);
      }
      catch(InterruptedException exc)
      {
      } 
      if(dealer.checkBust())
      {
        JOptionPane.showMessageDialog(this, "Dealer busted.", "Busted", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
  
  private void checkEndings(Player player)
  {
    if(!player.checkBust() && !player.checkBlackjack()) //if player did not bust and did not get a blackjack
    {
      if(dealer.checkBust()) //if dealer busts player wins
      {
        JOptionPane.showMessageDialog(this, player.getUserName() + " won $" + player.getBet() + " because the dealer busted.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
        player.winBet();
      }
      else if(dealer.checkBlackjack()) //if dealer gets a blacjack players that did not get a blackjack looses
      {
        JOptionPane.showMessageDialog(this, player.getUserName() + " lost $" + player.getBet() + " because the dealer got a blackjack.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
      }
      else //if both dealer and player did not bust or get a blackjack then just compare their hand values
      {
        if(player.getHandValue() > dealer.getHandValue())
        {
          JOptionPane.showMessageDialog(this, player.getUserName() + " won $" + player.getBet() + " by having a greater hand value than the dealer.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
          player.winBet();
        }
        
        else if(player.getHandValue() == dealer.getHandValue())
        {
          JOptionPane.showMessageDialog(this, player.getUserName() + " tied by having an equal hand value with the dealer.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
          player.tieBet();
        }
        
        else if(player.getHandValue() < dealer.getHandValue())
        {
          JOptionPane.showMessageDialog(this, player.getUserName() + " lost $" + player.getBet() + " by having a smaller hand value than the dealer.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
        }
      }      
    }
    else if(player.checkBlackjack())
    {
      if(!dealer.checkBlackjack())
      {
        JOptionPane.showMessageDialog(this, player.getUserName() + " won $" + player.getBet() * 1.5 + " by getting a blackjack.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
        player.winBet();
      }
      else
      {
        JOptionPane.showMessageDialog(this, player.getUserName() + " tied. Both the player and the dealer got a blackjack.", player.getUserName(), JOptionPane.INFORMATION_MESSAGE);
      }
    }
    player.resetHand();
    try
    {
      Thread.sleep(1000);
    }
    catch(InterruptedException exc)
    {
    }
  }
  
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    
    ArrayList<Card> dealerCardArrayList = dealer.getHand().getCardArrayList();    
    if(dealerCardArrayList != null)
    {
      for(int cardIndex = 0; cardIndex < dealerCardArrayList.size(); cardIndex++)
      {
        Card card = dealerCardArrayList.get(cardIndex); //draw dealer's cards
        try
        {
          Image cardImage = ImageIO.read(new File("cards/" + card.toString() + ".png"));
          g2.drawImage(cardImage, 520 + (cardIndex * 25), 100, 80, 120, null);             
        }
        catch(IOException e)
        {
        }   
      }
    } 
    
    for(int playerIndex = 0; playerIndex < players.size(); playerIndex++) //draw players' chips and cards
    {      
      Player player = players.get(playerIndex);
      ArrayList<Integer> chipArrayList = player.getChipArrayList();
      ArrayList<Card> cardArrayList = player.getHand().getCardArrayList();
      
      if(chipArrayList != null)
      {
        for(int chipIndex = 0; chipIndex < chipArrayList.size(); chipIndex++)
        {
          int chipValue = chipArrayList.get(chipIndex);
          try
          {
            Image chipImage = ImageIO.read(new File("chips/" + chipValue + ".png"));
            if(playerIndex == 0) //draw chips and cards based on player's position on te table
            {
              g2.drawImage(chipImage, 80, 410 + (chipIndex * 25), 60, 60, null);
            }
            else if(playerIndex == 1)
            {
              g2.drawImage(chipImage, 320, 480 + (chipIndex * 25), 60, 60, null);
            }
            else if(playerIndex == 2)
            {
              g2.drawImage(chipImage, 580, 510 + (chipIndex * 25), 60, 60, null);
            }
            else if(playerIndex == 3)
            {
              g2.drawImage(chipImage, 840, 480 + (chipIndex * 25), 60, 60, null);
            }
            else if(playerIndex == 4)
            {
              g2.drawImage(chipImage, 1080, 410 + (chipIndex * 25), 60, 60, null);
            }
          }
          catch(IOException e)
          {
          }   
        }
      }
      
      if(cardArrayList != null)
      {
        for(int cardIndex = 0; cardIndex < cardArrayList.size(); cardIndex++)
        {
          Card card = cardArrayList.get(cardIndex);
          try
          {
            Image cardImage = ImageIO.read(new File("cards/" + card.toString() + ".png"));
            if(playerIndex == 0)
            {
              g2.drawImage(cardImage, 10 + (cardIndex * 25), 290, 80, 120, null);
            }
            else if(playerIndex == 1)
            {
              g2.drawImage(cardImage, 260 + (cardIndex * 25), 350, 80, 120, null);
            }
            else if(playerIndex == 2)
            {
              g2.drawImage(cardImage, 520 + (cardIndex * 25), 390, 80, 120, null);
            }
            else if(playerIndex == 3)
            {
              g2.drawImage(cardImage, 760 + (cardIndex * 25), 360, 80, 120, null);
            }
            else if(playerIndex == 4)
            {
              g2.drawImage(cardImage, 1010 + (cardIndex * 25), 290, 80, 120, null);
            }
          }
          catch(IOException e)
          {
          }   
        }
      }   
    }
  }
}