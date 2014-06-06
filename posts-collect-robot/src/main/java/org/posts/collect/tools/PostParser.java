package org.posts.collect.tools;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.posts.collect.model.Post;

public class PostParser {
	
	public static List<Post> parsePage(String html){
		return null;
	}
	
	
	/**
	 * 解析posts列表页
	 */
	public static List<Post> parseList(String html) {
		List<Post> posts = new ArrayList<>();

		Document document = Jsoup.parse(html);

		Elements spans = document.select("span");
		Element mark = null;
		for (Element span : spans) {
			if (span.text().equals("普通主题")) {
				mark = span;
			}
		}

		Element parentTr = mark.parent().parent();

		Elements trs = parentTr.siblingElements();
		boolean flag = false;
		int i = 0;
		for (Element tr : trs) {
			if (i > 5) {
				flag = true;
			}
			if (flag) {
				Post post = new Post();
				Elements tds = tr.select("td");
				int index = 0;
				for (Element td : tds) {
					switch (index) {
					case 2:
						Elements children = td.children();
						boolean okFlag = true;
						StringBuilder sb = new StringBuilder();
						for (Element child : children) {
							if (child.tagName().equals("img")) {
								okFlag = false;
							}
							if (okFlag) {
								String attr = child.attr("href");
								if (attr.startsWith("thread-")) {
									post.setUrl(attr);
								}
								sb.append(child.text());
							}
						}
						post.setTitle(sb.toString());
						break;
					case 3:
						String text2 = td.select("a").text();
						post.setAuthor(text2);
						String publishDate = td.select("span").text();
						post.setPublishDate(Utils.transPublishDate(publishDate));
						break;
					case 4:
						String text3 = td.text();
						post.setReplyCount(Integer.parseInt(text3));
						break;
					case 5:
						String text4 = td.text();
						post.setReadCount(Integer.parseInt(text4));
						break;
					case 6:
						String text5 = td.text();
						String updateTime = text5.substring(0,
								text5.indexOf(" "));
						String lastAuthor = text5.substring(text5
								.lastIndexOf("by") + 3);
						post.setUpdateDate(Utils.transPublishDate(updateTime));
						break;
					default:
						break;
					}
					index++;
				}
				posts.add(post);
			}
			i++;
		}
		return posts;
	}

}
