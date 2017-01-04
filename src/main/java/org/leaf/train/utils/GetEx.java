package org.leaf.train.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.treeleaf.common.http.httpclient.Get;

import java.util.Map;

/**
 * @author leaf
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
        this.connectTimeout(500);
        this.readTimeout(1000);
    }

    @Override
    protected HttpClient buildHttpClient() throws Exception {
        HttpClient httpClient = HttpClientFactory.get();
        return httpClient;
    }

    protected HttpUriRequest buildHttpUriRequest(RequestBuilder builder) {
        RequestBuilder requestBuilder = builder.setUri(this.address);

        if (this.param != null && !this.param.isEmpty()) {
            for (Map.Entry<String, String> entry : this.param.entrySet()) {
                requestBuilder.addParameter(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        if (this.header != null) {
            for (String name : this.header) {
                requestBuilder.addHeader(name, this.header.getHeader(name));
            }
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(this.connectTimeout)
                .setConnectTimeout(this.connectTimeout)
                .setConnectionRequestTimeout(this.readTimeout)
                .build();

        requestBuilder.setConfig(requestConfig);

        return requestBuilder.build();
    }

}
