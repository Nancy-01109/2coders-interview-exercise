# 2coders-Interview-Exercise

This Android application is a fully functional **movie browsing app** built with modern Android development practices. It allows users to:

- Browse movies in a paginated list
- Search for movies by title or type
- View detailed information about movies
- Add and remove favorite movies
- Access previously loaded movies offline via caching

The app follows **MVVM architecture** with a clean separation of concerns.

---

## Requirements

- **JDK:** 17
- **Android Studio:** Narwhal 4 or newer
- **Gradle:** 8.11.1
- **Kotlin:** 1.9.25

---

## Architecture

- **Core:** Dependency Injection, utilities, and helpers
- **ViewModel:** Manages UI state, exposes flows for Compose
- **Data:** Handles data sources (network + cache), abstracts ViewModel from implementation details
- **Domain:** Contains models and mappers between layers
- **UI:** Composable functions for declarative UI

---

## API Key

This app requires a **TMDb API key**. Add your key in the `local.properties` file at the root of the project:

```properties
TMDB_API_KEY="your_api_key_here"