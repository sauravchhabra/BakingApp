package nanodegree.sauravchhabra.com.bakingapp.di;


import android.app.Application;
import nanodegree.sauravchhabra.com.bakingapp.BakingApp;

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

        AppComponentBuilder build();
    }

    void inject(BakingApp bakingApp);
}
