package com.git.githubcommits.network;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.git.githubcommits.AppController;
import com.git.githubcommits.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by K@R@N on 27-05-2016.
 */
public class NetworkCall {

    private static final String TAG = "NetworkCall";

    private JSONObject mJson;
    private AsyncTaskInterface mAsyncTaskInterface;
    private String mUrl;
    private String mCallingId;

    public NetworkCall(JSONObject json, AsyncTaskInterface asyncTaskInterface, String url) {
        mJson = json;
        mAsyncTaskInterface = asyncTaskInterface;
        mUrl = url;
    }

    public void execute() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Response : " + response.toString());
                        }

                        if (mAsyncTaskInterface != null) {
                            mAsyncTaskInterface.onTaskCompleted(response.toString(), mUrl);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Response : " + error.toString());
                        }

                        if (mAsyncTaskInterface != null) {
                            mAsyncTaskInterface.onTaskCompleted(error.toString(), mUrl);
                        }
                    }
                }
        );
// if timeout runs faster

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkTags.TIMEOUT_MILLISEC,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

}
