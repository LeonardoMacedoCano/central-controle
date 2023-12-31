import axios from 'axios';
import { Despesa } from '../types/Despesa';
import { Categoria } from '../types/Categoria';

const api = axios.create({
    baseURL: 'http://localhost:8080'
});

export const useApi = () => ({
    validateToken: async (token: string) => {
        try {
            const response = await api.get('auth/validateToken?token=' + token);
            return response.data;
        } catch (error: any) {
            console.error('Erro ao validar token:', error.message);
            throw error;
        }
    },
    login: async (username: string, senha: string) => {
        try {
            const response = await api.post('/auth/login', {
                username: username,
                senha: senha,
            });
            return response.data;
        } catch (error: any) {
            console.error('Erro ao logar:', error.message);
            throw error;
        }
    },
    logout: async () => {
        return { status: true };
        const response = await api.post('/logout');
        return response.data;
    },
    listarDespesas: async (token: string): Promise<Despesa[]> => {
        try {
            const response = await api.get('/despesa/listar', {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao listar as despesas:', error.message);
            throw error;
        }
    },
    addDespesa: async (token: string, data: Despesa) => {
        try {
            const response = await api.post('/despesa/add', {
                idCategoria: data.idCategoria,
                descricao: data.descricao,
                valor: data.valor,
                data: data.data,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
    
            return response.data;
        } catch (error: any) {
            console.error('Erro ao adicionar despesa:', error.message);
            throw error;
        }
    },
    editarDespesa: async (token: string, data: Despesa) => {
        try {
            const response = await api.put(`/despesa/editar/${data.id}`, {
                idCategoria: data.idCategoria,
                descricao: data.descricao,
                valor: data.valor,
                data: data.data,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
    
            return response.data;
        } catch (error: any) {
            console.error('Erro ao editar despesa:', error.message);
            throw error;
        }
    },
    excluirDespesa: async (token: string, id: number) => {
        try {
            const response = await api.delete(`/despesa/excluir/${id}`, {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao deletar a despesa:', error.message);
            throw error;
        }
    },
    listarTodasCategoriasDespesas: async (token: string): Promise<Categoria[]> => {
        try {
            const response = await api.get('/categoriadespesa/getTodasCategoriasDespesa', {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao listar as categorias de despesas:', error.message);
            throw error;
        }
    },
});
