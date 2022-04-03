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


import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleGame
{
  ArrayList<Player> players; //player list
  Player dealer; //dealer
  Deck deck; //deck
  Scanner scan = new Scanner(System.in);
  
  public ConsoleGame(int playerCount)
  {
    players = new ArrayList<Player>(); //creates the player list
    dealer = new Player("Dealer"); //creates a dealer.
    deck = new Deck(4);//Deck is created here. In this one, we are using 4 decks.
    deck.shuffle(); //shuffles the deck.
    for(int i = 1; i <= playerCount; i++)
    {
      players.add(new Player("P" + i)); //adds all the players
    }
    mainGame(); //starts the game
  }
  
  public void mainGame()
  {
    while(true) //continuously play (until everyone is broke)
    {
      //players bet first:
      for(Player p : players)
      { 
        playerBet(p);
      }
      
      //dealer draws two cards, first one is closed, second one is opened
      dealer.addCardToHand(deck.draw());
      Card newCard = deck.draw();
      newCard.turnUp();
      dealer.addCardToHand(newCard);
      System.out.println("Dealer has " + newCard + ".");
      
      
      //each player plays:
      for(Player p : players)
      { 
        play(p);
      }
      
      //dealer plays:
      boolean allPlayersBusted = true;
      for(Player p : players)
      {
        if(!p.checkBust())
        {
          allPlayersBusted = false;
          break;
        }
      }
      if(!allPlayersBusted) //if not everyone busted,
      {
        dealerPlay(); //dealer will play
      }
      
      //awarding system for each player:
      for(int playerIndex = 0; playerIndex < players.size(); playerIndex++)
      { 
        Player p = players.get(playerIndex);
        checkEndings(p);        
        if(p.getMoney() == 0) //if a player lost all his money, take him out.
        {
          players.remove(playerIndex); //removes the player.
          playerIndex--;
        }
      }
      
      dealer.resetHand(); //resets dealer's hand.
      
      if(players.size() == 0) //if all the players lost all their money, finish the game.
      {
        break;
      }
      
    }
    System.out.println("THANK YOU ALL FOR PLAYING."); //prints this message when everyone is broke
  }
  
  public void playerBet(Player player) //method that enables players to bet
  {    
    boolean checkBet = false;
    while(!checkBet) //run this until the player enters a valid bet
    {      
      System.out.println(player.getUserName() + " has $" + player.getMoney() + ".");
      System.out.println(player.getUserName() + ", how much you wanna bet?");
      int bet = scan.nextInt();
      checkBet = player.setBet(bet); //checks whether the player entered a valid bet
    }
    System.out.println(player.getUserName() + " got $" + player.getMoney() + " left.");
  }
  
  public void play(Player player) //players play here
  {    
    System.out.println(player.getUserName() + " is now playing.");    
    System.out.println(player.getUserName() + " got these two initially: ");
    //gives a card to the player
    Card newCard = deck.draw();
    newCard.turnUp();
    player.addCardToHand(newCard);
    System.out.println(newCard);
    //gives the player their second card
    newCard = deck.draw();
    newCard.turnUp();
    player.addCardToHand(newCard);
    System.out.println(newCard);
    System.out.println(player.getUserName() + "'s hand value is " + player.getHandValue() + ".");
    if(player.checkBlackjack()) //checks whether player blackjack'd.
    {
      System.out.println(player.getUserName() + " got a blackjack!");
    }
    
    boolean playerStand = false; //true when player decides to stand
    while(player.getHandValue() < 21 && !playerStand) //run until player decides to stand or the player busts
    {
      System.out.print("Hit, Stand, Double"); //available commands
      System.out.println("");
      String command = scan.next();
      command = command.toLowerCase(); //lowercases every command
      
      if(command.equals("hit")) //if the player decides to hit
      {
        //player gets a card
        newCard = deck.draw();
        newCard.turnUp();
        player.addCardToHand(newCard);
        System.out.println(player.getUserName() + " got " + newCard + ".");
        System.out.println(player.getUserName() + "'s hand value is " + player.getHandValue() + ".");
        if(player.checkBust()) //checks whether the player busted
        {
          System.out.println(player.getUserName() + " busted.");
        }
      }
      else if(command.equals("double")) //if the player decides to double down.
      {
        if(player.getCardCount() == 2 && player.doubleBet()) //first checks whether the player CAN double (when the player has 2 cards and the bet is <money/2)
        {
          System.out.println(player.getUserName() + " bets $" + player.getBet() / 2 + " more.");
          System.out.println(player.getUserName() + " got $" + player.getMoney() + " left.");
          //gives the player their next card
          newCard = deck.draw();
          newCard.turnUp();
          player.addCardToHand(newCard);
          System.out.println(player.getUserName() + " got " + newCard + ".");
          System.out.println(player.getUserName() + "'s hand value is " + player.getHandValue() + ".");
          if(player.checkBust()) //checks whether the player busted
          {
            System.out.println(player.getUserName() + " busted.");
          }
          else
          {
            playerStand = true; //since you cannot draw more cards when you double down, you basically stand after the hit.
          }
        }
        else
        {
          System.out.println("You can only double at the beginning if you have enough money."); //print this if the player wanted to double when they cannot
        }
      }
      else if(command.equals("stand")) //if the player decides to stand
      {
        playerStand = true; //player stands and the loop is over.
      }
    }
  }
  
  public void dealerPlay() //dealer's turn to play
  {
    if(dealer.checkBlackjack()) //checks whether the dealer got a blackjack
    {
      System.out.println("Dealer got a blackjack!");
      return;
    }
   
    System.out.println("Dealer's hand value is " + dealer.getHandValue() + ".");    
    while(dealer.getHandValue() < 17) //play until the dealer does not exceed 17 (soft hit 17)
    {
      //give the dealer their next card
      Card newCard = deck.draw();
      newCard.turnUp();
      dealer.addCardToHand(newCard);
      System.out.println("Dealer got " + newCard + ".");
      System.out.println("Dealer's hand value is " + dealer.getHandValue() + ".");
      if(dealer.checkBust()) //checks whether the dealer busted
      {
        System.out.println("Dealer busted.");
      }
    }
  }
  
  public void checkEndings(Player player) //checks endings (whether the player won, tied (push), or lost (bust)
  {
    //System.out.println(player.getUserName() + " got " + player.getHandValue() + " compared to dealer's " + dealer.getHandValue()); //can be turned on for extra information
    if(!player.checkBust() && !player.checkBlackjack()) //if the player did not bust nor got a blackjack
    {
      if(dealer.checkBust()) //if dealer busted.
      {
        System.out.println(player.getUserName() + " won because the dealer busted.");
        player.winBet(); //player wins
        System.out.println(player.getUserName() + " now has $" + player.getMoney() + ".");
      }
      else if(dealer.checkBlackjack()) //if dealer got a blackjack
      {
        System.out.println(player.getUserName() + " lost beacuse the dealer got a blackjack.");
        System.out.println(player.getUserName() + " now has $" + player.getMoney() + ".");
         //player busted
      }
      else
      {
        if(player.getHandValue() > dealer.getHandValue()) //if player's hand is bigger
        {
          System.out.println(player.getUserName() + " won by having a greater hand value than the dealer.");
          player.winBet(); //player wins
          System.out.println(player.getUserName() + " now has $" + player.getMoney() + ".");
        }
        
        else if(player.getHandValue() == dealer.getHandValue()) //if player's hand is equal to that of dealer
        {
          System.out.println(player.getUserName() + " tied by having an equal hand value with the dealer.");
          player.tieBet(); //player tied
        }
        
        else if(player.getHandValue() < dealer.getHandValue()) //if dealer's hand is bigger
        {
          System.out.println(player.getUserName() + " lost by having a smaller hand value than the dealer.");
           //player busted
        }
      }      
    }
    else if(player.checkBlackjack()) //if the player got a blackjack
    {
      if(!dealer.checkBlackjack()) //if dealer did not get a blackjack too
      {
        System.out.println(player.getUserName() + " won by getting a blackjack.");
        player.winBet(); //player wins
        System.out.println(player.getUserName() + " now has $" + player.getMoney() + ".");
      }
      else //if dealer got a blackjack too
      {
        System.out.println(player.getUserName() + " tied. Both the player and the dealer got a blackjack."); //player tied
      }
    }
    player.resetHand(); //resets the player's hand
    player.setBet(0); //resets the player's bet
  }
}
