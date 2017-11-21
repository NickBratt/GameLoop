package com.choncoder.gameloop;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer = new Timer();
    ImageView myImage;
    TextView myTextView;

    int imageX = 0, imageY = 0;
    int speed = 10;
    int directionX = 1, directionY = 1;
    int screenX, screenY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImage = findViewById(R.id.imageView);
        myTextView = findViewById(R.id.textView);


        final int FPS = 40;
        TimerTask updateGame = new UpdateGameTask();
        timer.scheduleAtFixedRate(updateGame, 0 , 1000/FPS);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        myTextView.setText(String.valueOf(speed));
    }

    public void goFaster (View view){
        speed += 10;
    }

    public void goSlower (View view){
        speed -= 10;
    }

    public void reset(View view){
        speed = 10;
        imageX = 0;
        imageY = 0;
        directionX = 1;
        directionY = 1;
    }

    class UpdateGameTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    myTextView.setText(String.valueOf(speed));

                    imageX += (speed * directionX);
                    imageY += (speed * directionY);

                    myImage.setX(imageX);
                    myImage.setY(imageY);

                    if ((imageX + myImage.getWidth()) > screenX || imageX < 0){
                        directionX = directionX * -1;
                    }
                    if ((imageY + myImage.getHeight()) > screenY || imageY < 0){
                        directionY = directionY * -1;
                    }
                }
            });
        }
    }
}
