package com.cloudinterface;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

class Interface {
    public String name;

}

@RestController
@RequestMapping("/api/interfaces")
public class InterfacesApiController {

    @PostMapping(value="")
    public String doPost(@RequestBody String object) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://interfacescosmosapi.azurewebsites.net/api/token");
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("username", "sds"));
        params.add(new BasicNameValuePair("password", "window"));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
                StringBuilder builder = new StringBuilder();
                int i;
                while((i = instream.read()) != -1) {
                    builder.append((char)i);
                }
                JSONObject jsonObj = new JSONObject(builder.toString());
                String token = jsonObj.getString("access_token");
                HttpPost httppost1 = new HttpPost("https://interfacescosmosapi.azurewebsites.net/api/interfaces/add");
                httppost1.setHeader("Authorization", "Bearer " + token);
                httppost1.setHeader("Content-Type", "application/json");
                httppost1.setEntity(new StringEntity(object));
                httpclient.execute(httppost1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                instream.close();
            }
        }
        return null;
    }

    @GetMapping("/interfaces")
    public String doGet(@RequestParam String id) {
        return id;
    }
}
