package nanodegree.sauravchhabra.com.bakingapp.utils;

import nanodegree.sauravchhabra.com.bakingapp.R;

/**
 * A simple class to get the image resource
 */
public class RecipeImage {

    public static int getRecipeImage(int id) {
        int drawable;
        switch (id) {
            case 1:
                drawable =
                        R.drawable.nutella_pie;
                break;
            case 2:
                drawable = R.drawable.brownies;
                break;
            case 3:
                drawable = R.drawable.yellow_cake;
                break;
            case 4:
                drawable = R.drawable.cheesecake;
                break;

            default:
                drawable = R.drawable.ic_launcher_foreground;
        }
        return drawable;
    }
}
