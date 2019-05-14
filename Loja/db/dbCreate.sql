--
-- File generated with SQLiteStudio v3.1.1 on dom jul 22 20:15:53 2018
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Carrinho
CREATE TABLE Carrinho (id_compra INTEGER PRIMARY KEY AUTOINCREMENT, total_compra DOUBLE NOT NULL DEFAULT (0), cliente VARCHAR (50) NOT NULL);
INSERT INTO Carrinho (id_compra, total_compra, cliente) VALUES (37, 5631, 'master');

-- Table: itens
CREATE TABLE itens (id INTEGER NOT NULL, nome VARCHAR NOT NULL, preco DOUBLE NOT NULL);
INSERT INTO itens (id, nome, preco) VALUES (37, 'Chocolate Biss', 10);
INSERT INTO itens (id, nome, preco) VALUES (37, 'Chocolate Biss', 10);
INSERT INTO itens (id, nome, preco) VALUES (37, 'Biscoito Treloso', 5);
INSERT INTO itens (id, nome, preco) VALUES (37, 'Batata-frita Rufles', 6);
INSERT INTO itens (id, nome, preco) VALUES (37, 'Smartphone Samsung Galaxy S9', 2800);
INSERT INTO itens (id, nome, preco) VALUES (37, 'Smartphone Samsung Galaxy S9', 2800);

-- Table: Produto
CREATE TABLE Produto (cod INTEGER PRIMARY KEY AUTOINCREMENT, tipo VARCHAR (100), nome VARCHAR (100) UNIQUE NOT NULL, preco DECIMAL NOT NULL, estoque INT NOT NULL);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (1, 'Alimentos', 'Chocolate Biss', 10, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (2, 'Alimentos', 'Biscoito Treloso', 5, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (3, 'Alimentos', 'Batata-frita Rufles', 6, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (4, 'Telefonia', 'Smartphone Samsung Galaxy S9', 2800, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (5, 'Telefonia', 'Smartphone Iphone 7', 3000, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (6, 'Telefonia', 'Smartphone Samsung J 5 Prime', 800, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (7, 'Alimentos', 'Sorvete Kibon', 15, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (8, 'Telefonia', 'Smartphone Samsung Galaxy S6', 2200, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (9, 'Móveis', 'Cadeira Escritório', 300, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (10, 'Móveis', 'Poltrona do papai', 1200, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (11, 'Móveis', 'Escrivania Top', 700, 100);
INSERT INTO Produto (cod, tipo, nome, preco, estoque) VALUES (12, 'Móveis', 'Mesa de canto Top', 960, 100);

-- Table: Tipo
CREATE TABLE Tipo (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR (100) UNIQUE);
INSERT INTO Tipo (id, nome) VALUES (1, 'Alimentos');
INSERT INTO Tipo (id, nome) VALUES (2, 'Telefonia');
INSERT INTO Tipo (id, nome) VALUES (3, 'Móveis');
INSERT INTO Tipo (id, nome) VALUES (4, 'Informática');

-- Table: usuario
CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, tipo VARCHAR (1) DEFAULT C, nome VARCHAR (15) UNIQUE NOT NULL);
INSERT INTO usuario (id, tipo, nome) VALUES (1, 'F', 'master');
INSERT INTO usuario (id, tipo, nome) VALUES (2, 'C', 'cliente');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
