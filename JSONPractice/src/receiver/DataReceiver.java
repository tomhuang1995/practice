package receiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataReceiver {
	
	private String url;
	public DataReceiver (String url)
	{
		this.url = url;
	}

	
    public JSONObject getData () throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST either fully consume the response content  or abort request
            // execution by calling CloseableHttpResponse#close().

            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                BufferedReader rd = new BufferedReader
                		  (new InputStreamReader(entity1.getContent()));
                
                JSONObject stuff = new JSONObject (rd.toString());
                
                EntityUtils.consume(entity1);
                return stuff;
            } finally {
                response1.close();
            }

           
        } finally {
            httpclient.close();
        }
        
        
    }

}