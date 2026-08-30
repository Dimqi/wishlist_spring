import React from 'react';
import { Card } from 'primereact/card';
import { Badge } from 'primereact/badge';
import { Button } from 'primereact/button';
import { Tag } from 'primereact/tag';

const WishCard = ({ wish, isOwner, onReserve, onUnreserve, user }) => {

    const alreadyReservedByMe = user && wish.reservedByUsernames?.includes(user.username);

    const hasAnyReservations = wish.reservedByUsernames?.length > 0;

    const getSeverity = (p) => {
        const mapping = {
            'MustHave': 'danger',
            'ShouldHave': 'warning',
            'CouldHave': 'info',
            'WontHave': 'secondary'
        };
        return mapping[p] || 'info';
    };

    return (
        <Card
            className={`h-full shadow-2 flex flex-column ${alreadyReservedByMe ? 'border-primary border-2' : ''}`}
            style={{ display: 'flex', flexDirection: 'column', height: '100%' }}
            pt={{
                body: { className: 'flex-1 flex flex-column' },
                content: { className: 'flex-1 flex flex-column' }
            }}
        >
            {/* Название и Приоритет */}
            <div className="flex justify-content-between align-items-start mb-2">
                <span className="text-xl font-bold">{wish.name}</span>
                <Badge value={wish.wishPriority} severity={getSeverity(wish.wishPriority)} />
            </div>

            {/* Тег */}
            <div className="mb-3">
                {wish.tagName && <Tag value={wish.tagName} severity="info" className="w-fit" />}
            </div>

            {/* Информация о тех, кто забронировал */}
            {hasAnyReservations && (
                <div className={`p-2 border-round text-xs mb-3 border-1 ${alreadyReservedByMe ? 'bg-primary-50 border-primary-100 text-primary-800' : 'bg-blue-50 border-blue-100 text-blue-800'}`}>
                    <i className={`pi ${alreadyReservedByMe ? 'pi-check-circle' : 'pi-users'} mr-2`}></i>
                    {alreadyReservedByMe ? (
                        <span><b>Вы забронировали этот подарок</b> {wish.reservedByUsernames.length > 1 && `(+ еще ${wish.reservedByUsernames.length - 1})`}</span>
                    ) : (
                        <span>Забронировали: {wish.reservedByUsernames.join(', ')}</span>
                    )}
                </div>
            )}

            {/* НИЗ КАРТОЧКИ */}
            <div className="mt-auto pt-3 border-top-1 border-100 flex flex-column gap-2">

                {/* Ссылка */}
                <div style={{ minHeight: '2rem', display: 'flex', alignItems: 'center' }}>
                    {wish.link ? (
                        <a href={wish.link} target="_blank" rel="noreferrer" className="p-button p-button-text p-button-sm p-0 no-underline" style={{ color: '#2196F3' }}>
                            <i className="pi pi-link mr-2"></i><span className="font-medium text-sm">Ссылка</span>
                        </a>
                    ) : <span className="text-400 text-xs italic">Без ссылки</span>}
                </div>

                {/* ЛОГИКА КНОПОК */}
                <div style={{ minHeight: '2.5rem' }}>
                    {!isOwner && (
                        <>
                            {alreadyReservedByMe ? (
                                <Button
                                    label="Отменить мою бронь"
                                    icon="pi pi-times"
                                    className="p-button-sm p-button-danger p-button-outlined w-full"
                                    onClick={() => onUnreserve(wish.id)}
                                />
                            ) : (
                                <Button
                                    label={user ? "Забронировать" : "Войти и забронировать"}
                                    icon={user ? "pi pi-check" : "pi pi-sign-in"}
                                    className={user ? "p-button-sm w-full" : "p-button-sm p-button-warning w-full"}
                                    onClick={() => onReserve(wish.id)}
                                />
                            )}
                        </>
                    )}

                    {isOwner && (
                        <div className="text-center text-500 text-xs surface-50 border-round p-2 border-1 border-200">
                            <i className="pi pi-user mr-2"></i> Ваше желание
                        </div>
                    )}
                </div>
            </div>
        </Card>
    );
};

export default WishCard;