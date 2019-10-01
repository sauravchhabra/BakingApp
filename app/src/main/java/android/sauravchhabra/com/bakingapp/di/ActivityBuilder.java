package android.sauravchhabra.com.bakingapp.di;

import android.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailActivity;
import android.sauravchhabra.com.bakingapp.ui.recipe.MainActivity;

import dagger.android.ContributesAndroidInjector;

/**
 * A simple class for dependency framework
 */
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract RecipeDetailActivity detailActivity();
}

//TODO: Fix this error
