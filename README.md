# Batalla Naval en terminal (Java + Lanterna)

Proyecto final **1MD** de Ismael y Yamandú. Juego de Batalla Naval.

## Contenido

1. [Demostración](#demostración)
2. [Estructura del proyecto](#estructura-del-proyecto)
3. [Controles](#controles)
4. [Arquitectura y flujo general](#arquitectura-y-flujo-general)
5. [Estados de las celdas](#estados-de-las-celdas)
6. [Glosario de variables globales](#glosario-de-variables-globales)
7. [Glosario de funciones](#glosario-de-funciones)
8. [Estructuras de datos](#estructuras-de-datos)

## Demostración

<!-- Para agregar el video: edita este README en GitHub, arrastra el archivo de video aquí y GitHub genera el enlace solo. Luego borra la línea de abajo. -->

*Video de la partida próximamente.*

## Estructura del proyecto

```text
1MD-PtoyectoFinal/
├── lib/
│   └── lanterna-3.1.2.jar
├── src/
│   ├── Main.java            # Archivo principal
│   └── modules/
│       ├── Game.java        # Lógica del juego
│       ├── Menu.java        # Menús 
│       ├── Bot.java         # Bot
│       ├── Display.java     # Utilidades 
│       └── Texts.java       # Textos 
└── .gitignore
```

| Archivo | Rol |
|---|---|
| `Main.java` | Crea la pantalla, muestra el menú y lanza el juego. |
| `modules/Menu.java` | Menús de configuración (usuario, tamaño, dificultad, velocidad) y menú final. |
| `modules/Game.java` | Lógica del juego. Contiene además las clases auxiliares `Board`, `Boat` y `Player`. |
| `modules/Bot.java` | Decide los disparos del rival según la dificultad. |
| `modules/Display.java` | Define las utilidades de dibujado. |
| `modules/Texts.java` | Todos los textos y el arte ASCII. |

## Arquitectura y flujo general

`Main.main` → `Menu.show` (configuración) → `Game.game_loop` (coloca barcos) → `Game.game` (turnos) → `Menu.menu_end` → volver / guardar / salir.

## Estados de las celdas

El tablero es una matriz `int[][]` y cada celda guarda un número. Se accede como `board[x][y]`, donde **x = columna** e **y = fila**.

| Valor | Significado | Cómo se dibuja |
|---|---|---|
| `0` | Agua sin disparar | `■` azul |
| `1` | Barco intacto | `■` rojo  |
| `2` | Barco impactado (acierto) | `x` roja |
| `3` | Disparo fallado (agua) | `0` blanco |

## Glosario de variables globales

### Clase `Game`

| Variable | Tipo | Valor inicial | Qué hace |
|---|---|---|---|
| `size_board` | `int` | 12 | Lado del tablero (cuadrado N×N) (12 a 25). |
| `boat_quantity` | `int` | 0 | Cantidad de barcos por jugador. El menú la fija como `size_board / 2`. |
| `dificult` | `int` | 0 | Dificultad: 0 Fácil, 1 Medio, 2 Difícil, 3 Imposible. La usa `Bot`. |
| `velocity` | `int` | 1 | Multiplicador de velocidad de animaciones (x1 a x4). |
| `sleep_time` | `int` | 750 | Milisegundos de pausa entre mensajes. |
| `plays` | `int` | 0 | Total de disparos del jugador. |
| `acerted` | `int` | 0 | Disparos acertados del jugador. |
| `missed` | `int` | 0 | Disparos fallados del jugador. |

### Clase `Menu`

| Variable | Tipo | Qué hace |
|---|---|---|
| `box_style` | `char[]` | Caracteres del marco: `{┌, ┐, └, ┘, ─, │}` (esquina sup-izq, sup-der, inf-izq, inf-der, horizontal, vertical). |
| `box_width` | `int` = 60 | Ancho del marco de los menús. |
| `box_height` | `int` = 20 | Alto del marco de los menús. |
| `username` | `String` = "" | Nombre del jugador (máx. 20 caracteres). Se usa en el menú final y como nombre del archivo guardado. |

### Clase `Bot`

| Variable | Tipo | Qué hace |
|---|---|---|
| `random` | `Random` | Generador aleatorio del bot. |
| `acuracity` | `int` = `4 - Game.dificult` | Parámetro de puntería. Cuanto menor, más probable que el bot apunte a un barco. |

### Clase `Texts`

Todas son `public static` y contienen cadenas del juego.

| Variable | Tipo | Qué contiene |
|---|---|---|
| `boat` | `String[]` | Arte ASCII del barco del menú (7 líneas). |
| `options` | `String[]` | Botones del menú principal: Jugar, Salir. |
| `final_options` | `String[]` | Botones del menú final: Volver, Guardar, Salir. |
| `dificulty` | `String[]` | Etiquetas: Fácil, Medio, Difícil, Imposible. |
| `title`, `title_username`, `title_board_size`, `title_dificult`, `title_velocity` | `String` | Títulos de cada pantalla del menú. |
| `title_put_boat`, `title_down_put_boat` | `String` | Títulos de la pantalla de colocar barcos. |
| `title_win`, `title_lose` | `String` | Mensajes de victoria o derrota. |
| `subtitle_velocity`, `warning`, `warning_two` | `String` | Subtítulos e indicaciones de ayuda. |
| `blank_space`, `blank_space_board`, `blank_space_velocity` | `String` | Espacios en blanco que dibujan el campo de entrada con fondo negro. |
| `acerted`, `missed`, `bot_acerted`, `bot_missed` | `String` | Mensajes tras cada disparo. |
| `GameIndication1` a `GameIndication5` | `String` | Indicaciones durante la partida |

## Glosario de funciones

### `Main.java`

#### `main(String[] args)`
- **`screen`** (`Screen`): pantalla de la terminal de Lanterna, creada con `DefaultTerminalFactory` e iniciada con `startScreen()`.
- **`choice`** (`int`): resultado de `Menu.show`. `0` = jugar, `1` = salir.
- **Qué hace:** abre la pantalla, muestra el menú y, si se eligió jugar, llama a `Game.game_loop`. Captura `IOException` e imprime el error.

### `Menu.java`

#### `show(Screen screen)` → `int`
Controla todos los menús de configuración. Devuelve la opción elegida (0 = jugar, 1 = salir).
- **`txt`** (`TextGraphics`): escribe texto en la pantalla.
- **`selected`** (`int`): opción resaltada del menú principal (0 Jugar, 1 Salir).
- **`menu`** (`int`): máquina de estados con la pantalla actual: 0 principal, 1 nombre, 2 tamaño, 3 dificultad, 4 velocidad.
- **`size`** (`TerminalSize`): tamaño actual de la terminal (para centrar).
- **`key`** (`KeyStroke`): tecla presionada, leída con `screen.readInput()`.
- **`username_input`** (`String`): carácter tipeado que se agrega a `username`.
- **Qué hace:** en un bucle infinito limpia la pantalla, dibuja el menú, lee una tecla y, según `menu`, actualiza la variable global correspondiente (`username`, `Game.size_board`, `Game.boat_quantity`, `Game.dificult`, `Game.velocity`, `Game.sleep_time`) y avanza a la siguiente pantalla.

#### `menu_end(TextGraphics txt, Screen screen, boolean win, int columns)` → `int`
- **Parámetros:** `txt`, `screen`, `win` (true si ganó el jugador), `columns` (ancho de la terminal).
- **`selected`** (`int`): opción resaltada (0 Volver, 1 Guardar, 2 Salir).
- **`user`, `plays`, `acerted`, `missed`** (`String`): líneas con el nombre y las estadísticas.
- **`key`** (`KeyStroke`): tecla presionada.
- **Qué hace:** dibuja el marco, las estadísticas, el título de victoria o derrota y los botones. Las flechas mueven la selección de forma circular y Enter devuelve el índice elegido.

#### `draw_ascii(TextGraphics txt, int columns, int menu, int dificult)` (privada)
- **`boat_x`** (`int`): columna inicial del barco ASCII, centrado según la línea más ancha.
- **`i`** (`int`): índice del bucle sobre `Texts.boat`.
- **Qué hace:** activa negrita, dibuja el marco y el barco ASCII línea por línea, y llama a `draw_options` para el contenido de la pantalla actual.

#### `draw_options(TextGraphics txt, int menu, int columns, int dificult)` (privada)
- **`i`** (`int`): índice sobre `Texts.dificulty`.
- **Qué hace:** con un `switch` dibuja el título y los campos de cada pantalla: nombre (fondo negro con `username`), tamaño (`Game.size_board`), lista de dificultades (la elegida con fondo blanco) y velocidad (`x` + `Game.velocity`).

#### `draw_menu(TextGraphics txt, int columns, int selected, int menu, int dificult)` (privada)
- **`x`, `y`** (`int`): posición de los botones principales.
- **Qué hace:** llama a `draw_ascii` y, solo en el menú principal, dibuja los botones Jugar y Salir con `Display.draw_options`.

### `Game.java`

#### `game_loop(Screen screen)`
- **`txt`** (`TextGraphics`), **`size`** (`TerminalSize`), **`columns`**, **`rows`** (`int`, `rows` no se usa).
- **`mid_x`** (`int`): columna donde empieza el tablero para quedar centrado.
- **`player`, `bot`** (`Player`): jugador humano y rival, cada uno con su tablero.
- **`boat`** (`Boat`): barco que el jugador va a colocar. **`bot_boat`** (`Boat`): barco generado para el bot.
- **`random`** (`Random`), **`username`** (`String`, copia de `Menu.username`), **`game`** (`boolean`, no se usa).
- **`boat_sizes`** (`int[]`): tamaño de cada barco (largo `boat_quantity`). Hace que el jugador tenga **los mismos barcos que el bot**.
- **Qué hace:** (1) por cada barco genera uno de tamaño 3 a 5 para el bot, lo guarda en `boat_sizes` y lo ubica al azar; (2) por cada tamaño guardado pide al jugador colocar su barco con `put_boat`; (3) llama a `game`.

#### `game(Screen screen, TextGraphics txt, int mid_x, int columns, Player bot, Player player, int[][] pboard, String username)`
- **`turn`** (`int`): 0 = turno del jugador, 1 = turno del bot.
- **`x`, `y`** (`int`): posición del puntero en el tablero enemigo. Inicia en el centro.
- **`playing`** (`boolean`): controla el bucle principal.
- **`key`** (`KeyStroke`): tecla presionada.
- **`target`** (`int[][]`): referencia al tablero del bot (`bot.pboard`).
- **`a`** (`int`): opción del menú final (0 volver, 1 guardar, 2 salir; 3 = sin acción).
- **Qué hace:**
  - *Turno del jugador:* dibuja el tablero enemigo (sin mostrar barcos) y el puntero; las flechas lo mueven; con Enter dispara. Si la celda vale 1 pasa a 2 (acierto, sigue jugando); si vale 0 pasa a 3 (fallo, pasa el turno). Actualiza `acerted`, `missed` y `plays`.
  - *Turno del bot:* muestra el tablero propio con barcos, llama a `Bot.Play`; si falla devuelve el turno al jugador.
  - *Fin:* con `has_boats` revisa ambos tableros; si alguno queda sin barcos abre `Menu.menu_end` y ejecuta `reset_game` (volver) o `save_game` (guardar).

#### `put_boat(int columns, Screen screen, TextGraphics txt, int mid, int x1, int x2, int y1, int y2, int[][] board, Player player)`
- **`min_x`, `max_x`, `min_y`, `max_y`** (`int`): rectángulo que ocupa el barco, inicializado con las coordenadas del barco generado.
- **`boat_on`** (`boolean`): `true` cuando el barco se colocó con éxito.
- **`key`** (`KeyStroke`): tecla presionada.
- **Qué hace:** dibuja el tablero, los barcos ya puestos y el barco actual. Las flechas desplazan el barco. Con Enter llama a `player.set_boat`, que solo acepta si no hay solapamiento.

#### `has_boats(int[][] board)` → `boolean`
- **`i`, `j`** (`int`): índices de recorrido.
- **Qué hace:** devuelve `true` si queda al menos una celda con valor 1. Sirve para detectar al ganador.

#### `reset_game(Screen screen, Player player, Player bot)`
- **Qué hace:** recrea los tableros vacíos con `reset_board`, restablece las variables globales (`username`, `dificult`, `velocity`, `size_board`, `sleep_time`, `plays`, `acerted`, `missed`) y llama a `Main.main` para volver al menú.

#### `write(Path path, String content)`
- **Qué hace:** escribe el texto en el archivo con `Files.writeString`. Si falla, imprime el mensaje del error.

#### `board_to_string(int[][] board, int size)` → `String`
- **`Sboard`** (`String`): texto acumulado. **`maxLen`** (`int`): ancho del número más largo. **`len`** (`int`): largo de cada número.
- **Qué hace:** convierte la matriz en texto con cada número alineado a `maxLen` y una fila por línea.

#### `save_game(int[][] bot_board, int[][] player_board, String user, int misses, int acerts, int size_board)`
- **`path`** (`Path`): archivo `<usuario>.txt`. **`content`** (`String`): nombre, tiros acertados y errados, y tableros.
- **Qué hace:** arma el reporte y lo guarda con `write`.

### Clases auxiliares en `Game.java`

#### Clase `Board`
- **`board`** (`int[][]`, privado): matriz N×N inicializada en 0.
- **`Board(int size)`**: crea la matriz.
- **`get_board()`**: devuelve la matriz.
- **`set_board(int x, int y)`**: si la celda no es 1 la pone en 1; si ya es 1 la pone en 2. Devuelve `true`. (No se usa en el juego.)

#### Clase `Boat`
- **`horizontal`** (`boolean`, privado): orientación.
- **`size`** (`int`): largo del barco.
- **`min_x`, `min_y`** (`int`): esquina inicial, siempre 0.
- **`max_x`, `max_y`** (`int`): esquina final. Vertical: `max_x = 0`, `max_y = size-1`. Horizontal: `max_y = 0`, `max_x = size-1`.
- **`generate_boat(int size_boat)`**: guarda el tamaño y elige orientación al azar con una variable local `a` (`Random`).

#### Clase `Player`
- **`board`** (`Board`) y **`pboard`** (`int[][]`): objeto tablero y acceso directo a su matriz.
- **`Player(int size)`** y **`reset_board(int size)`**: crean un tablero nuevo y vacío.
- **`set_boat(min_x, max_x, min_y, max_y)`** → `boolean`: si no hay solapamiento marca con 1 el rectángulo y devuelve `true`. Variable local: `boat_in`.
- **`verifyi_boat_in(min_x, max_x, min_y, max_y)`** → `boolean`: devuelve `true` si alguna celda del rectángulo ya vale 1. Variable local: `boat_in`.
- **`get_board()`** y **`set_board(int x, int y)`**: delegan en `Board`.
- **`set_boat_random_place(int[][] board, int max_x, int max_y, Random random, int size_board)`**: variables locales `x`, `y` (esquina aleatoria limitada para no salirse) y `free` (`boolean`, área libre). Repite hasta encontrar un lugar libre y marca las celdas con 1.

### `Bot.java`

#### `Play(int[][] board, int size)` → `boolean`
Disparo del bot sobre el tablero del jugador. Devuelve `true` si acierta.
- **`target`** (`int`): tipo de celda a la que apunta (1 = barco, 0 = agua).
- **`count`** (`int`): cantidad de celdas candidatas.
- **`n`** (`int`): índice aleatorio de la celda elegida. **`k`** (`int`): contador del recorrido. **`i`, `j`** (`int`): índices.
- **Qué hace:** con probabilidad `1/acuracity` apunta a un barco; si no, a agua. Cuenta candidatas, elige una al azar y dispara con `shoot`. Si no quedan celdas de ese tipo, cambia de objetivo con `1 - target`.

#### `count_cells(int[][] board, int size, int value)` → `int` (privada)
Cuenta cuántas celdas tienen el valor indicado (acumulador `count`).

#### `shoot(int[][] board, int x, int y)` (privada)
Si la celda es 1 la cambia a 2 (impacto); en otro caso la cambia a 3 (fallo).

### `Display.java`

| Función | Qué hace |
|---|---|
| `sleep(int sleep)` | Pausa `sleep` milisegundos con `Thread.sleep`. Si se interrumpe, restaura el estado de interrupción del hilo. |
| `center_x(int total, int width)` → `int` | Devuelve `(total - width) / 2`, la columna inicial para centrar un elemento. |
| `max_line_width(String[] lines)` → `int` | Devuelve el largo de la línea más larga (variable `max`). |
| `draw_box(txt, x, y, width, height, style)` | Dibuja un marco con los 6 caracteres de `style` (variables `top_left`, `top_right`, `bottom_left`, `bottom_right`, `horizontal`, `vertical`). |
| `draw_options(txt, options, selected, x, y, spacing)` | Escribe las opciones en vertical; la `selected` con fondo blanco y texto negro. Restaura los colores al final. |
| `draw_board(txt, x, y, size, board)` | Dibuja cada celda según su valor: 2 → `x` roja, 3 → `0` blanca, 0 y 1 → `■` azul. |
| `draw_boats(txt, board, size, x, y)` | Pinta de rojo con `■` las celdas con valor 1 (barcos intactos del jugador). |
| `draw_boats_and_pointer(txt, board, size, x, y, min_x, max_x, min_y, max_y)` | Dibuja los barcos colocados en rojo y el barco que se está colocando con fondo rojo, rellenando el espacio entre celdas contiguas. |
| `bot_animation()` |                                      |
| `draw_pointer(mid, top, txt, x, y)` | Dibuja el puntero (`■` azul sobre fondo rojo) en la celda `(x, y)` y restaura los colores. |

## Estructuras de datos

| Estructura | Dónde | Para qué sirve |
|---|---|---|
| `int[][]` | `Board`, `Player.pboard` | Tablero N×N con los estados 0 a 3. |
| `int[]` `boat_sizes` | `game_loop` | Tamaños de los barcos, compartidos entre bot y jugador. |
| `String[]` | `Texts` | Listas de opciones y arte ASCII. |
| `char[]` `box_style` | `Menu` | Los 6 caracteres del marco. |
| `Board`, `Boat`, `Player` | `Game.java` | Clases de dominio del juego. |
| `Screen`, `TextGraphics`, `KeyStroke`, `TerminalSize` | Lanterna | Pantalla, dibujo, teclado y tamaño de la terminal. |
| `Random` | `Game`, `Boat`, `Bot` | Generación aleatoria. |
| `Path` | `Game.save_game` | Ruta del archivo de guardado. |