package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";

		int entYear = 0;

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		TestListSubjectDao testListSubjectDao = new TestListSubjectDao();

		LocalDate today = LocalDate.now();
		int year = today.getYear();

		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		subjectCd = request.getParameter("f3");

		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// 検索条件不足
		if (entYearStr == null || entYearStr.equals("") || entYearStr.equals("0")
				|| classNum == null || classNum.equals("") || classNum.equals("0")
				|| subjectCd == null || subjectCd.equals("") || subjectCd.equals("0")) {

			request.setAttribute("subject_error", "入学年度とクラスと科目を選択してください");
			request.setAttribute("ent_year_set", entYearSet);
			request.setAttribute("class_num_set", classNumSet);
			request.setAttribute("subject_set", subjectSet);

			request.setAttribute("f1", entYearStr);
			request.setAttribute("f2", classNum);
			request.setAttribute("f3", subjectCd);

			request.getRequestDispatcher("test_list.jsp").forward(request, response);
			return;
		}

		entYear = Integer.parseInt(entYearStr);

		Subject subject = subjectDao.get(subjectCd, teacher.getSchool());
		List<TestListSubject> students = testListSubjectDao.filter(entYear, classNum, subject, teacher.getSchool());

		// 可変列用に回数一覧を作成
		Set<Integer> numSet = new TreeSet<>();
		for (TestListSubject student : students) {
			numSet.addAll(student.getPoints().keySet());
		}

		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", classNumSet);
		request.setAttribute("subject_set", subjectSet);

		request.setAttribute("f1", entYear);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);

		request.setAttribute("subject", subject);
		request.setAttribute("students", students);
		request.setAttribute("num_set", numSet);

		if (students.size() == 0) {
			request.setAttribute("message", "学生情報が存在しませんでした");
		}

		request.getRequestDispatcher("test_list_subject.jsp").forward(request, response);
	}
}