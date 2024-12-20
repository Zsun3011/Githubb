package com.example.oop_teamproject

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oop_teamproject.databinding.FragmentPaymentSystemBinding
import com.example.oop_teamproject.model.BookItem
import com.example.oop_teamproject.model.FileItem
import com.example.oop_teamproject.viewmodel.PaymentViewmodel
import com.example.oop_teamproject.viewmodel.UsersViewmodel
import java.util.Calendar

class PaymentSystemFragment : Fragment() {
    private lateinit var usersViewModel: UsersViewmodel
    private lateinit var paymentViewModel: PaymentViewmodel
    private lateinit var textView: TextView // 인쇄 정보를 표시할 TextView
    private lateinit var priceTextView: TextView // 가격 정보를 표시할 TextView
    private lateinit var editTextDate: EditText // 날짜 입력을 위한 EditText
    private lateinit var editTextTime: EditText // 시간 입력을 위한 EditText
    private lateinit var cardNameTextView: TextView // 카드 이름을 표시할 TextView

    private var binding: FragmentPaymentSystemBinding? = null
    private lateinit var page: String
    private var quantity: Int = 0
    private lateinit var type: String
    private lateinit var direction: String
    private lateinit var color: String
    private var name: String? = null
    private var selectedKey: String? = null
    private var price: Int = 0

    // 다이얼로그 선택 여부를 추적하는 변수
    private var isDateSelected = false
    private var isTimeSelected = false

    //file 인지 book 인지 확인
    private var isfile: Boolean = false

    private var isDialogShowing = false // 다이얼로그가 열려 있는지 여부를 추적하는 변수
    private var isUserTriggered = false // 사용자가 버튼을 눌렀는지 여부를 추적하는 변수


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentSystemBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel 초기화
        usersViewModel = ViewModelProvider(requireActivity()).get(UsersViewmodel::class.java)
        paymentViewModel = ViewModelProvider(requireActivity()).get(PaymentViewmodel::class.java)
        textView = view.findViewById(R.id.dataset) // TextView ID에 맞게 수정
        priceTextView = view.findViewById(R.id.price) // 가격을 표시할 TextView
        editTextDate = view.findViewById(R.id.editTextDate) // 날짜 입력 EditText
        editTextTime = view.findViewById(R.id.editTextTime) // 시간 입력 EditText 추가
        cardNameTextView = view.findViewById(R.id.cardname)

        // Bundle에서 인쇄 설정 정보 가져오기
        arguments?.let {bundle ->
            val source = bundle.getString("source")

            if (source == "fileReservation") {
                // 파일 예약 페이지에서 전달된 데이터 처리
                page = bundle.getString("page") ?: ""
                quantity = bundle.getInt("quantity")
                type = bundle.getString("type") ?: ""
                direction = bundle.getString("direction") ?: ""
                color = bundle.getString("color") ?: ""
                name = bundle.getString("name") ?: ""

                // 문자열 리소스를 사용하여 데이터 표시
                textView.text =
                    getString(R.string.print_info_format, page, quantity, type, direction, color, name)

                // 가격 계산 및 표시
                calculatePrice(page)
                isfile = true

            } else if (source == "bookReservation") {
                // 도서 예약 페이지에서 전달된 데이터 처리
                name = bundle.getString("bookName") ?: ""
                val bookPrice = bundle.getInt("bookPrice")
                quantity = bundle.getInt("quantity")

                // 도서 예약 관련 데이터 표시
                textView.text = getString(R.string.book_info_format, name, quantity, bookPrice)

                // 가격 계산 및 표시
                price = bookPrice * quantity // 가격을 bookPrice로 설정
                priceTextView.text = price.toString()
                isfile = false
            }

        }

        // 날짜 입력 필드 클릭 리스너
        editTextDate.setOnClickListener {
            showDatePickerDialog()
        }

        // 시간 입력 필드 클릭 리스너
        editTextTime.setOnClickListener {
            showTimePickerDialog()
        }

        // 결제 키 가져오기 버튼 클릭 리스너
        binding?.payment?.setOnClickListener {
            fetchPaymentKeys()
            isUserTriggered = true
        }

