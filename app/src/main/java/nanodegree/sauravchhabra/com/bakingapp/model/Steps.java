package nanodegree.sauravchhabra.com.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A simple class which implements serializable to save the data
 */
public class Steps implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String videoURL;

    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    //Public getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id1) {
        id = id1;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription1) {
        shortDescription = shortDescription1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description1) {
        description = description1;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL1) {
        videoURL = videoURL1;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL1) {
        thumbnailURL = thumbnailURL1;
    }

}
