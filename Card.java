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

public class Card
{
  private String type;
  private String suit;
  private boolean faceUp;
  
  public Card(String t, String s, boolean up)
  {
    type = t;
    suit = s;
    faceUp = up;
  }
  
  public String getType()
  {
    return type;
  }
  
  public String getSuit()
  {
    return suit;
  }
  
  public int getValue()
  {
    if(type.equals("K") || type.equals("Q") || type.equals("J"))
    {
      return 10;
    }
    else if(type.equals("A"))
    {
      return 11;
    }
    else
    {
      return Integer.parseInt(type);
    }
  }
  
  public void turnUp()
  {
    faceUp = true;
  }
  
  public void turnDown()
  {
    faceUp = true;
  }
  
  public String toString()
  {
    if(faceUp)
    {
      return type + suit;
    }
    else
    {
      return "faceDown";
    }
  }
}