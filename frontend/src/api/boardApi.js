const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

//GET Board
export const fetchBoardsById = async (boardId) => {
    const response = await fetch(`${API_BASE_URL}/boards/${boardId}`)

    if (!response.ok) {
        throw new Error(`Fehler beim Laden des Boards: ${response.status}`)
    }

    return await response.json();
}

export const createNewBoard = async (boardName) => {
    const response = await  fetch(`${API_BASE_URL}/boards`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: boardName })
    });

    if (!response.ok) {
        throw new Error(`Fehler beim Erstellen des Boards: ${response.status}`)
    }

    return await response.json();
}


export const fetchAllBoards = async () => {
    const response = await fetch(`${API_BASE_URL}/boards`);

    if (!response.ok) {
        throw new Error(`Fehler beim Laden aller Boards: ${response.status}`)
    }

    return await response.json();
}