import {useEffect, useState} from "react";
import {createNewBoard, fetchAllBoards} from "../api/boardApi.js";
import {Link} from "react-router";

export default function DashBoardPage() {
    const [boards, setBoards] = useState([]);
    const [newBoardName, setNewBoardName] = useState('')

    const loadBoards = () => {
        fetchAllBoards().then(data => setBoards(data.boardList));
    }

    useEffect(() => {
        loadBoards();
    }, []);

    const handleCreateNewBoard = async () => {
        if (newBoardName.trim() === '') return

        try {
            await createNewBoard(newBoardName)

            setNewBoardName('')
            loadBoards()
        } catch (err) {
            console.error("Board konnte nicht erstellt werden", err)
        }
    };

    return (
        <div>
            <h1>Mein Kanban Dashboard</h1>

            <div>
                <input
                    type={"text"}
                    placeholder={"Board Name..."}
                    onChange={event => setNewBoardName(event.target.value)}
                />
                <button onClick={handleCreateNewBoard}>
                    Erstellen
                </button>
            </div>

            <div>
                {boards.map(board => (
                    <Link
                        key={board.id}
                        to={`/board/${board.id}`}
                        style={{
                            display: 'block',
                            padding: '20px',
                            background: '#007bff',
                            color: 'white',
                            textDecoration: 'none',
                            borderRadius: '8px',
                            fontWeight: 'bold'
                        }}
                    >
                        {board.name}
                    </Link>
                ))}
            </div>

        </div>
    )
}