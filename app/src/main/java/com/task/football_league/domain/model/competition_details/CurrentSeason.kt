package com.task.football_league.domain.model.competition_details

data class CurrentSeason(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Winner
)