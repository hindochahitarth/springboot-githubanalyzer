# GitHub Profile Analyzer 🚀

An AI-powered tool designed to evaluate GitHub profiles for recruitment potential, providing detailed scoring, risk assessment, and actionable growth insights.

## 🎥 Demo Video
[Watch the Demo on Google Drive](https://drive.google.com/drive/folders/11hz2kRZ6f2BVnDah6B9w4ksk8AaajB49?usp=drive_link)

## ✨ Key Features
*   **Smart Scoring System**: Calculates an overall "Employability Score" based on code quality, consistency, and impact.
*   **Recruiter Verdict**: AI-generated summary (Strong Hire, Hire, Maybe, Risky) mimicking a technical recruiter's perspective.
*   **Risk Analysis**: Identifies red flags like "Dormant Profile", "Shallow Project Depth", or "Incomplete Repositories".
*   **Score Simulation**: Interactive "What-If" analysis showing how specific improvements (e.g., adding tests, CI/CD) boost the score.
*   **Visual Dashboard**: Clean, responsive UI with skill breakdowns and activity charts.

## 🛠️ Tech Stack
*   **Backend**: Java Spring Boot
*   **Frontend**: React.js, Tailwind CSS
*   **AI Engine**: Google Gemini API
*   **Data**: GitHub REST API

## 🚀 How to Run

### Backend
1.  Navigate to the project root.
2.  Configure your API keys in `application.properties`.
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

### Frontend
1.  Navigate to the frontend directory:
    ```bash
    cd frontend
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    npm run dev
    ```
