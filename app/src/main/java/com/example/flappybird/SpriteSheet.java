package com.example.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class SpriteSheet {
    private static final int SPRITE_HEIGHT_PIXELS = 130;
    private static final int SPRITE_WIDTH_PIXELS = 180;
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite[] getOiseauSpriteArray() {
        Sprite[] spriteArray = new Sprite[3];
        spriteArray[0] = new Sprite(this, new Rect(0*SPRITE_WIDTH_PIXELS, 0, 1*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[1] = new Sprite(this, new Rect(1*SPRITE_WIDTH_PIXELS, 0, 2*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        spriteArray[2] = new Sprite(this, new Rect(2*SPRITE_WIDTH_PIXELS, 0, 3*SPRITE_WIDTH_PIXELS, SPRITE_HEIGHT_PIXELS));
        //TODO: Proportionner la taille des sprites à la taille de l'écran et rendre dynamique le code du haut


        //TODO: Faire retourner l'oiseau qd il tombe

        //TODO: touches finales: proportions des tubes
        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}