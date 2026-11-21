# Divide Aí

Aplicativo Android para dividir a conta do bar ou do churrasco entre amigos, com gorjeta e resumo pronto para enviar no WhatsApp.

![Tela principal do Divide Aí](docs/tela-principal.png)

## Funcionalidades

- Digite o valor da conta em reais (ex.: `180,50`)
- Escolha o número de pessoas (de 1 a 50)
- Selecione a gorjeta: 0%, 10%, 12% ou 15%
- Veja o total com gorjeta e quanto cada pessoa paga
- Compartilhe o resumo por WhatsApp ou qualquer outro app

## Tecnologias

- Kotlin
- Jetpack Compose e Material 3
- Testes unitários com JUnit

## Decisões técnicas

- **Dinheiro em centavos (`Long`)**: evita os erros de arredondamento de `Double`. Os centavos que sobram na divisão são mostrados na tela.
- **Lógica separada da interface**: o cálculo fica em `Divisao.kt`, uma função pura coberta por testes (`DivisaoTest.kt`).
- **Tema escuro próprio**: paleta pensada para uso à noite, com contraste alto.

## Como rodar

1. Clone o repositório: `git clone https://github.com/devdenisviana/divide-ai.git`
2. Abra a pasta no Android Studio.
3. Aguarde a sincronização do Gradle e rode no emulador ou no celular (Android 8.0 ou superior).

## Próximos passos

- Dividir por itens consumidos
- Publicação na Google Play