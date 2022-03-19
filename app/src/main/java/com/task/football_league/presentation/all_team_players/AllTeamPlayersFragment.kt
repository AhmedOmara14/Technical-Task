package com.task.football_league.presentation.all_team_players

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.R
import com.task.football_league.common.HelperClass
import com.task.football_league.common.LoadingDialog
import com.task.football_league.common.NetworkHelper
import com.task.football_league.common.Resource
import com.task.football_league.databinding.FragmentAllTeamPlayersBinding
import com.task.football_league.di.PreferenceModule
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.competitions_team.Team
import com.task.football_league.domain.model.team_players.Squad
import com.task.football_league.domain.model.team_players.TeamPlayers
import com.task.football_league.presentation.all_team_players.adapter.TeamPlayersAdapter
import com.task.football_league.presentation.competitions_details.CompetitionDetailsFragmentArgs
import com.task.football_league.presentation.competitions_details.CompetitionsDetailsViewModel
import com.task.football_league.presentation.competitions_details.adapter.CompetitionsTeamsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AllTeamPlayersFragment : Fragment() {

    @set:Inject
    internal var preferenceModule: PreferenceModule? = null

    @set:Inject
    internal var networkHelper: NetworkHelper? = null
    private var viewModel: AllTeamPlayersViewModel? = null
    private var loadingDialog: LoadingDialog? = null
    private var binding: FragmentAllTeamPlayersBinding? = null
    private var navController: NavController? = null
    private lateinit var id: String
    private lateinit var competitionTeam: CompetitionTeam
    private lateinit var team: Team
    private lateinit var teamPlayersAdapter: TeamPlayersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTeamPlayersBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        getTeamDetails()

        getPlayers()
        binding?.ivBack?.setOnClickListener {
            val navDirections: NavDirections =
                AllTeamPlayersFragmentDirections.actionAllTeamPlayersFragmentToCompetitionDetailsFragment(
                    id, competitionTeam
                )
            navController!!.navigate(navDirections)
        }
    }

    private fun getPlayers() {
        preferenceModule?.let { it ->
            viewModel?.getPlayers(it.token, id)
                ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
                    when (response) {
                        is Resource.Loading -> {
                            activity?.supportFragmentManager?.let {
                                loadingDialog?.show(
                                    it,
                                    "loading_dialog"
                                )
                            }
                        }
                        is Resource.Success -> {
                            loadingDialog?.cancelDialog()
                            loadingDialog?.dismiss()
                            val data: TeamPlayers? = response.data
                            data?.squad?.let { it1 -> getPlayer(it1) }

                        }
                        is Resource.Error -> {
                            loadingDialog?.cancelDialog()
                            loadingDialog?.dismiss()
                            HelperClass.showToast(context, response.message)
                        }
                    }
                })
        }

    }
    private fun getPlayer(players:List<Squad>) {
        if (players.isNullOrEmpty()) {
            HelperClass.showToast(context,"No Team Available")
            binding?.tvNoTeams?.visibility = View.VISIBLE
            binding?.rvPlayers?.visibility = View.INVISIBLE
        } else {
            binding?.tvNoTeams?.visibility = View.INVISIBLE
            binding?.rvPlayers?.visibility = View.VISIBLE
            teamPlayersAdapter.setList(players)
            teamPlayersAdapter.notifyDataSetChanged()
        }
    }

    private fun getTeamDetails() {
        binding?.competitionTeam = team
        showImageTeam(team)
    }

    private fun setUpRecycler() {
        val recyclerView: RecyclerView = binding!!.rvPlayers
        recyclerView.adapter = teamPlayersAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }


    private fun initComponent() {
        viewModel = ViewModelProvider(this).get(AllTeamPlayersViewModel::class.java)
        loadingDialog = LoadingDialog()
        loadingDialog!!.isCancelable = false
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        id = arguments?.let { AllTeamPlayersFragmentArgs.fromBundle(it).id }.toString()
        competitionTeam =
            arguments?.let { AllTeamPlayersFragmentArgs.fromBundle(it).competitionTeam }!!
        team = arguments?.let { AllTeamPlayersFragmentArgs.fromBundle(it).specificTeam }!!
        teamPlayersAdapter = TeamPlayersAdapter()
        setUpRecycler()
    }

    fun showImageTeam(response: Team?) {
        Thread {
            activity?.runOnUiThread {
                if (response?.crestUrl?.isNotEmpty()!!) {
                    context?.let { it1 ->
                        binding?.ivTeam?.let {
                            HelperClass.fetchSvg(
                                it1,
                                response.crestUrl,
                                it
                            )
                        }
                    }
                }
            }
        }.start()

    }

}