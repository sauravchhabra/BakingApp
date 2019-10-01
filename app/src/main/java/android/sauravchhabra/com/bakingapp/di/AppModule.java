package android.sauravchhabra.com.bakingapp.di;

import android.app.Application;
import android.content.Context;
import android.sauravchhabra.com.bakingapp.BuildConfig;
import android.sauravchhabra.com.bakingapp.data.remote.Api;
import android.sauravchhabra.com.bakingapp.ui.detail.RecipeDetail;
import android.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailFragment;
import android.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailInteractor;
import android.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailView;
import android.sauravchhabra.com.bakingapp.ui.recipe.RecipeBasicView;
import android.sauravchhabra.com.bakingapp.ui.recipe.RecipeInteractor;
import android.sauravchhabra.com.bakingapp.ui.recipe.RecipeListFragment;
import android.sauravchhabra.com.bakingapp.ui.recipe.RecipeView;
import android.sauravchhabra.com.bakingapp.utils.Constants;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import net.simonvt.schematic.annotation.ContentUri;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple class to inject the dependency framework using dagger and reduce boilerplate code
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    Api getApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(Api.class);
    }

    @Provides
    Context getContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    RecipeBasicView getRecipesBasicView() {
        return new RecipeListFragment();
    }

    @Provides
    RecipeInteractor getRecipeInteractor(Api api) {
        return new RecipeInteractor(api);
    }

    @Provides
    RecipeView getRecipeProvider(RecipeBasicView recipeBasicView, RecipeInteractor recipeInteractor) {
        return new RecipeView(recipeBasicView, recipeInteractor);
    }

    @Provides
    RecipeDetail getRecipeDetailView() {
        return new RecipeDetailFragment();
    }

    @Provides
    RecipeDetailInteractor getRecipeDetailInteractor(Context context) {
        return new RecipeDetailInteractor(context);
    }

    @Provides
    RecipeDetailView getRecipeDetailProvider(RecipeDetail recipeDetail,
                                             RecipeDetailInteractor recipeDetailInteractor) {
        return new RecipeDetailView(recipeDetail, recipeDetailInteractor);
    }
}

//TODO: Fix all of these errors by defining the classes
