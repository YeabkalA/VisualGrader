import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageParser {
	
	public static void main(String[] args) throws IOException {
		//getAnswers("",30);
		//Grade g = grade("/home/ywubshit/Desktop/VisualGraderDocs/NewAnswerSheets/1213.png", "1234123412341234");
		//System.out.println(getID("/home/ywubshit/eclipse-workspace/VisualGrader/Docs/answer_sheets/ansSheet3.jpg"));
		//System.out.println(g.getMissedList());
		System.out.println(grade("/home/ywubshit/Desktop/VisualGraderDocs/AnswerSheets/4321.png", "00000"));
		//System.out.println(getAnswers("/home/ywubshit/Desktop/VisualGraderDocs/AnswerSheets/1214.png", 50));
		File[] answerSheets = new File("/home/ywubshit/Desktop/VisualGraderDocs/ClassAnswerSheets").listFiles();
		for(File f: answerSheets) {
			int id = ImageParser.getID(f.getAbsolutePath());
			System.out.println("======================="+id);
		}
	}


	public static HashMap<Integer, Integer> getAnswers(String filePath, int numQsn) throws IOException {
		
		HashMap<Integer, Integer> ansMap = new HashMap<>();
		//BufferedImage img = ImageIO.read(new File("/home/ywubshit/Desktop/ansSheet4.jpg"));
		BufferedImage img = ImageIO.read(new File(filePath));
		int startY = (int)((111.5/279)*img.getHeight());
		int endY = (int)(((111.5 + 130.5)/279)*img.getHeight());
		int startX = (int)((40.35/217)*img.getWidth());
		int startX_C2 = (int)(((40.35 + 69 + 13.83) /217)*img.getWidth());
		int endX = (int)(((217-25)/217.0)*img.getWidth());
		int cellH = (int)((5.23/279)*img.getHeight());
		int cellW = (int)((13.83/217.0)*img.getWidth());

		int qsn = 1;
		for(int i=startY;i<=endY;i+=cellH) {

			int choice = 1;
			int[] answers = new int[5];


			for(int j=startX;j<=(((40.35 + 68.5)/217)*img.getWidth());j+=cellW) {

				int total= 0, rs = 0, gs = 0,bs = 0;
				for(int k=j;k<j+cellW;k++) {		
					for(int m=i;m<i+cellH;m++) {

						int rgb = img.getRGB(k,m);
						int r = (0x00ff0000 & rgb) >> 16;
						int g = (0x0000ff00 & rgb) >> 8;
						int b = (0x000000ff & rgb);
						rs+=r;
						gs+=g;
						bs+=b;
						total = total + 1;
					}
				}
				int average = (rs/total + gs/total + bs/total)/3;

				//map.put(Integer.toString(qsn) + "-" + Integer.toString(choice), average);

				if(choice<=5)
					answers[choice-1]  = average;
				choice++;
			}
			boolean selected = false;
			for(int x: answers) {
				if(x<200) {
					selected = true;
					break;
				}
			}
			if(selected) {
				ArrayList<Integer> ans = new ArrayList<>();
				for(int y: answers) {
					ans.add(y);
				}
				System.out.println(qsn + "-" + ans.indexOf(Collections.min(ans)));
				ansMap.put(qsn, ans.indexOf(Collections.min(ans)));
			}
			else {
				System.out.println(qsn + "No answers selected");
				ansMap.put(qsn, -1);
			}
			qsn++;
			if(qsn>numQsn)
				return ansMap;
			if(qsn>25)
				break;
		}





		for(int i=startY;i<=endY;i+=cellH) {
			int choice = 1;
			int[] answers = new int[5];


			for(int j=startX_C2;j<=(((40.35 + 69 + 13.83 + 68.5)/217)*img.getWidth());j+=cellW) {

				int total= 0, rs = 0, gs = 0,bs = 0;
				for(int k=j;k<j+cellW;k++) {		
					for(int m=i;m<i+cellH;m++) {

						int rgb = img.getRGB(k,m);
						int r = (0x00ff0000 & rgb) >> 16;
						int g = (0x0000ff00 & rgb) >> 8;
						int b = (0x000000ff & rgb);
						rs+=r;
						gs+=g;
						bs+=b;
						total = total + 1;
					}
				}
				int average = (rs/total + gs/total + bs/total)/3;
				//map.put(Integer.toString(qsn) + "-" + Integer.toString(choice), average);

				if(choice<=5)
					answers[choice-1]  = average;
				choice++;
			}
			boolean selected = false;
			for(int x: answers) {
				if(x<200)
					selected = true;
			}
			if(selected) {
				ArrayList<Integer> ans = new ArrayList<>();
				for(int y: answers) {
					ans.add(y);
				}
				//System.out.println(qsn + "-" + ans.indexOf(Collections.min(ans)));
				ansMap.put(qsn, ans.indexOf(Collections.min(ans)));
			}
			else {
				//System.out.println(qsn + "No answers selected");
				ansMap.put(qsn, -1);
			}
			qsn++;
			if(qsn>numQsn)
				return ansMap;
			if(qsn>50)
				break;
		}


		//System.out.println(ansMap);
		return ansMap;
	}
	
	public static int getID(String filePath) throws IOException {
		//int[][] allAn = new int[4][4];
		BufferedImage img = ImageIO.read(new File(filePath));
		int startY = (int)((43.0/279)*img.getHeight());
		int endY = (int)(((90.0)/279)*img.getHeight());
		int startX = (int)((27.5/217)*img.getWidth());
		int endX = (int)(((107.0)/217)*img.getWidth());
		double cellH = (int)(((44.5/4)/279)*img.getHeight());
		double cellW = (int)(((64.0/4)/217.0)*img.getWidth());
		StringBuilder sb = new StringBuilder();
		int[] choiceMap = new int[5];
		
		
		
		int pos = 0;
		for(int i=startX;i<=endX;i+=cellW) {

			int choice = 1;
			int[] selections = new int[5];


			for(int j=startY;j<=endY;j+=cellH) {


				int total= 0, rs = 0, gs = 0,bs = 0;
				for(int k=j;k<j+cellH;k++) {		
					for(int m=i;m<i+cellW;m++) {

						int rgb = img.getRGB(m,k);
						int r = (0x00ff0000 & rgb) >> 16;
						int g = (0x0000ff00 & rgb) >> 8;
						int b = (0x000000ff & rgb);
						rs+=r;
						gs+=g;
						bs+=b;
						total = total + 1;
					}
				}
				int average = (rs/total + gs/total + bs/total)/3;

				//map.put(Integer.toString(qsn) + "-" + Integer.toString(choice), average);

				if(choice<=5)
					selections[choice-1]  = average;
				choice++;
			}
			//allAn[pos] = selections;
			boolean selected = false;
			for(int x: selections) {
				if(x<210)
					selected = true;
			}
			if(selected) {
				ArrayList<Integer> ans = new ArrayList<>();
				for(int y: selections) {
					ans.add(y);
				}
				System.out.println(selections.length);
				choiceMap[pos] = ans.indexOf(Collections.min(ans));
				
			}
			else {
				choiceMap[pos] = -1;
			}
			pos++;
			if(pos>3)
				break;
		}

		
		int id = 0;
		for(int o=0;o<choiceMap.length-1;o++) {
			id+=Math.pow(10, choiceMap.length - 1 - o)*(choiceMap[o]+1);
		}
		//for(int[] a: allAn) {
			//System.out.println(Arrays.toString(a));
		//}
		System.out.println(Arrays.toString(choiceMap));
		return id;

		
		
	}
	
	public static Grade grade(String filePath, String key) throws IOException {
		Grade grade = new Grade();
		char[] keyChars = key.toCharArray();
		HashMap<Integer, Integer> studentAnswers = getAnswers(filePath,key.length());
		int correct = 0;
		for(int i=0; i<keyChars.length;i++) {
			if(Integer.parseInt(Character.toString(keyChars[i])) == studentAnswers.get(i+1))
				correct++;
			else
				grade.addToMissedList(i+1);
		}
		grade.setRawGrade(correct);
		return grade;
	}
	

	
	


}
