CREATE DATABASE Noctoramento;
USE Noctoramento;

-- DROP DATABASE Noctoramento;

CREATE TABLE Empresa (
    idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
    razaoSocial VARCHAR(45),
    cnpjEmpresa CHAR(14),
    email VARCHAR(45),
    senha VARCHAR(45)
);

-- SELECT * FROM Empresa;

-- INSERT INTO Empresa (razaoSocial, cnpjEmpresa, email, senha) VALUES 
-- ('Noctoramento', '12345678000195', 'contato@noctoramento.com', 'urubu100');

CREATE TABLE Cargo (
    idCargo INT PRIMARY KEY AUTO_INCREMENT,
    nomeCargo VARCHAR(45) UNIQUE NOT NULL
);

INSERT INTO Cargo (nomeCargo) VALUES 
    ('CEO'),
    ('Gestor'),
    ('Gerente'),
    ('Analista'),
    ('Desenvolvedor'),
    ('Estagiário'),
    ('Suporte');

-- SELECT * FROM Cargo;

CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT,
    nomeUsuario VARCHAR(45),
    emailUsuario VARCHAR(45),
    senhaUsuario VARCHAR(45),
    fkEmpresa INT,
    fkCargo INT,
    CONSTRAINT fkEmpresa FOREIGN KEY (fkEmpresa) REFERENCES Empresa (idEmpresa),
    CONSTRAINT fkCargo FOREIGN KEY (fkCargo) REFERENCES Cargo (idCargo),
    PRIMARY KEY (idUsuario, fkEmpresa)
);

-- SELECT * FROM Usuario;

CREATE TABLE Notebook(
    idNotebook INT AUTO_INCREMENT,
    numeroSerie VARCHAR(45),
    fabricante VARCHAR(45),
    modelo VARCHAR(45),
    dtRegistro TIMESTAMP DEFAULT NOW(),
    fkEmpresa INT,
    CONSTRAINT fkEmpresaNotebook FOREIGN KEY (fkEmpresa) REFERENCES Empresa (idEmpresa),
    PRIMARY KEY (idNotebook, fkEmpresa)
);

-- SELECT * FROM Notebook;

CREATE TABLE InfoNotebook(
    idInfoNotebook INT AUTO_INCREMENT,
    sistemaOperacional VARCHAR(45),
    processador VARCHAR(45),
    capacidadeMaxCpu DOUBLE,
    maxDisco DOUBLE,
    maxMemoriaRam DOUBLE,
    fkNotebook INT,
    fkEmpresa INT,
    CONSTRAINT fkEmpresaInfoNotebook FOREIGN KEY (fkEmpresa) REFERENCES Empresa (idEmpresa),
    CONSTRAINT fkNotebook FOREIGN KEY (fkNotebook) REFERENCES Notebook (idNotebook),
    PRIMARY KEY (idInfoNotebook, fkEmpresa, fkNotebook)
);

-- SELECT * FROM InfoNotebook;

CREATE TABLE Alocacao(
    dataUsoInicio DATE,
    dataUsoFim DATE,
    fkNotebook INT,
    fkEmpresaNotebook INT,
    fkUsuario INT,
    fkEmpresaUsuario INT,
    CONSTRAINT fkNotebookAlocacao FOREIGN KEY (fkNotebook) REFERENCES Notebook (idNotebook),
    CONSTRAINT fkEmpresaNotebookAlocacao FOREIGN KEY (fkEmpresaNotebook) REFERENCES Notebook (fkEmpresa),
    CONSTRAINT fkUsuarioAlocacao FOREIGN KEY (fkUsuario) REFERENCES Usuario (idUsuario),
    CONSTRAINT fkEmpresaUsuarioAlocacao FOREIGN KEY (fkEmpresaUsuario) REFERENCES Usuario (fkEmpresa),
    PRIMARY KEY (fkNotebook, fkEmpresaNotebook, fkUsuario, fkEmpresaUsuario)
);

-- SELECT * FROM Alocacao;

CREATE TABLE Parametros(
    idParametros INT AUTO_INCREMENT,
    tempoSegCapturaDeDados INT,
    tempoSegAlertas INT,
    UsoCriticoCpu INT,
    UsoCriticoDisco INT,
    UsoCriticoMemoriaRam INT,
    UsoAlarmanteCpu INT,
    UsoAlarmanteDisco INT,
    UsoAlarmanteMemoriaRam INT,
    UsoNormalCpu INT,
    UsoNormalDisco INT,
    UsoNormalMemoriaRam INT,
    fkEmpresa INT,
    CONSTRAINT fkEmpresaParametros FOREIGN KEY (fkEmpresa) REFERENCES Empresa (idEmpresa),
    PRIMARY KEY (idParametros, fkEmpresa)
);

INSERT INTO Parametros (
UsoNormalCpu, UsoNormalDisco, UsoNormalMemoriaRam, 
    UsoAlarmanteCpu, UsoAlarmanteDisco, UsoAlarmanteMemoriaRam, 
    UsoCriticoCpu, UsoCriticoDisco, UsoCriticoMemoriaRam, 
     fkEmpresa
 ) VALUES (
    50, 70, 60,
    50, 70, 60,
    80, 90, 85,
    1
);

-- SELECT * FROM Parametros;

CREATE TABLE RegistroUsoNotebook(
idRegistroUsoNotebook INT AUTO_INCREMENT,
usoCpu DOUBLE,
usoDisco DOUBLE,
tempoAtividadeDisco VARCHAR(45),
usoMemoriaRam DOUBLE,
qtdJanelasEmUso INT,
dtHoraCaptura DATETIME,
fkNotebook INT,
fkEmpresa INT,
CONSTRAINT fkNotebookRegistroUsoNotebook FOREIGN KEY (fkNotebook) REFERENCES Notebook (idNotebook),
CONSTRAINT fkEmpresaRegistroUsoNotebook FOREIGN KEY (fkEmpresa) REFERENCES Notebook (fkEmpresa),
PRIMARY KEY (idRegistroUsoNotebook, fkNotebook, fkEmpresa)
);

-- SELECT * FROM RegistroUsoNotebook;

CREATE TABLE Alerta(
    idAlerta INT AUTO_INCREMENT,
    fkParametros INT,
    fkEmpresaParametros INT,
    dtHoraAlerta TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fkParametrosAlerta FOREIGN KEY (fkParametros) REFERENCES Parametros (idParametros),
    CONSTRAINT fkEmpresaParametrosAlerta FOREIGN KEY (fkEmpresaParametros) REFERENCES Parametros (fkEmpresa),
    PRIMARY KEY (idAlerta, fkParametros, fkEmpresaParametros)
);

-- SELECT * FROM Alerta;