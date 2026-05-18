<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">学生登録完了</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生登録完了
            </h2>

            <p class="text-center py-1"
			   style="background-color:#66CC99; margin-bottom:130px">
				登録が完了しました
			</p>

            <a href="StudentList.action" class="btn btn-secondary mt-3">
                学生一覧へ戻る
            </a>
        </section>
    </c:param>
</c:import>
