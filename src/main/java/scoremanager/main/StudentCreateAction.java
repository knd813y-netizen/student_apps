package scoremanager.main;

import java.util.List;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ★ FIX: get session from request
        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();

        ClassNumDao dao = new ClassNumDao();
        List<ClassNum> classList = dao.findBySchool(schoolCd);

        req.setAttribute("classList", classList);

        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}
