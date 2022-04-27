package com.farfromcampus.jeemocktestseries.models

import java.io.Serializable

class Mocktest:Serializable {
    var ques_ids: ArrayList<String> = ArrayList(30)
    var mock_id: String = ""
    var testtype: String = ""
    var test_number: Int = -1
    var name: String = ""
    var time: Int = 3
}