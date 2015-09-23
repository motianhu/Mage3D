package com.smona.app.mage3d;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.mage3d.R;
import com.mediatek.ngin3d.Container;
import com.mediatek.ngin3d.Image;
import com.mediatek.ngin3d.Layer;
import com.mediatek.ngin3d.Point;
import com.mediatek.ngin3d.Stage;
import com.mediatek.ngin3d.android.StageView;
import com.mediatek.ngin3d.animation.Animation;
import com.mediatek.ngin3d.animation.AnimationGroup;
import com.mediatek.ngin3d.animation.AnimationLoader;

public class WantflyView extends StageView {
    // presentation stage
    private final Stage mStage;
    private final Layer mLayer = new Layer();
    private Container mBodyParent;
    private Container mYunParent;
    AnimationGroup bodyGroup = new AnimationGroup();
    AnimationGroup yunGroup = new AnimationGroup();

    private Context mContext;

    public WantflyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mStage = getStage();
        mStage.setProjection(Stage.UI_PERSPECTIVE, 500.0f, 2000.0f, -1111);
        AnimationLoader.setCacheDir(context.getCacheDir());
        mBodyParent = new Container();
        mBodyParent.setPosition(new Point(0.0f, 0.0f, false));
        mStage.add(mLayer);
        mLayer.setUiPerspective(720, 1280, -1111);
        mLayer.add(mBodyParent);

        mYunParent = new Container();
        mYunParent.setPosition(new Point(0.0f, 0.0f, false));
        mLayer.add(mYunParent);

        final Image bj = Image.createFromAsset("bj.png");// (getResources(),
                                                         // R.drawable.bj);
        bj.setPosition(new Point(0.5f * 720, 0.5f * 1280, 0, false));
        mLayer.add(bj);

        final Image cao = Image.createFromAsset("cao.png");// (getResources(),
                                                           // R.drawable.cao);
        cao.setPosition(new Point(0.89f * 720, 0.87f * 1280, -16.0f, false));
        // cao.setMaterial("sway.mat");
        // cao.setMaterialProperty("SWAYING_SPEED", swayingSpeed);
        // cao.setMaterialProperty("SWAYING_DEPTH", swayingDepth);
        // cao.setMaterialProperty("SWAYING_PHASE", swayingPhase);
        mLayer.add(cao);

        final Image cao2 = Image.createFromAsset("cao2.png");// (getResources(),
                                                             // R.drawable.cao2);
        cao2.setPosition(new Point(0.89f * 720, 0.93f * 1280, -23.0f, false));
        // cao2.setMaterial("sway.mat");
        // cao2.setMaterialProperty("SWAYING_SPEED", swayingSpeed * 2);
        // cao2.setMaterialProperty("SWAYING_DEPTH", swayingDepth * 2);
        // cao2.setMaterialProperty("SWAYING_PHASE", swayingPhase - 180.0f);
        mLayer.add(cao2);

