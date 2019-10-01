package nanodegree.sauravchhabra.com.bakingapp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import dagger.android.support.AndroidSupportInjection;


/**
 * Simple fragment class to create the fragment and inject the module using dagger
 */
public abstract class BaseFragment extends Fragment {

    @LayoutRes
    public abstract int getLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
