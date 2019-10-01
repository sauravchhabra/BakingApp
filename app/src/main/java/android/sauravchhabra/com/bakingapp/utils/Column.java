package android.sauravchhabra.com.bakingapp.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * A simple class to calculate the number of columns in a given display size
 */
public class Column {

    public static int getNumberOfColumns(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 500;
        int width = displayMetrics.widthPixels;
        return width/widthDivider;
    }
}
