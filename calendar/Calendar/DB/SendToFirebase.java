package Calendar.DB;

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
           this.message = message;
           this.token = "token="+token;
           System.out.println(message);
           System.out.println(token);
           System.out.println(url);
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
               URLConnection conn; conn = (URLConnection)url.openConnection();
               //String data = "[message]="+URLEncoder.encode(message,"UTF-8") +
               //					"&[Token]="+URLEncoder.encode(token,"UTF-8");
               String data = String.format("message=%s&Token=%s",
            		   URLEncoder.encode(message,"UTF-8"),
            		   URLEncoder.encode(token,"UTF-8"));
               
               if(conn != null) {
                   conn.setConnectTimeout(5000);
                   conn.setDoInput(true);
                   conn.setDoOutput(true);
                   
                   OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                   writer.write(data);
                   writer.flush();
                   
                   BufferedReader rd = new BufferedReader(
                             new InputStreamReader(conn.getInputStream(), "UTF-8"));
                       String line = null;
                       while ((line = rd.readLine()) != null) {
                    	   output.append(line + "\n");
                       }
                       
                       rd.close();
                       writer.close();
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