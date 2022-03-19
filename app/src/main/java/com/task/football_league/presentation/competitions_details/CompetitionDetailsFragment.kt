package com.task.football_league.presentation.competitions_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.task.football_league.databinding.FragmentCompetitionDetailsBinding
import com.task.football_league.databinding.ItemTeamBinding
import com.task.football_league.di.PreferenceModule
import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.competitions_team.Team
import com.task.football_league.presentation.competitions_details.adapter.CompetitionTeamInterface
import com.task.football_league.presentation.competitions_details.adapter.CompetitionsTeamsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CompetitionDetailsFragment : Fragment(), CompetitionTeamInterface {
    @set:Inject
    internal var preferenceModule: PreferenceModule? = null
    @set:Inject
    internal var networkHelper: NetworkHelper? = null
    private var viewModel: CompetitionsDetailsViewModel? = null
    private var loadingDialog: LoadingDialog? = null
    private var binding: FragmentCompetitionDetailsBinding? = null
    private var navController: NavController? = null
    private lateinit var id: String
    private lateinit var competitionTeam: CompetitionTeam
    private lateinit var competitionsTeamsAdapter: CompetitionsTeamsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompetitionDetailsBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        getCompetitionsDetails()
        getTeam()
        binding?.ivBack?.setOnClickListener {
            navController!!.navigate(CompetitionDetailsFragmentDirections.actionCompetitionDetailsFragmentToAllCompetitionsFragment())
        }
    }
    private fun getTeam() {
        if (competitionTeam.teams.isNullOrEmpty()) {
            binding?.tvNoTeams?.visibility = View.VISIBLE
            binding?.rvCompetitions?.visibility = View.INVISIBLE
        } else {
            binding?.tvNoTeams?.visibility = View.INVISIBLE
            binding?.rvCompetitions?.visibility = View.VISIBLE
            competitionsTeamsAdapter.setList(competitionTeam.teams)
            competitionsTeamsAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecycler() {
        val recyclerView: RecyclerView = binding!!.rvCompetitions
        recyclerView.adapter = competitionsTeamsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    private fun getCompetitionsDetails() {
        preferenceModule?.let { it ->
            viewModel?.getCompetitionsById(it.token, id)
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
                            val data: CompetitionDetails? = response.data
                            binding?.competitionDetails = data

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


    private fun initComponent() {
        viewModel = ViewModelProvider(this).get(CompetitionsDetailsViewModel::class.java)
        loadingDialog = LoadingDialog()
        loadingDialog!!.isCancelable = false
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        id = arguments?.let { CompetitionDetailsFragmentArgs.fromBundle(it).id }.toString()
        competitionTeam =
            arguments?.let { CompetitionDetailsFragmentArgs.fromBundle(it).comptetionTeam }!!
        competitionsTeamsAdapter = CompetitionsTeamsAdapter()
        competitionsTeamsAdapter.setContext(context)
        competitionsTeamsAdapter.setListener(this)
        setUpRecycler()
    }

    @SuppressLint("CheckResult")
    override fun showImageTeam(response: Team?, binding: ItemTeamBinding) {
        Thread {
            activity?.runOnUiThread {
                if (response?.crestUrl?.isNotEmpty()!!) {
                    context?.let { it1 ->
                        HelperClass.fetchSvg(
                            it1,
                            response.crestUrl,
                            binding.ivTeam
                        )
                    }
                }
            }
        }.start()

    }

    override fun getTeamPlayer(response: Team?) {
        val navDirections: NavDirections =
            response?.let {
                CompetitionDetailsFragmentDirections.actionCompetitionDetailsFragmentToAllTeamPlayersFragment(
                    id, competitionTeam, it
                )
            }!!
        navController!!.navigate(navDirections)
    }

}