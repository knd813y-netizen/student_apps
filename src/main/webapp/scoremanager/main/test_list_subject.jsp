<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>

			<div class="mx-3 border rounded p-3 mb-3">

				<form action="TestListSubjectExecute.action" method="post" class="mb-0">
			
					<div class="d-grid align-items-center"
						style="grid-template-columns: 120px 110px 110px 230px 80px; column-gap: 36px;">
			
						<div class="d-flex align-items-center justify-content-center">
							<span>科目情報</span>
						</div>
			
						<div>
							<label class="form-label mb-1">入学年度</label>
							<select class="form-select" name="f1" style="width: 110px;">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}" <c:if test="${year == f1}">selected</c:if>>
										${year}
									</option>
								</c:forEach>
							</select>
						</div>
			
						<div>
							<label class="form-label mb-1">クラス</label>
							<select class="form-select" name="f2" style="width: 110px;">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num == f2}">selected</c:if>>
										${num}
									</option>
								</c:forEach>
							</select>
						</div>
			
						<div>
							<label class="form-label mb-1">科目</label>
							<select class="form-select" name="f3" style="width: 230px;">
								<option value="0">--------</option>
								<c:forEach var="sub" items="${subject_set}">
									<option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>
										${sub.name}
									</option>
								</c:forEach>
							</select>
						</div>
			
						<div class="d-flex align-items-center justify-content-center" style="padding-top: 10px;">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
			
					</div>
				</form>
			
				<hr class="my-3">
			
				<form action="TestListStudentExecute.action" method="post">
			
					<div class="d-grid align-items-center"
						style="grid-template-columns: 120px 260px 80px; column-gap: 36px;">
			
						<div class="d-flex align-items-center justify-content-center">
							<span>学生情報</span>
						</div>
			
						<div>
							<label class="form-label mb-1">学生番号</label>
							<input type="text" class="form-control" name="f4"
								placeholder="学生番号を入力してください" style="width: 260px;">
						</div>
			
						<div class="d-flex align-items-center justify-content-center" style="padding-top: 10px;">
							<button type="submit" class="btn btn-secondary">検索</button>
						</div>
			
					</div>
				</form>
			</div>

			<c:if test="${not empty subject}">
				<div class="mx-3 mb-2">科目：${subject.name}</div>
			</c:if>

			<c:if test="${not empty message}">
				<div class="mx-3">${message}</div>
			</c:if>

			<c:if test="${students.size() > 0}">
				<div class="mx-3" style="width: 86%;">
					<table class="table table-sm">
						<thead>
							<tr>
								<th style="width: 19%;">入学年度</th>
								<th style="width: 16%;">クラス</th>
								<th style="width: 20%;">学生番号</th>
								<th style="width: 20%;">氏名</th>
								<c:forEach var="num" items="${num_set}">
									<th class="text-center" style="width: 10%;">${num}回</th>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="student" items="${students}">
								<tr>
									<td>${student.entYear}</td>
									<td>${student.classNum}</td>
									<td>${student.studentNo}</td>
									<td>${student.studentName}</td>
									<c:forEach var="num" items="${num_set}">
										<td class="text-center">
											<c:choose>
											    <c:when test="${empty student.points[num]}">
											        -
											    </c:when>
											    <c:otherwise>
											        ${student.points[num]}
											    </c:otherwise>
											</c:choose>

										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</section>
	</c:param>
</c:import>
