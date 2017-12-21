package com.popland.pop.drawingonsurfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
MyView myView;
Bitmap bitmap, trump;
float x ,y;
Sprite sprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = new MyView(this);
        myView.setOnTouchListener(this);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        trump = BitmapFactory.decodeResource(getResources(),R.drawable.spritesheet);
        x = y = 0;
        setContentView(myView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myView.suspend();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myView.resume();;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
        }
        return false;
    }

    class MyView extends SurfaceView implements Runnable{
        Thread thread;
        boolean suspended;
        SurfaceHolder holder;

        public MyView(Context c){
            super(c);
            suspended = false;
            holder = getHolder();
        }

        @Override
        public void run() {
            sprite = new Sprite(MyView.this,trump);
            while(!suspended){//create loop like invalidate
                if(!holder.getSurface().isValid())//if surface not created, skip
                    continue;
                Canvas c = holder.lockCanvas();
                 drawing(c);
                holder.unlockCanvasAndPost(c);
            }
        }

        public void drawing(Canvas c){
            c.drawARGB(255,150,150,15);//draw background
            c.drawBitmap(bitmap, x-bitmap.getWidth()/2, y-bitmap.getHeight()/2, new Paint());
            sprite.onDraw(c);
        }

        public void suspend(){
            suspended = true;
            //while(true){infitnite loop like for(; ;)
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("AAA","break");
               // break;
          // }
            thread = null;
        }

        public void resume(){
            suspended = false;
            thread = new Thread(this);
            thread.start();
        }
    }
}
