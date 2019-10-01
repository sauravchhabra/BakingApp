package nanodegree.sauravchhabra.com.bakingapp.data.remote;

import nanodegree.sauravchhabra.com.bakingapp.model.Recipes;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<Recipes>> getRecipes();
}
