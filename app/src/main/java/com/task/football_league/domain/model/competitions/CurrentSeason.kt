package com.task.football_league.domain.model.competitions

data class CurrentSeason(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Any
)