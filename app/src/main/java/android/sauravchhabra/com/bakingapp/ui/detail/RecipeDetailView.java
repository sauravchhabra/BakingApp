package android.sauravchhabra.com.bakingapp.ui.detail;

import android.sauravchhabra.com.bakingapp.base.BaseView;
import android.sauravchhabra.com.bakingapp.ui.recipe.RecipeView;

import javax.inject.Inject;

public class RecipeDetailView implements BaseView<RecipeDetail> {

    private RecipeDetail mRecipeDetail;
    private RecipeDetailInteractor mRecipesDetailInteractor;

    @Inject
    public RecipeDetailView(RecipeDetail recipeDetail, RecipeDetailInteractor recipeDetailInteractor){
        mRecipeDetail = recipeDetail;
        mRecipesDetailInteractor = recipeDetailInteractor;
    }

    @Override
    public void setView(RecipeDetail view) {
        if (view == null)
            throw new IllegalArgumentException("This is an empty view");
        mRecipeDetail = view;

    }

    @Override
    public void removeView() {
        mRecipeDetail = null;

    }

    void addIngredientToList(String recipe, String quantity){
        mRecipesDetailInteractor.addIngredient(recipe, quantity);
    }
}
