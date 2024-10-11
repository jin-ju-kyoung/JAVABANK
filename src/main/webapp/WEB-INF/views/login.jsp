<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="js/script.js"></script>
    <title>javabank_Login</title>
</head>
<body>
    <section id="login">
        <form name="f" action="login.do" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="logo_box">
                <p>java<em>bank</em></p>
            </div>
            <c:if test="${param.error eq 'true'}">
			    <div class="error">로그인 실패: 아이디나 비밀번호가 잘못되었습니다.</div>
			</c:if>
            <div class="input_box">
            
                <label>
                    <input type="text" placeholder="ID" name="userid" required>
                </label>
                <label>
                    <input type="password" placeholder="PASSWORD" name="pw" required>
                </label>
                <button class="login_btn" type="submit">로그인</button>
            </div>

            <div class="save_box">
            	<label>
                	<c:if test="${empty cookie['saveId']}">
                		아이디 저장 <input type="checkbox" name="saveId">  
                	</c:if>
                	
                	<c:if test="${not empty cookie['saveId']}">
                		아이디 저장 <input type="checkbox" name="saveId" value="on" checked>  
                    </c:if>
                </label>
                
            </div>

            <div class="join_box">
                <a href="javascript:;">회원가입</a>
                <ul class="find_box">
                    <li><a href="javascript:;">아이디찾기</a></li>
                    <li><a href="javascript:;">비밀번호찾기</a></li>
                </ul>
            </div>
        </form>
    </section>
</body>
</html>