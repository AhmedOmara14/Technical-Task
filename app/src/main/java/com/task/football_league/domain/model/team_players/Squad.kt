package com.task.football_league.domain.model.team_players

data class Squad(
    val countryOfBirth: String,
    val dateOfBirth: String,
    val id: Int,
    val name: String,
    val nationality: String,
    val position: String,
    val role: String,
    val shirtNumber: Any
)