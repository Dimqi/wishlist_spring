import React, { createContext, useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        // Проверяем наличие токена при загрузке приложения
        const savedToken = localStorage.getItem('token');
        if (savedToken) {
            // В идеале здесь нужно сделать запрос /me для проверки валидности токена
            setUser({ token: savedToken });
        }
        setLoading(false);
    }, []);

    const login = (userData) => {
        // Сохраняем токен, полученный из response.data.token
        const token = userData.token;
        localStorage.setItem('token', token);
        setUser(userData);
        navigate('/dashboard');
    };

    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        navigate('/login');
    };

    return (
        <AuthContext.Provider value={{ user, login, logout, loading }}>
            {!loading && children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);