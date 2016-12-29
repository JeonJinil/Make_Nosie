package com.example.jeonjin_il.myfirstapp;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private MediaPlayer bird, campfire, grass, owl, rain, river, thunder, wind;
    private ImageButton bird_button, campfire_button, grass_button, owl_button, rain_button, river_button, thunder_button, wind_button;
    private boolean bird_num = false, campfire_num = false, grass_num = false, owl_num = false, rain_num = false, river_num = false, thunder_num = false, wind_num = false;
    private SeekBar bird_seekbar, campfire_seekbar, grass_seekbar, owl_seekbar, rain_seekbar, river_seekbar, thunder_seekbar, wind_seekbar;
    private int bird_seeknum = 50, campfire_seeknum = 50, grass_seeknum = 50, owl_seeknum = 50, rain_seeknum = 50, river_seeknum = 50, thunder_seeknum = 50, wind_seeknum = 50;
    long nStart = 0, nEnd = 0;
    private Handler mHandler;
    TextView textview2;
    private Notification.Builder builder;
    private NotificationManager nm;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public boolean onOptionsItemSelected(MenuItem item) {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                overridePendingTransition(0, 0);
                finish();
            }
        };
        int time = 1000;
        switch (item.getItemId()) {
            case R.id.subMenu_1:
                time = 1000 * 300;
                handler.sendEmptyMessageDelayed(0, time);
                Toast.makeText(this, "5분 설정 되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subMenu_2:
                time = 1000 * 300 * 2;
                handler.sendEmptyMessageDelayed(0, time);
                Toast.makeText(this, "10분 설정 되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subMenu_3:
                time = 1000 * 300 * 4;
                handler.sendEmptyMessageDelayed(0, time);
                Toast.makeText(this, "20분 설정 되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subMenu_4:
                time = 1000 * 1800;
                handler.sendEmptyMessageDelayed(0, time);
                Toast.makeText(this, "30분 설정 되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subMenu_5:
                time = 1000 * 3600;
                handler.sendEmptyMessageDelayed(0, time);
                Toast.makeText(this, "1시간 설정 되었습니다", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subMenu_6:
                time = 1000 * 3600 * 3;
                handler.sendEmptyMessageDelayed(0, time);
                Toast.makeText(this, "3시간 설정 되었습니다", Toast.LENGTH_SHORT).show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nm.cancelAll();
        Process.killProcess(Process.myPid());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("종료 확인 대화 상자")
                .setMessage("앱을 종료 하시 겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onDestroy();
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inti();

        mHandler = new Handler();
        mHandler.postDelayed(UpdateSongTime,1000);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.main);
        builder.setTicker("내가 만드는 화이트 노이즈.");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("내가만드는 화이트 노이즈");
        builder.setContentText("실행중입니다. 어플로가려면 누르세요");
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(123456, builder.build());


        bird_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bird_seeknum = bird_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - bird_seeknum) / Math.log(100));
                bird.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        bird_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bird_num) {
                    bird_num = true;
                    bird_seeknum = bird_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - bird_seeknum) / Math.log(100));
                    bird.setVolume(1 - log1, 1 - log1);
                    bird.start();
                    bird.setLooping(true);
                    bird_button.setImageResource(R.drawable.bird2);
                } else {
                    bird_num = false;
                    bird.pause();
                    bird_button.setImageResource(R.drawable.bird1);
                }
            }
        });

        campfire_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                campfire_seeknum = campfire_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - campfire_seeknum) / Math.log(100));
                campfire.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        campfire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!campfire_num) {
                    campfire_num = true;
                    campfire_seeknum = campfire_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - campfire_seeknum) / Math.log(100));
                    campfire.setVolume(1 - log1, 1 - log1);
                    campfire.start();
                    campfire.setLooping(true);
                    campfire_button.setImageResource(R.drawable.campfire2);
                } else {
                    campfire_num = false;
                    campfire.pause();
                    campfire_button.setImageResource(R.drawable.campfire1);
                }
            }
        });

        grass_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                grass_seeknum = grass_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - grass_seeknum) / Math.log(100));
                grass.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        grass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!grass_num) {
                    grass_num = true;
                    grass_seeknum = bird_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - grass_seeknum) / Math.log(100));
                    grass.setVolume(1 - log1, 1 - log1);
                    grass.start();
                    grass.setLooping(true);
                    grass_button.setImageResource(R.drawable.grass2);
                } else {
                    grass_num = false;
                    grass.pause();
                    grass_button.setImageResource(R.drawable.grass1);
                }
            }
        });

        owl_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                owl_seeknum = owl_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - owl_seeknum) / Math.log(100));
                owl.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        owl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!owl_num) {
                    owl_num = true;
                    owl_seeknum = owl_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - owl_seeknum) / Math.log(100));
                    owl.setVolume(1 - log1, 1 - log1);
                    owl.start();
                    owl.setLooping(true);
                    owl_button.setImageResource(R.drawable.owl2);
                } else {
                    owl_num = false;
                    owl.pause();
                    owl_button.setImageResource(R.drawable.owl1);
                }
            }
        });

        rain_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rain_seeknum = rain_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - rain_seeknum) / Math.log(100));
                rain.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        rain_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rain_num) {
                    rain_num = true;
                    rain_seeknum = rain_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - rain_seeknum) / Math.log(100));
                    rain.setVolume(1 - log1, 1 - log1);
                    rain.start();
                    rain.setLooping(true);
                    rain_button.setImageResource(R.drawable.rain2);
                } else {
                    rain_num = false;
                    rain.pause();
                    rain_button.setImageResource(R.drawable.rain1);
                }
            }
        });

        river_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                river_seeknum = river_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - river_seeknum) / Math.log(100));
                river.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        river_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!river_num) {
                    river_num = true;
                    river_seeknum = river_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - river_seeknum) / Math.log(100));
                    river.setVolume(1 - log1, 1 - log1);
                    river.start();
                    river.setLooping(true);
                    river_button.setImageResource(R.drawable.river2);
                } else {
                    river_num = false;
                    river.pause();
                    river_button.setImageResource(R.drawable.river1);
                }
            }
        });

        thunder_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thunder_seeknum = thunder_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - thunder_seeknum) / Math.log(100));
                thunder.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        thunder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!thunder_num) {
                    thunder_num = true;
                    thunder_seeknum = thunder_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - thunder_seeknum) / Math.log(100));
                    thunder.setVolume(1 - log1, 1 - log1);
                    thunder.start();
                    thunder.setLooping(true);
                    thunder_button.setImageResource(R.drawable.thunder2);
                } else {
                    thunder_num = false;
                    thunder.pause();
                    thunder_button.setImageResource(R.drawable.thunder1);
                }
            }
        });

        wind_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wind_seeknum = wind_seekbar.getProgress();
                float log1 = (float) (Math.log(100 - wind_seeknum) / Math.log(100));
                wind.setVolume(1 - log1, 1 - log1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        wind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wind_num) {
                    wind_num = true;
                    wind_seeknum = wind_seekbar.getProgress();
                    float log1 = (float) (Math.log(100 - wind_seeknum) / Math.log(100));
                    wind.setVolume(1 - log1, 1 - log1);
                    wind.start();
                    wind.setLooping(true);
                    wind_button.setImageResource(R.drawable.wind2);
                } else {
                    wind_num = false;
                    wind.pause();
                    wind_button.setImageResource(R.drawable.wind1);
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            nEnd = System.currentTimeMillis();
            long startTime = nEnd - nStart;
            textview2.setText(String.format("집중한시간 : %d hour , %d min, %d sec",
                    TimeUnit.MILLISECONDS.toHours(startTime),
                    TimeUnit.MILLISECONDS.toMinutes(startTime)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(startTime)),
                    TimeUnit.MILLISECONDS.toSeconds(startTime)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));
            mHandler.postDelayed(this,1000);
        }
    };


    void inti() {

        nStart = System.currentTimeMillis();

        textview2 = (TextView)findViewById(R.id.textView2);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        bird = MediaPlayer.create(this, R.raw.bird_sound);
        bird_button = (ImageButton) findViewById(R.id.bird);
        bird_seekbar = (SeekBar) findViewById(R.id.bird_seekbar);

        campfire = MediaPlayer.create(this, R.raw.campfire_sound);
        campfire_button = (ImageButton) findViewById(R.id.campfire);
        campfire_seekbar = (SeekBar) findViewById(R.id.campfire_seekbar);

        grass = MediaPlayer.create(this, R.raw.grass_sound);
        grass_button = (ImageButton) findViewById(R.id.grass);
        grass_seekbar = (SeekBar) findViewById(R.id.grass_seekbar);

        owl = MediaPlayer.create(this, R.raw.owl_sound);
        owl_button = (ImageButton) findViewById(R.id.owl);
        owl_seekbar = (SeekBar) findViewById(R.id.owl_seekbar);

        rain = MediaPlayer.create(this, R.raw.rain_sound);
        rain_button = (ImageButton) findViewById(R.id.rain);
        rain_seekbar = (SeekBar) findViewById(R.id.rain_seekbar);

        river = MediaPlayer.create(this, R.raw.river_sound);
        river_button = (ImageButton) findViewById(R.id.river);
        river_seekbar = (SeekBar) findViewById(R.id.river_seekbar);

        thunder = MediaPlayer.create(this, R.raw.thunder_sound);
        thunder_button = (ImageButton) findViewById(R.id.thunder);
        thunder_seekbar = (SeekBar) findViewById(R.id.thunder_seekbar);

        wind = MediaPlayer.create(this, R.raw.wind_sound);
        wind_button = (ImageButton) findViewById(R.id.wind);
        wind_seekbar = (SeekBar) findViewById(R.id.wind_seekbar);

    }
}
