package com.example.yash.resourcify;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yash on 2/20/2016.
 */
public class ServerStatusRequestObject extends Request<Integer> {

    private final Response.Listener mListener;
    private String mBody;
    private String mContentType;
    private HashMap mCustomHeaders;


    public ServerStatusRequestObject(int method,
                                     String url,
                                     HashMap customHeaders,
                                     String body,
                                     Response.Listener listener,
                                     Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        url="http://10.0.3.2/MyTaskMananger/v1/login";
        mCustomHeaders = customHeaders;
        mBody = body;
        mListener = listener;
        mContentType = "application/json";

        if (method == Method.POST) {
            RetryPolicy policy = new DefaultRetryPolicy(5000, 0, 5);
            setRetryPolicy(policy);
        }
    }

    public ServerStatusRequestObject(String url,
                                     HashMap customHeaders,
                                     Response.Listener listener,
                                     Response.ErrorListener errorListener) {

        super(Request.Method.GET, url, errorListener);
        mCustomHeaders = customHeaders;
        mListener = listener;
        mContentType = "application/x-www-form-urlencoded";
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.statusCode, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Integer statusCode) {
        mListener.onResponse(statusCode);
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        if (mCustomHeaders != null) {
            return mCustomHeaders;
        }
        return super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mBody.getBytes();
    }

    @Override
    public String getBodyContentType() {
        return mContentType;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String mContentType) {
        this.mContentType = mContentType;
    }
}
