package com.sdrzlyz.h9.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.yunzhisheng.basic.USCRecognizerDialog;
import cn.yunzhisheng.basic.USCRecognizerDialogListener;
import cn.yunzhisheng.common.USCError;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.util.TimeConverter;

import java.util.Date;

/**
 * Created by huagai on 14-4-30.
 */
public class Voice extends Activity {
    private final String appKey = "d5lm3yzkwlb6g36c2f7vzo4bl4t5hahcvtsfcsat";
    private String text = "";
    private TextView show_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice);

        Button voice_Btn = (Button) findViewById(R.id.voice_btn);
        show_voice = (TextView) findViewById(R.id.show_voice);
        voice_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });


    }


    private void test() {

        USCRecognizerDialog recognizer = new USCRecognizerDialog(this, appKey);
        recognizer.setListener(new USCRecognizerDialogListener() {
            @Override
            public void onResult(String result, boolean isLast) {
                text += TimeConverter.Gettime2Str()+"--->"+result+"\n";
                show_voice.setText(text);
            }

            @Override
            public void onEnd(USCError uscError) {

            }
        });
        recognizer.show();
    }
}
