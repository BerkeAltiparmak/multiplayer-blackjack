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
 *  notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Want A Punch? Studio or the names of its
 *  contributors may be used to endorse or promote products derived
 *  from this software without specific prior written permission.
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


import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

//For the cool animations:
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import javafx.animation.*;

//To put images
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.layout.Pane;

//If our frame throws IOException.
import java.io.IOException;
import java.io.File;

public class IntroFrame extends Application 
{
  boolean gameFrame = false; //this is used to prevent multiple stages to be opened at the end of the first part of the intro.
  boolean blackJackFrame = false; //this is used to prevent multiple frames to be opened at the end of this program.
  
  int FRAME_WIDTH; //width of the stage
  int FRAME_HEIGHT; //height of the stage
  
  Stage stage; //stage that works like a frame
  Scene scene; //scene that works like a panel
  
  public void start(Stage primaryStage) {
    
    primaryStage.setTitle("BlackJack by Want A Punch? Studio");
    
    studio(); //starts the first part of the intro, which introduces the studio
    
  }
  
  public void studio() //introducing the studio and its members
    
  {
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL); //type of stage is decleared
    stage.setTitle("BlackJack by Want A Punch? Studio");
    stage.setResizable(false);
    
    FRAME_WIDTH = 1200;
    FRAME_HEIGHT = 600;
    
    Group root = new Group(); //every component is put into root
    
    ImageView background = new ImageView("intropics/blackBackground.jpeg"); //black background
    //size of the black background is declared
    background.setFitHeight(FRAME_WIDTH*3);
    background.setFitWidth(FRAME_HEIGHT*3);
    root.getChildren().add(background); //is put into the root
    
    ImageView wImg = new ImageView("intropics/W.png"); //the W letter
    wImg.setFitHeight(FRAME_WIDTH/10);
    wImg.setFitWidth(FRAME_WIDTH/10);
    root.getChildren().add(wImg); //is put into the root
    
    ImageView aImg = new ImageView("intropics/A.png"); //the A letter
    aImg.setFitHeight(FRAME_WIDTH/10);
    aImg.setFitWidth(FRAME_WIDTH/10);
    root.getChildren().add(aImg); //is put into the root
    
    ImageView pImg = new ImageView("intropics/P.png"); //the P letter
    pImg.setFitHeight(FRAME_WIDTH/10);
    pImg.setFitWidth(FRAME_WIDTH/10);
    root.getChildren().add(pImg); //is put into the root
    
    ImageView titleImage = new ImageView("intropics/Title.png"); //the title that goes "Want A Punch? Studio"
    titleImage.setFitHeight(FRAME_WIDTH/2);
    titleImage.setFitWidth(FRAME_WIDTH/2);
    root.getChildren().add(titleImage); //is put into the root
    
    ImageView nameImage = new ImageView("intropics/Names.png"); //lists the names of the members
    nameImage.setFitHeight(FRAME_WIDTH/1.5);
    nameImage.setFitWidth(FRAME_WIDTH/1.1);
    root.getChildren().add(nameImage); //is put into the root
    
    
    Path pathW = new Path(); //creates a path that the letter W will follow
    pathW.getElements().addAll(new MoveTo(-5000, FRAME_HEIGHT/2), new HLineTo(FRAME_WIDTH/2 - FRAME_WIDTH/10)); //W will come from the left side
    
    Path pathA = new Path(); //creates a path that the letter A will follow
    pathA.getElements().addAll(new MoveTo(FRAME_WIDTH/2, -5000), new VLineTo(FRAME_HEIGHT/2)); //A will come from upside
    
    Path pathP = new Path(); //creates a path that the letter P will follow
    pathP.getElements().addAll(new MoveTo(FRAME_WIDTH + 5000, FRAME_HEIGHT/2), new HLineTo(FRAME_WIDTH/2 + FRAME_WIDTH/10)); //P will come from the right side
    
    Path pathT = new Path(); //creates a path that the title will follow
    pathT.getElements().addAll(new MoveTo(FRAME_WIDTH/2, FRAME_HEIGHT + 5000), new VLineTo(FRAME_HEIGHT/2 + FRAME_HEIGHT/4)); //Title will come from below
    
    Path pathN = new Path(); //creates a path that the names will follow
    pathN.getElements().addAll(new MoveTo(FRAME_WIDTH/2, FRAME_HEIGHT + 7000), new VLineTo(FRAME_HEIGHT/2 + FRAME_HEIGHT/4)); //Names will come from below
    
    
    RotateTransition rtW = new RotateTransition(Duration.seconds(2.5)); //makes the letter W rotate
    rtW.setByAngle(720); //in total letter W rotates 720 degrees
    
