# MyKanban – API Overview

Alle Pfade sind relativ zur Basis-URL `/api`.

---

## Board

Ressource mit:

- `id` (int, automatisch vergeben)
- `name` (String, Pflichtfeld)
- `columns` (Liste von Column-Objekten)

### POST /api/boards – Board erstellen

> **Kommentar:** Legt ein neues Board mit dem übergebenen Namen an. Es wird ohne Spalten erstellt (`columns` = leere Liste) und mit generierter ID gespeichert.

| | |
|---|---|
| **Payload** | `BoardCreateRequest`: `{ "name": "Mein Board" }` |
| **Erfolg** | `201 Created` – `BoardResponse` mit `id`, `name` und `columns` |
| **Fehler** | `400 Bad Request` – `name` fehlt oder ist `null` |

### GET /api/boards/{id} – Einzelnes Board lesen

> **Kommentar:** Lädt ein bestimmtes Board inklusive seiner Spalten und deren Tasks über die ID aus dem Pfad.

| | |
|---|---|
| **Payload** | keiner |
| **Erfolg** | `200 OK` – `BoardResponse` |
| **Fehler** | `404 Not Found` – Board mit dieser ID existiert nicht |

### GET /api/boards – Alle Boards lesen

> **Kommentar:** Gibt alle vorhandenen Boards zurück (jeweils inkl. Spalten und Tasks).

| | |
|---|---|
| **Payload** | keiner |
| **Erfolg** | `200 OK` – `MultipleBoardResponse` (Liste von `BoardResponse`) |

### PUT /api/boards/{id} – Board aktualisieren

> **Kommentar:** Ersetzt Name **und** Spaltenliste des Boards komplett (Voll-Update). Die ID aus dem Pfad bestimmt das zu ändernde Board.

| | |
|---|---|
| **Payload** | `BoardUpdateRequest`: `{ "name": "Neuer Name", "columns": [ ... ] }` |
| **Erfolg** | `200 OK` – aktualisiertes `BoardResponse` |
| **Fehler** | `404 Not Found` – Board existiert nicht |

### DELETE /api/boards/{id} – Board löschen

> **Kommentar:** Löscht das Board mit der angegebenen ID. Das gelöschte Board wird als Antwort noch zurückgegeben.

| | |
|---|---|
| **Payload** | keiner |
| **Erfolg** | `200 OK` – `BoardResponse` des gelöschten Boards |
| **Fehler** | `404 Not Found` – Board existiert nicht |

---

## Column

Ressource mit:

- `id` (int, automatisch vergeben)
- `name` (String, Pflichtfeld)
- `tasks` (Liste von Task-Objekten)
- `sorting` (Reihenfolge der Tasks)
- Farbe: Standard Weiß *(geplant / im Code noch nicht umgesetzt)*
- Emoji: optional *(geplant / im Code noch nicht umgesetzt)*

### POST /api/boards/{boardId}/columns – Spalte erstellen

> **Kommentar:** Erstellt eine neue Spalte und hängt sie an das Board mit `boardId` an. Existiert das Board nicht, schlägt der Aufruf fehl.

| | |
|---|---|
| **Payload** | `ColumnCreateRequest`: `{ "name": "In Arbeit" }` |
| **Erfolg** | `201 Created` – `ColumnResponse` |
| **Fehler** | `400 Bad Request` – ungültiges Payload<br>`404 Not Found` – Board mit `boardId` existiert nicht |

### PUT /api/columns/{id} – Spalte aktualisieren

> **Kommentar:** Ersetzt Name und Task-Liste der Spalte komplett. **Achtung:** Das Payload-Feld heißt im Code `Name` (großes N), nicht `name`.

| | |
|---|---|
| **Payload** | `ColumnUpdateRequest`: `{ "Name": "Neuer Name", "taskList": [ ... ], "sorting": [ ... ] }` |
| **Erfolg** | `200 OK` – aktualisiertes `ColumnResponse` |
| **Fehler** | `404 Not Found` – Spalte existiert nicht |

