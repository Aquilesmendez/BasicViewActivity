package com.example.basicviewactivity

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicviewactivity.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirstFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var heroeAdapter: HeroeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBreed.setOnQueryTextListener(this)
    }

    private fun initCharacter(
        heroes: List<CharacterResponse>,
        powerStats: List<CharacterPowerStats>,
        images: List<CharacterImage>
    ) {
        // Combina los datos de CharacterResponse, CharacterPowerStats y CharacterImage según el ID del personaje
        val combinedData = mutableListOf<Triple<CharacterResponse, CharacterPowerStats, CharacterImage>>()
        for (i in heroes.indices) {
            val character = heroes[i]
            val powerStat = powerStats[i]
            val image = images.find { it.id == character.id }
            if (image != null) {
                combinedData.add(Triple(character, powerStat, image))
            }
        }

        // Configura el adaptador con los datos combinados
        heroeAdapter = HeroeAdapter(combinedData)
        binding.rvHeroe.setHasFixedSize(true)
        binding.rvHeroe.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHeroe.adapter = heroeAdapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/10159916783383579/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchByName(query.lowercase())
        return true
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val searchCall =
                getRetrofit().create(APIService::class.java).searchCharacterByName(query).execute()
            val searchResponse = searchCall.body() as SearchResponse?

            val powerStatsList = mutableListOf<CharacterPowerStats>()
            val imageList = mutableListOf<CharacterImage>()
            if (searchResponse?.response == "success") {
                for (character in searchResponse.results) {
                    val powerStatsCall =
                        getRetrofit().create(APIService::class.java).getPowerStatsById(character.id)
                            .execute()
                    val powerStatsResponse =
                        powerStatsCall.body() as CharacterPowerStats?
                    powerStatsResponse?.let {
                        powerStatsList.add(it)
                    }
                    val imageCall =
                        getRetrofit().create(APIService::class.java).getImageById(character.id)
                            .execute()
                    val imageResponse = imageCall.body() as CharacterImage?
                    imageResponse?.let {
                        imageList.add(it)
                    }
                }
            }

            requireActivity().runOnUiThread {
                if (searchResponse?.response == "success") {
                    initCharacter(searchResponse.results, powerStatsList, imageList)
                } else {
                    showErrorDialog()
                }
                hideKeyboard()
            }
        }
    }

    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
