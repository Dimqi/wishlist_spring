import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Message } from 'primereact/message';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

const LoginPage = () => {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        const endpoint = isLogin ? '/auth/login' : '/auth/register';

        try {
            const response = await api.post(endpoint, formData);
            if (response.data && response.data.data) {
                login(response.data.data);

                const destination = location.state?.from || '/dashboard';
                navigate(destination, { replace: true });
            }
        } catch (err) {
            setError('Неверный логин или пароль');
        }
    };

    return (
        <div className="flex align-items-center justify-content-center mt-8">
            <Card title={isLogin ? 'Вход' : 'Регистрация'} style={{ width: '360px' }}>
                <form onSubmit={handleSubmit} className="flex flex-column gap-3">
                    <InputText
                        placeholder="Имя пользователя"
                        value={formData.username}
                        onChange={(e) => setFormData({...formData, username: e.target.value})}
                        required
                    />
                    <Password
                        placeholder="Пароль"
                        value={formData.password}
                        onChange={(e) => setFormData({...formData, password: e.target.value})}
                        feedback={false}
                        toggleMask
                        required
                    />
                    {error && <Message severity="error" text={error} />}
                    <Button label={isLogin ? 'Войти' : 'Создать аккаунт'} type="submit" />
                    <Button
                        label={isLogin ? 'Нет аккаунта? Зарегистрироваться' : 'Уже есть аккаунт? Войти'}
                        className="p-button-text p-button-sm"
                        type="button"
                        onClick={() => setIsLogin(!isLogin)}
                    />
                </form>
            </Card>
        </div>
    );
};

export default LoginPage;