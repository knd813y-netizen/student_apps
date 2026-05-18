package scoremanager.main;

import bean.Teacher;
import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.*;
import tool.Action;

public class StudentClassUpdateExecuteAction
	extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ロカール変数
		String url = "";
		// セッション取得
		HttpSession session = req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメーター取得
		String no = req.getParameter("no");
		String newClassNum = req.getParameter("newClassNum");
		
		// 学生取得
		StudentDao sDao = new StudentDao();
		sDao.updateStudentClass(no, newClassNum, teacher.getSchool());
		Student student = sDao.get(no);
		req.setAttribute("student", student);
		req.setAttribute("newClassNum", newClassNum);
		
		// 学生クラス番号変更完了画面へ
		url ="student_class_update_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}