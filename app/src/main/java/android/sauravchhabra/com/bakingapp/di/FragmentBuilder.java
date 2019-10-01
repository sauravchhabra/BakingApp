package android.sauravchhabra.com.bakingapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * A simple class for dependency framework
 */
@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract RecipeListFragment recipeListFragment();

    @ContributesAndroidInjector
    abstract RecipeDetailFragment recipeDetailFragment();
}
