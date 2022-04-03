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

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class MainGameFrame extends JFrame implements ComponentListener
{
  private static final int FRAME_WIDTH = 1200;
  private static final int FRAME_HEIGHT = 800;
  
  MainGamePanel gamePanel;
  
  public MainGameFrame()
  {
    prepareFrame();
    initComponents();
    addComponents();
    //MusicPlayer.playMusic("sound/jazz.wav");
  }
 
 //Sets the necessary parameters for the frame.
  private void prepareFrame()
  {
     setSize(FRAME_WIDTH, FRAME_HEIGHT);
     setResizable(false);
     setTitle("Blackjack");
     setLocationRelativeTo(null);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  }
  
  private void initComponents()
  {
    gamePanel = new MainGamePanel();
  }
  
  private void addComponents()
  {
    add(gamePanel, BorderLayout.CENTER);
  }
  
  // @TODO: Keep a constant aspect ratio
  @Override
  public void componentResized(ComponentEvent arg0)
  {
    int W = 3;  
    int H = 2;  
    Rectangle b = arg0.getComponent().getBounds();
    arg0.getComponent().setBounds(b.x, b.y, b.width, b.width*H/W);
  }
  
  public void componentShown(ComponentEvent evt)
  {
    System.out.println("componentShown");
  }

  public void componentHidden(ComponentEvent evt)
  {
    System.out.println("componentHidden");
  }

  public void componentMoved(ComponentEvent evt)
  {
    System.out.println("componentMoved");
  }  
}