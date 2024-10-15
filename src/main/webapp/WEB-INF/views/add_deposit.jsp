<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index_top.jsp"%>
    <!-- s: content -->
    <section id="add_deposit" class="content add_bank">
        <p>정기예금 개설</p>
        <div class="txt_box bg_yellow">
            <p>java<em>bank</em></p>
            <p>${loginId}님의 정기예금</p>
        </div>
        <form name="f" action="/addDepositOk.do" method="post" onsubmit="return submitForm()">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div>
                <p>통장 비밀번호 설정</p>
                <div class="passwd_box">
                    <label>
                        <input type="password" name="productPw" placeholder="비밀번호 4자리 입력" required>
                    </label>
                    <label>
                        <input type="password" placeholder="비밀번호확인 4자리 입력" required>
                    </label>
                </div>
            </div>

            <div>
                <p>예금금액 설정</p>
                <div class="">
                    <label>
                        <input type="text" name="monthlyPayment" value="" required>
                    </label>
                </div>
            </div>

            <div>
                <p>기간 선택</p>
                <div class="">
                    <select class="productTerm" name="productTerm">
                        <option value="6,2.8">6개월 (기본 2.8%)</option>
                        <option value="12,3.0">1년 (기본 3.0%)</option>
                    </select>
                </div>
                
                
            </div>

            <!-- 숨겨진 필드 추가 -->
            <input type="hidden" name="expiryDate" class="expiryDate">
            <input type="hidden" name="interestRate" class="interestRate">

            <div class="btn_box">
                <button type="submit">개설하기</button>
            </div>
        </form>
    </section>
    <!-- e: content -->
<%@ include file="index_footer.jsp"%>

<script>

function submitForm() {
    // 클래스 이름으로 select 요소를 가져옵니다.
    const selectedValue = document.getElementsByClassName('productTerm')[0].value;
    
    // 쉼표를 기준으로 개월수와 이자율을 분리합니다.
    const [months, interestRate] = selectedValue.split(',');

    // 오늘 날짜를 기준으로 만기일자를 계산합니다.
    const today = new Date();
    today.setMonth(today.getMonth() + parseInt(months));
    const expiryDate = today.toISOString().split('T')[0];  // 만기일자 (yyyy-mm-dd 형식으로 변환)

    // 계산된 만기일자와 이자율을 숨겨진 input에 넣어줍니다.
    document.getElementsByClassName('expiryDate')[0].value = expiryDate;
    document.getElementsByClassName('interestRate')[0].value = interestRate;

    // 폼 제출을 계속 진행하도록 true 반환
    return true;
}

</script>