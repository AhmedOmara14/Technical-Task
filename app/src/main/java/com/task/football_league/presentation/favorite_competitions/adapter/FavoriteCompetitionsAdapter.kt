package com.task.football_league.presentation.favorite_competitions.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.databinding.ItemFavoriteBinding
import com.task.football_league.domain.model.competitions_team.CompetitionTeam

class FavoriteCompetitionsAdapter : RecyclerView.Adapter<FavoriteCompetitionsAdapter.viewHolder>() {
    private var listCompetition: List<CompetitionTeam>? = null
    private var adapter: FavCompetitionsTeamsAdapter? = null
    private var context: Context? = null

    fun setList(list: List<CompetitionTeam>?) {
        this.listCompetition = list
    }

    fun setContext(context: Context?) {
        this.context = context
    }


    fun setTeamAdapter(adapter: FavCompetitionsTeamsAdapter?) {
        this.adapter = adapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return adapter?.let {
            viewHolder(
                ItemFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), it,context
            )
        }!!
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bindMain(listCompetition?.get(position))
    }

    override fun getItemCount(): Int {
        return if (listCompetition != null) listCompetition!!.size else 0
    }

    class viewHolder(
        val binding: ItemFavoriteBinding,
        val adapter: FavCompetitionsTeamsAdapter,
        var context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindMain(
            response: CompetitionTeam?
        ) {
            binding.competition = response
            val recyclerView: RecyclerView = binding.rvTeamFav
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            if (response?.teams?.isNotEmpty() == true) {
                binding.rvTeamFav.visibility = View.VISIBLE
                binding.tvNoTeams.visibility = View.INVISIBLE
                adapter.setList(response.teams)
                adapter.notifyDataSetChanged()
            } else {
                binding.rvTeamFav.visibility = View.INVISIBLE
                binding.tvNoTeams.visibility = View.VISIBLE
            }
        }

    }
}