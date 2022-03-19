package com.task.football_league.presentation.all_competitions

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.task.football_league.databinding.FragmentAllCompetitionsBinding
import com.task.football_league.di.PreferenceModule
import com.task.football_league.domain.model.competitions.Competition
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.presentation.all_competitions.adapter.AllCompetitionsAdapter
import com.task.football_league.presentation.all_competitions.adapter.AllCompetitionsInterface
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllCompetitionsFragment : Fragment(), AllCompetitionsInterface {
    @set:Inject
    internal var preferenceModule: PreferenceModule? = null
    @set:Inject
    internal var networkHelper: NetworkHelper? = null
    private var viewModel: AllCompetitionsViewModel? = null
    private var loadingDialog: LoadingDialog? = null
    private var binding: FragmentAllCompetitionsBinding? = null
    private var allCompetitionsAdapter: AllCompetitionsAdapter? = null
    private var navController: NavController? = null
    private var favCompetitionsList: List<CompetitionTeam>? = null
    private var competitionsList: ArrayList<Competition>? = null
    private var competitionsListFilter: ArrayList<Competition>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCompetitionsBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        setUpRecycler()
        getCompetitions()

        binding?.searchViewFilter?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                competitionsListFilter?.clear()
                competitionsList?.forEach {
                    if (it.name?.contains(query) == true) {
                        competitionsListFilter!!.add(it)
                    }
                }
                setData(competitionsListFilter)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                competitionsListFilter?.clear()
                competitionsList?.forEach {
                    if (it.name?.contains(newText) == true)
                        competitionsListFilter!!.add(it)
                }
                setData(competitionsListFilter)
                return false
            }
        })

    }

    private fun initComponent() {
        viewModel = ViewModelProvider(this).get(AllCompetitionsViewModel::class.java)
        allCompetitionsAdapter = AllCompetitionsAdapter()
        competitionsList = ArrayList()
        competitionsListFilter = ArrayList()
        allCompetitionsAdapter!!.setListener(this)
        setUpRecycler()
        loadingDialog = LoadingDialog()
        loadingDialog!!.isCancelable = false
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    private fun setUpRecycler() {
        val recyclerView: RecyclerView = binding!!.rvCompetitions
        recyclerView.adapter = allCompetitionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    private fun getCompetitions() {
        allCompetitionsAdapter?.setViewModel(viewModel, preferenceModule, viewLifecycleOwner)
        preferenceModule?.token?.let {
            viewModel?.getCompetitions(it)
                ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
                    when (response) {
                        is Resource.Loading -> {
                            activity?.supportFragmentManager?.let { it ->
                                loadingDialog?.show(
                                    it,
                                    "loading_dialog"
                                )
                            }
                        }
                        is Resource.Success -> {
                            loadingDialog?.cancelDialog()
                            loadingDialog?.dismiss()
                            val data: Competitions_Response? = response.data
                            competitionsList =
                                response.data?.competitions as ArrayList<Competition>?
                            setData(data?.competitions as ArrayList<Competition>?)

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

    private fun setData(competitions: ArrayList<Competition>?) {
        allCompetitionsAdapter?.setList(competitions)
        allCompetitionsAdapter?.notifyDataSetChanged()
    }


    override fun showDetailsOfCompetition(id: Int?, available: Boolean?, data: CompetitionTeam?) {
        if (!available!!)
            HelperClass.showToast(context, "This is Premium")
        else {
            val navDirections: NavDirections =
                data?.let {
                    AllCompetitionsFragmentDirections.actionAllCompetitionsFragmentToCompetitionDetailsFragment(
                        id.toString(), it
                    )
                }!!
            navController!!.navigate(navDirections)

        }
    }

    override fun insertData(data: CompetitionTeam?) {
        getFavCompetitions()
        AsyncTask.execute {
            kotlin.run {
                if (data != null) {
                    if (favCompetitionsList?.isNotEmpty() == true) {
                        favCompetitionsList?.forEach { favItem ->
                            if (data.competition != favItem.competition)
                                viewModel?.insert(data)
                            else
                                activity?.runOnUiThread {
                                    HelperClass.showToast(
                                        context,
                                        "Already Added"
                                    )
                                }
                        }
                    } else {
                        viewModel?.insert(data)
                    }

                } else
                    activity?.runOnUiThread {
                        HelperClass.showToast(
                            context,
                            "Available For Premium"
                        )
                    }
            }
        }

    }

    private fun getFavCompetitions() {
        viewModel?.get()?.observe(this, Observer {
            favCompetitionsList = it
        })

    }


}

