package com.omartech;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录Bitunion
 * 
 * @author Sonyfe25cp
 * @date Apr 23, 2014
 */

public class AutoFlashBitunion {

	Logger logger = LoggerFactory.getLogger(AutoFlashBitunion.class);

	public static void main(String[] args) {
		AutoFlashBitunion tl = new AutoFlashBitunion();
		tl.run();
	}

	CloseableHttpClient httpclient;

	void run() {
		httpclient = HttpClients.custom()
				.setDefaultCookieStore(new BasicCookieStore()).build();// 可以帮助记录cookie
		try {

			HttpPost loginPost = new HttpPost(
					"http://out.bitunion.org/logging.php?action=login");
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("action", "login"));
			nvps.add(new BasicNameValuePair("username", "mikki"));
			nvps.add(new BasicNameValuePair("password", "nvidia7600"));
			nvps.add(new BasicNameValuePair("cookietime", "3600"));
			nvps.add(new BasicNameValuePair("referer", "/home.php?"));
			nvps.add(new BasicNameValuePair("verify", "40422"));
			nvps.add(new BasicNameValuePair("verifyimgid",
					"20bbf91c2113e54306bb73e39642a5a0"));
			nvps.add(new BasicNameValuePair("styleid", "7"));
			nvps.add(new BasicNameValuePair("loginsubmit", "会员登录"));
			loginPost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));

			for (NameValuePair bnvp : nvps) {
				logger.info("nvp : {} -- {}", bnvp.getName(), bnvp.getValue());
			}
			logger.info("loginPost: {}", loginPost.toString());
			CloseableHttpResponse response2 = httpclient.execute(loginPost);
			logger.info("status: {}", response2.getStatusLine().getStatusCode());
			if (response2.getStatusLine().getStatusCode() == 200) {
				String html = IOUtils.toString(response2.getEntity()
						.getContent(), "GBK");
			}
			response2.close();
			updatePost();
			httpclient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取验证码
	 */
	void getVerify() {
		try {
			HttpGet get = new HttpGet("http://out.bitunion.org");
			String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
			get.setHeader("User-Agent", userAgent);
			CloseableHttpResponse response1;
			response1 = httpclient.execute(get);

			String verifyCodePath = "";
			String verifyCode = "";
			if (response1.getStatusLine().getStatusCode() == 200) {
				String html = IOUtils.toString(response1.getEntity()
						.getContent(), "GBK");
				// logger.info(html);
				Document doc = Jsoup.parse(html);
				Elements imgs = doc.select("img");
				for (Element img : imgs) {
					String url = img.attr("src");
					if (url.contains("verifyimg")) {
						verifyCodePath = url.substring(url.indexOf("=") + 1);
						logger.info(verifyCodePath);
						logger.info("请输入验证码：http://out.bitunion.org/{}", url);
						byte[] b = new byte[1024];
						int n = System.in.read(b);
						verifyCode = new String(b, 0, n);
						logger.info("验证码是: {}", verifyCode);
					}
				}
			}
			Header[] h = response1.getAllHeaders();
			for (Header hh : h) {
				logger.info("{} -- {}", hh.getName(), hh.getValue());
				get.setHeader(hh.getName(), hh.getValue());
			}
			response1.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新帖子内容
	 */
	void updatePost() {

		DateFormat df = new SimpleDateFormat("[yyyy-MM-dd]");
		Date date = new Date();
		String today = df.format(date);

		InputStream inputStream = AutoFlashBitunion.class.getClassLoader()
				.getResourceAsStream("post.txt");
		List<String> lines = null;
		try {
			lines = IOUtils.readLines(inputStream);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append("\n");
		}
		sb.append("updated at : " + today + "\n");
		String content = sb.toString();

		String[] array = { "【NoPrinter打印店】论文打印我们更专业！！！", "放假不回家的童鞋，寄些照片回家吧~~",
				"电子书打印哪家强？北理菜市场NoPrinter打印店啊~~", "顶贴送代金券啦~~打印店赔本赚吆喝啦~~~" };

		Random random = new Random();
		float nextDouble = random.nextFloat();
		float a = nextDouble * (array.length - 1);
		int round = Math.round(a);
		String titleBak = array[round];
		System.out.println(round);
		A meb = new A();
		meb.addTextBody("editsubmit", "submit");
		meb.addTextBody("page", "1");
		meb.addTextBody("viewperm", "0");
		meb.addTextBody("tag", "527");
		meb.addTextBody("subject", today + " " + titleBak,
				ContentType.APPLICATION_OCTET_STREAM);
		meb.addTextBody("origsubject", "【NoPrinter打印店】论文打印我们更专业！！！",
				ContentType.APPLICATION_OCTET_STREAM);
		meb.addTextBody("posticon", "0");
		meb.addTextBody("mode", "2");
		meb.addTextBody("font", "Verdana");
		meb.addTextBody("size", "3");
		meb.addTextBody("color", "White");
		meb.addTextBody("message", content,
				ContentType.APPLICATION_OCTET_STREAM);
		meb.addTextBody("usesig", "1");
		meb.addTextBody("attachedit", "new");
		meb.addTextBody("fid", "114");
		meb.addTextBody("tid", "10534806");
		meb.addTextBody("pid", "13965621");
		meb.addTextBody("postsubject", "【NoPrinter打印店】论文打印我们更专业！！！",
				ContentType.APPLICATION_OCTET_STREAM);

		HttpPost post = new HttpPost(
				"http://out.bitunion.org/post.php?action=edit");
		try {
			post.setEntity(new UrlEncodedFormEntity(meb.get(), "gbk"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			HttpResponse res = httpclient.execute(post);
			int code = res.getStatusLine().getStatusCode();
			String html = EntityUtils.toString(res.getEntity(), "gbk");
			logger.info("html: {}", html);
			Header[] t = res.getHeaders("Location");

			logger.info("code: {}, {}", code, t[0]);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试显示随便一个登录后才能看的页面
	 * 
	 * @param url
	 */
	void showGet(String url) {
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(get);
			int code = response.getStatusLine().getStatusCode();
			switch (code) {
			case 200:
				String html = IOUtils.toString(response.getEntity()
						.getContent(), "GBK");
				logger.info(html);
				break;
			default:
				logger.info("code: {}", code);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class A {
	private List<NameValuePair> lists = new ArrayList<>();

	public void addTextBody(String key, String value) {
		lists.add(new BasicNameValuePair(key, value));
	}

	public void addTextBody(String key, String value, ContentType t) {
		lists.add(new BasicNameValuePair(key, value));
	}

	public List<NameValuePair> get() {
		return lists;
	}
}