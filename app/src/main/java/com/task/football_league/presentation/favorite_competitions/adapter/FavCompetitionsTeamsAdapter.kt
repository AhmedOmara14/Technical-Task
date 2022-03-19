package com.task.football_league.presentation.favorite_competitions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.databinding.ItemFavTeamBinding
import com.task.football_league.databinding.ItemTeamBinding
import com.task.football_league.domain.model.competitions_team.Team

class FavCompetitionsTeamsAdapter : RecyclerView.Adapter<FavCompetitionsTeamsAdapter.viewHolder>() {
    private var listTeams: List<Team>? = null
    private var listener: FavCompetitionTeamInterface? = null


    fun setList(list: List<Team>?) {
        this.listTeams = list
    }

    fun setListener(listener: FavCompetitionTeamInterface?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemFavTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener = listener
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bindMain(listTeams?.get(position))
    }

    override fun getItemCount(): Int {
        return if (listTeams != null) listTeams!!.size else 0
    }

    class viewHolder(
        private val binding: ItemFavTeamBinding, var listener: FavCompetitionTeamInterface?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindMain(
            response: Team?
        ) {
            listener?.showImageTeam(response, binding)
            binding.competitionTeam = response
        }

    }
}