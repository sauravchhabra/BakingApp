package nanodegree.sauravchhabra.com.bakingapp.di;

import nanodegree.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailActivity;
import nanodegree.sauravchhabra.com.bakingapp.ui.recipe.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * A simple class for dependency framework
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract RecipeDetailActivity detailActivity();
}

//TODO: Fix this error
