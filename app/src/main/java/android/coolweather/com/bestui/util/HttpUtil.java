package android.coolweather.com.bestui.util;

import android.coolweather.com.bestui.JavaBean.Produce;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/29.
 */

public class HttpUtil {

    public static final String urlImage = "http://118.89.165.181:8080/CaiDaoJia/images/";
    public static final String urlProduce = "http://118.89.165.181:8080/CaiDaoJia/produceJSON";
    public static final String urlOrderAddress = "http://118.89.165.181:8080/CaiDaoJia/orderAddress";
    public static final String urlOrderProduces = "http://118.89.165.181:8080/CaiDaoJia/orderProduces";
    public static final String urlVerify = "http://118.89.165.181:8080/CaiDaoJia/verify";
    public static final String urlRegister = "http://118.89.165.181:8080/CaiDaoJia/register";
    /**发送请求**/
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**处理产品请求**/
    public static void handleProduceResponse(String response) {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(response).getAsJsonArray();
        Gson gson = new Gson();
        for (JsonElement element : jsonArray) {
           Produce produce = gson.fromJson(element, Produce.class);
            produce.save();
        }
    }

    public static void sendOKHttpPost(String json, String url, okhttp3.Callback callback) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void verifyOKHttp(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void registerOKHttp(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
