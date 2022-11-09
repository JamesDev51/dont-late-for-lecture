package com.team8.dlfl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.team8.dlfl.databinding.ActivityMarkBinding

class MarkActivity : AppCompatActivity() {

    val stations = arrayOf(
        Station("구파발", "화정", "대전"),
        Station("구파발","합정","없음")
    )
    lateinit var binding: ActivityMarkBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_mark)

        // 어떤 형식으로 보여줄거냐 -> 차곡 차곡 쌓아가는 것으로
        binding.recStations.layoutManager = LinearLayoutManager(this)

        // adapter가 적절하게 렌더링해줌
        binding.recStations.adapter = StationsAdapter(stations)

        //버튼 클릭 이벤트
        fun backToPage(view: View){
            val intent: Intent = Intent(this, MainActivity::class.java) // 뒤로갈 페이지는 나중에 수정
            finish()
        }


//        val btnPrevious2: Button = findViewById(R.id.btn_previous2)
//        //버튼 클릭 이벤트
//        btnPrevious2.setOnClickListner {
//            //메인 이동
//            val intent: Intent = Intent(this, MainActivity::class.java)
//
//        }

    }
}