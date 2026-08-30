import React from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { Badge } from 'primereact/badge';
import { Tag } from 'primereact/tag';

const WishCard = ({ wish, onClick, onReserve, isOwner, canReserve }) => {
    const isReserved = wish.reservedByUsernames && wish.reservedByUsernames.length > 0;

    const getPrioritySeverity = (priority) => {
        switch (priority) {
            case 'MustHave': return 'danger';
            case 'ShouldHave': return 'warning';
            case 'CouldHave': return 'info';
            case 'WontHave': return 'secondary';
            default: return null;
        }
    };

    const footer = (
        <div className="flex justify-content-between align-items-center">
            <Button label="Инфо" icon="pi pi-search" onClick={() => onClick(wish)} className="p-button-text p-button-sm" />
            {!isOwner && canReserve && !isReserved && (
                <Button label="Борнь" icon="pi pi-bookmark" className="p-button-sm" onClick={() => onReserve(wish.id)} />
            )}
        </div>
    );

    return (
        <Card footer={footer} className="shadow-2 h-full border-round-xl">
            <div className="flex flex-column gap-2">
                <div className="flex justify-content-between align-items-start">
                    <span className="text-xl font-bold line-height-2">{wish.name}</span>
                    <Badge value={wish.wishPriority} severity={getPrioritySeverity(wish.wishPriority)} />
                </div>

                {wish.tagName && (
                    <Tag value={wish.tagName} severity="info" style={{background: '#e0f2fe', color: '#0369a1', width: 'fit-content'}} />
                )}

                {wish.link && (
                    <a href={wish.link} target="_blank" rel="noopener noreferrer" className="text-blue-600 no-underline hover:underline block mt-2">
                        <i className="pi pi-link mr-1 text-sm"></i> Ссылка
                    </a>
                )}

                {isReserved && (
                    <div className="mt-3 p-2 bg-orange-100 border-round flex align-items-center">
                        <i className="pi pi-lock mr-2 text-orange-700"></i>
                        <small className="text-orange-900 font-medium">
                            Занято: {wish.reservedByUsernames.join(', ')}
                        </small>
                    </div>
                )}
            </div>
        </Card>
    );
};

export default WishCard;