package org.leaf.train.utils;

import org.apache.http.client.HttpClient;
import org.treeleaf.common.http.httpclient.Get;

/**
 * @author yaoshuhong
 * @date 2016-09-27 16:37
 */
public class GetEx extends Get {
    /**
     * 构建Get请求对象
     *
     * @param address 请求地址,可以是http或者https
     */
    public GetEx(String address) {
        super(address);
    }

    @Override
    protected HttpClient buildHttpClient() throws Exception {
        return HttpClientFactory.get();
    }

}
