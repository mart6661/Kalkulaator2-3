package com.sql.mart.sqlprojekt2and3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Mart on 5/16/2016.
 */
public class Logic extends BroadcastReceiver {
    private UOW uow;

    @Override
    public void onReceive(Context context, Intent intent) {
        uow = new UOW(context);

        if (isOrderedBroadcast()) {
        Bundle extras = intent.getExtras();
        if(extras!= null){
            String sign = extras.getString("sign");
            double number1 = extras.getDouble("number1");
            double number2 = extras.getDouble("number2");
            double value;
            if (sign.contains("+")) {
                setResultCode(Activity.RESULT_OK);
                value = number1+number2;
                setResultData(String.valueOf(value));
                uow.addToDatabase(number1, number2, sign, value);
            } else if (sign.contains("-")) {
                setResultCode(Activity.RESULT_OK);
                value = number1-number2;
                setResultData(String.valueOf(value));
                uow.addToDatabase(number1, number2, sign, value);
            } else if (sign.contains("*")) {
                setResultCode(Activity.RESULT_OK);
                value = number1*number2;
                setResultData(String.valueOf(value));
                uow.addToDatabase(number1, number2, sign, value);

            } else if (sign.contains("/")) {
                if (number2 == 0) {
                    // Cannot Divide By Zero

                } else {
                    setResultCode(Activity.RESULT_OK);
                    value = number1 / number2;
                    setResultData(String.valueOf(value));
                    uow.addToDatabase(number1, number2, sign, value);

                }
            }

            }

        }

    }
}