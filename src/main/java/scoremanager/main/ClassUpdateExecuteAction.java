package scoremanager.main;

import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.*;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

	@Override
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		// ローカル変数
		String url = "";
		// セッション取得
		HttpSession session =req.getSession();
		// ログインユーザー取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// パラメータ取得
		String oldClassNum = req.getParameter("oldClassNum");
		String newClassNum = req.getParameter("newClassNum");
		// Dao
		StudentDao sDao = new StudentDao();
		sDao.updateClass(oldClassNum, newClassNum, teacher.getSchool());
		
		// リクエストへセット
		req.setAttribute("oldClassNum", oldClassNum);
		req.setAttribute("newClassNum", newClassNum);
		
		// クラス番号変更完了画面へ
		url = "class_update_done.jsp";
		req.getRequestDispatcher(url)
			.forward(req, res);
	}
}