<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index_top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <!-- s: content -->
    <section id="main" class="content">
        <div class="account_box">
            <p class="account_tit">내 계좌</p>
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
                                <p class="account_name">${account.category}</p>
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
					                <button type="submit">이체</button>
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
                        <a href="">
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
                                <p class="account_name"></p>
                                <p class="account_number"></p>
                                <p class="account_amount"><fmt:formatNumber value="" />원</p>
								<p class="account_transaction">최근 거래: ( <fmt:formatNumber value="" />원 )</p>
                            </div>
                            <div class="btn_box">
                                <button type="button">조회</button>
                                <button type="button">이체</button>
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
                        <a href="">
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
                                <p class="account_name"></p>
                                <p class="account_number"></p>
                                <p class="account_amount"><fmt:formatNumber value="" />원</p>
								<p class="account_transaction">최근 거래: ( <fmt:formatNumber value="" />원 )</p>
                            </div>
                            <div class="btn_box">
                                <button type="button">조회</button>
                                <button type="button">이체</button>
                            </div>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </ul>
        </div>

        <div class="account_box">
            <p class="account_tit">상품</p>
            <ul class="product_list">
                <li>
                    <a href="javascript:;">
                        <div class="img_box">
                            <img src="/images/icons/account.png">
                        </div>
                        <span>정기예금</span>
                        <p class="txt_noti">6개월 기준 기본 2.8%</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <div class="img_box">
                            <img src="/images/icons/account.png">
                        </div>
                        <span>정기적금</span>
                        <p class="txt_noti">6개월 기준 기본 3.3%</p>
                    </a>
                </li>
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
	</script>
    