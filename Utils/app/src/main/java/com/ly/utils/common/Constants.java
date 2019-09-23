package com.ly.utils.common;

import android.os.Environment;

/**
 * Created by ly on 2019/9/23 14:07
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public interface Constants {
     String IMAGE_FILE="VehicleData";//文件夹
     String FILE="VehicleFile";//文件夹
     String IMAGE_PATH= Environment.getExternalStorageDirectory().getPath() + "/"+IMAGE_FILE;//照片的路径
     String FILE_PATH= Environment.getExternalStorageDirectory().getPath() + "/"+FILE;//文件的路径

     String IP_K ="ip";
     String PORT_K ="port";
     String SETTING="setting";
     static String QAUTH ="Authorization";

     String DOWNLOAD_FILE_BY_ID="*****";
}
