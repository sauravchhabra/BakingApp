package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.sauravchhabra.com.bakingapp.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_main, RecipeListFragment.newInstance())
                    .commit();
            //TODO: Fix this error
        }
    }

    private void setupToolbar(){
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }
}
