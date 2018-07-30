package android.thaihn.kotlindemo.screen.search

import dagger.Component

@Component(modules = arrayOf(SearchModule::class))
interface SearchComponent {

  fun inject(searchActivity: SearchActivity)
}