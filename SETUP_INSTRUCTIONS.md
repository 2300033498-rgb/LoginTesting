# 📘 Setup Instructions - Secure Login Testing Project

## Complete Step-by-Step Guide

---

## 🎯 Prerequisites Checklist

Before starting, ensure you have installed:

- [ ] **Node.js** version 14 or higher
- [ ] **Java JDK** version 11 or higher
- [ ] **Maven** version 3.6 or higher
- [ ] **Google Chrome** (latest version)
- [ ] **Git** (optional, for version control)
- [ ] **Terminal/Command Prompt** access
- [ ] **Code Editor** (VS Code recommended)

---

## 📥 Installation Steps

### Step 1: Verify Prerequisites

Open terminal/command prompt and verify installations:

```bash
# Check Node.js version
node --version
# Expected output: v14.x.x or higher

# Check npm version
npm --version
# Expected output: 6.x.x or higher

# Check Java version
java -version
# Expected output: java version "11.x.x" or higher

# Check Maven version
mvn -version
# Expected output: Apache Maven 3.6.x or higher
```

If any command fails, install the missing software:
- **Node.js:** https://nodejs.org/
- **Java JDK:** https://adoptium.net/
- **Maven:** https://maven.apache.org/download.cgi

### Step 2: Navigate to Project Directory

```bash
cd path/to/secure-login-testing-project
```

### Step 3: Install Frontend Dependencies

```bash
# Navigate to frontend directory
cd frontend

# Install all npm packages
npm install

# Wait for installation to complete
# You should see: "added XXX packages" message
```

**Expected output:**
```
added 1234 packages, and audited 1235 packages in 45s
found 0 vulnerabilities
```

### Step 4: Install Backend Dependencies

```bash
# Navigate to backend directory (from project root)
cd ../backend

# Install npm packages
npm install
```

**Expected output:**
```
added 234 packages, and audited 235 packages in 12s
found 0 vulnerabilities
```

### Step 5: Install Testing Framework Dependencies

```bash
# Navigate to automation-tests directory
cd ../automation-tests

# Maven will download all dependencies
mvn clean install
```

**Expected output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 3.456 s
```

**Note:** First run may take 5-10 minutes as Maven downloads all dependencies.

---

## 🚀 Running the Application

### Method 1: Manual Startup (Recommended for Learning)

**Terminal/Tab 1 - Backend Server:**
```bash
cd backend
npm start
```

**Expected output:**
```
╔════════════════════════════════════════════════════╗
║     SECURE LOGIN BACKEND - TESTING PROJECT        ║
╚════════════════════════════════════════════════════╝
🚀 Server running on http://localhost:5000
📝 Environment: development
✅ CORS enabled for: http://localhost:3000

Available Endpoints:
  GET  /api/health        - Health check
  POST /api/auth/login    - User login
  GET  /api/users         - List users (testing only)

Test Credentials:
  Username: admin      | Password: admin123
  Username: testuser   | Password: test1234
  Username: john.doe   | Password: john@123
════════════════════════════════════════════════════
```

**Terminal/Tab 2 - Frontend Application:**
```bash
cd frontend
npm run dev
```

**Expected output:**
```
  VITE v5.0.0  ready in 523 ms

  ➜  Local:   http://localhost:3000/
  ➜  Network: use --host to expose
  ➜  press h to show help
```

### Method 2: Using Separate Terminal Windows

**Windows Users:**
1. Open PowerShell/CMD
2. Navigate to `backend` folder: `cd backend`
3. Run: `npm start`
4. Open new PowerShell/CMD window
5. Navigate to `frontend` folder: `cd frontend`
6. Run: `npm run dev`

**Mac/Linux Users:**
1. Open Terminal
2. Navigate to `backend` folder: `cd backend`
3. Run: `npm start`
4. Open new Terminal tab (Cmd+T or Ctrl+Shift+T)
5. Navigate to `frontend` folder: `cd frontend`
6. Run: `npm run dev`

### Method 3: Background Processes (Advanced)

**Linux/Mac:**
```bash
# From project root
cd backend && npm start &
cd ../frontend && npm run dev &
```

**Windows (PowerShell):**
```powershell
# Start backend
Start-Process npm -ArgumentList "start" -WorkingDirectory ".\backend"

# Start frontend
Start-Process npm -ArgumentList "run", "dev" -WorkingDirectory ".\frontend"
```

---

## ✅ Verify Application is Running

### Step 1: Check Backend Health

Open browser and visit: `http://localhost:5000/api/health`

