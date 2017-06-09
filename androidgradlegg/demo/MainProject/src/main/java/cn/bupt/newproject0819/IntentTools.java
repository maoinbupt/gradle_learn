package cn.bupt.newproject0819;

import android.content.Context;
import android.content.Intent;

/**
 * Created by GaoFeng on 2017.03.20.
 */

public class IntentTools {
    /**
     * intent可以传递的参数
     */
    public static final String EXTRA_URL_WEBVIEW = "url";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_WEBVIEW_FROM = "webview_from";
    public static final String EXTRA_SUPPORT_SHARE = "support_share";
    public static final String EXTRA_PGCID = "pgcId";

    public static void startWebViewActivity(Context context, String url) {
        Intent intent = webViewActivityIntent(context, url);
        context.startActivity(intent);
    }
    public static Intent webViewActivityIntent(Context context, String url) {

        Intent intent = new Intent();
        intent.putExtra(EXTRA_URL_WEBVIEW, url);

        intent.setClass(context, WebViewActivity.class);
        return intent;
    }
}
