package com.example.basicviewactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.basicviewactivity.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var character: CharacterResponse
    private lateinit var powerStats: CharacterPowerStats
    private lateinit var image: CharacterImage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén los datos del primer fragmento
        character = requireArguments().getParcelable("character")!!
        powerStats = requireArguments().getParcelable("powerStats")!!
        image = requireArguments().getParcelable("image")!!

        // Actualiza la interfaz de usuario con los datos recibidos
        binding.tvSuperHero.text = character.name
        binding.tvIntelligence.text = "Intelligence: ${powerStats.intelligence}"
        // Actualiza más TextViews según tus necesidades

        // Carga la imagen utilizando Picasso o cualquier otra biblioteca de tu elección
        Picasso.get().load(image.url).into(binding.ivHeroImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
