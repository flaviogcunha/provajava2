	create database escola;

	use escola;

	create table aluno
	(cpf VARCHAR(14) PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	data_nascimento DATE,
	peso DOUBLE,
	altura DOUBLE	
	);

	create table historico_peso(
	id INT AUTO_INCREMENT PRIMARY KEY,
        cpf VARCHAR(14) NOT NULL,
	data DATE NOT NULL,
	peso DOUBLE,
        altura DOUBLE,
	FOREIGN KEY (cpf) REFERENCES aluno(cpf) ON DELETE CASCADE
	);




	select * from aluno;

	select * from historico_peso;
