package testpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpJsonClient {
	private static Logger log = Logger.getLogger(HttpJsonClient.class);

	public static String post(String url, String params) {
		HttpClient httpclient = new DefaultHttpClient();
//		if (!PayConfig.DEBUG_MOD) {// 生产环境时使用https
//			httpclient = WebClientDevWrapper.wrapClient(httpclient);
//		}
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);
		System.out.println(post);

		body = invoke(httpclient, post);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	public static String postgbk(String url, JSONObject params) {
		HttpClient httpclient = new DefaultHttpClient();

		String body = null;
		URL tem_url;
		URI uri = null;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.getString(key)));
		}
		String param = URLEncodedUtils.format(nvps, "gb2312");
		try {
			tem_url = new URL(url);
			uri = new URI(tem_url.getProtocol(), tem_url.getHost() + ":"
					+ tem_url.getPort(), tem_url.getPath(), param, null);
			System.out.println(uri);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost post = new HttpPost(uri);
		System.out.println(post);

		try {
			HttpResponse response = httpclient.execute(post);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "GB2312"));
			String line;
			while ((line = in.readLine()) != null) {
				body = line;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		URL tem_url;
		URI uri = null;

		try {
			tem_url = new URL(url);
			uri = new URI(tem_url.getProtocol(), tem_url.getHost() + ":"
					+ tem_url.getPort(), tem_url.getPath(), tem_url.getQuery(),
					null);
			System.out.println(uri);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		log.info("create httppost:" + uri);
		HttpGet get = new HttpGet(uri);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(HttpClient httpclient, HttpUriRequest httpost) {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		String body = null;
		try {
			body = EntityUtils.toString(entity);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

	private static HttpResponse sendRequest(HttpClient httpclient,
			HttpUriRequest httpost) {
		log.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, String params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		// Set<String> keySet = params.keySet();
		// for(String key : keySet) {
		// nvps.add(new BasicNameValuePair(key, params.get(key)));
		// }

		try {
			log.info("set utf-8 form entity to httppost");
			httpost.addHeader("Content-type", "application/json; charset=utf-8");
			httpost.setHeader("Accept", "application/json");
			httpost.setEntity(new StringEntity(params, Charset.forName("UTF-8")));

			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpost.getEntity().getContent(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return httpost;
	}

	public static class WebClientDevWrapper {

		@SuppressWarnings("deprecation")
		public static org.apache.http.client.HttpClient wrapClient(
				org.apache.http.client.HttpClient base) {
			try {
				SSLContext ctx = SSLContext.getInstance("TLS");
				X509TrustManager tm = new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] arg0,
							String arg1) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] arg0,
							String arg1) throws CertificateException {
					}
				};
				ctx.init(null, new TrustManager[] { tm }, null);
				SSLSocketFactory ssf = new SSLSocketFactory(ctx,
						SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("https", 443, ssf));
				ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(
						registry);
				return new DefaultHttpClient(mgr, base.getParams());
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}
}
