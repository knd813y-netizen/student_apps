<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true"
    import="java.util.*,java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>エラー詳細</title>
<style>
    body {
        font-family: monospace;
        margin: 20px;
        line-height: 1.5;
    }
    h1, h2 {
        margin-top: 24px;
    }
    table {
        border-collapse: collapse;
        width: 100%;
        margin-top: 8px;
    }
    th, td {
        border: 1px solid #999;
        padding: 8px;
        text-align: left;
        vertical-align: top;
    }
    pre {
        background: #f4f4f4;
        border: 1px solid #ccc;
        padding: 12px;
        overflow-x: auto;
        white-space: pre-wrap;
        word-break: break-word;
    }
    .null {
        color: #888;
    }
</style>
</head>
<body>

<%!
    private String h(Object obj) {
        if (obj == null) return "<span class='null'>null</span>";
        String s = String.valueOf(obj);
        s = s.replace("&", "&amp;");
        s = s.replace("<", "&lt;");
        s = s.replace(">", "&gt;");
        s = s.replace("\"", "&quot;");
        return s;
    }

    private String stackTraceToString(Throwable t) {
        if (t == null) return "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
%>

<%
    Throwable ex = null;

    if (exception != null) {
        ex = exception;
    }
    if (ex == null) {
        ex = (Throwable) request.getAttribute("error");
    }
    if (ex == null) {
        ex = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
    }
    if (ex == null) {
        ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
    }

    Object statusCode = request.getAttribute("jakarta.servlet.error.status_code");
    if (statusCode == null) statusCode = request.getAttribute("javax.servlet.error.status_code");

    Object message = request.getAttribute("jakarta.servlet.error.message");
    if (message == null) message = request.getAttribute("javax.servlet.error.message");

    Object requestUri = request.getAttribute("jakarta.servlet.error.request_uri");
    if (requestUri == null) requestUri = request.getAttribute("javax.servlet.error.request_uri");

    Object servletName = request.getAttribute("jakarta.servlet.error.servlet_name");
    if (servletName == null) servletName = request.getAttribute("javax.servlet.error.servlet_name");

    Object errorType = request.getAttribute("jakarta.servlet.error.exception_type");
    if (errorType == null) errorType = request.getAttribute("javax.servlet.error.exception_type");

    String queryString = request.getQueryString();
    String method = request.getMethod();
    String contextPath = request.getContextPath();
    String servletPath = request.getServletPath();
    String requestURL = request.getRequestURL().toString();
%>

<h1>エラー詳細画面</h1>

<h2>基本情報</h2>
<table>
    <tr><th>HTTPメソッド</th><td><%= h(method) %></td></tr>
    <tr><th>リクエストURL</th><td><%= h(requestURL) %></td></tr>
    <tr><th>コンテキストパス</th><td><%= h(contextPath) %></td></tr>
    <tr><th>サーブレットパス</th><td><%= h(servletPath) %></td></tr>
    <tr><th>クエリ文字列</th><td><%= h(queryString) %></td></tr>
    <tr><th>エラー時リクエストURI</th><td><%= h(requestUri) %></td></tr>
    <tr><th>サーブレット名</th><td><%= h(servletName) %></td></tr>
    <tr><th>ステータスコード</th><td><%= h(statusCode) %></td></tr>
    <tr><th>メッセージ</th><td><%= h(message) %></td></tr>
    <tr><th>例外クラス</th><td><%= h(errorType != null ? errorType : (ex != null ? ex.getClass().getName() : null)) %></td></tr>
</table>

<h2>例外オブジェクト</h2>
<%
    if (ex != null) {
%>
<table>
    <tr><th>例外クラス名</th><td><%= h(ex.getClass().getName()) %></td></tr>
    <tr><th>例外メッセージ</th><td><%= h(ex.getMessage()) %></td></tr>
    <tr><th>cause</th><td><%= h(ex.getCause()) %></td></tr>
</table>

<h2>スタックトレース</h2>
<pre><%= h(stackTraceToString(ex)) %></pre>
<%
    } else {
%>
<p>例外オブジェクトは取得できませんでした。</p>
<%
    }
%>

<h2>リクエストパラメータ</h2>
<table>
    <tr>
        <th>パラメータ名</th>
        <th>値</th>
    </tr>
<%
    Enumeration<String> paramNames = request.getParameterNames();
    boolean hasParam = false;
    while (paramNames.hasMoreElements()) {
        hasParam = true;
        String name = paramNames.nextElement();
        String[] values = request.getParameterValues(name);
%>
    <tr>
        <td><%= h(name) %></td>
        <td><%= h(Arrays.toString(values)) %></td>
    </tr>
<%
    }
    if (!hasParam) {
%>
    <tr><td colspan="2">パラメータはありません。</td></tr>
<%
    }
%>
</table>

<h2>requestスコープ属性</h2>
<table>
    <tr>
        <th>属性名</th>
        <th>値</th>
        <th>型</th>
    </tr>
<%
    Enumeration<String> reqAttrs = request.getAttributeNames();
    boolean hasReqAttr = false;
    while (reqAttrs.hasMoreElements()) {
        hasReqAttr = true;
        String name = reqAttrs.nextElement();
        Object val = request.getAttribute(name);
%>
    <tr>
        <td><%= h(name) %></td>
        <td><%= h(val) %></td>
        <td><%= h(val != null ? val.getClass().getName() : null) %></td>
    </tr>
<%
    }
    if (!hasReqAttr) {
%>
    <tr><td colspan="3">request属性はありません。</td></tr>
<%
    }
%>
</table>

<h2>sessionスコープ属性</h2>
<table>
    <tr>
        <th>属性名</th>
        <th>値</th>
        <th>型</th>
    </tr>
<%
    HttpSession sess = request.getSession(false);
    if (sess != null) {
        Enumeration<String> sessAttrs = sess.getAttributeNames();
        boolean hasSessAttr = false;
        while (sessAttrs.hasMoreElements()) {
            hasSessAttr = true;
            String name = sessAttrs.nextElement();
            Object val = sess.getAttribute(name);
%>
    <tr>
        <td><%= h(name) %></td>
        <td><%= h(val) %></td>
        <td><%= h(val != null ? val.getClass().getName() : null) %></td>
    </tr>
<%
        }
        if (!hasSessAttr) {
%>
    <tr><td colspan="3">session属性はありません。</td></tr>
<%
        }
    } else {
%>
    <tr><td colspan="3">session自体がありません。</td></tr>
<%
    }
%>
</table>

<h2>補助確認</h2>
<table>
    <tr><th>session user</th><td><%= h(sess != null ? sess.getAttribute("user") : null) %></td></tr>
    <tr><th>session teacher</th><td><%= h(sess != null ? sess.getAttribute("teacher") : null) %></td></tr>
    <tr><th>request user</th><td><%= h(request.getAttribute("user")) %></td></tr>
    <tr><th>request teacher</th><td><%= h(request.getAttribute("teacher")) %></td></tr>
</table>


<h1>エラー詳細画面</h1>

<table>
	<tr>
		<th>例外クラス</th>
		<td><%= request.getAttribute("errorType") %></td>
	</tr>
	<tr>
		<th>メッセージ</th>
		<td><%= request.getAttribute("errorMessage") %></td>
	</tr>
</table>

<h2>スタックトレース</h2>
<pre><%= request.getAttribute("errorStackTrace") %></pre>

</body>
</html>