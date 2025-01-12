# Uala Challenge

## Introduction

The **Uala Challenge** is an Android app developed to demonstrate efficient handling and searching of large datasets (cities and their details) while maintaining smooth user experience and data persistence. This README explains the approach taken to solve the problem, the decisions made during development, and the assumptions considered throughout the implementation.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Approach to the Search Problem](#approach-to-the-search-problem)
- [Technical Decisions](#technical-decisions)
- [Assumptions](#assumptions)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
  
---

## Project Overview

The app handles a large dataset of cities and their details efficiently using local storage, optimized search, and pagination. It also integrates maps for better user experience and includes extensive unit and UI testing to ensure reliability.

---

## Features

- **Data Persistence**: Local database using Room for offline support and reducing redundant API calls.
- **Optimized Search**: Indexed search on city names and countries for fast query results.
- **Efficient Memory Management**: Implements the Paging library to load data in chunks, avoiding memory overload and Out of Memory (OOM) errors.
- **Maps Integration**: Displays geographical information for better context.
- **Comprehensive Testing**: Includes unit tests for database queries and UI tests for search functionality.

Android Version api 35 (Last one) Android Studio IDE version Android Studio Ladybug Feature Drop | 2024.2.2 Canary 
---

## Approach to the Search Problem

To manage the large dataset of cities efficiently, the following steps were implemented:

1. **Local Storage with Room**:
   - The entire dataset is stored in a Room database to ensure persistence. This avoids the need for redownloading the dataset upon navigation or app restarts.

2. **Indexed Search**:
   - To enhance search performance, indexes were created on the `name` and `country` fields in the database. This drastically reduced query execution time.

3. **Pagination**:
   - The Paging library was used to load data in small chunks instead of all at once. This approach prevents high memory usage, which could lead to OOM errors (observed during initial implementation).

4. **Maps Integration**:
   - Added maps to visualize geographic locations, enhancing the user experience.

5. **Error Handling**:
   - Incorporated robust error handling to manage edge cases like empty datasets, failed searches, and incomplete data.

---

## Technical Decisions

- **Retrofit*:
  - Easy implementation to consume API data with clean code. 

- **Room Database**:
  - Chosen for its ease of use, integration with LiveData, and support for complex queries.
  
- **Paging Library**:
  - Selected to handle large datasets efficiently by fetching only required data chunks, optimizing both memory usage and performance.

- **Search Optimization**:
  - Indexing the `name` and `country` fields improved query performance significantly.

- **Testing**:
  - Comprehensive unit tests were written to ensure the correctness of SQL queries and the search logic.
  - UI tests validated the functionality of the search interface and user interactions.
 
- **MVVM**:
  - Also it has MVVM architecture pattern (without domain layer) and dependency inversion for better and scalable development.

---

## Assumptions

- The dataset contains a significant number of cities, requiring optimization for both storage and performance.
- Users need fast and accurate search results, even on lower-end devices.
- Data integrity and persistence are crucial to prevent loss of information between sessions.

---

## Technologies Used

- **Android Jetpack Components**:
  - Room Database
  - Retrofit 
  - Paging Library
  - Coroutines and Flow
- **Google Maps API**: For geographical visualization and navigation.
- **JUnit , Compose test and Hilt**: For unit and UI testing.

---

## Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/uala-challenge.git

2. Open the project in Android Studio.
3. Sync Gradle files and resolve dependencies.
4. Run the app on an emulator or physical device.

I didn't use different branches for features / fixes or release but I all my experience is based on that way of work. Branch -> Approved -> Merge in Release 1.x.x etc
