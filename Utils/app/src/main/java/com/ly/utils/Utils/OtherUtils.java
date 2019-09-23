package com.ly.utils.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by seatrend on 2018/12/3.
 */

public class OtherUtils {


    /**
     * 设置NumberPicker分割线颜色
     *
     * @param numberPicker：NumberPicker
     * @param color：int
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field SelectionDividerField : pickerFields) {
            if (SelectionDividerField.getName().equals("mSelectionDivider")) {
                SelectionDividerField.setAccessible(true);
                try {
                    SelectionDividerField.set(numberPicker, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static String getSystemProperty() {
        // name  pwv.custom.custom                          HC
//        try {
//            final Class systemPropertyClass = Class.forName("android.os.SystemProperties");
//            final Method getMethod = systemPropertyClass.getDeclaredMethod("get", String.class, String.class);
//            return (String) getMethod.invoke(null, "pwv.custom.custom ", "M");
//        } catch (Exception e) {
//            return "M";
//        }

        try {
            Class build = Class.forName("android.os.Build");
            String customName = (String)build.getDeclaredField("PWV_CUSTOM_CUSTOM").get(null);
            return customName;
        } catch (Exception e) {
            e.printStackTrace();
            return "M";
        }



    }
    /**
     * spinner 设置为指定数据
     * @param dmz
     * @param spinner
     */
    public static void setSpinnerToDmz(String dmz, Spinner spinner){
        if(TextUtils.isEmpty(dmz)){return;}
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i <count; i++) {
            String item = adapter.getItem(i);
            String adapterDmz = item.split(":")[0];
            if(dmz.equals(adapterDmz)){
                spinner.setSelection(i);
                break;
            }
        }

    }

    public static void goFaceComparePlugin(Activity activity, byte[] photo, int code) {
        Toast.makeText(activity,"正在开起人脸识别，请稍后...", Toast.LENGTH_SHORT).show();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setComponent(new ComponentName("com.seatrend.cd.hongruanfacecompare", "com.seatrend.cd.hongruanfacecompare.MainActivity"));
            intent.putExtra("photo",photo);
            activity.startActivityForResult(intent, code);
        } catch (Exception e) {
            Toast.makeText(activity,"未找到人脸识别插件，请先安装插件", Toast.LENGTH_SHORT).show();

        }

    }


    /**
     * 获取省份的简称
     * @return
     */
    public static char[] getProvince(){

        char[] jc={'京','津','冀','晋','蒙','辽','吉','黑','沪','苏','浙','皖','闽','赣','鲁',
                '豫','鄂','湘','粤','桂','琼','渝','川','黔','滇','藏','陕','甘','青','宁','新','台','港','澳'};

        return jc;
    }

    /**
     * 删除某个文件夹下 的照片
     * @param path
     */
    public static void deleteFileChild(String path){
        try {
            File catalog = new File(path);
            if(catalog.exists()){
                File[] files = catalog.listFiles();
                for (File f : files) {
                    if(f.getName().contains(".jpg")){
                        f.delete();
                    }
                }
            }
        }catch ( Exception e){
            e.printStackTrace();
        }
    }

    public static void toVin(Activity activity, int requestCode_vin) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setComponent(new ComponentName("com.seatrend.vin.app", "com.seatrend.vin.app.RequestAction"));
            activity.startActivityForResult(intent, requestCode_vin);
        }catch ( Exception io){
            Toast.makeText(activity,"请先安装VIN插件", Toast.LENGTH_SHORT).show();
        }
    }
}
