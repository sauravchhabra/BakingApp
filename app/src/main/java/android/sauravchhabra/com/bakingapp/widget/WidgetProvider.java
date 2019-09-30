package android.sauravchhabra.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.sauravchhabra.com.bakingapp.R;
import android.widget.RemoteViews;

/**
 * A simple widget provider class
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateAppWidget(context, appWidgetManager, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static RemoteViews getRecipesFromList(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
        Intent intent = new Intent(context, WidgetService.class);
        remoteViews.setRemoteAdapter(R.id.recipes_lv_widget, intent);

        remoteViews.setEmptyView(R.id.recipes_lv_widget, R.id.empty_rl_widget);

        return remoteViews;
    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] widgetIds){
        for (int widgetId: widgetIds){
            updateWidget(context, appWidgetManager, widgetId);
        }
    }

    public void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId){
        RemoteViews remoteViews = getRecipesFromList(context);
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }

}
