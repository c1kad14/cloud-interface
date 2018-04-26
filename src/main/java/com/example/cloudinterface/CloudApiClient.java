package com.example.cloudinterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class CloudApiClient {
	
	private String createAuditJson(String time, String name, boolean status) {
		JsonObject personModel = Json.createObjectBuilder()
				.add("time", time)
				.add("name", name)
				.add("status", status)
				.build();
		return personModel.toString();
	}

	public void doPost(String endpoint, String json) throws Exception {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://interfacescosmosapi.azurewebsites.net/api/" + endpoint + "/add");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Bearer " + getToken());
		httpPost.setEntity(new StringEntity(json));
		HttpResponse response = httpClient.execute(httpPost);


		String requestUri = httpPost.getRequestLine().toString() +
				"\\n" + response.getStatusLine().getReasonPhrase();
		int responseCode = response.getStatusLine().getStatusCode();
		String responseTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String auditJson = createAuditJson(responseTime, requestUri, (responseCode == 200 ? true : false));
		
		HttpClient httpAuditlogClient = HttpClients.createDefault();
		HttpPost postAudit = new HttpPost("http://interfacescosmosapi.azurewebsites.net/api/auditlogs/add");
		postAudit.setHeader("Content-Type", "application/json");
		postAudit.setHeader("Authorization", "Bearer " + getToken());
		postAudit.setEntity(new StringEntity(auditJson));
		httpAuditlogClient.execute(postAudit);
	}
	
	public void doPut(String endpoint, String json) throws Exception {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut("http://interfacescosmosapi.azurewebsites.net/api/" + endpoint + "/Update");
		httpPut.setHeader("Content-Type", "application/json");
		httpPut.setHeader("Authorization", "Bearer " + getToken());
		httpPut.setEntity(new StringEntity(json));
		httpClient.execute(httpPut);
	}

	public String doGet(String endpoint, String id) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://interfacescosmosapi.azurewebsites.net/api/" + endpoint + "/get?id=" + id);
		httpGet.setHeader("Content-Type", "application/json");
		try {
			httpGet.setHeader("Authorization", "Bearer " + getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpResponse resp = null;
		try {
			resp = httpClient.execute(httpGet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity responseEntity = resp.getEntity();
		if (responseEntity != null) {
			InputStream in = null;
			try {
				in = responseEntity.getContent();
				StringBuilder strBuilder = new StringBuilder();
				int c;
				if (in != null) {
					while ((c = in.read()) != -1) {
						strBuilder.append((char) c);
					}
					return strBuilder.toString();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String doGet(String endpoint) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://interfacescosmosapi.azurewebsites.net/api/" + endpoint + "/getlist");
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("Authorization", "Bearer " + getToken());
		HttpResponse resp;
		try {
			resp = httpClient.execute(httpGet);
			HttpEntity responseEntity = resp.getEntity();
			if (responseEntity != null) {
				InputStream in = responseEntity.getContent();
				StringBuilder strBuilder = new StringBuilder();
				int c;
				while ((c = in.read()) != -1) {
					strBuilder.append((char) c);
				}
				return strBuilder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void doDelete(String endpoint, String id) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete("http://interfacescosmosapi.azurewebsites.net/api/" + endpoint + "/delete?id=" + id);
		httpDelete.setHeader("Content-Type", "application/json");
		try {
			httpDelete.setHeader("Authorization", "Bearer " + getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpResponse resp = null;
		try {
			resp = httpClient.execute(httpDelete);
		} catch (IOException e) {
			e.printStackTrace();
		}
		resp.getEntity();
	}

	private String getToken() {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost postOauth = new HttpPost("http://interfacescosmosapi.azurewebsites.net/api/token");
		postOauth.addHeader("content-type", "application/x-www-form-urlencoded");
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("username", "sds"));
		params.add(new BasicNameValuePair("password", "window"));
		try {
			postOauth.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse oauthResp = httpClient.execute(postOauth);
			HttpEntity responseEntity = oauthResp.getEntity();
			if (responseEntity != null) {
				InputStream in = responseEntity.getContent();
				StringBuilder strBuilder = new StringBuilder();
				int c;
				while ((c = in.read()) != -1) {
					strBuilder.append((char) c);
				}
				JSONObject json = new JSONObject(strBuilder.toString());
				return json.getString("access_token");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



		return null;
	}
}
