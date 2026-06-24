# EasyMed — App Android de Gestão de Medicação

Projeto académico desenvolvido no âmbito da unidade curricular de **IPC** (Interfaces Pessoa-Computador), no Instituto Politécnico de Coimbra.

A aplicação permite ao utilizador **registar medicações**, **definir horários de toma** e **acompanhar o histórico** das tomas efetuadas.

---

## Tecnologias

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose
- **Navegação:** Navigation Compose
- **Tema:** Material 3
- **Mínimo Android:** API 24 (Android 7.0)
- **Compilado para:** API 34 (Android 14)

---

## Estado atual

Projeto em desenvolvimento incremental. Funcionalidades:

- [x] Ecrã de Splash com logo
- [x] Ecrã de Login
- [x] Ecrã de Registo
- [x] Dashboard com lista de medicações do dia
- [x] Adicionar medicação
- [x] Detalhe da medicação (marcar como tomada)
- [x] Histórico de tomas agrupado por data
- [x] Perfil do utilizador
- [x] Persistência de dados (Room)

---

## Como correr

### Pré-requisitos

- **Android Studio** Hedgehog 2023.1.1 ou mais recente
- **JDK 17** (vem incluído no Android Studio)
- **SDK Android 34** instalado (via SDK Manager)
- Emulador Android API 24+ ou dispositivo físico com Depuração USB ativada

### Passos

1. Clonar o repositório:
   ```bash
   git clone https://github.com/sanina11/EasyMed.git
   ```
2. Abrir a pasta no Android Studio (`File → Open`)
3. Aguardar o **Gradle Sync** terminar (pode demorar alguns minutos da primeira vez)
4. Selecionar um dispositivo no topo da janela
5. Carregar no botão **Run ▶** (ou `Shift + F10`)

---

## Estrutura do projeto

```
app/src/main/
├── java/pt/ipc/easymed/
│   ├── MainActivity.kt          # Ponto de entrada da app
│   ├── data/                    # Modelos de dados e repositório
│   ├── navigation/              # Grafo de navegação (rotas)
│   └── ui/
│       ├── theme/               # Cores, tipografia, tema Material
│       ├── components/          # Componentes reutilizáveis
│       └── screens/             # Um ficheiro por ecrã
└── res/
    ├── drawable/                # Imagens (logo, ícones)
    └── values/                  # strings.xml, colors.xml, themes.xml
```

---

## Arquitetura

A app segue um padrão simples baseado em:

- **Composables** (funções `@Composable`) para descrever a UI
- **State hoisting** — o estado vive nos ecrãs, é passado para baixo, e os eventos sobem em callbacks
- **Repositório singleton** em memória para gerir dados (a evoluir para Room)
- **Navegação centralizada** no `AppNavGraph`, desacoplada dos ecrãs

Os ecrãs não conhecem outros ecrãs — recebem callbacks (`onEntrar`, `onRegistar`, etc.) e o `AppNavGraph` decide o que cada callback faz.

---

## Autor

**Tiago Sanina** — Estudante de Engenharia Informática
**Miguel Sanina** — Estudante de Engenharia Informática

[GitHub](https://github.com/sanina11)
