package com.example.oop_teamproject.model

class FileItem (
    val color: String,        // 인쇄 색상 (흑백, 컬러)
    val direction: String,    // 인쇄 방향 (가로, 세로)
    val page: String,         // 페이지 수
    val quantity: Int,        // 수량
    val type: String,         // 인쇄 유형 (양면, 단면)
    val name: String?,         // 파일 이름
    val date: String,         // 입력받은 날짜
    val time: String,         // 입력받은 시간
    val price: Int            // 결제 금액
)