package com.codemonkeylabs.fpslibrary.sample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.codemonkeylabs.fpslibrary.sample.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.recyclerView)
    FPSRecyclerView recyclerView;
    @Bind(R.id.fps_values_tv)
    TextView fpsTextView;

    TinyDancer mTinyDancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRadioGroup();
        startMonitor();
    }


    private void startMonitor() {
        mTinyDancer = TinyDancer.create(getApplicationContext());
//        mTinyDancer.show();
//        mTinyDancer.start();
        mTinyDancer.dumpFps(true) ;
        // todo: 需要在 Activity的onStart 函数之前调用
        mTinyDancer.install();
    }

    private void setupRadioGroup() {
        radioGroup.check(R.id.defaultValue);

        // set initial value
        RadioButton button = ButterKnife.findById(radioGroup, R.id.defaultValue);
        recyclerView.setMegaBytes(Float.valueOf(button.getText().toString()));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = ButterKnife.findById(radioGroup, i);
                recyclerView.setMegaBytes(Float.valueOf(button.getText().toString()));
                recyclerView.notifyDataSetChanged();
            }
        });

        findViewById(R.id.show_fps_values).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTinyDancer.stop();
                showFpsData();
            }
        });

        findViewById(R.id.restart_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTinyDancer.resume();
            }
        });
    }

    private void showFpsData() {
        StringBuilder sb = new StringBuilder();
        for (Long value : mTinyDancer.getFpsData().getDataSet()) {
            sb.append("").append(value).append(",");
        }
        fpsTextView.setText(sb);
        fpsTextView.append("\n " + mTinyDancer.getFpsData().getActivityName() + ", avg fps : "  + mTinyDancer.getFpsData().getAverage());
    }

    @OnClick(R.id.start)
    public void start() {
        mTinyDancer.show();
    }

    @OnClick(R.id.stop)
    public void stop() {
        mTinyDancer.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( mTinyDancer != null ) {
            mTinyDancer.destroy();
        }
        ButterKnife.unbind(this);
    }
}
