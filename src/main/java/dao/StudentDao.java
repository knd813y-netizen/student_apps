package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
private String baseSql = "select * from student where school_cd=? ";
	
	public Student get(String no) throws Exception {
		//学生インスタンスを初期化
		Student student = new Student();
		//データーベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		
		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where no=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, no);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();
			
			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();
			
			if (rSet.next()) {
				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
				
			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスのnullをセット
				student = null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
			
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			
			}
			
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
		}
 
		return student;
	}
	
	private List<Student> postFilter(ResultSet resultSet, School school) throws Exception {
		List<Student> list = new ArrayList<>();
		try {
			//リザルトセットを全件操作
			while (resultSet.next()) {
				
				//学生インスタンスを初期化
				Student student = new Student();
				
				//学生インスタンスに検索結果をセット
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(school);
				
				//リストに追加
				list.add(student);
			}
		
		} catch (SQLException | NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
	
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "and ent_year=? and class_num=?";
		//SQL文のソート
		String order = "order by no asc";
		
		//SQL文の在学フラグ条件
		String conditionIsAttend = "";
		//在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		
		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
			
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				
				try {
					statement.close();
					
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
			
			//コネクションを閉じる
			if (connection != null) {
				
				try {
					connection.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
		}
		
		return list;
	}
	
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "and ent_year=?";
		//SQL文のソート
		String order = "order by no asc";
		
		//SQL文の在学フラグ条件
		String conditionIsAttend = "";
		//在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		
		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
			
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				
				try {
					statement.close();
					
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
			
			//コネクションを閉じる
			if (connection != null) {
				
				try {
					connection.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
		}
		
		return list;
	}
	
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文のソート
		String order = "order by no asc";
		
		//SQL文の在学フラグ条件
		String conditionIsAttend = "";
		//在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		
		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
			
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				
				try {
					statement.close();
					
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
			
			//コネクションを閉じる
			if (connection != null) {
				
				try {
					connection.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
		}
		
		return list;
	}
	
	public boolean save(Student student) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数
		int count = 0;
		
		try {
			//データベースから学生を取得
			Student old = get(student.getNo());
			if (old == null) {
				//学生が存在しなかった場合
				//プリペアードステートメントんいINSERT文をセット
				statement = connection.prepareStatement(
						"insert into student(no, name, ent_year, class_num, is_attend, school_cd) "
						+ "values(?, ?, ?, ?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
				
			} else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update student set name=?, ent_year=?, class_num=?, is_attend=? where no=? and school_cd=?");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
				statement.setString(6, student.getSchool().getCd());
			}
			//プリペアードステートメントを実行
			count = statement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
			
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
					
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
			
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
		}
			
		if (count > 0) {
			//実行件数が1以上ある場合
			return true;
			
		} else {
			//実行件数が0件の場合
			return false;
		}
	}

	public Student get(String no, String schoolCd) throws Exception {

	    Student student = null;
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        statement = connection.prepareStatement(
	            "select * from student where no=? and school_cd=?"
	        );
	        statement.setString(1, no);
	        statement.setString(2, schoolCd);

	        ResultSet rs = statement.executeQuery();

	        if (rs.next()) {
	            student = new Student();
	            student.setNo(rs.getString("no"));
	            student.setName(rs.getString("name"));
	            student.setEntYear(rs.getInt("ent_year"));
	            student.setClassNum(rs.getString("class_num"));
	            student.setAttend(rs.getBoolean("is_attend"));

	            SchoolDao schoolDao = new SchoolDao();
	            student.setSchool(schoolDao.get(schoolCd));
	        }

	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return student;
	}
	public List<Integer> getEntYearSet(School school) throws Exception {

	    List<Integer> list = new ArrayList<>();

	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rs = null;

	    try {
	        statement = connection.prepareStatement(
	            "SELECT DISTINCT ent_year FROM student WHERE school_cd=? ORDER BY ent_year"
	        );
	        statement.setString(1, school.getCd());

	        rs = statement.executeQuery();

	        while (rs.next()) {
	            list.add(rs.getInt("ent_year"));
	        }

	    } finally {
	        if (rs != null) rs.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return list;
	}
	public boolean existsByClassNum(String class_num, School school) throws Exception {

		boolean result = false;
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"select count(*) from student "
				+ "where class_num = ? and school_cd = ?"
			);
			// 値をバインド(bind)
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());
			// 実行
			ResultSet rSet = statement.executeQuery();
			
			if (rSet.next()) {
				result = rSet.getInt(1) > 0;
			}
		} finally {
			// PreparedStatementを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			// Connectionを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return result;
	}
	
	// クラス丸変更機能(update)
	public boolean updateClass(String oldClassNum, String newClassNum, School school) throws Exception {
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"update student " +
				"set class_num = ? " +
				"where class_num = ? " +
				"and school_cd = ?"
			);
			// 新しいクラス番号と現在のクラス番号と学校コードをバインド(bind)
			statement.setString(1,newClassNum);
			statement.setString(2,oldClassNum);
			statement.setString(3,school.getCd());
			// 実行
			count = statement.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			// PreparedStatementを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			// Connectionを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return count > 0;
	}
	
	// 学生のクラス変更
	public boolean updateStudentClass(String no, String classNum, School school) throws Exception {
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"update student " +
				"set class_num = ? " +
				"where no = ? " +
				"and school_cd = ?"
			);
			// 値をバインド(bind)
			statement.setString(1, classNum);
			statement.setString(2, no);
			statement.setString(3, school.getCd());
			// 実行
			count = statement.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			// PreparedStatementを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			// Connectionを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return count > 0;
	}
	
	public List<Student> filterByClassNum(String classNum, School school) throws Exception {
		// List初期化
		List<Student> list = new ArrayList<>();
		// Connection確立
		Connection connection = getConnection();
		// PreparedStatement
		PreparedStatement statement = null;
		// ResultSet
		ResultSet rSet = null;
		
		try {
			// SQL文をセット
			statement = connection.prepareStatement(
				"select * from student "
				+ "where class_num = ? "
				+ "and school_cd = ? "
				+ "order by no"
			);
			// クラス番号と学校コードをバインド(bind)
			statement.setString(1, classNum);
			statement.setString(2, school.getCd());
			// 実行
			rSet = statement.executeQuery();
			
			// ResultSet全件走査
			while (rSet.next()) {
				// Studentを初期化
				Student student = new Student();
				// 値をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);
				// Listに追加
				list.add(student);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			// ResultSetを閉じる
			if (rSet != null) {
				try {
					rSet.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			// PreparedStatementを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			
			// Connectionを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return list;
	}


}

