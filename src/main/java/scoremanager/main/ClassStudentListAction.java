package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassStudentListAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		// ローカル変数
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");

		// パラメータ取得
		String class_num = req.getParameter("class_num");
		// DAO
		StudentDao sDao = new StudentDao();

		// 学生一覧取得
		List<Student> students = sDao.filterByClassNum(class_num, teacher.getSchool());

		// リクエストへセット
		req.setAttribute("students", students);
		req.setAttribute("class_num", class_num);

		// 選んだクラスに在学中の学生一覧画面へ
		url = "class_student_list.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}