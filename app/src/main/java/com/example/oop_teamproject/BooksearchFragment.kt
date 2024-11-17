package com.example.oop_teamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //이부분 살짝 바꿨음. return 중복. 원래는 val view = ~~이 아닌, return inflater~~였습니다.
        val view = inflater.inflate(R.layout.fragment_booksearch, container, false)

        //여기부터~~~
        // tmp_btn 버튼에 클릭 리스너 추가
        val tmpButton: Button = view.findViewById(R.id.tmp_btn)
        tmpButton.setOnClickListener {
            // BooklistFragment로 화면 전환
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, BooklistFragment()) // fragment_container의 정확한 ID를 사용해야 함.
                .addToBackStack(null)
                .commit()
        }

        return view
        //~~~여기까지가 임시 버튼용 코드예요!
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BooksearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}