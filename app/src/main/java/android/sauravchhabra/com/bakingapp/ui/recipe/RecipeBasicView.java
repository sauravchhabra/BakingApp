package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.sauravchhabra.com.bakingapp.base.RequiredView;
import android.sauravchhabra.com.bakingapp.model.Recipes;

import java.util.List;

public interface RecipeBasicView extends RequiredView {

    void showRecipes(List<Recipes> recipesList);
}
