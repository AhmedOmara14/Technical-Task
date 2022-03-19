package com.task.football_league.presentation.all_team_players

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.football_league.common.Resource
import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.team_players.TeamPlayers
import com.task.football_league.domain.repository.MyRepository


class AllTeamPlayersViewModel @ViewModelInject constructor(myRepository: MyRepository) :
    ViewModel() {
    protected val repository: MyRepository
    fun getPlayers(token: String, id: String): LiveData<Resource<TeamPlayers?>?>? {
        return repository.getTeamPlayers(token, id)
    }

    init {
        repository = myRepository
    }
}