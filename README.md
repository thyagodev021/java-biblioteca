# 📚 Biblioteca em Java

Sistema de biblioteca para o terminal, feito em Java puro. Cadastre livros, busque, empreste e devolva. Os dados ficam salvos no arquivo `livros.csv`, então nada se perde ao fechar o programa.

## Funcionalidades

- Cadastrar, listar e remover livros
- Buscar por parte do título ou do autor
- Emprestar e devolver (com validação: não empresta duas vezes o mesmo livro)
- Persistência em arquivo CSV

## Estrutura

```
src/biblioteca/
├── Main.java        # menu no terminal
├── Biblioteca.java  # regras do sistema e leitura/gravação do arquivo
└── Livro.java       # modelo do livro
```

## Como rodar

Requisito: JDK 17 ou superior.

```bash
git clone https://github.com/thyagodev021/java-biblioteca.git
cd java-biblioteca
javac -d out src/biblioteca/*.java
java -cp out biblioteca.Main
```

## Conceitos usados

- Classes, encapsulamento e separação de responsabilidades
- `ArrayList` e Streams (`filter`, `map`, `toList`)
- Leitura e escrita de arquivos com `java.nio.file`
- Tratamento de exceções (`IllegalArgumentException`, `IllegalStateException`, `IOException`)
- `switch` moderno com `->`

## Autor

Thyago Ferreira - [@thyagodev021](https://github.com/thyagodev021)
