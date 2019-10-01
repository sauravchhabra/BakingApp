package nanodegree.sauravchhabra.com.bakingapp;

import android.app.Activity;
import android.app.Application;
import nanodegree.sauravchhabra.com.bakingapp.di.DaggerAppComponentBuilder;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;


/**
 * A simple class to manage dependencies
 */
public class BakingApp extends Application implements HasActivityInjector {


    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }


    private void initInjector() {
        DaggerAppComponentBuilder.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initInjector();
        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
            Timber.plant(new Timber.DebugTree());
        }

    }
}
