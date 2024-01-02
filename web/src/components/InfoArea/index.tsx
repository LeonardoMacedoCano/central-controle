import * as C from './styles';
import { formatarMesAno } from '../../utils/DateUtils';
import { FaArrowLeft, FaArrowRight } from 'react-icons/fa6';

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
            <C.AreaMes>
                <C.SetaMes onClick={() => handleMesChange('anterior')}>
                    <FaArrowLeft />
                </C.SetaMes>
                <C.TituloMes>
                    {formatarMesAno(dataSelecionada)}
                </C.TituloMes>
                <C.SetaMes onClick={() => handleMesChange('posterior')}>
                    <FaArrowRight />
                </C.SetaMes>
            </C.AreaMes>

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