        // LiveData 관찰
        paymentViewModel.paymentKeys.observe(viewLifecycleOwner) { keys ->
            if (keys.isNotEmpty() && isUserTriggered) {
                // 다이얼로그가 열려 있지 않은 경우에만 열기
                if (!isDialogShowing) {
                    showPaymentKeysDialog(keys) // 다이얼로그로 키 보여주기
                }
            } else if (keys.isEmpty()) {
                Toast.makeText(requireContext(), "결과가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }


        // 홈으로 가는 버튼 클릭 리스너
        binding?.gotohome?.setOnClickListener {
            // selectedKey와 price가 null이 아닌지 확인 후 호출
            if (selectedKey != null && isDateSelected && isTimeSelected) {
                // 카드 값 가져오기
                paymentViewModel.calculate(selectedKey!!, price).observe(viewLifecycleOwner) { cardValue ->
                    // cardValue가 price보다 작은지 확인
                    if (cardValue < price) {
                        // 잔액을 확인해주세요 메시지 표시
                        Toast.makeText(requireContext(), "잔액을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (isfile) {
                            saveFileData()
                        } else {
                            saveBookData()
                        }
                        findNavController().navigate(R.id.action_paymentSystemFragment_to_booksearchFragment)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "결제정보를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    //날짜 선택 다이얼로그
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // 날짜를 YYYY/MM/DD 형식으로 설정
                val formattedDate =
                    String.format("%04d/%02d/%02d", selectedYear, selectedMonth + 1, selectedDay)
                editTextDate.setText(formattedDate)
                isDateSelected = true // 날짜가 선택되었음을 표시
            }, year, month, day)

        datePickerDialog.show()
    }

    //시간 선택 다이얼로그
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // TimePickerDialog를 사용하여 시간 선택
        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                // 오전/오후 선택
                val amPm = if (selectedHour < 12) "AM" else "PM"
                // 12시간 형식으로 변환
                val hour12 = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                // 시간 문자열 생성
                val formattedTime = String.format("%02d:%02d %s", hour12, selectedMinute, amPm)
                editTextTime.setText(formattedTime)
                isTimeSelected = true // 시간이 선택되었음을 표시
            }, hour, minute, false)

        timePickerDialog.show()
    }

    //파일 가격 계산
    private fun calculatePrice(page: String) {
        // 입력 문자열에서 숫자 추출
        val numbers = page.split("-").map { it.trim().toIntOrNull() }

        // 두 개의 숫자가 모두 유효한 경우
        if (numbers.size == 2 && numbers.all { it != null }) {
            val firstNumber = numbers[0]!!
            val secondNumber = numbers[1]!!

            // 컬러 체크 및 가격 계산
            val unitPrice = if (color == "컬러") 500 else 200
            val price = (secondNumber - firstNumber + 1) * unitPrice * quantity
            priceTextView.text = price.toString()
        }
        // 숫자가 하나만 입력된 경우
        else if (numbers.size == 1 && numbers[0] != null) {
            val unitPrice = if (color == "컬러") 500 else 200
            val price = unitPrice * quantity
            priceTextView.text = price.toString()
        }
        // 유효하지 않은 입력 처리
        else {
            priceTextView.text = "유효하지 않은 입력입니다."
        }
    }

    //파일 데이터 저장
    private fun saveFileData() {
        // 가격을 정수로 변환 (기본값 0)
        val price = priceTextView.text.toString().toIntOrNull() ?: 0

        // FileItem 생성
        val fileItem = FileItem(
            page = page,
            quantity = quantity,
            type = type,
            direction = direction,
            color = color,
            name = name,
            date = editTextDate.text.toString(),  // 날짜 추가
            time = editTextTime.text.toString(),   // 시간 추가
            price = price                           // 가격 추가
        )

        // ViewModel을 통해 데이터 저장
        usersViewModel.saveFileItem(fileItem)
    }

    //도서 데이터 저장
    private fun saveBookData() {
        // 가격을 정수로 변환 (기본값 0)
        val price = priceTextView.text.toString().toIntOrNull() ?: 0

        // FileItem 생성
        val bookItem = BookItem(
            quantity = quantity,
            name = name,
            price = price, // 가격 추가
            date = editTextDate.text.toString(),  // 날짜 추가
            time = editTextTime.text.toString()   // 시간 추가
        )

        // ViewModel을 통해 데이터 저장
        usersViewModel.saveBookItem(bookItem)
    }

    //카드 목록 가져오기
    private fun fetchPaymentKeys() {
        paymentViewModel.fetchPaymentKeys() // payment 키 가져오기
    }

    //카드 목록 보여주기
    private fun showPaymentKeysDialog(keys: List<String>) {
        isDialogShowing = true // 다이얼로그가 열리기 시작함을 표시

        AlertDialog.Builder(requireContext()).apply {
            setTitle("Payment")
            setItems(keys.toTypedArray()) { _, which ->
                // 선택된 키를 처리하는 로직 추가
                selectedKey = keys[which]
                cardNameTextView.text = selectedKey // 선택한 키를 cardName TextView에 설정

                // 선택된 키에 해당하는 값을 가져와서 priceTextView의 값과 빼기
                price = priceTextView.text.toString().toIntOrNull() ?: 0 // priceTextView의 값 가져오기

                // 로그 추가: selectedKey와 price 확인
                Log.d("PaymentDialog", "Selected Key: $selectedKey, Price: $price")
            }
            setNegativeButton("취소") { _, _ ->
                isDialogShowing = false // 다이얼로그가 닫힐 때 상태 업데이트
            }
            setOnDismissListener {
                isDialogShowing = false // 다이얼로그가 닫힐 때 상태 업데이트
                isUserTriggered = false // 사용자가 버튼을 눌렀음을 초기화
            }
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}