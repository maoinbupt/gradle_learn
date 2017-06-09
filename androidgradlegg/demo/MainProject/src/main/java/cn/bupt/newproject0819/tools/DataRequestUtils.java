package cn.bupt.newproject0819.tools;

import com.common.sdk.net.connect.http.DaylilyRequest;

import cn.bupt.newproject0819.StringUtils;

/**
 * Created by GaoFeng on 2017.03.21.
 */

public class DataRequestUtils {

    private static final String GET_GAME_FEED_LIST = "/gamefeed.php?type=0&name=&tag=-1&orderby=new&pubid=undefined";
    /**
     * params
     */
    private static final String FILE_SEPARATOR = "/";

    public static String combineRequestUrl(String host, String method) {
        if (StringUtils.isNotBlank(host)) {
            if (host.endsWith(FILE_SEPARATOR)) {
                host = host.substring(0, host.length() - 1);
            }
        }
        if (StringUtils.isNotBlank(method)) {
            if (!method.startsWith(FILE_SEPARATOR)) {
                method = FILE_SEPARATOR + method;
            }
        }
        return host + method;
    }

    public static String combineRequestUrlWithParam(String host, String method, long param) {
        String url = String.format(combineRequestUrl(host, method), param);
        return url;
    }

    public static DaylilyRequest getGameFeedList(int type,String name,int tag,String orderby,String pubid) {
        String url = combineRequestUrl(Domains.getFormalGameFeed(), GET_GAME_FEED_LIST);
        DaylilyRequest request = new DaylilyRequest(url, DaylilyRequest.Method.GET);
        request.addQueryParam("type", type);
        request.addQueryParam("name", name);
        request.addQueryParam("tag", tag);
        request.addQueryParam("orderby",orderby);
        request.addQueryParam("pubid", pubid);
        return request;
    }
}
