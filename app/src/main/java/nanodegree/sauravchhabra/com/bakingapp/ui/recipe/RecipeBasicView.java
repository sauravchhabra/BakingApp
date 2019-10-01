package nanodegree.sauravchhabra.com.bakingapp.ui.recipe;

import nanodegree.sauravchhabra.com.bakingapp.base.RequiredView;
import nanodegree.sauravchhabra.com.bakingapp.model.Recipes;

import java.util.List;

public interface RecipeBasicView extends RequiredView {

    void showRecipes(List<Recipes> recipesList);
}
