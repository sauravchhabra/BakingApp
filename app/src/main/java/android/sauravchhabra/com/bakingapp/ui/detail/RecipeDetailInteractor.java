package android.sauravchhabra.com.bakingapp.ui.detail;

import android.content.ContentValues;
import android.content.Context;
import android.sauravchhabra.com.bakingapp.data.local.BakingContract;
import android.sauravchhabra.com.bakingapp.data.local.BakingProvider;
import android.sauravchhabra.com.bakingapp.utils.Constants;

import javax.inject.Inject;

public class RecipeDetailInteractor {
    private Context mContext;

    @Inject
    public RecipeDetailInteractor(Context context){
        mContext = context;
    }

    void addIngredient(String name, String quantity){
        ContentValues contentValues = new ContentValues();

        contentValues.put(BakingContract.RECIPE_NAME, name);
        contentValues.put(BakingContract.QUANTITY_MEASURE, quantity);

        mContext.getContentResolver().insert(BakingProvider.Recipe.CONTENT_URI, contentValues);
    }
}
