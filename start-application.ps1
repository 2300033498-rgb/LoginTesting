# Start Application Services (Manual Login Mode)
# This script starts ONLY the application - NO automation

Write-Host "╔════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║    SECURE LOGIN APPLICATION - MANUAL MODE         ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

$projectRoot = $PSScriptRoot

# Start Backend
Write-Host "📦 Starting Backend Server..." -ForegroundColor Yellow
$backend = Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$projectRoot\backend'; Write-Host ''; Write-Host '╔════════════════════════════╗' -ForegroundColor Green; Write-Host '║    BACKEND SERVER         ║' -ForegroundColor Green; Write-Host '╚════════════════════════════╝' -ForegroundColor Green; npm start" -PassThru

Start-Sleep -Seconds 5

# Start Frontend
Write-Host "🎨 Starting Frontend Application..." -ForegroundColor Yellow
$frontend = Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$projectRoot\frontend'; Write-Host ''; Write-Host '╔════════════════════════════╗' -ForegroundColor Blue; Write-Host '║   FRONTEND APPLICATION    ║' -ForegroundColor Blue; Write-Host '╚════════════════════════════╝' -ForegroundColor Blue; npm run dev" -PassThru

Start-Sleep -Seconds 5

# Verify Services
Write-Host ""
Write-Host "✅ Verifying Services..." -ForegroundColor Yellow

try {
    $backendHealth = Invoke-RestMethod -Uri "http://localhost:5000/api/health" -TimeoutSec 5
    Write-Host "   ✓ Backend is running on http://localhost:5000" -ForegroundColor Green
} catch {
    Write-Host "   ✗ Backend failed to start" -ForegroundColor Red
    Write-Host "   Please check the backend terminal for errors" -ForegroundColor Yellow
}

Start-Sleep -Seconds 2

try {
    $frontendCheck = Invoke-WebRequest -Uri "http://localhost:3000" -TimeoutSec 5 -UseBasicParsing
    Write-Host "   ✓ Frontend is running on http://localhost:3000" -ForegroundColor Green
} catch {
    Write-Host "   ✗ Frontend failed to start" -ForegroundColor Red
    Write-Host "   Please check the frontend terminal for errors" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "╔════════════════════════════════════════════════════╗" -ForegroundColor Green
Write-Host "║            ✅ APPLICATION READY!                   ║" -ForegroundColor Green
Write-Host "╚════════════════════════════════════════════════════╝" -ForegroundColor Green
Write-Host ""
Write-Host "🌐 Access Application:" -ForegroundColor White
Write-Host "   http://localhost:3000" -ForegroundColor Cyan
Write-Host ""
Write-Host "🔐 Test Credentials:" -ForegroundColor White
Write-Host "   Username: admin" -ForegroundColor Cyan
Write-Host "   Password: admin123" -ForegroundColor Cyan
Write-Host ""
Write-Host "📝 This is MANUAL LOGIN mode (normal usage)" -ForegroundColor Yellow
Write-Host "🤖 To run AUTOMATION TESTS separately, use: .\run-automation-tests.ps1" -ForegroundColor Yellow
Write-Host ""
Write-Host "⚠️  To stop: Close both PowerShell windows or press Ctrl+C in each" -ForegroundColor Gray
Write-Host ""

# Open browser after 3 seconds
Write-Host "Opening browser in 3 seconds..." -ForegroundColor Gray
Start-Sleep -Seconds 3
Start-Process "http://localhost:3000"

Write-Host "✨ Browser opened! You can now login manually." -ForegroundColor Green
Write-Host ""
