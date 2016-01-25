//package com.example.manager.photos.galantebudgallery;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.widget.ImageView;
//
//import com.example.manager.photos.galantebudgallery.adapters.FullScreenImageAdapter;
//import com.example.manager.photos.galantebudgallery.R;
//import com.example.manager.photos.galantebudgallery.utils.GalleryUtils;
//
//import java.util.ArrayList;
//
//public class FullImageActivity extends Activity {
//
//    private GalleryUtils utils;
//    private FullScreenImageAdapter adapter;
//    private ViewPager viewPager;
//
//    private Integer[] mImages = {
//            R.drawable.pic_1, R.drawable.pic_2,
//            R.drawable.pic_3, R.drawable.pic_4,
//            R.drawable.pic_5, R.drawable.pic_6,
//            R.drawable.pic_7, R.drawable.pic_8,
//            R.drawable.pic_9, R.drawable.pic_10,
//            R.drawable.pic_11, R.drawable.pic_12,
//            R.drawable.pic_13
//    };
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_full_screen_image);
//
//        viewPager = (ViewPager) findViewById(R.id.pager);
//
//        utils = new GalleryUtils(getApplicationContext());
//
//        Intent i = getIntent();
//        int position = i.getIntExtra("position", 0);
//
//        adapter = new FullScreenImageAdapter(FullImageActivity.this,
//                mImages);
//
//        viewPager.setAdapter(adapter);
//
//        // displaying selected image first
//        viewPager.setCurrentItem(position);
//    }
//}
