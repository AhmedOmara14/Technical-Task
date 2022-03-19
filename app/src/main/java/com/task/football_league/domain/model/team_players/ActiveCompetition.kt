package com.task.football_league.domain.model.team_players

data class ActiveCompetition(
    val area: Area,
    val code: String,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val plan: String
)