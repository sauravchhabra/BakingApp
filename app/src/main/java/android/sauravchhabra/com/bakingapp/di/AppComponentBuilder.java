package android.sauravchhabra.com.bakingapp.di;


import android.app.Application;
import android.sauravchhabra.com.bakingapp.BakingApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * A simple class for dependency framework
 */
@Singleton
@Component (modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
        })
public interface AppComponentBuilder {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(BakingApp bakingApp);
}

//TODO: Fix this error
