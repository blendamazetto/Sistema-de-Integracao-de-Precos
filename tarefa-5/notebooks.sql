CREATE SCHEMA lojas_notebook;

CREATE TABLE lojas_notebook.notebook(
    id_notebook SERIAL,
    modelo CHAR(30),
    descricao CHAR(300),
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
    nome_loja CHAR(20),
    classificacao NUMERIC(2,1),
    preco NUMERIC(8, 2),
    disponibilidade BOOLEAN,
    url_produto CHAR(300),
	data_crawling DATE,
    CONSTRAINT pk_produto PRIMARY KEY(id_loja_vende_notebook),
    CONSTRAINT fk_notebook FOREIGN KEY(id_notebook)
        REFERENCES lojas_notebook.notebook(id_notebook),
    CONSTRAINT fk_loja FOREIGN KEY(nome_loja)
        REFERENCES lojas_notebook.loja(nome_loja)
);