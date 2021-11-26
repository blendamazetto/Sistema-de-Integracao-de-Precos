CREATE USER user LOGIN CREATEDB PASSWORD 'senha';

CREATE DATABASE user WITH OWNER = user ENCODING = UTF8;

CREATE SCHEMA lojas_notebook;

CREATE TABLE lojas_notebook.notebook(
    nome CHAR(200) NOT NULL,
    CONSTRAINT pk_notebook PRIMARY KEY(nome)
);

CREATE TABLE lojas_notebook.produto(
    id SERIAL,
    nome_loja CHAR(50),
    preço FLOAT,
    disponibilidade BOOL,
    notebook_nome CHAR(200),
    CONSTRAINT pk_produto PRIMARY KEY(id),
    CONSTRAINT fk_notebook FOREIGN KEY(notebook_nome)
        REFERENCES lojas_notebook.notebook(nome)
);

CREATE TABLE lojas_notebook.especificacoes_tecnicas(
    notebook_nome CHAR(200),
    sistema_operacional CHAR(40),
    placa_de_video CHAR(60),
    ssd INT,
    hd INT,
    resolucao_da_tela CHAR(15),
    processador CHAR(20),
    memoria_ram INT,
    CONSTRAINT pk_especificacoes PRIMARY KEY(notebook_nome),
    CONSTRAINT fk_notebook FOREIGN KEY(notebook_nome)
        REFERENCES lojas_notebook.notebook(nome) 
);

CREATE TABLE lojas_notebook.caracteristicas_fisicas(
    cor CHAR(20),
    tamanho_tela FLOAT,
    Peso FLOAT,
    notebook_nome CHAR(200),
    CONSTRAINT pk_caracteristicas_fisicas PRIMARY KEY(notebook_nome),
    CONSTRAINT fk_notebook FOREIGN KEY(notebook_nome)
        REFERENCES lojas_notebook.notebook(nome)
);