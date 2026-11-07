const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

export const createNewColumn = async (boardId, columnName) => {
    const response = await fetch(`${API_BASE_URL}/boards/${boardId}/columns`,{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({name: columnName}),
    });

    if (!response.ok) {
        throw new Error(`Fehler beim erstellen der Spalte: ${response.status}`)
    }

    return await response.json();
}

export const updateColumnSorting = async (columnId, orderedTaskIds) => {
    const response = await fetch(`${API_BASE_URL}/columns/${columnId}/tasks/reorder`, {
        method: "PATCH",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({
            orderedTaskIds: orderedTaskIds,
        })
    });

    if (!response.ok) {
        throw new Error(`Fehler bei Reorder der Column: ${response.status}`)
    }

    return await response.json()
}
