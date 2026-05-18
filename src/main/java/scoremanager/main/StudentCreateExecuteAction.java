package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // ① リクエストパラメータ取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String attendStr = req.getParameter("attend");  // チェックボックス

        // ② 型変換
        int entYear = Integer.parseInt(entYearStr);
        boolean attend = (attendStr != null);  // チェックされていれば true

        // ③ Student オブジェクトへ詰める
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(attend);
        student.setSchool(school);

        // ④ DB 登録
        StudentDao dao = new StudentDao();
        dao.save(student);

        // ⑤ 完了画面へフォワード
        req.getRequestDispatcher("student_create_done.jsp")
           .forward(req, res);
    }
}
