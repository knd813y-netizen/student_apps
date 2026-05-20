package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String studentNo = "";

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();
		TestListStudentDao testListStudentDao = new TestListStudentDao();

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

		studentNo = request.getParameter("f4");

		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		if (studentNo == null || studentNo.trim().equals("")) {
			request.setAttribute("student_error", "このフィールドを入力してください。");
			request.setAttribute("ent_year_set", entYearSet);
			request.setAttribute("class_num_set", classNumSet);
			request.setAttribute("subject_set", subjectSet);
			request.getRequestDispatcher("test_list.jsp").forward(request, response);
			return;
		}

		Student student = studentDao.get(studentNo);
		List<TestListStudent> scores = new ArrayList<>();

		if (student != null) {
			scores = testListStudentDao.filter(student);
		}

		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classNumSet);
		request.setAttribute("subject_set", subjectSet);

		request.setAttribute("f4", studentNo);
		request.setAttribute("student", student);
		request.setAttribute("scores", scores);

		if (student == null || scores.size() == 0) {
			request.setAttribute("message", "成績情報が存在しませんでした");
		}

		request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
	}
}