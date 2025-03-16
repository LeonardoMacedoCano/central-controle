import React, { useState, useContext, useEffect, useRef } from 'react';
import { ThemeContext } from 'styled-components';
import { 
  FieldValue,
  FlexBox,
  Container,
  ImagePicker,
  ThemeSelector,
  Panel,
  FloatingButton
} from '../../components';
import { FaCheck } from 'react-icons/fa';
import { AuthContext, useMessage } from '../../contexts';
import { ArquivoService, TemaBaseService, UsuarioService } from '../../service';
import { Tema, Usuario, UsuarioForm } from '../../types';
import { IMG_PERFIL_PADRAO } from '../../utils';

export const UsuarioFormPage: React.FC = () => {
  const [temas, setTemas] = useState<Tema[]>([]);
  const [usuarioForm, setUsuarioForm] = useState<UsuarioForm | null>(null);
  const [confirmPassword, setConfirmPassword] = useState<string>('');
  const [imagemPerfil, setImagemPerfil] = useState<string>(IMG_PERFIL_PADRAO);
  const [isLoadingImage, setIsLoadingImage] = useState<boolean>(false);
  
  const blobRef = useRef<Blob | null>(null);
  const blobUrlRef = useRef<string | null>(null);
  
  const theme = useContext(ThemeContext);
  const auth = useContext(AuthContext);
  const message = useMessage();
  const temaBaseService = TemaBaseService();
  const usuarioService = UsuarioService();  
  const arquivoService = ArquivoService();

  const cleanupBlobUrl = () => {
    if (blobUrlRef.current) {
      URL.revokeObjectURL(blobUrlRef.current);
      blobUrlRef.current = null;
    }
  };

  useEffect(() => {
    if (auth.usuario) {
      loadTemas();
      convertAndSetUsuarioForm(auth.usuario);
    }
  }, [auth.usuario]);
  
  useEffect(() => {
    const fetchArquivo = async () => {
      if (!auth.usuario?.token) return;
      
      setIsLoadingImage(true);
      try {
        if (usuarioForm?.file) {
          cleanupBlobUrl();
          blobRef.current = usuarioForm.file;
          blobUrlRef.current = URL.createObjectURL(usuarioForm.file);
          setImagemPerfil(blobUrlRef.current);
        } else if (auth.usuario.idArquivo) {
          const result = await arquivoService.getArquivoById(auth.usuario.token, auth.usuario.idArquivo);
          if (result) {
            cleanupBlobUrl();
            blobRef.current = result;
            blobUrlRef.current = URL.createObjectURL(result);
            setImagemPerfil(blobUrlRef.current);
          } else {
            setImagemPerfil(IMG_PERFIL_PADRAO);
          }
        } else {
          setImagemPerfil(IMG_PERFIL_PADRAO);
        }
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar o arquivo.', error);
        setImagemPerfil(IMG_PERFIL_PADRAO);
      } finally {
        setIsLoadingImage(false);
      }
    };
  
    fetchArquivo();
    return () => {
      cleanupBlobUrl();
    };
  }, [auth.usuario?.idArquivo, usuarioForm?.file]);
  
  const loadTemas = async () => {
    if (!auth.usuario?.token) return;

    try {
      const result = await temaBaseService.getTemas(auth.usuario.token);
      if (result) setTemas(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar os temas.', error);
    }
  };

  const convertAndSetUsuarioForm = (usuario: Usuario) => {
    setUsuarioForm({
      username: usuario.username,
      currentPassword: '',
      newPassword: '',
      idTema: usuario.idTema,
    });
  };

  const isPasswordMatch = () => usuarioForm?.newPassword === confirmPassword;
  
  const isFormValid = () => usuarioForm?.username?.trim() !== '';

  const handleSubmit = async () => {
    if (!isFormValid() || !auth.usuario?.token || !usuarioForm) return;
    await usuarioService.updateUsuario(auth.usuario.token, usuarioForm);
  };

  const update = (updatedFields: Partial<UsuarioForm>) => {
    setUsuarioForm(prev => (prev ? { ...prev, ...updatedFields } : prev));
  };

  if (!usuarioForm) return <p>Carregando...</p>;

  return (
    <Container>
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={'Salvar'}
        mainAction={handleSubmit}
        disabled={!isFormValid()}
      />
      <Panel maxWidth='800px' title='Usuário'>
        <FlexBox flexDirection="column" style={{ padding: '20px' }}>
          <FlexBox flexDirection="row">
            <FlexBox.Item>
              <ImagePicker 
                currentImage={imagemPerfil}
                onImageChange={(file) => update({ file })}
                borderColor={theme.colors.tertiary}
                isLoading={isLoadingImage}
                key={imagemPerfil}
              />
            </FlexBox.Item>
          </FlexBox>
          <Panel>
            <FlexBox flexDirection="column">
              <FlexBox.Item borderBottom>
                <FieldValue
                  type="string"
                  value={usuarioForm.username}
                  description="Nome"
                  editable
                  onUpdate={(value) => update({ username: value })}
                />
              </FlexBox.Item>
              <FlexBox.Item borderBottom>
                <FieldValue
                  type="string"
                  value={usuarioForm.currentPassword}
                  description="Senha Atual"
                  editable
                  onUpdate={(value) => update({ currentPassword: value })}
                  placeholder="Digite sua senha atual"
                />
              </FlexBox.Item>
              <FlexBox.Item borderBottom>
                <FieldValue
                  type="string"
                  value={usuarioForm.newPassword}
                  description="Nova Senha"
                  editable
                  onUpdate={(value) => update({ newPassword: value })}
                  placeholder="Digite sua nova senha"
                />
              </FlexBox.Item>
              <FlexBox.Item>
                <FieldValue
                  type="string"
                  value={confirmPassword}
                  description="Confirmar Nova Senha"
                  editable
                  onUpdate={setConfirmPassword}
                  placeholder="Confirme sua nova senha"
                  variant={usuarioForm.newPassword && !isPasswordMatch() ? 'warning' : undefined}
                />
              </FlexBox.Item>
            </FlexBox>
          </Panel>
          <Panel maxWidth='1000px' title='Temas' transparent style={{ marginTop: '20px' }}>
            <ThemeSelector 
              themes={temas}
              currentTheme={usuarioForm.idTema}
              onThemeChange={(idTema) => update({ idTema })}
            />
          </Panel>
        </FlexBox>  
      </Panel>  
    </Container>
  );
};

export default UsuarioFormPage;
