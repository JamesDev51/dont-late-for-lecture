package com.team8.dlfl.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.team8.dlfl.R
import com.team8.dlfl.view.mainview.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseService"

    // 메세지를 수신할 때 호출된다 remoteMessage는 수신한 메세지
    // 메시지 수신 메서드(포그라운드) 추가2
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //val remoteMessage = remoteMessage
        //Log.d(TAG, "From: ${remoteMessage.from}")

        //받은 remoteMessage의 값 출력해보기. 데이터메세지 / 알림메세지
        Log.d(TAG, "Message data : ${remoteMessage.data}")
        Log.d(TAG, "Message noti : ${remoteMessage.notification}")

        if(remoteMessage.data.isNotEmpty()){
            //알림생성
            sendNotification(remoteMessage)
            Log.d(TAG, remoteMessage.data["title"].toString())
            Log.d(TAG, remoteMessage.data["body"].toString())
        }else {
            Log.e(TAG, "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
        }


    }

    // FCM 등록 토큰이 업데이트되면 호출
    // 토큰이 처름 생성될때 여기에서 토큰을 검색할 수 있다.
    // 토큰 생성 메서드
    override fun onNewToken(token: String) {
        Log.d(TAG, "새 토큰: $token")
        //super.onNewToken(token)

        //토큰 값을 따로 저장 (추가1)
        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("token", token).apply()
        editor.commit()
        Log.i(TAG, "성공적으로 토큰을 저장함")

        // 이 앱 인스턴스에 메세지를 보내려는 경우나 서버 측에서 이 앱 구독을 관리한다면,
        // FCM 등록 토큰을 앱 서버에 추가
        //sendRegistrationToServer(token)
    }

    // 알림 생성 메서드 (추가 3)
    private fun sendNotification(remoteMessage: RemoteMessage) {

        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임
        val intent = Intent(this, MainActivity::class.java)
        //각 key, value 추가
        for(key in remoteMessage.data.keys){
            intent.putExtra(key, remoteMessage.data.getValue(key))
        }
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남김(A-B-C-D-B => A-B)
        val pendingIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_IMMUTABLE)

        // 알림 채널 이름
        val channelId = "my_channel"
        // 알림 소리
        //val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보, 작업
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.arrow) // 아이콘 설정
            .setContentTitle(remoteMessage.data["title"].toString()) // 제목
            .setContentText(remoteMessage.data["body"].toString()) // 메시지 내용
            //.setAutoCancel(true) // 알람클릭시 삭제여부
            //.setSound(soundUri)  // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notification)
    }

    // 토큰 가져오기
    fun getFirebaseToken(){
        // 비동기 방식
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d(TAG, "토큰=${it}")
        }
    }


   //
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // 안드로이드 오레오 알림채널이 필요하기 때문에 넣음
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(0, notificationBuilder.build())
//    }
//    companion object {
//        private const val TAG = "MyFirebaseMsgService"
//    }

}