**Expected response:**
```json
{
  "status": "OK",
  "message": "Server is running",
  "timestamp": "2025-02-13T10:30:00.000Z"
}
```

### Step 2: Access Frontend

Open browser and visit: `http://localhost:3000`

You should see:
- ✅ Modern login page with gradient background
- ✅ Username and password fields
- ✅ Disabled login button (before entering credentials)
- ✅ Test credentials note at bottom

### Step 3: Test Manual Login

1. Enter username: `admin`
2. Enter password: `admin123`
3. Click "Sign In" button
4. You should be redirected to dashboard with welcome message

---

## 🧪 Running Automation Tests

### Prerequisites for Testing

**IMPORTANT:** Both frontend and backend MUST be running before executing tests!

**Verify services are running:**
```bash
# Check if backend is running
curl http://localhost:5000/api/health

# Check if frontend is accessible (should return HTML)
curl http://localhost:3000
```

### Step 1: Navigate to Test Directory

```bash
cd automation-tests
```

### Step 2: Run All Tests

```bash
mvn clean test
```

**What happens:**
1. Maven compiles test code
2. WebDriverManager downloads ChromeDriver (first time only)
3. Chrome browser opens and tests execute
4. Results are displayed in console
5. Reports are generated in `target/cucumber-reports/`

**Expected console output:**
```
╔════════════════════════════════════════════════════════════╗
║  Starting Test: Successful login with valid credentials
╚════════════════════════════════════════════════════════════╝
✓ Browser initialized successfully
→ Navigated to login page
→ Entered username: admin
→ Entered password: ad****23
→ Clicked login button
✓ Successfully redirected to dashboard
✓ Welcome message verified: Welcome back, admin!
✓ Test Passed
✓ Browser closed
═══════════════════════════════════════════════════════════

... (more tests)

Tests run: 60, Failures: 0, Errors: 0, Skipped: 0

[INFO] BUILD SUCCESS
```

### Step 3: View Test Reports

**Option 1: HTML Report (Recommended)**
```bash
# Navigate to reports directory
cd target/cucumber-reports/

# Open HTML report
# On Mac:
open cucumber.html

# On Linux:
xdg-open cucumber.html

# On Windows:
start cucumber.html
```

**Option 2: Console Summary**
```bash
# View test summary in terminal
cat target/cucumber-reports/cucumber.json
```

---

## 🎯 Running Specific Test Suites

### Run Only Functional Tests
```bash
mvn clean test -Dcucumber.filter.tags="@functional"
```

### Run Only Boundary Tests
```bash
mvn clean test -Dcucumber.filter.tags="@boundary"
```

### Run Only Security Tests
```bash
mvn clean test -Dcucumber.filter.tags="@security"
```

### Run Only UI Tests
```bash
mvn clean test -Dcucumber.filter.tags="@ui"
```

### Run Positive Tests Only
```bash
mvn clean test -Dcucumber.filter.tags="@positive"
```

### Run Negative Tests Only
```bash
mvn clean test -Dcucumber.filter.tags="@negative"
```

### Combine Multiple Tags
```bash
# Run functional AND positive tests
mvn clean test -Dcucumber.filter.tags="@functional and @positive"

# Run functional OR boundary tests
mvn clean test -Dcucumber.filter.tags="@functional or @boundary"

# Run all except security tests
mvn clean test -Dcucumber.filter.tags="not @security"
```

---

## 🌐 Testing in Different Browsers

### Chrome (Default)
```bash
mvn clean test -Dbrowser=chrome
```

### Firefox
```bash
# First, install Firefox browser
mvn clean test -Dbrowser=firefox
```

### Microsoft Edge
```bash
# First, install Edge browser
mvn clean test -Dbrowser=edge
```

### Headless Mode (No Browser Window)
```bash
mvn clean test -Dheadless=true
```

**Use headless mode for:**
- CI/CD pipelines
- Faster execution
- Running on servers without display

---

## 🔧 Troubleshooting

### Issue 1: "Port 3000 already in use"

**Solution:**
```bash
# Find process using port 3000
# On Mac/Linux:
lsof -ti:3000 | xargs kill -9

# On Windows:
netstat -ano | findstr :3000
taskkill /PID <PID_NUMBER> /F
```

### Issue 2: "Port 5000 already in use"

