-- EXERCÍCIO 6 — Modelagem do Banco de Dados (Sistema de Biblioteca)
-- Ordem: item e usuario não dependem de ninguém, então vêm primeiro.
-- emprestimo depende das duas, então vem por último.

CREATE TABLE item (
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo      VARCHAR(20)  NOT NULL UNIQUE,
    titulo      VARCHAR(200) NOT NULL,
    tipo        VARCHAR(10)  NOT NULL CHECK (tipo IN ('livro', 'revista')),
    autor       VARCHAR(150),
    edicao      VARCHAR(50),
    disponivel  BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE usuario (
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome          VARCHAR(150) NOT NULL,
    tipo          VARCHAR(10)  NOT NULL CHECK (tipo IN ('aluno', 'professor')),
    limite_itens  INTEGER      NOT NULL CHECK (limite_itens > 0)
);

CREATE TABLE emprestimo (
    id                       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    item_id                  INTEGER        NOT NULL REFERENCES item(id),
    usuario_id               INTEGER        NOT NULL REFERENCES usuario(id),
    data_retirada            DATE           NOT NULL,
    data_devolucao_prevista  DATE           NOT NULL,
    data_devolucao           DATE,          -- NULL enquanto o item não voltar
    valor_multa              NUMERIC(10,2)  NOT NULL DEFAULT 0
);

-- DADOS DE TESTE
-- Os IDs são gerados automaticamente, então os empréstimos buscam item e usuário pelo código/nome.

INSERT INTO item (codigo, titulo, tipo, autor, edicao, disponivel) VALUES
    ('L001', 'Dom Casmurro',        'livro',   'Machado de Assis', '1ª edição', FALSE),
    ('L002', 'O Cortiço',           'livro',   'Aluísio Azevedo',  '2ª edição', TRUE),
    ('R001', 'Revista de Ciências', 'revista', NULL,               'nº 10',     TRUE),
    ('R002', 'Revista de História', 'revista', NULL,               'nº 11',     TRUE);

INSERT INTO usuario (nome, tipo, limite_itens) VALUES
    ('Ana',    'aluno',     3),
    ('Carlos', 'professor', 5);

INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa)
VALUES
    -- Em aberto: Ana pegou "Dom Casmurro" (prazo de 14 dias)
    ((SELECT id FROM item WHERE codigo = 'L001'),
     (SELECT id FROM usuario WHERE nome = 'Ana'),
     '2026-03-02', '2026-03-16', NULL, 0),

    -- Já devolvido: Carlos devolveu "O Cortiço" 3 dias após o prazo (3 x R$ 0,50 = R$ 1,50)
    ((SELECT id FROM item WHERE codigo = 'L002'),
     (SELECT id FROM usuario WHERE nome = 'Carlos'),
     '2026-02-02', '2026-02-16', '2026-02-19', 1.50);
