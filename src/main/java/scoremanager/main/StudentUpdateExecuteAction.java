package scoremanager.main;

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

public class StudentUpdateExecuteAction extends Action {

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
		String isAttendStr = req.getParameter("is_attend");
		
		// 型変換
		int entYear = Integer.parseInt(entYearStr);
		boolean isAttend = isAttendStr != null;
		
		// 学生インスタンス生成
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());
		
		Map<String, String> errors = new HashMap<>();
		
		// 2026/05/20 変更理由: 設計書に合わせ、学生変更時に氏名が未入力の場合は保存せずエラーにする。
		if (name == null || name.trim().isEmpty()) {
			errors.put("name", "氏名を入力してください");
		}
		
		if (!errors.isEmpty()) {
			ClassNumDao cDao = new ClassNumDao();
			List<String> classNumSet = cDao.filter(teacher.getSchool());
			
			req.setAttribute("student", student);
			req.setAttribute("class_num_set", classNumSet);
			req.setAttribute("errors", errors);
			
			url = "student_update.jsp";
			req.getRequestDispatcher(url)
				.forward(req, res);
			return;
		}
		
		student.setName(name.trim());
		
		// 学生取得
		StudentDao sdao = new StudentDao();
		// DBに保存
		sdao.save(student);
		
		// 学生情報変更完了画面へ
		url = "student_update_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}
