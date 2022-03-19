package com.task.football_league.presentation.competitions_details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.football_league.common.Resource
import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.repository.MyRepository


class CompetitionsDetailsViewModel @ViewModelInject constructor(myRepository: MyRepository) :
    ViewModel() {
    protected val repository: MyRepository
    fun getCompetitionsById(token: String, id: String): LiveData<Resource<CompetitionDetails?>?>? {
        return repository.getCompetitionsById(token, id)
    }

    init {
        repository = myRepository
    }
}