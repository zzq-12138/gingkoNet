<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Info Page</title>
  <script>
    function submitForm() {
      document.getElementById('loginForm').submit();
    }
  </script>
</head>
<body>
<h1>Information</h1>
<p>${infoMsg}</p>
<!-- 移除隐藏字段，因为用户名和密码已存储在会话中 -->
<form id="loginForm" action="/schedule_xss/file/list" method="post">
</form>
<a href="#" onclick="submitForm()">点此继续</a>
</body>
</html>