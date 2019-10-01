package nanodegree.sauravchhabra.com.bakingapp.ui.recipe;

import nanodegree.sauravchhabra.com.bakingapp.base.BaseError;
import nanodegree.sauravchhabra.com.bakingapp.model.Recipes;

import java.util.List;

public interface RecipesCallback  extends BaseError {

    void onResponse(List<Recipes> recipesList);

    void onRecipeError();
}
