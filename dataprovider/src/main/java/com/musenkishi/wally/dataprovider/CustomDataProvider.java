package com.musenkishi.wally.dataprovider;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.musenkishi.wally.models.ExceptionReporter;
import com.musenkishi.wally.models.Image;
import com.musenkishi.wally.models.ImagePage;
import com.musenkishi.wally.models.filters.FilterGroupsStructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Maher on 9/24/2017.
 * <p>
 * Custom data provider that reads from any html source if it is properly formatted
 */

public class CustomDataProvider implements IDataProvider {
    private final Context context;
    private DownloadManager downloadManager;

    public CustomDataProvider(Context context, ExceptionReporter.OnReportListener onReportListener) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.context = context;
    }

    @Override
    public ArrayList<Image> getImagesSync(String path, int index,
                                          FilterGroupsStructure filterGroupsStructure) {
        return ImageCreator.getImages();
    }

    @Override
    public void getImages(String path, int index, FilterGroupsStructure filterGroupsStructure,
                          final OnImagesReceivedListener onImagesReceivedListener) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onImagesReceivedListener != null) {
                    ImageCreator.fillData(dataSnapshot);
                    onImagesReceivedListener.onImagesReceived(ImageCreator.getImages());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ImagePage getPageDataSync(String imagePageUrl) {
        return ImageCreator.getImagePage(imagePageUrl);
    }

    @Override
    public void getPageData(String imagePageUrl, OnPageReceivedListener onPageReceivedListener) {
        if (onPageReceivedListener != null) {
            onPageReceivedListener.onPageReceived(ImageCreator.getImagePage(imagePageUrl));
        }
    }

    private Image getRandomImage(ArrayList<Image> images) {
        Random rand = new Random();
        int max = images.size() - 1;
        int min = 0;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return images.get(randomNum);
    }

    public void setRandomWallpaper() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImageCreator.fillData(dataSnapshot);
                Image selectedImage = getRandomImage(ImageCreator.getImages());
                String imagePageURL = selectedImage.imagePageURL();
                Glide.with(context)
                        .load(imagePageURL)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                try {
                                    WallpaperManager.getInstance(context).setBitmap(resource);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
