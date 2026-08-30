import React, { useEffect, useState, useRef } from 'react';
import { wishService } from '../api/wishService';
import { shareService } from '../api/shareService'; // ВАЖНО: Добавили импорт
import WishCard from '../components/WishCard';
import WishDialog from '../components/WishDialog';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { ProgressSpinner } from 'primereact/progressspinner';

const Dashboard = () => {
    const [wishes, setWishes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showDialog, setShowDialog] = useState(false);
    const toast = useRef(null);

    const fetchWishes = async () => {
        setLoading(true);
        try {
            const response = await wishService.getAll();
            const result = response.data.listData;
            if (result) {
                setWishes(Array.isArray(result) ? result : [result]);
            } else {
                setWishes([]);
            }
        } catch (error) {
            console.error("Ошибка при получении желаний:", error);
            toast.current.show({
                severity: 'error',
                summary: 'Ошибка загрузки',
                detail: error.response?.data?.message || 'Сервер недоступен'
            });
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchWishes();
    }, []);

    const handleCreateWish = async (formData) => {
        try {
            await wishService.create(formData);
            setShowDialog(false);
            fetchWishes();
            toast.current.show({ severity: 'success', summary: 'Успех', detail: 'Желание добавлено' });
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось сохранить' });
        }
    };

    const copyShareLink = async () => {
        try {
            const response = await shareService.getLink();
            const data = response.data.data;

            const token = data.includes('token=') ? data.split('token=')[1] : data;

            const url = `${window.location.origin}/shared/${token}`;
            await navigator.clipboard.writeText(url);

            toast.current.show({
                severity: 'success',
                summary: 'Готово',
                detail: 'Ссылка на ваш вишлист скопирована!'
            });
        } catch (error) {
            toast.current.show({ severity: 'error', summary: 'Ошибка', detail: 'Не удалось создать ссылку' });
        }
    };


    return (
        <div className="p-4" style={{ maxWidth: '1200px', margin: '0 auto' }}>
            <Toast ref={toast} />

            <div className="flex justify-content-between align-items-center mb-5">
                <h1 className="m-0">Мои желания</h1>
                <div className="flex gap-2">
                    <Button
                        label="Поделиться"
                        icon="pi pi-share-alt"
                        className="p-button-outlined p-button-rounded"
                        onClick={copyShareLink}
                    />
                    {/* Кнопка "Добавить" */}
                    <Button
                        label="Добавить желание"
                        icon="pi pi-plus"
                        className="p-button-rounded"
                        onClick={() => setShowDialog(true)}
                    />
                </div>
            </div>

            {loading ? (
                <div className="flex justify-content-center mt-8">
                    <ProgressSpinner />
                </div>
            ) : (
                <div className="grid">
                    {wishes.length > 0 ? (
                        wishes.map((wish) => (
                            <div key={wish.id} className="col-12 md:col-6 lg:col-3 p-2">
                                <WishCard
                                    wish={wish}
                                    isOwner={true}
                                    onClick={(w) => console.log("Клик по", w)}
                                />
                            </div>
                        ))
                    ) : (
                        <div className="col-12 text-center p-8 mt-5 surface-100 border-round-xl">
                            <i className="pi pi-gift text-6xl text-400 mb-3"></i>
                            <p className="text-xl text-600">Ваш список пока пуст. Добавьте первое желание!</p>
                        </div>
                    )}
                </div>
            )}

            <WishDialog
                visible={showDialog}
                onHide={() => setShowDialog(false)}
                onSave={handleCreateWish}
            />
        </div>
    );
};

export default Dashboard;