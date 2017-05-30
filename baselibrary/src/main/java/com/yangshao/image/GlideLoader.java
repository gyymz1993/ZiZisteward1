package com.yangshao.image;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.yangshao.image.utils.AnimationMode;
import com.yangshao.image.utils.ScaleMode;
import com.yangshao.image.utils.ShapeMode;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @version Gide加载图片
 * @author: gyymz1993
 * 创建时间：2017/4/1 17:31
 **/
public class GlideLoader implements IEngineIgLoader {
    private RequestManager requestManager;
    private Context mContext;

    public GlideLoader(Context context) {
        mContext=context.getApplicationContext();
        requestManager = Glide.with(mContext);
    }


    @Override
    public void request(final SingleConfig config) {
        DrawableTypeRequest request = getDrawableTypeRequest(config, requestManager);
        int scaleMode = config.getScaleMode();
        switch (scaleMode) {
            case ScaleMode.CENTER_CROP:
                request.centerCrop();
                break;
            case ScaleMode.FIT_CENTER:
                request.fitCenter();
                break;
            default:
                request.fitCenter();
                break;
        }

        //设置图片加载动画
        setAnimator(config, request);

        setShapeModeAndBlur(config, request);
        //设置缩略图
        if (config.getThumbnail() != 0) {
            request.thumbnail(config.getThumbnail());
        }

        if (config.getErrorResId() > 0) {
            request.error(config.getErrorResId());
        }
        if (config.getTarget() instanceof ImageView) {
            request.into((ImageView) config.getTarget());
        }

    }


    private int statisticsCount(SingleConfig config) {
        int count = 0;
        if (config.getShapeMode() == ShapeMode.OVAL || config.getShapeMode() == ShapeMode.RECT_ROUND || config.getShapeMode() == ShapeMode.SQUARE) {
            count++;
        }
        return count;
    }


    /**
     * 设置图片滤镜和形状
     *
     * @param config
     * @param request
     */
    private void setShapeModeAndBlur(SingleConfig config, DrawableTypeRequest request) {
        int count = 0;
        Transformation[] transformation = new Transformation[statisticsCount(config)];

        switch (config.getShapeMode()) {
            case ShapeMode.RECT_ROUND:
                transformation[count] = new RoundedCornersTransformation
                    (mContext, config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);
                count++;
                break;
            case ShapeMode.OVAL:
                transformation[count] = new CropCircleTransformation(mContext);
                count++;
                break;

            case ShapeMode.SQUARE:
                transformation[count] = new CropSquareTransformation(mContext);
                count++;
                break;
        }

        if (transformation.length != 0) {
            request.bitmapTransform(transformation);

        }

    }


    private DrawableTypeRequest getDrawableTypeRequest(SingleConfig config, RequestManager requestManager) {
        DrawableTypeRequest request = null;
        if (!TextUtils.isEmpty(config.getUrl())) {
            request = requestManager.load(config.getUrl());
            Log.e("TAG","getUrl : "+config.getUrl());
        }  else if (config.getResId() > 0) {
            request = requestManager.load(config.getResId());
            Log.e("TAG","getResId : "+config.getResId());
        }
        return request;
    }

    /**
     * 设置加载进入动画
     *
     * @param config
     * @param request
     */
    private void setAnimator(SingleConfig config, DrawableTypeRequest request) {
        if (config.getAnimationType() == AnimationMode.ANIMATIONID) {
            request.animate(config.getAnimationId());
        } else if (config.getAnimationType() == AnimationMode.ANIMATOR) {
            request.animate(config.getAnimator());
        } else if (config.getAnimationType() == AnimationMode.ANIMATION) {
            request.animate(config.getAnimation());
        }
    }


}
