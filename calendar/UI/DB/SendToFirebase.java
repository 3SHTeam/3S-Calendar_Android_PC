package DB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendToFirebase extends Thread{
    private String result, URL, message, token;

       public SendToFirebase(String url, String message, String token){
           URL = url;
           this.message = "message="+message;
           this.token = "token="+token;
       }

       @Override
       public void run() {
          final String output = request(URL,message,token);
          result = output;
       }
       
       private String request(String urlStr, String message, String token) {
           StringBuilder output = new StringBuilder();
           try{
               URL url = new URL(urlStr);
               HttpURLConnection conn = (HttpURLConnection)url.openConnection();
               if(conn != null) {
                   conn.setConnectTimeout(5000);
                   conn.setDoInput(true);
                   conn.setDoOutput(true);

                   BufferedWriter writer = new BufferedWriter(
                         new OutputStreamWriter(conn.getOutputStream()));
                   writer.write(message, 0, message.length());
                   //writer.newLine();
                   writer.write(token, 0, token.length());
                   writer.flush();
                   writer.close();
                   
                   int resCode = conn.getResponseCode();
                   if (resCode == HttpURLConnection.HTTP_OK) {
                       BufferedReader reader = new BufferedReader(
                             new InputStreamReader(conn.getInputStream(), "UTF-8"));
                       String line = null;
                       while (true) {
                           line = reader.readLine();
                           if (line == null) {
                               break;
                           }
                           output.append(line + "\n");
                       }
                       reader.close();
                       conn.disconnect();
                   }
               }
           }catch (Exception ex){
               ex.printStackTrace();
           }
           return output.toString();
       }

       public String getResult(){
           return result;
       }
}