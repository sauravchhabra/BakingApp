package nanodegree.sauravchhabra.com.bakingapp.ui.steps;

import android.os.Bundle;
import nanodegree.sauravchhabra.com.bakingapp.R;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple class to set the fragment in the  steps activity
 */
public class StepsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_steps)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        setupToolbar();

        if (savedInstanceState != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_steps, StepsFragment.newInstance(null))
                    //TODO: Fix this error
                    .commit();
        }
    }

    private void setupToolbar(){
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
