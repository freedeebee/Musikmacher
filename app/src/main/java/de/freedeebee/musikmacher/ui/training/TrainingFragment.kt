package de.freedeebee.musikmacher.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import de.freedeebee.musikmacher.data.local.MusikmacherDatabase
import de.freedeebee.musikmacher.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTrainingBinding.inflate(
            inflater,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = MusikmacherDatabase.getInstance(application.applicationContext).trainingSessionDao
        val viewModelFactory = TrainingViewModelFactory(dao)

        viewModel = ViewModelProvider(this, viewModelFactory)[TrainingViewModel::class.java]

        viewModel.stopButtonVisible.observe(viewLifecycleOwner) { stopButtonVisible ->
            if (stopButtonVisible) {
                binding.stopButton.visibility = View.VISIBLE
                binding.startButton.visibility = View.GONE
            }
            else {
                binding.stopButton.visibility = View.GONE
                binding.startButton.visibility = View.VISIBLE
            }
        }

        val adapter = TrainingItemAdapter { sessionId ->
            viewModel.onListItemClicked(sessionId)
        }
        binding.sessionListView.adapter = adapter

        viewModel.navigateToSession.observe(viewLifecycleOwner) { sessionId ->
            sessionId?.let {
                val action = TrainingFragmentDirections.actionTrainingFragmentToTrainingEditFragment(sessionId)
                findNavController().navigate(action)
                viewModel.onListItemNavigated()
            }
        }

        viewModel.completedTrainings.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.nothingTrackedText.visibility = View.GONE
                    binding.sessionListView.visibility = View.VISIBLE
                } else {
                    binding.nothingTrackedText.visibility = View.VISIBLE
                    binding.sessionListView.visibility = View.GONE
                }
                adapter.submitList(it) {
                    binding.sessionListView.scrollToPosition(0)
                }
            }
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}