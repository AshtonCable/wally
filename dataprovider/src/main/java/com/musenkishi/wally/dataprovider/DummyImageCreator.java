package com.musenkishi.wally.dataprovider;

import android.net.Uri;
import android.util.Log;

import com.musenkishi.wally.models.Author;
import com.musenkishi.wally.models.Image;
import com.musenkishi.wally.models.ImagePage;
import com.musenkishi.wally.models.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Maher on 9/24/2017.
 * Provides images from a placeholder source to the application
 */

class DummyImageCreator {
    private static final String RESOLUTION = "640x480";

    private static ArrayList<Image> imageArrayList = new ArrayList<>();
    private static HashMap<String, ImagePage> imagesPageHashMap = new HashMap<>();

    private static void addImage(String imageUrl) {
        Image image = Image.create("image", imageUrl,
                imageUrl, RESOLUTION);
        imageArrayList.add(image);
        Uri imagePath = Uri.parse(imageUrl);
        Author author = Author.create("Unknown", Uri.EMPTY);
        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag = Tag.create("tag M");
        tags.add(tag);
        ImagePage imagePage = ImagePage.create("title M", "image", imagePath, RESOLUTION,"category M",
                "rating M", "uploader M", "uploadDate M", author, tags);
        imagesPageHashMap.put(imageUrl , imagePage);
    }

    static ArrayList<Image> getImages() {
        if (imageArrayList.isEmpty()) {
            addImage("https://firebasestorage.googleapis.com/v0/b/wally-image-source.appspot.com/o/sample3.jpg?alt=media&token=44c34882-5c1e-487a-99ed-0d3b49239d37");
            addImage("https://firebasestorage.googleapis.com/v0/b/wally-image-source.appspot.com/o/sample2.jpg?alt=media&token=5656b17a-2a76-4cd6-8f15-f1d7da027ac1");
            addImage("https://firebasestorage.googleapis.com/v0/b/wally-image-source.appspot.com/o/sample1.jpg?alt=media&token=0cc2d174-76dd-4e68-afed-cea68ecaf688");
        }
        return imageArrayList;
    }

    static ImagePage getImagePage(String imagePageUrl) {
        if (imageArrayList.isEmpty()) {
            addImage("https://firebasestorage.googleapis.com/v0/b/wally-image-source.appspot.com/o/sample3.jpg?alt=media&token=44c34882-5c1e-487a-99ed-0d3b49239d37");
            addImage("https://firebasestorage.googleapis.com/v0/b/wally-image-source.appspot.com/o/sample2.jpg?alt=media&token=5656b17a-2a76-4cd6-8f15-f1d7da027ac1");
            addImage("https://firebasestorage.googleapis.com/v0/b/wally-image-source.appspot.com/o/sample1.jpg?alt=media&token=0cc2d174-76dd-4e68-afed-cea68ecaf688");
        }
        return imagesPageHashMap.get(imagePageUrl);
    }
}
