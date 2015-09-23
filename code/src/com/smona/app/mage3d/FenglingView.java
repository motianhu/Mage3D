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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class FenglingView extends StageTextureView {

	public static final String TAG ="MageWallpaperView";
	public static final float ZNEAR = 2.0f;
    public static final float ZFAR = 3000.0f;
    public static final float ZSTAGE = -1111.0f;
    private Stage mStage;
    private float mRatio = 1.0f;
    private Container mScene;
    private Container f000;
    private int mSrceenHeight;
    private int mScreenWidth;

    private Animation mAnim[] =  new Animation[12];
    private Image mNum[]={
    		Image.createFromAsset("fengling0.png"),
    		Image.createFromAsset("fengling1.png"),
    		Image.createFromAsset("fengling2.png"),
    		Image.createFromAsset("fengling3.png"),

    };
    private Image mXulie[]={
    		Image.createFromAsset("fl0001.png"),
    		Image.createFromAsset("fl0002.png"),
    		Image.createFromAsset("fl0003.png"),
    		Image.createFromAsset("fl0004.png"),
    		Image.createFromAsset("fl0005.png"),
    		Image.createFromAsset("fl0006.png"),
    		Image.createFromAsset("fl0007.png"),
    		Image.createFromAsset("fl0008.png"),
    };
    private AnimationGroup mAnimGroup = new AnimationGroup();

    private GestureDetector mGestureDetector;
//    Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.bj);
    /*
	public MageWallpaperView(Context context){
		super(context);
		AnimationLoader.setCacheDir(context.getCacheDir());
		initStage();
		initAnimation();
		setImageTarget(index);
		mAnimation.setProgress(0.0f);
		mGestureDetector = new GestureDetector(context,new TinyGestureListener());
	}
	public MageWallpaperView(Context context, AttributeSet attrs) {
		super(context, attrs);
		AnimationLoader.setCacheDir(context.getCacheDir());
		initStage();
		initAnimation();
		setImageTarget(index);
		mAnimation.setProgress(0.0f);
		mGestureDetector = new GestureDetector(context,new TinyGestureListener());
	}
	*/
	public FenglingView(Context context, Stage stage, boolean antiAlias) {
		super(context, stage, antiAlias);
		setOpaque(false);
		AnimationLoader.setCacheDir(context.getCacheDir());
		initStage();
		initAnimation_Left();


//		mAnimGroup.setProgress(0.0f);
		mGestureDetector = new GestureDetector(context,new TinyGestureListener());

	}
	private void initStage(){
        mStage = getStage();
        mScene = new Container();
        mScene.setPosition(new Point(0.5f,0.5f,0.0f,true));
        mStage.setProjection(Stage.UI_PERSPECTIVE, ZNEAR,
                ZFAR, ZSTAGE);
        f000 = new Container();
        f000.setPosition(new Point(0.5f,0.5f,3.0f,true));
//        setZOrderOnTop(true);
        mStage.setBackgroundColor(new Color(31,33,48,255));
        mStage.add(mScene,f000);
        for(int i=0; i<4; i++){
        	mScene.add(mNum[i]);
        }
        for(int i=0;i<8;i++){
        	f000.add(mXulie[i]);
        }
	}

	private void initAnimation_Left(){
        mAnim[0] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_0);
        mAnim[0].setTarget(mNum[0]);
        mAnim[1] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_1);
        mAnim[1].setTarget(mNum[1]);
        mAnim[2] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_2);
        mAnim[2].setTarget(mNum[2]);
        mAnim[3] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_3);
        mAnim[3].setTarget(mNum[3]);
        
        mAnim[4] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0001);
        mAnim[4].setTarget(mXulie[0]);
        mAnim[5] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0002);
        mAnim[5].setTarget(mXulie[1]);
        mAnim[6] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0003);
        mAnim[6].setTarget(mXulie[2]);
        mAnim[7] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0004);
        mAnim[7].setTarget(mXulie[3]);
        mAnim[8] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0005);
        mAnim[8].setTarget(mXulie[4]);
        mAnim[9] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0006);
        mAnim[9].setTarget(mXulie[5]);
        mAnim[10] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0007);
        mAnim[10].setTarget(mXulie[6]);
        mAnim[11] = AnimationLoader.loadAnimation(getContext(), R.raw.xiari_fl_fl0008);
        mAnim[11].setTarget(mXulie[7]);
        for(int i=0;i<12;i++){
        	mAnimGroup.add(mAnim[i]);
        }
//        mAnimGroup.add(mAnim[0]);
//        mAnimGroup.add(mAnim[1]);
//        mAnimGroup.add(mAnim[2]);
//        mAnimGroup.add(mAnim[3]);
        
        mAnimGroup.disableOptions(Animation.START_TARGET_WITH_INITIAL_VALUE);
//        mAnimation.disableOptions(Animation.BACKWARD);
//        mAnimation.setProgress(0.0f);
        mAnimGroup.addListener(new AnimationListener());
        mAnimGroup.setLoop(true);
        mAnimGroup.start();
	}
    private class AnimationListener extends Animation.Listener{
        @Override
		public void onNewFrame(Animation animation, int elapsedMsec) {
			super.onNewFrame(animation, elapsedMsec);
			
//			float progress = elapsedMsec / ANIMATION_TIME;
//			if(progress >= 1.0f){
//				progress = 1.0f;
//			}
//
//			Log.d(TAG, "progress:"+progress);  
		}

        public void onStarted(Animation animation){         

            Log.d("AnimationListener", "Animation start: " + animation.getName());
        }
        public void onCompleted(Animation animation){

            Log.d("AnimationListener", "Direction: " + animation.getDirection());
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
        if (mScreenWidth / Configs.WIDTH_STANDARD <= mSrceenHeight / Configs.HEIGHT_STANDARD) {
            mRatio = mScreenWidth / Configs.WIDTH_STANDARD;
        } else {
            mRatio = mSrceenHeight / Configs.HEIGHT_STANDARD;
        }
        Log.d(TAG, "mScreenWidth:"+mScreenWidth+"mSrceenHeight:"+mSrceenHeight);
    }
    private void setActorScaleToFitDifferentPixel() {
        Scale normalScale = new Scale(mRatio, mRatio);
        mScene.setScale(normalScale);
        f000.setScale(normalScale);
    }

	@SuppressLint("ClickableViewAccessibility")
    @Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction()==MotionEvent.ACTION_DOWN){

		}
		else if(event.getAction()==MotionEvent.ACTION_UP){
			Log.d(TAG, "ACTION_UP");  

		}
		mGestureDetector.onTouchEvent(event);
		return true;
	}
	private class TinyGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d(TAG, "onFling velocityX:"+velocityX);
//			if(mCurrentMovement >= 0.0f){
//				if(velocityX < -1000f){
//					mAnimation.setDirection(0);
//				}
//			}
//			else{
//				if(velocityX > 1000f){
//					mAnimation.setDirection(1);
//				}
//			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
//			mSlider[0].setMaterialProperty("", "TEXTURE", bitmap);
//			mSlider[0].setMaterialProperty("", "TEXTURE", "slider6.png");
//			index ++;
//			if(index % 2 ==0){
//				mAnimation.setDirection(0);
//				mAnimation.setProgress(0.3f);
//				
//				mAnimation.start();
//			}
//			else{
//				mAnimation.setDirection(1);
//				mAnimation.setProgress(0.3f);
//				
//				mAnimation.start();
//			}
			return super.onSingleTapUp(e);
		}
		
	}
}
