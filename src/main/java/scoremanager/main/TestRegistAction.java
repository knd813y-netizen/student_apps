package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class TestRegistAction extends Action {

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

		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = null;
		List<Subject> subjectSet = null;
		List<Test> tests = null;

		// ----------------------------
		// 2. リクエストパラメータの取得
		// ----------------------------
		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		subjectCd = request.getParameter("f3");
		numStr = request.getParameter("f4");

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

		boolean isSearch =
				entYearStr != null
				|| classNum != null
				|| subjectCd != null
				|| numStr != null;

		if (isSearch) {

			if (entYearStr == null || entYearStr.equals("") || entYearStr.equals("0")
					|| classNum == null || classNum.equals("") || classNum.equals("0")
					|| subjectCd == null || subjectCd.equals("") || subjectCd.equals("0")
					|| numStr == null || numStr.equals("") || numStr.equals("0")) {

				errors.put("f1", "入学年度とクラスと科目と回数を選択してください");

			} else {

				entYear = Integer.parseInt(entYearStr);
				num = Integer.parseInt(numStr);

				Subject subject = subjectDao.get(subjectCd, teacher.getSchool());
				tests = testDao.filter(entYear, classNum, subject, num, teacher.getSchool());

				request.setAttribute("subject", subject);
				request.setAttribute("tests", tests);

				if (tests.size() == 0) {
					request.setAttribute("message", "学生情報が存在しませんでした");
				}
			}
		}

		// ----------------------------
		// 5. DBへデータ保存
		// ----------------------------
		// なし

		// ----------------------------
		// 6. レスポンス値をセット
		// ----------------------------
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classNumSet);
		request.setAttribute("subject_set", subjectSet);

		request.setAttribute("f1", entYearStr);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);
		request.setAttribute("f4", numStr);

		request.setAttribute("errors", errors);

		// 前回JSPの属性名にも対応
		request.setAttribute("search_error", errors.get("f1"));

		// ----------------------------
		// 7. JSPへフォワード
		// ----------------------------
		request.getRequestDispatcher("test_regist.jsp").forward(request, response);
	}
}