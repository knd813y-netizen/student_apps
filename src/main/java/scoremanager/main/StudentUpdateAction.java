package scoremanager.main;

import java.util.List;

import bean.ClassNum;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();

        // Student number from URL
        String no = req.getParameter("no");

        // Load student
        StudentDao sdao = new StudentDao();
        Student student = sdao.get(no, schoolCd);

        // Load class list
        ClassNumDao cdao = new ClassNumDao();
        List<ClassNum> classList = cdao.findBySchool(schoolCd);

        req.setAttribute("student", student);
        req.setAttribute("classList", classList);

        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
