package com.yangshao.image;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.yangshao.image.utils.AnimationMode;
import com.yangshao.image.utils.ShapeMode;
import com.yangshao.utils.UIUtils;
import java.io.File;

public class SingleConfig {
    private String url;

    private float thumbnail; //缩略图缩放倍数
    private String filePath; //文件路径
    private File file; //文件路径
    private int resId;  //资源id
    private View target;
    private int oWidth;
    private int oHeight;
    private int animationType;
    private int animationId;
    private Animation animation;
    private ViewPropertyAnimation.Animator animator;
    private int blurRadius;
    private int placeHolderResId;
    private int errorResId;

    private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    private int rectRoundRadius;//圆角矩形时圆角的半径
    private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...

    private BitmapListener bitmapListener;

    public SingleConfig(ConfigBuilder builder) {
        this.url = builder.url;
        this.thumbnail = builder.thumbnail;
        this.filePath = builder.filePath;
        this.file = builder.file;
        this.resId = builder.resId;
        this.target = builder.target;
        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;
        this.shapeMode = builder.shapeMode;
        if (shapeMode == ShapeMode.RECT_ROUND) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.scaleMode = builder.scaleMode;

        this.animationId = builder.animationId;
        this.animationType = builder.animationType;
        this.animator = builder.animator;
        this.animation = builder.animation;
        this.placeHolderResId = builder.placeHolderResId;
        this.errorResId = builder.errorResId;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    private boolean asBitmap;//只获取bitmap



    public File getFile() {
        return file;
    }

    public View getTarget() {
        return target;
    }

    public String getUrl() {
        return url;
    }

    public int getResId() {
        return resId;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public int getScaleMode() {
        return scaleMode;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getAnimationType() {
        return animationType;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


    public int getShapeMode() {
        return shapeMode;
    }


    public void setAnimationType(int animationType) {
        this.animationType = animationType;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setAnimator(ViewPropertyAnimation.Animator animator) {
        this.animator = animator;
    }

    public void setErrorResId(int errorResId) {
        this.errorResId = errorResId;
    }

    public void setShapeMode(int shapeMode) {
        this.shapeMode = shapeMode;
    }

    public void setScaleMode(int scaleMode) {
        this.scaleMode = scaleMode;
    }

    public int getRectRoundRadius() {
        return rectRoundRadius;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public int getAnimationId() {
        return animationId;
    }

    public BitmapListener getBitmapListener() {

        return bitmapListener;
    }

    public float getThumbnail() {
        return thumbnail;
    }


    private void show() {
        GlobalConfig.getLoader().request(this);
    }

    public interface BitmapListener {
        void onSuccess(Bitmap bitmap);

        void onFail();
    }

    public static class ConfigBuilder {
        private Context context;


        /**
         * 图片源
         * 类型	SCHEME	示例
         * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
         * 本地文件	file://	FileInputStream
         * Content provider	content://	ContentResolver
         * asset目录下的资源	asset://	AssetManager
         * res目录下的资源	  res://	Resources.openRawResource
         * Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
         *
         * @param config
         * @return
         */
        private String url;
        private float thumbnail;
        private String filePath;
        private File file;
        private int resId;


        private View target;


        private int oWidth; //选择加载分辨率的宽
        private int oHeight; //选择加载分辨率的高

        //UI:
        private int placeHolderResId;

        private int errorResId;

        private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
        private int rectRoundRadius;//圆角矩形时圆角的半径


        private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...


        public int animationId; //动画资源id
        public int animationType; //动画资源Type
        public Animation animation; //动画资源
        public ViewPropertyAnimation.Animator animator; //动画资源id

        public ConfigBuilder(Context context) {
            this.context = context;
        }


        /**
         * 缩略图
         *
         * @param thumbnail
         * @return
         */
        public ConfigBuilder thumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        /**
         * error图
         *
         * @param errorResId
         * @return
         */
        public ConfigBuilder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 设置网络路径
         *
         * @param url
         * @return
         */
        public ConfigBuilder url(String url) {
            this.url = url;
            return this;
        }


        /**
         * 加载SD卡资源
         *
         * @param file
         * @return
         */
        public ConfigBuilder file(File file) {
            this.file = file;

            return this;
        }

        /**
         * 加载drawable资源
         *
         * @param resId
         * @return
         */
        public ConfigBuilder res(int resId) {
            this.resId = resId;
            return this;
        }


        public void into(View targetView) {
            this.target = targetView;
            new SingleConfig(this).show();
        }


        /**
         * 加载图片的分辨率
         *
         * @param oWidth
         * @param oHeight
         * @return
         */
        public ConfigBuilder override(int oWidth, int oHeight) {
            this.oWidth = UIUtils.dip2px(oWidth);
            this.oHeight = UIUtils.dip2px(oHeight);
            return this;
        }

        /**
         * 占位图
         *
         * @param placeHolderResId
         * @return
         */
        public ConfigBuilder placeHolder(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }


        /**
         * 圆角
         *
         * @return
         */
        public ConfigBuilder asCircle() {
            this.shapeMode = ShapeMode.OVAL;
            return this;
        }

        /**
         * 形状为圆角矩形时的圆角半径
         *
         * @param rectRoundRadius
         * @return
         */
        public ConfigBuilder rectRoundCorner(int rectRoundRadius) {
            this.rectRoundRadius = UIUtils.dip2px(rectRoundRadius);
            this.shapeMode = ShapeMode.RECT_ROUND;
            return this;
        }


        /**
         * 正方形
         *
         * @return
         */
        public ConfigBuilder asSquare() {
            this.shapeMode = ShapeMode.SQUARE;
            return this;
        }


        /**
         * 拉伸/裁剪模式
         *
         * @param scaleMode 取值ScaleMode
         * @return
         */
        public ConfigBuilder scale(int scaleMode) {
            this.scaleMode = scaleMode;
            return this;
        }


        public ConfigBuilder animate(int animationId) {
            this.animationType = AnimationMode.ANIMATIONID;
            this.animationId = animationId;
            return this;
        }

        public ConfigBuilder animate(ViewPropertyAnimation.Animator animator) {
            this.animationType = AnimationMode.ANIMATOR;
            this.animator = animator;
            return this;
        }

        public ConfigBuilder animate(Animation animation) {
            this.animationType = AnimationMode.ANIMATION;
            this.animation = animation;
            return this;
        }


    }


}