**Solution:**
```bash
# Find and kill process on port 5000
# On Mac/Linux:
lsof -ti:5000 | xargs kill -9

# On Windows:
netstat -ano | findstr :5000
taskkill /PID <PID_NUMBER> /F
```

### Issue 3: "Cannot find ChromeDriver"

**Solution:**
```bash
# WebDriverManager should download automatically
# If it fails, manually install:
cd automation-tests
mvn clean install -U
```

### Issue 4: Tests failing with connection errors

**Checklist:**
- [ ] Is backend running on port 5000?
- [ ] Is frontend running on port 3000?
- [ ] Can you access http://localhost:3000 in browser?
- [ ] Can you access http://localhost:5000/api/health?
- [ ] Is firewall blocking connections?

**Verify connectivity:**
```bash
curl http://localhost:5000/api/health
curl http://localhost:3000
```

### Issue 5: "Module not found" errors

**Solution:**
```bash
# Reinstall dependencies
cd frontend
rm -rf node_modules package-lock.json
npm install

cd ../backend
rm -rf node_modules package-lock.json
npm install
```

### Issue 6: Maven build fails

**Solution:**
```bash
# Clean Maven cache and rebuild
cd automation-tests
mvn clean
rm -rf target/
mvn clean install -U
```

### Issue 7: Browser doesn't close after tests

**Solution:**
```bash
# Manually close Chrome processes
# On Mac/Linux:
pkill chrome

# On Windows:
taskkill /IM chrome.exe /F
```

---

## 📊 Understanding Test Results

### Console Output Interpretation

**Test Passed:**
```
✓ Test Passed
✓ Browser closed
```

**Test Failed:**
```
✗ Test Failed - Capturing screenshot
✓ Browser closed
```

### HTML Report Structure

1. **Summary Section**
   - Total scenarios executed
   - Pass/fail count
   - Total duration

2. **Feature Details**
   - Each feature file listed
   - Scenarios with step-by-step results
   - Screenshots for failures

3. **Statistics**
   - Pass rate percentage
   - Average execution time
   - Tag-based filtering

---

## 🎓 Testing Best Practices

### Before Running Tests:
1. ✅ Ensure both frontend and backend are running
2. ✅ Close unnecessary browser windows
3. ✅ Check system resources (RAM, CPU)
4. ✅ Disable screen savers/sleep mode

### During Test Execution:
1. ⚠️ Don't interact with the browser
2. ⚠️ Don't minimize the browser window
3. ⚠️ Don't lock your screen
4. ⚠️ Ensure stable internet connection (if needed)

### After Test Execution:
1. 📊 Review test reports
2. 📸 Check screenshots for failures
3. 📝 Document any issues found
4. 🔄 Re-run failed tests if needed

---

## 🚦 Quick Start Checklist

Use this checklist for presentations or demonstrations:

### Setup Phase (One-time)
- [ ] Install Node.js, Java, Maven
- [ ] Extract project files
- [ ] Run `npm install` in frontend/
- [ ] Run `npm install` in backend/
- [ ] Run `mvn clean install` in automation-tests/

### Demo Phase (Each time)
- [ ] Start backend: `cd backend && npm start`
- [ ] Start frontend: `cd frontend && npm run dev`
- [ ] Verify http://localhost:3000 works
- [ ] Verify http://localhost:5000/api/health works
- [ ] Run tests: `cd automation-tests && mvn clean test`
- [ ] Open test report: `target/cucumber-reports/cucumber.html`

---

## 📞 Getting Help

### Common Commands Reference

```bash
# Check if Node.js is installed
node --version

# Check if Java is installed
java -version

# Check if Maven is installed
mvn -version

# Install frontend dependencies
cd frontend && npm install

# Install backend dependencies
cd backend && npm install

# Start backend server
cd backend && npm start

# Start frontend app
cd frontend && npm run dev

# Run all tests
cd automation-tests && mvn clean test

# Run specific test suite
mvn clean test -Dcucumber.filter.tags="@functional"

# View test report
open target/cucumber-reports/cucumber.html
```

---

## 🎉 Success Criteria

Your setup is successful when:

✅ Backend server starts without errors
✅ Frontend application loads in browser
✅ Manual login works with test credentials
✅ Automated tests execute successfully
✅ Test reports are generated
✅ All 60+ test scenarios pass

---

**Ready to impress your professors! Good luck with your project! 🚀**
