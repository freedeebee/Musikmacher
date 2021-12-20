package de.freedeebee.musikmacher.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import de.freedeebee.musikmacher.R
import de.freedeebee.musikmacher.data.local.MusikmacherDatabase
import de.freedeebee.musikmacher.databinding.FragmentTrainingEditBinding

class TrainingEditFragment : Fragment() {

    private lateinit var binding: FragmentTrainingEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingEditBinding.inflate(
            inflater,
            container,
            false
        )

        val view = binding.root

        val sessionId = TrainingEditFragmentArgs.fromBundle(requireArguments()).sessionId

        val application = requireNotNull(this.activity).application
        val dao = MusikmacherDatabase.getInstance(application.applicationContext).trainingSessionDao
        val viewModelFactory = TrainingEditViewModelFactory(sessionId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TrainingEditViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToList.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().navigate(R.id.action_trainingEditFragment_to_trainingFragment)
                viewModel.onNavigatedToList()
            }
        }

        return view
    }
}