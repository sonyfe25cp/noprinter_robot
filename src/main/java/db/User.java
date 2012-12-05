package db;

import java.util.HashMap;

public class User {
	
	private String realname;
	private String email;
	private String address;
	private String phoneNum;
	private String promised;
	
	private String _id;
	private String className;
	private String password;
	private HashMap<String,String> regTime;
	private HashMap<String,String> areaCode;
	private String isSchoolAdmin;
	private String acceptEmailNotification;
	
	public String toString(){
		String str= String.format(" INSERT INTO user_users (" +
				"'email', 'encrypted_password', 'username', " +
				"'realname', 'area_code', 'address', 'phone', 'promised') " +
				"VALUES (" +
				"'%s', '%s', '%s', '%s', '北京理工大学-中关村校区', '%s', '%s', true);",
				email,password,_id,realname==null?"":realname,address==null?"":address,phoneNum==null?"":phoneNum);
		return str;
	}
	public void showMe(){
		System.out.println(this.toString());
	}
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPromised() {
		return promised;
	}
	public void setPromised(String promised) {
		this.promised = promised;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public HashMap<String, String> getRegTime() {
		return regTime;
	}
	public void setRegTime(HashMap<String, String> regTime) {
		this.regTime = regTime;
	}
	public HashMap<String, String> getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(HashMap<String, String> areaCode) {
		this.areaCode = areaCode;
	}
	public String getIsSchoolAdmin() {
		return isSchoolAdmin;
	}
	public void setIsSchoolAdmin(String isSchoolAdmin) {
		this.isSchoolAdmin = isSchoolAdmin;
	}
	public String getAcceptEmailNotification() {
		return acceptEmailNotification;
	}
	public void setAcceptEmailNotification(String acceptEmailNotification) {
		this.acceptEmailNotification = acceptEmailNotification;
	}
	
	

}
