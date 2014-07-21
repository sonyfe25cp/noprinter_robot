package org.posts.collect.tools;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	static Logger logger = LoggerFactory.getLogger(Utils.class);
	public static final String getResouce(String name) {
		try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(
				name)) {
			return IOUtils.toString(is);
		} catch (IOException e) {
			throw new RuntimeException("not found " + name, e);
		}
	}

	private static List<String> lines;
	public static String username;
	public static String password;
	static {
		try {
			InputStream is = Utils.class.getClassLoader().getResourceAsStream(
					"config");
			List<String> lines;
			lines = IOUtils.readLines(is);
			for (String line : lines) {
				if (line.startsWith("username")) {
					username = line.split(":")[1];
				} else if (line.startsWith("password")) {
					password = line.split(":")[1];
				}
			}
		} catch (IOException e) {
			logger.error("read config file error, the program will exit");
			System.exit(0);
		}
	}

	static DateFormat df = new SimpleDateFormat("yyyy-M-d");

	public static Date transPublishDate(String str) {
		Date date = null;
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
