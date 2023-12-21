import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080'
});

export const useApi = () => ({
    validateToken: async (token: string) => {
        const response = await api.get('auth/validateToken?token=' + token);
        return response.data;
    },
    login: async (username: string, senha: string) => {
        const response = await api.post('/auth/login', {
            username: username,
            senha: senha,
        });
        return response.data;
    },
    logout: async () => {
        return { status: true };
        const response = await api.post('/logout');
        return response.data;
    },
});
