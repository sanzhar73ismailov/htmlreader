package com.peer39.htmlreader.pkg;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import static java.lang.String.format;

public class PageReader {


    public List<Result> readPages(List<String> urls) {
        List<Result> results = new ArrayList<>();
        CredentialsProvider provider = new BasicCredentialsProvider();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            Result result = Result.builder()
                    .url(url)
                    .text("")
                    .success(false)
                    .build();
            results.add(result);
            try {
                HttpGet request = new HttpGet(url);
                CloseableHttpResponse response = httpClient.execute(request);
                final int statusCode = response.getStatusLine().getStatusCode();
                result.setResponseStatus(statusCode);
                if (statusCode == HttpStatus.SC_OK) {
                    result.setSuccess(true);
                    String content = EntityUtils.toString(response.getEntity());
                    result.setText(Util.extractText(content));
                } else {
                    result.setErrorMessage(response.getStatusLine().toString());
                }
            } catch (Exception e) {
                result.setErrorMessage(format("Exception %s, message: %s", e.getClass().getSimpleName(), e.getMessage()));
                e.printStackTrace();
            }
        }
        return results;
    }

}
