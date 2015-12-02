package Database;

import java.util.Date;

public class User {
	int id;
	String user_id;
	String user_name;
	int state;
	String createAt;
	
	
	public User(){
		
		
	}
public User(int u_id,String ur_id,String ur_name){
		this.id=u_id;
		this.user_id=ur_id;
		this.user_name=ur_name;
		
		
	}
public User(String ur_id, String ur_name){
	this.user_id = ur_id;
	this.user_name=ur_name;
	
	
}
public String getCreateAt() {
	return createAt;
}
public void setCreateAt(String createAt) {
	this.createAt = createAt;
}
public User(String ur_id, String ur_name,int state,String createAt){
	this.user_id = ur_id;
	this.user_name=ur_name;
	this.state= state;
	this.createAt = createAt;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getUser_name() {
	return user_name;
}
public void setUser_name(String user_name) {
	this.user_name = user_name;
}

	
}
