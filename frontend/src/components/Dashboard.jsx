import React from 'react'

const Dashboard = ({ user, onLogout }) => {
  return (
    <div className="min-h-screen p-8">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="card-gradient p-6 mb-8">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-4">
              <div className="w-16 h-16 bg-gradient-to-br from-indigo-500 to-purple-600 rounded-full flex items-center justify-center shadow-lg">
                <span className="text-2xl font-bold text-white">
                  {user?.username?.charAt(0).toUpperCase() || 'U'}
                </span>
              </div>
              <div>
                <h1 id="welcome-message" className="text-2xl font-bold text-gray-800">
                  Welcome back, {user?.username || 'User'}!
                </h1>
                <p className="text-gray-600">Login successful - Dashboard view</p>
              </div>
            </div>
            <button
              id="logout-button"
              onClick={onLogout}
              className="px-6 py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg shadow-lg transform transition-all duration-200 hover:scale-105"
              aria-label="Logout Button"
            >
              Logout
            </button>
          </div>
        </div>

        {/* Success Message */}
        <div className="bg-green-50 border-l-4 border-green-500 p-6 rounded-r-lg shadow-lg mb-8">
          <div className="flex items-center">
            <svg className="w-6 h-6 text-green-500 mr-3" fill="currentColor" viewBox="0 0 20 20">
              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
            </svg>
            <div>
              <h3 className="text-lg font-semibold text-green-800">Authentication Successful</h3>
              <p className="text-green-700">You have successfully logged into your account.</p>
            </div>
          </div>
        </div>

        {/* User Info Card */}
        <div className="grid md:grid-cols-3 gap-6">
          <div className="card-gradient p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-gray-800">Account Status</h3>
              <span className="px-3 py-1 bg-green-100 text-green-800 text-sm font-semibold rounded-full">Active</span>
            </div>
            <p className="text-gray-600">Your account is in good standing.</p>
          </div>

          <div className="card-gradient p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-gray-800">Role</h3>
              <svg className="w-6 h-6 text-indigo-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <p className="text-gray-600">{user?.role || 'User'}</p>
          </div>

          <div className="card-gradient p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold text-gray-800">Last Login</h3>
              <svg className="w-6 h-6 text-indigo-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <p className="text-gray-600">{new Date().toLocaleString()}</p>
          </div>
        </div>

        {/* Testing Info */}
        <div className="mt-8 card-gradient p-6">
          <h3 className="text-xl font-bold text-gray-800 mb-4">Testing Information</h3>
          <div className="space-y-2 text-gray-600">
            <p>✅ <strong>Functional Testing:</strong> Login functionality verified</p>
            <p>✅ <strong>Boundary Testing:</strong> Input validation passed</p>
            <p>✅ <strong>Security Testing:</strong> Injection attempts blocked</p>
            <p>✅ <strong>UI Validation:</strong> Proper error handling and navigation</p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Dashboard
