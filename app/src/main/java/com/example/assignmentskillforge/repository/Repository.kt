package com.example.assignmentskillforge.repository

import com.example.assignmentskillforge.data.SkillforgeResponse
import com.example.assignmentskillforge.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private val apiService = RetrofitClient.apiService
    private val gson = Gson()

    suspend fun getSkillforgeData(url: String): Result<SkillforgeResponse> {
        return withContext(Dispatchers.IO) {
            try {
                if (url.isBlank()) {
                    // Automatically fall back to local mock data if no URL is provided
                    Result.success(parseMockData())
                } else {
                    val response = apiService.getSkillforgeData(url)
                    Result.success(response)
                }
            } catch (e: Exception) {
                // If network fetch fails, fallback to local mock data so the app displays categories/courses anyway
                e.printStackTrace()
                try {
                    Result.success(parseMockData())
                } catch (jsonException: Exception) {
                    Result.failure(e)
                }
            }
        }
    }

    private fun parseMockData(): SkillforgeResponse {
        return gson.fromJson(MOCK_JSON, SkillforgeResponse::class.java)
    }

    companion object {
        private val MOCK_JSON = """
        {
          "meta": {
            "app": "Skillforge",
            "version": "1.0",
            "generatedAt": "2026-06-22"
          },
          "categories": [
            {
              "id": "cat_android",
              "name": "Android Development",
              "description": "Build modern, production-ready Android apps with Kotlin.",
              "iconColor": "#2dd4bf",
              "courseCount": 2,
              "courses": [
                {
                  "id": "course_kotlin_101",
                  "title": "Kotlin Fundamentals",
                  "subtitle": "Everything you need to start writing Kotlin",
                  "thumbnailUrl": "https://placehold.co/600x360/0f1720/2dd4bf/png?text=Kotlin+Fundamentals",
                  "level": "Beginner",
                  "durationHours": 6.5,
                  "rating": 4.7,
                  "studentsEnrolled": 18420,
                  "language": "English",
                  "lastUpdated": "2026-03-12",
                  "tags": ["Kotlin", "Basics", "JVM"],
                  "instructor": {
                    "id": "inst_aarav",
                    "name": "Aarav Sharma",
                    "title": "Senior Android Engineer",
                    "avatarUrl": "https://ui-avatars.com/api/?name=Aarav+Sharma&size=150&background=2dd4bf&color=ffffff&bold=true&format=png",
                    "bio": "10+ years building Android apps used by millions of people."
                  },
                  "description": "Start from zero and learn Kotlin's syntax, null safety, collections, and functions.",
                  "lessons": []
                },
                {
                  "id": "course_compose_201",
                  "title": "Jetpack Compose Essentials",
                  "subtitle": "Build UIs the modern, declarative way",
                  "thumbnailUrl": "https://placehold.co/600x360/0f1720/2dd4bf/png?text=Jetpack+Compose+Essentials",
                  "level": "Intermediate",
                  "durationHours": 9.0,
                  "rating": 4.8,
                  "studentsEnrolled": 11230,
                  "language": "English",
                  "lastUpdated": "2026-04-02",
                  "tags": ["Compose", "UI", "State"],
                  "instructor": {
                    "id": "inst_meera",
                    "name": "Meera Nair",
                    "title": "Android UI Specialist",
                    "avatarUrl": "https://ui-avatars.com/api/?name=Meera+Nair&size=150&background=2dd4bf&color=ffffff&bold=true&format=png",
                    "bio": "Conference speaker and author focused on Android UI and Compose."
                  },
                  "description": "Learn composable functions, state management, layouts, and lists.",
                  "lessons": []
                }
              ]
            },
            {
              "id": "cat_backend",
              "name": "Backend & APIs",
              "description": "Design and build the services that power your apps.",
              "iconColor": "#34d399",
              "courseCount": 2,
              "courses": [
                {
                  "id": "course_rest_301",
                  "title": "Designing REST APIs",
                  "subtitle": "Principles of clean, predictable APIs",
                  "thumbnailUrl": "https://placehold.co/600x360/0f1720/34d399/png?text=Designing+REST+APIs",
                  "level": "Intermediate",
                  "durationHours": 5.0,
                  "rating": 4.6,
                  "studentsEnrolled": 8740,
                  "language": "English",
                  "lastUpdated": "2026-02-20",
                  "tags": ["REST", "HTTP", "API Design"],
                  "instructor": {
                    "id": "inst_rahul",
                    "name": "Rahul Verma",
                    "title": "Backend Architect",
                    "avatarUrl": "https://ui-avatars.com/api/?name=Rahul+Verma&size=150&background=34d399&color=ffffff&bold=true&format=png",
                    "bio": "Designs large-scale APIs and mentors backend teams."
                  },
                  "description": "Learn resource modeling, status codes, pagination, and error handling.",
                  "lessons": []
                },
                {
                  "id": "course_node_302",
                  "title": "Node.js from Scratch",
                  "subtitle": "Build your first backend service",
                  "thumbnailUrl": "https://placehold.co/600x360/0f1720/34d399/png?text=Node.js+from+Scratch",
                  "level": "Beginner",
                  "durationHours": 7.5,
                  "rating": 4.5,
                  "studentsEnrolled": 15310,
                  "language": "English",
                  "lastUpdated": "2026-01-30",
                  "tags": ["Node.js", "Express", "Backend"],
                  "instructor": {
                    "id": "inst_sara",
                    "name": "Sara Khan",
                    "title": "Full-stack Developer",
                    "avatarUrl": "https://ui-avatars.com/api/?name=Sara+Khan&size=150&background=34d399&color=ffffff&bold=true&format=png",
                    "bio": "Builds full-stack products and loves teaching beginners."
                  },
                  "description": "Go from nothing to a working REST service using Node.js and Express.",
                  "lessons": []
                }
              ]
            },
            {
              "id": "cat_design",
              "name": "Product & UI Design",
              "description": "Design interfaces that people enjoy using.",
              "iconColor": "#fbbf24",
              "courseCount": 2,
              "courses": [
                {
                  "id": "course_uiux_401",
                  "title": "UI/UX Foundations",
                  "subtitle": "Core principles of great product design",
                  "thumbnailUrl": "https://placehold.co/600x360/0f1720/fbbf24/png?text=UI%2FUX+Foundations",
                  "level": "Beginner",
                  "durationHours": 4.5,
                  "rating": 4.7,
                  "studentsEnrolled": 9980,
                  "language": "English",
                  "lastUpdated": "2026-03-25",
                  "tags": ["UX", "UI", "Design"],
                  "instructor": {
                    "id": "inst_diya",
                    "name": "Diya Patel",
                    "title": "Product Designer",
                    "avatarUrl": "https://ui-avatars.com/api/?name=Diya+Patel&size=150&background=fbbf24&color=ffffff&bold=true&format=png",
                    "bio": "Designs delightful mobile experiences at scale."
                  },
                  "description": "Understand hierarchy, spacing, color, and typography.",
                  "lessons": []
                },
                {
                  "id": "course_figma_402",
                  "title": "Figma for Developers",
                  "subtitle": "Turn designs into code, faster",
                  "thumbnailUrl": "https://placehold.co/600x360/0f1720/figma/png?text=Figma+for+Developers",
                  "level": "Intermediate",
                  "durationHours": 3.5,
                  "rating": 4.6,
                  "studentsEnrolled": 7120,
                  "language": "English",
                  "lastUpdated": "2026-04-10",
                  "tags": ["Figma", "Handoff", "Design"],
                  "instructor": {
                    "id": "inst_arjun",
                    "name": "Arjun Mehta",
                    "title": "Design Engineer",
                    "avatarUrl": "https://ui-avatars.com/api/?name=Arjun+Mehta&size=150&background=fbbf24&color=ffffff&bold=true&format=png",
                    "bio": "Bridges design and engineering with design systems."
                  },
                  "description": "Learn to read Figma files, inspect specs, and export assets.",
                  "lessons": []
                }
              ]
            }
          ]
        }
        """.trimIndent()
    }
}
