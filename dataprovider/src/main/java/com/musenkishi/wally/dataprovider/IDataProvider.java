package com.musenkishi.wally.dataprovider;

import android.net.Uri;

import com.musenkishi.wally.dataprovider.models.DataProviderError;
import com.musenkishi.wally.dataprovider.models.SaveImageRequest;
import com.musenkishi.wally.models.Image;
import com.musenkishi.wally.models.ImagePage;
import com.musenkishi.wally.models.filters.FilterGroupsStructure;

import java.util.ArrayList;

/**
 * Created by Maher on 9/24/2017.
 *
 * implement this interface to provide your own data source
 */

interface IDataProvider {

    ArrayList<Image> getImagesSync(String path, int index,
                                   FilterGroupsStructure filterGroupsStructure);

    void getImages(String path, int index, FilterGroupsStructure filterGroupsStructure,
                   final IDataProvider.OnImagesReceivedListener onImagesReceivedListener);

    ImagePage getPageDataSync(String imagePageUrl);

    void getPageData(String imagePageUrl,
                     final IDataProvider.OnPageReceivedListener onPageReceivedListener);

    interface OnImagesReceivedListener {
        void onImagesReceived(ArrayList<Image> images);

        void onError(DataProviderError dataProviderError);
    }

    interface OnPageReceivedListener {
        void onPageReceived(ImagePage imagePage);

        void onError(DataProviderError dataProviderError);
    }
}