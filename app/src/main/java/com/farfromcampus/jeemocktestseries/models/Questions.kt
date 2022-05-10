package com.farfromcampus.jeemocktestseries.models

import java.io.Serializable

//@Parcelize
//class Test(
//    var Set: ArrayList<@RawValue Questions> = ArrayList(100),
//    var AnswerSheet: ArrayList<String> = ArrayList(100),
//    var subject: Array<Int> = Array(3){-1},
//    var mock_id: String = ""
//) : Parcelable
class Test:Serializable {
    var Set: ArrayList<Questions> = ArrayList(100)
    var AnswerSheet: Array<String> = Array(100){""}
    var subject: Array<Int> = Array(3){-1}
    var mock_id: String = ""
    var marks:Array<Int> = Array(6){0} //0->total marks 1->physics 2->chemistry 3->maths 4->wrong answer 5->total attempt
}

class Questions:Serializable{
    var question:String=""
//    var idx:Int=-1
//    var level:Int=-1           //1->Easy  2->Medium  3->Hard
//    var type:String=""         //Integer Match-Column MCQ
    var option:ArrayList<String> = ArrayList(5)
    var image:String=""
    var answer:String = ""
    var answer_img: String=""
    var solution:String=""
    var ques_id:String=""
    var subject_id:Int=-1      //0->Physics 1->Chemistry 2->Mathematics 3->Other
}
