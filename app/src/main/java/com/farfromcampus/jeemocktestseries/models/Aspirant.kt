package com.farfromcampus.jeemocktestseries.models

class Aspirant(
              val name:String="",

              val attempt_tests:ArrayList<Int> = ArrayList(),
              val Accuracy:Int=0,
              val wrong_answer:Int=0,
              val bookmarks:ArrayList<Int> = ArrayList()
)