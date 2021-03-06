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

public class Player
{
  private String userName;
  private Hand hand;
  private int money;
  private int bet;
  private ArrayList<Integer> chipArrayList; //arraylist for holding selected chips by the player during betting
  
  public Player(String u)
  {
    userName = u;
    hand = new Hand();
    money = GameRules.STARTING_MONEY;
    bet = 0;
    chipArrayList = null;
  }
  
  public void addChipArrayList(ArrayList<Integer> arrayList)
  {
    chipArrayList = arrayList;
  }
  
  public ArrayList<Integer> getChipArrayList()
  {
    return chipArrayList;
  }
  
  public void resetHand()
  {
    hand = new Hand();
    bet = 0;
    chipArrayList = null;
  }
  
  public void addCardToHand(Card c)
  {
    hand.addCard(c);
  }
  
  public int getHandValue()
  {
    return hand.getValue();
  }
  
  public int getCardCount()
  {
    return hand.getCardCount();
  }
  
  public void turnHandUp()
  {
    hand.turnUp();
  }
  
  public int getBet()
  {
    return bet;
  }
  
  public boolean setBet(int b)
  {
    if(b <= money) //check if player has enough money for this bet amount
    {
      bet = b;
      money = money - bet;
      return true;
    }
    return false;
  }
  
  public boolean doubleBet()
  {
    if(bet <= money) //check if player has enough money for doubling
    {
      money = money - bet;
      bet = bet * 2; //double bet
      ArrayList<Integer> newChipArrayList = new ArrayList<Integer>();
      for(Integer chipValue : chipArrayList) //double every chip in the chip array list to update chip images on thr game panel
      {
        newChipArrayList.add(chipValue);
        newChipArrayList.add(chipValue);
      }
      chipArrayList = newChipArrayList;
      
      return true;
    }
    return false;
  }
  
  public void winBet()
  {
    if(checkBlackjack())
    {
      money = (int)(money + (2.5 * bet)); //blackjack pays 3 to 2
    }
    else
    {
      money = money + (2 * bet); //normal win pays 1 to 1
    }
    bet = 0;
  }
  
  public void tieBet()
  {
    money = money + bet; //tie returns the amount the player bet
    bet = 0;
  }
  
  public boolean checkBlackjack() //checks whether the hand is blackjack
  { 
    if((hand.getCardCount() == 2) && (hand.getValue() == 21)) //if hand value is 21 with only two cards
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public boolean checkBust()
  {
    if(getHandValue()> 21)
    {
      return true;
    }
    else
    {
      return false;
    }    
  }
  
  public int getMoney()
  {
    return money;
  }
  
  public String getUserName()
  {
    return userName;
  }
  
  public Hand getHand()
  {
    return hand;
  }
}