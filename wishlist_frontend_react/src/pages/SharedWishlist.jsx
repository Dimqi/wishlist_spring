import React, { useEffect, useState, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { shareService } from '../api/shareService';
import { wishService } from '../api/wishService';
import WishCard from '../components/WishCard';
import { useAuth } from '../context/AuthContext';
import { Toast } from 'primereact/toast';

const SharedWishlist = () => {
    const { token } = useParams();
    const { user } = useAuth();
    const [wishes, setWishes] = useState([]);
    const toast = useRef(null);

    const loadWishes = () => {
        shareService.getWishesByToken(token).then(res => {
            const data = res.data.data;
            setWishes(Array.isArray(data) ? data : (data ? [data] : []));
        });
    };

    useEffect(() => { loadWishes(); }, [token]);

    const onReserve = async (id) => {
        if (!user) {
            toast.current.show({ severity: 'warn', summary: 'Авторизация', detail: 'Войдите, чтобы бронировать подарки' });
            return;
        }
        try {
            await wishService.reserve(id, token);
            toast.current.show({ severity: 'success', summary: 'Забронировано', detail: 'Вы обещали это подарить!' });
            loadWishes();
        } catch (e) {
            toast.current.show({ severity: 'error', detail: 'Ошибка бронирования' });
        }
    };

    return (
        <div className="p-4">
            <Toast ref={toast} />
            <h2 className="mb-4">Список желаний друга</h2>
            <div className="grid">
                {wishes.map(w => (
                    <div key={w.id} className="col-12 md:col-4 lg:col-3">
                        <WishCard
                            wish={w}
                            isOwner={false}
                            canReserve={!!user}
                            onReserve={onReserve}
                            onClick={() => {}}
                        />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default SharedWishlist;