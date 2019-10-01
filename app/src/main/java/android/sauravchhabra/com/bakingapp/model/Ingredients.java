package android.sauravchhabra.com.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A simple class which implements serializable to save the data
 */
public class Ingredients implements Serializable {

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    //Public getters and setters
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity1) {
        quantity = quantity1;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure1) {
        measure = measure1;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient1) {
        ingredient = ingredient1;
    }
}
