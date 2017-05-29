package com.yangshao.image.utils;

import android.app.Activity;

import com.yangshao.utils.L_;
import com.yangshao.utils.T_;

import java.io.File;

/**
 * Created by admin on 2017/5/11.
 */

public class Test extends Activity{

    /**
     * 压缩单张图片 Listener 方式
     */
    private void compressWithLs(File file) {
        CompressUtils.get(this)
                .load(file)
                .putGear(CompressUtils.THIRD_GEAR)
                .setFilename(System.currentTimeMillis() + "")
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //Toast.makeText(MainActivity.this, "I'm start", Toast.LENGTH_SHORT).show();
                        T_.showToastReal("开始压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        L_.e("压缩后：图片大小" + file.length() / 1024 + "k"+file.getAbsolutePath());
                        //fileList.add(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

}
