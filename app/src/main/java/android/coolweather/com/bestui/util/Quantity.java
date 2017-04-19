package android.coolweather.com.bestui.util;

import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/17.
 */

public class Quantity {
    /**
     * 数量加
     */
    public static void addDispose(TextView quantityText) {
        double number = Double.valueOf(quantityText.getText().toString());
        number += 0.5;
        quantityText.setText(String.valueOf(number));
    }

    /**
     * 数量减
     */
    public static void minusDispose(TextView quantityText) {
        double number = Double.valueOf(quantityText.getText().toString());
        number -= 0.5;
        if(number <= 0) {
            quantityText.setText("0");
        } else {
            quantityText.setText(String.valueOf(number));
        }
    }
}
