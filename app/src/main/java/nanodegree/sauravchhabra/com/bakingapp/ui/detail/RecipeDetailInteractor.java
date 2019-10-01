package nanodegree.sauravchhabra.com.bakingapp.ui.detail;

import android.content.ContentValues;
import android.content.Context;
import nanodegree.sauravchhabra.com.bakingapp.data.local.BakingContract;
import nanodegree.sauravchhabra.com.bakingapp.data.local.BakingProvider;

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
