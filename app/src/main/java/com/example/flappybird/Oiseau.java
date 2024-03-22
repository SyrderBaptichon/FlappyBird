package com.example.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class Oiseau extends BaseObjet{
    private float drop;
    private Animator animator;
    OiseauState oiseauState;

    public Oiseau(Animator animator) {
        this.drop = 0;
        this.animator = animator;
        this.oiseauState = new OiseauState(this);
    }

    public void draw(Canvas canvas) {
        drop();
        animator.draw(canvas, this);
    }

    private void drop() {
        this.drop += 0.5;
        this.y += drop;
    }

    /*
    public void setArrOiseaux(ArrayList<Bitmap> arrOiseaux) {
        this.arrOiseaux = arrOiseaux;
        //proportionner la taille des bitmap à la taille des oiseaux
        for(int i = 0; i < arrOiseaux.size(); i++) {
            this.arrOiseaux.set(i, Bitmap.createScaledBitmap(this.arrOiseaux.get(i), this.width, this.height, true));
        }
    }
    */
    /*
     public Bitmap getOiseau() {
        //Faire tourner l'oiseau qd il tombe
        if(this.drop < 0) {
            Matrix matrix =  new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(arrOiseaux.get(idOiseauCourant), 0, 0, arrOiseaux.get(idOiseauCourant).getWidth(), arrOiseaux.get(idOiseauCourant).getHeight(), matrix, true);
        } else {
            Matrix matrix = new Matrix();
            if(drop > 70) {
                matrix.postRotate(-25+(drop*2));
            } else {
                matrix.postRotate(45);
            }
            return Bitmap.createBitmap(arrOiseaux.get(idOiseauCourant), 0, 0, arrOiseaux.get(idOiseauCourant).getWidth(), arrOiseaux.get(idOiseauCourant).getHeight(), matrix, true);
        }
    }
*/
    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }

    public OiseauState getOiseauState() {
        return oiseauState;
    }

    public void update() {
        oiseauState.update();
    }
}
