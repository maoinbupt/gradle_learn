/*
 * RemoteException.java
 * classes : com.sohu.sohuvideo.control.exception.RemoteException
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-03-21 下午5:38:14
 */
package cn.bupt.newproject0819.model;

import android.text.TextUtils;

/**
 * com.sohu.sohuvideo.control.exception.RemoteException
 *
 * @author 田翔宇 <br/>
 *         服务器response的错误信息 create at 2014-03-21 下午5:38:14
 */
public class RemoteException extends Exception {
    /**
     * 序列号ID
     */
    private static final long serialVersionUID = -1460894893738016580L;

    /**
     * 错误代码
     */
    private int mErrorCode;
    private String mMemoErrorCode;

    public RemoteException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    public RemoteException(String errorCode, String errorMessage) {
        super(errorMessage);
        mMemoErrorCode = errorCode;
    }

    /**
     * @return int 服务器返回的错误代码
     */
    public int getErrorCode() {
        return mErrorCode;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("statusCode = ");
        sb.append(TextUtils.isEmpty(mMemoErrorCode) ? mErrorCode : mMemoErrorCode);
        sb.append(" ");
        sb.append(super.getMessage());
        return sb.toString();
    }
}
