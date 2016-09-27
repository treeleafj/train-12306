package org.leaf.train.utils;

import org.apache.http.client.HttpClient;
import org.treeleaf.common.http.httpclient.Get;

/**
 * @author yaoshuhong
 * @date 2016-09-27 16:37
 */
public class PostEx extends Get {

    public PostEx(String address) {
        super(address);
    }

    @Override
    protected HttpClient buildHttpClient() throws Exception {
        return HttpClientFactory.get();
    }
}
