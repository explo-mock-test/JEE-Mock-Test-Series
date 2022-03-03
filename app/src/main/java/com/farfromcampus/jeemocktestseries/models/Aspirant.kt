package com.farfromcampus.jeemocktestseries.models

class Aspirant(
              val name:String,
              val attempt_tests:ArrayList<Int> = ArrayList(),
              val correct_answer:Int,
              val wrong_answer:Int,
              val bookmarks:ArrayList<Int> = ArrayList()
)