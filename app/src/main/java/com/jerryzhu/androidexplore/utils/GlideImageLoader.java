package com.jerryzhu.androidexplore.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Author : jerryzhu
 * <p>
 * Time : 2018/12/16
 * <p>
 * Description : this is GlideImageLoader
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
