package com.jerryzhu.androidexplore.widget;


import android.animation.Animator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;


public class CircularRevealAnimation {

    public AnimationListener mAnimationListener;
    private Animator mAnimator;

    public void show(View triggerView,View showView){
        showActionView(true,triggerView,showView);
    }

    public void hide(View triggerView, View showView){

        showActionView(false,triggerView,showView);
    }

    private void showActionView(boolean isShow, View triggerView, View showView) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            if(isShow){
                showView.setVisibility(View.VISIBLE);
                if (mAnimationListener != null && mAnimator != null) {
                    mAnimator.cancel();
                    mAnimator = null;
                    mAnimationListener.onShowAnimationEnd();
                }

            }else{
                showView.setVisibility(View.INVISIBLE);

                if (mAnimationListener != null && mAnimator != null) {
                    mAnimator.cancel();
                    mAnimator = null;
                    mAnimationListener.onHideAnimationEnd();
                }
            }

        }

        int [] tvLocation = {0,0};
        triggerView.getLocationInWindow(tvLocation);
        int tvX = (int)(tvLocation[0] + triggerView.getWidth() * 0.5);
        int tvY = (int)(tvLocation[1] + triggerView.getHeight() * 0.5);


        int [] svLocation = {0,0};
        showView.getLocationInWindow(svLocation);
        int svX = (int)(svLocation[0] + showView.getWidth() * 0.5);
        int svY = (int)(svLocation[1] + showView.getHeight() * 0.5);

        int rippleW;
        if(tvX < svX){
            rippleW = showView.getWidth() - tvX;
        }else{
            rippleW = tvX - svLocation[0];
        }


        int rippleH;
        if(tvY < svY){
            rippleH = showView.getHeight() - tvY;
        }else{
            rippleH = tvY - svLocation[1];
        }

        float maxRadius = (float) Math.sqrt((double) (svX * svX + svY * svY));
        float startRadius;
        float endRadius;

        if(isShow){
            startRadius = 0;
            endRadius = maxRadius;
        }else{
            startRadius = maxRadius;
            endRadius = 0;
        }

        Animator animator = ViewAnimationUtils.createCircularReveal(showView, svX, svY, startRadius, endRadius);
        showView.setVisibility(View.VISIBLE);
        animator.setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(isShow){
                    showView.setVisibility(View.VISIBLE);
                    if (mAnimationListener != null){
                       mAnimationListener.onShowAnimationEnd();
                    }
                }else{
                    showView.setVisibility(View.INVISIBLE);
                    if (mAnimationListener != null){
                        mAnimationListener.onHideAnimationEnd();

                    }
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();


    }

    public void setAnimationListener(AnimationListener animationListener){
        this.mAnimationListener = animationListener;
    }


    public interface AnimationListener{

        void onShowAnimationEnd();

        void onHideAnimationEnd();

    }

}
