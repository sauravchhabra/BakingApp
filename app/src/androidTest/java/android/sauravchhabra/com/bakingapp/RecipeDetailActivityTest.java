package android.sauravchhabra.com.bakingapp;

import android.content.Context;
import android.content.Intent;

import nanodegree.sauravchhabra.com.bakingapp.R;
import nanodegree.sauravchhabra.com.bakingapp.model.Ingredients;
import nanodegree.sauravchhabra.com.bakingapp.model.Recipes;
import nanodegree.sauravchhabra.com.bakingapp.model.Steps;
import nanodegree.sauravchhabra.com.bakingapp.ui.detail.RecipeDetailActivity;
import nanodegree.sauravchhabra.com.bakingapp.utils.Constants;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    private Recipes mRecipeResponse = new Recipes();
    private static final int RECIPE_ID = 2;
    private static final String RECIPE_NAME = "Brownies";
    private static final List<Ingredients> RECIPE_INGREDIENT_LIST = new ArrayList<Ingredients>() {{
        add(new Ingredients() {{
            setQuantity(350);
            setMeasure("G");
            setIngredient("unsalted butter");
        }});
    }};
    private static final List<Steps> RECIPE_STEP_LIST = new ArrayList<Steps>() {{
        add(new Steps(){{
            setDescription("Recipe Introduction");
            setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4");
        }});
    }};

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mDetailActivityRule = new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            mRecipeResponse.setId(RECIPE_ID);
            mRecipeResponse.setName(RECIPE_NAME);
            mRecipeResponse.setIngredients(RECIPE_INGREDIENT_LIST);
            mRecipeResponse.setSteps(RECIPE_STEP_LIST);
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent resultIntent = new Intent(targetContext, RecipeDetailActivity.class);
            resultIntent.putExtra(Constants.RECIPES, mRecipeResponse);
            return resultIntent;
        }
    };

    //This project belongs to Saurav Chhabra. Student at Udacity

    @Test
    public void showToolbarTitle_AndSteps() {
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar_detail))))
                .check(matches(withText(RECIPE_NAME)));
        onView(withId(R.id.steps_vsv_details)).check(matches(isDisplayed()));
    }

}
