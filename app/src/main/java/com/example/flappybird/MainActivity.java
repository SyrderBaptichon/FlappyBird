package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView txt_score, txt_best_score, txt_score_over;
    public static RelativeLayout rl_game_over;
    public static Button start_btn;
    public GameView game_view;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//cacher la barre de statut

        //Récuperer la hauteur et la largeur de l'écran du téléphone
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;

        setContentView(R.layout.activity_main);

        txt_score = findViewById(R.id.text_score);
        txt_best_score = findViewById(R.id.text_best_score);
        txt_score_over = findViewById(R.id.text_score_over);
        rl_game_over = findViewById(R.id.rl_game_over);
        start_btn = findViewById(R.id.start_btn);
        game_view = findViewById(R.id.game_view);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game_view.setStart(true);
                txt_score.setVisibility(View.VISIBLE);
                start_btn.setVisibility(View.INVISIBLE);
            }
        });

        rl_game_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_btn.setVisibility(View.VISIBLE);
                rl_game_over.setVisibility(View.INVISIBLE);
                game_view.setStart(false);
                game_view.reset();
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.android_assets_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}