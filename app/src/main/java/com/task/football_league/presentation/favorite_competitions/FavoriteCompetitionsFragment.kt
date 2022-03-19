package com.task.football_league.presentation.favorite_competitions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.football_league.R
import com.task.football_league.common.HelperClass
import com.task.football_league.databinding.FragmentFavoriteCompetitionsBinding
import com.task.football_league.databinding.ItemFavTeamBinding
import com.task.football_league.di.PreferenceModule
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.competitions_team.Team
import com.task.football_league.presentation.favorite_competitions.adapter.FavCompetitionTeamInterface
import com.task.football_league.presentation.favorite_competitions.adapter.FavCompetitionsTeamsAdapter
import com.task.football_league.presentation.favorite_competitions.adapter.FavoriteCompetitionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCompetitionsFragment : Fragment(), FavCompetitionTeamInterface {
    @set:Inject
    internal var preferenceModule: PreferenceModule? = null
    private var viewModel: FavoriteCompetitionsViewModel? = null
    private var binding: FragmentFavoriteCompetitionsBinding? = null
    private var favoriteCompetitionsAdapter: FavoriteCompetitionsAdapter? = null
    private var favCompetitionsTeamsAdapter: FavCompetitionsTeamsAdapter? = null
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteCompetitionsBinding.inflate(inflater, container, false)
        binding!!.lifecycleOwner = this
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        setUpRecycler()
        getFavCompetitions()
    }

    private fun initComponent() {
        viewModel = ViewModelProvider(this).get(FavoriteCompetitionsViewModel::class.java)
        favoriteCompetitionsAdapter = FavoriteCompetitionsAdapter()
        favCompetitionsTeamsAdapter = FavCompetitionsTeamsAdapter()
        favCompetitionsTeamsAdapter!!.setListener(this)
        favoriteCompetitionsAdapter!!.setTeamAdapter(favCompetitionsTeamsAdapter)
        favoriteCompetitionsAdapter!!.setContext(context)
        setUpRecycler()
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

    private fun setUpRecycler() {
        val recyclerView: RecyclerView = binding!!.rvCompetitionsFav
        recyclerView.adapter = favoriteCompetitionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }


    private fun setData(competitions: List<CompetitionTeam>?) {
        if (competitions?.isEmpty() == true) {
            binding?.tvNoTeams?.visibility = View.VISIBLE
            binding?.rvCompetitionsFav?.visibility = View.INVISIBLE
        } else {
            binding?.tvNoTeams?.visibility = View.INVISIBLE
            binding?.rvCompetitionsFav?.visibility = View.VISIBLE
            favoriteCompetitionsAdapter?.setList(competitions)
            favoriteCompetitionsAdapter?.notifyDataSetChanged()
        }
    }


    private fun getFavCompetitions() {
        viewModel?.getFavCompetitions()?.observe(viewLifecycleOwner, Observer { data ->
            setData(data)
        })
    }

    override fun showImageTeam(response: Team?, binding: ItemFavTeamBinding) {
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
}