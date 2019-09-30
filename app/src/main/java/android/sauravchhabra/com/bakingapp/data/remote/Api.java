package android.sauravchhabra.com.bakingapp.data.remote;

import retrofit2.http.GET;

public interface Api {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<RecipeResponse>> getRecipes();

    //TODO: Fix this error
}
