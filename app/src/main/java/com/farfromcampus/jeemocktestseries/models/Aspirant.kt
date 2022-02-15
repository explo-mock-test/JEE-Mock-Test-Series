package com.farfromcampus.jeemocktestseries.models

class Aspirant(val std_id:String="",
              val name:String,
              val attempt_tests:Int,
              val correct_answer:Int,
              val wrong_answer:Int,
              val bookmarks:List<Int>
)