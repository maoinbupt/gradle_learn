package cn.bupt.newproject0819.tools;

import com.common.sdk.net.connect.http.NetworkResponseEx;
import com.common.sdk.net.connect.interfaces.IResultParserEx;

import cn.bupt.newproject0819.model.AbstractBaseModel;

public class DefaultResultParser implements IResultParserEx {

    Class<? extends AbstractBaseModel> cls;

    public DefaultResultParser(Class<? extends AbstractBaseModel> cls) {
        this.cls = cls;
    }

    @Override
    public Object parse(NetworkResponseEx response, String responseBody) throws Exception {
        Object data = DataParseUtils.parseCommonContent(cls, responseBody);
        return data;
    }

}
