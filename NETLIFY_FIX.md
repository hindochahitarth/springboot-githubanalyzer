# 🚨 URGENT: Fix Your Netlify Deployment

## The Problem
Your Netlify frontend is trying to connect to `http://localhost:8080/api` which doesn't exist in production! You need to tell it where your Railway backend is.

## Step-by-Step Fix

### Step 1: Get Your Railway Backend URL

1. **Go to Railway Dashboard** → Your `springboot-githubanalyzer` project
2. In the **"Settings"** tab, under **"Networking"** section
3. Click **"Generate Domain"** button (under "Public Networking")
4. Railway will give you a URL like: `springboot-githubanalyzer-production.up.railway.app`
5. **Copy this URL!** ✅

### Step 2: Add Environment Variables to Railway

While you're in Railway:

1. Go to **"Variables"** tab (left sidebar)
2. Click **"Add Variable"**
3. Add these three variables:

   ```
   GEMINI_API_KEY = your-actual-gemini-api-key-here
   GITHUB_TOKEN = your-github-personal-access-token
   JAVA_TOOL_OPTIONS = -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false
   ```

4. Railway will automatically redeploy with these variables

### Step 3: Configure Netlify Environment Variable ⚠️ **CRITICAL**

**YOU ARE HERE** - This is what you need to do RIGHT NOW:

1. **In Netlify**, go to: **Site Settings** → **Environment variables**
   
2. Click **"Add a variable"** → **"Add a single variable"**

3. **Add this variable:**
   - **Key:** `VITE_API_URL`
   - **Value:** `https://your-railway-url.up.railway.app/api`
   
   Example: `https://springboot-githubanalyzer-production.up.railway.app/api`

4. **Scopes:** Select both "Production" and "Deploy previews"

5. Click **"Create variable"**

### Step 4: Redeploy Netlify

1. Go to **"Deploys"** tab in Netlify
2. Click **"Trigger deploy"** → **"Clear cache and deploy site"**
3. Wait for the deployment to complete (2-3 minutes)

### Step 5: Test It!

After Netlify finishes deploying:

1. **Test your Railway backend first:**
   - Visit: `https://your-railway-url.up.railway.app/api/health`
   - You should see: `{"status":"UP","service":"GitHub Portfolio Analyzer",...}`

2. **Test your Netlify site:**
   - Visit: `https://githubanalyzerhackathon.netlify.app`
   - Try analyzing a GitHub profile like: `github.com/hindochahitarth`
   - It should work! 🎉

## Quick Checklist

- [ ] Generate Railway public domain
- [ ] Add `GEMINI_API_KEY` to Railway
- [ ] Add `GITHUB_TOKEN` to Railway (optional)
- [ ] Add `JAVA_TOOL_OPTIONS` to Railway
- [ ] Add `VITE_API_URL` to Netlify ⚠️ **MOST IMPORTANT**
- [ ] Redeploy Netlify site
- [ ] Test Railway `/api/health` endpoint
- [ ] Test Netlify site with profile analysis

## If It Still Doesn't Work

Check the browser console (F12) for errors and Netlify deploy logs to verify the environment variable was picked up during build.
