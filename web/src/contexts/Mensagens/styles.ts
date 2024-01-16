
const mensagemComum = {
    padding: '10px',
    position: 'fixed' as 'fixed',
    top: '10px',
    right: '10px',
    zIndex: 1000,
    transition: 'opacity 5s ease-out',
    borderRadius: '5px 0 5px 5px',
  } as const;
  
  export const mensagemErro = {
    ...mensagemComum,
    backgroundColor: '#ff3333',
    color: '#fff',
  } as const;
  
  export const mensagemSucesso = {
    ...mensagemComum,
    backgroundColor: '#33cc33',
    color: '#fff',
    
  } as const;