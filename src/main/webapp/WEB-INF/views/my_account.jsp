<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="css/reset.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="js/script.js"></script>
    <title>javabank</title>
</head>
<body>
    <!-- s: content -->
    <section id="my_account" class="content">
        <p>내 계좌</p>
        <div class="account_box">
            <p class="account_tit">입출금</p>
            <ul>
                <li class="nolist">
                    <p>나의 입출금 계좌가 없습니다.</p>
                    <div class="img_box">
                        <img src="/@sources/images/icons/account.png">
                    </div>
                </li>
                <li class="account_item bg_yellow">
                    <!-- 주거래: star.png / 주거래X: star_line.png -->
                    <div class="img_box">
                        <img src="/@sources/images/icons/star_line.png">
                    </div>
                    <div class="txt_box">
                        <p class="account_name">계좌명(입출금/예금/적금)</p>
                        <p class="account_number">0000-0000-0000-0000</p>
                        <p class="account_amount">0원</p>
                    </div>
                    <div class="btn_box">
                        <button type="button">조회</button>
                        <button type="button">이체</button>
                    </div>
                </li>
            </ul>
        </div>

        <div class="account_box">
            <p class="account_tit">예금</p>
            <ul>
                <li class="nolist">
                    <p>나의 예금이 없습니다.</p>
                    <div class="img_box">
                        <img src="/@sources/images/icons/account.png">
                    </div>
                </li>
                <li class="account_item bg_green">
                    <div class="txt_box">
                        <p class="account_name">계좌명(입출금/예금/적금)</p>
                        <p class="account_number">계좌번호</p>
                        <p class="account_amount">0원</p>
                    </div>
                    <div class="btn_box">
                        <button type="button">조회</button>
                        <button type="button">이체</button>
                    </div>
                </li>
            </ul>
        </div>

        <div class="account_box">
            <p class="account_tit">적금</p>
            <ul>
                <li class="nolist">
                    <p>나의 적금이 없습니다.</p>
                    <div class="img_box">
                        <img src="/@sources/images/icons/account.png">
                    </div>
                </li>
                <li class="account_item bg_blue">
                    <div class="txt_box">
                        <p class="account_name" style="color: #dbdbdb;">계좌명(입출금/예금/적금)</p>
                        <p class="account_number" style="color: #c3c1c1;">계좌번호</p>
                        <p class="account_amount" style="color: #dbdbdb;">0원</p>
                    </div>
                    <div class="btn_box">
                        <button type="button">조회</button>
                        <button type="button">이체</button>
                    </div>
                </li>
            </ul>
        </div>
    </section>
    <!-- e: content -->
</body>
</html>