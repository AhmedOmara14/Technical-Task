package com.task.football_league.presentation.all_competitions.adapter

import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.common.Resource
import com.task.football_league.databinding.ItemCompetitionBinding
import com.task.football_league.di.PreferenceModule
import com.task.football_league.domain.model.competitions.Competition
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.presentation.all_competitions.AllCompetitionsViewModel
import java.util.*
import kotlin.collections.ArrayList

class AllCompetitionsAdapter : RecyclerView.Adapter<AllCompetitionsAdapter.viewHolder>() {
    private var listCompetition: ArrayList<Competition>? = null
    private var context: LifecycleOwner? = null
    private var listener: AllCompetitionsInterface? = null
    private var viewModel: AllCompetitionsViewModel? = null
    private var preferenceModule: PreferenceModule? = null

    fun setList(list: ArrayList<Competition>?) {
        this.listCompetition = list
    }

    fun setViewModel(
        viewModel: AllCompetitionsViewModel?,
        preferenceModule: PreferenceModule?,
        context: LifecycleOwner?
    ) {
        this.viewModel = viewModel
        this.preferenceModule = preferenceModule
        this.context = context
    }

    fun setListener(listener: AllCompetitionsInterface?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemCompetitionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bindMain(listCompetition?.get(position))
        holder.setIsRecyclable(false)
        holder.bind(
            listCompetition?.get(position),
            preferenceModule,
            viewModel,
            context,
            listener,
            false
        )
        holder.itemView.setOnClickListener {
            holder.bind(
                listCompetition?.get(position),
                preferenceModule,
                viewModel,
                context,
                listener,
                true
            )
        }
    }

    override fun getItemCount(): Int {
        return if (listCompetition != null) listCompetition!!.size else 0
    }


    class viewHolder(
        val binding: ItemCompetitionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            response: Competition?,
            preferenceModule: PreferenceModule?,
            viewModel: AllCompetitionsViewModel?,
            context: LifecycleOwner?,
            listener: AllCompetitionsInterface?,
            getDetails: Boolean?
        ) {
            binding.ivDropDown.setOnClickListener {
                val visible =
                    if (binding.constraintLayoutDetails.visibility == View.GONE) View.VISIBLE else View.GONE
                TransitionManager.beginDelayedTransition(
                    binding.constraintLayoutDetails,
                    AutoTransition()
                )
                binding.constraintLayoutDetails.visibility = visible
                binding.ivDropUP.visibility = visible
                if (visible == View.VISIBLE) binding.ivDropDown.visibility = View.GONE
                else binding.ivDropDown.visibility = View.VISIBLE

                preferenceModule?.token?.let {
                    if (context != null) {
                        viewModel?.getTeamById(it, response?.id.toString())
                            ?.observe(context, androidx.lifecycle.Observer {
                                when (it) {
                                    is Resource.Success -> {
                                        if (it.data != null) {
                                            binding.tvTeamNumber.text = it.data.count.toString()
                                            response?.isAvailable = true
                                        } else {
                                            binding.tvTeamNumber.text = "Available For Premium"
                                            response?.isAvailable = false
                                        }
                                    }
                                    is Resource.Error -> {
                                        binding.tvTeamNumber.text = "Available For Premium"
                                    }
                                }
                            })
                    }
                }
            }

            binding.ivFavoriteInActive.setOnClickListener {
                preferenceModule?.token?.let { it ->
                    if (context != null) {
                        viewModel?.getTeamById(it, response?.id.toString())
                            ?.observe(context, androidx.lifecycle.Observer {
                                when (it) {
                                    is Resource.Success -> {
                                        listener?.insertData(it.data)
                                        if (it.data != null) {
                                            binding.ivFavoriteActive.visibility = View.VISIBLE
                                            binding.ivFavoriteInActive.visibility = View.INVISIBLE
                                        } else {
                                            binding.ivFavoriteActive.visibility = View.INVISIBLE
                                            binding.ivFavoriteInActive.visibility = View.VISIBLE
                                        }
                                    }
                                }
                            })
                    }
                }
            }

            binding.ivDropUP.setOnClickListener {
                val visible =
                    if (binding.constraintLayoutDetails.visibility == View.GONE) View.VISIBLE else View.GONE
                TransitionManager.beginDelayedTransition(
                    binding.constraintLayoutDetails,
                    AutoTransition()
                )
                binding.constraintLayoutDetails.visibility = visible
                binding.ivDropUP.visibility = visible
                if (visible == View.VISIBLE) binding.ivDropDown.visibility = View.GONE
                else {
                    binding.ivDropDown.visibility = View.VISIBLE
                }
            }

            if (getDetails == true) {
                preferenceModule?.token?.let { it ->
                    if (context != null) {
                        viewModel?.getTeamById(it, response?.id.toString())
                            ?.observe(context, androidx.lifecycle.Observer {
                                when (it) {
                                    is Resource.Success -> {
                                        if (getDetails == true) {
                                            response?.isAvailable = it.data != null
                                            listener?.showDetailsOfCompetition(
                                                response?.id,
                                                response?.isAvailable,
                                                it.data
                                            )
                                        }
                                    }
                                    is Resource.Error -> {
                                        binding.tvTeamNumber.text = "Available For Premium"
                                    }
                                }
                            })
                    }
                }
            }
        }

        fun bindMain(
            response: Competition?
        ) {
            binding.competition = response
        }

    }
}