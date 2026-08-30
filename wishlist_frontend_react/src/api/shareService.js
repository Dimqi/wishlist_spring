import api from './axios';

export const shareService = {
    getLink: () => api.get('/share/link'),

    getWishesByToken: (token) => api.get(`/share/getAllByToken?token=${token}`)
};