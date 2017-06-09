package cn.bupt.newproject0819;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class WebViewActivity extends FragmentActivity {

    protected WebViewFragment fragment;

    protected int from;
    protected boolean isInstallThirdApp;
    protected String mInputUrl;
    protected String title;
    protected boolean isSupportShare;


    public static String EXTRA_INSTALL_THIRD_APP = "extra_install_third_app";
    public static final String KEY_WEB_RESULT = "web_result_json";

    // 来自扫一扫
    public final static int FROM_SCAN_QR_CODE = 1;
    // 话费支付
    public final static int FROM_SMS_PAY_CODE = 2;
    // 会员中心
    public final static int VIP_CENTER = 3;
    // 夺宝SDK
    public final static int LOTTERY_SDK = 7;
    // AR SDK
    public final static int FROM_AR_SDK = 8;

    // 联通免流
    public final static int FROM_VIDEO_DETAIL = 4;
    public final static int FROM_DOWNLOAD_ABOUT = 5;
    public final static int FROM_OPERATION_POSITION = 6;


    protected int getContentViewLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());

        Intent intent = getIntent();

        initInputData(intent);
        initFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void initFragment() {
        fragment = (WebViewFragment) Fragment.instantiate(this, WebViewFragment.class.getName());

        // 传入地址
        WebViewFragment.InputParams inputParams = new WebViewFragment.InputParams(mInputUrl, isSupportShare, title,
                from, isInstallThirdApp);
        fragment.setInputParams(inputParams);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.webview_container, fragment);
        try {
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
        }
    }

    protected void initInputData(Intent intent) {
        mInputUrl = intent.getStringExtra(IntentTools.EXTRA_URL_WEBVIEW);
        isSupportShare = intent.getBooleanExtra(IntentTools.EXTRA_SUPPORT_SHARE, false);
        title = intent.getStringExtra(IntentTools.EXTRA_TITLE);
        from = intent.getIntExtra(IntentTools.EXTRA_WEBVIEW_FROM, 0);
        isInstallThirdApp = intent.getBooleanExtra(EXTRA_INSTALL_THIRD_APP, false);
    }





}
