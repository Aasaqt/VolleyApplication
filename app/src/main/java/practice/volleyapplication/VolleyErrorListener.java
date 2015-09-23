package practice.volleyapplication;

import android.content.Context;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by aasaqt on 22/9/15.
 */
public class VolleyErrorListener implements Response.ErrorListener{
    private Context mContext;
    private String mApiActionPath;
    private RequestListener mRequestListener;
    private final static String CATEGORY_ERROR = "CATEGORY_ERROR";

    public VolleyErrorListener(Context pContext,String pApiActionPath,RequestListener pRequestListener){
        this.mApiActionPath = pApiActionPath;
        this.mContext = pContext;
        this.mRequestListener = pRequestListener;
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        int errorCode;
        String message;
        if(error instanceof NoConnectionError){
            errorCode = Controller.ERROR_CODES.NO_INTERNET;
            message = "Network Not Available";
        } else if(error == null || error.networkResponse == null){
            if(error!=null && error.getMessage().equalsIgnoreCase("com.android.volley.AuthFailureError")){
                errorCode = Controller.ERROR_CODES.UNAUTHORISED;
                message = "Authentication Failed";
            }else{
                errorCode = Controller.ERROR_CODES.SHIT_HAPPENED;
                message = "Error object is null";
            }
        }else{
            errorCode = error.networkResponse.statusCode;
            if(error.networkResponse.data != null){
                message = new String(error.networkResponse.data);
            }else {
                message = "Respinse data is null";
            }
        }
        if(mRequestListener != null){
            mRequestListener.onRequestError(errorCode,message);
        }
    }
}
