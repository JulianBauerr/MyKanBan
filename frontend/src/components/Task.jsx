import {updateTask} from "../api/taskApi.js";

export default function Task({ taskData , onTaskUpdated,
                                 onMoveUp, onMoveDown, isFirstTask, isLastTask,
                             }) {

    const handleCheckboxChange = async (event) => {
        const newDoneStatus = event.target.checked;

        try {
            await updateTask(taskData.id, {
                name: taskData.name,
                description: taskData.description,
                done: newDoneStatus,
            });
            onTaskUpdated();
        } catch (error) {
            console.error("Could not update task:", error);
        }

    }

    return (
        <div style={{ background: '#eee', margin: '5px 0', padding: '5px' }}>
            <strong>{taskData.name}</strong>
            <p>{taskData.description}</p>
            <input type="checkbox" id={`${taskData.id}done`} checked={taskData.done} onChange={handleCheckboxChange}/>
            <button onClick={onMoveUp} disabled={isFirstTask}>🔼</button>
            <button onClick={onMoveDown} disabled={isLastTask}>🔽</button>
        </div>
    )
}