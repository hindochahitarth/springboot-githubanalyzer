## How to Add Environment Variable in Netlify

Based on your Netlify dashboard, here's EXACTLY what to click:

### Navigation Path:
**Site Settings** → **Environment variables** (left sidebar under "Build & deploy")

### What You'll See:
You're currently looking at the Build & Deploy settings page. You need to scroll down in the left sidebar to find "Environment variables".

### Steps:
1. In the **left sidebar**, scroll down to find **"Environment variables"**
2. Click on it
3. You'll see a button **"Add a variable"** or **"Declare variables"**
4. Click it and select **"Add a single variable"**
5. Enter:
   - **Key**: `VITE_API_URL`
   - **Value**: `https://YOUR-RAILWAY-URL.up.railway.app/api`
   - **Scopes**: Check ✓ "Production" and ✓ "Deploy previews"
6. Click **"Create variable"**

### After Adding the Variable:
1. Go to **"Deploys"** (in the left sidebar under "Project overview")
2. Click **"Trigger deploy"** button (top right)
3. Select **"Clear cache and deploy site"**
4. Wait 2-3 minutes for deployment to complete

That's it! Your site will rebuild with the new environment variable and connect to your Railway backend instead of localhost.
