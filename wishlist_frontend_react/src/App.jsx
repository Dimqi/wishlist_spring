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
                    {/* 1. Публичная страница логина */}
                    <Route path="/login" element={<LoginPage />} />

                    {/* 2. Публичная страница просмотра вишлиста (БЕЗ PrivateRoute) */}
                    <Route path="/shared/:token" element={<SharedWishlist />} />

                    {/* 3. Защищенный роут для владельца */}
                    <Route
                        path="/dashboard"
                        element={
                            <PrivateRoute>
                                <Dashboard />
                            </PrivateRoute>
                        }
                    />

                    {/* 4. Если адрес не совпал ни с чем выше - на логин */}
                    <Route path="*" element={<Navigate to="/login" replace />} />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App;