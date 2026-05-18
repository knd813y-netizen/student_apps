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

			<form action="StudentUpdateExecute.action" method="post">
				<div class="mx-3">

					<%-- 入学年度（表示のみ） --%>
					<div class="mb-3">
						<label class="form-label">入学年度</label>
						<div>${ent_year}</div>

						<%-- 更新処理へ渡すため hidden で保持 --%>
						<input type="hidden" name="ent_year" value="${ent_year}">
					</div>

					<%-- 学生番号（表示のみ） --%>
					<div class="mb-3">
						<label class="form-label">学生番号</label>
						<div>${no}</div>

						<%-- 更新処理へ渡すため hidden で保持 --%>
						<input type="hidden" name="no" value="${no}">
					</div>

					<%-- 氏名（変更可能） --%>
					<div class="mb-3">
						<label class="form-label" for="student-update-name">氏名</label>
						<input
							type="text"
							class="form-control"
							id="student-update-name"
							name="name"
							value="${name}"
							placeholder="氏名を入力してください"
							required>
					</div>

					<%-- クラス（変更可能） --%>
					<div class="mb-3">
						<label class="form-label" for="student-update-class-num">クラス</label>
						<select class="form-select" id="student-update-class-num" name="class_num">
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == class_num}">selected</c:if>>
									${num}
								</option>
							</c:forEach>
						</select>
					</div>

					<%-- 在学中チェックボックス --%>
					<div class="form-check mb-3">
						<input
							class="form-check-input"
							type="checkbox"
							id="student-update-is-attend"
							name="is_attend"
							value="true"
							<c:if test="${is_attend}">checked</c:if>>
						<label class="form-check-label" for="student-update-is-attend">
							在学中
						</label>
					</div>

					<%-- 変更ボタン --%>
					<div class="mb-3">
						<input class="btn btn-primary" type="submit" value="変更">
					</div>

					<%-- 戻るリンク --%>
					<div>
						<a href="StudentList.action">戻る</a>
					</div>

				</div>
			</form>

		</section>
	</c:param>
</c:import>