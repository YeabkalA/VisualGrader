import java.util.ArrayList;

public class Grade{
		private int rawGrade;
		private String letterGrade;
		private ArrayList<Integer> questionsMissed = new ArrayList();

		public void setRawGrade(int rawGrade) {
			this.rawGrade = rawGrade;
		}

		public int getRawGrade(){
			return rawGrade;
		}

		public void setLetterGrade(String letterGrade) {
			this.letterGrade = letterGrade;
		}

		public String getLetterGrade(){
			return letterGrade;
		}

		public void addToMissedList(int q) {
			this.questionsMissed.add(q);
		}
		
		public ArrayList<Integer> getMissedList(){
			return this.questionsMissed;
		}
		
		public Grade() {
			
		}


	}