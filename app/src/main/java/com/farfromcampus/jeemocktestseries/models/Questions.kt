package com.farfromcampus.jeemocktestseries.models

import java.io.Serializable
class Test:Serializable{
    var Set:ArrayList<Questions> = ArrayList(100)
    var AnswerSheet:ArrayList<Char> = ArrayList(100)
    var subject:ArrayList<Int> = ArrayList(3)
    var mock_id:String = ""
}
class Questions{
    var question:String=""
    var option:ArrayList<String> = ArrayList(5)
    var image:String=""
    var answer:Char = ' '
    var answer_img: String=""
    var solution:String=""
    var ques_id:String=""
    var subject_id:Int=-1
}
