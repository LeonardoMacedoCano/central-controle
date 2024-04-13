import styled from 'styled-components';

export const Body = styled.div`
  width: 500px;
  height: 500px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, 
    ${props => props.theme.colors.tertiary} 50%, 
    ${props => props.theme.colors.secondary} 50%
  );
  border-radius: 5px;
  box-shadow: 0 0 5px 1px;
`;


export const Titulo = styled.h1`
  color: ${props => props.theme.colors.white};
  font-family: 'Roboto Slab';
  font-size: 40px;
  margin-bottom: 120px;
`;

export const Icon = (theme: any) => ({
  fontSize: '15px',
  height: '100%',
  width: '50px',
  padding: '10px',
  borderRight: `2px solid ${theme.colors.quaternary}`,
});


export const Input = (theme: any) => ({
  width: '98%',
  height: '50px',
  marginBottom: '20px',
  display: 'flex',
  alignItems: 'center',
  borderRadius: '5px',
  border: `2px solid ${theme.colors.quaternary}`,
  backgroundColor: `${theme.colors.secondary}`, 
});
