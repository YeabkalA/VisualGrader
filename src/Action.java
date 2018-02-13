import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Action {
	private static String mainFolderPath;
	public static ArrayList<Student> students = new ArrayList<>();
	public static ArrayList<Grade> grades = new ArrayList<>();

	
	public static String teacherPassword;
	public static String teacherEmail;
	public static String teacherName;

	private static String keyPath = "";
	public static String examName = "";
	public static String key = "";
	public static HashMap<Integer, String> nameIDMap = new HashMap<>();
	public static HashMap<Integer, String> emailIDMap = new HashMap<>();
	
	
	
	private static String studentsDataDir = "";
	private static String teacherDataDir = "";
	private static String answerSheetDir = "";
	
	private HashMap<Student, Grade> gradeMap = new HashMap<>();
	
	private static BufferedReader b;
	
	
	public static void setStudentsDataPath(String path) {
		studentsDataDir = path;
	}
	public static void setTeacherDataPath(String path) {
		teacherDataDir = path;
	}
	public static void setAnswerSheetPath(String path) {
		answerSheetDir= path;
	}
	public static void setKeyPath(String path) throws IOException {
		keyPath = path;
		setKey();
	}
	public static void setExamName(String name) {
		examName = name;
	}
	
	
	public static String getStudentsDataPath() {
		return studentsDataDir;
	}
	
	public static String getTeacherDataPath() {
		return teacherDataDir;
	}
	
	public static String getAnsSheetDataPath() {
		return answerSheetDir;
	}
	
	public static String getKeyPath() {
		return keyPath;
	}
	public static String getExamName() {
		return examName;
	}
	public static void resetStudents() {
		students = new ArrayList<Student>();
	}
	
	public static void resetGrades() {
		grades = new ArrayList<Grade>();
	}
	
	public static boolean noStudent() {
		return students.size()==0;
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		//setStudentsDataPath("/home/ywubshit/Desktop/VisualGraderDocs/tryStudent.csv");
		//setTeacherDataPath("/home/ywubshit/Desktop/VisualGraderDocs/mainTeacherData.csv");
		populateMaps();
		System.out.println(nameIDMap.size());
		for(int id: nameIDMap.keySet()) {
			System.out.println(nameIDMap.get(id) + " " + emailIDMap.get(id));
		}
		//setAnswerSheetPath("/home/ywubshit/Desktop/VisualGraderDocs/AnswerSheets");
	    //setKeyPath("/home/ywubshit/Desktop/VisualGraderDocs/key.csv");
		System.out.println(keyPath);
		gradeExams();
		System.out.println("Graded " + students.size());
		for(Student s: students) {
			System.out.println(s.getName() + " " + s.getGrade().getRawGrade() + " " + s.getID());
		}
		System.out.println(teacherName);
		
		for(Student s: students) {
			System.out.println(s.getID() + s.getEmail());
		}
		
	}
	public static void emailGrades() {
		for(Student s: students) {
			final String username = "yeabkalaw@gmail.com";
			final String password = "temppasswor";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable","true");
			props.put("mail.smtp.host","smtp.gmail.com");
			props.put("mail.smtp.port","587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(username,password);
				}

			});

			try{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("yeabkalaw@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(s.getEmail()));
				System.out.println(s.getEmail());
				message.setSubject("Your grade for" + examName);
				message.setContent("Dear " + s.getName().split(" ")[0] + ", \n Your grade for " + examName + " is " + s.getGrade().getRawGrade()
						+ "/" + key.length() + "\n . The 25th and 75th percentile for this class were" + getStat()[2] + " and " + 
						getStat()[3] + ", respectively \n\n Sincerely \n\n" + teacherName, "text/html; charset=utf-8");
				Transport.send(message);
				System.out.println("the email was sent");
			} catch (MessagingException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void gradeExams() throws IOException {	
		File[] answerSheets = new File(answerSheetDir).listFiles();
		System.out.println("Seeing " + students.size());
		for(File f: answerSheets) {
			
			Grade g = ImageParser.grade(f.getAbsolutePath(), key);
			int id = ImageParser.getID(f.getAbsolutePath());
			System.out.println("======================="+id);
			String name= "", email = "";
			if(nameIDMap.containsKey(id))
				
				name = nameIDMap.get(Integer.parseInt(Integer.toString(id).substring(0)));
			else
				name = "unknown";
			if(emailIDMap.containsKey(id))
				 email = emailIDMap.get(id);
			else
				email = "yeabkalaw@gmail.com";
			Student s = new Student(id, name, email, g);
			students.add(s);
			grades.add(g);
		}
		
		
	}
	
	public static void populateMaps( ) throws IOException {
		FileReader f = new FileReader(studentsDataDir);
		b = new BufferedReader(f);
		String studentID = "", studentName = "", studentEmail = "";
		String line = "";
		while((line = b.readLine())!=null) {
			
			String[] data = line.split(",");
			studentName = data[1];
			studentID = data[0];
			studentEmail = data[2];
			nameIDMap.put(Integer.parseInt(studentID), studentName);
			emailIDMap.put(Integer.parseInt(studentID), studentEmail);
		}
		
		f = new FileReader(teacherDataDir);
		b = new BufferedReader(f);
		while((line = b.readLine())!=null) {
			
			String[] teacherData = line.split(",");
			System.out.println(Arrays.toString(teacherData));
			teacherName = teacherData[0];
			teacherEmail = teacherData[1];
			teacherPassword = teacherData[2];
		}
	}
	
	public static void setKey() throws IOException{
		FileReader f = new FileReader(keyPath);
		HashMap<String, String> letterToNo = new HashMap<>();
		letterToNo.put("A", "0");
		letterToNo.put("B", "1");
		letterToNo.put("C", "2");
		letterToNo.put("D", "3");
		letterToNo.put("E", "4");
		b = new BufferedReader(f);
		String line = "";
		StringBuilder sb = new StringBuilder();
		while((line = b.readLine())!=null) {
			sb.append(letterToNo.get(Character.toString(line.charAt(line.length()-1))));
		}
		key = sb.toString();
		System.out.println(key);
	}
	
	public static int[] getStat() {
		// smallest, largest, 25th, 75th, median
		
		int[] grades = new int[students.size()];
		for(int i=0;i<students.size();i++) {
			grades[i] = students.get(i).getGrade().getRawGrade();
			
		}
		Arrays.sort(grades);
		int median = 0;
		if((int)grades.length/2 == grades.length/2.0)
			median = grades[grades.length/2];
		else
			median = (grades[grades.length/2] + grades[grades.length/2 + 1])/2;
		int [] ret = {grades[0], grades[grades.length-1], grades[grades.length/4], grades[3*grades.length/4], median};
		return ret;
	}
	
	
}
