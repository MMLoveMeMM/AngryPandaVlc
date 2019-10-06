package pumpkin.org.angrypandavlc;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;

import java.io.IOException;
import java.util.ArrayList;

import pumpkin.org.angrypandavlc.util.LibVLCUtil;

public class MainActivity extends AppCompatActivity {

    private Button mBtn;
    private Button mJumpBtn;

    private MediaPlayer mediaPlayer;
    private Media media;
    private MediaPlayer.EventListener eventListener;

    private final String url="http://m8.music.126.net/20191006144633/df3c005592df3a68f7949588c1a8afc5/ymusic/59c5/8f95/298d/11d842f39fb503bb9b29c37ad4080a14.mp3";
    private final String url1 = "http://od.open.qingting.fm/m4a/5822c8767cb891101952eb6e_6245494_64.m4a?u=786&channelId=193626&programId=5825617";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.play);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.setRate(2.0f);
                mediaPlayer.play();

            }
        });

        ArrayList<String> options = new ArrayList<>();
        // options.add(":file-caching=1500");//文件缓存
//        options.add(":network-caching=1500");//网络缓存

//        options.add(":live-caching=1500");//直播缓存
//        options.add(":sout-mux-caching=1500");//输出缓存
//        options.add(":codec=mediacodec,iomx,all");
        options.add("--aout=opensles");
        options.add("--audio-time-stretch"); // time stretching
        options.add("-vvv");

        LibVLC libvlc = LibVLCUtil.getLibVLC(options);
        mediaPlayer = new MediaPlayer(libvlc);
        mediaPlayer.setRate(1.0f);
        media = new Media(libvlc, Uri.parse("http://od.open.qingting.fm/m4a/5822c8767cb891101952eb6e_6245494_64.m4a?u=786&channelId=193626&programId=5825617"));
        media.setHWDecoderEnabled(false, false);//软解码
        media.parse(Media.Parse.ParseNetwork);
        /**
         * 播放本地资源
         */
        String sdDir = Environment.getExternalStorageDirectory().toString();//获取跟目录
        //查找SD卡根路径
        sdDir += "/qilixiang.mp3";
        media = new Media(libvlc, sdDir);
        eventListener = new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {
                try {
                    //播放结束
                    if (mediaPlayer.getPlayerState() == MediaPlayer.Event.EndReached/*Media.State.Ended*/) {
                        mediaPlayer.setTime(0);
                        mediaPlayer.stop();
                    }
                } catch (Exception e) {
                    Log.d("vlc-event", e.toString());
                }
            }
        };
        mediaPlayer.setEventListener(eventListener);
        mediaPlayer.setMedia(media);








        mJumpBtn=(Button)findViewById(R.id.jump);
        mJumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {








                //Intent intent = new Intent();
                //intent.setClass(MainActivity.this, VLCActivity.class);
                //startActivity(intent);
            }
        });

    }

}
