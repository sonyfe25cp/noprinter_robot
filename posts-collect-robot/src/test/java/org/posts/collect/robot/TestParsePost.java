package org.posts.collect.robot;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.posts.collect.model.Post;
import org.posts.collect.tools.PostParser;
import org.posts.collect.tools.Utils;

public class TestParsePost extends TestCase{
	
	
	@Test
	public void testParseListPage(){
		String file = "posts.html";
		String html =  Utils.getResouce(file);
		
		PostParser pp = new PostParser();
		
		List<Post> parseList = pp.parseList(html);
		for(Post post : parseList){
			System.out.println(post.toString());
		}
		System.err.println(parseList.size());
		assertEquals(17, parseList.size());
	}
	@Test
	public void testParseListPage2(){
		String file = "post.html";
		String html =  Utils.getResouce(file);
		
		PostParser pp = new PostParser();
		
		List<Post> parseList = pp.parseList(html);
		for(Post post : parseList){
			System.out.println(post.toString());
		}
		System.err.println(parseList.size());
	}
	
}
