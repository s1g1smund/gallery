package com.example.manager.photos.galantebudgallery;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.manager.photos.galantebudgallery.dto.MyImage;
import com.example.manager.photos.galantebudgallery.fragments.FullScreenImageFragment;
import com.example.manager.photos.galantebudgallery.fragments.ImagesFragment;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<MyImage> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new ArrayList();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ImagesFragment imagesFragment = new ImagesFragment();
        fragmentTransaction.replace(R.id.frame_layout, imagesFragment);

//        Configuration configInfo = getResources().getConfiguration();
//        if(configInfo.orientation == Configuration.ORIENTATION_LANDSCAPE){
//
//        }
//        else{
//            ImagesFragment imagesFragment = new ImagesFragment();
//            fragmentTransaction.replace(R.id.linear_layout_fragments, imagesFragment);
//        }
        fragmentTransaction.commit();
    }
}

