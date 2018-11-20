package com.example.supjain.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * This class object holds all the recipe step details for each step of a recipe.
 */
public class RecipeStepsData implements Parcelable {

    public static final Creator<RecipeStepsData> CREATOR = new Creator<RecipeStepsData>() {
        @Override
        public RecipeStepsData createFromParcel(Parcel in) {
            return new RecipeStepsData(in);
        }

        @Override
        public RecipeStepsData[] newArray(int size) {
            return new RecipeStepsData[size];
        }
    };
    @SerializedName("id")
    private int stepId;
    @SerializedName("shortDescription")
    private String stepShortDescription;
    @SerializedName("description")
    private String stepDescription;
    @SerializedName("videoURL")
    private String stepVideoUrl;
    @SerializedName("thumbnailURL")
    private String stepThumbnailUrl;

    public RecipeStepsData(int stepId, String stepShortDescription, String stepDescription,
                           String stepVideoUrl, String stepThumbnailUrl) {
        this.stepId = stepId;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
        this.stepVideoUrl = stepVideoUrl;
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    protected RecipeStepsData(Parcel in) {
        stepId = in.readInt();
        stepShortDescription = in.readString();
        stepDescription = in.readString();
        stepVideoUrl = in.readString();
        stepThumbnailUrl = in.readString();
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getStepShortDescription() {
        return stepShortDescription;
    }

    public void setStepShortDescription(String stepShortDescription) {
        this.stepShortDescription = stepShortDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepVideoUrl() {
        return stepVideoUrl;
    }

    public void setStepVideoUrl(String stepVideoUrl) {
        this.stepVideoUrl = stepVideoUrl;
    }

    public String getStepThumbnailUrl() {
        return stepThumbnailUrl;
    }

    public void setStepThumbnailUrl(String stepThumbnailUrl) {
        this.stepThumbnailUrl = stepThumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(stepShortDescription);
        dest.writeString(stepDescription);
        dest.writeString(stepVideoUrl);
        dest.writeString(stepThumbnailUrl);
    }
}
