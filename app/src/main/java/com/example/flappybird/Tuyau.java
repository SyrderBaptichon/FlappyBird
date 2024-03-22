package com.example.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Tuyau extends BaseObjet{
    public static int vitesse;

    public Tuyau(float x, float y, int width, int height) {
        super(x, y, width, height);
        vitesse = 10*Constants.SCREEN_WIDTH/1080;
    }

    public void draw(Canvas canvas) {
        this.x -= vitesse; //Bouge les tuyau de la gauche vers la droite
        canvas.drawBitmap(this.bitmap, this.x, this.y, null);
    }

    public void randomY() {
        Random random = new Random();
        this.y = random.nextInt((0+this.height/4)+1) - this.height/4;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height,true);
    }
}
