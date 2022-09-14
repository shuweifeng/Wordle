package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    // author: calren
    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        private val fourLetterWords = "Size,Land"
        // val fourLetterWords =
        //    "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        private fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }

    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()


    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


    private fun changeColor(guess: String, check: String) : SpannableString {
        val ss = SpannableString(guess)
        val red = ForegroundColorSpan(Color.RED)
        val green = ForegroundColorSpan(Color.GREEN)
        val gray = ForegroundColorSpan(Color.GRAY)
        for (i in 0..3) {
            if (check[i] == 'O') {
                ss.setSpan(green, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if (check[i] == '+') {
                ss.setSpan(red, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                ss.setSpan(gray, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        return ss
    }


    private fun setColor(guess: String, check: String) : String {
        var result = ""
        for (i in 0..3) {
            if (check[i] == 'O') {
                result += "<front color=#00FF00>" + guess[i] + "</front>"
            } else if (check[i] == '+') {
                result += "<front color=#FF0000>" + guess[i] + "</front>"
            } else {
                result += "<front color=#808080>" + guess[i] + "</front>"
            }
        }

        return result
    }


    private fun isValidInput(guess: String) : Boolean {
        var result = true
        if (guess.length != 4) {
            result = false
        }
        if (!guess.all {it.isLetter()}) {
            result = false
        }
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)
        val textViewGroup1 = arrayOf(findViewById<TextView>(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4))

        val textViewGroup2 = arrayOf(findViewById<TextView>(R.id.textView5),
            findViewById(R.id.textView6),
            findViewById(R.id.textView7),
            findViewById(R.id.textView8))
        val textViewGroup3= arrayOf(findViewById<TextView>(R.id.textView9),
            findViewById(R.id.textView10),
            findViewById(R.id.textView11),
            findViewById(R.id.textView12))

        val textViewGroups = arrayOf(textViewGroup1, textViewGroup2, textViewGroup3)
        val answerTextView = findViewById<TextView>(R.id.textView13)

        var check = ""
        var count = 0
        var guessString= ""

        button.setOnClickListener {
            if (button.text.equals("reset")) {
                wordToGuess = FourLetterWordList.getRandomFourLetterWord()
                count = 0
                answerTextView.visibility = View.INVISIBLE
                button.text = "guess"
                editText.hint = "Enter 4 letter guess here"
                for (tvg in textViewGroups) {
                    for (tv in tvg) {
                        tv.visibility = View.INVISIBLE
                    }
                }

            } else {
                guessString = editText.text.toString()
                if (isValidInput(guessString)) {
                    guessString = guessString.uppercase()
                    check = checkGuess(guessString)
                    editText.getText().clear()
                    count += 1

                    textViewGroups[count - 1][2].text = guessString
                    // textViewGroups[count - 1][3].setText(Html.fromHtml(guessString))
                    textViewGroups[count - 1][3].text = check

                    for (tv in textViewGroups[count - 1]) {
                        tv.visibility = View.VISIBLE
                    }

                    if (count == 3 || check.equals("OOOO")) {
                        answerTextView.text = wordToGuess
                        answerTextView.visibility = View.VISIBLE
                        button.text = "reset"
                        editText.hint = "Click Reset Button"
                    }
                } else {
                    Toast.makeText(it.context, "Invalid Input!", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}


