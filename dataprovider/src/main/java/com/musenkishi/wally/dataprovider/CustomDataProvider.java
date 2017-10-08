package com.musenkishi.wally.dataprovider;

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
 * This class provides all the information needed to switch the app to a new source of images. Above
 * each method in this class there is a comment explaining what needs to be changed to have the app
 * read from your own built image source.
 */

public class CustomDataProvider implements IDataProvider {
    private final Context context;

    /*
     * Do not change the constructor
     */
    public CustomDataProvider(Context context, ExceptionReporter.OnReportListener onReportListener) {
        this.context = context;
    }

    /*
     * This method should always return an ArrayList<Image> where all images source info is
     * available
     */
    @Override
    public ArrayList<Image> getImagesSync(String path, int index,
                                          FilterGroupsStructure filterGroupsStructure) {
        return ImageCreator.getImages();
    }

    /*
     * This method pass the same ArrayList<Image> as the one returned in getImageSync(...) method
     * to the function call on the parameter onImagesReceivedListener
     * e.g: onImagesReceivedListener.onImagesReceived(ImageCreator.getImages());
     */
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

    /*
     * This method should always return an ImagePage instance using imagePageUrl as a key.
     * Normally, you can fetch a hashMap using the url as a key to get the ImagePage instance.
     */
    @Override
    public ImagePage getPageDataSync(String imagePageUrl) {
        return ImageCreator.getImagePage(imagePageUrl);
    }

    /*
     * This method pass the same ImagePage instance as the one returned in getPageDataSync(...)
     * method to the function call on the parameter onPageReceivedListener.
     * e.g: onPageReceivedListener.onPageReceived(ImageCreator.getImagePage(imagePageUrl));
     */
    @Override
    public void getPageData(String imagePageUrl, OnPageReceivedListener onPageReceivedListener) {
        if (onPageReceivedListener != null) {
            onPageReceivedListener.onPageReceived(ImageCreator.getImagePage(imagePageUrl));
        }
    }

    /*
     * This method is used to provide periodic wallpaper when this feature is enabled in the app.
     * It is called automatically when it is time to load a new wallpaper. Your job is only to
     * provide an image URL to the function setWallpaperFromUrl(...).
     */
    public void setRandomWallpaper() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImageCreator.fillData(dataSnapshot);
                Image selectedImage = getRandomImage(ImageCreator.getImages());
                String imagePageURL = selectedImage.imagePageURL();
                setWallpaperFromUrl(imagePageURL);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
     * Do not change.
     * This method is responsible of setting a wallpaper based on a provided URL
     */
    private void setWallpaperFromUrl(String imagePageURL) {
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

    /*
     * Helper method to get a random image from list of images.
     * Feel free to change or delete this function to based on your need.
     */
    private Image getRandomImage(ArrayList<Image> images) {
        Random rand = new Random();
        int max = images.size() - 1;
        int min = 0;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return images.get(randomNum);
    }
}
