NallaNudi (ನಲ್ಲನುಡಿ)
Bridge Dictionary for Technical Terms

NallaNudi is a specialized educational mobile application designed to assist Kannada-medium students in rural Karnataka during their transition to English-medium higher education. It functions as a linguistic "ladder," bridging the gap between conceptual understanding in the mother tongue and technical terminology in English.

📖 Project Overview
Students from regional-medium backgrounds often face a "linguistic shock" in STEM and Commerce streams. Standard dictionaries provide generic meanings that lack academic context. NallaNudi solves this by providing a curated technical glossary that works 100% offline, ensuring equity for students with limited internet access.

🚀 Key Features
Interactive Flashcards: Uses a "Tap to Reveal" mechanism to promote active recall. Definitions and examples stay hidden until the user is ready to check their knowledge.
My List (Revision Tool): Students can "Star" difficult terms to build a personalized list for daily revision.
Word of the Day: An automated module that highlights a new technical term every 24 hours to encourage incremental learning.
Voice Guide: Integrated Text-To-Speech (TTS) to help students master the correct English pronunciation of complex terms.
Subject-Wise Filtering: Quickly navigate through specific terminologies for Science, Mathematics, and Commerce.
Instant Search: High-performance local search engine optimized for response times under 200ms using Room Database indexing.

🛠️ Technical Stack
Frontend: XML with Material Design 3 (Cards, Chips, RecyclerView).
Language: Kotlin 1.9.22.
Database: Room Persistence Library (SQLite abstraction).
Concurrency: Kotlin Coroutines & Flow for lag-free background database operations.
Data Parsing: Native JSON parsing (org.json) for safe data migration.
Min SDK: API 24 (Android 7.0).
Target SDK: API 34 (Android 14).

🏗️ System Architecture
The application follows a Layered Architecture to ensure maintainability and performance:
Presentation Layer: MainActivity and WordAdapter manage the UI state and user interactions.
Logic Layer: Kotlin Coroutines handle asynchronous data fetching to keep the UI thread smooth.
Data Layer: WordDatabase and WordDao manage local persistence, ensuring the app remains fully functional without an internet connection.

📂 Project Structure
code
Text
app/src/main/java/com/example/nallanudi/
├── MainActivity.kt      # The brain of the app (Search, Filters, TTS)
├── Word.kt              # Data Entity (English, Kannada, Definition, Example, etc.)
├── WordDao.kt           # SQL Query Definitions (Search, Filter, Favorites)
├── WordDatabase.kt      # Database initialization and Migration logic
└── WordAdapter.kt       # Logic for the interactive "Tap to Reveal" UI

🎯 Social Impact
This project aims to reduce academic dropout rates among Kannada-medium students by removing the language barrier in higher education. By validating the mother tongue as a foundation for technical learning, NallaNudi democratizes access to scientific and commercial knowledge.

🔧 Installation & Usage
Clone the repository.
Open the project in Android Studio (Version 2024.1 or higher).
Ensure JDK 17 is configured in your Gradle settings.
Clean and Rebuild the project.
Run on a physical device or emulator.

👷 Author
Developed by Poola Mahalakshmi as a Final Year Internship Project.

Note to Reviewers:
The application includes a pre-populated database of 200+ technical terms. To see the data for the first time, simply launch the app; the system will automatically migrate the words.json assets into the local Room Database.
