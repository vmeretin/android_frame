package io.github.kmeret.frame.demo.ui.stars

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.kmeret.frame.android.extensions.attachAdapter
import io.github.kmeret.frame.android.extensions.visible
import io.github.kmeret.frame.android.ui.RecyclerAdapter
import io.github.kmeret.frame.demo.R
import io.github.kmeret.frame.demo.domain.entity.Repo
import io.github.kmeret.frame.demo.ui.ExceptionHandler
import io.github.kmeret.frame.lifecycle.ViewModelFragment
import kotlinx.android.synthetic.main.fragment_stars.*
import kotlinx.android.synthetic.main.fragment_stars.view.*
import kotlinx.android.synthetic.main.template_repo_starred.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class StarsFragment : ViewModelFragment<StarsViewModel>() {

    override val layoutResId = R.layout.fragment_stars
    override val viewModel: StarsViewModel by viewModel()

    private lateinit var repoListAdapter: RecyclerAdapter<Repo>

    override fun initView(rootView: View) {
        repoListAdapter = RecyclerAdapter(R.layout.template_repo_starred) { repo, repoRootView ->
            repoRootView.also {
                it.repoTitleView.text = repo.fullName
                it.repoDescriptionView.text = repo.description
                it.repoLanguageView.text = repo.language
                it.repoStarsView.text = repo.starsCount.toString()
                it.repoForksView.text = repo.forksCount.toString()
                it.repoUpdatedView.text = repo.updatedAt

                if (repo.description.isEmpty()) it.repoDescriptionView.visible(false)
            }
        }
        rootView.apply {
            repoListView.apply {
                attachAdapter(repoListAdapter)
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL ))
            }
            starsRefreshLayout.setOnRefreshListener { viewModel.requestStarredList() }
        }
    }

    override fun initViewModel() {
        viewModel.apply {
            repoList.observe { repoListAdapter.updateList(it) }
            loading.observe { isLoading -> starsRefreshLayout.isRefreshing = isLoading }
            empty.observe { isEmpty ->
                repoListView.visible(!isEmpty)
                starsEmpty.visible(isEmpty)
            }
            error.observe { ExceptionHandler.handle(it, requireContext()) }
        }

    }

}