        InitalBody();
        InitalYun();
    }

    public void InitalYun() {
        final Image y1 = Image.createFromAsset("y1.png");// (getResources(),
                                                         // R.drawable.y1);
        final Animation y1_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_y1);
        y1.setPosition(new Point(1.05f * 720, 0.17f * 1280, -20.0f, false));
        // y1.setScale(new Scale(1.5f,1.5f));
        y1_animation.setTarget(y1);
        mYunParent.add(y1);
        yunGroup.add(y1_animation);

        final Image y2 = Image.createFromAsset("y2.png");// (getResources(),
                                                         // R.drawable.y2);
        final Animation y2_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_y2);
        y2.setPosition(new Point(0.66f * 720, 0.67f * 1280, -13.0f, false));
        // y2.setScale(new Scale(1.5f,1.5f));
        y2_animation.setTarget(y2);
        mYunParent.add(y2);
        yunGroup.add(y2_animation);

        final Image y3 = Image.createFromAsset("y3.png");// (getResources(),
                                                         // R.drawable.y3);
        final Animation y3_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_y3);
        y3.setPosition(new Point(0.20f * 720, 0.47f * 1280, -18.0f, false));
        // y3.setScale(new Scale(1.5f,1.5f));
        y3_animation.setTarget(y3);
        mYunParent.add(y3);
        yunGroup.add(y3_animation);

        final Image y4 = Image.createFromAsset("y3.png");// (getResources(),
                                                         // R.drawable.y3);
        final Animation y4_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_y4);
        y4.setPosition(new Point(1.19f * 720, 0.47f * 1280, -20.0f, false));
        // y4.setScale(new Scale(1.5f,1.5f));
        y4_animation.setTarget(y4);
        mYunParent.add(y4);
        yunGroup.add(y4_animation);

        final Image y5 = Image.createFromAsset("y2.png");// (getResources(),
                                                         // R.drawable.y2);
        final Animation y5_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_y5);
        y5.setPosition(new Point(1.71f * 720, 0.66f * 1280, -14.0f, false));
        // y5.setScale(new Scale(1.5f,1.5f));
        y5_animation.setTarget(y5);
        mYunParent.add(y5);
        yunGroup.add(y5_animation);

        yunGroup.setLoop(true);
        yunGroup.start();
    }

    public void InitalBody() {

        final Image yj = Image.createFromAsset("yj.png");// (getResources(),
                                                         // R.drawable.yj);
        final Animation yj_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_yj);
        yj.setPosition(new Point(0.79f * 720, 0.82f * 1280, -19.25f, false));
        yj_animation.setTarget(yj);
        mBodyParent.add(yj);
        bodyGroup.add(yj_animation);

        final Image yj1 = Image.createFromAsset("yj.png");// (getResources(),
                                                          // R.drawable.yj);
        final Animation yj1_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_yj2);
        yj1.setPosition(new Point(0.66f * 720, 0.82f * 1280, -19.25f, true));
        yj1_animation.setTarget(yj1);
        mBodyParent.add(yj1);
        bodyGroup.add(yj1_animation);

        final Image s = Image.createFromAsset("s.png");// (getResources(),
                                                       // R.drawable.s);
        final Animation s_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_s);
        s.setPosition(new Point(0.73f * 720, 0.86f * 1280, -18.50f, false));
        s_animation.setTarget(s);
        mBodyParent.add(s);
        bodyGroup.add(s_animation);

        final Image z = Image.createFromAsset("z.png");// (getResources(),
                                                       // R.drawable.z);
        final Animation z_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_z);
        z.setPosition(new Point(0.58f * 720, 0.87f * 1280, -16.75f, false));
        z_animation.setTarget(z);
        mBodyParent.add(z);
        bodyGroup.add(z_animation);

        final Image y = Image.createFromAsset("y.png");// (getResources(),
                                                       // R.drawable.y);
        final Animation y_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_y);
        y.setPosition(new Point(0.88f * 720, 0.87f * 1280, -17.0f, false));
        y_animation.setTarget(y);
        mBodyParent.add(y);
        bodyGroup.add(y_animation);

        final Image zui = Image.createFromAsset("zui.png");// (getResources(),
                                                           // R.drawable.zui);
        final Animation zui_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_zui);
        zui.setPosition(new Point(0.73f * 720, 0.85f * 1280, -19.50f, false));
        zui_animation.setTarget(zui);
        mBodyParent.add(zui);
        bodyGroup.add(zui_animation);

        final Image tf = Image.createFromAsset("tf.png");// (getResources(),
                                                         // R.drawable.tf);
        final Animation tf_animation = AnimationLoader.loadAnimation(mContext,
                R.raw.comp1_tf);
        tf.setPosition(new Point(0.65f * 720, 0.77f * 1280, -17.25f, false));
        tf_animation.setTarget(tf);
        mBodyParent.add(tf);
        bodyGroup.add(tf_animation);

        bodyGroup.setLoop(true);
        bodyGroup.start();
    }
}
