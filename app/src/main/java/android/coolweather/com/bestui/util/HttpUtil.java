package android.coolweather.com.bestui.util;

import android.coolweather.com.bestui.JavaBean.Produce;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/4/29.
 */

public class HttpUtil {

    public static final String urlImage = "http://192.168.191.1:8080/CaiDaoJia/images/";
    public static final String urlProduce = "http://192.168.191.1:8080/CaiDaoJia/produceJSON";

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
}
