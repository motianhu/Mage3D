package com.smona.app.mage3d;

import javax.microedition.khronos.opengles.GL10;

import com.smona.app.mage3d.R;
import com.mediatek.ngin3d.Color;
import com.mediatek.ngin3d.Container;
import com.mediatek.ngin3d.Image;
import com.mediatek.ngin3d.Point;
import com.mediatek.ngin3d.Scale;
import com.mediatek.ngin3d.Stage;
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

public class MageWallpaperView extends StageTextureView {

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
    private int mMaxDistance = 648;
    private float mProgress;
    private Animation mAnim_left[] = new Animation[6];
    private Animation mAnim_right[] = new Animation[6];
    private Image mSlider[] = { Image.createFromAsset("slider1.png"),
            Image.createFromAsset("slider2.png"),
            Image.createFromAsset("slider3.png"),
            Image.createFromAsset("slider4.jpg"),
            Image.createFromAsset("slider5.jpg"),
            Image.createFromAsset("slider6.png") };
    private Image mShadow[] = { Image.createFromAsset("shadow.png"),
            Image.createFromAsset("shadow.png"),
            Image.createFromAsset("shadow.png"),
            Image.createFromAsset("shadow.png"),
            Image.createFromAsset("shadow.png"),
            Image.createFromAsset("shadow.png") };
    private Container mSliderShadow[] = new Container[6];
    private AnimationGroup mAnimGroup_Left = new AnimationGroup();
    private AnimationGroup mAnimGroup_Right = new AnimationGroup();
    private GestureDetector mGestureDetector;
    private Handler mDelay = new Handler();
    private int index = 0;
    private static final float ANIMATION_TIME = 1000f;

    @SuppressLint("NewApi")
    public MageWallpaperView(Context context, Stage stage, boolean antiAlias) {
        super(context, stage, antiAlias);
        setOpaque(false);
        AnimationLoader.setCacheDir(context.getCacheDir());
        initStage();
        initAnimation_Left();
        initAnimation_Right();
        addSliderShadow();
        setMaterial();
        setImageTarget(index);
        mAnimGroup_Left.setProgress(0.0f);
        mGestureDetector = new GestureDetector(context,
                new TinyGestureListener());
        for (int i = 0; i < 6; i++) {
            mSlider[0].enableMipmap(true);
            mSlider[0].setFilterQuality(Image.FILTER_QUALITY_HIGH);
            mShadow[0].enableMipmap(true);
            mShadow[0].setFilterQuality(Image.FILTER_QUALITY_HIGH);
        }
    }

    private void initStage() {
        mStage = getStage();
        mScene = new Container();
        mScene.setPosition(new Point(0.5f, 0.5f, 0.0f, true));
        mStage.setProjection(Stage.UI_PERSPECTIVE, ZNEAR, ZFAR, ZSTAGE);

        // setZOrderOnTop(true);
        mStage.setBackgroundColor(new Color(31, 33, 48, 255));
        mStage.add(mScene);
        for (int i = 0; i < 6; i++) {
            mSliderShadow[i] = new Container();
            mScene.add(mSliderShadow[i]);
        }

    }

    private void addSliderShadow() {
        for (int i = 0; i < 6; i++) {
            mSliderShadow[i].add(mSlider[i]);
            mSliderShadow[i].add(mShadow[i]);
            mShadow[i].setScale(new Scale(1.378f, 1.378f));
            mShadow[i].setPosition(new Point(0.0f, 0.0f, 5.0f, false));
        }
    }

    private void setMaterial() {
        for (int i = 0; i < 6; i++) {
            mSlider[i].setMaterial("changecolor.mat");
            mShadow[i].setMaterial("alpha_clear.mat");
        }

        mSlider[0].setMaterialProperty("TEXTURE", "slider1.png");
        mSlider[1].setMaterialProperty("TEXTURE", "slider2.png");
        mSlider[2].setMaterialProperty("TEXTURE", "slider3.png");
        mSlider[3].setMaterialProperty("TEXTURE", "slider4.jpg");
        mSlider[4].setMaterialProperty("TEXTURE", "slider5.jpg");
        mSlider[5].setMaterialProperty("TEXTURE", "slider6.png");
        mSlider[0].setMaterialProperty("COLORRGB", 1.0f);
        for (int i = 1; i < 6; i++) {
            mSlider[i].setMaterialProperty("COLORRGB", 0.45f);
        }
    }

    private void initAnimation_Left() {
        mAnim_left[0] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_left_1);

