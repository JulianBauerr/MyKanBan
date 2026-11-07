const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";

export const createNewTask = async (columnId, taskData) => {
    const response = await fetch(`${API_BASE_URL}/columns/${columnId}/tasks`,{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(taskData),
    });

    if (!response.ok) {
        throw new Error(`Fehler beim erstellen des Tasks: ${response.status}`)
    }

    return await response.json();
}

export const updateTask = async (taskId, updatedTaskData) => {
    const response = await fetch(`${API_BASE_URL}/tasks/${taskId}`,{
        method: "PUT",
        headers: {
            "content-Type": "application/json",
        },
        body: JSON.stringify(updatedTaskData),
    });

    if (!response.ok) {
        throw new Error("Fehler beim Updaten des Tasks: ${response.status}");
    }

    return await response.json();
}


