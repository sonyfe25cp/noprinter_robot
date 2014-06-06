package org.posts.collect.model;

import java.util.Date;

/**
 * @author Sonyfe25cp
 *
 * 2014年6月7日
 */
public class Post {

	private int id;
	private int postId;
	private String title;
	private String author;
	private Date publishDate;
	private int readCount;
	private int replyCount;
	private String url;
	private Date updateDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", postId=" + postId + ", title=" + title
				+ ", author=" + author + ", publishDate=" + publishDate
				+ ", readCount=" + readCount + ", replyCount=" + replyCount
				+ ", url=" + url + ", updateDate=" + updateDate + "]";
	}
	
	

}
