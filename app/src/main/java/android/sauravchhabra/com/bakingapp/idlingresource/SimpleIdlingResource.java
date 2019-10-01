package android.sauravchhabra.com.bakingapp.idlingresource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback mResourceCallback;

    private AtomicBoolean mIsIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    public void setIdle(boolean isIdle){
        mIsIdle.set(isIdle);
        if (isIdle && mResourceCallback != null){
            mResourceCallback.onTransitionToIdle();
        }
    }
}
