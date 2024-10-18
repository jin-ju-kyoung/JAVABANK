<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index_top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!-- s: content -->
    <section id="transfer" class="content">
        <form name="f" action="/transferMoney.do" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="bank_info">
                <p>송금할 계좌를 입력해주세요.</p>
                <select class="bank" name="bankName">
                    <option value="javabank">javabank</option>
                   <!--  <option value="국민">국민</option>
                    <option value="기업">기업</option>
                    <option value="농협">농협</option>
                    <option value="우리">우리</option>
                    <option value="하나">하나</option>
                    <option value="카카오뱅크">카카오뱅크</option>
                    <option value="케이뱅크">케이뱅크</option>
                    <option value="토스뱅크">토스뱅크</option>
                    <option value="새마을">새마을</option> -->
                </select>
                <label>
                    <input type="text" name="transferredAccount" value="" placeholder="계좌번호 직접입력" required
                    	class="accountNumberInput">
                </label>
                <button class="bg_yellow" type="submit">다음</button>
            </div>
            <c:if test="${not empty error}">
			    <p class="error" align="center">${error}</p>
			</c:if>
        </form>

        <div class="bank_list">
            <p>내계좌</p>
            <ul class="my_list account_list">
                <li>
                    <a href="javascript:;">
                        <div class="img_box">
                            <img src="/images/icons/passbook.png">
                        </div>
                        <div class="txt_box">
                            <p class="account_name">${category}</p>
                            <p class="deposit_account"><span>javabank</span>${depositAccount}</p>
                        </div>
                    </a>
                </li>
            </ul>
            
            
            <p>최근 이체</p>
            <ul class="recently_list account_list">
            <c:choose>
            	<c:when test="${empty transferList}">
	                <li>내역이 없습니다.</li>
	            </c:when>
	            <c:otherwise>
                	<c:forEach var="account" items="${transferList}">
				        <li>
				            <a href="javascript:;">
				                <div class="img_box">
				                    <img src="/images/icons/passbook.png">
				                </div>
				                <div class="txt_box">
				                    <p class="account_name">${account.transferredName}</p>
				                    <p class="deposit_account"><span>javabank</span>${account.transferredAccount}</p>
				                </div>
				            </a>
				        </li>
				    </c:forEach>
    			</c:otherwise>
    			</c:choose>
            </ul>
        </div>
    </section>
    <!-- e: content -->
<%@ include file="index_footer.jsp"%>

<script>
class AccountNumberFormatter {
    constructor(inputElement) {
        this.inputElement = inputElement;
        this.maxLength = 16;  // 최대 16자리 숫자로 제한
        this.initialize();
    }

    initialize() {
        // 입력 이벤트 리스너 추가
        this.inputElement.addEventListener('input', () => {
            this.formatAccountNumber();
        });
    }

    formatAccountNumber() {
        // 숫자만 남기기
        let value = this.inputElement.value.replace(/\D/g, '');

        // 16자리까지만 허용
        if (value.length > this.maxLength) {
            value = value.substring(0, this.maxLength);
        }

        // 4자리마다 하이픈 추가
        const formattedValue = value.replace(/(\d{4})(?=\d)/g, '$1-');

        // input 필드에 하이픈이 추가된 값 반영
        this.inputElement.value = formattedValue;
    }
}

// 페이지 로드 시 계좌번호 입력 필드에 대해 AccountNumberFormatter 클래스를 적용
document.addEventListener('DOMContentLoaded', () => {
    const accountInput = document.querySelector('.accountNumberInput');
    if (accountInput) {
        new AccountNumberFormatter(accountInput);
    }
});
</script>
