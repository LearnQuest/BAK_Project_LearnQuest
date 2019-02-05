package com.example.heidrun.bak_project_learnquest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class loginActivity extends AppCompatActivity {

    boolean successfulLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void onClickSwitchtoMain(View view) throws InterruptedException {
        CardView cardView = (CardView) findViewById(R.id.cardview);

        sendPOST(view);



    }

    public void sendPOST(View view) throws InterruptedException {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://ehealth.fh-joanneum.at/mira/users/login");

                    HttpURLConnection client = (HttpsURLConnection) url.openConnection();

                    client.setRequestMethod("POST");
                    client.setRequestProperty("Content-Type", "application/json");
                    client.setRequestProperty("Accept", "application/json");
                    client.setDoOutput(true);

                    JSONObject jsonParam = new JSONObject();
                    EditText mail = (EditText) findViewById(R.id.mail);
                    EditText pw = (EditText) findViewById(R.id.password);
                    String mailstring = (String) mail.getText().toString();
                    String pwstring = (String) pw.getText().toString();

                    jsonParam.put("email", mailstring);
                    jsonParam.put("password", pwstring);

                    DataOutputStream os = new DataOutputStream(client.getOutputStream());
                    os.writeBytes(jsonParam.toString());
                    os.flush();
                    os.close();

                    int responseCode = client.getResponseCode();
                    //final StringBuilder output = new StringBuilder("Request URL " + url);
                    //output.append(System.getProperty())
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String line;
                    StringBuilder stringb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        stringb.append(line);
                    }
                    br.close();

                    Log.i("TRYConnect", stringb.toString());

                    JSONObject res = new JSONObject(stringb.toString());

                    handleJsonResult(res);

                    Log.i("TryConnect", res.toString());


                    client.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



        thread.start();
        thread.join();

        LoginProcedure(view);

    }

    private void LoginProcedure(View view){
        if (successfulLogin) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            Snackbar.make(view, "Login ist fehlgeschlagen! Bitte die Eingabe überprüfen!", Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }
    }

    private void handleJsonResult(JSONObject json) throws JSONException {

        if (json.has("status")) {

            if (json.get("status").equals("OK")) {
                successfulLogin = true;
            } else {
                //   Toast.makeText(getApplicationContext(), "Status ERROR", Toast.LENGTH_SHORT).show();
                successfulLogin = false;
            }

        } else {
            successfulLogin = false;
        }
    }
}

