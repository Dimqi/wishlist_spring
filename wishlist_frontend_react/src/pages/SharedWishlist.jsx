import React, { useEffect, useState, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { shareService } from '../api/shareService';
import { wishService } from '../api/wishService';
import { useAuth } from '../context/AuthContext';
import WishCard from '../components/WishCard';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { ProgressSpinner } from 'primereact/progressspinner';

const SharedWishlist = () => {
    const { token } = useParams();
    const { user } = useAuth();
    const navigate = useNavigate();
    const [wishes, setWishes] = useState([]);
    const [loading, setLoading] = useState(true);
    const toast = useRef(null);

    const loadWishes = async () => {
        setLoading(true);
        try {
            const response = await shareService.getWishesByToken(token);

            const result = response.data.listData || response.data.data;

            if (result) {
                setWishes(Array.isArray(result) ? result : [result]);
            } else {
                setWishes([]);
            }
        } catch (error) {
            console.error("Ошибка загрузки публичного списка:", error);
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Список не найден или токен невалиден' });
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { loadWishes(); }, [token]);

    const handleReserve = async (wishId) => {
        if (!user) {
            toast.current.show({
                severity: 'info',
                summary: 'Нужна авторизация',
                detail: 'Войдите, чтобы забронировать подарок'
            });
            return;
        }

        try {
            await wishService.reserve(wishId, token);
            toast.current.show({ severity: 'success', summary: 'Успех', detail: 'Вы забронировали этот подарок!' });
            loadWishes(); // Перегружаем список, чтобы увидеть изменения
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось забронировать' });
        }
    };

    return (
        <div className="p-4" style={{ maxWidth: '1200px', margin: '0 auto' }}>
            <Toast ref={toast} />

            <div className="flex justify-content-between align-items-center mb-5">
                <h1>Список желаний друга</h1>
                {!user && (
                    <Button label="Войти" icon="pi pi-sign-in" onClick={() => navigate('/login')} className="p-button-text" />
                )}
            </div>

            {loading ? (
                <div className="flex justify-content-center mt-8"><ProgressSpinner /></div>
            ) : (
                <div className="grid">
                    {wishes.map(wish => (
                        <div key={wish.id} className="col-12 md:col-6 lg:col-3 p-2">
                            <WishCard
                                wish={wish}
                                isOwner={false}
                                canReserve={true}
                                onReserve={handleReserve}
                            />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default SharedWishlist;