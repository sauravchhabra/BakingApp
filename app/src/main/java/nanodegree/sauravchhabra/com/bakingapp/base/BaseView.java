package nanodegree.sauravchhabra.com.bakingapp.base;

/**
 * Simple interface to define the global methods which other classes will have to implement
 * according to their requirement
 */
public interface BaseView<V> {
    void setView(V view);

    void removeView();
}
