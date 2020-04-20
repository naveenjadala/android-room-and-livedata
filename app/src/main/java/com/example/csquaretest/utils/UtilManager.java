package com.example.csquaretest.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.SyncStateContract;

import com.example.csquaretest.MainActivity;
import com.example.csquaretest.model.ErrorVO;

public class UtilManager extends SyncStateContract.Constants {

    public static void clearStack(Activity activity) {
        Intent intent = MainActivity.build(activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static String showErrorMessage(Object obj, Activity activity) {
         if(obj != null) {
             try {
                 ErrorVO errorVO = (ErrorVO)obj;
                 if(errorVO.getError() != null) {
                     return errorVO.getError();
                 }
             } catch (Exception e) {
                 return "Somthing went wrong";
             }
         }
         return "";
    }
}
