package org.posts.collect.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.posts.collect.model.Post;
import org.posts.collect.tools.PostParser;
import org.posts.collect.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Crawler {

	Logger logger = LoggerFactory.getLogger(Crawler.class);

	String initURL = "http://out.bitunion.org/forum-14-1.html";

	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		try {
			crawler.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void run() throws IOException {
		getGoodClient();
		String output = "/tmp/"+System.currentTimeMillis()+".json";
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(output)));
		List<Post> posts = parseIndexPage(initURL, true);
		Gson gson = new Gson();
		for(Post post : posts){
			int readCount = post.getReadCount();
			if(readCount > 1000){
				String url = post.getUrl();
				List<Post> details = parseIndexPage(url, false);
				Post post2 = details.get(0);
				String json = gson.toJson(post2);
				bw.write(json+"\n");
			}
		}
		bw.close();
		httpclient.close();
	}

	List<Post> parseIndexPage(String url, boolean list) {
		HttpGet get = new HttpGet(url);
		List<Post> parseList = null;
		try {
			CloseableHttpResponse execute = httpclient.execute(get);
			if (execute.getStatusLine().getStatusCode() == 200) {
				String html = IOUtils.toString(
						execute.getEntity().getContent(), "GBK");
				if(list){
					parseList = PostParser.parseList(html);
				}else{
					parseList = PostParser.parsePage(html);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return parseList;
		}
	}

	CloseableHttpClient httpclient;

	public void getGoodClient() {
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
	}

}
