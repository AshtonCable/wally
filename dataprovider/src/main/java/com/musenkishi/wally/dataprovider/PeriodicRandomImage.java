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
import com.musenkishi.wally.models.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Maher on 10/6/2017.
 * This class is responsible for setting a random wallpaper after reading it from server
 */

public class PeriodicRandomImage {

    private static Image getRandomImage(ArrayList<Image> images) {
        Random rand = new Random();
        int max = images.size() - 1;
        int min = 0;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return images.get(randomNum);
    }

    public static void setRandomWallpaper(final Context context) {
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
