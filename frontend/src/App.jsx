import { BrowserRouter, Routes, Route } from "react-router";
import BoardPage from "./pages/BoardPage.jsx";
import DashBoardPage from "./pages/DashBoardPage.jsx";

export default function App() {
  return(
      <BrowserRouter>
          <Routes>
            <Route path={"/"} element={<DashBoardPage />}/>
            <Route path={"/board/:id"} element={<BoardPage />}/>
          </Routes>
      </BrowserRouter>
  )
}