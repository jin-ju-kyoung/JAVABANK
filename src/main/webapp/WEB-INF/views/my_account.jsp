<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index_top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <!-- s: content -->
    <section id="my_account" class="content">
        <p>내 계좌</p>
        <div class="account_box">
            <p class="account_tit">입출금</p>
            <ul>
                <c:choose>
                <c:when test="${!hasAccount}">
                    <!-- 계좌가 없을 때 -->
                    <li class="nolist">
                        <a href="/addAccount.do">
                            <p>등록된 계좌가 없습니다. 계좌를 추가해주세요.</p>
                            <div class="img_box">
                                <img src="/images/icons/account.png">
                            </div>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <!-- 계좌가 있을 때 -->
                    <c:forEach var="account" items="${accountList}">
                        <li class="account_item bg_yellow">
                            <div class="txt_box">
                                <p class="account_name">${account.category}
                                	<c:if test="${account.mainAccount == 'Y'}">
                    				<span class="main_account_label">[주계좌]</span>
                					</c:if>
               					</p>
                                <p class="account_number">${account.depositAccount}</p>
                                <p class="account_amount"><fmt:formatNumber value="${account.balance}" />원</p>
								<p class="account_transaction">최근 거래: ${account.type} ( <fmt:formatNumber value="${account.deltaAmount}" />원 )</p>
                            </div>
                            
                            
                             <div class="btn_box">
					            <form action="/transfer.do" method="POST">
					            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					                <input type="hidden" name="category" value="${account.category}">
					                <input type="hidden" name="depositAccount" value="${account.depositAccount}">
					                <input type="hidden" name="balance" value="${account.balance}">
					                <input type="hidden" name="type" value="${account.type}">
					                <input type="hidden" name="deltaAmount" value="${account.deltaAmount}">
					                <button type="button" onclick="window.location.href='/accountList.do?depositAccount=${account.depositAccount}&category=${account.category}&balance=${account.balance}'">조회</button>
					                <button type="submit">이체</button>
					                <button type="button" onclick="confirmDelete('${account.depositAccount}')">계좌삭제</button>
					            </form>
					        </div>
                        </li>
                    </c:forEach>
                    <button type="button" onclick="window.location.href='/addAccount.do'">계좌 추가</button>
                </c:otherwise>
            </c:choose>
            </ul>
        </div>

        <div class="account_box">
            <p class="account_tit">예금</p>
            <ul>
                <c:choose>
                <c:when test="${!hasDeposit}">
                    <!-- 계좌가 없을 때 -->
                    <li class="nolist">
                        <a href="/addDeposit.do">
                            <p>등록된 계좌가 없습니다. 예금계좌를 추가해주세요.</p>
                            <div class="img_box">
                                <img src="/images/icons/account.png">
                            </div>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <!-- 계좌가 있을 때 -->
                    <c:forEach var="deposit" items="${depositList}">
                        <li class="account_item bg_green">
                            <div class="txt_box">
                                <p class="account_name">${deposit.category}</p>
                                <p class="account_number">${deposit.productAccount}</p>
                                <p class="account_amount"><fmt:formatNumber value="${deposit.balance}" />원</p>
								<!-- <p class="account_transaction">최근 거래: ( <fmt:formatNumber value="" />원 )</p> -->
                            </div>
                            <div class="btn_box">
                            
                             <form action="/transfer.do" method="POST">
					            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					                <input type="hidden" name="category" value="${deposit.category}">
					                <input type="hidden" name="depositAccount" value="${deposit.productAccount}">
					                <input type="hidden" name="balance" value="${deposit.balance}">
                                <button type="button" onclick="window.location.href='/depositList.do?productAccount=${deposit.productAccount}&category=${deposit.category}&balance=${deposit.balance}'">조회</button>
                                <button type="button" onclick="location.href='product_delete?productAccount=${deposit.productAccount}'">계좌삭제</button>
                              <!--   <button type="submit">이체</button> -->
                             </form>
                             
                             
                            </div>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </ul>
        </div>

        <div class="account_box">
            <p class="account_tit">적금</p>
            <ul>
                <c:choose>
                <c:when test="${!hasSavings}">
                    <!-- 계좌가 없을 때 -->
                    <li class="nolist">
                        <a href="/addInstallmentSaving.do">
                            <p>등록된 계좌가 없습니다. 적금계좌를 추가해주세요.</p>
                            <div class="img_box">
                                <img src="/images/icons/account.png">
                            </div>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <!-- 계좌가 있을 때 -->
                    <c:forEach var="saving" items="${InstallmentSavingsList}">
                        <li class="account_item bg_blue">
                            <div class="txt_box">
                                <p class="account_name">${saving.category}</p>
                                <p class="account_number">${saving.productAccount}</p>
                                <p class="account_amount"><fmt:formatNumber value="${saving.balance}" />원</p>
								<!-- <p class="account_transaction">최근 거래: ( <fmt:formatNumber value="" />원 )</p> -->
                            </div>
                            <div class="btn_box">
                            <form action="/transfer.do" method="POST">
					            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					                <input type="hidden" name="category" value="${saving.category}">
					                <input type="hidden" name="depositAccount" value="${saving.productAccount}">
					                <input type="hidden" name="balance" value="${saving.balance}">
                                <button type="button" onclick="window.location.href='/savingList.do?productAccount=${saving.productAccount}&category=${saving.category}&balance=${saving.balance}'">조회</button>
                                <button type="button" onclick="location.href='product_delete?productAccount=${saving.productAccount}'">계좌삭제</button>
                                <!--   <button type="submit">이체</button> -->
                            </form>
                            </div>
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
    	let msg = "${msg}";
		if(msg){
			alert(msg);
		}
		
		function confirmDelete(depositAccount) {
			//console.log(depositAccount);
	        if (confirm("정말로 이 계좌를 해지하시겠습니까?")) {
	            // 사용자가 확인을 누른 경우, 계좌 해지 요청을 보냅니다.
	            location.href = '/accountDelete.do?depositAccount=' + depositAccount;
	        } else {
	            // 사용자가 취소를 누른 경우, 아무 동작도 하지 않습니다.
	            return false;
	        }
	    }
	</script>