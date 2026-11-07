import {useEffect, useState} from "react";
import {fetchBoardsById} from "../api/boardApi.js";
import Column from "../components/Column.jsx";
import {createNewColumn} from "../api/columnApi.js";
import {useParams} from "react-router";

export default function BoardPage() {
    const {id} = useParams()
    const [board, setBoard] = useState(null);
    const [error, setError] = useState(null);
    const [newColumnName, setNewColumnName] = useState('')

    const loadBoard = () => {
        fetchBoardsById(id)
            .then((dataFromBackend) => {
                setBoard(dataFromBackend)
            })
            .catch((err) =>
                setError(err.message))
    };

    useEffect(() => {
        loadBoard();
    }, []);

    const handleCreateNewColumn = async () => {
        if (newColumnName.trim() === '') return

        try {
            await createNewColumn(board.id, newColumnName);
            setNewColumnName('')
            loadBoard()
        } catch (err) {
            console.error('Spalte konnte nicht angelegt werden', err)
        }
    }


    if (error) return <div>Fehler: {error}</div>
    if (board == null) return <div>Lade Board, warte auf API ...</div>


    return (
        <div className="board-page">
            <h1>{board.name}</h1>

            <div className="columns-container" style={{display: 'flex', gap: '20px'}}>
                {board.columns.map((col) => (
                    <Column key={col.id} columnData={col} onBoardChanged={loadBoard}/>
                ))}
            </div>
            <input
                type={"text"}
                placeholder={"Name der Spalte..."}
                onChange={(event) => setNewColumnName(event.target.value)}
                style={{ width: '100%', padding: '5px', marginBottom: '10px' }}
            />
            <button onClick={handleCreateNewColumn} style={{ width: '100%', padding: '8px', cursor: 'pointer' }} >
                + Spalte hinzufügen</button>
        </div>
    );
}