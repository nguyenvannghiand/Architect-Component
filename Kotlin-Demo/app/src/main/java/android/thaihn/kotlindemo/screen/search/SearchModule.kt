package android.thaihn.kotlindemo.screen.search

import dagger.Module
import dagger.Provides

@Module
class SearchModule(private var activity: SearchActivity) {

  @Provides
  fun providesActivity(): SearchActivity {
    return SearchActivity()
  }

  @Provides
  fun providesSearchPresenter(): SearchContract.Presenter<SearchContract.View> {
    return SearchPresenter<SearchContract.View>()
  }
}