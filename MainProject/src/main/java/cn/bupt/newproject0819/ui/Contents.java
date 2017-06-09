package cn.bupt.newproject0819.ui;

/**
 * @author zhaowenzhuo
 *         <p/>
 *         Card内逐项Item属性对象
 * @category App-contents---app详情对象
 * @category Pic-contents---轮播图对象
 * @category Gruop-contents---分类对象
 */
public class Contents {

    /**
     * 通用部分
     */
    /* icon地址 */
    private String icon_url;
    /* 动作行为类型 */
    private int action_type;
    /* 给主线的行为值 */
    private String action_url;
    /* 行为值 */
    private String action_value;
    /* 是否是最后一个位置 */
    private boolean isLastPosition;

    /**
     * 非通用部分
     */
	/*
	 * App相关
	 */
	/* AppId */
    private int app_id;
    /* app简介 */
    private String desc;
    /* app名称 */
    private String app_name;
    /* app类型 */
    private int app_type;
    /* app下载地址 */
    private String download_url;
    /* app下载地址 */
    private String size;
    /* app包名 */
    private String package_name;
    /* app版本名称 */
    private String version_name;
    /* app版本号 */
    private long version_code;
    /* 角标信息 */
    private String corner_mark;
    /* 角标路径 */
    private String corner_path;
    /* apk包检验码 */
    private String verify_code;
    /* 下载次数 */
    private long total_downloads;
    /* 是否在下载中 */
    private boolean isDownLoading;
    /* 下载进度 */
    private int progress;
    /* 礼包个数 */
    private int gift_count;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isDownLoading() {
        return isDownLoading;
    }

    public void setDownLoading(boolean isDownLoading) {
        this.isDownLoading = isDownLoading;
    }

    /**
     * 焦点图专用
     */
	/* picture专用 */
    private String picture_url;
    /* 焦点图名称 */
    private String title;

    public String getCorner_path() {
        return corner_path;
    }

    public void setCorner_path(String corner_path) {
        this.corner_path = corner_path;
    }

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public boolean isLastPosition() {
        return isLastPosition;
    }

    public void setLastPosition(boolean isLastPosition) {
        this.isLastPosition = isLastPosition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public String getAction_value() {
        return action_value;
    }

    public void setAction_value(String action_value) {
        this.action_value = action_value;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getApp_type() {
        return app_type;
    }

    public void setApp_type(int app_type) {
        this.app_type = app_type;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public long getVersion_code() {
        return version_code;
    }

    public void setVersion_code(long version_code) {
        this.version_code = version_code;
    }

    public String getCorner_mark() {
        return corner_mark;
    }

    public void setCorner_mark(String corner_mark) {
        this.corner_mark = corner_mark;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTotal_downloads() {
        return total_downloads;
    }


    public void setTotal_downloads(long total_downloads) {
        this.total_downloads = total_downloads;
    }

    public int getGift_count() {
        return gift_count;
    }

    public void setGift_count(int gift_count) {
        this.gift_count = gift_count;
    }

}
