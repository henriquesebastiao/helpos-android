# CLAUDE.md — helpos-android

> Instruções para qualquer instância do Claude (Claude Code, Claude.ai, agentes derivados) que atue neste repositório.

## ⚠️ Use SEMPRE a skill `helpos-dev`

Este repositório é o **app Android nativo do HelpOS**. Antes de propor planos, escrever código, sugerir refatorações ou responder qualquer pergunta substantiva sobre este projeto, **carregue e siga a skill `helpos-dev`**.

A skill contém:

- Stack canônica fixada (versões, ferramentas, decisões já tomadas).
- Arquitetura Clean Architecture + MVVM e regras invioláveis.
- Modelo de domínio e glossário (CTO, PPPoE, IXC, OS, SVA, OUI, etc.).
- Padrões de UI Material 3 com cores dinâmicas.
- Convenções de código, commits e idioma.
- Anti-padrões — coisas explicitamente proibidas neste projeto.
- Arquivos de referência com exemplos concretos:
  - `references/android-conventions.md` — estrutura de módulos, `libs.versions.toml`, tema com cores dinâmicas, DTO + Retrofit + kotlinx.serialization, ApiKeyInterceptor, ViewModel + UiState + Composable completos, Navigation type-safe, módulo Hilt, permissões do Manifest, ícone adaptativo com camada monochrome, comandos Gradle.
  - `references/domain-glossary.md` — vocabulário do domínio ISP.

**Se a skill não estiver disponível no ambiente atual, pare e avise o usuário** antes de prosseguir com palpites. Não tente reconstruir as decisões de cabeça — elas existem precisamente para não serem reinventadas a cada sessão.

## Quando consultar a skill

Em **toda** sessão neste repositório, no início:

1. Leia o `SKILL.md` da `helpos-dev` integralmente.
2. Identifique qual feature/camada a tarefa toca (auth, clients, service orders, photo checklist, speedtest, wifi scan, voice report, wifi qr code, lan scan, tema, navegação, infra) e leia o arquivo de referência correspondente.
3. Só então comece a planejar.

Em sessões longas, releia o `SKILL.md` se a tarefa virar de assunto (ex.: estava em UI, agora foi para integração com Wi-Fi scan).

## Resumo de 30 segundos do projeto

Para você não começar totalmente no escuro caso a skill demore a carregar:

- **App Android nativo** do HelpOS, ferramenta de campo para técnicos de redes de provedor de internet de fibra.
- **Stack**: Kotlin 2.0+ (K2), Jetpack Compose + Material 3, **Min SDK 31** (Material You nativo), MVVM + Clean Architecture, Hilt, Retrofit 2 + OkHttp + **kotlinx.serialization** (não Gson/Moshi), Coroutines + Flow + StateFlow, Navigation Compose type-safe, Coil 3, ZXing core, `PdfDocument` nativo, `MediaRecorder`, `SpeechRecognizer` nativo, LibreSpeed (não Ookla), Timber.
- **Sem offline na v1** — sem Room, sem WorkManager, sem fila de sync. App exige internet sempre.
- **Auth**: API key no header `X-API-Key`, armazenada em `EncryptedSharedPreferences`. Sem JWT, sem tela de login — só uma tela de configuração onde cola a key.
- **Camadas**: `presentation` (Compose + ViewModel) → `domain` (UseCase + Model + Repository interface) ← `data` (RepositoryImpl + Retrofit + DTO + Mapper).
- **Idioma**: código em inglês, strings de UI / comentários / commits / mensagens em pt-BR.
- **Commits**: Conventional Commits estrito (`feat:`, `fix:`, `chore:`, etc.) em pt-BR.
- **IA**: chamada direta à Claude API do app (relato em primeira pessoa a partir da transcrição STT). Chave da Claude API também em `EncryptedSharedPreferences`.

A skill tem o resto.

## Comandos úteis

```bash
./gradlew assembleDebug              # build debug
./gradlew installDebug               # instalar no device conectado
./gradlew lint ktlintCheck detekt    # qualidade
./gradlew test                       # unit tests (JVM)
./gradlew connectedAndroidTest       # tests instrumentados (precisa de device/emulator)
./gradlew assembleRelease            # build release (precisa de signing config)
```

Antes de declarar qualquer tarefa "pronta": rodar lint, ktlint, detekt e testes. Sem exceções.

## O que NÃO fazer

- ❌ Ignorar a skill `helpos-dev` por considerar que "já sabe o suficiente".
- ❌ Criar XML Layouts (`activity_main.xml`, `<LinearLayout>`, etc.) — é Compose puro, sem exceção.
- ❌ Trocar kotlinx.serialization por Gson/Moshi, Hilt por Koin, Coroutines/Flow por RxJava/AsyncTask, Material 3 por Material 2.
- ❌ Usar `LiveData` em vez de `StateFlow`/`Flow`.
- ❌ Cores hard-coded (`Color(0xFFFF0000)`) — sempre `MaterialTheme.colorScheme.*`.
- ❌ Strings hard-coded em Composables — sempre `stringResource(R.string.*)`.
- ❌ Adicionar Room, WorkManager ou qualquer coisa de offline-first na v1. Se a tarefa parece exigir isso, **pare e pergunte** antes de implementar.
- ❌ Usar bibliotecas pesadas de gráficos (MPAndroidChart, Vico) para o gráfico de canais Wi-Fi — fazer com `Canvas` do Compose direto.
- ❌ Acessar `Context` dentro de ViewModel direto — passar via dependência.
- ❌ `Log.d()` / `Log.e()` — sempre Timber.

A lista completa de anti-padrões está na seção 9 do `SKILL.md`.

## Sobre permissões e integrações sensíveis

Algumas features deste app dependem de comportamentos do Android moderno que mudam por versão e por fabricante. **Sempre confira a skill** (seção 5 — Pontos de integração críticos) antes de implementar:

- **Wi-Fi scan**: throttling de 4 scans em 2 min no Android 9+, permissões `ACCESS_FINE_LOCATION` + `NEARBY_WIFI_DEVICES` (com `neverForLocation`).
- **LAN scan**: Android moderno bloqueia ARP raw — estratégia híbrida é ping sweep + mDNS (`NsdManager`), cobertura **parcial** por design.
- **OUI lookup**: arquivo `oui.txt` empacotado em `assets/` gzipado.
- **Transcrição**: `SpeechRecognizer` nativo do Android com `Locale("pt", "BR")`. Não tente chamar Claude API com áudio — Claude não aceita áudio nativamente.

---

**Em caso de conflito** entre algo escrito aqui e a skill `helpos-dev`, **a skill vence**. Este arquivo é um ponteiro; a skill é a fonte de verdade.