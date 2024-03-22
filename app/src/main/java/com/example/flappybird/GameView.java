package com.example.flappybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class GameView extends View {
    private Oiseau oiseau;
    private android.os.Handler handler;
    private Runnable runnable;
    private ArrayList<Tuyau> arrTayaux;
    private int sommeTuyau, distTuyau;
    private int score, best_score = 0;
    private boolean start;
    private Context context;
    private int soundJump;
    private boolean loadedSound;
    private SoundPool soundPool;
    SpriteSheet spriteSheet;
    Animator animator;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            best_score = sharedPreferences.getInt("bestscore", 0);
        }
        score = 0;
        start = false;
        spriteSheet = new SpriteSheet(context);
        animator = new Animator(spriteSheet.getOiseauSpriteArray());
        initOiseau(animator);
        initTuyau();

        //Créer une boucle pour rafraichir l'interface
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        if(Build.VERSION.SDK_INT >= 21) { //Vérifie si la version d'android utilisé
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                loadedSound = true;
            }
        });
        soundJump = this.soundPool.load(context, R.raw.android_assets_sfx_wing, 1);
    }

    private void initTuyau() {
        sommeTuyau = 6;
        distTuyau = 300*Constants.SCREEN_HEIGHT/1920;
        arrTayaux = new ArrayList<>();
        for(int i = 0; i < sommeTuyau; i++) {
            if(i < sommeTuyau/2) {
                this.arrTayaux.add(new Tuyau(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200*Constants.SCREEN_WIDTH/1080)/(sommeTuyau/2)),
                        0, 200*Constants.SCREEN_WIDTH/1080, Constants.SCREEN_HEIGHT/2));
                this.arrTayaux.get(this.arrTayaux.size()-1).setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.toptube));
                this.arrTayaux.get(this.arrTayaux.size()-1).randomY();
            } else {
                this.arrTayaux.add(new Tuyau(this.arrTayaux.get(i-sommeTuyau/2).getX(), this.arrTayaux.get(i-sommeTuyau/2).getY()+this.arrTayaux.get(i-sommeTuyau/2).getHeight()+this.distTuyau,
                        200*Constants.SCREEN_WIDTH/1080, Constants.SCREEN_HEIGHT/2));
                this.arrTayaux.get(this.arrTayaux.size()-1).setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bottomtube));
            }
        }
    }

    private void initOiseau(Animator animator) {
        oiseau = new Oiseau(animator);
        oiseau.setWidth(100*Constants.SCREEN_WIDTH/1080);
        oiseau.setHeight(100*Constants.SCREEN_HEIGHT/1920);
        oiseau.setX(100*Constants.SCREEN_WIDTH/1080);
        oiseau.setY(Constants.SCREEN_HEIGHT/2-oiseau.getHeight()/2);
        oiseau.update();
    }

    public void draw(Canvas canvas) { //un canvas est une surface pour dessiner
        super.draw(canvas);
        if(start) {
            oiseau.draw(canvas);
            oiseau.update();
            for(int i = 0; i < sommeTuyau; i++) {
                //Détection des collisions
                if(oiseau.getRect().intersect(arrTayaux.get(i).getRect()) || oiseau.getY()-oiseau.getHeight()<0 || oiseau.getY()>Constants.SCREEN_HEIGHT) {
                    Tuyau.vitesse = 0;
                    MainActivity.txt_score_over.setText(MainActivity.txt_score.getText());
                    MainActivity.txt_best_score.setText("Best Score: "+best_score);
                    MainActivity.txt_score.setVisibility(INVISIBLE);
                    MainActivity.rl_game_over.setVisibility(VISIBLE);
                }
                //Si on a dépassé un couple de tuyau on augmente les score
                if(this.oiseau.getX()+this.oiseau.getWidth() > arrTayaux.get(i).getX()+arrTayaux.get(i).getWidth()/2
                        && this.oiseau.getX()+this.oiseau.getWidth() <= arrTayaux.get(i).getX()+arrTayaux.get(i).getWidth()/2+Tuyau.vitesse
                        && i < sommeTuyau/2) {
                    score++;
                    if(score > best_score) {
                        best_score = score;
                        SharedPreferences sharedPreferences = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("bestscore", best_score);
                        editor.apply();
                    }
                    MainActivity.txt_score.setText(""+score);
                }
                //Si le tuyau dépasse l'écran on reset la position
                if(this.arrTayaux.get(i).getX() < -arrTayaux.get(i).getWidth()) {
                    this.arrTayaux.get(i).setX(Constants.SCREEN_WIDTH);
                    if(i < sommeTuyau/2) {
                        arrTayaux.get(i).randomY();
                    } else {
                        arrTayaux.get(i).setY(this.arrTayaux.get(i-sommeTuyau/2).getY()+this.arrTayaux.get(i-sommeTuyau/2).getHeight()+this.distTuyau);
                    }
                }
                this.arrTayaux.get(i).draw(canvas);
            }
        } else {
            if(oiseau.getY()>Constants.SCREEN_HEIGHT/2) {
                oiseau.setDrop(-15*Constants.SCREEN_HEIGHT/1920);
            }
            oiseau.draw(canvas);
            oiseau.update();
        }
        handler.postDelayed(runnable, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { //Faire sauter l'oiseau qd on touche l'écran
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oiseau.setDrop(-15);
            if(loadedSound) {
                int streamId = this.soundPool.play(this.soundJump, (float) 1, (float) 1, 1, 0, 1f);
            }
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        MainActivity.txt_score.setText("0");
        score = 0;
        initTuyau();
        initOiseau(animator);
    }
}
