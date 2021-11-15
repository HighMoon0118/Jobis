package com.ssafy.jobis.presentation.study.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.jobis.R
import com.ssafy.jobis.presentation.chat.ChatActivity
import com.ssafy.jobis.presentation.chat.ChatLogActivity
import com.ssafy.jobis.presentation.study.CreateStudy
import kotlinx.coroutines.currentCoroutineContext

// class 뒤에 : 를 사용하면 상속의 개념
// RecyclerView.Adapter를 상속받는데 ProfileAdapter.CustomViewHolder type을 상속받음
// CustomViewHolder는 여기서 정의 함
// profileList는 엑티비티에서 미리 준빈되어 있는 리스트임
class TmpAdapter(val studyList: ArrayList<CreateStudy.Study>):RecyclerView.Adapter<TmpAdapter.CustomViewHolder>() {


    // viewHolder를 만들때 작동하는 함수
    // list_item.xml의 화면을 여기서 붙히는 작업
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TmpAdapter.CustomViewHolder {
        // 1번째 인자: adapter와 연결될 엑티비티의 context를 가져옴
        // inflate = 붙히다
        // 뒤에 1번째 인자 : 어뎁터를 사용할 layout 틀
        // 뒤에 2번째 인자 : 어뎁터를 적용할 layout
        // 뒤에 3번째 인자 : false
        // 이거는 공부가 더 필요하겠다
        // 가지고 있는 list_item.xml에 대한걸 끌고 와서 어뎁터에 붙혀주는 역할을 함
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tmp_list_item, parent, false)

        // view를 생성 해 준 다음에 CustomViewHolder에 전달을 해준것
        // 아래에 class CustomViewHolder에 view를 전달해 준 것
        // 그러면 생성된 CustomViewHolder를 return해줌
        // + recyclerView에 있는것 클릭처리
        return CustomViewHolder(view).apply {
//             itemView: viewHolder가 가지고 있는 view들을 의미
            itemView.setOnClickListener{
                // adapterPostion : 말 그대로 현재 클릭된 어뎁터의 포지션
                val curPos:Int = adapterPosition
                val study: CreateStudy.Study = studyList.get(curPos)
                // parent.context : 현재 연결되어 있는 parent의 context
                Toast.makeText(parent.context, "이름 : ${study.studyName}", Toast.LENGTH_SHORT).show()

                val intent = Intent(view.context, ChatLogActivity::class.java)
                intent.putExtra("studyName", study.studyName)
                view.context.startActivity(intent)
            }

        }
    }

    // onCreateViewHolder에서 만들어진 view를 가져다가 Bind(연결)을 해주는것
    // 스크롤을 하는등 recyclerView를 사용을 할 때 지속적으로 호출되면서
    // 이 view에 대해서 데이터를 안정적으로 매치 해줌
    // 가져온 정보를 여기에서 xml파일에 넣어줌(바인딩)
    override fun onBindViewHolder(holder: TmpAdapter.CustomViewHolder, position: Int) {
        holder.name.text = studyList.get(position).studyName
        holder.uid.text = studyList.get(position).uid
        holder.describe.text = studyList.get(position).studyDescribe

        Log.d("holder", holder.toString())
    }

    override fun getItemCount(): Int {
        return studyList.size
    }

    // 뷰에 대한것을 잡아주는 역할
    // 코틀린 클래스에 대한걸 좀 공부해야할듯
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_studyName)        // 이름
        val uid = itemView.findViewById<TextView>(R.id.tv_uid)          // 나이
        val describe = itemView.findViewById<TextView>(R.id.tv_studyDescribe)          // 직업


    }
}