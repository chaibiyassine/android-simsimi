package com.score.simsimichatbot;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GetSimsimiResponse {

    private String key = "XjJT_aI7s1.aVqT9wfwp57Ge5PjI_Lp2s7e2OyY~";

    private Context context;
    private String response = "";
    private int status = 404;
    private setOnRequestListener mListener;


    public GetSimsimiResponse(Context context, String message, String language, setOnRequestListener listener) {
        this.context = context;
        mListener = listener;
        getResponse(message, language);
    }

    private void getResponse(String message, String language) {

        final String URL = "https://wsapi.simsimi.com/190410/talk";
        HashMap<String, String> params = new HashMap<>();
        params.put("utext", message);
        params.put("lang", language);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            System.out.println(res.toString());
                            status = res.getInt("status");
                            response = res.getString("atext");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type ", "application/json");
                params.put("x-api-key", key);

                return params;
            }
        };;

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                if(mListener != null) {
                    mListener.onRequestFinished(status, response);
                }
            }
        });

    }


    public interface setOnRequestListener {
        void onRequestFinished(int status, String response);
    }


}
