package practice.volleyapplication;

/**
 * Created by aasaqt on 22/9/15.
 */
public interface RequestListener {
    public void onRequestStarted();

    public void onRequestCompleted( Object responseObject);

    public void onRequestError(final int errorCode,final String message);
}
