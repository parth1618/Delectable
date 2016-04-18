package web.module.customer;

public class PersonalInfo {
	
	private String name;
	private String email;
	private String phone;
	
	public PersonalInfo(){
		
	}
	
	public PersonalInfo(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	
	

}
