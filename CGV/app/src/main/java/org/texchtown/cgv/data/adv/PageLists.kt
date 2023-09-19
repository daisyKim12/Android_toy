package org.texchtown.cgv.data.adv

import org.texchtown.cgv.R
import org.texchtown.cgv.data.Talk
import org.texchtown.cgv.data.adv.BottomAdv
import org.texchtown.cgv.data.adv.TopAdv

object PageLists {

    val homeAdvSlides = listOf(
        TopAdv(R.drawable.home_rv_adv1),
        TopAdv(R.drawable.home_rv_adv2),
        TopAdv(R.drawable.home_rv_adv3)
    )

    val bottomSlides = listOf(
        BottomAdv(R.drawable.bottom_sheet_vp1),
        BottomAdv(R.drawable.bottom_sheet_vp2),
        BottomAdv(R.drawable.bottom_sheet_vp3)
    )

    val eventAdvSlides = listOf(
        TopAdv(R.drawable.event_adv1),
        TopAdv(R.drawable.event_adv2),
        TopAdv(R.drawable.event_adv3),
        TopAdv(R.drawable.event_adv4),
        TopAdv(R.drawable.event_adv5),
        TopAdv(R.drawable.event_adv6),
        TopAdv(R.drawable.event_adv7)
    )

    val eventSlides = listOf(
        BottomAdv(R.drawable.event_rv1),
        BottomAdv(R.drawable.event_rv2),
        BottomAdv(R.drawable.event_rv3),
        BottomAdv(R.drawable.event_rv4),
        BottomAdv(R.drawable.event_rv5),
        BottomAdv(R.drawable.event_rv6)
    )

    val snackSlides = listOf(
        BottomAdv(R.drawable.fast_rv_snack1),
        BottomAdv(R.drawable.fast_rv_snack2),
        BottomAdv(R.drawable.fast_rv_snack3)
    )

    val groupList = listOf(
        Group("영화관람권"),
        Group("기프트카드"),
        Group("음료"),
        Group("스낵")
    )

    val giftSlide = listOf(
        Gift(R.drawable.gift_rv_ticket1, "CGV 영화 관람권", "즐거운 경험은 CGV에서!", "12,000원"),
        Gift(R.drawable.gift_rv_ticket2, "CGV 골드클래스", "최고의 관람환경을 제공하는 프리미엄 상영관", "40,000원"),
        Gift(R.drawable.gift_rv_ticket3, "4DX관람관", "오감만족 영화 속 주인공 되기", "19,000원"),

        Gift(R.drawable.gift_rv_card1, "PACONNIE A형", "충전형 선불 카드", "금액충전형"),
        Gift(R.drawable.gift_rv_card2, "PACONNIE B형", "충전형 선불 카드", "금액충전형"),
        Gift(R.drawable.gift_rv_card3, "PACONNIE C형", "충전형 선불 카드", "금액충전형"),

        Gift(R.drawable.gift_rv_drink1, "탄산음료(M)", "시원한 탄산음료와 함께 스트레스도 날리세요", "2,500원"),
        Gift(R.drawable.gift_rv_drink2, "아메리카노(HOT)", "현대인의 필수품", "3,500원"),
        Gift(R.drawable.gift_rv_drink3, "아메리카노(COLD)", "현대인의 필수품", "4,000원"),

        Gift(R.drawable.gift_rv_snack1, "칠리치즈나쵸", "바삭바삭 나쵸, 얼마나 맛있게요?", "4,900원"),
        Gift(R.drawable.gift_rv_snack2, "플레인핫도그", "플레인핫도그", "4,500원"),
        Gift(R.drawable.gift_rv_snack3, "오징어(완제품)", "한봉지로는 모자라", "3,500원")
    )

    val moviePosterSlide = listOf<String>(
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/2238/223800_P09_101811.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/0749/74977_P61_170924.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/1845/184513_P20_164436.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/1873/187311_P30_103719.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/1845/184509_P59_145214.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/2257/225700_P24_142906.jpg",
        "http://imgnews.naver.net/image/438/2023/01/18/0000052895_007_20230118114802231.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/2154/215466_P01_135602.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/2245/224582_P01_151006.jpg",
        "https://ssl.pstatic.net/imgmovie/mdi/mit110/2249/224929_P01_095650.jpg"
    )

    val actorList = listOf<List<String>>(
        listOf("이노우에 다케히코"),
        listOf("제인스 카메론", "조 샐다나", "샘 워신턴", "시고니 위버"),
        listOf("황정민", "현빈", "강기영")

    )

    val movieTalkSlide = listOf(
        Talk("https://ssl.pstatic.net/imgmovie/mdi/mit110/2238/223800_P09_101811.jpg", "농구", "DTsBNob9gNY"),
        Talk("https://ssl.pstatic.net/imgmovie/mdi/mit110/0749/74977_P61_170924.jpg", "아바타", "oor75itYFbs")
    )

    val trailerSlide = listOf<String>(
        "DTsBNob9gNY",
        "oor75itYFbs"
    )
}