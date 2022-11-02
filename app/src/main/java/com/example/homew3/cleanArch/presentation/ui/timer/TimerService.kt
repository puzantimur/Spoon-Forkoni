package com.example.homew3.cleanArch.presentation.ui.timer

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.secondsToTime
import kotlinx.coroutines.*

class TimerService : Service() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val notificationHelper by lazy { TimeNotificationManager(this) }

    private var timerState: Int = 0

    private var timerJob: Job? = null

    private var currentTime = 0

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)


        val a: Int? = intent?.getIntExtra(TIMER, 0)

        when (intent?.getSerializableExtra(TIMER_SERVICE_COMMAND)) {
            1 -> startTimer(a)
            2 -> endTimer()
        }

        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        isServiceRunning = false
    }


    private fun updateServiceState() {
        when (timerState) {
            1 -> {
                sendBroadcast(
                    Intent(INTENT_ACTION)
                        .putExtra(STOPWATCH_VALUE, currentTime)
                )
                notificationHelper.makeNotificationWithText(
                    NOTIFICATION_ID,
                    "Time left: ${currentTime.secondsToTime()}"
                )
            }

            2 -> {
                stopService()
            }
            else -> {
                return
            }
        }
    }

    private fun startTimer(value: Int?) {
        timerState = 1

        currentTime = value!!

        startForeground(NOTIFICATION_ID, notificationHelper.notificationBuilder.build())
        updateServiceState()

        startCoroutineTimer()
    }


    private fun endTimer() {
        timerState = 2
        timerJob?.cancel()
        updateServiceState()
    }

    private fun stopService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        } else {
            stopSelf()
        }
    }

    private fun startCoroutineTimer() {
        timerJob = coroutineScope.launch {
            while (currentTime > 0) {
                currentTime--
                delay(1000)
                updateServiceState()
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 137
        const val INTENT_ACTION = "Action"
        const val TIMER_SERVICE_COMMAND = "START_PAUSE_STOP"
        const val STOPWATCH_VALUE = "TimerValue"
        const val TIMER = "TimerValue"

        var isServiceRunning = false
    }
}