package com.example.harry.customandroid.activities.widgets.imageEdit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;
import java.util.UUID;

import me.kareluo.imaging.IMGEditActivity;
import me.kareluo.imaging.IMGGalleryActivity;
import me.kareluo.imaging.gallery.model.IMGChooseMode;
import me.kareluo.imaging.gallery.model.IMGImageInfo;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/8.
 */

public class ImageEditActivity extends BaseActivity {

    private final int REQ_IMAGE_EDIT = 1;
    private final int REQ_IMAGE_CHOOSE = 2;

    private SimpleDraweeView imageOrigin;
    private SimpleDraweeView imageEdit;
    private File mImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_edit;
    }

    @Override
    public int getTitleId() {
        return R.string.image_edit_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageOrigin = findViewById(R.id.sdv_image);
        imageEdit = findViewById(R.id.sdv_image_edit);
        findViewById(R.id.btn_choose_image).setOnClickListener(v -> chooseImage());
    }

    private void chooseImage() {
        Intent intent = IMGGalleryActivity.newIntent(this, new IMGChooseMode.Builder()
                .setSingleChoose(true)
                .build());
        startActivityForResult(intent, REQ_IMAGE_CHOOSE);
    }

    private void onChooseImages(List<IMGImageInfo> images) {
        IMGImageInfo image = images.get(0);
        if (image != null) {
            imageOrigin.setImageURI(image.getUri());
        }

        // Begin to edit
        mImage = new File(getCacheDir(), UUID.randomUUID().toString() + ".jpg");
        Intent intent = new Intent(this, IMGEditActivity.class)
                .putExtra(IMGEditActivity.EXTRA_IMAGE_URI, image.getUri())
                .putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, mImage.getAbsolutePath());
        startActivityForResult(intent, REQ_IMAGE_EDIT);
    }

    private void onImageEditDone() {
        imageEdit.setImageURI(Uri.fromFile(mImage));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_IMAGE_CHOOSE:
                if (resultCode == Activity.RESULT_OK) {
                    onChooseImages(IMGGalleryActivity.getImageInfos(data));
                }
            case REQ_IMAGE_EDIT:
                if (resultCode == Activity.RESULT_OK) {
                    onImageEditDone();
                }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageEditActivity.class);
        context.startActivity(starter);
    }
}
