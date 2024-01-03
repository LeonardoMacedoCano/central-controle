import * as C from './styles';
import { formatarMesAno } from '../../utils/DateUtils';
import { FaCaretSquareRight, FaCaretSquareLeft } from "react-icons/fa";

type Props = {
    dataSelecionada: string;
    onMesChange: (newMonth: string) => void;
    titulo: string;
    infoDescricao: string;
    infoValor: string;
};

export const InfoArea = ({
    dataSelecionada,
    onMesChange,
    titulo,
    infoDescricao,
    infoValor,
}: Props) => {
  
    const handleMesChange = (direction: 'anterior' | 'posterior') => {
        const [ano, mes] = dataSelecionada.split('-');
        const newDate = new Date(parseInt(ano), parseInt(mes) - 1, 1);

        if (direction === 'anterior') {
            newDate.setMonth(newDate.getMonth() - 1);
        } else {
            newDate.setMonth(newDate.getMonth() + 1);
        }

        onMesChange(`${newDate.getFullYear()}-${newDate.getMonth() + 1}`);
    };

    return (
        <C.Container>
            <C.AreaData>
                <C.DataDescricao>
                    Data
                </C.DataDescricao>

                <C.DataValor>
                    <C.DataSeta onClick={() => handleMesChange('anterior')}>
                        <FaCaretSquareLeft />
                    </C.DataSeta>
                    <C.TituloMes>
                        {formatarMesAno(dataSelecionada)}
                    </C.TituloMes>
                    <C.DataSeta onClick={() => handleMesChange('posterior')}>
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
