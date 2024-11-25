package com.example.oop_teamproject.model

class FileItem (
    val color: String,        // 인쇄 색상
    val direction: String,    // 인쇄 방향 (가로, 세로 등)
    val page: String,         // 페이지 수
    val quantity: Int,        // 수량
    val type: String,          // 인쇄 유형 (양면, 단면 등)
    val name: String          // 이름 추가
)