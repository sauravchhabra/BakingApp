package nanodegree.sauravchhabra.com.bakingapp.utils;

import retrofit2.HttpException;

/**
 * A simple class to check if the web address is invalid and throw the exception
 */
public class WebsiteNotFound {

    public static final String ERROR = "Unable to find the web address";

    public static boolean isHttpError(Throwable throwable){
        return throwable instanceof HttpException && ((HttpException) throwable).code() == 404;
    }
}
