CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    datainclusao DATETIME NOT NULL,
	ativo CHAR(1) DEFAULT 'S'
);

INSERT INTO usuario (id, username, senha, datainclusao, ativo)
VALUES(1, 'user1', '$2a$10$2FQaqTlAqTPZz0n5eAbqpeap8Ac1vhW.hQhbidlpXBy08k5OAgM3a', '2024-01-01', 'S');

CREATE TABLE categoriadespesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE despesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT NOT NULL,
    idcategoria INT NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor FLOAT NOT NULL,
    data DATETIME NOT NULL,
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
    FOREIGN KEY (idcategoria) REFERENCES categoriadespesa(id)
);

CREATE TABLE categoriatarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE tarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT NOT NULL,
    idcategoria INT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    datainclusao DATETIME NOT NULL,
    dataprazo DATETIME NOT NULL,
    finalizado CHAR(1) DEFAULT 'N',
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
    FOREIGN KEY (idcategoria) REFERENCES categoriatarefa(id)
);

CREATE TABLE categoriaideia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255)
);

CREATE TABLE ideia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT,
    idcategoria INT,
    titulo VARCHAR(255),
    descricao VARCHAR(255),
    datainclusao DATETIME,
    dataprazo DATETIME,
    finalizado CHAR,
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
    FOREIGN KEY (idcategoria) REFERENCES categoriaideia(id)
);