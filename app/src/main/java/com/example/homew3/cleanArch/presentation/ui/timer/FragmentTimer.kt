package com.example.homew3.cleanArch.presentation.ui.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.secondsToTime
import com.example.homew3.databinding.FragmentTimerBinding

class FragmentTimer : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val stopwatchReceiver = StopwatchReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTimerBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            hours.maxValue = 23
            hours.minValue = 0
            hours.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            minutes.maxValue = 59
            minutes.minValue = 0
            minutes.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            buttonStart.setOnClickListener {
                val selectedTime = (hours.value * 3600) + (minutes.value * 60)
                sendCommandToForegroundService(1, selectedTime)
            }
            buttonStop.setOnClickListener {
                val selectedTime = (hours.value * 3600) + (minutes.value * 60)
                sendCommandToForegroundService(2, selectedTime)
            }


            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(
            stopwatchReceiver,
            IntentFilter(TimerService.INTENT_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(stopwatchReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendCommandToForegroundService(stopwatchState: Int, timerValue: Int) {
        val extras = Bundle()
        extras.putSerializable(TimerService.TIMER, timerValue)
        extras.putSerializable(TimerService.TIMER_SERVICE_COMMAND, stopwatchState)
        val intent = Intent(requireContext(), TimerService::class.java)
            .putExtras(extras)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun updateUi(elapsedTime: Int) {
        binding.textTime.text = elapsedTime.secondsToTime()
    }

    inner class StopwatchReceiver() : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == TimerService.INTENT_ACTION) {
                updateUi(intent.getIntExtra(TimerService.STOPWATCH_VALUE, 0))
            }
        }
    }
}
