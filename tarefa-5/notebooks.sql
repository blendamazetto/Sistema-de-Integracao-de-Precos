CREATE SCHEMA lojas_notebook;

CREATE TABLE lojas_notebook.notebook(
    id_notebook SERIAL,
    modelo CHAR(20),
    descricao CHAR(200),
	marca CHAR(20),
    CONSTRAINT pk_notebook PRIMARY KEY(id_notebook)
);

CREATE TABLE lojas_notebook.loja(
    nome_loja CHAR(20),
    url_site CHAR(40),
    CONSTRAINT pk_loja PRIMARY KEY(nome_loja)
);

CREATE TABLE lojas_notebook.loja_vende_notebook(
    id_loja_vende_notebook SERIAL,
    id_notebook INT,
    nome_loja CHAR(50),
    classificacao FLOAT,
    preço NUMERIC(6, 2),
    disponibilidade BOOL,
    descricao CHAR(200),
    url_produto CHAR(200),
	data_crawling DATE,
    CONSTRAINT pk_produto PRIMARY KEY(id_loja_vende_notebook),
    CONSTRAINT fk_notebook FOREIGN KEY(id_notebook)
        REFERENCES lojas_notebook.notebook(id_notebook),
    CONSTRAINT fk_loja FOREIGN KEY(nome_loja)
        REFERENCES lojas_notebook.loja(nome_loja)
);