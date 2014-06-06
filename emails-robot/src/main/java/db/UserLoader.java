package db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class UserLoader {

	public static void main(String[] args) {
		String filePath = "users_data.txt";
		String ip = "localhost";
		int port = 27017;
		String userTable = "User";
		String dbName = "cloud";
		for (int i = 0; i < args.length;) {
			if (args[i].equals("-f")) {
				filePath = args[i + 1];
				i += 2;
			}else if(args[i].equals("-i")) {
				ip = args[i + 1];
				i += 2;
			}else if(args[i].equals("-p")) {
				port = Integer.parseInt(args[i + 1]);
				i += 2;
			}else if(args[i].equals("-d")) {
				dbName = args[i + 1];
				i += 2;
			}else if(args[i].equals("-c")) {
				userTable = args[i + 1];
				i += 2;
			} else if (args[i].equals("-h")) {
				System.out.println("-f 保存的文件名,默認值：users_data.txt");
				System.out.println("-i mongoDB的ip,默認值：127.0.0.1");
				System.out.println("-p mongoDB的port,默認值：27017");
				System.out.println("-d mongoDB的db名称,默認值：cloud");
				System.out.println("-c mongoDB的user表名称,默認值：User");
				System.exit(0);
			}
		}
		try {
			Gson gson = new Gson();
			Mongo mongo = new Mongo(ip, port);
			DB db = mongo.getDB(dbName);
			DBCollection collection = db.getCollection(userTable);
			BasicDBObject searchQuery = new BasicDBObject();
			DBCursor cursor = collection.find(searchQuery);
			File file = new File(filePath);
			FileWriter fw =new FileWriter(file);
			while (cursor.hasNext()) {
				DBObject object = cursor.next();
				String jsonString = object.toString();
				User user = gson.fromJson(jsonString, new TypeToken<User>() {}.getType());
				fw.write(user.toString());
				fw.write("\n");
			}
			fw.flush();
			fw.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
