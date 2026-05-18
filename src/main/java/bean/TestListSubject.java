package bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer, Integer> points = new LinkedHashMap<>();
	
	public int getEntYear() {
		return entYear;
	}
	
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	
	public String getStudentNo() {
		return studentNo;
	}
	
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getClassNum() {
		return classNum;
	}
	
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}
	
	/**
	 * 指定回数の点数をセット
	 */
	public void putPoint(int num, int point) {
		this.points.put(num, point);
	}

	/**
	 * 指定回数の点数を取得
	 */
	public Integer getPoint(int num) {
		return this.points.get(num);
	}
}
