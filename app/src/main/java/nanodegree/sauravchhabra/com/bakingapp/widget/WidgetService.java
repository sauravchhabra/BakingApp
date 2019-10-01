package nanodegree.sauravchhabra.com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import nanodegree.sauravchhabra.com.bakingapp.R;
import nanodegree.sauravchhabra.com.bakingapp.data.local.BakingContract;
import nanodegree.sauravchhabra.com.bakingapp.data.local.BakingProvider;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * A simple widget service class
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipesRemoteViewFactory(getApplicationContext());
    }

    public class RecipesRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{

        private Context mContext;
        private Cursor mCursor;

        RecipesRemoteViewFactory(Context context){
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (mCursor!= null){
                mCursor.close();
                mCursor = mContext.getContentResolver().query(
                        BakingProvider.Recipe.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            }

        }

        @Override
        public void onDestroy() {
            if (mCursor != null){
                mCursor.close();
            }

        }

        @Override
        public int getCount() {
            return (mCursor!= null) ? mCursor.getCount() : 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mCursor == null || mCursor.getCount() == 0) return null;

            mCursor.moveToPosition(position);

            int nameIndex = mCursor.getColumnIndex(BakingContract.RECIPE_NAME);
            int quantityIndex = mCursor.getColumnIndex(BakingContract.QUANTITY_MEASURE);

            String name = mCursor.getString(nameIndex);
            String quantity = mCursor.getString(quantityIndex);

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                    R.layout.ingredient_widget_item);

            remoteViews.setViewVisibility(R.id.recipe_tv_widget_item, View.VISIBLE);
            remoteViews.setTextViewText(R.id.recipe_tv_widget_item, name);

            remoteViews.setViewVisibility(R.id.ingredients_tv_widget_item, View.VISIBLE);
            remoteViews.setTextViewText(R.id.ingredients_tv_widget_item, quantity);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
