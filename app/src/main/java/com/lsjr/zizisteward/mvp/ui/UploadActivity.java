package com.lsjr.zizisteward.mvp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.lsjr.zizisteward.R;
import com.lsjr.zizisteward.iinterface.FreshImaCallBack;
import com.lsjr.zizisteward.mvp.presenter.UploadPresenter;
import com.lsjr.zizisteward.mvp.ui.adapter.ImgGridAdapter;
import com.lsjr.zizisteward.mvp.view.IUploadView;
import com.yangshao.utils.FileUtil;
import com.yangshao.utils.L_;
import com.yangshao.utils.T_;
import com.yanzhenjie.album.Album;
import com.ys.lib.base.BaseMvpActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by admin on 2017/5/6.
 */

public class UploadActivity extends BaseMvpActivity<UploadPresenter> implements FreshImaCallBack,IUploadView{

    private static final int REQUEST_CODE_GALLERY=100;  //打开相册
    private static final int REQUEST_CODE_PREVIEW=101; //图片预览

    private GridView gvImage;
    private ImgGridAdapter adapter;
    private ArrayList<String> igList=new ArrayList<>();
    private final static int maxImageSize=5;
    LinearLayout llSend;
    List<File> fileList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gvImage = (GridView) findViewById(R.id.gvImage);
        llSend= (LinearLayout) findViewById(R.id.ll_send);
        adapter = new ImgGridAdapter(this, igList, maxImageSize);
        adapter.setImgShowFresh(this);//实现刷新接口
        gvImage.setAdapter(adapter);
        llSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageFiles();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected UploadPresenter createPresenter() {
        return new UploadPresenter(this);
    }

    public  void uploadImageFiles(){

        for (String image:igList){
            //创建 一个压缩后的路径
            File compressImageCacheFile = FileUtil.createCompressImageCacheFile();
            if (!compressImageCacheFile.exists()){
                T_.showToastReal("文件不存在");
                return;
            }
            //L_.e(compressImageCacheFile.getAbsoluteFile().getAbsolutePath());
            /* 没有压缩的源文件*/
            //Bitmap bitmap=BitmapFactory.decodeFile(image);
            //开始压缩
            //ImageUtils.compressBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), 40, compressImageCacheFile.getAbsolutePath().getBytes(), true);

            //ImageCompress.getCompressImagePaht(image,compressImageCacheFile);
            //ImageUtils.doCompressImage(image,compressImageCacheFile,50);

            Bitmap bitmap = BitmapFactory.decodeFile(image);
            //String codeString = ImageUtils.compressBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), 40, compressImageCacheFile.getAbsolutePath().getBytes(), true);
            //String codeString = ImageUtils.compressBitmap(bitmap,compressImageCacheFile);
            //ImageUtils.getImageCompress(image,compressImageCacheFile);
            File file=new File(image);
            L_.e("压缩前：图片大小" + file.length() / 1024 + "k");
            compressWithLs(file);

        }

       //s createPresenter().onUploadImage(fileList);

    }


    /**
     * 压缩单张图片 Listener 方式
     */
    private void compressWithLs(File file) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> pathList = Album.parseResult(data);
                    L_.e(pathList.toString());
                    igList.clear();//不可直接指向
                    igList.addAll(pathList);
                    L_.e(igList.toString());
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void initView() {


    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int loadViewLayout() {
        return R.layout.upload_main;
    }


    @Override
    public void previewImag(int position) {
        Album.gallery(this)//预览图片
                .requestCode(REQUEST_CODE_PREVIEW)
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .checkedList(igList)
                .currentPosition(position)
                .checkFunction(false)
                .start();
    }

    @Override
    public void updateGvIgShow(int postition) {
        igList.remove(postition);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openGallery() {
        Album.album(this)//打开相册
                .requestCode(REQUEST_CODE_GALLERY)
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .selectCount(maxImageSize)
                .columnCount(3)
                .camera(true)
                .checkedList(igList)
                .start();
    }

    @Override
    public void onUploadSucceed(boolean isSucceeds) {
        //FileUtil.deleteFile(FileUtil.getTempPahtFile());
        L_.e("onUploadSucceed"+isSucceeds);
    }
}
