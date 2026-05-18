package scoremanager.main;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数の宣言
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメーター取得
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		// エラーはHashMapに格納
		Map<String, String> errors = new HashMap<>();
		
		// 入学年度チェック
		int entYear = 0;
		
		if (entYearStr == null || entYearStr.equals("0")) {
			errors.put(
				"ent_year",
				"入学年度を選択してください"
			);
			
		} else {
			// 型変換
			entYear = Integer.parseInt(entYearStr);
		}
		
		// 学生番号重複チェック
		StudentDao sDao = new StudentDao();
		if (no != null &&! no.isEmpty() && sDao.existsByNo(no)) {
			errors.put(
				"no",
				"学生番号が重複しています"
			);
		}
		
		// エラーがある場合
		if (errors.size() > 0) {
			
			// 現在年取得
			LocalDate today = LocalDate.now();
			int year = today.getYear();
			
			// 入学年度一覧
			List<Integer> entYearSet = new ArrayList<>();
			
			for (int i = year - 10; i <= year; i++) {
				entYearSet.add(i);
			}
			
			// クラス一覧
			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());
			
			// リクエストへセット
			req.setAttribute("errors",errors);
			req.setAttribute("ent_year_set",entYearSet);
			req.setAttribute("class_num_set",classNumSet);
			
			// 入力値保持
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("ent_year",entYearStr);
			req.setAttribute("class_num",classNum);
			
			// 学生情報登録画面へ
			url = "student_create.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			return;
		}
		
		// 学生生成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(true);
		student.setSchool(teacher.getSchool());
		
		// DBに保存
		sDao.save(student);
		
		// 学生情報登録完了画面へ
		url = "student_create_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}
