# Run Automation Tests (Selenium + Cucumber)
# Run this ONLY when you need to execute automated tests
# Prerequisites: Backend and Frontend must be running!

Write-Host "╔════════════════════════════════════════════════════╗" -ForegroundColor Magenta
Write-Host "║    AUTOMATION TESTING MODE - SELENIUM + CUCUMBER  ║" -ForegroundColor Magenta
Write-Host "╚════════════════════════════════════════════════════╝" -ForegroundColor Magenta
Write-Host ""

$projectRoot = $PSScriptRoot

# Check Prerequisites
Write-Host "📋 Checking Prerequisites..." -ForegroundColor Yellow
Write-Host ""

# Check Backend
Write-Host "   Checking Backend (port 5000)..." -ForegroundColor Gray
try {
    $backendHealth = Invoke-RestMethod -Uri "http://localhost:5000/api/health" -TimeoutSec 3
    Write-Host "   ✓ Backend is running" -ForegroundColor Green
} catch {
    Write-Host "   ✗ Backend is NOT running!" -ForegroundColor Red
    Write-Host "" 
    Write-Host "   ⚠️  ERROR: You must start the backend first!" -ForegroundColor Red
    Write-Host "   Run this command in another terminal:" -ForegroundColor Yellow
    Write-Host "   cd backend && npm start" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "   Or run: .\start-application.ps1" -ForegroundColor Cyan
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

# Check Frontend
Write-Host "   Checking Frontend (port 3000)..." -ForegroundColor Gray
try {
    $frontendCheck = Invoke-WebRequest -Uri "http://localhost:3000" -TimeoutSec 3 -UseBasicParsing
    Write-Host "   ✓ Frontend is running" -ForegroundColor Green
} catch {
    Write-Host "   ✗ Frontend is NOT running!" -ForegroundColor Red
    Write-Host ""
    Write-Host "   ⚠️  ERROR: You must start the frontend first!" -ForegroundColor Red
    Write-Host "   Run this command in another terminal:" -ForegroundColor Yellow
    Write-Host "   cd frontend && npm run dev" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "   Or run: .\start-application.ps1" -ForegroundColor Cyan
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "✅ All prerequisites met! Ready to run tests." -ForegroundColor Green
Write-Host ""

# Ask user what to run
Write-Host "╔════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║           SELECT TEST CATEGORY TO RUN             ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""
Write-Host "1. Run ALL tests (60+ scenarios, ~5-7 minutes)" -ForegroundColor White
Write-Host "2. Functional Tests only (15 scenarios, ~2 minutes)" -ForegroundColor White
Write-Host "3. Boundary Tests only (20 scenarios, ~3 minutes)" -ForegroundColor White
Write-Host "4. Security Tests only (15 scenarios, ~2 minutes)" -ForegroundColor White
Write-Host "5. UI Validation Tests only (10 scenarios, ~2 minutes)" -ForegroundColor White
Write-Host "6. Exit" -ForegroundColor Gray
Write-Host ""

$choice = Read-Host "Enter your choice (1-6)"

$tags = ""
$testName = ""

switch ($choice) {
    "1" { 
        $testName = "ALL TESTS"
        Write-Host ""
        Write-Host "🚀 Running ALL 60+ test scenarios..." -ForegroundColor Yellow
    }
    "2" { 
        $tags = "-Dcucumber.filter.tags=@functional"
        $testName = "FUNCTIONAL TESTS"
        Write-Host ""
        Write-Host "🚀 Running Functional Tests (15 scenarios)..." -ForegroundColor Yellow
    }
    "3" { 
        $tags = "-Dcucumber.filter.tags=@boundary"
        $testName = "BOUNDARY TESTS"
        Write-Host ""
        Write-Host "🚀 Running Boundary Value Analysis Tests (20 scenarios)..." -ForegroundColor Yellow
    }
    "4" { 
        $tags = "-Dcucumber.filter.tags=@security"
        $testName = "SECURITY TESTS"
        Write-Host ""
        Write-Host "🚀 Running Security Tests (15 scenarios)..." -ForegroundColor Yellow
    }
    "5" { 
        $tags = "-Dcucumber.filter.tags=@ui"
        $testName = "UI VALIDATION TESTS"
        Write-Host ""
        Write-Host "🚀 Running UI Validation Tests (10 scenarios)..." -ForegroundColor Yellow
    }
    "6" {
        Write-Host "Exiting..." -ForegroundColor Gray
        exit 0
    }
    default {
        Write-Host "Invalid choice. Exiting..." -ForegroundColor Red
        exit 1
    }
}

Write-Host "   Chrome browser will open automatically" -ForegroundColor Gray
Write-Host "   Do NOT close the browser or interact with it" -ForegroundColor Gray
Write-Host ""
Write-Host "═══════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

# Run Tests
cd "$projectRoot\automation-tests"

if ($tags -eq "") {
    mvn clean test
} else {
    mvn clean test $tags
}

$exitCode = $LASTEXITCODE

Write-Host ""
Write-Host "═══════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

if ($exitCode -eq 0) {
    Write-Host "╔════════════════════════════════════════════════════╗" -ForegroundColor Green
    Write-Host "║         ✅ $testName COMPLETED!                    " -ForegroundColor Green
    Write-Host "╚════════════════════════════════════════════════════╝" -ForegroundColor Green
    Write-Host ""
    Write-Host "📊 Opening Test Report..." -ForegroundColor Yellow
    
    $reportPath = "$projectRoot\automation-tests\target\cucumber-reports\cucumber.html"
    
    if (Test-Path $reportPath) {
        Start-Sleep -Seconds 2
        Start-Process $reportPath
        Write-Host "   ✓ Report opened in browser" -ForegroundColor Green
    } else {
        Write-Host "   ✗ Report not found at: $reportPath" -ForegroundColor Red
    }
    
    Write-Host ""
    Write-Host "📁 Report Location:" -ForegroundColor White
    Write-Host "   $reportPath" -ForegroundColor Cyan
    
} else {
    Write-Host "╔════════════════════════════════════════════════════╗" -ForegroundColor Red
    Write-Host "║         ❌ TESTS FAILED OR ENCOUNTERED ERRORS     ║" -ForegroundColor Red
    Write-Host "╚════════════════════════════════════════════════════╝" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please check the console output above for error details." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Common Issues:" -ForegroundColor White
    Write-Host "  - Backend or Frontend not running properly" -ForegroundColor Gray
    Write-Host "  - Firewall blocking connections" -ForegroundColor Gray
    Write-Host "  - ChromeDriver version mismatch" -ForegroundColor Gray
    Write-Host ""
}

Write-Host ""
Write-Host "════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""
Read-Host "Press Enter to exit"
