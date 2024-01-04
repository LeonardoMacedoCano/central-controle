import * as C from './styles';
import { formatarMesAno } from '../../utils/DateUtils';
import { FaCaretSquareRight, FaCaretSquareLeft } from "react-icons/fa";

type Props = {
    dataSelecionada: string;
    onDataChange: (data: string) => void;
    dataDescricao: string;
    titulo: string;
    infoDescricao: string;
    infoValor: string;
};

export const InfoArea = ({
    dataSelecionada,
    onDataChange,
    dataDescricao,
    titulo,
    infoDescricao,
    infoValor,
}: Props) => {
  
    const handleDataChange = (direction: 'anterior' | 'posterior') => {
        const [ano, mes] = dataSelecionada.split('-');
        const novaData = new Date(parseInt(ano), parseInt(mes) - 1, 1);

        if (direction === 'anterior') {
            novaData.setMonth(novaData.getMonth() - 1);
        } else {
            novaData.setMonth(novaData.getMonth() + 1);
        }

        onDataChange(`${novaData.getFullYear()}-${novaData.getMonth() + 1}`);
    };

    return (
        <C.Container>
            <C.AreaData>
                <C.DataDescricao>
                    {dataDescricao}
                </C.DataDescricao>

                <C.DataValor>
                    <C.DataSeta onClick={() => handleDataChange('anterior')}>
                        <FaCaretSquareLeft />
                    </C.DataSeta>
                    <C.TituloMes>
                        {formatarMesAno(dataSelecionada)}
                    </C.TituloMes>
                    <C.DataSeta onClick={() => handleDataChange('posterior')}>
                        <FaCaretSquareRight />
                    </C.DataSeta>
                </C.DataValor>
            </C.AreaData>

            <C.AreaTitulo>
                {titulo}
            </C.AreaTitulo>

            <C.AreaInfo>
                <C.InfoDescricao>
                    {infoDescricao}
                </C.InfoDescricao>
                <C.InfoValor>
                    {infoValor}
                </C.InfoValor>
            </C.AreaInfo>
        </C.Container>
    );
};
