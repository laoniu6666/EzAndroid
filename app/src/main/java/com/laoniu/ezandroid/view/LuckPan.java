package com.laoniu.ezandroid.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import com.blankj.utilcode.util.SizeUtils;
import com.laoniu.ezandroid.R;
import com.laoniu.ezandroid.model.Now_players_info;
import com.laoniu.ezandroid.utils.TurnImageUtils;
import com.laoniu.ezandroid.utils.WKCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * LuckPan
 */
public class LuckPan extends View {
    private Paint mPaintArc;//转盘扇形画笔
    private Paint mPaintItemStr;//转盘文字画笔
    private float mRadius;//圆盘的半径
    private RectF rectFPan;//构建转盘的矩形
    //    private RectF rectFStr;//构建文字圆盘的矩形
//    private RectF rectFBitmap;//构建图片圆盘的矩形
    private List<Now_players_info> mItemStrs;
//    private ArrayList<Path> mArcPaths;
    private float mItemAnge;
    private int mRepeatCount = 4;//转几圈
    private int mLuckNum = 2;//最终停止的位置
    private float mStartAngle = 0;//存储圆盘开始的位置
    private float mTextSize = 20;//文字大小
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

//        mArcPaths = new ArrayList<>();
    }

    /**
     * 设置转盘数据
     * @param items
     */
    public void setItems(List<Now_players_info> items){
        mItemStrs = items;
        mStartAngle=0;
        invalidate();
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
//        rectFStr = new RectF(-mRadius/7*5,-mRadius/7*5,mRadius/7*5,mRadius/7*5);
//        rectFBitmap = new RectF(-mRadius/7*5,-mRadius/7*5,mRadius/7*5,mRadius/7*5);
        //每一个Item的角度
        mItemAnge = 360 / mItemStrs.size();
        mTextSize = mRadius/9;
        mPaintItemStr.setTextSize(mTextSize);
        //数据初始化
        mStartAngle=0;
    }
    public void startAnim(){
        if(objectAnimator!=null){
            objectAnimator.cancel();
        }
        float v = mItemAnge*mLuckNum-mItemAnge/2;//半个item+指定的item+上次的位置=总旋转角度
        objectAnimator = ObjectAnimator.ofFloat(this, "rotation", mStartAngle, mStartAngle+mRepeatCount*360+v);
        objectAnimator.setDuration(4000);
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
        mStartAngle += mRepeatCount*360+v;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,getHeight()/2);//画布中心点设置为（0，0）
        canvas.rotate(-90);
        drawPanItem(canvas);
//        drawText(canvas);
        drawIcon(canvas);

    }
    //画文字
//    private void drawText(Canvas canvas) {
//        for(int x = 0;x<mItemStrs.size();x++){
//            Path path = mArcPaths.get(x);
//            canvas.drawTextOnPath(mItemStrs.get(x).name,path,0,0,mPaintItemStr);
//        }
//    }

    //画头像
    private void drawIcon(Canvas mCanvas) {
        float imgHalfWidth = SizeUtils.dp2px(20);
        int startAngele=(int)mItemAnge/2;
        for (int j = 0; j < mItemStrs.size(); j++) {
            float angle = (float) Math.toRadians(startAngele);
            startAngele+=mItemAnge;

            //确定图片在圆弧中 中心点的位置
            float hypotenuse = (float) (mRadius / 2 + imgHalfWidth/2);//斜边
            float y = (float) (hypotenuse * Math.sin(angle));
            float x = (float) (hypotenuse * Math.cos(angle));

            Bitmap bitmap = mItemStrs.get(j).bmp;
//            bitmap = TurnImageUtils.rotateScaleBtimap(bitmap, 360 / mItemStrs.size() * j + 180 / mItemStrs.size() + 90, (int) (imgHalfWidth * 2));
            // 确定绘制图片的位置
            int width_bitmap = bitmap.getWidth() / 2;
            RectF rect = new RectF(x - width_bitmap, y - width_bitmap, x + width_bitmap, y + width_bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCanvas.drawBitmap(bitmap, null, rect, paint);
        }
    }

    private void drawPanItem(Canvas canvas) {
        float startAng = 0;//扇形开始的角度
        for (int x = 0;x< mItemStrs.size();x++){
            mPaintArc.setColor(getContext().getColor(itemColor[x]));
            Path path = new Path();
//            path.addArc(rectFBitmap,startAng,mItemAnge);//文字的路径圆形比盘的小
//            mArcPaths.add(path);
            canvas.drawArc(rectFPan,startAng,mItemAnge,true,mPaintArc);
            startAng+=mItemAnge;
        }
    }
}