        mAnim_left[1] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_left_2);

        mAnim_left[2] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_left_3);

        mAnim_left[3] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_left_4);

        mAnim_left[4] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_left_5);

        mAnim_left[5] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_left_6);

        mAnimGroup_Left.add(mAnim_left[0]);
        mAnimGroup_Left.add(mAnim_left[1]);
        mAnimGroup_Left.add(mAnim_left[2]);
        mAnimGroup_Left.add(mAnim_left[3]);
        mAnimGroup_Left.add(mAnim_left[4]);
        mAnimGroup_Left.add(mAnim_left[5]);

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

            float progress = elapsedMsec / ANIMATION_TIME;
            if (progress >= 1.0f) {
                progress = 1.0f;
            }
            for (int i = 0; i < 6; i++) {
                if (i == index) {
                    mSlider[i].setMaterialProperty("COLORRGB",
                            1.0f - progress * 0.55f);
                } else if (i == ((1 + index) % 6)) {
                    mSlider[i].setMaterialProperty("COLORRGB",
                            0.45f + progress * 0.55f);
                } else {
                    mSlider[i].setMaterialProperty("COLORRGB", 0.45f);
                }
            }
            Log.d(TAG, "progress:" + progress);
        }

        public void onStarted(Animation animation) {

            Log.d("AnimationListener",
                    "Animation start: " + animation.getName());
        }

        public void onCompleted(Animation animation) {
            if (animation.getDirection() == Animation.FORWARD) {
                index++;
                index = index % 6;
            }
            Log.d("AnimationListener", "Direction: " + animation.getDirection());
        }

    }

    private void initAnimation_Right() {
        mAnim_right[0] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_right_1);

        mAnim_right[1] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_right_2);

        mAnim_right[2] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_right_3);

        mAnim_right[3] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_right_4);

        mAnim_right[4] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_right_5);

        mAnim_right[5] = AnimationLoader.loadAnimation(getContext(),
                R.raw.slide_right_6);

        mAnimGroup_Right.add(mAnim_right[0]);
        mAnimGroup_Right.add(mAnim_right[1]);
        mAnimGroup_Right.add(mAnim_right[2]);
        mAnimGroup_Right.add(mAnim_right[3]);
        mAnimGroup_Right.add(mAnim_right[4]);
        mAnimGroup_Right.add(mAnim_right[5]);

        mAnimGroup_Right
                .disableOptions(Animation.START_TARGET_WITH_INITIAL_VALUE);

        mAnimGroup_Right.addListener(new AnimationRightListener());
    }

    private class AnimationRightListener extends Animation.Listener {
        @Override
        public void onNewFrame(Animation animation, int elapsedMsec) {
            super.onNewFrame(animation, elapsedMsec);
            float progress = elapsedMsec / ANIMATION_TIME;
            if (progress >= 1.0f) {
                progress = 1.0f;
            }
            int rightIndex = index - 1;
            if (rightIndex < 0) {
                rightIndex = 5;
            }
            for (int i = 0; i < 6; i++) {
                if (i == index) {
                    mSlider[i].setMaterialProperty("COLORRGB",
                            1.0f - progress * 0.55f);
                } else if (i == rightIndex) {
                    mSlider[i].setMaterialProperty("COLORRGB",
                            0.45f + progress * 0.55f);
                } else {
                    mSlider[i].setMaterialProperty("COLORRGB", 0.45f);
                }
            }
            Log.d(TAG, "animation:" + animation.getDirection());
        }

        public void onStarted(Animation animation) {

            Log.d("AnimationListener",
                    "Animation start: " + animation.getName());
        }

        public void onCompleted(Animation animation) {
            if (animation.getDirection() == Animation.FORWARD) {
                index--;
                if (index < 0) {
                    index = 5;
                }
            }
            Log.d("AnimationListener", "Animation end: " + animation.getName());
        }

    }

    private void setImageTarget(int index) {

        for (int i = 0; i < 6; i++) {
            mAnim_left[i].setTarget(null);
            mAnim_left[i].setTarget(mSliderShadow[(i + index) % 6]);
            mAnim_right[i].setTarget(null);
            mAnim_right[i].setTarget(mSliderShadow[(i + index) % 6]);
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

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mAnimGroup_Left.isStarted() || mAnimGroup_Right.isStarted())
                return false;
            mCurrentMovement = 0;
            setImageTarget(index);
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

    private class TinyGestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            Log.d(TAG, "onFling velocityX:" + velocityX);
            // if(mCurrentMovement >= 0.0f){
            // if(velocityX < -1000f){
            // mAnimation.setDirection(0);
            // }
            // }
            // else{
            // if(velocityX > 1000f){
            // mAnimation.setDirection(1);
            // }
            // }
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
            // mSlider[0].setMaterialProperty("", "TEXTURE", bitmap);
            // mSlider[0].setMaterialProperty("", "TEXTURE", "slider6.png");
            Log.d(TAG, "name:" + mSlider[0].getName());
            // index ++;
            // if(index % 2 ==0){
            // mAnimation.setDirection(0);
            // mAnimation.setProgress(0.3f);
            //
            // mAnimation.start();
            // }
            // else{
            // mAnimation.setDirection(1);
            // mAnimation.setProgress(0.3f);
            // mAnimation.start();
            // }
            return super.onSingleTapUp(e);
        }

    }
}
