package cn.hiboot.framework.research.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * 各种第三方http工具试用
 */
public class HttpDemo {


    @Test
    public void okHttp() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://raw.github.com/square/okhttp/master/README.md")
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }

    }

}
