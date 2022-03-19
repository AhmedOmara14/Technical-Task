package com.task.football_league.presentation.favorite_competitions.adapter

import com.task.football_league.databinding.ItemFavTeamBinding
import com.task.football_league.databinding.ItemTeamBinding
import com.task.football_league.domain.model.competitions_team.Team

interface FavCompetitionTeamInterface {
    fun showImageTeam(response: Team?, binding: ItemFavTeamBinding)

}