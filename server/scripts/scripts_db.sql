CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    datainclusao DATE NOT NULL,
	ativo CHAR(1) DEFAULT 'S'
);

CREATE TABLE despesaformapagamento (
    id INT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioconfig (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT NOT NULL UNIQUE,
    despesanumeromaxitempagina INT DEFAULT 10,
    despesavalormetamensal DECIMAL(10, 2),
    despesadiapadraovencimento INT DEFAULT 10,
    despesaformapagamentopadrao INT,
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
	FOREIGN KEY (despesaformapagamentopadrao) REFERENCES despesaformapagamento(id)
);

CREATE TABLE despesacategoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE lancamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT NOT NULL,
    datalancamento DATE NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    tipo ENUM('DESPESA', 'RECEITA', 'PASSIVO', 'ATIVO') NOT NULL,
    FOREIGN KEY (idusuario) REFERENCES usuario(id)
);

CREATE TABLE despesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idlancamento INT NOT NULL UNIQUE,
    idcategoria INT NOT NULL,
    FOREIGN KEY (idlancamento) REFERENCES lancamento(id),
    FOREIGN KEY (idcategoria) REFERENCES despesacategoria(id)
);

CREATE TABLE despesaparcela (
    id INT AUTO_INCREMENT PRIMARY KEY,
    iddespesa INT NOT NULL,
    idformapagamento INT,
    numero INT NOT NULL,
    datavencimento DATE NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    pago CHAR(1) DEFAULT 'N',
    FOREIGN KEY (iddespesa) REFERENCES despesa(id),
    FOREIGN KEY (idformapagamento) REFERENCES despesaformapagamento(id),
    UNIQUE KEY (iddespesa, numero)
);

