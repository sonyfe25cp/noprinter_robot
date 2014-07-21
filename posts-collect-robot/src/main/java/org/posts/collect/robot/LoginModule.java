package org.posts.collect.robot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.posts.collect.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginModule {

	Logger logger = LoggerFactory.getLogger(LoginModule.class);

	CloseableHttpClient httpclient;

	public CloseableHttpClient getGoodClient() {
		httpclient = HttpClients.custom()
				.setDefaultCookieStore(new BasicCookieStore()).build();// 可以帮助记录cookie
		try {
			HttpPost loginPost = new HttpPost(
					"http://out.bitunion.org/logging.php?action=login");
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("action", "login"));
			nvps.add(new BasicNameValuePair("username", Utils.username));
			nvps.add(new BasicNameValuePair("password", Utils.password));
			nvps.add(new BasicNameValuePair("cookietime", "3600"));
			nvps.add(new BasicNameValuePair("referer", "/home.php?"));
			nvps.add(new BasicNameValuePair("verify", "40422"));
			nvps.add(new BasicNameValuePair("verifyimgid",
					"20bbf91c2113e54306bb73e39642a5a0"));
			nvps.add(new BasicNameValuePair("styleid", "7"));
			nvps.add(new BasicNameValuePair("loginsubmit", "会员登录"));
			loginPost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));

			logger.info("loginPost: {}", loginPost.toString());
			CloseableHttpResponse response2 = httpclient.execute(loginPost);
			logger.info("status: {}", response2.getStatusLine().getStatusCode());
			if (response2.getStatusLine().getStatusCode() == 200) {
				String html = IOUtils.toString(response2.getEntity()
						.getContent(), "GBK");
			}
			response2.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpclient;
	}

}
