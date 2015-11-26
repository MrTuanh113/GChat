package Database;

public class User {
	int id;
	String user_id;
	String user_name;
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
