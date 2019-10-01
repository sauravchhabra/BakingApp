package nanodegree.sauravchhabra.com.bakingapp.di;

import nanodegree.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailFragment;
import nanodegree.sauravchhabra.com.bakingapp.ui.recipe.RecipeListFragment;

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
