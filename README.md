# MedicAppIPC — App de gestão de medicação

Projeto académico (IPC) em **Kotlin + Jetpack Compose**, baseado no design Balsamiq fornecido.

## Como abrir no Android Studio

1. Descomprime o ZIP para uma pasta — por exemplo `Documentos/MedicAppIPC`.
2. Abre o **Android Studio** (versão Hedgehog 2023.1.1 ou mais recente).
3. *File → Open* → seleciona a **pasta** `MedicAppIPC` (não um ficheiro).
4. Quando o Android Studio perguntar pelo Gradle wrapper, escolhe **"Use Gradle wrapper"** (gera-se automaticamente).
5. Aguarda o **Gradle Sync** terminar (pode demorar alguns minutos da primeira vez — vai descarregar dependências).
6. Cria/usa um emulador Android (API 24 ou superior) ou liga um telemóvel por USB com Depuração USB ativada.
7. Carrega no botão **Run ▶** (ou `Shift+F10`).

> Se aparecer "Missing gradle wrapper jar": menu *File → Sync Project with Gradle Files* — o Android Studio repõe os ficheiros em falta.
> Se a primeira sincronização falhar por falta de SDK, aceita o que ele propõe instalar.

## O que está implementado

| Ecrã | Composable | Notas |
|---|---|---|
| Splash | `SplashScreen` | Avança após 1,5s ou ao tocar |
| Login | `LoginScreen` | Não valida credenciais (protótipo) |
| Registar | `RegistarScreen` | Valida campos vazios + passwords iguais |
| Dashboard | `DashboardScreen` | Lista do dia + checkbox para marcar tomado |
| Adicionar | `AdicionarMedicacaoScreen` | Nome / Dosagem / Hora / Frequência |
| Detalhe | `DetalheMedicacaoScreen` | Mostra estado "Tomado / Não tomado" e popup de confirmação |
| Histórico | `HistoricoScreen` | Tomas agrupadas por data (Hoje / Ontem / data) |
| Perfil | `PerfilScreen` | Avatar, nome, email, editar, terminar sessão |

A bottom navigation (Início / Histórico / Perfil) está em todos os ecrãs principais.

## Arquitetura

```
app/src/main/java/pt/ipc/medicapp/
├── MainActivity.kt            ← entry point, monta o tema e o NavGraph
├── data/
│   ├── Models.kt              ← Medicacao, RegistoToma, Utilizador, Frequencia
│   └── MedicRepository.kt     ← repositório singleton em memória
├── navigation/
│   ├── Routes.kt
│   └── AppNavGraph.kt         ← grafo de navegação Compose
└── ui/
    ├── theme/                 ← cores, tipografia, MedicAppTheme
    ├── components/            ← AppLogo, AppBottomBar (reutilizáveis)
    └── screens/               ← um ficheiro por ecrã
```

### Dados

Como é um protótipo, **os dados estão só em memória** (`MedicRepository` singleton). Quando fechas a app, perde-se tudo. O repositório arranca com 3 medicações e algum histórico de exemplo, para o ecrã não ficar vazio na demo.

Para passar para persistência: substituir `mutableStateListOf` por uma BD Room ou DataStore — a interface dos ecrãs não muda.

## Pequenos ajustes que podes querer fazer

- **Logo**: em `ui/components/AppLogo.kt` está desenhada uma pílula em código — se preferires usar a imagem que tens no Balsamiq, substitui por um `Image(painter = painterResource(R.drawable.logo))` e mete o ficheiro em `res/drawable/`.
- **Cores**: paleta em `ui/theme/Color.kt` (verde-água). Mexe à vontade.
- **Fontes**: Material 3 default — se quiseres uma fonte específica, adiciona em `res/font/` e referência em `Type.kt`.
- **Bottom nav clica em "Início" estando no Dashboard**: já está a tratar disso (no-op).

## Versões (testado com)

- Android Studio Hedgehog/Iguana
- Kotlin 1.9.24
- AGP 8.5.2
- Compose BOM 2024.06.00
- minSdk 24, targetSdk/compileSdk 34
