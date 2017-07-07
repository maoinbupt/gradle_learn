package cn.bupt.newproject0819;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {
    
    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

    }

    private void initData() {

    }

    private void inflateData() {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
