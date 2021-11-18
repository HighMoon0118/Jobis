package com.ssafy.jobis.presentation.myPage

import android.animation.ObjectAnimator
import android.animation.ObjectAnimator.ofFloat
import android.animation.ValueAnimator
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ssafy.jobis.databinding.FragmentMyBinding
import com.ssafy.jobis.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.protobuf.Value
import com.ssafy.jobis.R
import com.ssafy.jobis.data.model.calendar.Schedule
import com.ssafy.jobis.data.model.community.Comment
import com.ssafy.jobis.data.response.PostResponse
import com.ssafy.jobis.data.response.PostResponseList
import com.ssafy.jobis.data.response.UserResponse
import com.ssafy.jobis.presentation.admin.AdminActivity
import com.ssafy.jobis.presentation.calendar.AlarmReceiver
import com.ssafy.jobis.presentation.community.CustomPostAdapter
import com.ssafy.jobis.presentation.community.detail.ui.detail.CustomCommentAdapter
import com.ssafy.jobis.presentation.job.JobScheduleAdapter
import com.ssafy.jobis.presentation.login.Jobis
import kotlinx.android.synthetic.main.fragment_my.*

class MyPageFragment: Fragment() {

    private lateinit var myPageViewModel: MyPageViewModel
    private var mainActivity: MainActivity? = null
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private var uid: String? = null
    lateinit var auth: FirebaseAuth
    var db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPageViewModel = ViewModelProvider(this, MyPageViewModelFactory())
            .get(MyPageViewModel::class.java)
        auth = Firebase.auth
        uid = Firebase.auth.currentUser?.uid
        myPageViewModel.myLikeList.observe(viewLifecycleOwner,
            Observer { myLikeList ->
                myLikeList ?: return@Observer
                updateMyLike(myLikeList)
            })
        myPageViewModel.myPostList.observe(viewLifecycleOwner,
            Observer { myPostList ->
                myPostList ?: return@Observer
                updateMyPost(myPostList)
            })
        myPageViewModel.myCommentList.observe(viewLifecycleOwner,
            Observer { myCommentList ->
                myCommentList ?: return@Observer
                updateMyComment(myCommentList)
            })
        myPageViewModel.myJobList.observe(viewLifecycleOwner,
            Observer { myJobList ->
                myJobList ?: return@Observer
                updateMyJob(myJobList)
            })
        myPageViewModel.isScheduleDeleted.observe(viewLifecycleOwner,
            Observer { isScheduleDeleted ->
                isScheduleDeleted ?: return@Observer
                if (isScheduleDeleted) {
                    myPageViewModel.loadMyJobList(requireContext())
                    Toast.makeText(context, "공고가 달력에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "삭제할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        myPageViewModel.isNicknameChanged.observe(viewLifecycleOwner,
            Observer { isNicknameChanged ->
                isNicknameChanged ?: return@Observer
                if (isNicknameChanged) {
                    binding.myPageNickNameTextView.text = Jobis.prefs.getString("nickname", "??")
                    Toast.makeText(context, "닉네임 변경 완료", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "닉네임 변경 실패", Toast.LENGTH_SHORT).show()
                }
            })

        myPageViewModel.isUserDeleted.observe(viewLifecycleOwner,
            Observer { isUserDeleted ->
                isUserDeleted ?: return@Observer
                if (isUserDeleted) {
                    // 로그아웃 하고 로그인 페이지로 이동
                    Toast.makeText(context, "회원탈퇴되었습니다.", Toast.LENGTH_LONG).show()
                    auth.signOut()
                    Jobis.prefs.setString("nickname", "??")
                    mainActivity?.goUserActivity()
                } else {
                    Toast.makeText(context, "회원탈퇴 실패했습니다. 나중에 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                }
            })

        binding.myPageNickNameTextView.text = Jobis.prefs.getString("nickname", "??")
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            Jobis.prefs.setString("nickname", "??")
            mainActivity?.goUserActivity()
        }

        binding.expandableLayout.parentLayout.setOnClickListener {
            val checked = binding.expandableLayout.isExpanded
            if (checked) {
                binding.expandableLayout.collapse()
            } else {
                binding.expandableLayout.expand()
            }
        }

        binding.jobToggleButton.setOnClickListener {
            val isChecked = binding.jobToggleButton.isChecked
            if (isChecked) {
                binding.myJobRecyclerView.visibility = View.VISIBLE
                myPageViewModel.loadMyJobList(requireContext())
            } else {
                binding.myJobRecyclerView.visibility = View.GONE
            }
        }

        binding.likeToggleButton.setOnClickListener {
            val isChecked = binding.likeToggleButton.isChecked
            // 체크되어있으면 리사이클러뷰를 활성화시킨다.
            myPageViewModel.loadMyLikeList(auth.currentUser!!.uid)
            if (isChecked) {
                binding.myLikeRecyclerView.visibility = View.VISIBLE
                myPageViewModel.loadMyLikeList(auth.currentUser!!.uid)
            } else {
                binding.myLikeRecyclerView.visibility = View.GONE
            }
        }

        binding.postToggleButton.setOnClickListener {
            val isChecked = binding.postToggleButton.isChecked
            if (isChecked) {
                binding.myPostRecyclerView.visibility = View.VISIBLE
                myPageViewModel.loadMyPostList(auth.currentUser!!.uid)
            } else {
                binding.myPostRecyclerView.visibility = View.GONE
            }
        }

        binding.commentToggleButton.setOnClickListener {
            val isChecked = binding.commentToggleButton.isChecked
            if (isChecked) {
                myPageViewModel.loadMyCommentList(auth.currentUser!!.uid)
                binding.myCommentRecyclerView.visibility = View.VISIBLE
            } else {
                binding.myCommentRecyclerView.visibility = View.GONE
            }
        }

        binding.nickNameEditButton.setOnClickListener {
            openEditDialog()
        }
        if (auth.currentUser?.email == "ssafy@gmail.com") {
            binding.adminButton.visibility = View.VISIBLE
            binding.adminButton.setOnClickListener {
                val intent = Intent((activity as MainActivity), AdminActivity::class.java)
                startActivity(intent)
            }
        }

        binding.myDeleteButton.setOnClickListener {
            showUserDeletePopup()
        }

        myPageViewModel.loadMyJobList(requireContext())
        myPageViewModel.loadMyLikeList(auth.currentUser!!.uid)
        myPageViewModel.loadMyPostList(auth.currentUser!!.uid)
        myPageViewModel.loadMyCommentList(auth.currentUser!!.uid)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    fun updateMyLike(postList: PostResponseList) {
        val adapter = CustomPostAdapter()
        adapter.listData = postList
        adapter.setOnItemClickListener(object: CustomPostAdapter.OnItemClickListener{
            override fun onItemClick(v: View, post: PostResponse, pos: Int) {
                if (post.id != null) {
                    (activity as MainActivity).goMyPageDetail(post.id)
                }
            }
        })

        binding.myLikeRecyclerView.adapter = adapter
        binding.myLikeRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }

    fun updateMyPost(postList: PostResponseList) {
        val adapter = CustomPostAdapter()
        adapter.listData = postList
        adapter.setOnItemClickListener(object: CustomPostAdapter.OnItemClickListener{
            override fun onItemClick(v: View, post: PostResponse, pos: Int) {
                if (post.id != null) {
                    (activity as MainActivity).goMyPageDetail(post.id)
                }
            }
        })

        binding.myPostRecyclerView.adapter = adapter
        binding.myPostRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }

    fun updateMyComment(commentList: MutableList<Comment>) {
        val adapter = CustomCommentAdapter()
        adapter.listData = commentList
        adapter.setOnItemClickListener(object: CustomCommentAdapter.OnItemClickListener{
            override fun onItemClick(v: View, comment: Comment, pos: Int) {
                (activity as MainActivity).goCommunityDetailActivity(comment.post_id)
            }
        })
        binding.myCommentRecyclerView.adapter = adapter
        binding.myCommentRecyclerView.layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL)
    }

    fun updateMyJob(jobList: List<Schedule>) {
        val adapter = JobScheduleAdapter()
        adapter.listData = jobList
        adapter.setOnItemClickListener(object: JobScheduleAdapter.OnItemClickListener{
            override fun onItemClick(v: View, schedule: Schedule, post: Int) {
                showDeletePopup(schedule)
            }
        })
        binding.myJobRecyclerView.adapter = adapter
        binding.myJobRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
    }

    private fun showDeletePopup(schedule: Schedule) {
        val inflater = (activity as MainActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.select_popup, null)
        val alertDialog = AlertDialog.Builder((activity as MainActivity))
        alertDialog.setTitle("${schedule.companyName}")
            .setMessage("공고를 달력에서 삭제하시겠습니까?")
            .setPositiveButton("네", DialogInterface.OnClickListener { dialogInterface, i ->
                cancelAlarm(schedule, schedule.id)
                myPageViewModel.deleteSchedule(schedule, requireContext())
            })
            .setNegativeButton("아니오", DialogInterface.OnClickListener { dialogInterface, i ->
                return@OnClickListener
            })
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

    private fun cancelAlarm(schedule: Schedule, alarm_id: Int) {
        val alarmManager = ContextCompat.getSystemService(
            requireContext(),
            AlarmManager::class.java
        ) as AlarmManager
        val intent = Intent(this.context, AlarmReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this.context, alarm_id, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.cancel(pendingIntent) // 알람 취소
        pendingIntent.cancel() // pendingIntent 취소

        Toast.makeText(this.context, "Alaram Cancelled", Toast.LENGTH_LONG).show()
    }

    private fun openEditDialog() {
        val inflater = (activity as MainActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.select_popup, null)
        val container = FrameLayout(requireContext())
        val editText = EditText(context)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.leftMargin = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        params.rightMargin = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        editText.layoutParams = params
        container.addView(editText)

        val alertDialog = AlertDialog.Builder((activity as MainActivity))
        alertDialog.setTitle("닉네임 변경하기")
            .setMessage("변경할 닉네임을 입력하세요.")
            .setPositiveButton("변경", DialogInterface.OnClickListener { dialogInterface, i ->
                val value = editText.text.toString()
                if (value.isNotEmpty()) {
                    myPageViewModel.updateNickName(uid!!, value)
                } else {
                    Toast.makeText(context, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->
                return@OnClickListener
            })
            .setCancelable(false)
            .setView(container)
            .create()
        alertDialog.show()
    }

    private fun showUserDeletePopup() {
        val inflater = (activity as MainActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.select_popup, null)
        val alertDialog = AlertDialog.Builder((activity as MainActivity))
        alertDialog.setTitle("정말 회원탈퇴 하시겠습니까?")
            .setPositiveButton("네...😥", DialogInterface.OnClickListener { dialogInterface, i ->
                myPageViewModel.deleteAccount(uid!!)
            })
            .setNegativeButton("아니오😉", DialogInterface.OnClickListener { dialogInterface, i ->
                return@OnClickListener
            })
            .create()
        alertDialog.setView(view)
        alertDialog.show()
    }

//    private fun changeVisibility(view: View, isExpanded: Boolean) {
//        val objectAnimator = ObjectAnimator.ofFloat(view, "translationY", -1200)
//        objectAnimator.du
//        val va = if (isExpanded) ValueAnimator.ofInt(0, 600) else ValueAnimator.ofInt(600 ,0)
//        va.duration = 500
//        va.addUpdateListener { ValueAnimator.AnimatorUpdateListener {
//            view.layoutParams.height = it.animatedValue as Int
//            view.requestLayout()
//            view.visibility = if (isExpanded) View.VISIBLE else View.GONE
//        } }
//        va.start()
//    }

}

