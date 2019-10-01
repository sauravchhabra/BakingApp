package nanodegree.sauravchhabra.com.bakingapp.ui.detail;

import nanodegree.sauravchhabra.com.bakingapp.base.BaseView;

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
