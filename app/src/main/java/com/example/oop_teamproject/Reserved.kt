package com.example.oop_teamproject

data class Reserved(val number:Any, val type:String, val name:String, val count:Any, val price:Any, val cancel:String)
//예약목록의 각 항목들 표기. Int의 값을 가져야 할 친구들은 첫 항목 모든 항목이 String이여야 할 때가 있으므로 Any 사용