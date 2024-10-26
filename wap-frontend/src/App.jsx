import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.bundle.min.js"
import AddApartment from './components/apartment/AddApartment'
import ExistingApartments from "./components/apartment/ExistingApartments"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import EditApartment from "./components/apartment/EditApartment"
import Home from "./components/home/Home"

function App() {
  return (
    <>
      <main>
        <Router>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/edit-apartment/:apartmentId" element={<EditApartment />} />
            <Route path="/existing-apartments" element={<ExistingApartments />} />
            <Route path="/add-apartment" element={<AddApartment />} />
          </Routes>
        </Router>
      </main>
    </>
  )
}

export default App
