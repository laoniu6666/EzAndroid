package com.laoniu.ezandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.laoniu.ezandroid.model.Now_players_info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnImageUtils {

    int hasLoad = 0;
    WKCallback wkCallback;

    public void LoadImgs(Context mContext, String[] url, Handler handler, WKCallback wkCallback) {
        this.wkCallback = wkCallback;
        Map<String, Bitmap> bitmaps = new HashMap<>(16);
        for (int i = 0; i < url.length; i++) {
            String imageUrl = url[i];
            Glide.with(mContext)
                    .asBitmap()
                    .load(url[i])
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            bitmaps.put(imageUrl, resource);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    hasLoad++;
                                    if (hasLoad == url.length) {
                                        wkCallback.onCall(bitmaps);
                                    }
                                }
                            });
                            return true;
                        }
                    }).preload();
        }

    }

    public static String[] getImgs(List<Now_players_info> playerList) {
        String[] imgs = new String[playerList.size()];
        for (int i = 0; i < playerList.size(); i++) {
            imgs[i] = playerList.get(i).avator;
        }
        return imgs;
    }

    public static Bitmap circleBitmap(Bitmap result, int mBorderWidth) {
        //获取Bitmap的宽度
        int width = result.getWidth();
        int size = (int) (width - (mBorderWidth / 2));
        int x = (width - size) / 2;
        int y = (width - size) / 2;
        Bitmap squared = Bitmap.createBitmap(result, x, y, size, size);
        result = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        //圆画笔
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        //边框画笔
        Paint mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        float borderRadius = r - mBorderWidth / 2;
        canvas.drawCircle(r, r, borderRadius, mBorderPaint);

        return result;
    }

    public static Bitmap rotateScaleBtimap(Bitmap bitmap, int angle, float newWidth) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        int width = bitmap.getWidth();
        float scale = newWidth / width;
        matrix.postScale(scale, scale);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, width, matrix, false);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return newBmp;
    }

}
