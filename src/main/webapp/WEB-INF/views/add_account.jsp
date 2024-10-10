<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index_top.jsp"%>
    <!-- s: content -->
    <section id="add_account" class="content add_bank">
        <p>입출금통장 개설</p>
        <div class="txt_box bg_yellow">
            <p>java<em>bank</em></p>
            <p>${loginId}님의 통장</p>
        </div>
        <form name="f" action="addAccountOk.do" method="post" onsubmit="return validatePassword()">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div>
                <p>통장 비밀번호 설정</p>
                <div class="passwd_box">
                    <label>
                        <input type="password" class="password" name="depositPw" placeholder="비밀번호 4자리 입력" maxlength="4" required oninput="checkPasswords()">
                    </label>
                    <label>
                        <input type="password" class="password" placeholder="비밀번호확인 4자리 입력" maxlength="4" required oninput="checkPasswords()">
                    </label>
                </div>
            <div class="error-message" style="color: red;"></div>
            </div>

            <div>
                <p>금융거래 1일 이체한도</p>
                <div class="transferLimit">
                    <label>
                        <input type="text" name="transferLimit" class="transferLimit" oninput="formatCurrency(this)" value="" placeholder="금액을 입력하세요(최대 10,000,000원)">
                    </label>
                </div>
            </div>

            <div class="btn_box">
                <button type="submit">개설하기</button>
            </div>

        </form>
    </section>
    <!-- e: content -->
<%@ include file="index_footer.jsp"%>


	<script>
	
	// 실시간으로 비밀번호를 확인하는 함수
    function checkPasswords() {
        var passwords = document.getElementsByClassName("password");
        var password1 = passwords[0].value;
        var password2 = passwords[1].value;
        var errorMessage = document.getElementsByClassName("error-message")[0];

        // 오류 메시지를 초기화
        errorMessage.textContent = "";
        
        if (password1.length !== 4) {
            errorMessage.textContent = "비밀번호는 4자리여야 합니다.";
            return;
        }

        // 두 비밀번호가 일치하는지 확인
        if (password1 !== password2) {
            errorMessage.textContent = "비밀번호가 일치하지 않습니다. 다시 확인해주세요.";
            return;
        }

        // 모든 조건이 맞으면 오류 메시지를 비워둠
        errorMessage.textContent = "";
    }

		
 // 폼 제출 시 비밀번호를 최종 확인하는 함수
    function validatePassword() {
        var passwords = document.getElementsByClassName("password");
        var password1 = passwords[0].value;
        var password2 = passwords[1].value;
        var errorMessage = document.getElementsByClassName("error-message")[0];

        // 비밀번호가 4자리인지 확인
        if (password1.length !== 4) {
            errorMessage.textContent = "비밀번호는 4자리여야 합니다.";
            return false; // 폼 제출 중지
        }

        // 두 비밀번호가 일치하는지 확인
        if (password1 !== password2) {
            errorMessage.textContent = "비밀번호가 일치하지 않습니다. 다시 확인해주세요.";
            return false; // 폼 제출 중지
        }
        
     // 이체한도 확인 로직
        let transferLimitField = document.getElementById("transferLimit");
        let rawValue = transferLimitField.value.replace(/,/g, ''); // 쉼표 제거 후 숫자만 남김

        // 이체 한도 값이 유효한지 확인
        if (isNaN(rawValue) || rawValue === '') {
            alert("유효한 금액을 입력하세요.");
            return false; // 폼 제출 중지
        }

        // 쉼표가 제거된 값을 다시 입력 필드에 설정 (숫자만 서버로 전달)
        transferLimitField.value = rawValue;

        return true; // 비밀번호가 유효하면 폼 제출 허용
    }
 
 // 금액을 포맷하고 최대 입력 제한을 적용하는 함수
    function formatCurrency(input) {
        // 숫자만 추출
        let value = input.value.replace(/,/g, '');

        // 입력값이 숫자가 아닌 경우 빈 문자열로 변환
        if (isNaN(value) || value === '') {
            input.value = '';
            return;
        }

        // 숫자 값을 숫자로 변환
        value = parseInt(value, 10);

        // 금액이 천만원(10,000,000)보다 크면 제한
        if (value > 10000000) {
            value = 10000000;
        }

        // 천 단위로 쉼표 추가
        input.value = value.toLocaleString();
    }
 
 
 
	</script>