package com.example.interview_exercise_2coders.navigation

sealed class NavigationRoutes(val route: String) {
    object MovieList : NavigationRoutes("movie_list")
    object MovieDetail : NavigationRoutes("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
}
