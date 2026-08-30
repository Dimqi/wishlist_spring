import React from 'react';
import { Card } from 'primereact/card';
import { Badge } from 'primereact/badge';
import { Button } from 'primereact/button';

const WishCard = ({ wish, isOwner, onReserve, canReserve }) => {
    const isReserved = wish.reservedByUsernames && wish.reservedByUsernames.length > 0;

    const getSeverity = (p) => {
        const mapping = { 'MustHave': 'danger', 'ShouldHave': 'warning', 'CouldHave': 'info', 'WontHave': 'secondary' };
        return mapping[p] || 'info';
    };

    const footer = (
        <div className="flex justify-content-between align-items-center">
            {/* Ссылка на товар, если есть */}
            {wish.link ? (
                <a href={wish.link} target="_blank" rel="noreferrer" className="p-button p-component p-button-text p-button-sm no-underline">
                    <i className="pi pi-external-link mr-1"></i> Магазин
                </a>
            ) : <span></span>}

            {/* Кнопка бронирования для друзей */}
            {!isOwner && canReserve && !isReserved && (
                <Button
                    label="Забронировать"
                    icon="pi pi-check"
                    className="p-button-sm p-button-raised"
                    onClick={() => onReserve(wish.id)}
                />
            )}
        </div>
    );

    return (
        <Card className={`h-full shadow-2 ${isReserved ? 'opacity-80' : ''}`} footer={footer}>
            <div className="flex flex-column gap-2">
                <div className="flex justify-content-between align-items-start">
                    <span className={`text-xl font-bold ${isReserved ? 'line-through text-500' : ''}`}>
                        {wish.name}
                    </span>
                    <Badge value={wish.wishPriority} severity={getSeverity(wish.wishPriority)} />
                </div>

                {wish.tagName && (
                    <span className="text-sm text-500"><i className="pi pi-tag mr-1"></i>{wish.tagName}</span>
                )}

                {isReserved && (
                    <div className="mt-2 p-2 border-round bg-orange-50 text-orange-700 text-sm border-1 border-orange-100">
                        <i className="pi pi-lock mr-2"></i>
                        Уже забронировал: {wish.reservedByUsernames.join(', ')}
                    </div>
                )}
            </div>
        </Card>
    );
};

export default WishCard;