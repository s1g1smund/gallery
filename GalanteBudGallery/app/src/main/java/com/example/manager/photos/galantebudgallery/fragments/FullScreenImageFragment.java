package com.example.manager.photos.galantebudgallery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manager.photos.galantebudgallery.R;
import com.example.manager.photos.galantebudgallery.adapters.FullScreenImageAdapter;
import com.example.manager.photos.galantebudgallery.utils.GalleryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullScreenImageFragment extends Fragment {

    private GalleryUtils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    ArrayList<String> images;

    //private Context mContext;
//    private Integer[] mImages = {
//            R.drawable.pic_1, R.drawable.pic_2,
//            R.drawable.pic_3, R.drawable.pic_4,
//            R.drawable.pic_5, R.drawable.pic_6,
//            R.drawable.pic_7, R.drawable.pic_8,
//            R.drawable.pic_9, R.drawable.pic_10,
//            R.drawable.pic_11, R.drawable.pic_12,
//            R.drawable.pic_13
//    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_full_screen_image, container, false);


        Bundle bundle = getArguments();
        //String[] myStrings = bundle.getStringArray("images");
        images = bundle.getStringArrayList("imgList");
        int position = bundle.getInt("position");

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        utils = new GalleryUtils(getActivity());


        adapter = new FullScreenImageAdapter(getActivity(), images); // TO DO
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        return view;
    }
}
