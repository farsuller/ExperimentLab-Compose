package com.compose.experiment.data

import com.compose.experiment.utils.Constants.COLLABERA
import com.compose.experiment.utils.Constants.EMAPTA
import com.compose.experiment.utils.Constants.FPT
import com.compose.experiment.utils.Constants.YONDU
import com.compose.experiment.utils.Constants.ZIGZAG
import com.compose.experiment.utils.Res


enum class Experience(
    val id: Int,
    val jobPosition: String,
    val description:List<String>,
    val company: String,
    val image: String,
    val from: String,
    val to: String,
) {
    First(
        id = 1,
        jobPosition = "Jr. Mobile Application Developer",
        description = ZIGZAG,
        company = "Zigzag OffShoring",
        image = Res.Image.work1,
        from = "Mar. 2017",
        to = "Dec. 2020",
    ),
    Second(
        id = 2,
        jobPosition = "Jr. Mobile Application Developer",
        description = EMAPTA,
        company = "EMAPTA",
        image = Res.Image.work2,
        from = "Jan. 2020",
        to = "Sept. 2020",
    ),
    Third(
        id = 3,
        jobPosition = "(Software Engineer) Android Mobile Developer",
        description = YONDU,
        company = "Yondu Inc.",
        image = Res.Image.work3,
        from = "Sept. 2020",
        to = "Feb. 2022",
    ),
    Fourth(
        id = 3,
        jobPosition = "Android Developer",
        description = COLLABERA,
        company = "Collabera Digital",
        image = Res.Image.work4,
        from = "Feb. 2022",
        to = "Jan. 2024",
    ),
    Fifth(
        id = 4,
        jobPosition = "Software Developer Engineer",
        description = FPT,
        company = "FPT Software Philippines Corp.",
        image = Res.Image.work5,
        from = "Feb. 2024",
        to = "Present"
    ),
}
