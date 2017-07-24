package id.co.sigma.portalsigma;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.co.sigma.portalsigma.volley.Config;
import id.co.sigma.portalsigma.volley.CustomRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = LoginActivity.class.getSimpleName();
    //    private TextView signup;
    private TextView signin;
    //    private TextView fb;
//    private TextView account;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //        signup = (TextView)findViewById(R.id.signup);
        signin = (TextView) findViewById(R.id.buttonsignin);
//        fb = (TextView)findViewById(R.id.fb);
//        account = (TextView)findViewById(R.id.account);
        editTextEmail = (EditText) findViewById(R.id.usermail);
        editTextPassword = (EditText) findViewById(R.id.userpass);

        signin.setOnClickListener(this);

//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(it);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        login();
    }

    private void login() {
        StringBuilder url = new StringBuilder();
        url.append(Config.BASE_URL);
        url.append("login");
        //Getting values from edit texts
        final String usermail = editTextEmail.getText().toString().trim();
        final String userpass = editTextPassword.getText().toString().trim();

        loading = ProgressDialog.show(this, "Authentication...", "Please wait...", false, false);
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url.toString(),
//                getParams(usermail,userpass),this.responseListener(),this.errorListener()
//        );
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                String msg = "";
                JSONObject feed = null;
                String nikPersonil = "";
                try {
                    msg = response.getString("msg");
                    feed = response.getJSONObject("feed");
                    nikPersonil = feed.getString("EMP_ID");

                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (msg.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Config.EMAIL_SHARED_PREF, editTextEmail.getText().toString().trim());
                    editor.putString(Config.KEY_SUBMIT_BY, nikPersonil);
                    //Saving values to editor
                    editor.commit();

                    //Starting profile activity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    //If the server response is not success
                    //Displaying an error message on toast
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loading.dismiss();
                Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMAIL, usermail);
                params.put(Config.KEY_PASSWORD, userpass);
                return params;
            }
        };

//        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(jsObjRequest);
    }

    protected Map<String, String> getParams(String usermail, String userpass) {
        Map<String, String> params = new HashMap<>();
        //Adding parameters to request
        params.put(Config.KEY_EMAIL, usermail);
        params.put(Config.KEY_PASSWORD, userpass);
        return params;
    }

    private Response.Listener<JSONObject> responseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                String msg = "";
                JSONObject feed = null;
                String nikPersonil = "";
                try {
                    msg = response.getString("msg");
                    feed = response.getJSONObject("feed");
                    nikPersonil = feed.getString("EMP_ID");

                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (msg.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Config.EMAIL_SHARED_PREF, editTextEmail.getText().toString().trim());
                    editor.putString(Config.KEY_SUBMIT_BY, nikPersonil);
                    //Saving values to editor
                    editor.commit();

                    //Starting profile activity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    //If the server response is not success
                    //Displaying an error message on toast
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loading.dismiss();
                Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
