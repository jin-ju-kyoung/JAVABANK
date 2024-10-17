<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="index_top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <!-- s: content -->
    <section id="account_list" class="content">
        <div class="info_box bg_yellow">
            <div class="txt_box">
                <p class="account_name">${category}</p>
                <p class="account_number">${productAccount}</p>
                <p class="account_amount"><fmt:formatNumber value="${balance}"/>원</p>
            </div>
            <div class="btn_box">
                <button type="button">이체</button>
            </div>
        </div>

        <div class="account_details">
            <div class="toolbar">
                <select name="period">
                    <option value="1M">1개월</option>
                    <option value="3M">3개월</option>
                    <option value="1Y">1년</option>
                </select>
                <select name="details">
                    <option value="all">전체</option>
                    <option value="deposit">입금</option>
                    <option value="payment">출금</option>
                </select>
            </div>
            
            <ul class="account_list">
        <c:choose>
            <c:when test="${empty depositList}">
                <li>이체 내역이 없습니다.</li>
            </c:when>
            <c:otherwise>
                <c:forEach var="deposit" items="${depositList}">
                    <li class="account_items">
                        <div class="txt_box">
                            <p class="account_date font_gray">
                                <fmt:formatDate value="${deposit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </p>
                            <p class="account_name">${deposit.type}</p>
                            <p class="account_memo font_darkgray">${deposit.memo}</p>
                        </div>
                        <div class="account_info">
                            <p class="account_type font_blue">${deposit.type}</p>
                            <p class="delta_amount font_blue">
                                <fmt:formatNumber value="${deposit.deltaAmount}" />원
                            </p>
                            <p class="account_balance font_darkgray">
                                <fmt:formatNumber value="${deposit.balance}" />원
                            </p>
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