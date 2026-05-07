# Sistema Distribuído com RabbitMQ e IA Embutida

Este projeto é a implementação da Trabalho 6 da disciplina de Sistemas Distribuídos. O objetivo é demonstrar a orquestração de containers se comunicando de forma assíncrona através de um broker de mensagens, simulando o processamento de imagens com Inteligência Artificial.

## Arquitetura do Sistema

O sistema é composto por 4 containers rodando em uma mesma rede Docker (`rabbit_net`):

1. **RabbitMQ Broker**: Atua como o intermediário de mensagens. Utiliza o tipo `Topic` no Exchange (`topic_imagens`) para rotear as mensagens para as filas corretas baseando-se na *routing key* (`plate` ou `sign`).
2. **Gerador de Mensagens (Produtor)**: Aplicação Java que gera uma carga constante de 5 mensagens por segundo, simulando o envio de imagens de placas de veículos e sinais de trânsito.
3. **Consumidor de Placas (IA 1)**: Aplicação Java que consome apenas as mensagens com a chave `plate`. Utiliza a biblioteca Smile para simular o processamento da imagem. Processa propositalmente mais devagar (1 seg/mensagem) para demonstrar o enfileiramento.
4. **Consumidor de Sinais (IA 2)**: Aplicação Java que consome apenas as mensagens com a chave `sign`. Também utiliza a biblioteca Smile e possui um tempo de processamento simulado de 1 segundo.

## 🚀 Tecnologias Utilizadas

* **Java 11** (com Maven)
* **Docker & Docker Compose**
* **RabbitMQ** (com interface de administração)
* **Smile** (Statistical Machine Intelligence and Learning Engine)

## Como Executar o Projeto

**Pré-requisitos:** É necessário ter o Docker e o Docker Compose instalados na máquina.

1. Clone este repositório:
   ```bash
   git clone <https://github.com/suehtamxx/trabalho_06_sd.git>
   cd sistema-carga-ia
   ```
2. Suba a infraestutura completa:
    ```bash
   docker compose up -d --build
   ```
3. Acesse o painel de administração do RabbitMQ pelo navegador para ver o sistema em funcionamento e as filas crescendo devido à diferença entre a taxa de produção e consumo:
 ```bash
    URL: http://localhost:15672
    
    Usuário: guest
    
    Senha: guest
```
4. Para parar a execução e remover os containers:
```bash
  docker compose down
```
