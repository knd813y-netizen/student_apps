<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<%-- タイトル --%>
	<c:param name="title">
		得点管理システム
	</c:param>

	<%-- 追加スクリプトなし --%>
	<c:param name="scripts"></c:param>

	<%-- 画面本体 --%>
	<c:param name="content">
		<section class="me-4">

			<%-- 見出し --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

			<%-- 完了メッセージ --%>
			<div class="alert alert-success mx-3" role="alert">
				変更が完了しました
			</div>

			<%-- 学生一覧リンク --%>
			<div class="mx-3">
				<a href="StudentList.action">学生一覧</a>
			</div>

		</section>
	</c:param>
</c:import>