package bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	// 2026/05/19 変更理由: クラス図の points: Map<Integer,Integer> に合わせるため、点数は数値で保持する。
	// getPoint ではクラス図の戻り値 String に合わせて、取得時だけ文字列へ変換する。
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
	 * 2026/05/19 変更理由: クラス図の points: Map<Integer,Integer> に合わせ、点数はIntegerで保持するため。
	 */
	public void putPoint(int key, int value) {
		this.points.put(key, value);
	}

	/**
	 * 指定回数の点数を取得
	 * 2026/05/19 変更理由: クラス図の getPoint(key: int): String と一致させるため。
	 */
	public String getPoint(int key) {
		Integer point = this.points.get(key);
		return point == null ? null : String.valueOf(point);
	}
}
