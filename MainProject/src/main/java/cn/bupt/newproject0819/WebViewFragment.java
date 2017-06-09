/**
 *
 */
package cn.bupt.newproject0819;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chengjiangsang116630
 */
public class WebViewFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    protected Activity thisActivity;
    protected View popMaskView;

    // 频道支持H5新增参数
    public static final String KEY_URL = "key_url";

    public static class InputParams {
        protected String url;
        protected boolean isSupportShare;
        protected String title = "";
        protected int from = 0;
        protected boolean isInstallThirdApp;

        public InputParams(String url, boolean isSupportShare, String title, int from, boolean isInstallThirdApp) {
            this.url = url;
            this.isSupportShare = isSupportShare;
            this.title = title;
            this.from = from;
            this.isInstallThirdApp = isInstallThirdApp;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isSupportShare() {
            return isSupportShare;
        }

        public void setIsSupportShare(boolean isSupportShare) {
            this.isSupportShare = isSupportShare;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public boolean isInstallThirdApp() {
            return isInstallThirdApp;
        }

        public void setIsInstallThirdApp(boolean isInstallThirdApp) {
            this.isInstallThirdApp = isInstallThirdApp;
        }
    }

    public void setInputParams(InputParams inputParams) {
        this.inputParams = inputParams;
        // this.inputParams.setUrl("http://qf.56.com/home/indexSohu.h5");
    }

    View.OnClickListener closeButtonListener;

    public void setOnCloseButtonClickListener(View.OnClickListener closeButtonListener) {
        this.closeButtonListener = closeButtonListener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        thisActivity = (Activity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(KEY_URL))) {
            inputParams = new InputParams(getArguments().getString(KEY_URL), false, "", FROM_CHANNEL, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtils.d(TAG, "DetailRelatedListFragment:onViewCreated--");
        super.onViewCreated(view, savedInstanceState);
        if (inputParams == null) {
            // thisActivity.showErrorDialog(R.string.wrong_params);
            return;
        }
        initView(view);
        initListener();
        initViewByData();
        initWebViewDebug();
    }


    private final static String TAG = "WebViewActivity";
    public static String EXTRA_INSTALL_THIRD_APP = "extra_install_third_app";
    public static final String KEY_WEB_RESULT = "web_result_json";

    protected Handler mhandler = new Handler();
    // 来自扫一扫
    public final static int FROM_SCAN_QR_CODE = 1;
    // 来自频道
    public final static int FROM_CHANNEL = 2;

    // 来自应用宝版版有优惠活动
    public final static int FROM_TMASSISTANT = 282; // 282是应用宝的渠道号


    protected VideoEnabledWebView mWebView;
    protected boolean responseAction = false;
    protected InputParams inputParams;
    protected String currentUrl;

    protected String imgUrl;

    protected HashMap<String, String> headers;

    protected static final String URL_ACTION_LOGIN = "action=2.6";
    private static final String URL_ACTION_JUMP = "action=";
    /**
     * 目前响应的action
     */
    protected int currentAction = -1;
    protected static final int ACTION_VALUE_LOGIN = 1;
    private static final int ACTION_VALUE_JUMP = 2;

    protected ProgressVideoEnabledWebChromeClient chromeClient;

    protected final static int REQUESTCODE_FILECHOOSER = 1001;
    protected ValueCallback<Uri> mFileUploadCallback;
    protected ValueCallback<Uri[]> mFilePathCallbackArray;




    /**
     * 标识webview是否已经初始化并且加载当前URL完毕（mInputUrl,currenUrl等）
     */
    private boolean isInitWebViewSettingAndLoadUrl = false;
    private boolean isNeedWaitForReloadOfRetry;


    protected IWebViewFragmentEventListener eventListener;

    public void setEventListener(IWebViewFragmentEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public interface IWebViewFragmentEventListener {
        void onCloseClicked();
    }

    // Fixme 4.4.0~4.4.2系统无法上传图片，系统删除了相关api
    protected class ProgressVideoEnabledWebChromeClient extends VideoEnabledWebChromeClient {

        public ProgressVideoEnabledWebChromeClient(View activityNonVideoView, ViewGroup activityVideoView,
                                                   View loadingView, VideoEnabledWebView webView, TextView titleView) {
            super(activityNonVideoView, activityVideoView, loadingView, webView, titleView);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        // file upload callback (Android 2.2 (API level 8) -- Android 2.3 (API
        // level 10)) (hidden method)
        public void openFileChooser(ValueCallback<Uri> uploadFile) {
            openFileChooser(uploadFile, "");
        }

        // file upload callback (Android 3.0 (API level 11) -- Android 4.0 (AP
        // level 15)) (hidden method)
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
            mFileUploadCallback = uploadFile;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Image Chooser"), REQUESTCODE_FILECHOOSER);
        }

        // file upload callback (Android 4.1 (API level 16) -- Android 4.3 (API
        // level 18)) (hidden method)
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
            openFileChooser(uploadFile, acceptType);
        }

        // For Android >=5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFilePathCallbackArray = filePathCallback;
                try {
                    Intent intent = fileChooserParams.createIntent();
                    startActivityForResult(intent, REQUESTCODE_FILECHOOSER);
                } catch (Exception e) {
                    // TODO: when open file chooser failed
                }
                return true;
            }
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }



    /**
     * 判断是否开启webview调试模式
     */
    protected void initWebViewDebug() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && LogUtils.isDebug()) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    protected void initView(View view) {
        mWebView = (VideoEnabledWebView) view.findViewById(R.id.webView);
    }


    protected void initListener() {
        mWebView.setOnLongClickListener(this);
    }


    protected WebViewClient webViewClient;

    protected void initWebViewClient() {
        webViewClient = new BaseWebViewClient();
    }

    public class BaseWebViewClient extends WebViewClient {
        @Override
        public void onLoadResource(WebView view, String url) {
            // 处理h5支付，用于获取orderId
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            LogUtils.d("WebViewActivity", "onPageFinished, url = " + url);
            if (detectIsNetAvailableAndReturnResponse(url) && inputParams.from == WebViewActivity.VIP_CENTER) {
                isInitWebViewSettingAndLoadUrl = true;
            }
            LogUtils.p("WebViewActivity, onPageFinished, webview title = " + view.getTitle());
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            LogUtils.d("WebViewActivity", "onReceivedError->code:" + errorCode + "->description:" + description
                    + "->failingUrl:" + failingUrl);

            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        // 加载的SSL页面的证书有问题时：弹框提示是否要无视错误继续浏览？
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 测试ar使用
//            url = "sohuvideo://action.cmd?action=3.12&activityID=0&enableScan=1&scanType=o_matchinginfo,m_carlogo.dat&activityPrefix=1200&bucket=ar-image&imageWidth=150&imageHeight=150";
            if (StringUtils.isBlank(url)) {
                return true;
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                // 常规URL
                url = buildUrl(url);// 在URL里面加入参数
                currentUrl = url;
                return super.shouldOverrideUrlLoading(view, url);
            } else {
                // 非常规URL
                // 1.处理h5里边的action
                }
                currentUrl = url;
                return true;
            }
        }


    @SuppressLint("NewApi")
    protected void initWebSetting() {
        if (StringUtils.isNotBlank(inputParams.getUrl()) && URLUtil.isNetworkUrl(inputParams.getUrl())) {
            // 有网
                // 可缩放
                WebSettings settings = mWebView.getSettings();
                settings.setBuiltInZoomControls(false);
                settings.setSupportZoom(true);
                settings.setUseWideViewPort(true);
                // 客串文件
                settings.setAllowFileAccess(true);
                settings.setDomStorageEnabled(true);
                // 应用可以有数据库
                settings.setDatabaseEnabled(true);

                // 应用可以有缓存
                settings.setAppCacheEnabled(true);
                String appCaceDir = thisActivity.getApplicationContext().getDir("cache", Context.MODE_PRIVATE)
                        .getPath();
                settings.setAppCachePath(appCaceDir);

                // 此处不可更改
                settings.setUserAgentString(settings.getUserAgentString() + " SohuVideoMobile/6.5.0"
                        + " (Platform/AndroidPhone;" + " Android/" + Build.VERSION.RELEASE + ")");

                // 可定位
                settings.setGeolocationEnabled(true);

                // 可解析js
                try {
                    settings.setJavaScriptEnabled(true);
                    mWebView.addJavascriptInterface(new SohuJsBridge(), "handler");
                } catch (NullPointerException npe) {
                    // http://bugly.qq.com/detail?app=900006681&pid=1&ii=3005
                    LogUtils.d(TAG, "setJavaScriptEnabled() or addJavascriptInterface() NullPointerException!!!");
                    LogUtils.printStackTrace(npe);
                } catch (Exception e) {
                    LogUtils.printStackTrace(e);
                }

                // 解决6.0手机https的链接图片不加载的问题
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }


                // 展示进度以及连接不成功的提示语
                initWebViewClient();
                mWebView.setWebViewClient(webViewClient);


                //TODO webClient
//                progressWait = new ProgressBar(thisActivity);
//                chromeClient = new ProgressVideoEnabledWebChromeClient(nonVideoLayout, videoLayout, progressWait,
//                        mWebView, mTitleBar.getTitleView());
//                mWebView.setWebChromeClient(chromeClient);

                final Handler handler = new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        if (mWebView != null) {
                            mWebView.loadUrl(inputParams.getUrl(), headers);
                        }
                        return false;
                    }
                });
                Thread temp = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        CookieSyncManager.createInstance(thisActivity);
                        CookieManager cookieManager = CookieManager.getInstance();
                        cookieManager.setAcceptCookie(true);
                        // cookieManager.removeAllCookie();
                        handler.sendEmptyMessageDelayed(0, 50);
                    }
                });
                temp.run();
        } else {// 地址不是http
            closeWebView();
        }
    }

    // 从url中解析出电话号码
    protected String getTeleNumberFromUrl(String url) {
        Pattern pattern = Pattern.compile(WebView.SCHEME_TEL + "[0-9\\-]+");
        Matcher matcher = pattern.matcher(url);
        String tele = "";
        if (matcher.find(0)) {
            tele = matcher.group(0);
        }
        return tele;
    }

    protected String buildUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        url = url.trim();
        if (url.substring(url.length() - 4).equals(".apk")) {
            return url;
        }

        // fyf修改，只对搜狐域名的url加参数

        return url;
    }



    protected void initViewByData() {
        if (inputParams == null) {
            return;
        }
        currentUrl = inputParams.getUrl();

        inputParams.setUrl(buildUrl(inputParams.getUrl()));


        initWebSetting();
        LogUtils.d(TAG, "URL=" + inputParams.getUrl());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (chromeClient != null) {
            chromeClient.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    public void closeWebView() {
        if (eventListener != null) {
            eventListener.onCloseClicked();
        }
    }

    public void destroyWebView() {
        try {
            if (mWebView != null) {
                unregisterForContextMenu(mWebView);
                pauseWebView();
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }
        } catch (Exception e) {
            // ignore
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResume() {
        super.onResume();
        if (inputParams != null) {
            LogUtils.p("fyf", "currentUrl = " + currentUrl + "\nurl = " + inputParams.getUrl());
        }
        if (inputParams.from == WebViewActivity.VIP_CENTER && !isInitWebViewSettingAndLoadUrl) {
            detectIsNetAvailableAndReturnResponse(currentUrl);
        }
        if (responseAction) {
            // yb旧代码
            // initViewByData(getIntent());
            // fyf修改
            responseAction = false;
            switch (currentAction) {
                case ACTION_VALUE_JUMP:
                    LogUtils.p(TAG, "action jump!!   " + "currentUrl = " + currentUrl);
                    currentAction = -1;
                            mWebView.reload();
                            mWebView.onResume();
                    break;
                case ACTION_VALUE_LOGIN: {
                    LogUtils.p(TAG, "action login!!   " + "currentUrl = " + currentUrl);
                    currentAction = -1;
                    break;
                }
                default:
                    break;
            }
        }
        // 解决js不调用的问题
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseWebView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }

    protected void webViewGoBack() {
        if (chromeClient != null && chromeClient.isVideoFullscreen()) {
            chromeClient.onBackPressed();
            return;
        }
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            closeWebView();
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void pauseWebView() {
        if (mWebView != null && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            mWebView.onPause();
        }
    }

    /**
     * js bridge
     */
    class SohuJsBridge {
        @JavascriptInterface
        public void toast(String text) {
            if (StringUtils.isNotBlank(text)) {
//                ToastUtils.ToastShort(getContext(), text);
            }
        }

        // api17以上必须要加此注解，否则js无法回调到这里
        @JavascriptInterface
        public void receive(final String h5PicUrl, final String h5ShareUrl, final String h5Title, final String h5Desc) {

        }

        // api17以上必须要加此注解，否则js无法回调到这里
        @JavascriptInterface
        public void appCallback(final int act, final int status, final String jsonDataString) {
            LogUtils.p("fyf----------------appCallback收到h5发来的信息act = " + act + ", status = " + status
                    + ", jsonDataString = " + jsonDataString);
            switch (act) {
            // 只处理需要在当前页面响应的信息
                default:
                    break;
            }

        }

        /**
         * H5页面js回调函数：http://mwiki.sohuno.com/pages/viewpage.action?pageId=
         * 18156370 note:js回调中请勿使用内部类，混淆会有问题 by jayyang
         *
         * @param jsonData
         */
        // api17以上必须要加此注解，否则js无法回调到这里
        @JavascriptInterface
        public void sohuAppCallback(String jsonData) {
        }

        @JavascriptInterface
        public void appUpdatePhone(String phoneNum) {
            LogUtils.p(TAG, "appUpdatePhone() done");
        }

        // 安卓回传用户绑定关系
        // window.handler.appUpdateBind('1');
        @JavascriptInterface
        public void appUpdateBind(String action) {
            LogUtils.p(TAG, "appUpdateBind() done");

        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_FILECHOOSER:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (mFilePathCallbackArray == null) {
                        super.onActivityResult(requestCode, resultCode, data);
                        return;
                    }
                    mFilePathCallbackArray.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,
                            data));
                    mFilePathCallbackArray = null;
                } else {
                    if (mFileUploadCallback == null) {
                        super.onActivityResult(requestCode, resultCode, data);
                        return;
                    }
                    Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
                    mFileUploadCallback.onReceiveValue(result);
                    mFileUploadCallback = null;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 返回键处理
     *
     * @return
     */
    public boolean onBackPressed() {

        if (chromeClient != null && chromeClient.isVideoFullscreen()) {
            chromeClient.onBackPressed();
            return true;
        }

        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            closeWebView();
            return false;
        }

    }





    /**
     * 影院H5调用的js方法 参数和主线略有不同
     */

    /**
     * 侦测目前网络状态，并进行有网或者无网的处理
     *
     * @param url
     * @return
     */
    private boolean detectIsNetAvailableAndReturnResponse(String url) {
            return true;
    }

    protected MainActivity getMainActivity() {
        if (null != thisActivity && thisActivity.getParent() instanceof MainActivity) {
            return (MainActivity) (thisActivity.getParent());
        }
        return null;
    }

    public void redirectLoad(String url) {
        mWebView.loadUrl(url);
    }

}
