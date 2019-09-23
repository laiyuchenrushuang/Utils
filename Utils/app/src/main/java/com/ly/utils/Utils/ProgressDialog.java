package com.ly.utils.Utils;

import android.app.Dialog;
import android.content.Context;

import com.ly.utils.R;

/**
 * Created by seatrend on 2018/12/26.
 */

public class ProgressDialog {





    public static Dialog getProgressDialog(Context context){

        try{
            final Dialog mDialog = new Dialog(context);
            mDialog.setContentView(R.layout.dialog_progress);
            mDialog.setCanceledOnTouchOutside(false);
            return mDialog;
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }

    }



}
