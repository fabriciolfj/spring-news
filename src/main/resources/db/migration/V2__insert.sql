-- Inserts para a tabela Owner
INSERT INTO Owner (id)
VALUES
    (DEFAULT), -- O ID será gerado automaticamente devido ao tipo "bigserial"
    (DEFAULT),
    (DEFAULT);

-- Inserts para a tabela Pet
INSERT INTO Pet (id, birth_date, type, owner_id)
VALUES
    (DEFAULT, '2020-01-15', 'Cachorro', 1),
    (DEFAULT, '2019-03-22', 'Gato', 1),
    (DEFAULT, '2018-07-30', 'Pássaro', 2);