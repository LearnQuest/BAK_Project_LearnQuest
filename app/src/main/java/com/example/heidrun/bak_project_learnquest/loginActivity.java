package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

    /**
     * @param view
     * @throws InterruptedException
     * @throws JSONException        Hier erfolgt die "Anmeldung"
     *                              Es wird auch auf Internetverrbindung überprüft
     */
    public void onClickSwitchtoMain(View view) throws InterruptedException, JSONException {
        CardView cardView = (CardView) findViewById(R.id.cardview);
        boolean netacces = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo i = connectivityManager.getActiveNetworkInfo();

        if (i != null) {
            netacces = true;
        } else {
            netacces = false;
        }

        if (netacces) {
            sendPOST(view);
        } else {
            Snackbar.make(view, "Keine Internetverbindung vorhanden!", Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();


        }
    }

    private JSONObject res1 = new JSONObject();

    /**
     * Hier wird die Schnittstelle zu MIRA aufgebaut
     *
     * @param view
     * @throws InterruptedException
     * @throws JSONException
     */
    public void sendPOST(View view) throws InterruptedException, JSONException {

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
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String line;
                    StringBuilder stringb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        stringb.append(line);
                    }
                    br.close();

                    JSONObject res = new JSONObject(stringb.toString());

                    handleJsonResult(res);

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

        LoginProcedure(view, res1);
    }

    /**
     * Hier werden Benutzername und Passwort abgefragt
     *
     * @param view
     * @param json
     * @throws JSONException
     */
    private void LoginProcedure(View view, JSONObject json) throws JSONException {
        try {
            if (successfulLogin) {

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Username", json.get("familyname").toString());
                intent.putExtra("Email", json.get("email").toString());
                startActivity(intent);

            } else {
                Snackbar.make(view, "Login ist fehlgeschlagen! Bitte die Eingabe überprüfen!", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Diese Methode ermöglicht es auf eine "Passwort vergessen seite" weitergeleitet zu werden
     * Es wird davor die Internetverbindung überprüft
     *
     * @param view
     */
    public void onClickOpenURL(View view) {
        try {
            boolean netacces = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info != null) {
                netacces = true;
            } else {
                netacces = false;
            }
            if (netacces) {
                String url = "https://pwms.fh-joanneum.at/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } else {
                Snackbar.make(view, "Keine Internetverbindung vorhanden!", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Methode öffnet ein Dialogfenster
     *
     * @param view
     */
    public void openDialog(View view) {
        final Dialog d = new Dialog(this);
        d.setTitle("Help");
        d.setContentView(R.layout.fragezeichendalog);
        d.show();

        TextView tv = d.findViewById(R.id.ok);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
            }
        });
    }

    /**
     * @param json
     * @throws JSONException
     */
    private void handleJsonResult(JSONObject json) throws JSONException {
        try {
            if (json.has("status")) {

                if (json.get("status").equals("OK")) {
                    successfulLogin = true;
                    res1 = json;
                } else {
                    successfulLogin = false;
                }

            } else {
                successfulLogin = false;
            }
        } catch (Exception ex) {
            Toast.makeText(this, getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
    }
}

