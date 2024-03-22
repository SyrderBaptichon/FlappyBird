package com.example.flappybird;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class BaseObjet {
    //Classe utilisé pour créer des objets
    protected float x, y;
    protected int width, height;
    private Rect rect; //Rectangle utilisé pour détecter des collisions
    protected Bitmap bitmap;

    public BaseObjet() {

    }

    public BaseObjet(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rect getRect() {
        return new Rect((int) this.x, (int) this.y, (int) this.x+width, (int) this.y+height);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
