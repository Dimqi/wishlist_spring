import React, { useState, useEffect } from 'react';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';

const WishDialog = ({ visible, onHide, onSave, wish }) => {
    const [formData, setFormData] = useState({
        name: '',
        link: '',
        wishPriority: 'ShouldHave',
        tagName: ''
    });

    const priorities = [
        { label: 'Обязательно (Must Have)', value: 'MustHave' },
        { label: 'Желательно (Should Have)', value: 'ShouldHave' },
        { label: 'Можно (Could Have)', value: 'CouldHave' },
        { label: 'В другой раз (Wont Have)', value: 'WontHave' }
    ];

    useEffect(() => {
        if (wish) {
            setFormData({
                ...wish,
                tagName: wish.tagName || ''
            });
        } else {
            setFormData({ name: '', link: '', wishPriority: 'ShouldHave', tagName: '' });
        }
    }, [wish, visible]);

    const handleSave = () => {
        onSave(formData);
    };

    return (
        <Dialog header={wish ? "Редактировать" : "Новое желание"} visible={visible} style={{ width: '450px' }} onHide={onHide}>
            <div className="flex flex-column gap-4 mt-2">
                <div className="p-float-label">
                    <InputText id="name" value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} className="w-full" />
                    <label htmlFor="name">Название желания</label>
                </div>

                <div className="p-float-label">
                    <InputText id="link" value={formData.link} onChange={(e) => setFormData({...formData, link: e.target.value})} className="w-full" />
                    <label htmlFor="link">Ссылка (URL)</label>
                </div>

                <div className="flex flex-column gap-2">
                    <label className="font-bold">Приоритет</label>
                    <Dropdown
                        value={formData.wishPriority}
                        options={priorities}
                        onChange={(e) => setFormData({...formData, wishPriority: e.value})}
                        className="w-full"
                    />
                </div>

                <div className="p-float-label">
                    <InputText id="tag" value={formData.tagName} onChange={(e) => setFormData({...formData, tagName: e.target.value})} className="w-full" />
                    <label htmlFor="tag">Категория (тег)</label>
                </div>

                <Button label="Сохранить" icon="pi pi-check" onClick={handleSave} disabled={!formData.name || formData.name.length < 3} />
            </div>
        </Dialog>
    );
};

export default WishDialog;