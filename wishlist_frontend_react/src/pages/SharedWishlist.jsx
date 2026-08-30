import React, { useEffect, useState, useRef } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { shareService } from '../api/shareService';
import { wishService } from '../api/wishService';
import { useAuth } from '../context/AuthContext';
import WishCard from '../components/WishCard';
import { Toast } from 'primereact/toast';
import { ProgressSpinner } from 'primereact/progressspinner';

const SharedWishlist = () => {
    const { token } = useParams();
    const { user } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const [wishes, setWishes] = useState([]);
    const [loading, setLoading] = useState(true);
    const toast = useRef(null);

    const loadWishes = async () => {
        setLoading(true);
        try {
            const res = await shareService.getWishesByToken(token);
            const data = res.data.listData;
            setWishes(Array.isArray(data) ? data : (data ? [data] : []));
        } finally { setLoading(false); }
    };

    useEffect(() => { loadWishes(); }, [token]);

    const handleReserve = async (id) => {
        if (!user) {
            toast.current.show({ severity: 'info', summary: 'Вход', detail: 'Нужна авторизация' });
            setTimeout(() => navigate('/login', { state: { from: location.pathname } }), 1000);
            return;
        }
        try {
            await wishService.reserve(id, token);
            toast.current.show({ severity: 'success', summary: 'Успешно' });
            loadWishes();
        } catch (e) { toast.current.show({ severity: 'error', summary: 'Ошибка' }); }
    };

    const handleUnreserve = async (id) => {
        try {
            await wishService.unreserve(id);
            toast.current.show({ severity: 'info', summary: 'Бронь отменена' });
            loadWishes();
        } catch (e) { toast.current.show({ severity: 'error', summary: 'Не удалось отменить' }); }
    };

    return (
        <div className="p-4 mx-auto" style={{maxWidth: '1200px'}}>
            <Toast ref={toast} />
            <h1 className="text-center mb-5 font-bold">Вишлист друга</h1>
            {loading ? <div className="flex justify-content-center mt-8"><ProgressSpinner /></div> : (
                <div className="grid">
                    {wishes.map(w => (
                        <div key={w.id} className="col-12 md:col-6 lg:col-3 p-2 flex">
                            <WishCard
                                wish={w}
                                isOwner={false}
                                user={user}
                                onReserve={handleReserve}
                                onUnreserve={handleUnreserve}
                            />
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default SharedWishlist;