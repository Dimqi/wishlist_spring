import api from './axios';

export const wishService = {
    getAll: () => api.get('/wishes/all'),

    create: (wishData) => api.post('/wishes', wishData),

    delete: (id) => api.delete(`/wishes/${id}`),

    reserve: (wishId, token) =>
        api.patch(`/wishes/reserveWish?token=${token}&wish_id=${wishId}`),

    unreserve: (wishId) =>
        api.patch(`/wishes/unReserveWish?wish_id=${wishId}`)
};