package com.ly.utils.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Zhangyue on 2017/6/5.
 * 从相册或相机 选择图片的帮助类
 */

public class ChoiceFromAlbum {
    private static Bitmap bitmap;
    private final  static int NEW_HIGHT=50;//图片的高度
    public static Bitmap getBitmapFromAlbum(Intent data, Context context){
        String imagePath;
        if(Build.VERSION.SDK_INT >=19){//判断手机版本
             imagePath= handleImageOnKitKat(data, context);
        }else {

            imagePath = handleImageBeforKitKat(data, context);
        }
        if(imagePath != null){
            bitmap=getBitmapImage(imagePath);
            return bitmap;
        }
        return null;
    }
    public static Bitmap getBitmapFromCamera(Context context, Uri imagePath){
        File file = new File(String.valueOf(imagePath));
        try {

            Bitmap bitmap= BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imagePath));
            //这里通过方法将bitmap 进行了处理，因为上面得到的Bitmap可能比较大，防止OOM
            //Bitmap scaleBitmap = BitmapUtils.scaleDownBitmap(bitmap, NEW_HIGHT, context);
            /*if(scaleBitmap != null) {
                return scaleBitmap;
            }*/
            return bitmap;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
        return null;
    }



    private static String handleImageBeforKitKat(Intent data, Context context) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null,context);

        return imagePath;
    }
    private static Bitmap getBitmapImage(String imagePath) {
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            return bitmap;
        }
        return null;

    }
    private static String handleImageOnKitKat(Intent data, Context context) {
        String ImagePath=null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(context,uri)){//如果是document 类型的URI，则通过document id处理
            String documentId = DocumentsContract.getDocumentId(uri);

            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = documentId.split(":")[1]; //解析数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                ImagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection,context);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri withAppendedId = ContentUris.withAppendedId(uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                ImagePath=getImagePath(withAppendedId,null,context);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){//如果是content的URI  则通过普通方式处理
            ImagePath=getImagePath(uri,null,context);
        }else if("file".equalsIgnoreCase(uri.getScheme())){//如果是 file 类型的URI 直接获取图片路劲即可
            ImagePath=uri.getPath();
        }
        return ImagePath;
    }
    private static String getImagePath(Uri uri, String selection, Context context) {
        String path=null;
        Cursor cursor =context.getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}

