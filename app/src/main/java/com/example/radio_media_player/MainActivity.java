package com.example.radio_media_player;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView wP;
    int station = 0;
    ImageButton play, skip_n, skip_p;
    String antena1 = "https://antenaone.crossradio.com.br/stream/2;";
    String url = "https://playerservices.streamtheworld.com/api/livestream-redirect/BANDFM_SPAAC.m3u8?dist=onlineradiobox";
    MediaPlayer player = new MediaPlayer();
    boolean isPlaying = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        play = findViewById(R.id.play_b);
        skip_n = findViewById(R.id.skip_n_b);
        skip_p = findViewById(R.id.skip_p_b);
        wP = findViewById(R.id.wP);

        wP.setImageResource(R.drawable.band_i);

        player.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        play.setOnClickListener( l -> {
            if(isPlaying) {
                try {
                    player.setDataSource(url);
                    player.setOnPreparedListener(mp -> mp.start()); // Inicia quando estiver pronto
                    player.prepareAsync(); // Prepara de forma assíncrona para streaming
                } catch (IOException ignored) {

                }
                play.setImageResource(R.drawable.stop_ib);
                isPlaying = !isPlaying;
            } else {
                // Liberar o player quando a atividade for destruída
               player.stop();
               play.setImageResource(R.drawable.play_ib);
               isPlaying = !isPlaying;
            }
        });
    skip_n.setOnClickListener(l -> next_station());
    skip_p.setOnClickListener(l -> prev_station());
    }
    public void next_station() {
        station++;
        if (station > 4) {
            station = 0;
        }
        setStation();
    }

    public void prev_station() {
        station--;
        if (station < 0) {
            station = 4;
        }
        setStation();
    }
    public void setStation(){
        switch (station){
            case 0:
                url = "https://playerservices.streamtheworld.com/api/livestream-redirect/BANDFM_SPAAC.m3u8?dist=onlineradiobox";
                wP.setImageResource(R.drawable.band_i);
                break;
            case 1:
                url = antena1;
                wP.setImageResource(R.drawable.antena1_i);
                break;
            case 2:
                url = "https://playerservices.streamtheworld.com/api/livestream-redirect/RADIO_ALPHAFM_ADP.m3u8?dist=onlineradiobox";
                wP.setImageResource(R.drawable.alpha_i);
                break;
            case 3:
                url = "https://playerservices.streamtheworld.com/api/livestream-redirect/MIXFM_SAOPAULOAAC.m3u8?dist=onlineradiobox";
                wP.setImageResource(R.drawable.mix_i);
                break;
            case 4:
                url = "https://playerservices.streamtheworld.com/api/livestream-redirect/NATIVA_SPAAC.m3u8?dist=onlineradiobox";
                wP.setImageResource(R.drawable.nativa_i);
                break;
        }
        player.reset();
        player.stop();
        play.setImageResource(R.drawable.play_ib);
        isPlaying = !isPlaying;
    }
}