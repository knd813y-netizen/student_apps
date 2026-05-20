package bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	// 2026/05/19 変更理由: getPoint(key: int): String に合わせ、成績参照画面で扱う点数を文字列で保持する。
	// TestListSubjectDao からは int で渡されるため、putPoint で String に変換する。
	private Map<Integer, Integer> points = new LinkedHashMap<>();
	
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
	public Map<Integer, Integer> getPoints() {
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
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	/**
	 * 指定回数の点数をセット
	 * 2026/05/19 変更理由: DBから取得した数値の点数を、表示用のStringとして保持するため。
	 */
	public void putPoint(int key, int value) {
		this.points.put(key, value);
	}

	/**
	 * 指定回数の点数を取得
	 * 2026/05/19 変更理由: getPoint(key: int): String と一致させるため。
	 */
	public Integer getPoint(int key) {
		return this.points.get(key);
	}
}
