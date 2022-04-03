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
import java.util.Random;

public class Deck
{
  private int deckCount;
  private ArrayList<Card> deck;
  
  public Deck(int n)
  {
    deckCount = n;
    deck = new ArrayList<Card>();
    init();
  }
  
  private void init()
  {
    String[] suitArray = new String[]{"C", "D", "H", "S"};
    String[] specialTypeArray = new String[]{"A", "J", "Q", "K"};
    for(int i = 2; i <= 9; i++)
    {
      for(String suit : suitArray)
      {
        for(int j = 0; j < deckCount; j++)
        {
          deck.add(new Card(String.valueOf(i), suit, false));
        }
      }
    }    
    for(String type : specialTypeArray)
    {
      for(String suit : suitArray)
      {
        for(int j = 0; j < deckCount; j++)
        {
          deck.add(new Card(type, suit, false));
        }
      }
    }
  }
  
  public void shuffle()
  {
    Random rand = new Random();
    ArrayList<Card> shuffledDeck = new ArrayList<Card>();
    while(deck.size() != 0)
    {
      shuffledDeck.add(deck.remove(rand.nextInt(deck.size())));
    }
    deck = shuffledDeck;
  }
  
  public Card draw()
  {
    if(deck.size() == 0)
    {
      init();
      shuffle();
    }
    return deck.remove(0);
  }
  
  public String toString()
  {
    String deckString = "";
    for(Card c : deck)
    {
      deckString = deckString + c.toString() + "\n";
      System.out.println(c);
    }
    return deckString;
  }
}