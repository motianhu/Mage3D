package com.smona.app.mage3d;

import javax.microedition.khronos.opengles.GL10;

import com.smona.app.mage3d.R;
import com.mediatek.ngin3d.Actor;
import com.mediatek.ngin3d.Color;
import com.mediatek.ngin3d.Container;
import com.mediatek.ngin3d.HitTestResult;
import com.mediatek.ngin3d.Image;
import com.mediatek.ngin3d.Point;
import com.mediatek.ngin3d.Scale;
import com.mediatek.ngin3d.Stage;
import com.mediatek.ngin3d.Text;
import com.mediatek.ngin3d.android.StageTextureView;
import com.mediatek.ngin3d.animation.Animation;
import com.mediatek.ngin3d.animation.AnimationGroup;
import com.mediatek.ngin3d.animation.AnimationLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MoneyView extends StageTextureView {

    public static final String TAG = "MageWallpaperView";
    public static final float ZNEAR = 2.0f;
    public static final float ZFAR = 3000.0f;
    public static final float ZSTAGE = -1111.0f;
    private Stage mStage;
    private float mRatio = 1.0f;
    private Container mScene;
    private int mSrceenHeight;
    private int mScreenWidth;
    private int mCurrentMovement;
    private int mMaxDistance = 400;
    private float mProgress;
    private Animation mAnim_left[] = new Animation[3];
    private Animation mAnim_right[] = new Animation[3];
    private Image mMoney[] = { Image.createFromAsset("yuan.png"),
            Image.createFromAsset("yuan.png"),
            Image.createFromAsset("yuan.png"),

    };
    private Text mAllMoneytext = new Text("总资产(元)");
    private Text mAllMoneynum = new Text("123.0");

    private Text mDaiShouyitext = new Text("待收收益(元)");
    private Text mDaiShouyinum = new Text("456.0");

    private Text mYiShouyitext = new Text("已收收益(元)");
    private Text mYiShouyinum = new Text("789.0");

    private Text mXiangxi = new Text("详细 >");

    private Container mMoneyContaner[] = new Container[3];
    private AnimationGroup mAnimGroup_Left = new AnimationGroup();
    private AnimationGroup mAnimGroup_Right = new AnimationGroup();
    private GestureDetector mGestureDetector;
    private Handler mDelay = new Handler();
    private int mCurrentCenterIndex = 0;

    @SuppressLint("NewApi")
    public MoneyView(Context context, Stage stage, boolean antiAlias) {
        super(context, stage, antiAlias);
        setOpaque(false);
        AnimationLoader.setCacheDir(context.getCacheDir());
        initStage();
        initAnimation_Left();
        initAnimation_Right();
        addMoney();
        setMaterial();
        setPositonScale();
        setImageTarget(mCurrentCenterIndex);
        mAnimGroup_Left.setProgress(0.0f);
        mGestureDetector = new GestureDetector(context,
                new TinyGestureListener());
        for (int i = 0; i < 3; i++) {
            mMoney[i].enableMipmap(true);
            mMoney[i].setFilterQuality(Image.FILTER_QUALITY_HIGH);
        }

    }

    private void initStage() {
        mStage = getStage();
        mScene = new Container();
        mScene.setPosition(new Point(0.5f, 0.5f, 0.0f, true));
        mStage.setProjection(Stage.UI_PERSPECTIVE, ZNEAR, ZFAR, ZSTAGE);

        // setZOrderOnTop(true);
        mStage.setBackgroundColor(new Color(0, 0, 0, 0));
        mStage.add(mScene);
        for (int i = 0; i < 3; i++) {
            mMoneyContaner[i] = new Container();
            mScene.add(mMoneyContaner[i]);
        }

    }

    private void addMoney() {
        for (int i = 0; i < 3; i++) {
            mMoneyContaner[i].add(mMoney[i]);
        }
        mMoneyContaner[0].add(mAllMoneytext);
        mMoneyContaner[0].add(mAllMoneynum);
        mMoneyContaner[0].add(mXiangxi);
        mMoneyContaner[1].add(mDaiShouyitext);
        mMoneyContaner[1].add(mDaiShouyinum);
        mMoneyContaner[2].add(mYiShouyitext);
        mMoneyContaner[2].add(mYiShouyinum);

    }

    private void setPositonScale() {
        mAllMoneytext.setPosition(new Point(0.0f, -60f, -3.0f, false));
        mDaiShouyitext.setPosition(new Point(0.0f, -60f, -3.0f, false));
        mYiShouyitext.setPosition(new Point(0.0f, -60f, -3.0f, false));
        mAllMoneynum.setPosition(new Point(0.0f, 0f, -3.0f, false));
        mDaiShouyinum.setPosition(new Point(0.0f, 0f, -3.0f, false));
        mYiShouyinum.setPosition(new Point(0.0f, 0f, -3.0f, false));

        mXiangxi.setPosition(new Point(0.0f, 60f, -3.0f, false));

        setTextSize(mAllMoneynum, 32f);
        setTextSize(mDaiShouyinum, 32f);
        setTextSize(mYiShouyinum, 32f);

        setTextSize(mXiangxi, 22f);
        mXiangxi.getNode().useCollisionSquare();
        mXiangxi.getNode().setCollisionScale(new Scale(3.0f, 3.0f));

    }

    private void setMaterial() {
        for (int i = 0; i < 3; i++) {
            mMoney[i].setMaterial("alpha_clear.mat");
        }
    }

    private void setTextSize(Text text, float textsize) {
        text.setTextSize(textsize);
    }

    @SuppressLint("NewApi")
    private void initAnimation_Left() {
        mAnim_left[0] = AnimationLoader.loadAnimation(getContext(),
                R.raw.yuan_left_1);

        mAnim_left[1] = AnimationLoader.loadAnimation(getContext(),
                R.raw.yuan_left_2);

        mAnim_left[2] = AnimationLoader.loadAnimation(getContext(),
                R.raw.yuan_left_3);

        mAnimGroup_Left.add(mAnim_left[0]);
        mAnimGroup_Left.add(mAnim_left[1]);
        mAnimGroup_Left.add(mAnim_left[2]);

        mAnimGroup_Left
                .disableOptions(Animation.START_TARGET_WITH_INITIAL_VALUE);
        // mAnimation.disableOptions(Animation.BACKWARD);
        // mAnimation.setProgress(0.0f);
        mAnimGroup_Left.addListener(new AnimationLeftListener());
    }

    private class AnimationLeftListener extends Animation.Listener {
        @Override
        public void onNewFrame(Animation animation, int elapsedMsec) {
            super.onNewFrame(animation, elapsedMsec);

        }

        public void onStarted(Animation animation) {

            Log.d("AnimationListener",
                    "Animation start: " + animation.getName());
        }

        public void onCompleted(Animation animation) {
            if (animation.getDirection() == Animation.FORWARD) {
                mCurrentCenterIndex++;
                mCurrentCenterIndex = mCurrentCenterIndex % 3;
            }
            Log.d("AnimationListener", "Direction: " + animation.getDirection());
        }

    }

    @SuppressLint("NewApi")
    private void initAnimation_Right() {
        mAnim_right[0] = AnimationLoader.loadAnimation(getContext(),
                R.raw.yuan_right_1);

        mAnim_right[1] = AnimationLoader.loadAnimation(getContext(),
                R.raw.yuan_right_2);

        mAnim_right[2] = AnimationLoader.loadAnimation(getContext(),
                R.raw.yuan_right_3);

        mAnimGroup_Right.add(mAnim_right[0]);
        mAnimGroup_Right.add(mAnim_right[1]);
        mAnimGroup_Right.add(mAnim_right[2]);

        mAnimGroup_Right
                .disableOptions(Animation.START_TARGET_WITH_INITIAL_VALUE);

        mAnimGroup_Right.addListener(new AnimationRightListener());
    }

    private class AnimationRightListener extends Animation.Listener {
        @Override
        public void onNewFrame(Animation animation, int elapsedMsec) {
            super.onNewFrame(animation, elapsedMsec);
        }

        public void onStarted(Animation animation) {

            Log.d("AnimationListener",
                    "Animation start: " + animation.getName());
        }

        public void onCompleted(Animation animation) {
            if (animation.getDirection() == Animation.FORWARD) {
                mCurrentCenterIndex--;
                if (mCurrentCenterIndex < 0) {
                    mCurrentCenterIndex = 2;
                }
            }
            Log.d("AnimationListener", "Animation end: " + animation.getName());
        }

    }

    private void setImageTarget(int index) {

        for (int i = 0; i < 3; i++) {
            mAnim_left[i].setTarget(null);
            mAnim_left[i].setTarget(mMoneyContaner[(i + index) % 3]);
            mAnim_right[i].setTarget(null);
            mAnim_right[i].setTarget(mMoneyContaner[(i + index) % 3]);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        super.onSurfaceChanged(gl, width, height);
        mSrceenHeight = height;
        mScreenWidth = width;
        setRateToScale();
        setActorScaleToFitDifferentPixel();
    }

    private void setRateToScale() {
        if (mScreenWidth / Configs.WIDTH_STANDARD <= mSrceenHeight
                / Configs.HEIGHT_STANDARD) {
            mRatio = mScreenWidth / Configs.WIDTH_STANDARD;
        } else {
            mRatio = mSrceenHeight / Configs.HEIGHT_STANDARD;
        }
        Log.d(TAG, "mScreenWidth:" + mScreenWidth + "mSrceenHeight:"
                + mSrceenHeight);
    }

    private void setActorScaleToFitDifferentPixel() {
        Scale normalScale = new Scale(mRatio, mRatio);
        mScene.setScale(normalScale);

    }

    private void startAnimation_Left() {
        mDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimGroup_Left.start();
            }
        }, 100);
    }

    private void startAnimation_Right() {
        mDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimGroup_Right.start();
            }
        }, 100);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mAnimGroup_Left.isStarted() || mAnimGroup_Right.isStarted())
                return false;
            mCurrentMovement = 0;
            setImageTarget(mCurrentCenterIndex);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "ACTION_UP");
            if (mCurrentMovement > 0.0f) {
                startAnimation_Left();
            } else if (mCurrentMovement < 0.0f) {
                startAnimation_Right();
            }
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private boolean getHitTestActor(Actor target, MotionEvent e) {
        if (!target.getVisible()) {
            return false;
        }
        HitTestResult result = target
                .hitTestFull(new Point(e.getX(), e.getY()));
        if (result.getActor() != null)
            return true;
        return false;
    }

    private class TinyGestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            Log.d(TAG, "onFling velocityX:" + velocityX);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
            mCurrentMovement += distanceX;
            if (mCurrentMovement >= 0.0f) {
                mProgress = mCurrentMovement / (mMaxDistance * mRatio);
                if (mProgress >= 1.0f) {
                    mProgress = 1.0f;
                }
                mAnimGroup_Left.setProgress(mProgress);
                if (mProgress >= 0.5f) {
                    mAnimGroup_Left.setDirection(Animation.FORWARD);
                } else {
                    mAnimGroup_Left.setDirection(Animation.BACKWARD);
                }
                Log.d(TAG, "mProgress:" + mProgress);
            } else {
                mProgress = Math.abs(mCurrentMovement)
                        / (mMaxDistance * mRatio);
                mAnimGroup_Right.setProgress(mProgress);
                if (mProgress >= 0.5f) {
                    mAnimGroup_Right.setDirection(Animation.FORWARD);
                } else {
                    mAnimGroup_Right.setDirection(Animation.BACKWARD);
                }
                Log.d(TAG, "mProgress:" + mProgress);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            setImageTarget(mCurrentCenterIndex);
            Log.d(TAG, "查看详细->去相应应用: mCurrentCenterIndex="
                    + mCurrentCenterIndex);
            if (getHitTestActor(mMoneyContaner[mCurrentCenterIndex], e)) {

            } else if (mCurrentCenterIndex == 0) {
                if (getHitTestActor(mMoneyContaner[1], e)) {
                    mAnimGroup_Right.setDirection(Animation.BACKWARD);
                    startAnimation_Left();
                } else if (getHitTestActor(mMoneyContaner[2], e)) {
                    mAnimGroup_Right.setDirection(Animation.FORWARD);
                    startAnimation_Right();
                }
            } else if (mCurrentCenterIndex == 1) {
                if (getHitTestActor(mMoneyContaner[2], e)) {
                    mAnimGroup_Right.setDirection(Animation.BACKWARD);
                    startAnimation_Left();
                } else if (getHitTestActor(mMoneyContaner[0], e)) {
                    mAnimGroup_Right.setDirection(Animation.FORWARD);
                    startAnimation_Right();
                }
            } else if (mCurrentCenterIndex == 2) {
                if (getHitTestActor(mMoneyContaner[0], e)) {
                    mAnimGroup_Right.setDirection(Animation.BACKWARD);
                    startAnimation_Left();
                } else if (getHitTestActor(mMoneyContaner[1], e)) {
                    mAnimGroup_Right.setDirection(Animation.FORWARD);
                    startAnimation_Right();
                }
            }
            return super.onSingleTapUp(e);
        }

    }
}
