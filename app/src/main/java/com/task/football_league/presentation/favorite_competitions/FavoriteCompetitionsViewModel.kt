package com.task.football_league.presentation.favorite_competitions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.repository.MyRepository


class FavoriteCompetitionsViewModel @ViewModelInject constructor(myRepository: MyRepository) :
    ViewModel() {
    protected val repository: MyRepository
    init { repository = myRepository }
    fun getFavCompetitions(): MutableLiveData<List<CompetitionTeam>> {
        return repository.getFavCompetitions()
    }
}