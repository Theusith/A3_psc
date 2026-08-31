# Atividades de Fixação — Full Stack (React + Spring Boot)

Domínio sugerido: **Sistema de Biblioteca** (empréstimo de livros). É estruturalmente idêntico ao seu `sistema-ReserV` (troque "cliente/reserva" por "usuário/empréstimo"), mas o domínio diferente evita que você só copie e cole — você tem que pensar em cada camada.

Se preferir outro domínio (tarefas, produtos de estoque, ingressos de evento...), a estrutura dos exercícios continua igual — só troque as entidades.

**Entidades sugeridas:**
- `Livro` (id, titulo, autor, isbn, disponivel)
- `Usuario` (id, nome, email, senha, tipo)
- `Emprestimo` (id, idUsuario, idLivro, dataEmprestimo, dataDevolucaoPrevista, devolvido)

---

## Bloco 1 — Backend: Model e Repository (JDBC)

### Exercício 1.1 — Classe Model
Crie `Livro.java` com:
- Atributos privados: `id`, `titulo`, `autor`, `isbn`, `disponivel` (boolean).
- Construtor vazio (obrigatório para o Jackson).
- Construtor com todos os campos exceto `id`.
- Getters e setters.

**Checklist de validação**: se você tentar fazer `@RequestBody Livro livro` sem o construtor vazio, o quê acontece? Teste e escreva a resposta num comentário.

### Exercício 1.2 — Repository com `PreparedStatement`
Crie `LivroRepository.java` com os métodos:

```java
void inserir(Livro livro) throws SQLException;
Livro buscarPorId(int id) throws SQLException;
List<Livro> listarTodos() throws SQLException;
List<Livro> listarDisponiveis() throws SQLException;   // WHERE disponivel = true
boolean atualizar(int id, Livro dados) throws SQLException;
boolean deletarPorId(int id) throws SQLException;
```

**Restrições (para forçar boas práticas):**
- Todo SQL usa `?` — nenhuma concatenação de string com valores.
- Toda `Connection`/`PreparedStatement` é aberta em `try-with-resources`.
- Nenhum método deixa uma `Connection` aberta sem fechar mesmo se der exceção.

**Pegadinha proposital**: implemente `buscarPorId` retornando `null` quando não encontrar, e escreva um teste manual (`main` temporário) chamando com um ID que não existe, pra confirmar que não estoura `NullPointerException` sem querer.

---

## Bloco 2 — Backend: Service e Controller (REST)

### Exercício 2.1 — Service com regra de negócio
Crie `GerenciadorLivros.java`. Além de delegar pro Repository, implemente **uma regra de negócio real**:

- `emprestarLivro(int idLivro)`: só permite se `disponivel == true`; se sim, marca como indisponível e retorna `true`; senão retorna `false`.
- `devolverLivro(int idLivro)`: marca `disponivel = true`.

Pense: essa regra deveria estar no Controller, no Service, ou no Repository? Justifique por escrito.

### Exercício 2.2 — Controller REST completo
Crie `LivroController.java` com `@RestController` e `@RequestMapping("/livros")`, implementando:

| Método | Rota | Status esperado |
|---|---|---|
| GET | `/livros` | 200 |
| GET | `/livros/{id}` | 200 ou 404 |
| GET | `/livros/disponiveis` | 200 |
| POST | `/livros` | 201 ou 400 (se faltar campo obrigatório) |
| PUT | `/livros/{id}` | 200 ou 404 |
| DELETE | `/livros/{id}` | 204 ou 404 |
| POST | `/livros/{id}/emprestar` | 200 (sucesso) ou 409 Conflict (se já emprestado) |

**Desafio extra**: o endpoint de emprestar devolve `409 Conflict` quando o livro já está emprestado — isso não estava no seu `ContaController` original. Pesquise `HttpStatus.CONFLICT` e pense em por que 409 é semanticamente mais correto que 400 nesse caso.

### Exercício 2.3 — `ErroResposta` reutilizado
Reaproveite a classe `ErroResposta` do seu projeto original para os retornos de erro. Isso é intencional: você deve conseguir copiar essa classe sem alterar nada, porque ela não tem regra de negócio nenhuma — só estrutura de resposta.

---

## Bloco 3 — Frontend: componentes e hooks

