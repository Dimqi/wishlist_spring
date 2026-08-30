import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import { Message } from 'primereact/message';

import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

const LoginPage = () => {
    const [isLogin, setIsLogin] = useState(true); // Переключатель Вход/Регистрация
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const { login } = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        const endpoint = isLogin ? '/auth/login' : '/auth/register';

        try {
            const response = await api.post(endpoint, formData);
            // Согласно вашему DTO: response.data — это ApiResponseDto,
            // response.data.data — это UserDto, в котором лежит token
            if (response.data && response.data.data) {
                login(response.data.data);
            }
        } catch (err) {
            setError(err.response?.data?.message || 'Произошла ошибка при аутентификации');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex align-items-center justify-content-center" style={{ minHeight: '100vh' }}>
            <Card title={isLogin ? 'Вход' : 'Регистрация'} style={{ width: '100%', maxWidth: '400px' }}>
                <form onSubmit={handleSubmit} className="flex flex-column gap-3">
                    <div className="p-float-label">
                        <InputText
                            id="username"
                            value={formData.username}
                            onChange={(e) => setFormData({...formData, username: e.target.value})}
                            className="w-full"
                            required
                        />
                        <label htmlFor="username">Имя пользователя</label>
                    </div>

                    <div className="p-float-label">
                        <Password
                            id="password"
                            value={formData.password}
                            onChange={(e) => setFormData({...formData, password: e.target.value})}
                            toggleMask
                            feedback={!isLogin} // Показывать индикатор сложности только при регистрации
                            className="w-full"
                            inputClassName="w-full"
                            required
                        />
                        <label htmlFor="password">Пароль</label>
                    </div>

                    {error && <Message severity="error" text={error} />}

                    <Button label={isLogin ? 'Войти' : 'Создать аккаунт'} icon="pi pi-user" loading={loading} />

                    <Button
                        type="button"
                        label={isLogin ? 'Нет аккаунта? Зарегистрироваться' : 'Уже есть аккаунт? Войти'}
                        className="p-button-text"
                        onClick={() => setIsLogin(!isLogin)}
                    />
                </form>
            </Card>
        </div>
    );
};

export default LoginPage;