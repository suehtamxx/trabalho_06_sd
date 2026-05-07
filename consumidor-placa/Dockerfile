# Usa uma imagem oficial do Maven com Java 17 (pode mudar o 17 para 21 se estiver usando o Java 21)
FROM maven:3.9-eclipse-temurin-11

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de dependências e o código fonte
COPY pom.xml .
COPY src ./src

# Comando para compilar e executar o projeto usando o Maven
CMD ["mvn", "clean", "compile", "exec:java", "-Dexec.mainClass=org.example.Main"]