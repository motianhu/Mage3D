package com.smona.app.mage3d;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;

import com.smona.app.mage3d.R;
import com.mediatek.ngin3d.Color;
import com.mediatek.ngin3d.Container;
import com.mediatek.ngin3d.Image;
import com.mediatek.ngin3d.Point;
import com.mediatek.ngin3d.Rotation;
import com.mediatek.ngin3d.Stage;
import com.mediatek.ngin3d.android.StageView;

public class RootView extends StageView {
    public static final float ZNEAR = 2.0f;
    public static final float ZFAR = 2000.0f;
    public static final float ZSTAGE = -1111.0f;
    // private Random mRandom = new Random(System.currentTimeMillis());
    private Stage mStage;
    private Container mScene;
    private Container mFront;
    private Container mBack;

    private int mCurrentMovement;
    private int mMaxDistance = 400;
    private float mAngle = 0.f;
    private float mXrotaion = 0.f;
    private GestureDetector mGestureDetector;
    public static final String TAG = "timeweather";
    private ValueAnimator mAnim;

    public RootView(Context context) {
        super(context);
        mStage = getStage();
        mScene = new Container();
        mScene.setPosition(new Point(0.5f, 0.5f, 0.0f, true));
        mStage.setProjection(Stage.UI_PERSPECTIVE, ZNEAR, ZFAR, ZSTAGE);

        setZOrderOnTop(true);
        mStage.setBackgroundColor(new Color(0, 0, 0, 0));

        final Image bj = Image
                .createFromResource(getResources(), R.drawable.bj);
        bj.setPosition(new Point(0.5f, 0.5f, 100.0f, true));
        mStage.add(bj, mScene);
        initContainer();
        initFront();
        initBack();
        setFrontOpacity();
        setBackOpacity();
        mGestureDetector = new GestureDetector(context,
                new TinyGestureListener());
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs, true);
        mStage = getStage();
        mScene = new Container();
        mScene.setPosition(new Point(0.5f, 0.5f, 0.0f, true));
        mStage.setProjection(Stage.UI_PERSPECTIVE, ZNEAR, ZFAR, ZSTAGE);

        setZOrderOnTop(true);
        mStage.setBackgroundColor(new Color(0, 0, 0, 0));

        final Image bj = Image
                .createFromResource(getResources(), R.drawable.bj);
        bj.setPosition(new Point(0.5f, 0.5f, 100.0f, true));
        mStage.add(bj, mScene);
        initContainer();
        initFront();
        initBack();
        setFrontOpacity();
        setBackOpacity();
        mGestureDetector = new GestureDetector(context,
                new TinyGestureListener());
    }

    public void initContainer() {
        mFront = new Container();
        mBack = new Container();

        mScene.add(mFront, mBack);
    }

    public void initFront() {
        final Image front = Image.createFromResource(getResources(),
                R.drawable.front);
        front.setDoubleSided(true);
        front.setPosition(new Point(0.0f, 0.0f, -50.0f, true));

        mFront.add(front);
    }

    public void initBack() {
        final Image back = Image.createFromResource(getResources(),
                R.drawable.back);
        back.setDoubleSided(true);
        back.setPosition(new Point(0.0f, 0.0f, 50.0f, true));
        back.setRotation(new Rotation(1.0f, 0.0f, 0.0f, 180.0f));

        mBack.add(back);
    }

    // public RootView(Context context, AttributeSet attrs) {
    // super(context, attrs);
    // // TODO Auto-generated constructor stub
    // }

    private class TinyGestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            final int FLING_MIN_DISTANCE = 100;
            final int FLING_MIN_VELOCITY = 200;
            if ((e1.getY() - e2.getY()) > FLING_MIN_DISTANCE
                    && velocityY < -FLING_MIN_VELOCITY) {

                Log.d(TAG, "up");
            } else if ((e2.getY() - e1.getY()) > FLING_MIN_DISTANCE
                    && velocityY > FLING_MIN_VELOCITY) {
                Log.d(TAG, "down");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
            Log.d(TAG, "onScroll");
            mCurrentMovement += distanceY;
            mAngle = -180.f * (float) mCurrentMovement / mMaxDistance;
            mAngle = mAngle + mXrotaion;
            setXRotation0_360();
            mScene.setRotation(new Rotation(mAngle, 0, 0));
            setFrontOpacity();
            setBackOpacity();

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

    }

    @SuppressLint({ "NewApi", "ClickableViewAccessibility" })
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mAnim != null)
                if (mAnim.isRunning())
                    mAnim.cancel();
            mXrotaion = mAngle;
            mAngle = 0.f;
            mCurrentMovement = 0;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // mXrotaion = angle;
            if (mAngle < 90.0f)
                startAnimation(mAngle, 0);
            else if (mAngle >= 90.0f && mAngle < 180.0f)
                startAnimation(mAngle, 180.0f);
            else if (mAngle >= 180.0f && mAngle < 270.0f)
                startAnimation(mAngle, 180.0f);
            else if (mAngle >= 270.0f && mAngle < 360.0f)
                startAnimation(mAngle, 360.0f);
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @SuppressLint("NewApi")
    private void startAnimation(float start, float end) {
        float time = Math.abs(end - start) / 180.0f * 3000;
        mAnim = ValueAnimator.ofFloat(start, end);
        mAnim.setDuration((long) time);
        mAnim.setInterpolator(new BounceInterpolator());

        mAnim.start();

        mAnim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator ani) {
                Float mangle = (Float) ani.getAnimatedValue();
                mAngle = mangle;
                mScene.setRotation(new Rotation(mAngle, 0, 0));
                setXRotation0_360();
                setFrontOpacity();
                setBackOpacity();
            }
        });
    }

    private void setXRotation0_360() {
        if (mAngle >= 360.0f)
            mAngle = mAngle - 360.0f;
        else if (mAngle < 0.0f)
            mAngle = mAngle + 360.0f;
    }

    private void setFrontOpacity() {
        float mOpacity = 0.0f;
        if (mAngle <= 180.0f)
            mOpacity = (170.0f - mAngle) / 180.0f * 300;
        else if (mAngle > 180.0f)
            mOpacity = (mAngle - 190.0f) / 180.0f * 300;

        if (mOpacity < 0.0f)
            mOpacity = 0.0f;
        else if (mOpacity > 255.0f)
            mOpacity = 255.0f;
        mFront.setOpacity((int) mOpacity);
    }

    private void setBackOpacity() {
        float mOpacity = 0.0f;
        if (mAngle <= 180.0f)
            mOpacity = (mAngle - 10.0f) / 180.0f * 512;
        else if (mAngle > 180.0f)
            mOpacity = (350.0f - mAngle) / 180.0f * 512;

        if (mOpacity < 0.0f)
            mOpacity = 0.0f;
        else if (mOpacity > 255.0f)
            mOpacity = 255.0f;
        mBack.setOpacity((int) mOpacity);

    }
}
