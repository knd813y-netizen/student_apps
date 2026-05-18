<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">学生新規登録</c:param>

    <c:param name="scripts"></c:param>

<c:param name="content">
    <section class="me-4">
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
            学生新規登録
        </h2>

        <form action="StudentCreateExecute.action" method="post" class="w-75">

            <div class="mb-3">
                <label class="form-label">学籍番号</label>
                <input type="text" name="no" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">氏名</label>
                <input type="text" name="name" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">入学年度</label>
                <select name="ent_year" class="form-select">
                    <%
                        int year = java.time.LocalDate.now().getYear();
                        for (int y = year - 10; y <= year + 1; y++) {
                    %>
                        <option value="<%= y %>"><%= y %></option>
                    <% } %>
                </select>
            </div>

			<div class="mb-3">
			    <label class="form-label">クラス番号</label>
				<select name="class_num" class="form-select" required>
				    <c:forEach var="c" items="${classList}">
				        <option value="${c.class_num}">${c.class_num}</option>
				    </c:forEach>
				</select>
			</div>
			
            <div class="form-check mb-4">
                <input type="checkbox" name="attend" value="1" class="form-check-input">
                <label class="form-check-label">在学中</label>
            </div>

            <button type="submit" class="btn btn-primary">登録する</button>
        </form>
    </section>
</c:param>

</c:import>
