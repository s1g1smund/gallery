package com.example.manager.photos.galantebudgallery.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.manager.photos.galantebudgallery.R;
import com.example.manager.photos.galantebudgallery.adapters.ImageAdapter;
import com.example.manager.photos.galantebudgallery.constants.GalleryConstants;
import com.example.manager.photos.galantebudgallery.dto.MyImage;
import com.example.manager.photos.galantebudgallery.utils.GalleryUtils;

import java.util.ArrayList;

public class ImagesFragment extends Fragment{

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private ArrayList<MyImage> images;
    private GalleryUtils utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    private Uri mCapturedImageURI;

    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_layout, container, false);

        images = new ArrayList();
        utils = new GalleryUtils(getActivity());
        gridView = (GridView) view.findViewById(R.id.grid_view);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "Pozycja: "+position, Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imgList",getPaths());
                bundle.putInt("position", position);

                FullScreenImageFragment fullImage = new FullScreenImageFragment();
                fullImage.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout, fullImage);
                ft.addToBackStack(null);
                //ft.show(getFragmentManager().findFragmentById(R.id.the_fragg));
                //ft.addToBackStack(null);
                ft.commit();
            }
        });

        btn = (Button) view.findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddImage();
            }
        });

        // Initilizing Grid View
        InitilizeGridLayout();

        refreshView();

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) { // TO DO
//
//                // Sending image id to FullScreenActivity
//                Intent i = new Intent(getActivity(), FullImageActivity.class);
//                // passing array index
//                i.putExtra("id", position);
//                startActivity(i);
//            }
//        });

        return view;
    }

    private ArrayList<String> getPaths(){
        ArrayList<String> list = new ArrayList<>();
        for (MyImage image :
                images) {
            list.add(image.getPath());
        }
        return list;
    }

    public void onImageClick(int position){

    }

    public static ImagesFragment newInstance() {
        ImagesFragment f = new ImagesFragment();
        return f;
    }

    private void refreshView(){
        adapter = new ImageAdapter(getActivity(), images, columnWidth);
        gridView.setAdapter(adapter);
    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GalleryConstants.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((GalleryConstants.NUM_OF_COLUMNS + 1) * padding)) / GalleryConstants.NUM_OF_COLUMNS);

        gridView.setNumColumns(GalleryConstants.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    public void btnAddImage() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.gallery_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
                refreshView();
            }
        });
        dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                activeGallery();
            }
        });
        dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTakePhoto();
            }
        });

        // show dialog on screen
        dialog.show();
    }


    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    MyImage image = new MyImage();
                    image.setTitle("Test");
                    image.setDescription("test choose a photo from gallery and add it to list view");
                    image.setPath(picturePath);
                    images.add(image);
                    /*Uri imgUri = data.getData();
                    String realUrl = getRealPathFromURI(imgUri);

                    Toast.makeText(getApplicationContext(), realUrl, Toast.LENGTH_LONG).show();

                    MyImage image = new MyImage();
                    image.setPath(realUrl);
                    image.setDescription("Description v1 - gallery");
                    image.setTitle("Title v1 - gallery");
                    images.add(image);*/
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturePath = cursor.getString(column_index_data);
                    MyImage image = new MyImage();
                    image.setTitle("Test");
                    image.setDescription("test take a photo and add it to list view");
                    image.setPath(picturePath);
                    images.add(image);
                    /*Uri selectedImage = data.getData();

                    String realUrl = getRealPathFromURI(selectedImage);
                    Toast.makeText(getApplicationContext(), realUrl, Toast.LENGTH_LONG).show();

                    MyImage image = new MyImage();
                    image.setPath(realUrl);
                    image.setDescription("Description v1 - take photo");
                    image.setTitle("Title v1 - take photo");
                    images.add(image);*/
                }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
