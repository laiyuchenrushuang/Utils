package com.ly.utils.Utils;


import android.hardware.Camera.Size;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyCamPara {
    private static final String tag = "MyCamPara";
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();
    private static MyCamPara myCamPara = null;

    private MyCamPara() {

    }

    public static MyCamPara getInstance() {
        if (myCamPara == null) {
            myCamPara = new MyCamPara();
            return myCamPara;
        } else {
            return myCamPara;
        }
    }

    public Size getPreviewSize(List<Size> list, int th) {
        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Size s : list) {
            if ((s.width > th) && (equalRate(s, 1.33f))) {
                Log.i(tag, "the last size:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if (i > (list.size() - 1)) {
            th = 600;
            i = 0;
            for (Size s : list) {
                if ((s.width > th) && (equalRate(s, 1.33f))) {
                    Log.i(tag, "the last size:w = " + s.width + "h = " + s.height);
                    break;
                }
                i++;
            }
        }
        return list.get(i);
    }

   /* public Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Size result = null;
        float dr = Float.MAX_VALUE;
        float ratio = (float) width / (float) height;
        for (Size size : parameters.getSupportedPreviewSizes()) {
            float r = (float) size.width / (float) size.height;
            if (Math.abs(r - ratio) < dr && size.width <= width && size.height <= height) {
                dr = Math.abs(r - ratio);
                result = size;
            }
        }
        return result;
    }*/

    public Size getPictureSize(List<Size> list, int th, float rate) {
        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Size s : list) {
            if ((s.width > th) && equalRate(s, rate)) {
                Log.i(tag, "the last pice size:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        if (i==list.size()){
            return list.get(list.size()-1);
        }else return list.get(i);

    }

    private boolean equalRate(Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
            return Math.abs(r - rate) <= 0.2;
    }


    private class CameraSizeComparator implements Comparator<Size> {
        public int compare(Size lhs, Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
