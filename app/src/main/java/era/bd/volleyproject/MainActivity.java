package era.bd.volleyproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    String  TAG = "volleyStringRequest";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");

        /*
         String https://androidtutorialpoint.com/api/volleyString
         JsonObject URL	https://androidtutorialpoint.com/api/volleyJsonObject
         JsonArray URL	https://androidtutorialpoint.com/api/volleyJsonArray
         Image URL	https://androidtutorialpoint.com/api/lg_nexus_5x
         */

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volleyStringRequst("https://androidtutorialpoint.com/api/volleyString");
                volleyStringRequst("http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volleyJsonObjectRequest("https://androidtutorialpoint.com/api/volleyJsonObject");
                volleyJsonObjectRequest("http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse");
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volleyJsonArrayRequest("https://androidtutorialpoint.com/api/volleyJsonArray");
                volleyJsonArrayRequest("http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse");
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyImageLoader("https://androidtutorialpoint.com/api/lg_nexus_5x");
            }
        });



        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volleyStringRequst("https://androidtutorialpoint.com/api/volleyString");
                JsonObjectRequest();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volleyJsonObjectRequest("https://androidtutorialpoint.com/api/volleyJsonObject");
                jsonArrayRequest();
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //volleyJsonArrayRequest("https://androidtutorialpoint.com/api/volleyJsonArray");
                //postParametersRequest();
                //customPostParametersRequest2();
                //doLoginAction();
                //login();
                login2();
            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingRequestHeaders();
            }
        });

    }

    public void volleyStringRequst(String url){

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        progressDialog.show();

        StringRequest strReq = new StringRequest(url, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                showAlert(response, null);
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.hide();
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }


    public void volleyJsonObjectRequest(String url){

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";
        progressDialog.show();

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        showAlert(response.toString(), null);
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.hide();
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }


    //**************************Array Req Start***************************
    public void volleyJsonArrayRequest(String url){

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyJsonArrayRequest";

        progressDialog.show();

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        progressDialog.hide();                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.hide();
            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    //**************************Array Req End***************************

    public void volleyImageLoader(String url){
        progressDialog.show();
        ImageLoader imageLoader = AppSingleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
                progressDialog.hide();
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    showAlert("Image", response);
                    progressDialog.hide();
                }
            }
        });
    }


    public void volleyCacheRequest(String url){
        Cache cache = AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry reqEntry = cache.get(url);
        if(reqEntry != null){
            try {
                String data = new String(reqEntry.data, "UTF-8");
                //Handle the Data here.
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{

            //Request Not present in cache, launch a network request instead.
        }
    }

    public void volleyInvalidateCache(String url){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    public void volleyDeleteCache(String url){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    public void volleyClearCache(){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
    }


    private void showAlert(String response, ImageLoader.ImageContainer imageresponse){

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View showDialogView = li.inflate(R.layout.show_dialog, null);
        TextView outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
        ImageView outputImageView = (ImageView)showDialogView.findViewById(R.id.image_view_dialog);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(showDialogView);
        alertDialogBuilder
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setCancelable(false)
                .create();
        outputTextView.setText(response.toString());
        try {
            if(imageresponse.getBitmap()!=null) {
                outputImageView.setImageBitmap(imageresponse.getBitmap());
            }
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }

        alertDialogBuilder.show();
    }


    //*****************Android Have Example ************
    private void JsonObjectRequest(){
       // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://api.androidhive.info/volley/person_object.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private  void jsonArrayRequest(){
         // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = "http://api.androidhive.info/volley/person_array.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    private  void postParametersRequest(){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://api.androidhive.info/volley/person_object.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    private  void customPostParametersRequest(){
        // Tag used to cancel the request
        //String tag_json_obj = "json_obj_req";

        String url = "http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,//Param 1
                url,//Param 2
                null,//Param 3
                new Listener<JSONObject>() {//Param 4

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {//Param 5

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.hide();
                }
                }) {//Req Boddy
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("number", "5");
                        return params;
                    }

                };

        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjReq);
    }

    private  void addingRequestHeaders(){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://api.androidhive.info/volley/person_object.json";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private  void imageRequst(){
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

         // If you are using normal ImageView
//        imageLoader.get(Cons.URL_IMAGE, new ImageLoader.ImageListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Image Load Error: " + error.getMessage());
//            }
//
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
//                if (response.getBitmap() != null) {
//                    // load image into imageview
//                   // imageView.setImageBitmap(response.getBitmap());
//                }
//            }
//        });
    }

    //Source: http://www.androidhive.info/2014/05/android-working-with-volley-library-1/

    //******************android have End***********************************


    public class GetStudensts extends Request<String>{

        private Map<String, String> mParams;

        public GetStudensts(Map<String, String> params, int method, String url, Response.ErrorListener listener) {
            super(method, url, listener);
            mParams = new HashMap<String, String>();
            mParams = (Map<String, String>) params;
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            return null;
        }

        @Override
        protected void deliverResponse(String response) {

        }

        @Override
        public Map<String, String> getParams() {
            return mParams;
        }
    }

//    public class LoginRequest extends Request<String> {
//
//        // ... other methods go here
//
//        private Map<String, String> mParams;
//
//        public LoginRequest(ArrayList<String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
//            super(Method.POST, "http://test.url", errorListener);
//            mListener = listener;
//            mParams = new HashMap<String, String>();
//            mParams.put("paramOne", param1);
//            mParams.put("paramTwo", param2);
//
//        }
//
//        @Override
//        public Map<String, String> getParams() {
//            return mParams;
//        }
//    }



    private void doLoginAction() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse",
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            showAlert(response.toString(), null);
                            //JSONObject jsonObject = new JSONObject(response);
//                            JSONArray loginNodes = jsonObject.getJSONArray(KEY_USER_LOIGN_NODES);
//                            for (int i = 0; i < loginNodes.length(); i++) {
//                                JSONObject jo = loginNodes.getJSONObject(i);
//                                String errorFlag = jo.getString(KEY_USER_LOIGN_ERROR_FLAG);
//                                String errorMessage = jo.getString(KEY_USER_LOIGN_ERROR_MESSAGE);
//                                String sessionId = jo.getString(KEY_USER_LOIGN_SESSION_ID);
//
//
//                                if ("Y".equals(errorFlag)) {
//                                    Intent intent = new Intent(getActivity(), Welcome.class);
//                                    savePreferences("sessionId",sessionId);
//                                    savePreferences("userId",username);
//                                    savePreferences("companyCode","200");
//                                    startActivity(intent);
//                                    getActivity().finish();
//                                } else {
//                                    tv_message.setTextColor(Color.parseColor("#F50000"));
//                                    tv_message.startAnimation(myAnimation);
//                                    tv_message.setText(errorMessage);
//                                }

                            ///}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //  Intent intent =new Intent(getActivity(),Welcome.class);
                        //startActivity(intent);
                        // getActivity().finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error!=null && error.getMessage() !=null){
                            Toast.makeText(getApplicationContext(),"error VOLLEY "+error.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("Error : " ,error.getMessage());
                        }
                        else{
                            Log.e("Error : " ,error.getMessage());
                            Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_LONG).show();

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", "5");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    private  void customPostParametersRequest2(){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,//Param 1
                url,//Param 2
                null,//Param 3
                new Listener<JSONObject>() {//Param 4

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        showAlert(response.toString(), null);
                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {//Param 5

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        pDialog.hide();
                    }
                }) {//Req Boddy
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("number", "5");
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void login(){
        final String url = "http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse";
        progressDialog.show();

        StringRequest sr = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        showAlert(response.toString(), null);
                        progressDialog.hide();
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //showAlert(response.toString(), null);
                        progressDialog.hide();
                    }
                }){
           @Override
            protected Map<String, String> getParams(){
               Map<String, String> params = new HashMap<String, String>();
               params.put("number","4");
               return  params;
           }
        };

        AppController.getInstance().addToRequestQueue(sr);

    }

    private void login2(){
        String url = "http://10.11.201.180:8084/CMSE/RetrofitAndroidArrayResponse";
        progressDialog.show();
        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        showAlert(response.toString(), null);
                        progressDialog.hide();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
          @Override
            protected  Map<String, String> getParams(){
              Map<String, String> params = new HashMap<String, String>();
              params.put("number","778");
              return  params;
            }
        };
        AppController.getInstance().addToRequestQueue(jor);
    }
}
