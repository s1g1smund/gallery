package com.example.manager.photos.galantebudgallery.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.manager.photos.galantebudgallery.dto.MyImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Activity _activity;
    private ArrayList<MyImage> _images = new ArrayList<MyImage>();
    //private Integer[] mImages;
    private int imageWidth;

    public ImageAdapter(Activity activity, ArrayList<MyImage> images, int imageWidth) {
        this._activity = activity;
        this._images = images;
        this.imageWidth = imageWidth;
    }

//    public ImageAdapter(Activity activity, Integer[] mImages, int imageWidth) {
//        this._activity = activity;
//        this.mImages = mImages;
//        this.imageWidth = imageWidth;
//    }

    @Override
    public int getCount() {
        //return this._filePaths.size();
        return this._images.size();
    }

    @Override
    public Object getItem(int position) {
        //return this._filePaths.get(position);
        return this._images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

        // get screen dimensions
        Bitmap image = decodeFile(_images.get(position).getPath(), imageWidth,
                imageWidth);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth)); //+((int)(0.25*imageWidth))
        imageView.setImageBitmap(image);
        ////imageView.setImageResource(mImages[position]);
//        Picasso.with(_activity.getApplicationContext())
//                .load(_images.get(position).getPath())
//                .error(R.drawable.load_image_error)
//                //.resize(imageWidth+((int)(0.25*imageWidth)), imageWidth)
//                .resize(70, 70)
//                .into(imageView);

        // image view click listener
        //imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

//    class OnImageClickListener implements OnClickListener {
//
//        int _postion;
//
//        // constructor
//        public OnImageClickListener(int position) {
//            this._postion = position;
//        }
//
//        @Override
//        public void onClick(View v) {
//            // on selecting grid view image
//            // launch full screen activity
////            Intent i = new Intent(_activity, FullImageActivity.class);
////            i.putExtra("position", _postion);
////            _activity.startActivity(i);
//        }
//    }

    /*
     * Resizing image size
     */
    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {
            File f = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}