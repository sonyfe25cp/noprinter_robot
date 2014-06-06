package org.posts.collect.tools;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;

public class Utils {

    public static final String getResouce(String name) {
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(name)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException("not found " + name, e);
        }
    }
    
    static DateFormat df = new SimpleDateFormat("yyyy-M-d");
    public static Date transPublishDate(String str){
    		Date date = null;
			try {
				date = df.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		return date;
    }
}
