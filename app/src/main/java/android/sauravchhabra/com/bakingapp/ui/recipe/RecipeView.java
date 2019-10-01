package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.sauravchhabra.com.bakingapp.base.BaseView;
import android.sauravchhabra.com.bakingapp.model.Recipes;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple class to set the basic view
 */
public class RecipeView implements BaseView<RecipeBasicView>, RecipesCallback {

    RecipeBasicView mRecipeBasicView;
    RecipeInteractor mRecipeInteractor;

    @Inject
    public RecipeView(RecipeBasicView recipeBasicView, RecipeInteractor recipeInteractor) {
        mRecipeBasicView = recipeBasicView;
        mRecipeInteractor = recipeInteractor;
    }

    @Override
    public void onNetworkError() {
        mRecipeBasicView.showNetworkConnectionError();

    }

    @Override
    public void onServerError() {
        mRecipeBasicView.showServerError();
    }

    @Override
    public void setView(RecipeBasicView view) {
        if (view == null) {
            throw new IllegalArgumentException("This is an empty view");
        }
    }

    @Override
    public void removeView() {
        mRecipeBasicView = null;
    }

    @Override
    public void onResponse(List<Recipes> recipesList) {
        mRecipeBasicView.hideLoading();
        mRecipeBasicView.showRecipes(recipesList);
    }

    @Override
    public void onRecipeError() {
        mRecipeBasicView.showErrorMessage();
    }

    void handleScreenRotation(List<Recipes> recipesList) {
        mRecipeBasicView.hideLoading();
        mRecipeBasicView.showRecipes(recipesList);
    }

    void getRecipes() {
        mRecipeBasicView.showLoading();
        mRecipeInteractor.loadRecipesFromApi(this);
    }

    void handleScreeRotation(List<Recipes> recipeList) {
        mRecipeBasicView.hideLoading();
        mRecipeBasicView.showRecipes(recipeList);
    }

}
