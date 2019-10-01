package android.sauravchhabra.com.bakingapp.ui.recipe;

import android.content.Context;
import android.content.res.Configuration;
import android.sauravchhabra.com.bakingapp.R;
import android.sauravchhabra.com.bakingapp.model.Recipes;
import android.sauravchhabra.com.bakingapp.utils.RecipeImage;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple class to bind the data to it's respective view
 */
public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {

    private List<Recipes> mRecipesList = new ArrayList<>();
    private Context mContext;
    private ClickListener mClickListener;

    RecipeRecyclerAdapter(List<Recipes> recipesList, Context context, ClickListener clickListener) {
        mRecipesList = recipesList;
        mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipes_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerAdapter.ViewHolder viewHolder, int i) {
        Recipes recipes = mRecipesList.get(i);
        String servingSize = recipes.getServings() + " serving";
        viewHolder.dish.setText(recipes.getName());
        viewHolder.serving.setText(servingSize);
        viewHolder.dishImage.setImageResource(RecipeImage.getRecipeImage(recipes.getId()));

        viewHolder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClickListener(recipes);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipesList.size();
    }

    //A simple interface to setup click listener
    interface ClickListener {
        void onClickListener(Recipes recipes);
    }

    //A simple View Holder class to bind the data
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_iv_main)
        ImageView dishImage;

        @BindView(R.id.dish_tv_main)
        TextView dish;

        @BindView(R.id.serving_main)
        TextView serving;

        @BindView(R.id.recipe_cv_main)
        CardView cardViewContainer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
