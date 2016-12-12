package com.codemonkeylabs.fpslibrary.sample.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.codemonkeylabs.fpslibrary.sample.AppComponent;
import com.codemonkeylabs.fpslibrary.sample.FPSApplication;
import com.codemonkeylabs.fpslibrary.sample.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private AppComponent component;

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.recyclerView)
    FPSRecyclerView recyclerView;
    @Bind(R.id.fps_values_tv)
    TextView fpsTextView;

    TinyDancer mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FPSApplication application = (FPSApplication) getApplication();
        component = application.getComponent();
        component.inject(this);
        ButterKnife.bind(this);

        setupRadioGroup();
        mBuilder = TinyDancer.create();
        mBuilder.show(getApplicationContext());
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
                mBuilder.stop();
                StringBuilder sb = new StringBuilder();
                for (Long value : mBuilder.getFpsValues().getDataSet()) {
                    sb.append("").append(value).append(",");
                }
                Toast.makeText(MainActivity.this, "fps : " + sb.toString() + ", agv : " + mBuilder.getFpsValues().getAverage(), Toast.LENGTH_SHORT).show();
                fpsTextView.setText(sb);
            }
        });
    }

    @OnClick(R.id.start)
    public void start() {
        mBuilder.show(getApplicationContext());
    }

    @OnClick(R.id.stop)
    public void stop() {
        mBuilder.hide(getApplicationContext());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        component = null;
        ButterKnife.unbind(this);
    }
}
