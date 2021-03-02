package at.ac.fhcampuswien.numberguessinggame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _generatedNumbers = MutableLiveData<ArrayList<Int>>()
    val generatedNumbers: LiveData<ArrayList<Int>>
        get() = _generatedNumbers

    private val _guessedCorrectlyLD = MutableLiveData<String>()
    val guessedCorrectlyLD: LiveData<String>
        get() = _guessedCorrectlyLD

    private val _correctPositionLD = MutableLiveData<String>()
    val correctPositionLD: LiveData<String>
        get() = _correctPositionLD

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        createRandomNumbers()
    }

    private fun createRandomNumbers() {

        val numbers: ArrayList<Int> = arrayListOf<Int>()

        val limit = 4
        val start = 0
        val end = 9

        // iterate through, from 1 to 4
        for (i in 1..limit) {

            var fin = 0

            // create new random number as long as no duplicate was produced
            while (fin == 0) {

                // generate random number, from 0 to 9
                val currentNumber = rand(start, end)

                // if array doesn't contain current number yet or array is empty
                if (!numbers.contains(currentNumber) || numbers.isEmpty()) {
                    // add number to array
                    numbers.add(currentNumber)
                    fin = 1
                }
            }
        }
        //Log.i("GameViewModel", "ALL: $numbers")

        _generatedNumbers.value = numbers
    }

    // https://www.techiedelight.com/generate-random-numbers-between-specified-range-kotlin/#:~:text=Kotlin%20offers%20the%20Random%20class,the%20specified%20value%20(exclusive).
    private fun rand(start: Int, end: Int): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }

    fun guess(firstNum: Int, secondNum: Int, thirdNum: Int, fourthNum: Int) {

        val numbersGuessed: MutableList<Int> = arrayListOf<Int>()
        numbersGuessed.addAll(listOf(firstNum, secondNum, thirdNum, fourthNum))

        Log.i("GameViewModel", "CORRECT Numbers: ${generatedNumbers.value}")
        Log.i("GameViewModel", "GUESSED Numbers: $numbersGuessed")

        var guessedCorrectly = 0
        var correctPosition = 0

        // check correct position
        for (i in 0..3) {
            if (numbersGuessed[i] == generatedNumbers.value!![i]) {
                correctPosition += 1
            }
        }
        // check guessed correctly
        for (item in numbersGuessed) {
            if (generatedNumbers.value!!.contains(item)) {
                guessedCorrectly += 1
            }
        }
        _message.value = "${guessedCorrectly}:${correctPosition}"

        _guessedCorrectlyLD.value = guessedCorrectly.toString()
        _correctPositionLD.value = correctPosition.toString()


        if (correctPosition == 4) {
            _eventGameFinish.value = true
        }

        Log.i("GameViewModel", "GuessedCorrectlyLD: ${guessedCorrectlyLD.value}")
        Log.i("GameViewModel", "CorrectPositionLD: ${correctPositionLD.value}")

        Log.i("GameViewModel", "GuessedCorrectly: $guessedCorrectly")
        Log.i("GameViewModel", "CorrectPosition: $correctPosition")

    }
}

/*
//numbers.addAll(listOf(1, 2, 3, 6))
Log.i("GameViewModel", "${numbers[0]}")
Log.i("GameViewModel", "${numbers[1]}")
Log.i("GameViewModel", "${numbers[2]}")
Log.i("GameViewModel", "${numbers[3]}")*/