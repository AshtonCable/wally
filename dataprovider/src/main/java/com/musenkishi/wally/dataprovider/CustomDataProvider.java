package com.musenkishi.wally.dataprovider;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.musenkishi.wally.dataprovider.models.SaveImageRequest;
import com.musenkishi.wally.models.ExceptionReporter;
import com.musenkishi.wally.models.Image;
import com.musenkishi.wally.models.ImagePage;
import com.musenkishi.wally.models.filters.FilterGroupsStructure;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Maher on 9/24/2017.
 *
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
    public void getImages(String path, String query, String color, int index,
                          FilterGroupsStructure filterGroupsStructure,
                          OnImagesReceivedListener onImagesReceivedListener) {

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

    // TODO: Move the below to a new class

    public SaveImageRequest downloadImageIfNeeded(Uri path, String filename, String notificationTitle) {
        FileManager fileManager = new FileManager();

        if (fileManager.fileExists(filename)) {
            File file = fileManager.getFile(filename);
            Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
            return new SaveImageRequest(fileUri);
        } else {

            String type = ".png"; //fallback to ".png"

            DownloadManager.Request request = new DownloadManager.Request(path);
            request.setTitle(notificationTitle);
            request.setVisibleInDownloadsUi(false);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.allowScanningByMediaScanner();
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "/Wally/" + filename + type);

            return new SaveImageRequest(downloadManager.enqueue(request));
        }
    }

    public Uri getFilePath(String filename) {
        FileManager fileManager = new FileManager();
        if (fileManager.fileExists(filename)) {
            File file = fileManager.getFile(filename);
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        }
        return null;
    }
}
