package com.popland.pop.drawingonsurfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by hai on 20/12/2017.
 */

public class Sprite {
 MainActivity.MyView myView;
 Bitmap trump;
 int x , y;
 int xSpeed , ySpeed;
 int width , height;
 int row, column;
//SpriteSheet is a big image of continuous movement, coordinate(0,0) at left top, row * column = movements(vertical) * movements(horizontal)
    public Sprite(MainActivity.MyView myView,Bitmap trump){
        this.myView = myView;
        this.trump = trump;
        x = y = 0;
        //get unit image's dimension
        width = trump.getWidth()/6;
        height = trump.getHeight()/4;
        xSpeed = 5;
        ySpeed = 0;
        row = 1;
        column = 0;
    }

    public void update(){
        try {
            Thread.sleep(50);//adjust FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //hit right wall
        if(x > (myView.getWidth()-width)){
            xSpeed = 0;
            ySpeed = 5;
            row = 0;
        }
        //hit bottom wall
        if(y >(myView.getHeight()-height)){
            xSpeed = -5;
            ySpeed = 0;
            row = 3;
        }
        //hit left wall
        if(x + xSpeed <0){
            xSpeed = 0;
            ySpeed = -5;
            row = 2;
        }
        //hit top wall
        if(y + ySpeed <0){
            xSpeed = 5;
            ySpeed = 0;
            row = 1;
        }
        column++;
        column = column % 6;
        x += xSpeed;
        y += ySpeed;
    }

    public void onDraw(Canvas canvas){
        update();
        //determine an specific unit image
        int srcW = column * width;
        int srcH = row * height;
        Rect src = new Rect(srcW,srcH,srcW+width,srcH+height);//subset of bitmap
        Rect dst = new Rect(x,y,x+width,y+height);//new scaled/translated image of the subset
        canvas.drawBitmap(trump,src,dst,null);
    }
}
