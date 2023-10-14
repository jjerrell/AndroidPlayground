/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about

import dev.jjerrell.android.playground.feature.about.ui.compose.AboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun aboutModule() = module {
    single<ContributorRepository> { ContributorRepositoryImpl() }
    viewModel { AboutViewModel(repository = get()) }
}
