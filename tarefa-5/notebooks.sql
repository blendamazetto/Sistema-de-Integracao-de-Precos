CREATE USER user LOGIN CREATEDB PASSWORD 'senha';

CREATE DATABASE user WITH OWNER = user ENCODING = UTF8;

CREATE SCHEMA lojas_notebook;

CREATE TABLE lojas_notebook.notebook(
    id_notebook SERIAL,
    modelo CHAR(20),
    descricao CHAR(200),
    CONSTRAINT pk_notebook PRIMARY KEY(id_notebook)
);

CREATE TABLE lojas_notebook.loja(
    nome_loja CHAR(20),
    url_site CHAR(40),
    CONSTRAINT pk_loja PRIMARY KEY(nome_loja)
)

CREATE TABLE lojas_notebook.produto(
    id SERIAL,
    nome_notebook CHAR(50),
    nome_loja CHAR(50),
    classificacao FLOAT,
    preço NUMERIC(6, 2),
    disponibilidade BOOL,
    notebook_nome CHAR(200),
    url_produto CHAR(200),
    CONSTRAINT pk_produto PRIMARY KEY(id),
    CONSTRAINT fk_notebook FOREIGN KEY(nome_notebook)
        REFERENCES lojas_notebook.notebook(nome),
    CONSTRAINT fk_loja FOREIGN KEY(nome_loja)
        REFERENCES lojas_notebook.loja(nome)
);
