# Android Internship Project – Task 5 Final Submission

This project was developed as part of my Android App Development Internship.

The application fetches data from a REST API and displays it in a clean user interface with search and refresh functionality.

## Features

- Fetch posts from a REST API using Retrofit
- Display posts in a RecyclerView
- Show post title and description
- Search posts by title
- Pull-to-refresh to reload data
- Loading indicator while fetching data
- Empty state when search returns no results
- Material card UI for better presentation

## Technologies Used

- Kotlin
- Android Studio
- Retrofit
- RecyclerView
- SwipeRefreshLayout
- Material Design Components

## How the App Works

When the application starts, it requests data from the API:

https://jsonplaceholder.typicode.com/posts

The response is parsed and displayed in a scrollable list.  
Users can search posts using the search bar or refresh the data using pull-to-refresh.

## Learning Outcome

Through this project I learned:

- REST API integration in Android
- Working with RecyclerView
- Handling asynchronous network calls
- Implementing search functionality
- Improving UI using Material components
