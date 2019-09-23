package com.ly.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.util.Log;

import com.ly.utils.common.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by seatrend on 2018/8/28.
 */

public class BitmapUtils {

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 10;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,options);
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while ( baos.toByteArray().length / 1024>300) { //循环判断如果压缩后图片是否大于50kb,大于继续压缩
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h = (int) (newHeight * densityMultiplier);
        int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

        photo = Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public static String saveBitmap(Bitmap bitmap, String fileName){

        File catalog = new File( Constants.IMAGE_PATH);
        if (!catalog.exists()) {
            catalog.mkdirs();
        }

        File file=new File(catalog,fileName+".jpg");
        if(file.exists()){
            file.delete();
        }
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            return file.getPath();
        } catch (Exception e) {
            return  null;
        }finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getSmallBitmap(String filePath) {
        Bitmap bitmap;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565; ///为了兼容人脸比对 这里选择用 ARGB_8888 格式
        options.inSampleSize = calculateInSampleSize(options, 1280, 960);
        options.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            options.inSampleSize = calculateInSampleSize(options, 480, 320);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(filePath, options);
            Log.d("getSmallBitmap","Exception= "+e.getMessage());
        }

        return bitmap;
    }
    public static Bitmap getSmallSquareBitmap(String filePath) {
        Bitmap bitmap;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = calculateInSampleSize(options, 600, 600);
        options.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            options.inSampleSize = calculateInSampleSize(options, 480, 320);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(filePath, options);
            Log.d("getSmallBitmap","Exception= "+e.getMessage());
        }

        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
        /**创建一个和原始图片一样大小位图*/
        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        /**创建带有位图roundConcerImage的画布*/
        Canvas canvas = new Canvas(roundConcerImage);
        /**创建画笔  */
        Paint paint = new Paint();
        /**创建一个和原始图片一样大小的矩形*/
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        /**去锯齿*/
        paint.setAntiAlias(true);
        /**画一个和原始图片一样大小的圆角矩形*/
        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
        /**设置相交模式  */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**把图片画到矩形去  */
        canvas.drawBitmap(bitmap, rect, rectF, paint);


        /**引时圆角区域为透明，给其填充白色  */
        paint.setColor(Color.WHITE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        return roundConcerImage;
    }


    public static String getTwoInOneBitmap(String path1, String path2){

        try {
            Bitmap bitmap1 =getSmallSquareBitmap(path1);
            Bitmap bitmap2 = getSmallSquareBitmap(path2);
            Bitmap mergeBitmap = mergeBitmap(bitmap1, bitmap2);
            String idcardPositiveAndNegative = saveBitmap(mergeBitmap, System.currentTimeMillis()+"");
            if (bitmap1!=null){
                bitmap1.recycle();
            }
            if (bitmap2!=null){
                bitmap2.recycle();
            }
            if (mergeBitmap!=null){
                mergeBitmap.recycle();
            }
            return idcardPositiveAndNegative;
        }catch (Exception | OutOfMemoryError e){
            return "";
        }

    }
    /*public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }*/

    /*public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth()+secondBitmap.getWidth(),
                firstBitmap.getHeight(),firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, firstBitmap.getWidth(), 0, null);
        return bitmap;
    }*/

    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        int firstBitmapWidth = firstBitmap.getWidth();
        int secondBitmapWidth = secondBitmap.getWidth();

        double b;
        if (firstBitmapWidth>secondBitmapWidth){
            b=(double)firstBitmapWidth/secondBitmapWidth;
            secondBitmap=zoomImg(secondBitmap, (int) (secondBitmap.getWidth()*b), (int) (secondBitmap.getHeight()*b));
        }else if (firstBitmapWidth<secondBitmapWidth){
            b=(double)secondBitmapWidth/firstBitmapWidth;
            firstBitmap=zoomImg(firstBitmap, (int) (firstBitmap.getWidth()*b), (int) (firstBitmap.getHeight()*b));

        }

        int newWidth = firstBitmap.getWidth() > secondBitmap.getWidth() ? firstBitmap.getWidth() : secondBitmap.getWidth();
        int newHeight = firstBitmap.getHeight() + secondBitmap.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight,firstBitmap.getConfig());
        //setBitmapBGColor(bitmap,Color.WHITE);
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(firstBitmap, 0, 0, null);
        canvas.drawBitmap(secondBitmap, 0, firstBitmap.getHeight(), null);
        return bitmap;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
    public static void setBitmapBGColor(Bitmap bitmap, int color){
         for(int i=0;i<bitmap.getWidth();i++){
             for(int j=0;j<bitmap.getHeight();j++){
                  bitmap.setPixel(i,j,color);//将bitmap的每个像素点都设置成相应的颜色
                }
              }
         }
}
