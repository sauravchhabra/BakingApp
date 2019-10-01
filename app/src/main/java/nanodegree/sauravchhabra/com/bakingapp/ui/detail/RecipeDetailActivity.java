package nanodegree.sauravchhabra.com.bakingapp.ui.detail;

import android.os.Bundle;
import nanodegree.sauravchhabra.com.bakingapp.R;
import nanodegree.sauravchhabra.com.bakingapp.base.BaseActivity;
import nanodegree.sauravchhabra.com.bakingapp.model.Recipes;
import nanodegree.sauravchhabra.com.bakingapp.utils.Constants;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_detail)
    Toolbar mToolbar;

    private boolean mIsTwoPane = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        Recipes recipes = (Recipes) getIntent().getExtras().getSerializable(Constants.RECIPES);
        if (recipes != null) setupToolbar(recipes);

        if (savedInstanceState == null) {
            if (findViewById(R.id.steps_container_detail) != null) {
                mIsTwoPane = true;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_container_detail, RecipeDetailFragment.newInstance(recipes))
                        .commit();
            } else {
                mIsTwoPane = false;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_container_detail, RecipeDetailFragment.newInstance(recipes))
                        .commit();
            }
        }
    }

    private void setupToolbar(Recipes recipes) {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipes.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
