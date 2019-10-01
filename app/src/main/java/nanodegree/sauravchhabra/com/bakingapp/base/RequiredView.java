package nanodegree.sauravchhabra.com.bakingapp.base;

/**
 * Simple interface to define the global methods which other classes will have to implement
 * according to their requirement
 */
public interface RequiredView {

    void showLoading();

    void hideLoading();

    void showErrorMessage();

    void showNetworkConnectionError();

    void showServerError();
}
