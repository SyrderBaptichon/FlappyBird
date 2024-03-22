package com.example.flappybird;

import android.graphics.Canvas;

public class Animator {
    private static final int MAX_UPDATES = 20;
    private Sprite[] oiseauSpriteArray;
    private int updateBeforeNextFrame;
    private int idx_strtd_mvng_frame = 0;
    private int idx_moving_frame = 1;

    public Animator(Sprite[] oiseauSpriteArray) {
        this.oiseauSpriteArray = oiseauSpriteArray;
    }

    public void draw(Canvas canvas, Oiseau oiseau) {
        switch (oiseau.getOiseauState().getState()) {
            case STARTED_MOVING:
                updateBeforeNextFrame = MAX_UPDATES;
                drawFrame(canvas, oiseau, oiseauSpriteArray[idx_strtd_mvng_frame]);
                break;
            case IS_MOVING:
                updateBeforeNextFrame--;
                if(updateBeforeNextFrame == 0) {
                    updateBeforeNextFrame = MAX_UPDATES;
                    toggleIdxMovingFrame();
                }
                drawFrame(canvas, oiseau, oiseauSpriteArray[idx_moving_frame]);
                break;
            default:
                break;
        }
    }

    private void toggleIdxMovingFrame() {
        if(idx_moving_frame == 1) {
            idx_moving_frame = 2;
        } else if (idx_moving_frame == 2) {
            idx_moving_frame = 0;
        } else {
            idx_moving_frame = 1;
        }
    }

    public void drawFrame(Canvas canvas, Oiseau oiseau, Sprite sprite) {
        sprite.draw(canvas, (int)oiseau.getX() - sprite.getWidth()/2, (int)oiseau.getY() - sprite.getHeight()/2);
    }
}
