package com.example.oop_teamproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oop_teamproject.repository.PaymentRepository

class PaymentViewmodel : ViewModel() {
    private val repository: PaymentRepository = PaymentRepository()
    private val _paymentKeys = MutableLiveData<List<String>>()
    val paymentKeys: LiveData<List<String>> get() = _paymentKeys

    private val _cardValue = MutableLiveData<Long>()
    val cardValue: LiveData<Long> get() = _cardValue

    private val _remainingAmount = MutableLiveData<Long>()
    val remainingAmount: LiveData<Long> get() = _remainingAmount

    // payment 키 가져오기
    fun fetchPaymentKeys() {
        repository.fetchPaymentKeys(
            onSuccess = { keys ->
                _paymentKeys.value = keys
            },
            onFailure = { error ->
                Log.e("PaymentViewModel", "Error fetching payment keys: ${error.message}")
                _paymentKeys.value = emptyList() // 실패 시 빈 리스트 설정
            }
        )
    }

    fun calculate(selectedKey: String, price: Int): LiveData<Double> {
        val result = MutableLiveData<Double>() // LiveData 객체 생성

        repository.fetchCardValue(selectedKey,
            onSuccess = { value ->
                Log.d("PaymentViewModel", "Fetched card value: $value, Price: $price") // 로그 추가
                _cardValue.value = value

                // 남은 금액 계산
                val remaining = value - price
                Log.d("PaymentViewModel", "Remaining amount calculated: $remaining") // 로그 추가
                _remainingAmount.value = remaining // 남은 금액 업데이트

                // 카드 값에서 price를 빼는 로직
                val newCardValue = value - price
                repository.updateCardValue(selectedKey, newCardValue,
                    onSuccess = {
                        Log.d("PaymentViewModel", "Card value updated successfully.")
                        result.value = newCardValue.toDouble() // Long을 Double로 변환하여 LiveData에 설정
                    },
                    onFailure = { error ->
                        Log.e("PaymentViewModel", "Error updating card value: ${error.message}")
                        result.value = value.toDouble() // Long을 Double로 변환하여 LiveData에 설정
                    }
                )
            },
            onFailure = { error ->
                Log.e("PaymentViewModel", "Error fetching card value: ${error.message}")
                _cardValue.value = 0 // 실패 시 0 설정
                _remainingAmount.value = 0 // 실패 시 남은 금액도 0 설정
                result.value = 0.0 // 실패 시 LiveData에 0.0 설정
            }
        )

        return result // LiveData 반환
    }
}
