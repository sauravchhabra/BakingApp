package android.sauravchhabra.com.bakingapp.ui.detail;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.sauravchhabra.com.bakingapp.base.BaseFragment;
import android.sauravchhabra.com.bakingapp.model.Recipes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;

public class RecipeDetailFragment extends BaseFragment
        implements RecipeDetail, IStepperAdapter, LoaderManager.LoaderCallbacks<Cursor> {

    public static RecipeDetailFragment newInstance(Recipes recipes){


    }

    @Override
    public int getLayout() {
        return 0;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @NonNull
    @Override
    public CharSequence getTitle(int i) {
        return null;
    }

    @Nullable
    @Override
    public CharSequence getSummary(int i) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public View onCreateCustomView(int i, Context context, VerticalStepperItemView verticalStepperItemView) {
        return null;
    }

    @Override
    public void onShow(int i) {

    }

    @Override
    public void onHide(int i) {

    }
}
