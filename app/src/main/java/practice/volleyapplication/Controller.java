package practice.volleyapplication;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by aasaqt on 22/9/15.
 */
public class Controller {

    private static final RetryPolicy DEFAULT_RETRY_POLICY = new RetryPolicy() {
        @Override
        public void retry(VolleyError error) throws VolleyError {
            if(error.networkResponse.statusCode == 401)
                throw new VolleyError(error);
        }

        @Override
        public int getCurrentTimeout() {
            return 0;
        }

        @Override
        public int getCurrentRetryCount() {
            return 3;
        }
    };

    private static RequestQueue mRequestQueue;

    private static NetworkResponse defNoInternetNetworkResponse = new NetworkResponse(
            ERROR_CODES.NO_INTERNET, "Network Not Available".getBytes(), null,
            false);

    public static final void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    private static Request<String> bundleToVolleyRequestNoCaching(
            final Context context, int request_method_type,
            final Object newRequest, String url, final RequestListener mListener) {
        StringBuffer buffer = new StringBuffer(url);
        buffer.replace(0, UrlResolver.BASE_URL.length() - 1, "response--");
        final String url_recieved = buffer.toString();
        // end
        Request<String> tempRequest = new JsonRequest<String>(
                request_method_type, url, JsonUtils.jsonify(newRequest),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new VolleyErrorListener(context, url_recieved, mListener)) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Response<String> mResponse;
                if (response.statusCode == 200) {
                    String responseBody = new String(response.data);
                    if (mListener != null)
                        mListener.onRequestCompleted(responseBody);
                    mResponse = Response.success(responseBody,
                            HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    parseNetworkError(new VolleyError(response));
                    mResponse = Response.error(new VolleyError(response));
                }
                return mResponse;
            }
        };
        return tempRequest;
    }

    private static void dispatchToQueue(Request<String> request,
                                        Context mContext) {
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            VolleyError error = new VolleyError(defNoInternetNetworkResponse);
            if (request.getMethod() != Request.Method.GET) {
                request.deliverError(error);
            } else {
                request.setRetryPolicy(DEFAULT_RETRY_POLICY);
                mRequestQueue.add(request);
            }
        } else {
            request.setRetryPolicy(DEFAULT_RETRY_POLICY);
            mRequestQueue.add(request);
        }
    }

    public static void getMovies(Context context,
                                  RequestListener requestListener) {
        String url = UrlResolver
                .withAppendedPath(UrlResolver.EndPoints.NEWS);
        Request<String> volleyTypeRequest = bundleToVolleyRequestNoCaching(
                context, Request.Method.GET, null, url, requestListener);
        volleyTypeRequest.setShouldCache(false);
        dispatchToQueue(volleyTypeRequest, context);
    }







    public interface ERROR_CODES {
        int BAD_REQUEST = 400;
        int UNAUTHORISED = 401;
        int UNAUTHORIZED_ACCESS = 403;
        int NOT_FOUND = 404;
        int USERNAME_NOT_AVAILABLE = 409;
        int SOURCE_FILE_DOESNT_EXIST = 920;
        int CUSTOM_ERROR_CODE = 1001;
        int SHIT_HAPPENED = 1022;
        int FILES_MISSING = 1023;
        int NO_INTERNET = 1025;
        int EMPTY_RESULTS = 1026;
        int API_FAILURE = 1027;

    }
}