    RotateTransition rtP = new RotateTransition(Duration.seconds(2.5)); //makes the letter P rotate
    rtP.setByAngle(-7200); //in total letter P rotates 720 degrees
    
    
    FadeTransition ftB = new FadeTransition(Duration.millis(3000)); //black background fades in 3 seconds
    ftB.setFromValue(1); //from its full color
    ftB.setToValue(0.1); //to 0.1, it fades
    
    FadeTransition ftR = new FadeTransition(Duration.millis(6000)); //everything (the root) fades in 6 seconds
    ftR.setFromValue(1); //from their full color
    ftR.setToValue(0); //to 0, it fades completely
    
    
    
    Scene scene = new Scene(root, FRAME_WIDTH, FRAME_HEIGHT); //creates a scene that has root in it
    stage.setScene(scene); //adds the scene to the stage
    stage.show(); //shows the stage
    
    
    
    PathTransition ptW = new PathTransition(Duration.millis(2500), pathW, wImg); //w will move in 2.5 seconds
    ptW.play();
    PathTransition ptA = new PathTransition(Duration.millis(2500), pathA, aImg); //a will move in 2.5 seconds
    ptA.play();
    PathTransition ptP = new PathTransition(Duration.millis(2500), pathP, pImg); //p will move in 2.5 seconds
    ptP.play();
    PathTransition ptT = new PathTransition(Duration.millis(3000), pathT, titleImage); //title will move in 2.5 seconds
    ptT.play();
    PathTransition ptN = new PathTransition(Duration.millis(3000), pathN, nameImage); //names will move in 2.5 seconds
    ptN.play();
    
    
    ParallelTransition pt2W = new ParallelTransition(wImg, rtW); //w will rotate
    pt2W.play();
    ParallelTransition pt2P = new ParallelTransition(pImg, rtP); //p will rotate
    pt2P.play();
    ParallelTransition ptB = new ParallelTransition(background, ftB); //background will fade away
    ptB.play();
    ParallelTransition ptR = new ParallelTransition(root, ftR); //root will fade away
    ptR.play();
    
    
    //keeping track of time:
    Timeline timeline = new Timeline();
    
    
    timeline.currentTimeProperty().addListener(new InvalidationListener() {
      
      public void invalidated(Observable ov) {
        
        int time = (int) timeline.getCurrentTime().toSeconds();
        
        if(time == 5) //when 5 seconds have passed
        {
          if(!gameFrame) //otherwise, multiple stages pop up.
          {
            gameFrame = true; //so that no more than one stage pops up.
            game();
          }
        }
        
        if(time == 8) //when 8 seconds have passed
        {
          if(!blackJackFrame) //otherwise, two blackjack frames popped up.
          {
            try
            {
              stage.toBack();
              MainGameFrame mgf = new MainGameFrame();
              mgf.setVisible(true);
              
    
              //BoardsFrame bf = new  BoardsFrame(); //this will be added in the multiplayer part, in the future.
              
              blackJackFrame = true; //so that no more than one frame pops up.
            }
            catch(Exception e)
            {
              System.out.println("Uh oh. IOEXCEPTION!");
            }
          }
        }
      }
    });
    
    Duration time = new Duration(10000); //setting time
    KeyFrame keyFrame = new KeyFrame(time); //so that time can be counted
    timeline.getKeyFrames().add(keyFrame); //so that time can be counted
    timeline.setCycleCount(1); //sets cycle
    timeline.setAutoReverse(true); //makes the auto reverse possible
    
    timeline.play(); //starts counting time
    
  }
  
  public void game() //blackjack image comes up.
  {
    Pane root = new HBox(); //root that will have the blackjack image
    
    ImageView background = new ImageView("intropics/introwallpaper.jpg"); //the blackjack image
    //setting the size of the image.
    background.setFitHeight(FRAME_HEIGHT);
    background.setFitWidth(FRAME_WIDTH);
    root.getChildren().add(background); //adding the image to the root
    
    FadeTransition ft = new FadeTransition(Duration.millis(1000)); //fades in in 1 second
    ft.setFromValue(0); //from nothing
    ft.setToValue(1); //to its full color
    
    ParallelTransition pt2 = new ParallelTransition(root, ft); //makes the blackjack fade in
    pt2.play();
    
    scene = new Scene(root, FRAME_WIDTH, FRAME_HEIGHT); //creates the scene with the root
    stage.setScene(scene); //adds the scene to the stage
    stage.show(); //shows the stage
  }
  
}
