package com.task.football_league.domain.model.competitions

data class Competitions_Response(
    val competitions: List<Competition>,
    val count: Int
)