const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const { body, validationResult } = require('express-validator');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 5000;

// ==================== MIDDLEWARE ====================

// Security headers
app.use(helmet());

// CORS configuration
app.use(cors({
  origin: process.env.ALLOWED_ORIGINS || 'http://localhost:3000',
  credentials: true
}));

// Body parser
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Request logging
app.use((req, res, next) => {
  console.log(`[${new Date().toISOString()}] ${req.method} ${req.url}`);
  next();
});

// ==================== MOCK DATABASE ====================

/**
 * Mock user database for testing
 * In production, this would be replaced with actual database
 */
const users = [
  {
    id: 1,
    username: 'admin',
    password: 'admin123', // In production: use bcrypt hashing
    email: 'admin@example.com',
    role: 'Administrator'
  },
  {
    id: 2,
    username: 'testuser',
    password: 'test1234',
    email: 'test@example.com',
    role: 'User'
  },
  {
    id: 3,
    username: 'john.doe',
    password: 'john@123',
    email: 'john.doe@example.com',
    role: 'User'
  }
];

// ==================== VALIDATION MIDDLEWARE ====================

/**
 * Validates login request inputs
 * Implements boundary value analysis and security checks
 */
const loginValidation = [
  body('username')
    .trim()
    .notEmpty().withMessage('Username is required')
    .isLength({ min: 1, max: 255 }).withMessage('Username must be between 1 and 255 characters')
    .matches(/^[^<>]*$/).withMessage('Username contains invalid characters')
    .custom((value) => {
      // SQL Injection pattern detection
      const sqlPatterns = /(\bOR\b|\bAND\b|--|#|\/\*|\*\/|xp_|sp_|'|")/i;
      if (sqlPatterns.test(value)) {
        throw new Error('Invalid characters detected in username');
      }
      return true;
    }),
  
  body('password')
    .notEmpty().withMessage('Password is required')
    .isLength({ min: 6, max: 128 }).withMessage('Password must be between 6 and 128 characters')
    .custom((value) => {
      // Script injection pattern detection
      const scriptPatterns = /<script|javascript:|onerror=|onload=/i;
      if (scriptPatterns.test(value)) {
        throw new Error('Invalid characters detected in password');
      }
      return true;
    })
];

// ==================== HELPER FUNCTIONS ====================

/**
 * Sanitizes user input to prevent XSS attacks
 */
const sanitizeInput = (input) => {
  return input
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#x27;')
    .replace(/\//g, '&#x2F;');
};

/**
 * Finds user by username or email
 */
const findUser = (identifier) => {
  return users.find(user => 
    user.username.toLowerCase() === identifier.toLowerCase() ||
    user.email.toLowerCase() === identifier.toLowerCase()
  );
};

/**
 * Verifies user credentials
 * In production: use bcrypt.compare()
 */
const verifyPassword = (inputPassword, storedPassword) => {
  return inputPassword === storedPassword;
};

// ==================== ROUTES ====================

/**
 * Health check endpoint
 */
app.get('/api/health', (req, res) => {
  res.json({
    status: 'OK',
    message: 'Server is running',
    timestamp: new Date().toISOString()
  });
});

/**
 * Login endpoint
 * POST /api/auth/login
 * 
 * Request Body:
 * {
 *   "username": "admin",
 *   "password": "admin123"
 * }
 * 
 * Response (Success):
 * {
 *   "success": true,
 *   "message": "Login successful",
 *   "user": { ... }
 * }
 * 
 * Response (Failure):
 * {
 *   "success": false,
 *   "message": "Error message"
 * }
 */
app.post('/api/auth/login', loginValidation, (req, res) => {
  // Check for validation errors
  const errors = validationResult(req);
  
  if (!errors.isEmpty()) {
    const errorMessages = errors.array().map(err => err.msg);
    console.log(`[VALIDATION ERROR] ${errorMessages.join(', ')}`);
    
    return res.status(400).json({
      success: false,
      message: errorMessages[0], // Return first error
      errors: errorMessages
    });
  }

  const { username, password } = req.body;

  // Log login attempt (sanitized for security)
  console.log(`[LOGIN ATTEMPT] Username: ${sanitizeInput(username)}`);

  // Boundary Testing: Check for empty values (additional check)
  if (!username || !password) {
    console.log('[LOGIN FAILED] Empty credentials');
    return res.status(400).json({
      success: false,
      message: 'Username and password are required'
    });
  }

  // Boundary Testing: Check length constraints
  if (username.length > 255) {
    console.log('[LOGIN FAILED] Username too long');
    return res.status(400).json({
      success: false,
      message: 'Username exceeds maximum length'
    });
  }

  if (password.length < 6) {
    console.log('[LOGIN FAILED] Password too short');
    return res.status(400).json({
      success: false,
      message: 'Password must be at least 6 characters'
    });
  }

  if (password.length > 128) {
    console.log('[LOGIN FAILED] Password too long');
    return res.status(400).json({
      success: false,
      message: 'Password exceeds maximum length'
    });
  }

  // Security Testing: Detect SQL Injection attempts
  const sqlInjectionPattern = /(\bOR\b.*=.*|'.*OR.*'.*=.*'|".*OR.*".*=.*"|--)/i;
  if (sqlInjectionPattern.test(username) || sqlInjectionPattern.test(password)) {
    console.log('[SECURITY ALERT] SQL Injection attempt detected');
    return res.status(403).json({
      success: false,
      message: 'Invalid credentials format detected'
    });
  }

  // Security Testing: Detect XSS/Script Injection attempts
  const xssPattern = /<script|javascript:|onerror=|onload=|<img|<iframe/i;
  if (xssPattern.test(username) || xssPattern.test(password)) {
    console.log('[SECURITY ALERT] XSS/Script injection attempt detected');
    return res.status(403).json({
      success: false,
      message: 'Invalid characters in credentials'
    });
  }

  // Find user in mock database
  const user = findUser(username);

  if (!user) {
    console.log(`[LOGIN FAILED] User not found: ${sanitizeInput(username)}`);
    return res.status(401).json({
      success: false,
      message: 'Invalid username or password'
    });
  }

  // Verify password
  if (!verifyPassword(password, user.password)) {
    console.log(`[LOGIN FAILED] Invalid password for user: ${sanitizeInput(username)}`);
    return res.status(401).json({
      success: false,
      message: 'Invalid username or password'
    });
  }

  // Successful login
  console.log(`[LOGIN SUCCESS] User: ${sanitizeInput(user.username)}`);
  
  // Return user data (exclude password)
  const { password: _, ...userWithoutPassword } = user;
  
  res.status(200).json({
    success: true,
    message: 'Login successful',
    user: userWithoutPassword
  });
});

/**
 * Get all users endpoint (for testing purposes only)
 * In production, this should be protected and limited
 */
app.get('/api/users', (req, res) => {
  const usersWithoutPasswords = users.map(({ password, ...user }) => user);
  res.json({
    success: true,
    users: usersWithoutPasswords
  });
});

// ==================== ERROR HANDLING ====================

/**
 * 404 handler
 */
app.use((req, res) => {
  res.status(404).json({
    success: false,
    message: 'Endpoint not found'
  });
});

/**
 * Global error handler
 */
app.use((err, req, res, next) => {
  console.error('[ERROR]', err.stack);
  res.status(500).json({
    success: false,
    message: 'Internal server error'
  });
});

// ==================== SERVER STARTUP ====================

app.listen(PORT, () => {
  console.log('╔════════════════════════════════════════════════════╗');
  console.log('║     SECURE LOGIN BACKEND - TESTING PROJECT        ║');
  console.log('╚════════════════════════════════════════════════════╝');
  console.log(`🚀 Server running on http://localhost:${PORT}`);
  console.log(`📝 Environment: ${process.env.NODE_ENV || 'development'}`);
  console.log(`✅ CORS enabled for: ${process.env.ALLOWED_ORIGINS || 'http://localhost:3000'}`);
  console.log('');
  console.log('Available Endpoints:');
  console.log('  GET  /api/health        - Health check');
  console.log('  POST /api/auth/login    - User login');
  console.log('  GET  /api/users         - List users (testing only)');
  console.log('');
  console.log('Test Credentials:');
  console.log('  Username: admin      | Password: admin123');
  console.log('  Username: testuser   | Password: test1234');
  console.log('  Username: john.doe   | Password: john@123');
  console.log('════════════════════════════════════════════════════');
});

module.exports = app;