### Exercício 3.1 — Serviço de API
Crie `frontend/src/services/livrosApi.js`, espelhando seu `api.js`, mas:
- **desta vez, trate `response.ok`** em todas as funções (o bug que identificamos no seu projeto original). Se `!response.ok`, jogue uma exceção com a mensagem vinda do `ErroResposta`.

```js
export async function listarLivros() { /* ... */ }
export async function emprestarLivro(id) { /* ... */ }
```

### Exercício 3.2 — Componente de listagem
Crie `ListaLivros.jsx`:
- `useState` para `livros`, `carregando`, `erro`.
- `useEffect` com array de dependência `[]` pra carregar na montagem.
- Botão "Emprestar" em cada linha, desabilitado (`disabled`) quando `livro.disponivel === false`.

### Exercício 3.3 — Formulário com dois modos
Crie `FormularioLivro.jsx`, igual ao padrão do seu `FormularioCliente.jsx`:
- Um único componente que serve para **criar** e **editar**, controlado pela prop `livroParaEditar`.
- `useEffect` que repopula o formulário quando a prop muda.
- `async function` no submit, com `try/catch/finally` controlando um estado `enviando`.

**Desafio extra**: adicione validação client-side simples (ex: `isbn` com 13 dígitos) antes de chamar a API — sem usar nenhuma biblioteca, só JS puro.

### Exercício 3.4 — Levantamento de estado ("lifting state up")
No `App.jsx`, replique o padrão do projeto original: o estado de "qual livro está sendo editado" mora no `App`, não no filho, e desce como prop para `FormularioLivro` e sobe como callback (`aoEditar`) de `ListaLivros`.

Depois de implementar, escreva num comentário: por que esse estado não pode morar dentro de `ListaLivros` ou de `FormularioLivro` sozinho?

---

## Bloco 4 — Integração ponta a ponta

### Exercício 4.1 — CORS
Configure (ou reaproveite) o `WebConfig.java` para liberar `localhost:5173`. Teste **removendo** a configuração de propósito e observe o erro exato no console do navegador — anote a mensagem, isso ajuda a reconhecer o erro no futuro.

### Exercício 4.2 — Fluxo completo manual
Com backend e frontend rodando ao mesmo tempo:
1. Cadastre 2 livros.
2. Empreste um deles pela UI.
3. Tente emprestar o mesmo livro de novo — confirme que a UI mostra o erro 409 de forma decente (não um alert genérico).
4. Edite um livro e confirme que a lista recarrega sozinha (padrão `key={chaveLista}`).

### Exercício 4.3 (avançado) — Tabela de relacionamento
Implemente `Emprestimo` como entidade própria (em vez de só um boolean em `Livro`), com FK para `usuario` e `livro`, similar ao relacionamento `reservas.idCliente → usuarios.id` do seu projeto. Isso te obriga a lidar com JOIN em SQL cru — bom exercício de JDBC.

```sql
SELECT e.*, l.titulo, u.nome
FROM emprestimos e
JOIN livros l ON e.id_livro = l.id
JOIN usuarios u ON e.id_usuario = u.id
WHERE u.id = ?
```

---

## Bloco 5 (opcional, próximo nível) — Corrigindo os problemas do projeto original

Depois de terminar os blocos acima, volte no seu `sistema-ReserV` de verdade e aplique, um de cada vez:

1. Troque a senha em texto plano por `BCryptPasswordEncoder` (`spring-boot-starter-security` como dependência).
2. Substitua `new GerenciadorContas()` nos Controllers por injeção via construtor com `@Autowired`.
3. Adicione `response.ok` no `api.js` real do projeto.
4. Delete o `FormularioCliente.jsx` duplicado que não é mais usado.

Cada um desses é pequeno o suficiente pra fazer isolado, e cada um te obriga a mexer numa camada diferente (segurança, DI, frontend, limpeza).

---

## Como validar seu progresso

Para cada exercício, pergunte-se:
- Consigo explicar em uma frase por que cada camada (`Controller`/`Service`/`Repository`) existe, sem repetir a definição genérica?
- Se eu tirar o `try-with-resources`, o que quebra?
- Se eu tirar o array de dependências do `useEffect`, o que muda no comportamento?
- Meu Controller devolve o status HTTP certo pra cada cenário de erro, ou tudo vira 400/500 genérico?

Se travar em algum exercício, me chama que a gente debuga junto olhando o erro específico.
