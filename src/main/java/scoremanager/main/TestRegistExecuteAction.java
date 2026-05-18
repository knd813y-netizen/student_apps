package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ----------------------------
		// 1. ローカル変数の宣言
		// ----------------------------
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		String numStr = "";

		int entYear = 0;
		int num = 0;

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();

		Map<String, String> errors = new HashMap<>();
		Map<String, String> pointErrors = new HashMap<>();

		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = null;
		List<Subject> subjectSet = null;

		// ----------------------------
		// 2. リクエストパラメータの取得
		// ----------------------------
		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		subjectCd = request.getParameter("f3");
		numStr = request.getParameter("f4");

		String[] studentNoArray = request.getParameterValues("student_no");
		String[] classNumArray = request.getParameterValues("class_num");
		String[] pointArray = request.getParameterValues("point");

		// ----------------------------
		// 3. DBからデータ取得
		// ----------------------------
		classNumSet = classNumDao.filter(teacher.getSchool());
		subjectSet = subjectDao.filter(teacher.getSchool());

		// ----------------------------
		// 4. ビジネスロジック
		// ----------------------------
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		if (entYearStr == null || entYearStr.equals("") || entYearStr.equals("0")
				|| classNum == null || classNum.equals("") || classNum.equals("0")
				|| subjectCd == null || subjectCd.equals("") || subjectCd.equals("0")
				|| numStr == null || numStr.equals("") || numStr.equals("0")
				|| studentNoArray == null
				|| classNumArray == null
				|| pointArray == null) {

			errors.put("f1", "入学年度とクラスと科目と回数を選択してください");

			request.setAttribute("ent_year_set", entYearSet);
			request.setAttribute("class_num_set", classNumSet);
			request.setAttribute("subject_set", subjectSet);

			request.setAttribute("f1", entYearStr);
			request.setAttribute("f2", classNum);
			request.setAttribute("f3", subjectCd);
			request.setAttribute("f4", numStr);

			request.setAttribute("errors", errors);
			request.setAttribute("search_error", errors.get("f1"));

			request.getRequestDispatcher("test_regist.jsp").forward(request, response);
			return;
		}

		entYear = Integer.parseInt(entYearStr);
		num = Integer.parseInt(numStr);

		Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

		List<Test> registList = new ArrayList<>();

		for (int i = 0; i < studentNoArray.length; i++) {

			String studentNo = studentNoArray[i];
			String rowClassNum = classNumArray[i];
			String pointStr = pointArray[i];

			int point = 0;
			boolean isPointOk = true;

			if (pointStr == null || pointStr.equals("")) {
				pointErrors.put(studentNo, "0～100の範囲で入力してください");
				errors.put(studentNo, "0～100の範囲で入力してください");
				isPointOk = false;

			} else {
				try {
					point = Integer.parseInt(pointStr);

					if (point < 0 || point > 100) {
						pointErrors.put(studentNo, "0～100の範囲で入力してください");
						errors.put(studentNo, "0～100の範囲で入力してください");
						isPointOk = false;
					}

				} catch (NumberFormatException e) {
					pointErrors.put(studentNo, "0～100の範囲で入力してください");
					errors.put(studentNo, "0～100の範囲で入力してください");
					isPointOk = false;
				}
			}

			if (isPointOk) {
				Student student = new Student();
				student.setNo(studentNo);
				student.setSchool(teacher.getSchool());

				Test test = new Test();
				test.setStudent(student);
				test.setClassNum(rowClassNum);
				test.setSubject(subject);
				test.setSchool(teacher.getSchool());
				test.setNo(num);
				test.setPoint(point);

				registList.add(test);
			}
		}

		if (pointErrors.size() > 0) {

			List<Test> tests = testDao.filter(entYear, classNum, subject, num, teacher.getSchool());

			// 入力エラー時、入力された値をできるだけ画面に戻す
			for (Test test : tests) {
				String studentNo = test.getStudent().getNo();

				for (int i = 0; i < studentNoArray.length; i++) {
					if (studentNo.equals(studentNoArray[i])) {
						try {
							if (pointArray[i] == null || pointArray[i].equals("")) {
								test.setPoint(-1);
							} else {
								test.setPoint(Integer.parseInt(pointArray[i]));
							}
						} catch (NumberFormatException e) {
							test.setPoint(-1);
						}
					}
				}
			}

			request.setAttribute("ent_year_set", entYearSet);
			request.setAttribute("class_num_set", classNumSet);
			request.setAttribute("subject_set", subjectSet);

			request.setAttribute("f1", entYearStr);
			request.setAttribute("f2", classNum);
			request.setAttribute("f3", subjectCd);
			request.setAttribute("f4", numStr);

			request.setAttribute("subject", subject);
			request.setAttribute("tests", tests);

			request.setAttribute("errors", errors);
			request.setAttribute("point_errors", pointErrors);

			request.getRequestDispatcher("test_regist.jsp").forward(request, response);
			return;
		}

		// ----------------------------
		// 5. DBへデータ保存
		// ----------------------------
		testDao.save(registList);

		// ----------------------------
		// 6. レスポンス値をセット
		// ----------------------------
		// 完了画面は固定メッセージのため、特になし

		// ----------------------------
		// 7. JSPへフォワード
		// ----------------------------
		request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
	}
}