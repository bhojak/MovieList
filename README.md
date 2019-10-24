# MovieList
The MovieDB API based project on Kotlin MVVM architecture + Coroutines + Koin - dependency injection

Where we have 2 main use cases:

* Get a list of movies.
* Show details for an specific clicked movie.

## How to build on your environment
// replace this line in the App build file 

buildConfigField 'String', 'TMDB_API_KEY', '"MY-TMDB-API-KEY"'


# Technical details and  Open-source libraries
Minimum SDK level 19
## Kotlin + MVVM  + Coroutines
### Clean architecture patterns - MVVM Architecture
MVVM Architecture (View - DataBinding - ViewModel - Model)
* LiveData -  It notifies views of any change in the domain layer data.
* Lifecycle - dispose of observing data when lifecycle state changes.
* ViewModel - UI related data holder, lifecycle aware.
* Room Database - Persistence - construct the database.
* Repository pattern - offline first and avoid same multiple server requests.
* Koin - dependency injection
* Material Design & Animations
* Retrofit2 & Gson - constructing the REST API
* OkHttp3 - implementing interceptor, logging and mocking web server
* Glide - loading images
* BaseRecyclerViewAdapter - implementing adapters and viewHolders
* Mockito-Kotlin - Junit mock test
* Timber - logging
* Stetho - debugging persistence data & network packets
* Ripple animation, Shared element transition
* Custom Views AndroidTagView, ExpandableTextView

