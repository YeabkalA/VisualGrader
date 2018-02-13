import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Student{
	private int id;
	private String name;
	private String email;
	private Grade grade;

	public Student(int id, String name, String email, Grade g) {
		this.id=id;
		this.email = email;
		this.name = name;
		this.grade = g;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Grade getGrade() {
		return this.grade;
	}

	public void sendGrade() {
		

	}
	



}