### DELETE /api/columns/{id} – Spalte löschen

> **Kommentar:** Löscht die Spalte mit der angegebenen ID und gibt sie als Antwort zurück.

| | |
|---|---|
| **Payload** | keiner |
| **Erfolg** | `200 OK` – `ColumnResponse` der gelöschten Spalte |
| **Fehler** | `404 Not Found` – Spalte existiert nicht |

### PATCH /api/columns/{columnId}/tasks/reorder – Sortierung ändern

> **Kommentar:** Legt die Reihenfolge der Tasks innerhalb einer Spalte neu fest. Die übergebene Liste **ersetzt** die komplette bisherige Reihenfolge. Enthält sie eine Task-ID, die nicht zu dieser Spalte gehört, wird der Aufruf mit `400` abgelehnt.

| | |
|---|---|
| **Payload** | `ColumnReorderRequest`: `{ "orderedTaskIds": [3, 1, 2] }` – Task-IDs in neuer Reihenfolge |
| **Erfolg** | `200 OK` – `ColumnResponse` mit neuer Sortierung |
| **Fehler** | `400 Bad Request` – Task-ID gehört nicht zur Spalte<br>`404 Not Found` – Spalte existiert nicht |

---

## Task

Ressource mit:

- `id` (int, automatisch vergeben)
- `name` (String, Pflichtfeld)
- `description` (String, optional)
- `done` (boolean, Pflichtfeld – "Erledigt")
- `steps` (Schritte, optional) *(geplant / im Code noch nicht umgesetzt)*

### POST /api/columns/{columnId}/tasks – Task erstellen

> **Kommentar:** Erstellt einen neuen Task und hängt ihn **ans Ende** der Tasks-Liste der Spalte `columnId` an.

| | |
|---|---|
| **Payload** | `TaskCreateRequest`: `{ "name": "Bugfix", "description": "...", "done": false }` |
| **Erfolg** | `200 OK` – `TaskResponse` *(im Code `HttpStatus.OK` – üblich wäre hier `201 Created`, siehe Korrekturen unten)* |
| **Fehler** | `400 Bad Request` – ungültiges Payload<br>`404 Not Found` – Spalte mit `columnId` existiert nicht |

### PUT /api/tasks/{id} – Task aktualisieren

> **Kommentar:** Ersetzt Name, Beschreibung und Erledigt-Status des Tasks komplett (Voll-Update).

| | |
|---|---|
| **Payload** | `TaskUpdateRequest`: `{ "name": "...", "description": "...", "done": true }` |
| **Erfolg** | `200 OK` – aktualisiertes `TaskResponse` |
| **Fehler** | `404 Not Found` – Task existiert nicht |

### DELETE /api/tasks/{id} – Task löschen

> **Kommentar:** Löscht den Task mit der angegebenen ID und gibt ihn als Antwort zurück.

| | |
|---|---|
| **Payload** | keiner |
| **Erfolg** | `200 OK` – `TaskResponse` des gelöschten Tasks |
| **Fehler** | `404 Not Found` – Task existiert nicht |

### PATCH /api/tasks/{columnId}/move – Task zwischen Spalten verschieben

> **Kommentar:** Verschiebt einen Task von seiner aktuellen Spalte in eine andere. `{columnId}` im Pfad ist die **alte** Spalte, aus der der Task entfernt wird; die Zielspalte steckt im Payload. Der Task wird in der Zielspalte **ans Ende** angehängt.

| | |
|---|---|
| **Payload** | `TaskMoveRequest`: `{ "taskId": 5, "moveToColumnId": 2 }` – Task-ID **und** neue Spalten-ID |
| **Erfolg** | `200 OK` – `TaskResponse` des verschobenen Tasks |
| **Fehler** | `404 Not Found` – Task oder eine der Spalten existiert nicht |
