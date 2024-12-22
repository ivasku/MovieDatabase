# MovieDatabase
Android native project showing the movies, search and show movie details

It uses MVVM with CLEAN architecture. 

Libraries used: Retrofit (network), Hilt (Dependency injection), Compose (for UI), Coil (for image loading).



To use this project you must add the API KEY to the Application class (MovieApp.kt), here:

```
companion object {
  const val API_KEY = "123445567788"  // Replace with the actual key
}
```
