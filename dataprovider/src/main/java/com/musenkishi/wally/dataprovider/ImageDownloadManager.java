package com.musenkishi.wally.dataprovider;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.musenkishi.wally.dataprovider.models.SaveImageRequest;

import java.io.File;

/**
 * Created by Maher on 10/6/2017.
 */

public class ImageDownloadManager {
    private final Context context;
    private DownloadManager downloadManager;

    public ImageDownloadManager(Context context){
        this.context = context;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public SaveImageRequest downloadImageIfNeeded(Uri path, String filename, String notificationTitle) {
        FileManager fileManager = new FileManager();

        if (fileManager.fileExists(filename)) {
            File file = fileManager.getFile(filename);
            Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
            return new SaveImageRequest(fileUri);
        } else {

            String type = ".png"; //fallback to ".png"
            if (path.toString().lastIndexOf(".") != -1) { //-1 means there are no punctuations in the path
                type = path.toString().substring(path.toString().lastIndexOf("."));
            }
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
