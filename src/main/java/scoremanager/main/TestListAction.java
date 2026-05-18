package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ----------------------------
		// 1. ローカル変数の宣言
		// ----------------------------
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		List<Integer> entYearSet = new ArrayList<>();

		// ----------------------------
		// 2. リクエストパラメータの取得
		// ----------------------------
		// 初期表示なのでなし

		// ----------------------------
		// 3. DBからデータ取得
		// ----------------------------
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		var subjectSet = subjectDao.filter(teacher.getSchool());

		// ----------------------------
		// 4. ビジネスロジック
		// ----------------------------
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
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

		// ----------------------------
		// 7. JSPへフォワード
		// ----------------------------
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}