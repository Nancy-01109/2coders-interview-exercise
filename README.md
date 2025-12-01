# 2coders-interview-exercise
This Android application is a fully functional movie browsing app built with modern Android development practices. It allows users to:

Browse movies in a paginated list

Search for movies by title or type

View detailed information about movies

Add and remove favorite movies

Access previously loaded movies offline via caching

The app follows MVVM architecture with a clean separation of concerns.

# Features

Jetpack Compose: Declarative UI components and custom composables

MVVM + Repository Pattern: Clean architecture for maintainable code

Room Database + Paging 3: Offline caching and paginated movie lists

Retrofit + OkHttp: Network requests with optional Curl logging

Coroutines & Flow: Asynchronous and reactive programming

Favorites: Add/remove movies from favorites with local persistence

Unit Testing: MockK and Turbine for ViewModel and Repository tests

# Architecture

Core: Dependency Injection, utilities, and helpers

ViewModel: Manages UI state, exposes flows for Compose

Data: Handles data sources (network + cache), abstracts ViewModel from implementation details

Domain: Contains models and mappers between layers

UI: Composable functions for declarative UI

# Requirements

JDK: 17

Android Studio: Narwhal 4

Gradle: 8.11.1

Kotlin: 1.9.25

# Dependencies

Jetpack Compose: UI framework

Room + Paging 3: Local database and pagination

Retrofit + OkHttp: Networking

MockK: Unit testing and mocking

Turbine: Testing Kotlin Flows

Coroutines & Flow: Asynchronous programming

# Testing

Unit Tests: Written with JUnit, MockK, and Turbine

ViewModel Tests: Test UI state updates and repository interactions

Repository Tests: Mock network services and DAOs for offline caching