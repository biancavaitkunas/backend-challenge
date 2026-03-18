# 🔐 Validador de Senhas - Backend Challenge

Olá! Este é o meu projeto para o Backend Challenge. A ideia aqui é uma API RESTful simples, feita com **Java 21 e Spring Boot**, que recebe uma senha e verifica se ela é forte o suficiente com base em uma série de regras.

Para deixar o teste mais legal, eu também criei uma interface visual simples para testar a API em tempo real pelo navegador!

## 💡 Como eu resolvi o problema

Quando li o desafio, a primeira coisa que veio à cabeça foi criar um serviço com um monte de `if` e `else` verificando cada regra (tamanho, caracteres, números, etc.). O problema é que isso ia gerar um código gigante, difícil de ler e chato de manter.

Para resolver isso, decidi aplicar **Clean Code** e o **Padrão Strategy**, mas de um jeito bem prático:

1. **Separação de Responsabilidades:** Em vez de um "super método" validador, eu criei uma interface chamada `PasswordValidationRule`.
2. **Uma regra, uma classe:** Cada regra do desafio (ex: `DigitRule`, `LengthRule`, `NoWhitespaceRule`) virou uma classe separada. O trabalho delas é único e simples: retornar `true` ou `false` para aquela regra específica.
3. **Spring:** No meu `PasswordValidationService`, eu peço para o Spring Boot injetar uma **Lista** com todas essas regras.
4. **Validação limpa:** Na hora de validar, eu só uso o recurso de *Streams* do Java. Ele roda a senha por todas as regras da lista. Se falhar em uma, ele já retorna `false` na hora.

**Por que fiz assim?** Por causa do SOLID (principalmente o Princípio do Aberto/Fechado - OCP). Se amanhã o time de segurança decidir que senhas precisam de um número mínimo de letras, eu não preciso mexer e correr o risco de quebrar o código antigo. Eu só crio uma classe nova e o sistema continua rodando perfeitamente!

---

## 💭 Premissas Assumidas

Durante o desenvolvimento, precisei tomar algumas decisões sobre pontos que poderiam ter mais de uma interpretação:

1. **Retorno da API (Apenas Boolean vs. Lista de Erros):** * **Premissa:** Para uma API de validação, quem consome (um App ou outro microsserviço) geralmente só quer saber se passou ou não (`true/false`). Retornar uma lista de quais regras falharam na API deixaria a resposta pesada.
    * **Decisão:** A API retorna `{"isValid": true/false}`. Porém, para melhorar a experiência do usuário, coloquei a lista detalhada de requisitos (o que faltou preencher) direto no Frontend (na página HTML).
2. **Regra de Caracteres Repetidos:**
    * **Premissa:** O requisito diz "Não possuir caracteres repetidos". Isso poderia significar caracteres consecutivos (ex: `aa`) ou em qualquer lugar da senha (ex: `a123a`).
    * **Decisão:** Assumi a regra mais segura para senhas fortes: o caractere não pode se repetir em **nenhum** lugar da string. Usei a lógica do código para barrar qualquer duplicidade.
3. **A Interface Visual (Frontend):**
    * **Premissa:** Testar APIs apenas pelo Postman/Insomnia ou cURL é funcional, mas ter uma tela interativa facilita muito a validação da regra de negócio.
    * **Decisão:** Adicionei um arquivo `index.html` básico servido pelo próprio Spring Boot. Não afeta a arquitetura da API, mas melhora muito o teste de quem for avaliar o projeto!
4. **Documentação com Swagger (OpenAPI):**
    * **Premissa:** É sempre uma boa prática documentar os contratos de uma API para outros desenvolvedores.
    * **Decisão:** Configurei o SpringDoc no projeto. Assim, quem quiser validar o endpoint tecnicamente, sem usar a interface web, pode testar os requests e responses direto pela página do Swagger.

---

## ⚙️ Regras de Validação
Para a senha retornar `true`, ela precisa passar por todos esses testes:
* Ter 9 ou mais caracteres.
* Ter pelo menos 1 dígito (0-9).
* Ter pelo menos 1 letra minúscula e 1 letra maiúscula.
* Ter pelo menos 1 caractere especial (`!@#$%^&*()-+`).
* Não ter caracteres repetidos em sequência ou em qualquer lugar da string (ex: `aa` ou `aba`).
* Não ter espaços em branco.

---

## 🚀 Tecnologias Utilizadas
* **Java 21** (Para aproveitar os recursos mais modernos da linguagem)
* **Spring Boot 3** (Facilita a criação da API web)
* **SpringDoc OpenAPI / Swagger** (Para documentação interativa dos endpoints)
* **Maven** (Para gerenciar as dependências e build)
* **JUnit 5 & Mockito** (Para garantir que tudo funciona com testes)
* **HTML/CSS/JS** (Para a telinha de teste)

---

## 📖 Como usar a API

A API expõe apenas um endpoint simples:

### `POST /api/v1/passwords/validation`

**Corpo da requisição (JSON):**
```json
{
  "password": "SuaSenhaSegura1!"
}
```

**Resposta de Sucesso (JSON):**
```json
{
  "isValid": true
}
```
*(Se a senha falhar em qualquer regra, o retorno será `{"isValid": false}` com status 200 OK).*

---

## 💻 Como rodar o projeto na sua máquina

**Pré-requisitos:** Você só precisa ter o **Java 21** instalado.
*(Aviso importante: certifique-se de que o Java está adicionado nas Variáveis de Ambiente do seu sistema, especialmente a variável `JAVA_HOME` e o `PATH`, para o terminal reconhecer os comandos).*

1. Clone este repositório no seu computador.
2. Abra o terminal e acesse a pasta raiz do projeto.
3. Não precisa instalar o Maven! Eu deixei o Maven Wrapper configurado. Basta rodar:
    * **No Windows:** `mvnw clean compile spring-boot:run`
    * **No Linux/Mac:** `./mvnw clean compile spring-boot:run`
4. Quando o Spring Boot terminar de subir, abra seu navegador de preferência e acesse a interface visual em:
   👉 **`http://localhost:8080`** 5. Para acessar a documentação interativa e testar via **Swagger**, acesse:
   👉 **`http://localhost:8080/swagger-ui/index.html`**

---

## 🧪 Como rodar os Testes (100% de Cobertura)

Fiz questão de cobrir todo o código de regras com testes automatizados para provar que a lógica funciona de verdade. Dividi eles em três partes (Pirâmide de Testes):

* **Testes de Regras (Unitários):** Criei testes aninhados para testar cada regra (classe) isoladamente.
* **Testes de Serviço:** Para garantir que a lista de regras trabalha bem em conjunto.
* **Testes do Controller (Integração):** Usando o `MockMvc` para garantir que o Spring está recebendo e devolvendo o JSON certinho.

Para rodar todos os testes de uma vez pelo terminal, execute:
* **No Windows:** `mvnw test`
* **No Linux/Mac:** `./mvnw test`