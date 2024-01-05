CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    datainclusao DATETIME NOT NULL
);

CREATE TABLE categoriadespesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255)
);

CREATE TABLE despesa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idusuario INT,
    idcategoria INT,
    descricao VARCHAR(255),
    valor FLOAT,
    data DATETIME,
    FOREIGN KEY (idusuario) REFERENCES usuario(id),
    FOREIGN KEY (idcategoria) REFERENCES categoriadespesa(id)
);