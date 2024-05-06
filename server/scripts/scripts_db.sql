CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    datainclusao DATETIME NOT NULL,
	ativo CHAR(1) DEFAULT 'S'
);

INSERT INTO usuario (id, username, senha, datainclusao, ativo)
VALUES(1, 'user1', '$2a$10$2FQaqTlAqTPZz0n5eAbqpeap8Ac1vhW.hQhbidlpXBy08k5OAgM3a', '2024-01-01', 'S');

CREATE TABLE formapagamento (
    id INT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE usuarioconfig (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT NOT NULL UNIQUE,
    despesanumeroitempagina INT DEFAULT 10,
    despesavalormetamensal DECIMAL(10, 2),
    despesadiapadraovencimento INT DEFAULT 10,
    despesaidformapagamentopadrao INT,
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
	FOREIGN KEY (despesaidformapagamentopadrao) REFERENCES formapagamento(id)
);

CREATE TABLE categoriadespesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE despesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT NOT NULL,
    idcategoria INT NOT NULL,
    datalancamento DATE NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
    FOREIGN KEY (idcategoria) REFERENCES categoriadespesa(id)
);

CREATE TABLE parcela (
    id INT AUTO_INCREMENT PRIMARY KEY,
    iddespesa INT NOT NULL,
    idformapagamento INT,
    numero INT NOT NULL,
    datavencimento DATE NOT NULL,
    valor FLOAT NOT NULL,
    pago CHAR(1) DEFAULT 'N',
    FOREIGN KEY (iddespesa) REFERENCES despesa(id),
    FOREIGN KEY (idformapagamento) REFERENCES formapagamento(id),
    UNIQUE KEY (iddespesa, numero)
);
