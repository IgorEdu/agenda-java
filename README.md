Tecnologias usadas

- Java
- Swing
- MySQL

Configurando banco de dados:

- Conectar ao MySQL com um usuário com permissão para criação e exclusão de databases e tabelas (CREATE e DROP)
- Restaurar o backup localizado no diretorio "dump-db":
	mysql -u [usuario] -p < dump-agenda-utfpr.sql

- Configurar o arquivo database.properties (considerar a troca dos itens entre chaves)
	user=[usuario]
	password=[senha]
	dburl=jdbc:mysql://[endereco]:[porta]/agenda-utfpr
	useSSL=false
	allowPublicKeyRetrieval=true

- Acessar e executar a Classe App.java
