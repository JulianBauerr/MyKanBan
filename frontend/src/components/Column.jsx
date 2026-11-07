import Task from "./Task.jsx";
import {useState} from "react";
import {createNewTask} from "../api/taskApi.js";
import {updateColumnSorting} from "../api/columnApi.js";

export default function Column({ columnData, onBoardChanged }) {
    const [isModalOpen, setIsModalOpen] = useState(false)

    const [taskName, setTaskName] = useState('')
    const [taskDesc, setTaskDesc] = useState('')

    const handleSaveTask = async () => {
        if (taskName.trim() === '') return

        try {
            await createNewTask(columnData.id, {
                    name: taskName,
                    description: taskDesc,
                    done: false
                });

            setIsModalOpen(false);
            setTaskName('');
            setTaskDesc('');

            onBoardChanged();
        } catch (err) {
            console.error("Task kann nicht erstellt werden", err)
        }
    }

    const handleCancel = () => {
        setIsModalOpen(false);
        setTaskName('');
        setTaskDesc('');
    }

    const handleMoveTaskUp = async (index) => {
        if (index === 0) return

        const newTasks = [...columnData.tasks]
        const tmp = newTasks[index - 1]
        newTasks[index - 1] = newTasks[index]
        newTasks[index] = tmp

        const orderedTaskIds = newTasks.map(task => task.id)

        try {
            await updateColumnSorting(columnData.id, orderedTaskIds)
            onBoardChanged()
        } catch (err) {
            console.error(err)
        }
    }

    const handleMoveTaskDown = async (index) => {
        if (index === columnData.tasks.length - 1) return

        const newTasks = [...columnData.tasks]
        const tmp = newTasks[index + 1]
        newTasks[index + 1 ] = newTasks[index]
        newTasks[index] = tmp

        const orderedTasksIds = newTasks.map(task => task.id)

        try {
            await updateColumnSorting(columnData.id, orderedTasksIds)
            onBoardChanged()
        } catch (err) {
            console.error(err)
        }
    }

    return(
        <div style={{ border: '1px solid black', padding: '10px', width: '250px' }}>
            <h2>{columnData.name}</h2>

            <div className="task-container" style={{}}>
                {columnData.tasks.map((task, index) => (
                    <Task
                        key={task.id}
                        taskData={task}
                        onTaskUpdated={onBoardChanged}

                        onMoveUp={() => handleMoveTaskUp(index)}
                        onMoveDown={() => handleMoveTaskDown(index)}
                        isFirstTask={index === 0}
                        isLastTask={index === columnData.tasks.length - 1}
                    />
                ))}
            </div>
            <button onClick={() => setIsModalOpen(true)}>New Task</button>

            {isModalOpen && (
                <div style={{
                    position: 'fixed', // Löst das Element aus dem normalen Fluss
                    top: 0, left: 0, right: 0, bottom: 0, // Zieht es über den ganzen Bildschirm
                    backgroundColor: 'rgba(0, 0, 0, 0.5)', // Halbtransparenter schwarzer Hintergrund
                    display: 'flex',
                    justifyContent: 'center', // Zentriert horizontal
                    alignItems: 'center',     // Zentriert vertikal
                    zIndex: 1000 // Stellt sicher, dass es GANZ VORNE ist
                }}>
                    <div style={{
                        background: 'white',
                        padding: '20px',
                        borderRadius: '8px',
                        width: '300px',
                        display: 'flex',
                        flexDirection: 'column', // Elemente untereinander
                        gap: '10px' // Abstand zwischen den Elementen
                    }}>
                        <h3>Neuen Task erstellen</h3>

                        <input
                            type="text"
                            placeholder="Task Name"
                            value={taskName}
                            onChange={(e) => setTaskName(e.target.value)}
                            style={{ padding: '8px' }}
                        />

                        <textarea
                            placeholder="Beschreibung (optional)"
                            value={taskDesc}
                            onChange={(e) => setTaskDesc(e.target.value)}
                            style={{ padding: '8px', minHeight: '60px' }}
                        />

                        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px', marginTop: '10px' }}>
                            <button onClick={handleCancel} style={{ padding: '8px' }}>Abbrechen</button>
                            <button onClick={handleSaveTask} style={{ padding: '8px', background: '#007bff', color: 'white', border: 'none', borderRadius: '4px' }}>Speichern</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    )
}