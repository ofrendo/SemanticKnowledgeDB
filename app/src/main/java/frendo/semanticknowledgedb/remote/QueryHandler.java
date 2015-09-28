package frendo.semanticknowledgedb.remote;

import android.content.SyncStatusObserver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import frendo.semanticknowledgedb.Env;

/**
 * Created by Oliver on 26.09.2015.
 */
public class QueryHandler {

    private static HashMap<String, String> queryResults = new HashMap<>();

    private String address;

    public QueryHandler() {
        address = (Env.dev == false)
                ? "https://semanticknowledgedbbackend.herokuapp.com/"
                : "http://192.168.178.76:5000";
    }

    public String performPostCall(String postBody) {
        if (queryResults.containsKey(postBody)) {
            return queryResults.get(postBody);
        }

        URL url;
        String response = "";
        try {
            url = new URL(address);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(15000);
            //conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Log.i("QueryHandler", "performPostCall: Sending query: " + postBody);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postBody);
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }

            Log.i("QueryHandler", "performPostCall: Received: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        queryResults.put(postBody, response);
        return response;
    }


}
