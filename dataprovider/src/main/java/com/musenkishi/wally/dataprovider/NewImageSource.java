package com.musenkishi.wally.dataprovider;

import android.net.Uri;

import com.musenkishi.wally.models.Author;
import com.musenkishi.wally.models.Image;
import com.musenkishi.wally.models.ImagePage;
import com.musenkishi.wally.models.Tag;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Maher on 10/14/2017.
 * An image source class with hard-coded URL to illustrate how to change the source of the app
 */

class NewImageSource {
    private static ArrayList<Image> imageArrayList = new ArrayList<>();
    private static HashMap<String, ImagePage> imagesPageHashMap = new HashMap<>();

    private static void addImage(String imageId, String thumbURL, String imageURL, String resolution) {
        Image image = Image.create(imageId, thumbURL,
                imageURL, resolution);
        imageArrayList.add(image);
    }

    private static void addPage(String title, String imageId, String imageURL, String resolution, String category, String rating, String uploader, String uploadDate, String authorName, ArrayList<String> tagStrings) {
        Uri imagePath = Uri.parse(imageURL);
        Author author = Author.create(authorName, Uri.EMPTY);
        ArrayList<Tag> tags = new ArrayList<>();
        for (String tagString : tagStrings) {
            Tag tag = Tag.create(tagString);
            tags.add(tag);
        }
        ImagePage imagePage = ImagePage.create(title, imageId, imagePath, resolution, category,
                rating, uploader, uploadDate, author, tags);
        imagesPageHashMap.put(imageURL, imagePage);
    }

    static void fillData() {
        if (imageArrayList.isEmpty()) {

            ArrayList<String> imagesUrl = new ArrayList<>();
            imagesUrl.add("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Swaziland_landscape.jpg/1280px-Swaziland_landscape.jpg");
            imagesUrl.add("https://upload.wikimedia.org/wikipedia/commons/9/9e/Balaton_Hungary_Landscape.jpg");
            imagesUrl.add("https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/Pantheon_%288350771972%29.jpg/1200px-Pantheon_%288350771972%29.jpg");
            imagesUrl.add("https://static.pexels.com/photos/21787/pexels-photo.jpg");

            final String IMAGE_ID = "id";
            final String RESOLUTION = "512x512";
            final String CATEGORY = "category";
            final String RATE = "rate";
            final String UPLOAD = "upload";
            final String DATE = "date";
            final String AUTHOR_NAME = "author_name";
            ArrayList<String> tags = new ArrayList<>();
            tags.add("tag1");
            tags.add("tag2");

            for (String url : imagesUrl) {
                addImage(IMAGE_ID, url, url, RESOLUTION);
                addPage("", IMAGE_ID, url, RESOLUTION, "category", "rate", "upload", "date", "authorname", tags);
            }
        }
    }

    static ArrayList<Image> getImages() {
        return imageArrayList;
    }

    static ImagePage getImagePage(String imagePageUrl) {
        return imagesPageHashMap.get(imagePageUrl);
    }
}
