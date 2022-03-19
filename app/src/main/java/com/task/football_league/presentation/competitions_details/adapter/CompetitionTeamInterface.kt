package com.task.football_league.presentation.competitions_details.adapter

import com.task.football_league.databinding.ItemTeamBinding
import com.task.football_league.domain.model.competitions_team.Team

interface CompetitionTeamInterface {
    fun showImageTeam(response: Team?, binding: ItemTeamBinding)

    fun getTeamPlayer(response: Team?)

}