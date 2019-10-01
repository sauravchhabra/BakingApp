package nanodegree.sauravchhabra.com.bakingapp.ui.detail;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import nanodegree.sauravchhabra.com.bakingapp.R;
import nanodegree.sauravchhabra.com.bakingapp.base.BaseFragment;
import nanodegree.sauravchhabra.com.bakingapp.data.local.BakingProvider;
import nanodegree.sauravchhabra.com.bakingapp.model.Recipes;
import nanodegree.sauravchhabra.com.bakingapp.ui.steps.StepsActivity;
import nanodegree.sauravchhabra.com.bakingapp.ui.steps.StepsFragment;
import nanodegree.sauravchhabra.com.bakingapp.utils.Constants;
import nanodegree.sauravchhabra.com.bakingapp.utils.IsTabletOrLandscape;
import nanodegree.sauravchhabra.com.bakingapp.widget.WidgetProvider;
import nanodegree.sauravchhabra.com.bakingapp.widget.WidgetService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;

public class RecipeDetailFragment extends BaseFragment
        implements RecipeDetail, IStepperAdapter, LoaderManager.LoaderCallbacks<Cursor> {

    @Inject
    RecipeDetailView mView;

    @BindView(R.id.recipe_tv_details)
    TextView mRecipeTextView;

    @BindView(R.id.steps_vsv_details)
    VerticalStepperView mVerticalStepperView;

    private Recipes mRecipes;
    private Cursor mCursor;

    private static final String RECIPE = "recipe_arg";
    private static final int LOADER_ID = 1;

    //Required public constructor
    public RecipeDetailFragment() {

    }

    public static RecipeDetailFragment newInstance(Recipes recipes) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE, recipes);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        return recipeDetailFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recipe_detail;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mRecipes = (Recipes) getArguments().getSerializable(RECIPE);
            setIngredients(mRecipes);
            mVerticalStepperView.setStepperAdapter(this);
        }
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void setIngredients(Recipes recipes) {
        mRecipeTextView.setText("Ingredients\n");
        for (int i = 0; i < recipes.getIngredients().size(); i++) {
            String ingredients = recipes.getIngredients().get(i).getIngredient();
            double quantity = recipes.getIngredients().get(i).getQuantity();
            String measure = recipes.getIngredients().get(i).getMeasure();

            String formatText = (i + 1) + ". " + ingredients + "(" + quantity + " "
                    + measure + ")" + "\n";
            mRecipeTextView.append(formatText);
        }
    }

    private String getFormattedText(Recipes recipes) {
        List<String> quantityList = new ArrayList<>();
        for (int i = 0; i < recipes.getIngredients().size(); i++) {
            quantityList.add(recipes.getIngredients().get(i).getQuantity() + " "
                    + recipes.getIngredients().get(i).getMeasure() + " x " +
                    recipes.getIngredients().get(i).getIngredient());
        }
        return TextUtils.join("\n", quantityList);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        if (getActivity() != null) {
            return new CursorLoader(
                    getActivity(), BakingProvider.Recipe.CONTENT_URI, null,
                    null, null, null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.getCount()  > 0){
            mCursor = cursor;
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @NonNull
    @Override
    public CharSequence getTitle(int i) {
        return "Step " + i;
    }

    @Nullable
    @Override
    public CharSequence getSummary(int i) {
        return Html.fromHtml(mRecipes.getSteps().get(i).getShortDescription());
    }

    @Override
    public int size() {
        return mRecipes.getSteps().size();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.STEPS_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            int steps = data.getIntExtra(Constants.STEP_INDEX_RESULT, 0);
            mVerticalStepperView.setCurrentStep(steps);
            mVerticalStepperView.scrollBy(steps, 0);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_widget_menu){
            mView.addIngredientToList(mRecipes.getName(), getFormattedText(mRecipes));

            updateAppWidget();
            Toast.makeText(getActivity(), "Recipe added. Please add the new widget",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateAppWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        if (getActivity() != null){
            RemoteViews remoteViews = new RemoteViews(getActivity().getPackageName(),
                    R.layout.app_widget_layout);
            Intent intent = new Intent(getActivity(), WidgetService.class);
            remoteViews.setRemoteAdapter(R.id.recipes_lv_widget, intent);

            remoteViews.setEmptyView(R.id.recipes_lv_widget, R.id.empty_rl_widget);
            ComponentName componentName = new ComponentName(getActivity(), WidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.recipes_lv_widget);
        }
    }

    @Override
    public View onCreateCustomView(int i, Context context, VerticalStepperItemView verticalStepperItemView) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.vertical_stepper_item,
                verticalStepperItemView, false);

        TextView textView = inflater.findViewById(R.id.steps_tv_item);
        textView.setText(mRecipes.getSteps().get(i).getShortDescription());

        Button nextButton = inflater.findViewById(R.id.show_button_item);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verticalStepperItemView.canNextStep()) {
                    verticalStepperItemView.nextStep();
                }
                if (IsTabletOrLandscape.isTabletDisplay(getActivity())) {
                    if (inflater.findViewById(R.id.container_steps) == null) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_steps,
                                        StepsFragment.newInstance(mRecipes.getSteps().get(i)))
                                .commit();
                    } else {
                        Intent intent = new Intent(getActivity(), StepsActivity.class);
                        intent.putExtra(Constants.STEPS, (Serializable)mRecipes.getSteps());
                        intent.putExtra(Constants.STEPS_INDEX, i);
                        startActivityForResult(intent, Constants.STEPS_REQUEST_CODE);
                    }
                }
            }
        });

        Button prevButton= inflater.findViewById(R.id.back_button_item);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != 0){
                    verticalStepperItemView.prevStep();
                } else {
                    verticalStepperItemView.setAnimationEnabled(!verticalStepperItemView.isAnimationEnabled());
                }
            }
        });

        ImageView thumbnail = inflater.findViewById(R.id.thumbnail_iv_item);
        if(!mRecipes.getSteps().get(i).getVideoURL().equals("")){
            thumbnail.setVisibility(View.VISIBLE);
        } else
        {
            thumbnail.setVisibility(View.INVISIBLE);
        }
        return inflater;
    }

    @Override
    public void onShow(int i) {

    }

    @Override
    public void onHide(int i) {

    }
}
