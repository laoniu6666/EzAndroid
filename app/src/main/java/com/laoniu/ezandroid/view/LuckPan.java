package com.laoniu.ezandroid.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.model.Now_players_info;
import com.laoniu.ezandroid.utils.WKCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zwk
 * @CreateDate: 2020/7/8 15:50
 */

public class LuckPan extends View {
    private long rotateTime = 3000l;//旋转时间

    private Paint mPaintArc;//转盘扇形画笔
    private Paint mPaintItemStr;//转盘文字画笔
    private float mRadius;//圆盘的半径
    private RectF rectFPan;//构建转盘的矩形
    private RectF rectFStr;//构建文字圆盘的矩形
    private List<Now_players_info> mItemStrs=new ArrayList<>();
    private ArrayList<Path> mArcPaths;
    private float mItemAnge;
    private int mRepeatCount = 4;//转几圈
    private int mLuckNum = 2;//最终停止的位置
    private float mStartAngle = 0;//存储圆盘开始的位置
    private float mOffsetAngle = 0;//圆盘偏移角度（当Item数量为4的倍数的时候）
    private float mTextSize = 15;//文字大小
    private ObjectAnimator objectAnimator;
    private WKCallback WKCallback;

    int[] itemColor = new int[]{
            R.color.turntable_item01,
            R.color.turntable_item02,
            R.color.turntable_item03,
            R.color.turntable_item04,
            R.color.turntable_item05,
            R.color.turntable_item06,
            R.color.turntable_item07,
            R.color.turntable_item08,
            R.color.turntable_item09,
            R.color.turntable_item10
    };


    public WKCallback getWKCallback() {
        return WKCallback;
    }

    public void setWKCallback(WKCallback WKCallback) {
        this.WKCallback = WKCallback;
    }

    public LuckPan(Context context) {
        this(context,null);
    }

    public LuckPan(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckPan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintArc.setStyle(Paint.Style.FILL);

        mPaintItemStr = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintItemStr.setColor(Color.parseColor("#ED2F2F"));
        mPaintItemStr.setStrokeWidth(3);
        mPaintItemStr.setTextAlign(Paint.Align.CENTER);

        mArcPaths = new ArrayList<>();
    }

    /**
     * 设置转盘数据
     * @param items
     */
    public void setItems(List<Now_players_info> items){
//        if(items.size()<0){
//            return;
//        }
        mItemStrs = items;
        mOffsetAngle=0;
        mStartAngle=0;
        mOffsetAngle = 360/getItemSize()/2;
//        invalidate();
        requestLayout();
    }


    public int getItemSize(){
        return mItemStrs.size();
    }

    /**
     * 设置转盘数据
     */
    public void setLuckNumber(int luckNumber){
        mLuckNum = luckNumber;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w,h)/2;
        //这里是将（0，0）点作为圆心
        rectFPan = new RectF(-mRadius,-mRadius,mRadius,mRadius);
        rectFStr = new RectF(-mRadius/7*5,-mRadius/7*5,mRadius/7*5,mRadius/7*5);
        //每一个Item的角度
        mItemAnge = 360 / getItemSize();
        mTextSize = mRadius/9;
        mPaintItemStr.setTextSize(mTextSize);
        //数据初始化
        mOffsetAngle=0;
        mStartAngle=0;
        mOffsetAngle = mItemAnge/2;
    }
    public void startAnim(){
//        mLuckNum = random.nextInt( mItemStrs.length);//随机生成结束位置
        if(objectAnimator!=null){
            objectAnimator.cancel();
        }
        float v = mItemAnge*mLuckNum+mStartAngle%360;//如果转过一次了那下次旋转的角度就需要减去上一次多出的，否则结束的位置会不断增加的
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", mStartAngle, mStartAngle-mRepeatCount*360-v);
        objectAnimator.setDuration(rotateTime);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(WKCallback!=null){
                    WKCallback.onCall(mItemStrs.get(mLuckNum));
                }
            }
        });
        objectAnimator.start();
        mStartAngle -= mRepeatCount*360+v;
    }
    public void stopAnim(){
        if(objectAnimator!=null){
            objectAnimator.cancel();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth()/2,getHeight()/2);//画布中心点设置为（0，0）
        canvas.rotate(-90-mOffsetAngle);
        drawPanItem(canvas);
//        drawText(canvas);
        drawIcon(canvas);
    }
    //画文字
    private void drawText(Canvas canvas) {
        for(int x = 0;x<getItemSize();x++){
            Path path = mArcPaths.get(x);
            canvas.drawTextOnPath(mItemStrs.get(x).name,path,0,0,mPaintItemStr);
        }
    }
    //画头像
    private void drawIcon(Canvas mCanvas) {
        int width = (int) mRadius/2;
        int imgWidth = (int) mRadius / 6;

        float startAngele= mItemAnge/2;

        for (int j = 0; j < getItemSize(); j++) {
            float angle = (float) Math.toRadians(startAngele);
            startAngele+=mItemAnge;
            final float currentAngle=startAngele;

            //确定图片在圆弧中 中心点的位置
            float x = (float) (width + (mRadius / 2 + imgWidth/2) * Math.cos(angle)) -mRadius/2;
            float y = (float) (width + (mRadius / 2 + imgWidth/2) * Math.sin(angle)) -mRadius/2;

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // 确定绘制图片的位置
            RectF rect = new RectF(x - imgWidth, y - imgWidth, x + imgWidth, y + imgWidth);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_turntable_laughcry);
//            Matrix matrix = new Matrix();
//            matrix.setRotate(currentAngle+45);
//            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
            mCanvas.drawBitmap(bitmap, null, rect, paint);

//            Glide.with(this).load(mItemStrs.get(j).getHead()).into(new SimpleTarget<Drawable>() {
//                @Override
//                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_turntable_laughcry);
//                    Matrix matrix = new Matrix();
//                    matrix.setRotate(currentAngle+45);
//                    bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
//                    mCanvas.drawBitmap(bitmap, null, rect, paint);
//                }
//            });

        }
    }

    private void drawPanItem(Canvas canvas) {
        float startAng = 0;//扇形开始的角度
        for (int x = 0;x< getItemSize();x++){
            mPaintArc.setColor(itemColor[x]);
            Path path = new Path();
            path.addArc(rectFStr,startAng,mItemAnge);//文字的路径圆形比盘的小
            mArcPaths.add(path);
            canvas.drawArc(rectFPan,startAng,mItemAnge,true,mPaintArc);
            startAng+=mItemAnge;
        }
    }
}
