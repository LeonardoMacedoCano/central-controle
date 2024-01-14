import axios from 'axios';
import { Despesa } from '../types/Despesa';
import { Tarefa } from '../types/Tarefa';
import { useMessages } from '../contexts/Mensagens';

const api = axios.create({
    baseURL: 'http://localhost:8080'
});

export const useApi = () => {
    const messages = useMessages();

    const getMensagemErro = (error: any, mensagemPadrao: string) => {
        if (error.response && error.response.data && error.response.data.error) {
            messages.exibirErro(error.response.data.error);
        } else {
            messages.exibirErro(mensagemPadrao);
        }
    }

    return {
        validateToken: async (token: string) => {
            try {
                const response = await api.get('auth/validateToken?token=' + token);
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao validar token.');
            }
        },
        login: async (username: string, senha: string) => {
            try {
                console.log('aaa');
                const response = await api.post('/auth/login', {
                    username: username,
                    senha: senha,
                });
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao logar.');
            }
        },
        listarDespesas: async (token: string, ano: number, mes: number) => {
            try {
                const response = await api.get(`/despesa/listar?ano=${ano}&mes=${mes}`, {
                    headers: {
                    Authorization: `Bearer ${token}`,
                    },
                });
            
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao listar as despesas.');
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
                getMensagemErro(error, 'Erro ao adicionar despesa.');
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
                getMensagemErro(error, 'Erro ao editar despesa.');
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
                getMensagemErro(error, 'Erro ao deletar a despesa.');
            }
        },
        listarTodasCategoriasDespesa: async (token: string) => {
            try {
                const response = await api.get('/categoriadespesa/getTodasCategoriasDespesa', {
                    headers: {
                    Authorization: `Bearer ${token}`,
                    },
                });
            
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao listar as categorias de despesa.');
            }
        },
        listarTarefas: async (token: string, ano: number, mes: number) => {
            try {
                const response = await api.get(`/tarefa/listar?ano=${ano}&mes=${mes}`, {
                    headers: {
                    Authorization: `Bearer ${token}`,
                    },
                });
            
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao listar as tarefas.');
            }
        },
        addTarefa: async (token: string, data: Tarefa) => {
            try {
                const response = await api.post('/tarefa/add', {
                    idCategoria: data.idCategoria,
                    titulo: data.titulo,
                    descricao: data.descricao,
                    dataPrazo: data.dataPrazo,
                    finalizado: data.finalizado,
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
        
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao adicionar tarefa.');
            }
        },
        editarTarefa: async (token: string, data: Tarefa) => {
            try {
                const response = await api.put(`/tarefa/editar/${data.id}`, {
                    idCategoria: data.idCategoria,
                    titulo: data.titulo,
                    descricao: data.descricao,
                    dataPrazo: data.dataPrazo,
                    finalizado: data.finalizado,
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
        
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao editar tarefa.');
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
                getMensagemErro(error, 'Erro ao deletar a tarefa.');
            }
        },
        listarTodasCategoriasTarefa: async (token: string) => {
            try {
                const response = await api.get('/categoriatarefa/getTodasCategoriasTarefa', {
                    headers: {
                    Authorization: `Bearer ${token}`,
                    },
                });
            
                return response.data;
            } catch (error: any) {
                getMensagemErro(error, 'Erro ao listar as categorias de tarefa.');
            }
        },
    }
};
