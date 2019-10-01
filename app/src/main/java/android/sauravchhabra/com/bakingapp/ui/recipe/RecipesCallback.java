package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.sauravchhabra.com.bakingapp.base.BaseError;
import android.sauravchhabra.com.bakingapp.model.Recipes;

import java.util.List;

public interface RecipesCallback  extends BaseError {

    void onResponse(List<Recipes> recipesList);

    void onRecipeError();
}
