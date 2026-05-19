package bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer, String> points = new LinkedHashMap<>();
	
// ゲッター(Getter)とセッター(Setter)の設置
	public int getEntYear() {
		return entYear;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public String getClassNum() {
		return classNum;
	}
	public Map<Integer, String> getPoints() {
		return points;
	}

	
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public void setPoints(Map<Integer, String> points) {
		this.points = points;
	}

	/**
	 * 指定回数の点数をセット
	 */
	public void putPoint(int key, int value) {
		this.points.put(key, String.valueOf(value));
	}

	/**
	 * 指定回数の点数を取得
	 */
	public String getPoint(int key) {
		return this.points.get(key);
	}
}
