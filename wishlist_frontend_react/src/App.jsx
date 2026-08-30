import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';

// ИМПОРТЫ СТРАНИЦ
import LoginPage from './pages/LoginPage';
import Dashboard from './pages/Dashboard';       // Добавьте это
import SharedWishlist from './pages/SharedWishlist'; // И ЭТО

// Защищенный роут
const PrivateRoute = ({ children }) => {
    const { user, loading } = useAuth();
    if (loading) return <div>Загрузка...</div>;
    return user ? children : <Navigate replace to="/login" />;
};

function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <Routes>
                    {/* Публичные роуты */}
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/shared/:token" element={<SharedWishlist />} />

                    {/* Защищенные роуты */}
                    <Route
                        path="/dashboard"
                        element={
                            <PrivateRoute>
                                <Dashboard />
                            </PrivateRoute>
                        }
                    />

                    {/* Редирект по умолчанию */}
                    <Route path="*" element={<Navigate to="/login" />} />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App;