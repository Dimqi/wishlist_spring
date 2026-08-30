import api from './axios';

export const shareService = {
    // Получить токен-ссылку текущего пользователя
    getLink: () => api.get('/share/link'),
    // Получить желания другого пользователя по токену
    getWishesByToken: (token) => api.get(`/share/getAllByToken?token=${token}`)
};