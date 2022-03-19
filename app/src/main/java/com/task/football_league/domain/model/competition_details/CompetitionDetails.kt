package com.task.football_league.domain.model.competition_details

data class CompetitionDetails(
    val area: Area,
    val code: String,
    val currentSeason: CurrentSeason,
    val emblemUrl: Any,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val plan: String,
    val seasons: List<Season>
)