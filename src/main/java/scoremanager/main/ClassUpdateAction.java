package scoremanager.main;

import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassUpdateAction extends Action {

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
		String classNum = req.getParameter("class_num");
		// Dao
		ClassNumDao cDao = new ClassNumDao();
		// クラス一覧取得
		List<String> classNums = cDao.filter(teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("oldClassNum", classNum);
		req.setAttribute("classNums", classNums);
		
		// クラス番号変更画面へ
		url = "class_update.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}