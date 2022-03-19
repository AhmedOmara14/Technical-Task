package com.task.football_league.presentation.all_competitions.adapter

import com.task.football_league.domain.model.competitions_team.CompetitionTeam

interface AllCompetitionsInterface {
    fun showDetailsOfCompetition(id: Int?, available: Boolean?, data: CompetitionTeam?)
    fun insertData(data: CompetitionTeam?)
}