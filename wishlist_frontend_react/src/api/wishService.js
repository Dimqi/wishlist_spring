import api from './axios';

export const wishService = {
    getAll: () => api.get('/wishes/all'),
    getById: (id) => api.get(`/wishes/${id}`),
    // Используем WishRequestDTO
    create: (wishData) => api.post('/wishes', wishData),
    delete: (id) => api.delete(`/wishes/${id}`),

    // Передача через @RequestParam
    addTagToWish: (wishId, tagName) =>
        api.patch(`/wishes/addTagToWish?wish_id=${wishId}&name=${tagName}`),

    reserve: (wishId, token) =>
        api.patch(`/wishes/reserveWish?wish_id=${wishId}&token=${token}`),

    unreserve: (wishId) =>
        api.patch(`/wishes/unReserveWish?wish_id=${wishId}`)
};