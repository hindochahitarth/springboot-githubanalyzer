# Deployment Guide

## Current Issue
Your frontend is deployed on Netlify but trying to connect to `localhost:8080`, which won't work in production.

## Solutions

### Option 1: Deploy Backend (Recommended)
You need to deploy your Spring Boot backend to a hosting service. Here are some options:

#### **Railway (Easy & Free)**
1. Go to [railway.app](https://railway.app)
2. Sign in with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your repository
5. Railway will auto-detect Spring Boot and deploy it
6. Copy the provided URL (e.g., `https://your-app.up.railway.app`)

#### **Render (Free Tier)**
1. Go to [render.com](https://render.com)
2. Create a new "Web Service"
3. Connect your GitHub repository
4. Build Command: `./mvnw clean package -DskipTests`
5. Start Command: `java -Djava.net.preferIPv4Stack=true -jar target/githubprofileanalyzer-0.0.1-SNAPSHOT.jar`

#### **Heroku**
```bash
# Install Heroku CLI, then:
heroku login
heroku create your-app-name
git push heroku main
```

### Option 2: Configure Environment Variables

After deploying your backend:

1. **In Netlify** (for your frontend):
   - Go to Site Settings → Environment Variables
   - Add: `VITE_API_URL` = `https://your-backend-url.com/api`
   - Redeploy your site

2. **For Local Development**:
   ```bash
   cd frontend
   # Create .env file:
   echo "VITE_API_URL=http://localhost:8080/api" > .env
   ```

### Option 3: Test Locally First

To test everything locally before deploying:

1. **Start Backend** (with IPv4 fix):
   ```bash
   mvn spring-boot:run "-Dspring-boot.run.jvmArguments=-Djava.net.preferIPv4Stack=true"
   ```

2. **Start Frontend**:
   ```bash
   cd frontend
   npm run dev
   ```

3. **Test** at `http://localhost:5173`

## Environment Configuration

### Backend Environment Variables
Set these in your hosting service:

- `GITHUB_TOKEN` (optional) - GitHub Personal Access Token for higher rate limits
- `GEMINI_API_KEY` - Your Google Gemini API key for AI insights

### Frontend Environment Variables
Set in Netlify:

- `VITE_API_URL` - Your deployed backend URL (e.g., `https://your-backend.railway.app/api`)

## CORS Configuration
The backend is now configured to accept requests from:
- `http://localhost:5173` (local dev)
- `http://localhost:3000` (alternative local)
- `https://githubanalyzerhackathon.netlify.app` (production)

## Quick Fix for Testing

If you want to test RIGHT NOW without deploying the backend:

1. Keep your Spring Boot app running locally
2. Use a tunnel service like **ngrok**:
   ```bash
   # Install ngrok from https://ngrok.com
   ngrok http 8080
   ```
3. Copy the HTTPS URL ngrok provides (e.g., `https://abc123.ngrok.io`)
4. In Netlify environment variables, set:
   - `VITE_API_URL` = `https://abc123.ngrok.io/api`
5. Redeploy your Netlify site

⚠️ **Note**: ngrok URLs change each time you restart, so this is only for testing!
