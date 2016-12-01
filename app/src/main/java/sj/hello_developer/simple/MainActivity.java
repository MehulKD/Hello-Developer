package sj.hello_developer.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sj.hello_developer.DeveloperActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DeveloperActivity.startActivity(MainActivity.this);
        this.finish();
    }
}
