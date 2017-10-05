package com.musenkishi.wally.dataprovider;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.musenkishi.wally.models.Author;
import com.musenkishi.wally.models.Image;
import com.musenkishi.wally.models.ImagePage;
import com.musenkishi.wally.models.Tag;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maher on 9/24/2017.
 * Provides images from an external source to the application
 */

class ImageCreator {

    private static ArrayList<Image> imageArrayList = new ArrayList<>();
    private static HashMap<String, ImagePage> imagesPageHashMap = new HashMap<>();

    private ImageCreator() {

    }

    private static void addPage(String title, String imageId, String imageURL, String resolution, String category, String rating, String uploader, String uploadDate, String authorName, ArrayList<String> tagStrings) {
        Uri imagePath = Uri.parse(imageURL);
        Author author = Author.create(authorName, Uri.EMPTY);
        ArrayList<Tag> tags = new ArrayList<>();
        for(String tagString: tagStrings){
            Tag tag = Tag.create(tagString);
            tags.add(tag);
        }
        ImagePage imagePage = ImagePage.create(title, imageId, imagePath, resolution, category,
                rating, uploader, uploadDate, author, tags);
        imagesPageHashMap.put(imageURL, imagePage);
    }

    private static void addImage(String imageId, String imageURL, String resolution) {
        Image image = Image.create(imageId, imageURL,
                imageURL, resolution);
        imageArrayList.add(image);
    }

    static ArrayList<Image> getImages() {
        return imageArrayList;
    }

    static void fillData(DataSnapshot imagesDataSnapshot) {
        if (imageArrayList.isEmpty()) {
            for (DataSnapshot providerDataSnapshot : imagesDataSnapshot.getChildren()) {
                String author = (String) providerDataSnapshot.child("author").getValue();
                String category = (String) providerDataSnapshot.child("category").getValue();
                String rating = (String) providerDataSnapshot.child("rating").getValue();
                String resolution = (String) providerDataSnapshot.child("resolution").getValue();
                String title = (String) providerDataSnapshot.child("title").getValue();
                String imageId = (String) providerDataSnapshot.child("uid").getValue();
                String uploadDate = (String) providerDataSnapshot.child("upload_date").getValue();
                String uploader = (String) providerDataSnapshot.child("uploader").getValue();
                String imageUrl = (String) providerDataSnapshot.child("url").getValue();
                ArrayList<String> tagsString = new ArrayList<>();
                for (DataSnapshot tagsDataSnapshot : providerDataSnapshot.child("tags").getChildren()) {
                    String tag = tagsDataSnapshot.getKey();
                    tagsString.add(tag);
                }
                addImage(imageId,imageUrl,resolution);
                addPage(title,imageId,imageUrl,resolution,category,rating,uploader,uploadDate,author,tagsString);
            }
        }
    }

    static ImagePage getImagePage(String imagePageUrl) {
        return imagesPageHashMap.get(imagePageUrl);
    }
}
