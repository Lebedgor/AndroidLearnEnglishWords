package com.example.englishwordsapp
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.englishwordsapp.databinding.ActivityLearnWordBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding){
            continueButton.setOnClickListener {
                markAnswerNeutral()
                showNextQuestion(trainer)
            }
            skipButton.setOnClickListener {
                showNextQuestion(trainer)
            }
        }

    }

    private fun showNextQuestion(trainer: LearnWordsTrainer){
        val firstQuestion: Question? = trainer.getNextQuestion()

        with(binding){
            if(firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS){
                layoutVariants.isVisible = false
                tvQuestionWord.isVisible = false
                skipButton.text = "Complete"
            } else {
                skipButton.text = ContextCompat.getString(this@MainActivity, R.string.button_skip)
                layoutVariants.isVisible = true
                tvQuestionWord.isVisible = true
                markAnswerNeutral()
                tvQuestionWord.text = firstQuestion.correctAnswer.original
                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    if(trainer.checkAnswer(0)){
                        markAnswerNeutral()
                        markAnswerCorrect(
                            binding.layoutAnswer1,
                            binding.tvVariantNumber1,
                            binding.tvVariantValue1
                        )
                    } else {
                        markAnswerNeutral()
                        markAnswerWrong(
                            binding.layoutAnswer1,
                            binding.tvVariantNumber1,
                            binding.tvVariantValue1
                        )
                    }
                }
                layoutAnswer2.setOnClickListener {
                    if(trainer.checkAnswer(1)){
                        markAnswerNeutral()
                        markAnswerCorrect(
                            binding.layoutAnswer2,
                            binding.tvVariantNumber2,
                            binding.tvVariantValue2
                        )
                    } else {
                        markAnswerNeutral()
                        markAnswerWrong(
                            binding.layoutAnswer2,
                            binding.tvVariantNumber2,
                            binding.tvVariantValue2
                        )
                    }
                }
                layoutAnswer3.setOnClickListener {
                    if(trainer.checkAnswer(2)){
                        markAnswerNeutral()
                        markAnswerCorrect(
                            binding.layoutAnswer3,
                            binding.tvVariantNumber3,
                            binding.tvVariantValue3
                        )
                    } else {
                        markAnswerNeutral()
                        markAnswerWrong(
                            binding.layoutAnswer3,
                            binding.tvVariantNumber3,
                            binding.tvVariantValue3
                        )
                    }
                }
                layoutAnswer4.setOnClickListener {
                    if(trainer.checkAnswer(3)){
                        markAnswerNeutral()
                        markAnswerCorrect(
                            binding.layoutAnswer4,
                            binding.tvVariantNumber4,
                            binding.tvVariantValue4
                        )
                    } else {
                        markAnswerNeutral()
                        markAnswerWrong(
                            binding.layoutAnswer4,
                            binding.tvVariantNumber4,
                            binding.tvVariantValue4
                        )
                    }
                }
            }
        }
    }

//    private fun markAnswerNeutral(
//        layoutAnswer: LinearLayout,
//        variantNumber: TextView,
//        variantValue: TextView
//    ) {
//
//        layoutAnswer.background = ContextCompat.getDrawable(
//                this@MainActivity,
//                R.drawable.shape_rounded_containers
//            )
//
//
//        variantNumber.apply {
//            background = ContextCompat.getDrawable(
//                this@MainActivity,
//                R.drawable.shape_rounded_variants
//            )
//            setTextColor(ContextCompat.getColor(
//                this@MainActivity,
//                R.color.black
//            ))
//        }
//
//        variantValue.setTextColor(ContextCompat.getColor(
//                this@MainActivity,
//                R.color.textVariantColor
//            ))
//
//
//        binding.skipButton.isVisible = true
//        binding.clInfoLayout.isVisible = false
//    }

    private fun markAnswerNeutral() {

        with(binding){
            for(layoutAnswer in listOf(layoutAnswer1, layoutAnswer2, layoutAnswer3, layoutAnswer4)){
                layoutAnswer.background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.shape_rounded_containers
                )
            }

            for(variantNumber in listOf(tvVariantNumber1, tvVariantNumber2, tvVariantNumber3, tvVariantNumber4)){
                variantNumber.apply {
                    background = ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.shape_rounded_variants
                    )
                    setTextColor(ContextCompat.getColor(
                        this@MainActivity,
                        R.color.black
                    ))
                }
            }

            for(variantValue in listOf(tvVariantValue1, tvVariantValue2, tvVariantValue3, tvVariantValue4)){
                variantValue.setTextColor(ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantColor
                ))
            }
        }



        binding.skipButton.isVisible = true
        binding.clInfoLayout.isVisible = false
    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        variantNumber: TextView,
        variantValue: TextView
    ) {

        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_wrong_containers
        )
        variantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_wrong_variants
            )
            setTextColor(Color.WHITE)
        }
        variantValue.setTextColor(ContextCompat.getColor(
            this@MainActivity,
            R.color.wrongColor
        ))

        showResultMessage(false)
    }


    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        variantNumber: TextView,
        variantValue: TextView
    ) {

        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_correct_containers
        )
        variantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_correct_variants
            )
            setTextColor(Color.WHITE)
        }
        variantValue.setTextColor(ContextCompat.getColor(
            this@MainActivity,
            R.color.correctColor
        ))

        showResultMessage(true)

    }


    private fun showResultMessage(isCorrect: Boolean){
        val color: Int
        val message: String
        val resultIconResource: Int

        if(isCorrect){
            color = ContextCompat.getColor(this, R.color.correctColor)
            message = ContextCompat.getString(this, R.string.title_correct)
            resultIconResource = R.drawable.ic_correct
        } else {
            color = ContextCompat.getColor(this, R.color.wrongColor)
            message = ContextCompat.getString(this, R.string.title_wrong)
            resultIconResource = R.drawable.ic_wrong
        }
        with(binding) {
            clInfoLayout.setBackgroundColor(color)
            continueButton.setTextColor(color)
            ivAnswerIcon.setImageResource(resultIconResource)
            tvAnswerInfoText.text = message
        }
        getInfoBlockVisible()
    }

    private fun getInfoBlockVisible() {
        binding.skipButton.isVisible = false
        binding.clInfoLayout.isVisible = true
    }


}