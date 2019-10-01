package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sauravchhabra.com.bakingapp.R;
import android.sauravchhabra.com.bakingapp.base.BaseFragment;
import android.sauravchhabra.com.bakingapp.idlingresource.SimpleIdlingResource;
import android.sauravchhabra.com.bakingapp.model.Recipes;
import android.sauravchhabra.com.bakingapp.utils.Column;
import android.sauravchhabra.com.bakingapp.utils.Constants;
import android.sauravchhabra.com.bakingapp.utils.isTabletOrLandscape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends BaseFragment implements
        RecipeBasicView, RecipeRecyclerAdapter.ClickListener {

    @BindView(R.id.pb_list)
    ProgressBar mProgressBar;

    @BindView(R.id.dish_rv_list)
    RecyclerView mRecyclerView;

    @Inject
    RecipeView mRecipeView;

    RecipeRecyclerAdapter mRecipeRecyclerAdapter;
    List<Recipes> mRecipesList = new ArrayList<>();

    public static final String STATE_KEY = "recipe_state_key";

    int toast_length = Toast.LENGTH_SHORT;
    Context toast_context = getContext();

    @Nullable
    private SimpleIdlingResource mSimpleIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResources() {
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }

    private void setIdlingResource(boolean isIdle) {
        if (mSimpleIdlingResource != null) {
            mSimpleIdlingResource.setIdle(isIdle);
        }
    }

    @VisibleForTesting
    @NonNull
    public static RecipeListFragment getInstance() {
        return new RecipeListFragment();
    }

    public static RecipeListFragment newInstance() {
        Bundle bundle = new Bundle();
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        recipeListFragment.setArguments(bundle);
        return recipeListFragment;
    }

    //Empty constructor required
    public RecipeListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIdlingResources();
        setIdlingResource(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);

        mRecipeView.setView(this);
        if (savedInstanceState != null) {
            mRecipesList = (List<Recipes>) savedInstanceState.getSerializable(STATE_KEY);
            mRecipeView.handleScreenRotation(mRecipesList);
        } else {
            mRecipeView.getRecipes();
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(STATE_KEY, (Serializable) mRecipesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecipeView.removeView();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recipe_list;
    }

    @Override
    public void showRecipes(List<Recipes> recipesList) {
        mRecipeRecyclerAdapter = new RecipeRecyclerAdapter(recipesList, getActivity(), this);

        if (!isTabletOrLandscape.isTabletDisplay((getActivity()))) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                    Column.getNumberOfColumns(getActivity())));
        }
        mRecyclerView.setAdapter(mRecipeRecyclerAdapter);
        mRecipesList = recipesList;
        setIdlingResource(true);
    }

    @Override
    public void onClickListener(Recipes recipes) {
        Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        //TODO: Fix this error
        intent.putExtra(Constants.RECIPES, recipes);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(toast_context, "Oops, this wasn't supposed to happen", toast_length).show();
    }

    @Override
    public void showNetworkConnectionError() {
        Toast.makeText(toast_context, "Possible a netword connection error", toast_length).show();
    }

    @Override
    public void showServerError() {
        Toast.makeText(toast_context, "Server related error", toast_length).show();
    }
}
