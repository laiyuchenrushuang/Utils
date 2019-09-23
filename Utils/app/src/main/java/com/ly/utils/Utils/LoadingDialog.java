package com.ly.utils.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.ly.utils.R;

/**
 * Created by seatrend on 2018/8/28.
 * */



public class LoadingDialog {


    private static LoadingDialog mLoadingDialog;
    private LoadingDialog(){};
    private Dialog mDialog;

    public static LoadingDialog getInstance(){
        if(mLoadingDialog==null){
            mLoadingDialog=new LoadingDialog();
        }
        return mLoadingDialog;
    }



    public  void showLoadDialog(Context context){
        mDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_animation, null);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public  void dismissLoadDialog(){
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }
}
