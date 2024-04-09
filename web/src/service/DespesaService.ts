import DefaultService from './DefaultService';
import { PagedResponse } from '../types/PagedResponse';
import { DespesaResumoMensal } from '../types/DespesaResumoMensal';
import { Despesa } from '../types/Despesa';
import { Categoria } from '../types/Categoria';
import { usarMensagens } from '../contexts/mensagens';

interface DespesaApi {
  listarDespesaResumoMensal: (token: string, page: number, size: number, ano: number, mes: number) => Promise< PagedResponse<DespesaResumoMensal> | undefined >;
  getDespesaByIdWithParcelas: (token: string, id: number) => Promise< Despesa | undefined >;
  getTodasCategoriasDespesa: (token: string) => Promise< Categoria[] | undefined >;
  gerarDespesa: (token: string, data: Despesa) => Promise< undefined >;
  excluirDespesa: (token: string, id: number) => Promise< undefined >; 
}

const DespesaService = (): DespesaApi => {
  const { request } = DefaultService();
  const mensagens = usarMensagens();

  const listarDespesaResumoMensal = async (token: string, page: number, size: number, ano: number, mes: number) => {
    try {
      return await request<PagedResponse<DespesaResumoMensal>>(`get`, `despesa?page=${page}&size=${size}&ano=${ano}&mes=${mes}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getDespesaByIdWithParcelas = async (token: string, id: number) => {
    try {
      return await request<Despesa>(`get`, `despesa/${id}`, token);
    } catch (error) {
      return undefined;
    }
  };

  const getTodasCategoriasDespesa = async (token: string) => {
    try {
      return await request<Categoria[]>(`get`, 'categoriadespesa', token);
    } catch (error) {
      return undefined;
    }
  };

  const despesaPayload = (data: Despesa) => ({
    categoria: data.categoria,
    descricao: data.descricao,
    parcelas: data.parcelas
  });

  const gerarDespesa = async (token: string, data: Despesa) => {
    try {
      await request<undefined>('post', 'despesa', token, mensagens, despesaPayload(data));
    } catch (error) {
      return undefined;
    }
  };

  const excluirDespesa = async (token: string, id: number) => {
    try {
      await request<undefined>(`delete`, `despesa/${id}`, token, mensagens);
    } catch (error) {
      return undefined;
    }
  };

  return {
    listarDespesaResumoMensal,
    getDespesaByIdWithParcelas,
    getTodasCategoriasDespesa,
    gerarDespesa,
    excluirDespesa
  };
};

export default DespesaService;
