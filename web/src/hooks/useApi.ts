import axios from 'axios';
import { Despesa } from '../types/Despesa';
import { Tarefa } from '../types/Tarefa';
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
    listarDespesas: async (token: string, ano: number, mes: number): Promise<Despesa[]> => {
        try {
            const response = await api.get(`/despesa/listar?ano=${ano}&mes=${mes}`, {
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
    listarTodasCategoriasDespesa: async (token: string): Promise<Categoria[]> => {
        try {
            const response = await api.get('/categoriadespesa/getTodasCategoriasDespesa', {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao listar as categorias de despesa:', error.message);
            throw error;
        }
    },
    listarTarefas: async (token: string, ano: number, mes: number): Promise<Tarefa[]> => {
        try {
            const response = await api.get(`/tarefa/listar?ano=${ano}&mes=${mes}`, {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao listar as tarefas:', error.message);
            throw error;
        }
    },
    addTarefa: async (token: string, data: Tarefa) => {
        try {
            const response = await api.post('/tarefa/add', {
                idCategoria: data.idCategoria,
                titulo: data.titulo,
                descricao: data.descricao,
                dataInclusao: data.dataInclusao,
                dataPrazo: data.dataPrazo,
                finalizado: data.finalizado,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
    
            return response.data;
        } catch (error: any) {
            console.error('Erro ao adicionar tarefa:', error.message);
            throw error;
        }
    },
    editarTarefa: async (token: string, data: Tarefa) => {
        try {
            const response = await api.put(`/tarefa/editar/${data.id}`, {
                idCategoria: data.idCategoria,
                titulo: data.titulo,
                descricao: data.descricao,
                dataInclusao: data.dataInclusao,
                dataPrazo: data.dataPrazo,
                finalizado: data.finalizado,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
    
            return response.data;
        } catch (error: any) {
            console.error('Erro ao editar tarefa:', error.message);
            throw error;
        }
    },
    excluirTarefa: async (token: string, id: number) => {
        try {
            const response = await api.delete(`/tarefa/excluir/${id}`, {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao deletar a tarefa:', error.message);
            throw error;
        }
    },
    listarTodasCategoriasTarefa: async (token: string): Promise<Categoria[]> => {
        try {
            const response = await api.get('/categoriadespesa/getTodasCategoriasTarefa', {
                headers: {
                Authorization: `Bearer ${token}`,
                },
            });
        
            return response.data;
        } catch (error: any) {
            console.error('Erro ao listar as categorias de tarefa:', error.message);
            throw error;
        }
    },
});
