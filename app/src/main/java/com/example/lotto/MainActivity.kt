package com.example.lotto


import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.lotto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var testValue = "이것은 테스트를 위한 것입니다. 의미 없는 변수입니다."
    private val numberTextList: List<TextView> by lazy {
        listOf<TextView>(
            binding.num1Tv,
            binding.num2Tv,
            binding.num3Tv,
            binding.num4Tv,
            binding.num5Tv,
            binding.num6Tv
        )
    }

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 45

        initAddButton()
        initClearButton()
        initRunButton()

    }

    private fun initClearButton() {
        binding.clearButton.setOnClickListener {
            didRun = false
            pickNumberSet.clear()
            numberTextList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun initAddButton() {
        binding.addButton.setOnClickListener {

            if (didRun) {
                Toast.makeText(
                    this,
                    "초기화 후 시도해 주세요.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(
                    this,
                    "번호는 5개까지만. 남은 것은 랜덤 돌리세요",
                    Toast.LENGTH_SHORT
                ).show()
            }

            var pickNum = binding.numberPicker.value

            if(pickNumberSet.contains(pickNum)){
            Toast.makeText(
                this,
                "삐- 중복 선택 금지",
                Toast.LENGTH_SHORT

            ).show()
                return@setOnClickListener
            }

            val textView = numberTextList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = pickNum.toString()

            setNumberBackground(pickNum, textView)
            pickNumberSet.add(pickNum)
        }
    }

    private fun initRunButton() {
        binding.runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed() { index, number ->
                val textView = numberTextList[index]
                textView.text = number.toString()
                textView.isVisible = true

                setNumberBackground(number, textView)
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (pickNumberSet.contains(i))
                    continue
                this.add(i)

            }

        }
        numberList.shuffle()
        val numList = pickNumberSet.toList() +
                numberList.subList(0, 6 - pickNumberSet.size)
        return numList.sorted()
    }


    private fun setNumberBackground(number: Int, tv: TextView) {
        when (number) {
            in 1..10 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_yellow
                )
            in 11..20 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_blue
                )
            in 21..30 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_red
                )
            in 31..40 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_green
                )
            else -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_gray
                )
        }

    }
}