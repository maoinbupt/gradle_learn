package cn.bupt.newproject0819.tools;


import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import cn.bupt.newproject0819.model.AbstractBaseModel;
import cn.bupt.newproject0819.model.AbstractModel;
import cn.bupt.newproject0819.model.AbstractStatusModel;
import cn.bupt.newproject0819.model.RemoteException;

public final class DataParseUtils {

    public static <T extends AbstractBaseModel> T parseCommonContent(Class<T> cls, String content)
            throws JSONException, RemoteException {
        T response = parseCommonContentNoCheckStatus(cls, content);

        // TODO status == 1 时未确认是否一定代表为正常状态
//        if (response.getStatus() != 200 && response.getStatus() != 1) {
//            throw new RemoteException(response.getStatus(), null);
//        }

        return response;
    }

    public static <T extends AbstractBaseModel> T parseCommonContentNoCheckStatus(Class<T> cls, String content)
            throws JSONException, RemoteException {
        final T response;
        try {
            response = new Gson().fromJson(content, cls);
        } catch (JsonSyntaxException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonIOException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonParseException e) {
            throw new JSONException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JSONException(e.getMessage());
        }

        if (response == null) {
            throw new JSONException("JsonParser result is null.");
        }

        return response;
    }

    public static <T extends AbstractStatusModel> T parseCommonContentNoStatusText(Class<T> cls, String content)
            throws JSONException, RemoteException {
        final T response;
        try {
            response = new Gson().fromJson(content, cls);
        } catch (JsonSyntaxException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonIOException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonParseException e) {
            throw new JSONException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JSONException(e.getMessage());
        }

        if (response == null) {
            throw new JSONException("JsonParser result is null.");
        }

        // TODO status == 1 时未确认是否一定代表为正常状态
//        if (response.getStatus() != 200 && response.getStatus() != 1) {
//            throw new RemoteException(response.getStatus(), null);
//        }

        return response;
    }

    public static <T extends AbstractModel> T parseCommonContentNoStatus(Class<T> cls, String content)
            throws JSONException, RemoteException {
        final T response;
        try {
            response = new Gson().fromJson(content, cls);
        } catch (JsonSyntaxException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonIOException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonParseException e) {
            throw new JSONException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JSONException(e.getMessage());
        }

        if (response == null) {
            throw new JSONException("JsonParser result is null.");
        }

        return response;
    }

}
