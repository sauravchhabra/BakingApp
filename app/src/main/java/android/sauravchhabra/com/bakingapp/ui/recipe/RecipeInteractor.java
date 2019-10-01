package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.sauravchhabra.com.bakingapp.data.remote.Api;
import android.sauravchhabra.com.bakingapp.model.Recipes;
import android.sauravchhabra.com.bakingapp.utils.WebsiteNotFound;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple class to load the data from the API and show either the result or error based on the
 * response
 */
public class RecipeInteractor {

    Api mApi;

    @Inject
    public RecipeInteractor(Api api1) {
        mApi = api1;
    }

    public void loadRecipesFromApi(RecipesCallback callback) {
        mApi.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> onSuccess(response, callback),
                        error -> onError(error, callback));
    }

    private void onSuccess(List<Recipes> response, RecipesCallback callback) {
        if (response != null) {
            if (response.size() > 0) {
                callback.onResponse(response);
            } else {
                callback.onRecipeError();
            }
        } else {
            callback.onRecipeError();
        }
    }

    private void onError(Throwable t, RecipesCallback callback) {
        if (WebsiteNotFound.isHttpError(t)) {
            callback.onRecipeError();
        } else if (t.getMessage().equals(WebsiteNotFound.ERROR)) {
            callback.onNetworkError();
        } else {
            callback.onServerError();
        }
    }
}
