package com.musenkishi.wally.dataprovider;

import android.content.Context;

import com.musenkishi.wally.models.Filter;

/**
 * Created by Maher on 9/28/2017.
 *
 * Reads and writes filters in shared preference
 */

public class FilterProvider {
    private SharedPreferencesDataProvider sharedPreferencesDataProvider;

    public FilterProvider(Context context) {
        sharedPreferencesDataProvider = new SharedPreferencesDataProvider(context);
    }

    public SharedPreferencesDataProvider getSharedPreferencesDataProviderInstance() {
        return sharedPreferencesDataProvider;
    }


    public void setTimeSpan(String tag, Filter<String, String> timespan) {
        sharedPreferencesDataProvider.setTimespan(tag, timespan);
    }

    public Filter<String, String> getTimespan(String tag) {
        return sharedPreferencesDataProvider.getTimespan(tag);
    }

    public void setBoards(String tag, String paramValue) {
        sharedPreferencesDataProvider.setBoards(tag, paramValue);
    }

    public String getBoards(String tag) {
        return sharedPreferencesDataProvider.getBoards(tag);
    }

    public void setPurity(String tag, String paramValue) {
        sharedPreferencesDataProvider.setPurity(tag, paramValue);
    }

    public String getPurity(String tag) {
        return sharedPreferencesDataProvider.getPurity(tag);
    }

    public void setAspectRatio(String tag, Filter<String, String> aspectRatio) {
        sharedPreferencesDataProvider.setAspectRatio(tag, aspectRatio);
    }

    public Filter<String, String> getAspectRatio(String tag) {
        return sharedPreferencesDataProvider.getAspectRatio(tag);
    }

    public void setResolutionOption(String tag, String paramValue) {
        sharedPreferencesDataProvider.setResolutionOption(tag, paramValue);
    }

    public String getResolutionOption(String tag) {
        return sharedPreferencesDataProvider.getResolutionOption(tag);
    }

    public void setResolution(String tag, Filter<String, String> resolution) {
        sharedPreferencesDataProvider.setResolution(tag, resolution);
    }

    public Filter<String, String> getResolution(String tag) {
        return sharedPreferencesDataProvider.getResolution(tag);
    }

}
