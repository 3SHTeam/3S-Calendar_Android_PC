package Calendar.DB;

import java.io.*;
import java.net.*;

public class SendToDB extends Thread{
    private String result, URL, message;

       public SendToDB(String url, String message){
           URL = url;
           this.message = "message="+message;
       }

       @Override
       public void run() {
          final String output = request(URL,message);
          result = output;
       }
       
       private String request(String urlStr, String message) {
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