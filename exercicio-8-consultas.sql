-- EXERCÍCIO 7 — Consultas SQL
-- Requer as tabelas e os dados criados no exercicio-2-modelagem.sql

-- 1. Listar todo o acervo, com código, título, tipo e disponibilidade
SELECT codigo, titulo, tipo, disponivel
FROM item
ORDER BY codigo;

-- 2. Listar os empréstimos em aberto, com o nome do usuário e o título do item
SELECT u.nome AS usuario,
       i.titulo AS item,
       e.data_devolucao_prevista
FROM emprestimo e
JOIN usuario u ON u.id = e.usuario_id
JOIN item i ON i.id = e.item_id
WHERE e.data_devolucao IS NULL
ORDER BY e.data_devolucao_prevista;

-- 3. Calcular o total de multas acumuladas por usuário
-- LEFT JOIN mantém usuários sem nenhum empréstimo, com total 0
SELECT u.nome AS usuario,
       COALESCE(SUM(e.valor_multa), 0) AS total_multas
FROM usuario u
LEFT JOIN emprestimo e ON e.usuario_id = u.id
GROUP BY u.id, u.nome
ORDER BY total_multas DESC;

-- 4. Listar os itens que nunca foram emprestados
-- Se o item não tem nenhuma linha em emprestimo, e.id fica NULL
SELECT i.codigo, i.titulo, i.tipo
FROM item i
LEFT JOIN emprestimo e ON e.item_id = i.id
WHERE e.id IS NULL
ORDER BY i.codigo;
