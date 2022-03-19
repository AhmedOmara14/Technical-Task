package com.task.football_league.presentation.all_team_players.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.databinding.ItemPlayersBinding
import com.task.football_league.domain.model.team_players.Squad

class TeamPlayersAdapter : RecyclerView.Adapter<TeamPlayersAdapter.viewHolder>() {
    private var listPlayers: List<Squad>? = null
    fun setList(list: List<Squad>?) {
        this.listPlayers = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemPlayersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(listPlayers?.get(position))
    }

    override fun getItemCount(): Int {
        return if (listPlayers != null) listPlayers!!.size else 0
    }

    class viewHolder(
        private val binding: ItemPlayersBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            response: Squad?
        ) {
            binding.player = response
        }

    }
}