package com.task.football_league.presentation.competitions_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.databinding.ItemTeamBinding
import com.task.football_league.domain.model.competitions_team.Team

class CompetitionsTeamsAdapter : RecyclerView.Adapter<CompetitionsTeamsAdapter.viewHolder>() {
    private var listTeams: List<Team>? = null
    private var context: Context? = null
    private var competitionTeamInterface: CompetitionTeamInterface? = null


    fun setList(list: List<Team>?) {
        this.listTeams = list
    }
    fun setListener(competitionTeamInterface: CompetitionTeamInterface?) {
        this.competitionTeamInterface = competitionTeamInterface
    }

    fun setContext(context: Context?) {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return context?.let {
            viewHolder(
                ItemTeamBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }!!
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bindMain(listTeams?.get(position),competitionTeamInterface)
        holder.itemView.setOnClickListener{
            competitionTeamInterface?.getTeamPlayer(listTeams?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return if (listTeams != null) listTeams!!.size else 0
    }

    class viewHolder(
        private val binding: ItemTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMain(
            response: Team?,
            competitionTeamInterface: CompetitionTeamInterface?
        ) {
            competitionTeamInterface?.showImageTeam(response,binding)
            binding.competitionTeam = response
        }

    }
}