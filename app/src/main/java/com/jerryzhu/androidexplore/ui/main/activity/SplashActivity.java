package com.jerryzhu.androidexplore.ui.main.activity;

import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;
import com.jerryzhu.androidexplore.R;
import com.jerryzhu.androidexplore.app.AndroidExploreApp;
import com.jerryzhu.androidexplore.base.activity.BaseActivity;
import com.jerryzhu.androidexplore.bridge.main.SplashBridge;
import com.jerryzhu.androidexplore.presenter.main.SplashPresenter;
import com.jerryzhu.androidexplore.utils.StatusBarUtil;
import butterknife.BindView;

/**
 * author: jerryzhu
 * Date : 2018/12/8
 * Description :
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashBridge.View {
    @BindView(R.id.one_animation)
    LottieAnimationView oneAnimation;
    @BindView(R.id.two_animation)
    LottieAnimationView twoAnimation;
    @BindView(R.id.three_animation)
    LottieAnimationView threeAnimation;
    @BindView(R.id.four_animation)
    LottieAnimationView fourAnimation;
    @BindView(R.id.five_animation)
    LottieAnimationView fiveAnimation;
    @BindView(R.id.six_animation)
    LottieAnimationView sixAnimation;
    @BindView(R.id.seven_animation)
    LottieAnimationView sevenAnimation;
    @BindView(R.id.eight_animation)
    LottieAnimationView eightAnimation;
    @BindView(R.id.nine_animation)
    LottieAnimationView nineAnimation;
    @BindView(R.id.ten_animation)
    LottieAnimationView tenAnimation;

    @Override
    protected void initDataAndEvent() {

        startAnimation(oneAnimation,"W.json");
        startAnimation(twoAnimation,"A.json");
        startAnimation(threeAnimation,"N.json");
        startAnimation(fourAnimation,"A.json");
        startAnimation(fiveAnimation,"N.json");
        startAnimation(sixAnimation,"D.json");
        startAnimation(sevenAnimation,"R.json");
        startAnimation(eightAnimation,"O.json");
        startAnimation(nineAnimation,"I.json");
        startAnimation(tenAnimation,"D.json");

    }

    private void startAnimation(LottieAnimationView animation, String name) {

        animation.setAnimation(name);
        animation.playAnimation();

    }

    @Override
    protected void initToolBar() {

        if(!AndroidExploreApp.isFirstRun){
            jumpTomainActivity();
            return;
        }
        AndroidExploreApp.isFirstRun = false;
        StatusBarUtil.immersive(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void jumpTomainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    @Override
    protected void onDestroy() {
        cancleAnimation();
        super.onDestroy();
    }

    private void cancleAnimation() {
        oneAnimation.cancelAnimation();
        twoAnimation.cancelAnimation();
        threeAnimation.cancelAnimation();
        fourAnimation.cancelAnimation();
        fiveAnimation.cancelAnimation();
        sixAnimation.cancelAnimation();
        sevenAnimation.cancelAnimation();
        eightAnimation.cancelAnimation();
        nineAnimation.cancelAnimation();
        tenAnimation.cancelAnimation();

    }

}
