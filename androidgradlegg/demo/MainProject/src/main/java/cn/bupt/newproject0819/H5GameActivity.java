package cn.bupt.newproject0819;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.Toast;

/**
 * H5游戏
 * Created by jialei on 15/9/10.
 */
public class H5GameActivity extends Activity {

    private WebView mWebView;
    private WebViewFragment webViewFragment;

    private final static String INTENT_URL_KEY = "intent_url_key";

    public static void launchActivity(Context context, String url) {
        Intent intent = new Intent(context, H5GameActivity.class);
        intent.putExtra(INTENT_URL_KEY, url);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_activity_h5game);

        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra(INTENT_URL_KEY);

        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        } else {
            Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
        }

    }
}
